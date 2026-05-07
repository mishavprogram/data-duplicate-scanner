package com.mykhailo.dataduplicatescanner.model;

public class DuplicateRecord {

    private final String email;
    private final int idsCount;
    private final int usedIdsCount;
    private final int linkedRowsCount;

    public DuplicateRecord(
            String email,
            int idsCount,
            int usedIdsCount,
            int linkedRowsCount
    ) {
        this.email = email;
        this.idsCount = idsCount;
        this.usedIdsCount = usedIdsCount;
        this.linkedRowsCount = linkedRowsCount;
    }

    public String getEmail() {
        return email;
    }

    public int getIdsCount() {
        return idsCount;
    }

    public int getUsedIdsCount() {
        return usedIdsCount;
    }

    public int getLinkedRowsCount() {
        return linkedRowsCount;
    }
}