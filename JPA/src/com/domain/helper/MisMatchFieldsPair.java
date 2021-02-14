package com.domain.helper;

import java.util.List;

public class MisMatchFieldsPair {
    private final List<Field> fieldsNotInDB;
    private final List<Field> fieldsNotInClass;

    public MisMatchFieldsPair(List<Field> fieldsNotInDB, List<Field> fieldsNotInClass) {
        this.fieldsNotInDB = fieldsNotInDB;
        this.fieldsNotInClass = fieldsNotInClass;
    }

    public List<Field> getFieldsNotInDB() {
        return fieldsNotInDB;
    }

    public List<Field> getFieldsNotInClass() {
        return fieldsNotInClass;
    }
}
