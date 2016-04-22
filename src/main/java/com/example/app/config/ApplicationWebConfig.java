package com.example.app.config;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.gwizard.web.WebConfig;

import lombok.Data;

@Data
public class ApplicationWebConfig extends WebConfig {

    @NotNull
    private Map<String, String> initParameters = new HashMap<>();

}
