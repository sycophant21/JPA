package com.domain.helper;

import java.util.List;

public class TableBuilder {
    private String tableName;
    private List<Field> entities;
    private PrimaryKey primaryKey;
    private List<ForeignKey> foreignKeys;

    public TableBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public TableBuilder setEntities(List<Field> entities) {
        this.entities = entities;
        return this;
    }

    public TableBuilder setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public TableBuilder setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
        return this;
    }
    public Table build() {
        return new Table(tableName,entities,primaryKey,foreignKeys);
    }
}
