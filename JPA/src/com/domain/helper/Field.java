package com.domain.helper;

public class Field {
    private final String nameInClass;
    private final String nameInDB;
    private final DataTypeClass dataTypeTableInClass;
    private final DataTypeTable dataTypeTableInDB;
    private final boolean isNullable;
    private int fieldSize;

    public Field(String nameInClass, String nameInDB, DataTypeClass dataTypeTableInClass, DataTypeTable dataTypeTableInDB, boolean isNullable, int fieldSize) {
        this.nameInClass = nameInClass;
        this.nameInDB = nameInDB;
        this.dataTypeTableInClass = dataTypeTableInClass;
        this.dataTypeTableInDB = dataTypeTableInDB;
        this.isNullable = isNullable;
        this.fieldSize = fieldSize;
    }
    public Field(String nameInClass, String nameInDB, DataTypeClass dataTypeTableInClass, DataTypeTable dataTypeTableInDB, boolean isNullable) {
        this.nameInClass = nameInClass;
        this.nameInDB = nameInDB;
        this.dataTypeTableInClass = dataTypeTableInClass;
        this.dataTypeTableInDB = dataTypeTableInDB;
        this.isNullable = isNullable;
    }


    public String getNameInClass() {
        return nameInClass;
    }

    public String getNameInDB() {
        return nameInDB;
    }

    public DataTypeClass getDataTypeInClass() {
        return dataTypeTableInClass;
    }

    public DataTypeTable getDataTypeInDB() {
        return dataTypeTableInDB;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public int getFieldSize() {
        return fieldSize;
    }



}
