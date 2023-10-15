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
                String checkStatus = (dueDate != null) ? "checkedOut" : "checkedIn";

                Book book = new Book(id, title, author, barcode);
                book.setDueDate(dueDate);
                book.setCheckStatus(checkStatus);
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
                int id = Integer.parseInt(bookInfo[0].trim());
                String title = bookInfo[1].trim();
                String author = bookInfo[2].trim();
                String barcode = bookInfo.length > 3 ? bookInfo[3].trim() : "default_barcode";

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

        //Prompt the user to enter a valid barcode
        String barcode;
        while (true) {
            System.out.print("Enter a barcode sequence of 8 characters without spaces or special characters: ");
            barcode = inputScanner.nextLine();
            if (!barcode.matches("[\\w]{8}")) { // Checks if barcode is exactly 8 alphanumeric characters
                System.out.println("Invalid barcode. Please try again.");
            } else {
                final String finalBarcode = barcode;
                boolean barcodeExists = books.stream().anyMatch(book -> book.getBarcode().equals(finalBarcode));
                if (barcodeExists) {
                    System.out.println("A book with this barcode already exists. Please try again with a different barcode.");
                } else {
                    break;
                }
            }
        }

// Add new book to list
        books.add(new Book(newId, title, author, barcode)); // Modified to include barcode

