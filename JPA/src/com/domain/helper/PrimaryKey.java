package com.domain.helper;

import java.util.List;

public class PrimaryKey {
    private final List<Field> primaryKeys;

    public PrimaryKey(List<Field> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<Field> getPrimaryKeys() {
        return primaryKeys;
    }

    public String createPrimaryKey() {
        String query = "PRIMARY KEY (";
        for (Field field : primaryKeys) {
            query = query.concat(field.getNameInDB() + ",");
        }
        return query.substring(0,query.lastIndexOf(",")).concat(")");
    }
}
