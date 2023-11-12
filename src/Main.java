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
import java.sql.Connection;


/**
 * Class name: Main
 * This class is the entry point of the Library Management System program. It handles the user interface and interactions.
 */
public class Main {
    /**
     * Method name: main
     * This method is the entry point of the program. It initializes the library, handles user input, and controls the program flow.
     *
     * @param args Command line arguments
     * @throws IOException If an input or output exception occurred
     */
    public static void main(String[] args) throws IOException {

        Library library = new Library(); // Create a new library object

        //Call GUI
        MainFrame gui = new MainFrame(); // Create a new GUI object
        gui.setupGUI(library);  // Pass the library instance to setupGUI


        //Loading last saved version
        System.out.println("Loading last saved version file...\n"); // Print a message to indicate loading process
       // library.loadBooksFromFile(Path_to_Database.database); // Load books from the file specified by Path_to_Database.database

        //Create connection to database mySQL
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();

        //removed menu and switch to work in terminal - will use only GUI from now on
    }
}