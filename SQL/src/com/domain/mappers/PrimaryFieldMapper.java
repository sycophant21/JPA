package com.domain.mappers;

import com.Managers.ClassTableMapper;
import com.domain.helper.CustomAnnotations;
import com.domain.helper.DBMapping;
import com.domain.helper.Table;
import com.utils.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PrimaryFieldMapper implements FieldMapper {
    private final ClassTableMapper classTableMapper;

    public PrimaryFieldMapper(ClassTableMapper classTableMapper) {
        this.classTableMapper = classTableMapper;
    }

    @Override
    public void map(Field field, Table table) {
        return;
    }
    private List<com.domain.helper.Field> getPrimaryFields(Class clazz) throws NoSuchFieldException {
        List<com.domain.helper.Field> primaryFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (Utility.isPrimary(field.getType().getSimpleName())) {
                if (field.getType().getSimpleName().equalsIgnoreCase("String")) { // Remove this if
                    primaryFields.add(new com.domain.helper.Field(field.getName(), Utility.getDBTypeName(field.getName()), field.getType().getSimpleName(), Utility.getDataTypeNames(field.getType().getSimpleName()) + "(" + Utility.getVarcharSize(field) + ")", Utility.isNullable(field), Utility.getVarcharSize(field)));
                } else {
                    primaryFields.add(new com.domain.helper.Field(field.getName(), Utility.getDBTypeName(field.getName()), field.getType().getSimpleName(), Utility.getDataTypeNames(field.getType().getSimpleName()), Utility.isNullable(field), Utility.getVarcharSize(field)));
                }
            }
        }
        return primaryFields;
    }
    private List<com.domain.helper.Field> getNonPrimaryInlineFields(Class clazz, String tableName) throws NoSuchFieldException {
        List<com.domain.helper.Field> nonPrimaryFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Utility.isPrimary(field.getType().getSimpleName())) {
                CustomAnnotations customAnnotations = field.getAnnotation(CustomAnnotations.class);
                if (customAnnotations != null) {
                    if (customAnnotations.dbMapping() == DBMapping.INLINE) {
                        nonPrimaryFields.addAll(getPrimaryFields(field.getType()));
                        nonPrimaryFields.addAll(getNonPrimaryInlineFields(field.getType(), classTableMapper.getTableName(field.getType().getSimpleName())));
                    }
                }
            }
        }
        return nonPrimaryFields;
    }
}
