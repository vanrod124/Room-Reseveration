package com.room.reservation.program.enums;

public enum EmploymentStatus {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    EmploymentStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return this.compareTo(EmploymentStatus.ACTIVE) == 0;
    }

    public boolean isInactive() {
        return this.compareTo(EmploymentStatus.INACTIVE) == 0;
    }

    public String getStatus() {
        return status;
    }

    private String status;
}
