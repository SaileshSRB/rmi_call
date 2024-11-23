package org.library.client;

import org.library.common.LibraryService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LibraryClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            LibraryService service = (LibraryService) registry.lookup("LibraryService");

            System.out.println("Available Books: " + service.listBooks());
            System.out.println("Search Result: " + service.searchBook("Book1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
