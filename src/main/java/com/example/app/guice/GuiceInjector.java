package com.example.app.guice;

import com.google.inject.Injector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuiceInjector {

    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    public static void setInjector(Injector inj) {
        injector = inj;
    }

}
