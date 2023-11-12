/*
 * Library Management System
 * Name: Fernanda Frederico Ribeiro da Silva
 * Class: Software Development I CEN-3024C-16046
 * Professor: Walauskis
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class name: Library
 * This class represents a library and its collection of books.
 * It provides methods for loading books from a text file, adding new books to the collection, listing all books in the collection remove books from the file,
 * and backup an old version of the file before doing new changes.
 */
public class Library {
    private static ArrayList<Book> books;

    public ArrayList<Book> getBooks() {
        return books;
    }

    /**
     * Constructor for the Library class.
     * Initializes an empty list of books.
     */
    public Library() {
        books = new ArrayList<>();
    }

    /**
     * Method name: loadBooksFromDatabase
     * This method loads books from a MySQL database into the library's collection of books.
     * @throws SQLException If a database access error occurred
     */
    public void loadBooksFromDatabase() throws SQLException {
        // clear any existing books before loading new books from the database.
        books.clear();

        // Load new books from the database
        try {
            DatabaseConnector dbConnector = new DatabaseConnector();
            Connection connection = dbConnector.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                String barcode = rs.getString("Barcode");
                LocalDate returnDate = rs.getDate("ReturnDate") != null ? rs.getDate("ReturnDate").toLocalDate() : null;
                String checkStatus = (returnDate != null) ? "checkedOut" : "checkedIn";

                Book book = new Book(id, title, author, barcode);
                book.setDueDate(returnDate);
                book.setCheckStatus(checkStatus);
                books.add(book);
            }

            System.out.println("Database Loaded Successfully!");
        } catch (SQLException e) {
            System.out.println("Cannot load books from the database: " + e.getMessage());
        }

