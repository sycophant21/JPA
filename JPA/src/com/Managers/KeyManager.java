package com.Managers;

import com.domain.helper.CustomAnnotations;

import java.lang.reflect.Field;

public class KeyManager {
    private final ClassManager classManager;
    private static KeyManager keyManager;
    private KeyManager(ClassManager classManager) {
        this.classManager = classManager;
    }

    public static KeyManager getInstance() {
        if (keyManager == null) {
            keyManager = new KeyManager(new ClassManager(new FilesManager()));
        }
        return keyManager;
    }
    public Field getForeignKey(String className) throws ClassNotFoundException {
        Class clazz = classManager.getClass(className);
        for (Field field : clazz.getDeclaredFields()) {
            CustomAnnotations customAnnotations = field.getAnnotation(CustomAnnotations.class);
            if (customAnnotations != null && customAnnotations.primaryKey()) {
                return field;
            }
        }
        return null;
    }
}
