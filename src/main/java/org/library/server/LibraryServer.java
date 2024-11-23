package org.library.server;

import org.library.common.LibraryService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class LibraryServer implements LibraryService {
    private Map<String, String> books; // bookId -> book title
    private Map<String, String> borrowedBooks; // userId -> borrowed bookId

    public LibraryServer() {
        books = new HashMap<>();
        borrowedBooks = new HashMap<>();

        // Adding some books
        books.put("1", "Book1 by Author1");
        books.put("2", "Book2 by Author2");
    }

    @Override
    public String searchBook(String title) {
        return books.values().stream()
                .filter(book -> book.contains(title))
                .findFirst()
                .orElse("Book not found");
    }

    @Override
    public boolean borrowBook(String bookId, String userId) {
        if (books.containsKey(bookId) && !borrowedBooks.containsKey(userId)) {
            borrowedBooks.put(userId, bookId);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnBook(String bookId, String userId) {
        if (borrowedBooks.containsKey(userId) && borrowedBooks.get(userId).equals(bookId)) {
            borrowedBooks.remove(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addBook(String title, String author) {
        String bookId = String.valueOf(books.size() + 1); // simple bookId generation
        books.put(bookId, title + " by " + author);
        return true;
    }

    @Override
    public List<String> listBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<String> listBorrowedBooks(String userId) {
        List<String> borrowedList = new ArrayList<>();
        for (Map.Entry<String, String> entry : borrowedBooks.entrySet()) {
            if (entry.getKey().equals(userId)) {
                borrowedList.add(books.get(entry.getValue()));
            }
        }
        return borrowedList;
    }

    public static void main(String[] args) {
        try {
            LibraryServer server = new LibraryServer();
            LibraryService stub = (LibraryService) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LibraryService", stub);

            System.out.println("LibraryServer is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
