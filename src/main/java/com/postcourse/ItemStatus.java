package com.postcourse;

public enum ItemStatus {
    AVAILABLE(true), ON_LOAN(false);

    private final boolean isBorrowable;

    ItemStatus(boolean b) {
        this.isBorrowable = b;
    }

    public boolean isBorrowable() {
        return isBorrowable;
    }
}
