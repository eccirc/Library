package com.postcourse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVWriter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


//
//        A functioning Library System: You will build a console application that allows the user to
//        see what books (data provided) the library currently has available. The data will be
//        provided in CSV format, parse this into JSON format and write the data to a JSON file.
//        This should be done using Java and not done manually. The idea is to add persistence
//        to your application by reading/writing to a file.
//
//        Loan System: A user should be able to loan a book and also see what books they
//        currently have loaned. A user should not be able to loan a book that is already out on
//        loan. Write to a new JSON file to make the users persistent.2
//        3. Reports: A library admin should be able to run a report that shows all the books
//        currently out on loan and also the amount of times a particular books have been
//        loaned out. Ext. Can you output the report in CSV format?

public class Library {

    private final Map<ItemType,List<LibraryItem>> items = Map.of(ItemType.BOOK, Inventory.getBooksFromJSON());
    private List<Patron> patrons = new ArrayList<>();
    private final Admin admin = new Admin("Library Administrator");
    private Patron currentPatron;

    private final String patronsFile = "patrons.json";

    public Library() throws IOException {
       try {
           patrons = readPatronsFromJsonFile("patrons.json");

       } catch (IOException e){
           System.out.println("Error reading from our records, " + e);
       }
    }
    public List<Patron> readPatronsFromJsonFile(String fileName) throws IOException {
            String pathString = Files.readString(Path.of(fileName));
            return Inventory.getMapper().readValue(pathString, new TypeReference<>() {
            });
    }

    public void writeToJsonFile(String fileName, List items) {
        try {
            Inventory.getWriter().writeValue(Paths.get(fileName).toFile(), items);
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public List<LibraryItem> getItemsByType(ItemType type) {
        return items.get(type);
    }

    public LibraryItem getSingleItem(ItemType type, int index){
        return this.getItemsByType(type).get(index);
    }

    public List<Patron> getPatrons() {
        return patrons;
    }

    public Optional<Patron> getPatronByName(String name){
        if(patrons.stream().anyMatch(Patron -> Objects.equals(Patron.getName(), name))) {
            return Optional.of(patrons.stream().filter(Patron -> Objects.equals(Patron.getName(), name)).findFirst().get());
        }
        else {
            System.out.println("No library member matches that name");
            return Optional.empty();
        }
    }

    public Admin getAdmin() {
        return admin;
    }

    public void joinLibrary(String fullName){
        Patron newPatron = new Patron(fullName);
        patrons.add(newPatron);
        writeToJsonFile(patronsFile, getPatrons());
    }

    public void setCurrentPatron(Patron currentPatron) {
        this.currentPatron = currentPatron;
    }

    public Patron getCurrentPatron(){
        return currentPatron;
    }

    public void handleLendItem(String patronTo, ItemType type, int itemID){
        Optional<Patron> patron = getPatronByName(patronTo);
        LibraryItem item = getSingleItem(type, itemID - 1);
        if(item.getStatus().isBorrowable()) {
            item.setnTimesBorrowed(item.getnTimesBorrowed() + 1);
            patron.ifPresent(foundPatron -> foundPatron.borrowItem(item));
            writeToJsonFile(type.getFileName(), getItemsByType(type));
            writeToJsonFile(patronsFile, getPatrons());
            patron.ifPresent(value -> System.out.println("A new book has been loaned to " + value.getName()) );
        }
        else {
            System.out.println("Sorry, that item is already on loan!");
        }
    }
    public void handleReturnItem(String patronFrom, ItemType type, int itemID){
        Optional<Patron> patron = getPatronByName(patronFrom);
        LibraryItem item = getSingleItem(type,  itemID - 1);
        patron.ifPresent(foundPatron -> {
            foundPatron.returnItem(itemID);
            item.setStatus(ItemStatus.AVAILABLE);
            writeToJsonFile("books.json", getItemsByType(type));
            writeToJsonFile("patrons.json", getPatrons());
        });

    }


    public void printReport(ItemType type) {
        String fileName = "AdminReport_" + type + ".csv";
        try(
                Writer writer = Files.newBufferedWriter(Paths.get(fileName));
                CSVWriter csvWriter = new CSVWriter(writer, ';', CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
                ) {
            String[] headers = {"Item_id", "Item_title", "Item_status", "Times_borrowed"};
            csvWriter.writeNext(headers);
            getItemsByType(type).forEach(item -> {
                String[] details = new String[]{item.getId(), item.getItemTitle(), item.getStatus().toString(), String.valueOf(item.getnTimesBorrowed())};
                csvWriter.writeNext(details);

            });
            System.out.println("Report printed. Results can be found at " + fileName);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
