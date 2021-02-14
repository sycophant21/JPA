package com.Managers;

public class TableCorrector {
    private final QueryManager queryManager;

    public TableCorrector(QueryManager queryManager) {
        this.queryManager = queryManager;
    }


    public void addColumn(String tableName, String columnName, String dataType, boolean isPrimary) {
        System.out.println(queryManager.addColumnQueryGenerator(tableName, columnName, dataType, isPrimary));
    }

    public void removeColumn(String tableName, String columnName) {
        System.out.println(queryManager.dropColumnQueryGenerator(tableName, columnName));
    }

}