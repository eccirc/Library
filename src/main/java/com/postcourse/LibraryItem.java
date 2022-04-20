package com.postcourse;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LibraryItem {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonAlias(value = "details")
    private  Object details;
    private  ItemType type;
    private int nTimesBorrowed = 0;
    private ItemStatus status;
    private String id;

    private String itemTitle;


    public LibraryItem(Object object, ItemType type, ItemStatus status, String id, String title) {
        this.details = object;
        this.type = type;
        this.status = status;
        this.id = id;
        this.itemTitle = title;
    }

    public LibraryItem() {
    }

    public int getnTimesBorrowed() {
        return nTimesBorrowed;
    }

    public void setnTimesBorrowed(int nTimesBorrowed) {
        this.nTimesBorrowed = nTimesBorrowed;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public Object getDetails() {
        return details;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "LibraryItem{" +
                "item=" + details +
                "title=" + itemTitle +
                ", type=" + type +
                ", nTimesBorrowed=" + nTimesBorrowed +
                ", status=" + status +
                '}';
    }
}
