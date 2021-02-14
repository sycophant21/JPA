package com.Managers;

import com.domain.helper.*;
import com.domain.mappers.ForignKeyMapper;
import com.domain.mappers.PrimaryKeyMapper;
import com.domain.mappers.FieldMapper;
import com.domain.mappers.PrimaryFieldMapper;
import com.utils.FieldManager;
import com.utils.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class TableManager {
    private final ClassManager classManager;
    private final List<Table> tables;
    private final Map<String, List<ForeignKey>> foreignKeysMap;
    private final ClassTableMapper classTableMapper;
    private final FieldManager fieldManager;

    public TableManager(ClassManager classManager, List<Table> tables, Map<String, List<ForeignKey>> foreignKeysMap, ClassTableMapper classTableMapper, FieldManager fieldManager) {
        this.classManager = classManager;
        this.tables = tables;
        this.foreignKeysMap = foreignKeysMap;
        this.classTableMapper = classTableMapper;
        this.fieldManager = fieldManager;
    }

    public void fill() throws ClassNotFoundException {
        List<Class> classes = classManager.getAllClasses("com/domain");
        List<FieldMapper> list = asList(new PrimaryFieldMapper(classTableMapper), new PrimaryKeyMapper(), new ForignKeyMapper(foreignKeysMap));

        for (Class c : classes) {
            List<com.domain.helper.Field> primaryKeys = new ArrayList<>();
            for (Field field : c.getDeclaredFields()) {
                for(FieldMapper fieldMapper : list) {
                    fieldMapper.map(field, new Table(null,null,null,null));
                }
                if (fieldManager.isPrimaryDataType(field)) {
                    if (fieldManager.isPrimaryKey(field)) {

                    }
                }
                else {

                }
            }
            PrimaryKey primaryKey = new PrimaryKey(primaryKeys);
        }
    }

    public void fillTables() throws ClassNotFoundException, NoSuchFieldException {
        List<Class> classes = classManager.getAllClasses("com/domain");
        fillTableNames(classes); // to be removed
        for (Class c : classes) {
            String tableName = classTableMapper.getTableName(c.getSimpleName()); // pass class instead of class name


            List<com.domain.helper.Field> primaryFields = getPrimaryFields(c, tableName, true);
            PrimaryKey primaryKey = new PrimaryKey(getPrimaryKeys(c));
            primaryFields.addAll(getNonPrimaryInlineFields(c, classTableMapper.getTableName(c.getSimpleName())));
            List<Class> nonPrimaryExternalFields = getNonPrimaryExternalFields(c);
            for (Class clazz : nonPrimaryExternalFields) {
                addForeignKeys(ClassTableMapper.getInstance().getTableName(clazz.getSimpleName()), getForeignKey(primaryKey, tableName));
                //foreignKeysMap.put(ClassTableMapper.getInstance().getTableName(clazz.getSimpleName()), getForeignKey(primaryKeys, tableName))
            }
            tables.add(new Table(tableName, primaryFields, primaryKey, new ArrayList<>()));
        }
        fillForeignKeys();
    }

    private void fillForeignKeys() {
        for (Table table : tables) {
            for (ForeignKey foreignKey : foreignKeysMap.get(table.getTableName())) {
                table.addForeignKey(foreignKey);
            }
        }
    }

    private void fillTableNames(List<Class> classes) {
        for (Class c : classes) {
            String tableName = classTableMapper.getTableName(c.getSimpleName());
            foreignKeysMap.put(tableName, new ArrayList<>());
        }
    }

    private ForeignKey getForeignKey(PrimaryKey primaryKey, String tableName) {
        return new ForeignKey(primaryKey, tableName);
    }




    private List<Class> getNonPrimaryExternalFields(Class clazz) {
        List<Class> nonPrimaryFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Utility.isPrimary(field.getType().getSimpleName())) {
                CustomAnnotations customAnnotations = field.getAnnotation(CustomAnnotations.class);
                if (customAnnotations != null) {
                    if (customAnnotations.dbMapping() == DBMapping.EXTERNAL) {
                        nonPrimaryFields.add(field.getType());
                    }
                }
            }
        }
        return nonPrimaryFields;
    }

    private List<com.domain.helper.Field> getPrimaryKeys(Class clazz) {
        List<com.domain.helper.Field> primaryKeys = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(CustomAnnotations.class) != null && field.getAnnotation(CustomAnnotations.class).primaryKey()) {
                boolean isNullable = field.getAnnotation(CustomAnnotations.class).isNullable();
                com.domain.helper.Field primaryKey;
                if (field.getType().getSimpleName().toLowerCase().contains("string")) {
                    int size = field.getAnnotation(CustomAnnotations.class).alternateSize();
                    primaryKey = new com.domain.helper.Field(field.getName(), Utility.getDBTypeName(field.getName()), field.getType().getSimpleName(), Utility.getDataTypeNames(field.getType().getSimpleName()), isNullable, size);
                }
                else {
                    primaryKey = new com.domain.helper.Field(field.getName(), Utility.getDBTypeName(field.getName()), field.getType().getSimpleName(), Utility.getDataTypeNames(field.getType().getSimpleName()), isNullable);
                }
                    primaryKeys.add(primaryKey);
            }
        }
        return primaryKeys;
    }

    private void addForeignKeys(String tableName, ForeignKey foreignKey) {
        foreignKeysMap.get(tableName).add(foreignKey);
    }
}
