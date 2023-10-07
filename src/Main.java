/*
* Library Management System
* Name: Fernanda Frederico Ribeiro da Silva
* Class: Software Development I CEN-3024C-16046
* Professor: Walauskis
*/

/**
 * Library Management System (LMS) is a console-based application for managing a library's collection of books.
 * The LMS allows users to add new books to the collection from a text file, remove a book from the collection using its ID number,
 * and list all books currently in the collection.
 * The text file used to add new books should be formatted as follows: each line represents a book, and the id, title, and author are separated by a comma.
 */

import java.io.IOException;
import java.util.Scanner;

/**
 * Class name: Main
 * This class is the entry point of the Library Management System program. It handles the user interface and interactions.
 */
public class Main {
    /**
     * Method name: main
     * This method is the entry point of the program. It initializes the library, handles user input, and controls the program flow.
     * @param args Command line arguments
     * @throws IOException If an input or output exception occurred
     */
    public static void main(String[] args) throws IOException {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;


        //Loading last saved version
        System.out.println("Loading last saved version file...\n");
        library.loadBooksFromFile(Path_to_Database.database);


        //menu

        while (!exit) {
            System.out.println("\n----------------- Library Management System -----------------\n");
            System.out.printf("%-30s %-30s %n", "------ Management ------", "------ User ------");
            System.out.printf("%-30s %-30s %n", "1. Add new Books to Database", "2. List books");
            System.out.printf("%-30s %-30s %n", "2. List books", "6. CheckOut Books by title");
            System.out.printf("%-30s %-30s %n", "3. Remove Books by ID", "7. CheckIn Books by title");
            System.out.printf("%-30s %-30s %n", "4. Remove Books by Barcode", "");
            System.out.printf("%-30s %-30s %n", "5. Remove Books by Title", "");
            System.out.println("\n8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1:
                    //Add Books from File Books_Database.txt
                    library.addBookToFile(Path_to_Database.database);
                    library.loadBooksFromFile(Path_to_Database.database);
                    library.listBooks();
                    break;
                case 2:
                    //List Books
                    library.listBooks();
                    break;

                case 3:
                    //Remove Books By ID
                    System.out.println("Write the id of the book to be removed:\n");
                    int id = scanner.nextInt();
                    library.removeBook(id);
                    System.out.println("\n\nDatabase backup - Backup_Books_Database.txt create...\n saving new database...");

                    //create this thread to make a little pause for the user know that a Backup exists for their database
                    try {
                        Thread.sleep(2000); // Pause for 2000 milliseconds, or 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    library.listBooks();

                    break;

                case 4:
                    //Remove Books BY Barcode
                    System.out.println("Write the Barcode of the book to be removed:\n");
                    String barcode = scanner.nextLine();
                    library.removeBookBarcode(barcode, true); // Pass the barcode to the removeBook method
                    System.out.println("\n\nDatabase backup - Backup_Books_Database.txt create...\n saving new database...");

                    //create this thread to make a little pause for the user know that a Backup exists for their database
                    try {
                        Thread.sleep(2000); // Pause for 2000 milliseconds, or 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    library.listBooks();

                    break;

                case 5:
                    // Remove books by title
                    System.out.println("Please enter the title of the book you want to remove:");
                    String title = scanner.nextLine();
                    // Remove the book with the given title
                    library.removeBookByTitle(title);
                    library.listBooks();
                    break;

                case 6:
                    // Checkout a book
                    System.out.print("Enter the title of the book: ");
                    title = scanner.nextLine();
                    boolean successOut = library.checkoutBook(title, Path_to_Database.database);
                    if (successOut) {
                        System.out.println("Successfully checked out " + title);
                    } else {
                        System.out.println("Failed to check out " + title);
                    }

                    //create this thread to make a little pause for the user read
                    try {
                        Thread.sleep(2000); // Pause for 2000 milliseconds, or 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    library.listBooks();
                    break;

                case 7:
                    // CheckIn a book
                    System.out.print("Enter the title of the book: ");
                    title = scanner.nextLine();
                    boolean successIn = library.checkInBook(title, Path_to_Database.database);
                    if (successIn) {
                        System.out.println("Successfully checked In " + title);
                    } else {
                        System.out.println("Failed to checkIn " + title);
                    }

                    //create this thread to make a little pause for the user read
                    try {
                        Thread.sleep(2000); // Pause for 2000 milliseconds, or 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    library.listBooks();
                    break;

                case 8:
                    //exit
                    System.out.println("Exiting..." + "Byebye!\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}