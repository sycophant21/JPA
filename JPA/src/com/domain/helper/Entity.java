package com.domain.helper;

import java.util.Objects;

public class Entity {
    private String tableName;
    @CustomAnnotations(alternateFieldName = "COLUMN_NAME")
    private String entityName;
    @CustomAnnotations(alternateFieldName = "DATA_TYPE")
    private String entityDataType;
    @CustomAnnotations(alternateFieldName = "COLUMN_KEY")
    private String isPrimary;
    @CustomAnnotations(alternateFieldName = "IS_NULLABLE")
    private String isNull;
    @CustomAnnotations(alternateFieldName = "CHARACTER_MAXIMUM_LENGTH")
    private String stringSize;

    public Entity() {}

    public Entity(String tableName, String entityName, String entityDataType, String isPrimary, String isNull, String stringSize) {
        this.tableName = tableName;
        this.entityName = entityName;
        this.entityDataType = entityDataType;
        this.isPrimary = isPrimary;
        this.isNull = isNull;
        this.stringSize = stringSize;
    }

    public String getStringSize() {
        return stringSize;
    }

    public String getIsNull() {
        return isNull;
    }

    public String getTableName() {
        return tableName;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public String getEntityDataType() {
        return entityDataType;
    }

    public String getEntityName() {
        return entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return getTableName().equals(entity.getTableName()) && getEntityName().equals(entity.getEntityName()) && getEntityDataType().equals(entity.getEntityDataType()) && getIsPrimary().equals(entity.getIsPrimary()) && getIsNull().equals(entity.getIsNull());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTableName(), getEntityName(), getEntityDataType(), getIsPrimary(), getIsNull());
    }
}
