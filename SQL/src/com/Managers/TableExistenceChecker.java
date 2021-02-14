package com.Managers;

import com.domain.helper.Field;
import com.domain.helper.Table;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TableExistenceChecker {
    private final TableCorrector tableCorrector;
    private final ClassManager classManager;
    private final DBManager dbManager;
    private final List<Table> tables;

    public TableExistenceChecker(TableCorrector tableCorrector, ClassManager classManager, DBManager dbManager, List<Table> tables) {
        this.tableCorrector = tableCorrector;
        this.classManager = classManager;
        this.dbManager = dbManager;
        this.tables = tables;
    }

    public void checkExistence() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<String, List<com.domain.helper.Field>> entities = dbManager.getAllTables();
        for (Table table : tables) {
            if (entities.containsKey(table.getTableName())) {
                matchEntities(entities.get(table.getTableName()), table.getEntities());
            } else {
                table.createTable();
            }
        }
    }

    private void matchEntities(List<com.domain.helper.Field> entities, List<com.domain.helper.Field> fields) {
        for (Field field : entities) {
            boolean found = false;
            for (Field f : fields) {
                if (f.getNameInDB().equalsIgnoreCase(field.getNameInDB())) {

                }
            }
        }
    }
}