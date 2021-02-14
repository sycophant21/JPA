package com.Managers;

import com.domain.helper.Entity;
import com.domain.helper.Field;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class DBManager {
    private final QueryManager<Entity> queryManager;

    public DBManager(QueryManager<Entity> queryManager) {
        this.queryManager = queryManager;
    }

    public Map<String, List<Field>> getAllTables() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        List<Entity> resultSet = queryManager.executeQuery(new String[]{"TABLE_NAME", "COLUMN_NAME", "DATA_TYPE", "COLUMN_KEY", "IS_NULLABLE", "CHARACTER_MAXIMUM_LENGTH"},"INFORMATION_SCHEMA.COLUMNS",new String[]{"TABLE_SCHEMA = 'JPA'"}, Entity.class);
        Map<String, List<Field>> fields = new HashMap<>();
        for (Entity e : resultSet) {
            boolean isNullable = false;
            int size = 0;
            if (e.getIsNull().equalsIgnoreCase("YES")) {
                isNullable = true;
            }
            if (e.getStringSize().length() > 0) {
                size = Integer.parseInt(e.getStringSize());
            }
            Field field = new Field("", e.getEntityName(), "", e.getEntityDataType(), isNullable, size);
            if (fields.containsKey(e.getTableName())) {
                fields.get(e.getTableName()).add(field);
            }
            else {
                List<Field> fieldList = new ArrayList<>();
                fieldList.add(field);
                fields.put(e.getTableName(), fieldList);
            }
        }
        return fields;
    }
}
