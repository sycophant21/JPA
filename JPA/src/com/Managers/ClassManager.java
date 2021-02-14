package com.Managers;

import com.domain.helper.CustomAnnotations;
import com.domain.helper.Entity;
import com.domain.helper.TableNameAnnotation;
import com.utils.Utility;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassManager {
    private final FilesManager filesManager;

    public ClassManager(FilesManager filesManager) {
        this.filesManager = filesManager;
    }

    private List<Class> getClassesFromFilePath(String path) throws ClassNotFoundException {
        List<File> files = filesManager.getFiles(path);
        List<Class> classes = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory()) {
                classes.add(getClass(filesManager.getFileName(f, path)));
            }
        }
        return classes;
    }

    public Map<String, List<Entity>> getEntitiesToCheck(String path) throws ClassNotFoundException {
        Map<String, List<Entity>> entities = new HashMap<>();
        List<Class> classes = getClassesFromFilePath(path);
        for (Class c : classes) {
            entities.put(ClassTableMapper.getInstance().getTableName(c.getSimpleName()), getFieldsForClass(c));
        }
        return entities;
    }

    private List<Entity> getFieldsForClass(Class clazz) {
        List<Entity> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            String fieldDataType = f.getType().getSimpleName();
            if (fieldDataType.equalsIgnoreCase("String")) {
                fieldDataType = "varchar";
            }
            CustomAnnotations customAnnotations = f.getDeclaredAnnotation(CustomAnnotations.class);
            String isPrimary = "";
            String isNull = "YES";
            if (customAnnotations == null) {
                fieldNames.add(new Entity(ClassTableMapper.getInstance().getTableName(clazz.getSimpleName()), Utility.getDBTypeName(f.getName()), fieldDataType, isPrimary, isNull));
            } else {
                if (customAnnotations.primaryKey()) {
                    isPrimary = "PRI";
                }
                if (!customAnnotations.isNullable()) {
                    isNull = "NO";
                }
                if (customAnnotations.alternateFieldName().equalsIgnoreCase("")) {
                    fieldNames.add(new Entity(ClassTableMapper.getInstance().getTableName(clazz.getSimpleName()),  Utility.getDBTypeName(f.getName()), fieldDataType, isPrimary, isNull));
                }
                else {
                    fieldNames.add(new Entity(ClassTableMapper.getInstance().getTableName(clazz.getSimpleName()), customAnnotations.alternateFieldName(), fieldDataType, isPrimary, isNull));
                }
            }
        }
        return fieldNames;
    }

    public Class getClass(String className) throws ClassNotFoundException {
        if (className.contains(".")) {
            if (className.contains(".class")) {
                return Class.forName(filesManager.getFilePath(className.substring(0,className.indexOf(".class"))));
            }
            return Class.forName(className);
        }
        else {
            return Class.forName(filesManager.getFilePath(className));
        }
    }

    public Field getFieldFromClass(Class clazz, String fieldName) {

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equalsIgnoreCase(fieldName) || Utility.getDBTypeName(f.getName()).equalsIgnoreCase(fieldName)) {
                return f;
            }
            else {
                if (f.getAnnotation(CustomAnnotations.class) != null) {
                    if (f.getAnnotation(CustomAnnotations.class).alternateFieldName().equalsIgnoreCase(fieldName)) {
                        return f;
                    }
                }
            }
        }
        return null;
    }

    public List<Class> getAllClasses(String path) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        for (File file : filesManager.getFiles(path)) {
            if (!file.isDirectory()) {
                classes.add(getClass(file.getName()));
            }
        }
        return classes;
    }

}
