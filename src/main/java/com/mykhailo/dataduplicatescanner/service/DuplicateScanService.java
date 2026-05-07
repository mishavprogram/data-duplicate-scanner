package com.mykhailo.dataduplicatescanner.service;

import com.mykhailo.dataduplicatescanner.config.AppConfig;
import com.mykhailo.dataduplicatescanner.model.DuplicateRecord;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DuplicateScanService {

    public List<DuplicateRecord> scanDatabase(String databaseName) {
        List<DuplicateRecord> records = new ArrayList<>();

        String url = "jdbc:mysql://" + AppConfig.getHost() + ":" + AppConfig.getPort() + "/" + databaseName;

        String sql = """
                SELECT
                    main.%s AS email,
                    COUNT(DISTINCT main.%s) AS ids_count,
                    COUNT(DISTINCT link.%s) AS used_ids_count,
                    COUNT(link.%s) AS linked_rows_count
                FROM %s main
                LEFT JOIN %s link
                    ON link.%s = main.%s
                GROUP BY main.%s
                HAVING COUNT(DISTINCT main.%s) > 1
                ORDER BY linked_rows_count DESC, ids_count DESC
                """.formatted(
                AppConfig.getTargetColumn(),
                AppConfig.getMainIdColumn(),
                AppConfig.getLinkTargetIdColumn(),
                AppConfig.getLinkTargetIdColumn(),
                AppConfig.getTargetTable(),
                AppConfig.getLinkTable(),
                AppConfig.getLinkTargetIdColumn(),
                AppConfig.getMainIdColumn(),
                AppConfig.getTargetColumn(),
                AppConfig.getMainIdColumn()
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
                        resultSet.getInt("ids_count"),
                        resultSet.getInt("used_ids_count"),
                        resultSet.getInt("linked_rows_count")
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