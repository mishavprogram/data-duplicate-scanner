package com.mykhailo.dataduplicatescanner.model;

public class DuplicateRecord {

    private final String value;
    private final int count;

    public DuplicateRecord(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }
}