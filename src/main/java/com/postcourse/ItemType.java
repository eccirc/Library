package com.postcourse;

public enum ItemType{
    BOOK(Book.class, "books.json"), DVD(null, ""), CD(null, ""), JOURNAL(null, ""
    );

    private final Object obj;
    private final String fileName;
    ItemType(Object o, String fileName) {
        this.obj = o;
        this.fileName = fileName;
    }

    public Object getObj() {
        return obj;
    }

    public String getFileName() {
        return fileName;
    }
}
