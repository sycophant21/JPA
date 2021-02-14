package com.domain.mappers;

import com.domain.helper.Table;

import java.lang.reflect.Field;

public interface FieldMapper {

    void map(Field field, Table table);
}
