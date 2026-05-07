package com.mykhailo.dataduplicatescanner.service;

import com.mykhailo.dataduplicatescanner.config.AppConfig;
import com.mykhailo.dataduplicatescanner.model.DuplicateRecord;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class DuplicateScanService {

    public List<DuplicateRecord> scanDatabase(String databaseName) {
        List<DuplicateRecord> records = new ArrayList<>();

        String url = "jdbc:mysql://" + AppConfig.getHost() + ":" + AppConfig.getPort() + "/" + databaseName;

        String sql = """
        SELECT %s, COUNT(*) AS duplicate_count
        FROM %s
        GROUP BY %s
        HAVING COUNT(*) > 1
        ORDER BY duplicate_count DESC
        """.formatted(
                AppConfig.getTargetColumn(),
                AppConfig.getTargetTable(),
                AppConfig.getTargetColumn()
        );

        try (
                Connection connection = DriverManager.getConnection(
                        url,
                        AppConfig.getUser(),
                        AppConfig.getPassword()
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                records.add(new DuplicateRecord(
                        resultSet.getString("email"),
                        resultSet.getInt("duplicate_count")
                ));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to scan database: " + databaseName, exception);
        }

        return records;
    }

    /*
    Some databases do not contain the target table.
    For example:
    - admin databases
    - technical databases
    - special service schemas

    We do not want the whole scan to crash because of one incompatible database.

    Return:
    - duplicate count if scan succeeds
    - -1 if database cannot be scanned
    */
    public int countDuplicatedValues(String databaseName) {
        try {
            return scanDatabase(databaseName).size();
        } catch (Exception exception) {
            return -1;
        }
    }
}