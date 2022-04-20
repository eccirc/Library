package com.postcourse;

import java.util.List;
import java.util.Optional;

public interface Borrower {
    void borrowItem(LibraryItem item);

    void returnItem(int itemId);

    Optional<List<LibraryItem>> showAllBorrowed();

    Optional<LibraryItem> showSingleItem(int itemId);
}
