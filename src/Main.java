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
            System.out.println("\n------Menu------");
            System.out.println("1. Add new Books to Database");
            System.out.println("2. List books");
            System.out.println("3. Remove Books by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

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
                    //Remove Books
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