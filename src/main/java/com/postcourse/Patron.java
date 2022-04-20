package com.postcourse;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.*;

public class Patron extends User implements Borrower {

    @JsonAlias("borrowedBooks")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<LibraryItem> borrowedItems = new ArrayList<>();
    @JsonAlias("uid")
    private UUID userID;

    public Patron(String name) {
        super(name);
        this.userID = UUID.randomUUID();
        super.setType(UserType.PATRON);
    }

    public Patron() {
    }

    public UUID getUserID() {
        return userID;
    }

    @Override
    public void borrowItem(LibraryItem item) {
        this.borrowedItems.add(item);
        item.setStatus(ItemStatus.ON_LOAN);
    }
    @Override
    public void returnItem(int itemId) {
      Optional<LibraryItem> itemToReturn = showSingleItem(itemId);
      if(itemToReturn.isPresent()){
          System.out.println("Returning item to Library");
          borrowedItems.remove(itemToReturn.get());
      }
      else {
          System.out.println("Incorrect item ID or no items currently on loan");
      }
    }
    @Override
    public Optional<List<LibraryItem>> showAllBorrowed() {
        if(borrowedItems.size() > 0){
            return Optional.of(borrowedItems);
        }
        else return Optional.empty();
    }

    @Override
    public Optional<LibraryItem> showSingleItem(int itemId) {
        if(showAllBorrowed().isPresent()) {
            return showAllBorrowed().get().stream().filter(item->  Integer.parseInt(item.getId()) == itemId).findFirst();
        }
        else{
            System.out.println("No books currently on loan");
            return Optional.empty();
        }
    }


    @Override
    public String toString() {
        return "Patron{" +
                "name=" + super.getName() +
                ", borrowedItems=" + borrowedItems +
                ", userID=" + userID +
                '}';
    }


}
