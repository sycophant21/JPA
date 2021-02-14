package com.Managers;

import com.domain.helper.CustomAnnotations;
import com.domain.helper.Table;
import com.utils.Utility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryManager<T> {
    private final ConnectionManager connectionManager;
    private final ClassManager classManager;
    private final List<Table> tables;

    public QueryManager(ConnectionManager connectionManager, ClassManager classManager, List<Table> tables) {
        this.connectionManager = connectionManager;
        this.classManager = classManager;
        this.tables = tables;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return connectionManager.getStatement().executeQuery(query);
    }

    public List<T> executeQuery(String query, Class<T> clazz) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        return createClassInstanceFromResultSet(executeQuery(query), clazz);
    }

    private List<T> createClassInstanceFromResultSet(ResultSet resultSet, Class<T> clazz) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T object = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(object, resultSet.getString(getFieldName(field)));
            }
            list.add(object);
        }
        return list;
    }

    public List<T> executeQuery(String[] select, String from, String[] where, Class<T> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return executeQuery(selectQueryGenerator(select, from, where), clazz);

    }

    public String selectQueryGenerator(String[] whatToSelect, String from, String[] where) {
        String query = "SELECT ";
        query = concat(whatToSelect, query);
        query = query.substring(0, query.lastIndexOf(','));
        query = query.concat("\nFROM " + from + "\nWHERE ");
        query = concat(where, query);
        query = query.substring(0, query.lastIndexOf(','));
        return query.concat("\n;");
    }

    private String concat(String[] strings, String s) {
        for (String s1 : strings) {
            s = s.concat(s1 + ", ");
        }
        return s;
    }

    private String getFieldName(Field field) {
        String name;
        if (field.getAnnotation(CustomAnnotations.class) != null) {
            if (!field.getAnnotation(CustomAnnotations.class).alternateFieldName().equalsIgnoreCase("")) {
                name = field.getAnnotation(CustomAnnotations.class).alternateFieldName();
            } else {
                name = Utility.getDBTypeName(field.getName());
            }
        } else {
            name = Utility.getDBTypeName(field.getName());
        }
        return name;
    }

    public String dropColumnQueryGenerator(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + "\nDROP COLUMN " + columnName;
    }

    public String addColumnQueryGenerator(String tableName, String columnName, String dataType, boolean isPrimary) {
        if (isPrimary) {
            return "ALTER TABLE " + tableName + "\nADD " + columnName + " " + Utility.getDataTypeNames(dataType) + " PRIMARY KEY";
        }
        return "ALTER TABLE " + tableName + "\nADD " + columnName + " " + Utility.getDataTypeNames(dataType);
    }
}