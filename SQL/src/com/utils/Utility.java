package com.utils;

import com.domain.helper.CustomAnnotations;

import java.lang.reflect.Field;

public class Utility {
    public static String getDBTypeName(String name) {
        String camelCasing = "";
        for (int i = 0; i < name.length(); i++) {
            char charAtI = name.charAt(i);
            if (charAtI >= 65 && charAtI <= 90) {
                camelCasing = camelCasing.concat("_");
            }
            camelCasing = camelCasing.concat(String.valueOf(charAtI));
        }
        return camelCasing.toUpperCase();
    }

    public static String getDataTypeNames(String dataType) {
        String returnString = "";
        if (dataType.equalsIgnoreCase("int")) {
            returnString = "INTEGER";
        } else if (dataType.equalsIgnoreCase("char")) {
            returnString = "CHARACTER";
        } else if (dataType.equalsIgnoreCase("boolean")) {
            returnString = "BOOLEAN";
        } else if (dataType.equalsIgnoreCase("string")) {
            returnString = "VARCHAR";
        }
        return returnString;
    }

    public static boolean isPrimary(String dataType) {
        String lowerCaseString = dataType.toLowerCase();
        return lowerCaseString.contains("bool") || lowerCaseString.contains("int") || lowerCaseString.contains("char") || lowerCaseString.contains("varchar") || lowerCaseString.contains("string");
    }

    public static int getVarcharSize(Field field) throws NoSuchFieldException {
        if (field.getType().getSimpleName().equalsIgnoreCase("String")) {
            if (field.getDeclaredAnnotation(CustomAnnotations.class) != null) {
                return field.getDeclaredAnnotation(CustomAnnotations.class).alternateSize();
            } else {
                return 255;
            }
        }
        else {
            return -1;
        }
    }

    public static boolean isPrimaryKey(Field field) {
        return field.getAnnotation(CustomAnnotations.class) != null && field.getAnnotation(CustomAnnotations.class).primaryKey();
    }

    public static boolean isNullable(Field field) {
        return field.getAnnotation(CustomAnnotations.class) != null && field.getAnnotation(CustomAnnotations.class).isNullable();
    }
}
