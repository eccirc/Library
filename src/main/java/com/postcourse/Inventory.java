package com.postcourse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Inventory {
    private final static String fileName = "books_data.csv";
    private final static String booksJsonRef = "books.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

    static {
        try {
            List<Book> booksRaw = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(Book.class)
                    .build()
                    .parse();
            List<LibraryItem> booksAsItems = booksRaw.stream().map(Book -> new LibraryItem(Book, ItemType.BOOK, ItemStatus.AVAILABLE,
                    Book.getId(), Book.getTitle())).collect(Collectors.toList());
            File fileRef = new File(booksJsonRef);
            if(!fileRef.exists() || fileRef.length() == 0) {
                writer.writeValue(Paths.get(booksJsonRef).toFile(), booksAsItems);
                System.out.println("Details written to file");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<LibraryItem> getBooksFromJSON() throws IOException {
        String filePath = Files.readString(Path.of(booksJsonRef));
        return mapper.readValue(filePath, new TypeReference<>(){});
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static ObjectWriter getWriter() {
        return writer;
    }
}
