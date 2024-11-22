package org.library.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.library.common.LibraryService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class LibraryApp extends Application {

    private LibraryService libraryService;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Connect to the RMI Registry and LibraryService
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            libraryService = (LibraryService) registry.lookup("LibraryService");

            // Build the UI
            TabPane tabPane = new TabPane();
            tabPane.getTabs().addAll(
                    createSearchBookTab(),
                    createBorrowBookTab(),
                    createReturnBookTab(),
                    createAddBookTab(),
                    createListBooksTab()
            );

            Scene scene = new Scene(tabPane, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library Management System");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error connecting to the Library Service: " + e.getMessage());
        }
    }

    private Tab createSearchBookTab() {
        Tab tab = new Tab("Search Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter book title");

        Button searchButton = new Button("Search");
        Label resultLabel = new Label();

        searchButton.setOnAction(e -> {
            try {
                String title = searchField.getText();
                if (!title.isEmpty()) {
                    String result = libraryService.searchBook(title);
                    resultLabel.setText(result);
                } else {
                    showError("Please enter a book title.");
                }
            } catch (Exception ex) {
                showError("Error searching for book: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Book Title:"), searchField, searchButton, resultLabel);
        tab.setContent(layout);
        return tab;
    }

    private Tab createBorrowBookTab() {
        Tab tab = new Tab("Borrow Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        TextField bookIDField = new TextField();
        bookIDField.setPromptText("Enter book ID");

        TextField userIDField = new TextField();
        userIDField.setPromptText("Enter your user ID");

        Button borrowButton = new Button("Borrow");
        Label resultLabel = new Label();

        borrowButton.setOnAction(e -> {
            try {
                String bookID = bookIDField.getText();
                String userID = userIDField.getText();
                if (!bookID.isEmpty() && !userID.isEmpty()) {
                    boolean success = libraryService.borrowBook(bookID, userID);
                    resultLabel.setText(success ? "Book borrowed successfully." : "Failed to borrow book.");
                } else {
                    showError("Please enter both Book ID and User ID.");
                }
            } catch (Exception ex) {
                showError("Error borrowing book: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Book ID:"), bookIDField, new Label("User ID:"), userIDField, borrowButton, resultLabel);
        tab.setContent(layout);
        return tab;
    }

    private Tab createReturnBookTab() {
        Tab tab = new Tab("Return Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        TextField bookIDField = new TextField();
        bookIDField.setPromptText("Enter book ID");

        TextField userIDField = new TextField();
        userIDField.setPromptText("Enter your user ID");

        Button returnButton = new Button("Return");
        Label resultLabel = new Label();

        returnButton.setOnAction(e -> {
            try {
                String bookID = bookIDField.getText();
                String userID = userIDField.getText();
                if (!bookID.isEmpty() && !userID.isEmpty()) {
                    boolean success = libraryService.returnBook(bookID, userID);
                    resultLabel.setText(success ? "Book returned successfully." : "Failed to return book.");
                } else {
                    showError("Please enter both Book ID and User ID.");
                }
            } catch (Exception ex) {
                showError("Error returning book: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Book ID:"), bookIDField, new Label("User ID:"), userIDField, returnButton, resultLabel);
        tab.setContent(layout);
        return tab;
    }

    private Tab createAddBookTab() {
        Tab tab = new Tab("Add Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        TextField titleField = new TextField();
        titleField.setPromptText("Enter book title");

        TextField authorField = new TextField();
        authorField.setPromptText("Enter author name");

        Button addButton = new Button("Add");
        Label resultLabel = new Label();

        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                if (!title.isEmpty() && !author.isEmpty()) {
                    boolean success = libraryService.addBook(title, author);
                    resultLabel.setText(success ? "Book added successfully." : "Failed to add book.");
                } else {
                    showError("Please enter both Title and Author.");
                }
            } catch (Exception ex) {
                showError("Error adding book: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Book Title:"), titleField, new Label("Author Name:"), authorField, addButton, resultLabel);
        tab.setContent(layout);
        return tab;
    }

    private Tab createListBooksTab() {
        Tab tab = new Tab("List Books");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        Button listButton = new Button("List All Books");
        TextArea bookListArea = new TextArea();
        bookListArea.setEditable(false);

        listButton.setOnAction(e -> {
            try {
                List<String> books = libraryService.listBooks();
                bookListArea.setText(String.join("\n", books));
            } catch (Exception ex) {
                showError("Error listing books: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(listButton, bookListArea);
        tab.setContent(layout);
        return tab;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
