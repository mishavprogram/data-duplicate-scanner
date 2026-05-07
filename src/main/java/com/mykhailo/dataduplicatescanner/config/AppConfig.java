package com.mykhailo.dataduplicatescanner.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream inputStream =
                    new FileInputStream("config/application-local.properties");

            properties.load(inputStream);

        } catch (IOException exception) {
            throw new RuntimeException("Failed to load local config", exception);
        }
    }

    public static String getTargetTable() {
        return properties.getProperty("app.table.target");
    }

    public static String getTargetColumn() {
        return properties.getProperty("app.column.target");
    }

    public static String getLinkTable() {
        return properties.getProperty("app.table.link");
    }

    public static String getLinkTargetIdColumn() {
        return properties.getProperty("app.column.link-target-id");
    }

    public static String getMainIdColumn() {
        return properties.getProperty("app.column.main-id");
    }

    public static String getHost() {
        return properties.getProperty("app.db.host");
    }

    public static String getPort() {
        return properties.getProperty("app.db.port");
    }

    public static String getUser() {
        return properties.getProperty("app.db.user");
    }

    public static String getPassword() {
        return properties.getProperty("app.db.password");
    }

    public static String getDatabasePrefix() {
        return properties.getProperty("app.db.name-prefix");
    }
}