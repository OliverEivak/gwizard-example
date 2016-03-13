package com.example.app.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.example.app.resource.BaseResource;

public class ReflectionUtils {

    public static Set<Class<?>> getResourceClasses() {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.example.app.resource"))));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
            Class resource = iterator.next();
            if (!resource.getSuperclass().equals(BaseResource.class)) {
                iterator.remove();
            }
        }

        return classes;
    }

}
