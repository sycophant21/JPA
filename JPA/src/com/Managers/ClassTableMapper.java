package com.Managers;

import com.domain.helper.TableNameAnnotation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassTableMapper {
    private static ClassTableMapper classTableMapper;
    private final FilesManager filesManager;
    private final ClassManager classManager;
    private final Map<String, String> classNames;
    private final Map<String, String> tableNames;

    private ClassTableMapper(FilesManager filesManager, ClassManager classManager, Map<String, String> classNames, Map<String, String> tableNames) {
        this.filesManager = filesManager;
        this.classManager = classManager;
        this.classNames = classNames;
        this.tableNames = tableNames;
        fillMaps();
    }

    public static ClassTableMapper getInstance() {
        if (classTableMapper == null) {
            classTableMapper = new ClassTableMapper(new FilesManager(),new ClassManager(new FilesManager()) , new HashMap<>(), new HashMap<>());
        }
        return classTableMapper;
    }

    private void fillMaps() {
        List<File> files = filesManager.getFiles("com.domain");
        List<Class> classes = files.stream().filter(k->!k.isDirectory()).map(k-> {
            try {
                return classManager.getClass(k.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        classNames.putAll(classes.stream().collect(Collectors.toMap(k->getClassDBName(k).toUpperCase(), Class::getSimpleName)));
        tableNames.putAll(classes.stream().collect(Collectors.toMap(k->k.getSimpleName().toUpperCase(), this::getClassDBName)));
    }

    public String getTableName(String className) {
        return tableNames.get(className.toUpperCase());
    }
    public String getClassName(String tableName) {
        return classNames.get(tableName.toUpperCase());
    }

    private String getClassDBName(Class clazz) {
        TableNameAnnotation tableNameAnnotation = (TableNameAnnotation) clazz.getDeclaredAnnotation(TableNameAnnotation.class);
        if (tableNameAnnotation == null || tableNameAnnotation.tableName().equals("")) {
            return clazz.getSimpleName();
        } else {
            return tableNameAnnotation.tableName();
        }
    }

}
