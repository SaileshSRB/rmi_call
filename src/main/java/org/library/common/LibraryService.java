package org.library.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryService extends Remote {

    // Method to search for a book by title
    String searchBook(String title) throws RemoteException;

    // Method to borrow a book by bookId and userId
    boolean borrowBook(String bookId, String userId) throws RemoteException;

    // Method to return a borrowed book by bookId and userId
    boolean returnBook(String bookId, String userId) throws RemoteException;

    // Method to add a new book to the library
    boolean addBook(String title, String author) throws RemoteException;

    // Method to list all books in the library
    List<String> listBooks() throws RemoteException;

    // Method to list all borrowed books by a specific user
    List<String> listBorrowedBooks(String userId) throws RemoteException;
}
