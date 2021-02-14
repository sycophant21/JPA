package com.Managers;

import com.domain.helper.TableNameAnnotation;

import java.io.File;
import java.net.URL;
import java.util.*;

public class FilesManager {

    public List<File> getFiles(String path) {
        List<File> list = new ArrayList<>();
        try {
            String filePath = path.replaceAll("\\.", "/");
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(filePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                File dir = new File(url.getFile());
                if (dir.listFiles() != null) {
                    Collections.addAll(list, Objects.requireNonNull(dir.listFiles()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public String getFileName(File file, String path) {
        String fileName = file.getName();
        String className = fileName.substring(0, fileName.indexOf("."));
        return path + "." + className;
    }

    public String getFilePath(String fileName) {
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("com/domain/");
            if (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                File dir = new File(url.getFile());
                for (File file : Objects.requireNonNull(dir.listFiles())) {
                    if (!file.isDirectory()) {
                        String className = "com.domain."+file.getName().substring(0,file.getName().indexOf("."));
                        if (file.getName().equalsIgnoreCase(fileName + ".class")) {
                            return "com.domain." + fileName;
                        }
                        else {
                            if (Class.forName(className).getDeclaredAnnotation(TableNameAnnotation.class) != null) {
                                if (Class.forName(className).getDeclaredAnnotation(TableNameAnnotation.class).tableName().equalsIgnoreCase(fileName)) {
                                    return "com.domain." + Class.forName(file.getName()).getDeclaredAnnotation(TableNameAnnotation.class).tableName();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
