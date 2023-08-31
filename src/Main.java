/*
* Library Management System
* Name: Fernanda Frederico Ribeiro da Silva
* Class: Software Development I CEN-3024C-16046
* Professor: Walauskis
*/
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Add books from file");
            System.out.println("2. List books");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //Add Books from File Books_Database.txt // Needs the full path if not finding file
                    library.addBooksFromFile("C:\\Users\\ferna\\Desktop\\BAS\\SOFT_DEV1\\LMS\\LMS\\src\\Books_Database.txt");
                    library.listBooks();
                    break;
                case 2:
                    //List Books
                    library.listBooks();
                    break;
                case 3:
                    System.out.println("Exiting..." + "Byebye!\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}