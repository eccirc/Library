package com.postcourse;

import com.opencsv.bean.CsvBindByName;

public class Book {

    @CsvBindByName(column = "Number")
    private String id;
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Author")
    private String Author;
    @CsvBindByName(column = "Genre")
    private String genre;
    @CsvBindByName(column = "SubGenre")
    private String subGenre;
    @CsvBindByName(column = "Publisher")
    private String publisher;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getGenre() {
        return genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", Author='" + Author + '\'' +
                ", genre='" + genre + '\'' +
                ", subGenre='" + subGenre + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