        Collections.sort(books, Comparator.comparingInt(Book::getId));
    }

    /**
     * Method name: addBookToDatabase
     * This method adds a new book to the library's collection and saves it to a MySQL database.
     *
     * @throws SQLException If a database access error occurred
     */
    public void addBookToDatabase(String title, String author, String barcode) throws SQLException {
        // Create a new Book object
        Book book = new Book(0, title, author, barcode); // the ID will be set later

        // Add new book to the database
        try (Connection connection = new DatabaseConnector().connect()) {
            // Get the maximum ID from the Books table
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM Books");
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }

            // Set the ID of the new book to maxId + 1
            book.setId(maxId + 1);

            // Insert the new book
            String sql = "INSERT INTO Books (ID, Title, Author, Barcode) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getBarcode());
            pstmt.executeUpdate();

            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding book to the database: " + e.getMessage());
        }
    }

    /**
     * Method name: removeBookBarcode
     * This method removes a book from the library's collection using its ID or barcode.
     *
     * @param identifier The ID or barcode of the book to be removed.
     * @param isBarcode A boolean flag indicating whether the identifier is a barcode. If true, the identifier is treated as a barcode; if false, it's treated as an ID.
     * @throws SQLException If a database access error occurred
     */
    public String removeBookBarcode(String identifier, boolean isBarcode) throws SQLException {
        // Remove the book from the database
        String sql = "DELETE FROM Books WHERE " + (isBarcode ? "Barcode" : "ID") + " = ?";

        try (Connection connection = new DatabaseConnector().connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if (isBarcode) {
                pstmt.setString(1, identifier);
            } else {
                pstmt.setInt(1, Integer.parseInt(identifier));
            }

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book with " + (isBarcode ? "barcode " : "ID ") + identifier + " removed!");

                // Reorder the ids
                reorderIds();

                return "Book with " + (isBarcode ? "barcode " : "ID ") + identifier + " removed!";
            } else {
                return "Book with " + (isBarcode ? "barcode " : "ID ") + identifier + " not found";
            }
        } catch (SQLException e) {
            System.out.println("Error removing book from the database: " + e.getMessage());
            return "Error removing book from the database: " + e.getMessage();
        }
    }

    /**
     * Method name: removeBookByTitle
     * This method removes a book from the library's collection by title, which is supplied by the user.
     *
     * @param title The title of the book to be removed.
     * @throws SQLException If a database access error occurred
     */
    public String removeBookByTitle(String title) throws SQLException {
        // Remove the book from the database
        String sql = "DELETE FROM Books WHERE Title = ?";

        try (Connection connection = new DatabaseConnector().connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, title);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The book with title " + title + " was successfully removed from the database.");

                // Reorder the ids
                reorderIds();

                return "The book with title " + title + " was successfully removed from the database.";

            } else {
                return "The book with title " + title + " was not found in the database.";
            }
        } catch (SQLException e) {
            System.out.println("Error removing book from the database: " + e.getMessage());
            return "Error removing book from the database: " + e.getMessage();
        }
    }


    /**
     * Method name: listBooks
     * This method lists all books currently in the library's collection.
     *
     * @return A string representation of all books in the collection
     * @throws SQLException If a database access error occurred
     */
    public String listBooks() throws SQLException {
        StringBuilder bookList = new StringBuilder();

        // Load books from the database
        try {
            DatabaseConnector dbConnector = new DatabaseConnector();
            Connection connection = dbConnector.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books ORDER BY ID");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                String barcode = rs.getString("Barcode");
                LocalDate dueDate = rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null;
                String checkStatus = (dueDate != null) ? "checkedOut" : "checkedIn";

                Book book = new Book(id, title, author, barcode);
                book.setDueDate(dueDate);
                book.setCheckStatus(checkStatus);

                bookList.append(book.toString()).append("\n");
            }


            System.out.println("Books Loaded Successfully!");
        } catch (SQLException e) {
            System.out.println("Cannot load books from the database: " + e.getMessage());
        }

        // Reorder the ids
        reorderIds();

        return bookList.toString();
    }


    /**
     * Method name: checkoutBook
     * This method checks out a book from the library's collection using its title.
     * If the book is found and is not already checked out, it sets the book as checked out and assigns a due date 4 weeks from now.
     * It then updates the library database.
     *
     * @param title The title of the book to be checked out.
     * @return boolean Returns true if the book was successfully checked out, false otherwise.
     */
    public String checkoutBook(String title) throws SQLException {
        // SQL query to get the book by title
        String getBookSql = "SELECT * FROM Books WHERE Title = ?";

        // SQL query to update the book status and due date
        String updateBookSql = "UPDATE Books SET CheckStatus = ?, DueDate = ? WHERE Title = ?";

        try (Connection connection = new DatabaseConnector().connect();
             PreparedStatement pstmtGetBook = connection.prepareStatement(getBookSql);
             PreparedStatement pstmtUpdateBook = connection.prepareStatement(updateBookSql)) {

            // Get the book by title
            pstmtGetBook.setString(1, title);
            ResultSet rs = pstmtGetBook.executeQuery();

            if (!rs.next()) {
                return "Book not found in the library.";
            } else if ("checkedOut".equals(rs.getString("CheckStatus"))) {
                return "Book is already checked out.";
            } else {
                // Set the book as checked out and assign a due date 4 weeks from now
                pstmtUpdateBook.setString(1, "checkedOut");
                pstmtUpdateBook.setDate(2, java.sql.Date.valueOf(LocalDate.now().plus(4, ChronoUnit.WEEKS)));
                pstmtUpdateBook.setString(3, title);
                pstmtUpdateBook.executeUpdate();

                return "Book checked out. Due date is in 4 weeks: " + LocalDate.now().plus(4, ChronoUnit.WEEKS);
            }
        } catch (SQLException e) {
            System.out.println("Error checking out book: " + e.getMessage());
            return "Error checking out book: " + e.getMessage();
        }
    }

    /**
     * Method name: checkInBook
     * This method checks in a book to the library's collection using its title.
     * If the book is found and is checked out, it sets the book as checked in and removes the due date.
     * If the due date has passed, it informs the user about a fine. It then prints a message indicating that the book was successfully checked in.
     *
     * @param title The title of the book to be checked in.
     *
     * @return boolean Returns true if the book was successfully checked in, false otherwise.
     * @throws IOException If an input or output exception occurred
     */
    public String checkInBook(String title) throws SQLException {
        // SQL query to get the book by title
        String getBookSql = "SELECT * FROM Books WHERE Title = ?";

        // SQL query to update the book status and due date
        String updateBookSql = "UPDATE Books SET CheckStatus = ?, DueDate = NULL WHERE Title = ?";

        try (Connection connection = new DatabaseConnector().connect();
             PreparedStatement pstmtGetBook = connection.prepareStatement(getBookSql);
             PreparedStatement pstmtUpdateBook = connection.prepareStatement(updateBookSql)) {

            // Get the book by title
            pstmtGetBook.setString(1, title);
            ResultSet rs = pstmtGetBook.executeQuery();

            if (!rs.next()) {
                return "Book not found in the library.";
            } else if (!"checkedOut".equals(rs.getString("CheckStatus"))) {
                return "This book was not checked out.";
            } else if (rs.getDate("DueDate") == null) {
                return "This book does not have a due date.";
            } else {
                // Set the book as checked in and remove the due date
                pstmtUpdateBook.setString(1, "checkedIn");
                pstmtUpdateBook.setString(2, title);
                pstmtUpdateBook.executeUpdate();

                if (rs.getDate("DueDate").toLocalDate().isBefore(LocalDate.now())) {
                    return "The due date for this book has passed. There is a fine of $5 to be paid at the library front.";
                }

                return "Book checked in successfully.";
            }
        } catch (SQLException e) {
            System.out.println("Error checking in book: " + e.getMessage());
            return "Error checking in book: " + e.getMessage();
        }
    }

    public void reorderIds() throws SQLException {
        try (Connection connection = new DatabaseConnector().connect()) {
            // Start transaction
            connection.setAutoCommit(false);

            // Get the maximum ID from the Books table
            String getMaxIdSql = "SELECT MAX(id) FROM Books";
            PreparedStatement pstmtGetMaxId = connection.prepareStatement(getMaxIdSql);
            ResultSet rs = pstmtGetMaxId.executeQuery();
            rs.next();
            int maxId = rs.getInt(1);

            // Update the IDs that are 0 or duplicate with new IDs
            String updateInvalidIdSql = "UPDATE Books SET id = ? WHERE id = 0 OR id IN (SELECT id FROM (SELECT id FROM Books GROUP BY id HAVING COUNT(*) > 1) AS a)";
            PreparedStatement pstmtUpdateInvalidId = connection.prepareStatement(updateInvalidIdSql);
            ResultSet rsInvalidId = pstmtUpdateInvalidId.executeQuery();
            while (rsInvalidId.next()) {
                pstmtUpdateInvalidId.setInt(1, ++maxId);
                pstmtUpdateInvalidId.executeUpdate();
            }

            // Create a temporary table with new id order
            String createTempTableSql = "CREATE TEMPORARY TABLE temp_table AS SELECT id, title, ROW_NUMBER() OVER (ORDER BY id) as new_id FROM Books";
            try (PreparedStatement pstmt = connection.prepareStatement(createTempTableSql)) {
                pstmt.executeUpdate();
            }

            // Update the original table with new ids
            String updateIdSql = "UPDATE Books INNER JOIN temp_table ON Books.id = temp_table.id SET Books.id = temp_table.new_id";
            try (PreparedStatement pstmt = connection.prepareStatement(updateIdSql)) {
                pstmt.executeUpdate();
            }

            // Verify the checkout status
            String verifyCheckoutStatusSql = "UPDATE Books SET CheckStatus = IF(DueDate IS NULL, 'checkedIn', 'checkedOut')";
            try (PreparedStatement pstmt = connection.prepareStatement(verifyCheckoutStatusSql)) {
                pstmt.executeUpdate();
            }

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error reordering ids: " + e.getMessage());
        }
    }
}
