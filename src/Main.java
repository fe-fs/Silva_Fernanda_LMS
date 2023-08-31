/*
* Library Management System
* Name: Fernanda Frederico Ribeiro da Silva
* Class: Software Development I CEN-3024C-16046
* Professor: Walauskis
*/
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;


        //Loading last saved version
        System.out.println("Loading last saved version file...\n");
        library.loadBooksFromFile(Path_to_Database.database);



        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Load books saved from file Books_Database.txt");
            System.out.println("2. List books");
            System.out.println("3. Remove Books by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //Add Books from File Books_Database.txt // Needs the full path if not finding file
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
                    System.out.println("Exiting..." + "Byebye!\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}