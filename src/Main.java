/*
* Library Management System
* Name: Fernanda Frederico Ribeiro da Silva
* Class: Software Development I CEN-3024C-16046
* Professor: Walauskis
*/
public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        //Add Books from File Books_Database.txt // Change path in case it was not found just with name
        library.addBooksFromFile("C:\\Users\\ferna\\Desktop\\BAS\\SOFT_DEV1\\LMS\\LMS\\src\\Books_Database.txt");

        //List Books
        System.out.println("\n---------All Books in Database----------");
        library.listBooks();


        }
    }