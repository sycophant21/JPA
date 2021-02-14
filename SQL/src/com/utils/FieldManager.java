package com.utils;

import com.domain.helper.DataTypeClass;

import java.lang.reflect.Field;

public class FieldManager {
    public boolean isPrimaryKey(Field field) {
        return Utility.isPrimaryKey(field);
    }

    public boolean isPrimaryDataType(Field field) {
        return Utility.isPrimary(field.getType().getSimpleName());
    }

    public com.domain.helper.Field fieldConverter(Field field) {
        String nameInClass = field.getName();
        String nameInDb = Utility.getDBTypeName(field.getName());
        DataTypeClass dataTypeTableInClass = DataTypeClass
    }

}