// Save updated list of books to text file
        try (PrintWriter writer = new PrintWriter(new FileWriter(Books_Database))) {
            for (Book book : books) {
                String dueDate = book.getDueDate() != null ? book.getDueDate().toString() : "null";
                writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getBarcode() + "," + dueDate); // Modified to include dueDate
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
   /* public void removeBook(int id) throws IOException {
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
    }*/

    public void removeBookID(int id, String Books_Database) throws IOException {
        // Backup the original database
        backupDatabase(Books_Database, Path_to_Database.databaseBackup);

        int index = binarySearch(id, 0, books.size()-1);
        if (index !=-1){
            System.out.println("Book ID!" + id + " removed!");
            books.remove(index);

            // Update the original database
            updateDatabase(Books_Database);
        } else {
            System.out.println("Book ID: " + id +  " not found");
        }
    }

    /**
     * Method name: removeBookBarcode
     * This method removes a book from the library's collection using its ID or barcode.
     *
     * @param identifier The ID or barcode of the book to be removed.
     * @param isBarcode A boolean flag indicating whether the identifier is a barcode. If true, the identifier is treated as a barcode; if false, it's treated as an ID.
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
     * Method name: removeBookByTitle
     * This method removes a book from the library's collection by title, which is supplied by the user.
     * @param title The title of the book to be removed.
     */
    public void removeBookByTitle(String title) {
        // Sort the books by title for binary search
        Collections.sort(books, Comparator.comparing(Book::getTitle));

        // Perform binary search to find the book by title
        int index = binarySearchByTitle(title, 0, books.size() - 1);

        // If the book is found, remove it from the collection
        if (index != -1) {
            books.remove(index);
            System.out.println("The book with title " + title + " was successfully removed from the database.");
        } else {
            System.out.println("The book with title " + title + " was not found in the database.");
        }
    }

    /**
     * Method name: binarySearchByTitle
     * This method performs a binary search on the library's collection of books to find a book with a specific title.
     * @param title The title of the book to be found.
     * @param left The leftmost index of the search range.
     * @param right The rightmost index of the search range.
     * @return The index of the book with the specified title, or -1 if not found.
     */
    public int binarySearchByTitle(String title, int left, int right) {
        if (right >= left) {
            int mid = left + (right - left) / 2;

            // If the book is present at the middle
            if (books.get(mid).getTitle().equals(title))
                return mid;

            // If the book title is lexicographically smaller
            if (books.get(mid).getTitle().compareTo(title) > 0)
                return binarySearchByTitle(title, left, mid - 1);

            // Else the book can only be present in right subarray
            return binarySearchByTitle(title, mid + 1, right);
        }

        // We reach here when the book is not present in the list
        return -1;
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
                out.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getBarcode() + "," + dueDate);
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
    public void listBooks(String filePath){
        // Reorganize IDs before listing books
        try {
            reorganizeIDs(filePath);
            fillIDGaps(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //print books
        System.out.println("\n---------All Books in Database----------");
        for(Book book:books){
            System.out.println(book);
        }

        //create this thread to make a little pause for the user read the list
        try {
            Thread.sleep(3000); // Pause for 3000 milliseconds, or 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public boolean checkoutBook(String title, String Books_Database) {
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
        } else if ("checkedOut".equals(bookToCheckout.getCheckStatus())) {
            System.out.println("Book is already checked out.");
            return false;
        } else {
            bookToCheckout.setCheckStatus("checkedOut");
            bookToCheckout.setDueDate(LocalDate.now().plus(4, ChronoUnit.WEEKS));
            System.out.println("Book checked out. Due date is in 4 weeks: " + bookToCheckout.getDueDate());

            // Save updated list of books to text file
            try (PrintWriter writer = new PrintWriter(new FileWriter(Books_Database))) {
                for (Book book : books) {
                    String dueDate = book.getDueDate() != null ? book.getDueDate().toString() : "null";
                    writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getBarcode() + "," + dueDate + "," + book.getCheckStatus());
                }
                System.out.println("Database updated successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file " + Books_Database);
            }

            return true;
        }
    }

    /**
     * Method name: checkInBook
     * This method checks in a book to the library's collection using its title.
     * If the book is found and is checked out, it sets the book as checked in and removes the due date.
     * If the due date has passed, it informs the user about a fine. It then prints a message indicating that the book was successfully checked in.
     *
     * @param title The title of the book to be checked in.
     * @param Books_Database The path to the text file where the updated book information will be saved.
     * @return boolean Returns true if the book was successfully checked in, false otherwise.
     * @throws IOException If an input or output exception occurred
     */
    public boolean checkInBook(String title, String Books_Database) throws IOException {
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
        } else if (!"checkedOut".equals(bookToCheckIn.getCheckStatus())) {
            System.out.println("This book was not checked out.");
            return false;
        } else {
            bookToCheckIn.setCheckStatus("checkedIn");
            if (bookToCheckIn.getDueDate().isBefore(LocalDate.now())) {
                System.out.println("The due date for this book has passed. There is a fine of $5 to be paid at the library front.");
            }
            bookToCheckIn.setDueDate(null);
            System.out.println("Book checked in successfully.");

            // Save updated list of books to text file
            try (PrintWriter writer = new PrintWriter(new FileWriter(Books_Database))) {
                for (Book book : books) {
                    String dueDate = book.getDueDate() != null ? book.getDueDate().toString() : "null";
                    writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getBarcode() + "," + dueDate + "," + book.getCheckStatus());
                }
                System.out.println("Database updated successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file " + Books_Database);
            }

            return true;
        }
    }

    /**
     * Method name: reorganizeIDs
     * This method reorganizes the IDs of the books in the library's collection and writes the updated information back to the text file.
     * @param Books_Database The path to the text file where the updated book information will be saved.
     * @throws IOException If an input or output exception occurred
     */
    public static void reorganizeIDs(String Books_Database) throws IOException {
        // Sort the books by ID
        Collections.sort(books, Comparator.comparingInt(Book::getId));

        // Reassign IDs
        for (int i = 0; i < books.size(); i++) {
            books.get(i).setId(i + 1);  // Assuming IDs start from 1
        }

        // Save the updated list of books to the text file
        try {
            PrintWriter writer = new PrintWriter(new File(Books_Database));
            for (Book book : books) {
                writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getBarcode() + "," + book.getDueDate());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + Books_Database + "\n ---- Check if path for Books_Database.txt has changed! ---- ");
        }
    }

    /**
     * Method name: fillIDGaps
     * This method fills the gaps in the IDs of the books in the library's collection and writes the updated information back to the text file.
     * @param Books_Database The path to the text file where the updated book information will be saved.
     * @throws IOException If an input or output exception occurred
     */
    public static void fillIDGaps(String Books_Database) throws IOException {
        // Sort the books by ID
        Collections.sort(books, Comparator.comparingInt(Book::getId));

        // Reassign IDs
        int expectedID = 1;  // Assuming IDs start from 1
        for (Book book : books) {
            book.setId(expectedID++);
        }
        //System.out.println("ID reassigned!");

        // Save the updated list of books to the text file
        try {
            PrintWriter writer = new PrintWriter(new File(Books_Database));
            for (Book book : books) {
                writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getBarcode() + "," + book.getDueDate());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + Books_Database + "\n ---- Check if path for Books_Database.txt has changed! ---- ");
        }
    }
}
