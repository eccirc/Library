package com.postcourse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LibraryCLI {
    private final Scanner guide = new Scanner(System.in);
    private final Library myLibrary = new Library();

    private int choice;

    private final int[] options = new  int[]{1,2,3};

    public LibraryCLI() throws IOException {
    }

    public void runInteraction(){
        System.out.println("Welcome to David's Library!" + "\n");
        System.out.println("Please select from the following options: \n" +
                "1: Login as Admin \n" +
                "2: Login as Existing User \n" +
                "3: Join as New User"
        );

        choiceInteraction();

       switch (choice){
           case 1:
               adminInteraction();
               break;
           case 2:
               userInteraction();
               break;
           case 3:
               newUserInteraction();
               break;
           default:
               choiceInteraction();
       }
    }
    public void choiceInteraction(){
        System.out.println("Enter choice: ");
        choice = guide.nextInt();
        if(Arrays.stream(options).noneMatch(num -> num == choice))
        {
            System.out.println("Invalid option, please enter 1 2 or 3");
            choiceInteraction();

        }
    }
    private void adminInteraction(){
        System.out.println("\n Hello " + myLibrary.getAdmin().getName() + "!\n");
        System.out.println("What would you like to do?");
        System.out.println("1: Show all current Library patrons \n" +
                "2: Show all current Library items \n" +
                "3: Print a report "
                );
       choiceInteraction();

        switch (choice){
            case 1:
                System.out.println(myLibrary.getPatrons());
                break;
            case 2:
                System.out.println(myLibrary.getItemsByType(ItemType.BOOK));
                break;
            case 3:
                myLibrary.printReport(ItemType.BOOK);
            default:
                choiceInteraction();
        }
        System.out.println("Type 'r' to run through the options again or any other key to exit");
        String endingInteraction = guide.next();
        if ("r".equals(endingInteraction)) {
            adminInteraction();
        } else {
            System.out.println("Leaving the library");
        }

    }
    private void userInteraction(){
        System.out.println("Hello! Welcome back to the library. \n To login, type your name matching one from the list below: \n");
        myLibrary.getPatrons().forEach(patron -> System.out.println(patron.getName() + "\n"));
        guide.nextLine();
        String name = guide.nextLine();
        System.out.println(name);

        myLibrary.getPatronByName(name).ifPresent( patron -> {
            myLibrary.setCurrentPatron(myLibrary.getPatronByName(name).get());
                    Patron foundPatron = myLibrary.getCurrentPatron();
                    System.out.println("Hi, " + foundPatron.getName());
            System.out.println("What would you like to do? \n" +
                    "1: View all and borrow a book \n" +
                    "2: View all of your books currently on loan \n" +
                    "3: Return an item to the library "
                    );
            choiceInteraction();
            switch (choice){
                case 1:
                    myLibrary.getItemsByType(ItemType.BOOK).forEach(item -> System.out.println(item.getId() + " " + item.getItemTitle()));
                    break;
                case 2:
                    System.out.println(foundPatron.showAllBorrowed());
                    break;
                case 3:
                    if(foundPatron.showAllBorrowed().isEmpty()){
                        System.out.println("No books currently on loan, going back to menu.");
                        choiceInteraction();
                        break;
                    }
                    String bookID;
                   foundPatron.showAllBorrowed().ifPresent(item -> item.forEach(i -> System.out.println(i.getId() + " " + i.getItemTitle())));

                   break;
                default:
                    choiceInteraction();

            }
        }
        );

    }
    private void newUserInteraction(){
        System.out.println("Hello new user, welcome to the library!");

    }
}
