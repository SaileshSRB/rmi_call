package org.library.server;

import org.library.common.LibraryService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class LibraryServer implements LibraryService {
    private List<String> books;

    public LibraryServer() {
        books = new ArrayList<>();
        books.add("Book1 by Author1");
        books.add("Book2 by Author2");
    }

    @Override
    public String searchBook(String title) {
        return books.stream().filter(book -> book.contains(title)).findFirst().orElse("Book not found");
    }

    @Override
    public boolean borrowBook(String bookId, String userId) {
        return books.removeIf(book -> book.contains(bookId));
    }

    @Override
    public boolean returnBook(String bookId, String userId) {
        books.add(bookId + " (Returned)");
        return true;
    }

    @Override
    public boolean addBook(String title, String author) {
        books.add(title + " by " + author);
        return true;
    }

    @Override
    public List<String> listBooks() {
        return books;
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
