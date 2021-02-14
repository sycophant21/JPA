package com.domain.helper;

import java.util.List;

public class Table {
    private final String tableName;
    private List<Field> entities;
    private PrimaryKey primaryKey;
    private List<ForeignKey> foreignKeys;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public Table(String tableName, List<Field> entities, PrimaryKey primaryKey, List<ForeignKey> foreignKeys) {
        this.tableName = tableName;
        this.entities = entities;
        this.primaryKey = primaryKey;
        this.foreignKeys = foreignKeys;
    }

    public String getTableName() {
        return tableName;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        foreignKeys.add(foreignKey);
    }

    public List<Field> getEntities() {
        return entities;
    }

    public void createTable() {
        String query = "CREATE TABLE " + tableName + "(\n";
        for (Field field : entities) {
            query = query.concat(field.getNameInDB() + " " + field.getDataTypeInDB() + ",\n");
        }
        if (!foreignKeys.isEmpty()) {
            for (ForeignKey foreignKey : foreignKeys) {
                for (Field field : foreignKey.getPrimaryKey().getPrimaryKeys()) {
                    query = query.concat(field.getNameInDB() + " " + field.getDataTypeInDB() + ",\n");
                }
            }
            for (ForeignKey foreignKey : foreignKeys) {
                query = query.concat(foreignKey.createForeignKey() + ",\n");
            }
        }
        query = query.concat(primaryKey.createPrimaryKey() + ",\n");
        System.out.println(query.substring(0,query.lastIndexOf(",")).concat("\n);"));
    }

}
