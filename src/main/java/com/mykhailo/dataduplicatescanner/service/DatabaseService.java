package com.mykhailo.dataduplicatescanner.service;

import com.mykhailo.dataduplicatescanner.config.AppConfig;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    public List<String> findDatabases() {
        List<String> databases = new ArrayList<>();

        String url = "jdbc:mysql://" + AppConfig.getHost() + ":" + AppConfig.getPort();

        try (
                Connection connection = DriverManager.getConnection(
                        url,
                        AppConfig.getUser(),
                        AppConfig.getPassword()
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SHOW DATABASES")
        ) {
            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);

                if (databaseName.startsWith(AppConfig.getDatabasePrefix())) {
                    databases.add(databaseName);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load database list", exception);
        }

        return databases;
    }
}