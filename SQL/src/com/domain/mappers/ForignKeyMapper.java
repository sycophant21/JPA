package com.domain.mappers;

import com.domain.helper.ForeignKey;
import com.domain.helper.Table;
import com.domain.mappers.FieldMapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ForignKeyMapper implements FieldMapper {
    private final Map<String, List<ForeignKey>> foreignKeysMap;
    public ForignKeyMapper(Map<String, List<ForeignKey>> foreignKeysMap) {
        this.foreignKeysMap = foreignKeysMap;
    }


    @Override
    public void map(Field field, Table table) {

    }
}
