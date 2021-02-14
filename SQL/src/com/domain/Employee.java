package com.domain;

import com.domain.helper.CustomAnnotations;
import com.domain.helper.DBMapping;

import java.lang.reflect.Field;

public class Employee {
    @CustomAnnotations(alternateSize = 127)
    private final String employeeName;
    @CustomAnnotations(primaryKey = true)
    private final int employeeId;
    @CustomAnnotations(dbMapping = DBMapping.EXTERNAL)
    private final Student student;

    public Employee(String employeeName, int employeeId, Student student) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getEmployeeId() {
        return employeeId;
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
