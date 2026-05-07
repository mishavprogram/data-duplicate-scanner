package com.mykhailo.dataduplicatescanner.model;

public class DatabaseScanSummary {

    private final String databaseName;
    private final int duplicatedValuesCount;

    public DatabaseScanSummary(String databaseName, int duplicatedValuesCount) {
        this.databaseName = databaseName;
        this.duplicatedValuesCount = duplicatedValuesCount;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public int getDuplicatedValuesCount() {
        return duplicatedValuesCount;
    }
}