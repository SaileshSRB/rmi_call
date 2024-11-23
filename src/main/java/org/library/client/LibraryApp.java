package org.library.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

                    createListBooksTab(),
                    createListBorrowedBooksTab()
            );

            // Styling the Tab Pane
            tabPane.setStyle("-fx-background-color: #f0f0f0; -fx-tab-min-width: 120; -fx-font-size: 14px;");

            Scene scene = new Scene(tabPane, 600, 500);
            scene.setFill(Color.LIGHTGRAY);  // Background color for the scene
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library Management System");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error connecting to the Library Service: " + e.getMessage());
        }
    }


    // Create Search Book Tab

    private Tab createSearchBookTab() {
        Tab tab = new Tab("Search Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter book title");
        searchField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5;");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-border-radius: 5;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5;"));

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");


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


    // Create Borrow Book Tab

    private Tab createBorrowBookTab() {
        Tab tab = new Tab("Borrow Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20));

        TextField bookIDField = new TextField();
        bookIDField.setPromptText("Enter book ID");
        bookIDField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        TextField userIDField = new TextField();
        userIDField.setPromptText("Enter your user ID");
        userIDField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        Button borrowButton = new Button("Borrow");
        borrowButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-border-radius: 5;");
        borrowButton.setOnMouseEntered(e -> borrowButton.setStyle("-fx-background-color: #007B9E; -fx-text-fill: white; -fx-border-radius: 5;"));
        borrowButton.setOnMouseExited(e -> borrowButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-border-radius: 5;"));

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");


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


    // Create Return Book Tab

    private Tab createReturnBookTab() {
        Tab tab = new Tab("Return Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20));

        TextField bookIDField = new TextField();
        bookIDField.setPromptText("Enter book ID");
        bookIDField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        TextField userIDField = new TextField();
        userIDField.setPromptText("Enter your user ID");
        userIDField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        Button returnButton = new Button("Return");
        returnButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-radius: 5;");
        returnButton.setOnMouseEntered(e -> returnButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-border-radius: 5;"));
        returnButton.setOnMouseExited(e -> returnButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-radius: 5;"));

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");


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


    // Create Add Book Tab

    private Tab createAddBookTab() {
        Tab tab = new Tab("Add Book");
        tab.setClosable(false);

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20));

        TextField titleField = new TextField();
        titleField.setPromptText("Enter book title");
        titleField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        TextField authorField = new TextField();
        authorField.setPromptText("Enter author name");
        authorField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        Button addButton = new Button("Add Book");
        addButton.setStyle("-fx-background-color: #8bc34a; -fx-text-fill: white; -fx-border-radius: 5;");
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #7cb342; -fx-text-fill: white; -fx-border-radius: 5;"));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: #8bc34a; -fx-text-fill: white; -fx-border-radius: 5;"));

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");


        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                if (!title.isEmpty() && !author.isEmpty()) {
                    boolean success = libraryService.addBook(title, author);
                    resultLabel.setText(success ? "Book added successfully." : "Failed to add book.");
                } else {

                    showError("Please enter both title and author.");

                }
            } catch (Exception ex) {
                showError("Error adding book: " + ex.getMessage());
            }
        });


        layout.getChildren().addAll(new Label("Book Title:"), titleField, new Label("Author:"), authorField, addButton, resultLabel);

        tab.setContent(layout);
        return tab;
    }

    // Create List Books Tab
    private Tab createListBooksTab() {
        Tab tab = new Tab("List All Books");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Button listBooksButton = new Button("List Books");
        listBooksButton.setStyle("-fx-background-color: #673ab7; -fx-text-fill: white; -fx-border-radius: 5;");
        listBooksButton.setOnMouseEntered(e -> listBooksButton.setStyle("-fx-background-color: #512b7f; -fx-text-fill: white; -fx-border-radius: 5;"));
        listBooksButton.setOnMouseExited(e -> listBooksButton.setStyle("-fx-background-color: #673ab7; -fx-text-fill: white; -fx-border-radius: 5;"));

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        listBooksButton.setOnAction(e -> {
            try {
                List<String> books = libraryService.listBooks();
                resultLabel.setText("Books in library:\n" + String.join("\n", books));
            } catch (Exception ex) {
                showError("Error listing books: " + ex.getMessage());
            }
        });


        layout.getChildren().addAll(listBooksButton, resultLabel);
        tab.setContent(layout);
        return tab;
    }

    // Create List Borrowed Books Tab
    private Tab createListBorrowedBooksTab() {
        Tab tab = new Tab("List Borrowed Books");
        tab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextField userIDField = new TextField();
        userIDField.setPromptText("Enter your user ID");
        userIDField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        Button listButton = new Button("List Borrowed Books");
        listButton.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-border-radius: 5;");
        listButton.setOnMouseEntered(e -> listButton.setStyle("-fx-background-color: #fb8c00; -fx-text-fill: white; -fx-border-radius: 5;"));
        listButton.setOnMouseExited(e -> listButton.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-border-radius: 5;"));

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        listButton.setOnAction(e -> {
            try {
                String userID = userIDField.getText();
                if (!userID.isEmpty()) {
                    List<String> borrowedBooks = libraryService.listBorrowedBooks(userID);
                    resultLabel.setText("Borrowed books:\n" + String.join("\n", borrowedBooks));
                } else {
                    showError("Please enter a user ID.");
                }
            } catch (Exception ex) {
                showError("Error listing borrowed books: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("User ID:"), userIDField, listButton, resultLabel);
        tab.setContent(layout);
        return tab;
    }

    // Show error message dialog
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/* new changes */

