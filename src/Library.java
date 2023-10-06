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
    private ArrayList<Book> books;

    /**
     * Constructor for the Library class.
     * Initializes an empty list of books.
     */
    public Library() {
        books = new ArrayList<>();
    }

    /**
     * Method name: loadBooksFromFile
     * This method loads books from a text file into the library's collection of books.
     * @param Books_Database The path to the text file containing the books to be loaded.
     * @throws IOException If an input or output exception occurred
     */
    public void loadBooksFromFile(String Books_Database) throws IOException {
        // clear any existing books before loading new books from the text file.
        books.clear();

        // Load new books from the text file
        try {
            Scanner scanner = new Scanner(new File(Books_Database));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bookInfo = line.split(",");

                int id = Integer.parseInt(bookInfo[0]);
                String title = bookInfo[1];
                String author = bookInfo[2];
                String barcode = bookInfo.length > 3 ? bookInfo[3] : "default_barcode";
                LocalDate dueDate = !bookInfo[4].trim().equals("null") ? LocalDate.parse(bookInfo[4].trim()) : null;

                Book book = new Book(id, title, author, barcode);
                book.setDueDate(dueDate);
                books.add(book);
            }
            scanner.close();
            System.out.println("File Loaded Successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + Books_Database + "\n ---- Check if path for Books_Database.txt has changed! ---- ");
        }

        Collections.sort(books, Comparator.comparingInt(Book::getId));
    }
    /**
     * Method name: addBookToFile
     * This method adds a new book to the library's collection and saves it to a text file.
     * @param Books_Database The path to the text file where the new book will be saved.
     * @throws IOException If an input or output exception occurred
     */
    public void addBookToFile(String Books_Database) throws IOException {
        // Backup the original database
        backupDatabase(Path_to_Database.database, Path_to_Database.databaseBackup);

        // Load existing books from the text file
        List<Book> books = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(Books_Database));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bookInfo = line.split(",");

                int id = Integer.parseInt(bookInfo[0]);
                String title = bookInfo[1];
                String author = bookInfo[2];
                String barcode = bookInfo.length > 3 ? bookInfo[3] : "default_barcode";
                books.add(new Book(id, title, author, barcode)); // Modified to include barcode
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + Books_Database + "\n ---- Check if path for Books_Database.txt has changed! ---- ");
        }

        // Generate a new ID by checking the .txt file for existing ID numbers and continuing from the last ID saved on it
        int newId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;

        // Prompt the user to enter the name of the book and the author
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter the title of the book: ");
        String title = inputScanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = inputScanner.nextLine();

        // Prompt the user to enter a valid barcode
        String barcode;
        while (true) {
            System.out.print("Enter a barcode sequence of 8 characters without spaces or special characters: ");
            barcode = inputScanner.nextLine();
            if (barcode.matches("[\\w]{8}")) { // Checks if barcode is exactly 8 alphanumeric characters
                break;
            } else {
                System.out.println("Invalid barcode. Please try again.");
            }
        }

        // Add new book to list
        books.add(new Book(newId, title, author, barcode)); // Modified to include barcode

        // Save updated list of books to text file
        try (PrintWriter writer = new PrintWriter(new FileWriter(Books_Database))) {
            for (Book book : books) {
                writer.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + "," + book.getBarcode()); // Modified to include barcode
            }
            System.out.println("Book added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file " + Books_Database);
        }
    }
    /**
     * Method name: removeBook
     * This method removes a book from the library's collection using its ID number.
     * @param id The ID number of the book to be removed.
     * @throws IOException If an input or output exception occurred
     */
    public void removeBook(int id) throws IOException {
        // Backup the original database
        backupDatabase(Path_to_Database.database, Path_to_Database.databaseBackup);

        int index = binarySearch(id, 0, books.size()-1);
        if (index !=-1){
            System.out.println("Book ID!" + id + " removed!");
            books.remove(index);

            // Update the original database   ---- NEEDS to make a correction to the id after removed, if continue with this project!
            updateDatabase(Path_to_Database.database);
        } else {
            System.out.println("Book ID: " + id +  " not found");
        }
    }

    /**
     * Method name: removeBook
     * This method removes a book from the library's collection using its ID or barcode.
     *
     * @param identifier The ID or barcode of the book to be removed.
     * @throws IOException If an input or output exception occurred
     */
    public void removeBookBarcode(String identifier, boolean isBarcode) throws IOException {
        // Backup the original database
        backupDatabase(Path_to_Database.database, Path_to_Database.databaseBackup);

        // Find the book with the given identifier
        Book bookToRemove = null;
        for (Book book : books) {
            if ((isBarcode && book.getBarcode().equals(identifier)) || (!isBarcode && String.valueOf(book.getId()).equals(identifier))) {
                bookToRemove = book;
                break;
            }
        }

        // If the book was found, remove it
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("Book with " + (isBarcode ? "barcode " : "ID ") + identifier + " removed!");

            // Update the ID numbers of the remaining books
            for (int i = 0; i < books.size(); i++) {
                books.get(i).setId(i + 1);
            }

            // Update the original database
            updateDatabase(Path_to_Database.database);
        } else {
            System.out.println("Book with " + (isBarcode ? "barcode " : "ID ") + identifier + " not found");
        }
    }

    /**
     * Method name: backupDatabase
     * This method creates a backup of the original database file.
     * @param originalFile The path to the original database file.
     * @param backupFile The path to the backup file.
     * @throws IOException If an input or output exception occurred
     */
    //create a backup from the last loaded file before update the database content
    public void backupDatabase(String originalFile, String backupFile) throws IOException {
        Path src = Paths.get(originalFile);
        Path dest = Paths.get(backupFile);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }
    /**
     * Method name: updateDatabase
     * This method saves changes made to the library's collection of books to the database text file.
     * @param filename The path to the database text file.
     * @throws IOException If an input or output exception occurred
     */
    //save changes from the software to the database text file
    public void updateDatabase(String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (Book book : books) {
                String dueDate = book.getDueDate() != null ? book.getDueDate().toString() : "null";
                out.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + "," + book.getBarcode() + "," + dueDate);
            }
        }
    }
    /**
     * Method name: binarySearch
     * This method performs a binary search on the library's collection of books to find a book with a specific ID number.
     * @param id The ID number of the book to be found.
     * @param left The leftmost index of the search range.
     * @param right The rightmost index of the search range.
     * @return The index of the book with the specified ID number, or -1 if not found.
     */
    private int binarySearch(int id, int left, int right){
        if(left > right){
            return -1;
        }
        int mid = left + (right - left)/2;
        if (books.get(mid).getId()==id){
            return mid;
        }else if(books.get(mid).getId()<id){
            return binarySearch(id, mid+1, right);
        }else{
            return binarySearch(id,left,mid-1);
        }
    }
    /**
     * Method name: listBooks
     * This method lists all books currently in the library's collection.
     */
    public void listBooks(){
        System.out.println("\n---------All Books in Database----------");
        for(Book book:books){
            System.out.println(book);
        }
    }

    /**
     * Method name: CheckOut
     * @param title
     * @return
     */

    public boolean checkoutBook(String title) {
        Book bookToCheckout = null;
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                bookToCheckout = book;
                break;
            }
        }

        if (bookToCheckout == null) {
            System.out.println("Book not found in the library.");
            return false;
        } else if (bookToCheckout.isCheckedOut()) {
            System.out.println("Book is already checked out.");
            return false;
        } else {
            bookToCheckout.setCheckedOut(true);
            bookToCheckout.setDueDate(LocalDate.now().plus(3, ChronoUnit.WEEKS));
            System.out.println("Book checked out. Due date is in 3 weeks: " + bookToCheckout.getDueDate());

            // Save updated list of books to text file
            try {
                updateDatabase(Path_to_Database.database);
                System.out.println("Database updated successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file " + Path_to_Database.database);
            }

            return true;
        }
    }

    /**checkIn
     *
     * @param title
     */
    public boolean checkInBook(String title) {
        Book bookToCheckIn = null;
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                bookToCheckIn = book;
                break;
            }
        }

        if (bookToCheckIn == null) {
            System.out.println("Book not found in the library.");
            return false;
        } else if (!bookToCheckIn.isCheckedOut()) {
            System.out.println("This book was not checked out.");
            return false;
        } else {
            bookToCheckIn.setCheckedOut(false);
            if (bookToCheckIn.getDueDate().isBefore(LocalDate.now())) {
                System.out.println("The due date for this book has passed. There is a fine of $5 to be paid at the library front.");
            }
            bookToCheckIn.setDueDate(null);
            System.out.println("Book checked in successfully.");
            return true;
        }
    }
}
