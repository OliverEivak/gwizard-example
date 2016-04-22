package com.example.app.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtils {

    public static Set<Class<?>> getClasses(String packagePrefix, Class superClass) {
        Set<Class<?>> result = new HashSet<>();

        try {
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses(packagePrefix);
            for (ClassPath.ClassInfo classInfo : classes) {
                Class clazz = classInfo.load();
                if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                    result.add(clazz);
                }
            }
        } catch (IOException e) {
            log.error("Error getting classes", e);
        }

        return result;
    }

}
