package org.library.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryService extends Remote {
    String searchBook(String title) throws RemoteException;
    boolean borrowBook(String bookId, String userId) throws RemoteException;
    boolean returnBook(String bookId, String userId) throws RemoteException;
    boolean addBook(String title, String author) throws RemoteException;
    List<String> listBooks() throws RemoteException;
}
