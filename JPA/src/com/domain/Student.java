package com.domain;

import com.domain.helper.CustomAnnotations;
import com.domain.helper.TableNameAnnotation;

import java.lang.reflect.Field;

@TableNameAnnotation(tableName = "Student")
public class Student {
    @CustomAnnotations(alternateSize = 127)
    private final String studentName;
    @CustomAnnotations(primaryKey = true)
    private final int studentId;
    @CustomAnnotations(alternateFieldName = "STUDENT_AGE")
    private final int age;
    private final int test;

    public Student(String studentName, int studentID, int age, int test) {
        this.studentName = studentName;
        this.studentId = studentID;
        this.age = age;
        this.test = test;
    }

    public int getAge() {
        return age;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getStudentID() {
        return studentId;
    }

    public int getTest() {
        return test;
    }

    public String getPrimaryKey() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getAnnotation(CustomAnnotations.class) != null && field.getAnnotation(CustomAnnotations.class).primaryKey()) {
                return field.getName();
            }
        }
        return "";
    }
}
