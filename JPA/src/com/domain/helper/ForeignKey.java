package com.domain.helper;

public class ForeignKey {
    private final PrimaryKey primaryKey;
    private final String reference;


    public ForeignKey(PrimaryKey primaryKey, String reference) {
        this.primaryKey = primaryKey;
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public String createForeignKey() {
        String query = "FOREIGN KEY (";
        String foreignKey = "";
        for (Field field : primaryKey.getPrimaryKeys()) {
            foreignKey = foreignKey.concat(field.getNameInDB() + ",");
        }
        foreignKey = foreignKey.substring(0,foreignKey.lastIndexOf(","));
        return query.concat(foreignKey + ") REFERENCES " + reference + "(" + foreignKey + ")");
    }
}
