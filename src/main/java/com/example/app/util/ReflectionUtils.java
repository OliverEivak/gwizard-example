package com.example.app.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.example.app.resource.BaseResource;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.util.concurrent.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtils {

    public static Set<Class<?>> getResources() {
        return getClasses("com.example.app.resource", BaseResource.class);
    }

    public static Set<Class<?>> getServices() {
        return getClasses("com.example.app.services", Service.class);
    }

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

        }

        return result;
    }

}
