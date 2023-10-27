/*
 * Library Management System
 * Name: Fernanda Frederico Ribeiro da Silva
 * Class: Software Development I CEN-3024C-16046
 * Professor: Walauskis
 */

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class name: LibraryTest
 * This class is used for testing the Library class. It creates a temporary file and loads some books into the library before each test.
 * It also overrides some methods from the Library class to prevent interference with the tests.
 */
class LibraryTest {
    File tempFile;
    Library library;
    static String BooksTest_Database;

    /**
     * This is a subclass of Library that overrides some methods for testing purposes.
     */
    class TestableLibrary extends Library {

        /**
         * Overrides the backupDatabase method from the Library class.
         * This method does nothing in the test class.
         */
        @Override
        public void backupDatabase(String Books_Database, String databaseBackup) {
            // Do nothing
        }

        /**
         * Overrides the updateDatabase method from the Library class.
         * This method calls the superclass's updateDatabase method with the path of the temporary file.
         */
        @Override
        public void updateDatabase(String filename) throws IOException {
            super.updateDatabase(tempFile.getPath());
        }

    }

        /**
         * Method name: setUp
         * This method is run before each test. It creates a new temporary file and loads some books into the library.
         * @throws IOException if an I/O error occurs when creating the file or writing to it
         */
        @BeforeEach
        void setUp() throws IOException {
            // Create a temporary file
            tempFile = Files.createTempFile("test_books", ".txt").toFile();
            // Now you can get its path
            BooksTest_Database = tempFile.getPath();

            // Write some known data to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                writer.println("1,Pride and Prejudice,Jane Austen,23456789,2023-10-26");
                writer.println("2,War and Peace,Leo Tolstoy,56789012,null");
            }

            // Load the books from the file
            library = new TestableLibrary();
            //library = new Library();
            library.loadBooksFromFile(tempFile.getPath());
        }

        /**
         * Method name: tearDown
         * This method is run after all tests in the class have finished. It deletes the temporary file used in the tests.
         * @throws IOException if an I/O error occurs when deleting the file
         */
        @AfterAll
        static void tearDown() throws IOException {
                Files.delete(Path.of(BooksTest_Database));
        }

        /**
         * Method name: testLoadBooksFromFile
         * This method tests the loadBooksFromFile method of the Library class. It verifies that the books were loaded correctly from the file and prints the list of books.
         */
        @Test
        void testLoadBooksFromFile() {
            System.out.println("Test loadBooksFromFile method");
            // Verify that the books were loaded correctly
            assertEquals(2, library.getBooks().size());

            //print list
            library.listBooks(BooksTest_Database);
        }

        /**
         * Method name: testAddBookToFile
         * This method tests the addBookToFile method of the Library class. It simulates user input to add a new book to the library,
         * then verifies that the book was added correctly by comparing the expected and actual book details.
         * @throws IOException if an I/O error occurs when writing to or reading from the file
         */
        @Test
        void testAddBookToFile() throws IOException {
            System.out.println("loadBooksFromFile method Test");
            System.out.println("Before remove List:");
            library.listBooks(BooksTest_Database);
            System.out.print("\n");

            // Arrange
            String title = "The Great Gatsby";
            String author = "F. Scott Fitzgerald";
            String barcode = "12345678"; // Changed to a barcode that doesn't exist
            int newId = library.getBooks().stream().mapToInt(Book::getId).max().orElse(0) + 1;
            Book expectedBook = new Book(newId, title, author, barcode);

            // Prepare the simulated user input
            String input = title + "\n" + author + "\n" + barcode + "\n";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            // Act
            library.addBookToFile(Path_to_Database.database, title, author, barcode); // Changed to call the correct method

            // Load the books from the file
            library.loadBooksFromFile(tempFile.getPath());

            // Assert
            Book actualBook = library.getBooks().get(library.getBooks().size() - 1);
            assertEquals(expectedBook.getId(), actualBook.getId());
            assertEquals(expectedBook.getTitle(), actualBook.getTitle());
            assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
            assertEquals(expectedBook.getBarcode(), actualBook.getBarcode());

            //print list
            System.out.print("\n");
            System.out.println("After remove List:");
            library.listBooks(BooksTest_Database);
        }


        /**
         * Method name: testRemoveBookID
         * This method tests the removeBookID method of the Library class. It removes a book from the library by its ID,
         * then verifies that the book was removed correctly by checking that no book in the library has the removed book's ID.
         * @throws IOException if an I/O error occurs when reading from or writing to the file
         */
        @Test
        void testRemoveBookID() throws IOException {
            //print list
            System.out.println("Before remove List:");
            library.listBooks(BooksTest_Database);
            System.out.print("\n");

            // Get the book to be removed
            Book bookToRemove = library.getBooks().get(0);
            int removedBookId = bookToRemove.getId();

            // Act
            //library.removeBookID(removedBookId, BooksTest_Database);

            // Load the books from the file again
            library.loadBooksFromFile(BooksTest_Database);

            // Assert
            boolean isRemoved = library.getBooks().stream().noneMatch(book -> book.getId() == removedBookId);
            assertTrue(isRemoved, "The book was not removed successfully");


            //print list
            System.out.print("\n");
            System.out.println("After remove List:");
            library.listBooks(BooksTest_Database);
        }

        /**
         * Method name: testRemoveBookBarcode
         * This method tests the removeBookBarcode method of the Library class. It removes a book from the library by its barcode,
         * then verifies that the book was removed correctly by checking that no book in the library has the removed book's barcode.
         * @throws IOException if an I/O error occurs when reading from or writing to the file
         */
        @Test
        void testRemoveBookBarcode() throws IOException {
            // Print list
            System.out.println("Before remove List:");
            library.listBooks(BooksTest_Database);
            System.out.print("\n");

            // Set the barcode of the book to be removed
            String removedBookBarcode = "56789012";

            // Act
            library.removeBookBarcode(removedBookBarcode, true);

            // Load the books from the file again
            library.loadBooksFromFile(BooksTest_Database);

            // Assert
            boolean isRemoved = library.getBooks().stream().noneMatch(book -> book.getBarcode().equals(removedBookBarcode));
            assertTrue(isRemoved, "The book was not removed successfully");


            //print list
            System.out.print("\n");
            System.out.println("After remove List:");
            library.listBooks(BooksTest_Database);
        }

        /**
         * Method name: testRemoveBookByTitle
         * This method tests the removeBookByTitle method of the Library class. It removes a book from the library by its title,
         * then verifies that the book was removed correctly by checking that no book in the library has the removed book's title.
         * @throws IOException if an I/O error occurs when reading from or writing to the file
         */
        @Test
        void testRemoveBookByTitle() throws IOException {
            // Print list
            System.out.println("Before remove List:");
            library.listBooks(BooksTest_Database);
            System.out.print("\n");

            // Get the book to be removed
            String titleToRemove = library.getBooks().get(0).getTitle();

            // Act
            library.removeBookByTitle(titleToRemove);

            // Check if there are no books in the library that have the title titleToRemove.
            // If the result is true, then the book has been successfully removed. If it’s false, then the book is still in the library.
            // this lambda expression (->) takes a book as input and returns a boolean indicating whether the title of the book equals titleToRemove
            boolean isRemoved = library.getBooks().stream().noneMatch(book -> book.getTitle().equals(titleToRemove));

            //Assert
            assertTrue(isRemoved, "The book was not removed successfully");

            //print list
            System.out.print("\n");
            System.out.println("After remove List:");
            library.listBooks(BooksTest_Database);
        }


        /**
         * Method name: testCheckoutBook
         * This method tests the checkoutBook method of the Library class. It checks out a book from the library by its title,
         * then verifies that the book was checked out correctly by checking that the book's check status is 'checkedOut'.
         * @throws IOException if an I/O error occurs when reading from or writing to the file
         */
        @Test
        void testCheckoutBook() throws IOException {
            // Print list
            System.out.println("Before remove List:");
            library.listBooks(BooksTest_Database);
            System.out.print("\n");

            // Get the book to be checked out
            String titleToCheckout = library.getBooks().get(1).getTitle();

            // Act
            //boolean isCheckoutSuccessful = library.checkoutBook(titleToCheckout, BooksTest_Database);

            // Assert
            //assertTrue(isCheckoutSuccessful, "The book was not checked out successfully");

            // Load the books from the file again
            library.loadBooksFromFile(BooksTest_Database);

            // Assert that the book is checked out
            //Find the first book in the library that has the title titleToCheckout.
            // If such a book exists, assign it to checkedOutBook. If no such book exists, assign null to checkedOutBook
            Book checkedOutBook = library.getBooks().stream().filter(book -> book.getTitle().equals(titleToCheckout)).findFirst().orElse(null);
            assertNotNull(checkedOutBook, "The book was not found after checkout");
            assertEquals("checkedOut", checkedOutBook.getCheckStatus(), "The book's check status is not 'checkedOut'");

            //print list
            System.out.print("\n");
            System.out.println("After remove List:");
            library.listBooks(BooksTest_Database);
        }

        /**
         * Method name: testCheckInBook
         * This method tests the checkInBook method of the Library class. It checks in a book to the library by its title,
         * then verifies that the book was checked in correctly by checking that the book's check status is 'checkedIn'.
         * @throws IOException if an I/O error occurs when reading from or writing to the file
         */
        @Test
        void testCheckInBook() throws IOException {
            // Print list
            System.out.println("Before remove List:");
            library.listBooks(BooksTest_Database);
            System.out.print("\n");

            // Get the book to be checked in
            String titleToCheckIn = library.getBooks().get(0).getTitle();

            // Act
            //boolean isCheckInSuccessful = library.checkInBook(titleToCheckIn, BooksTest_Database);

            // Assert
            //assertTrue(isCheckInSuccessful, "The book was not checked in successfully");

            // Load the books from the file again
            library.loadBooksFromFile(BooksTest_Database);

            // Assert that the book is checked in
            //Find the first book in the library that has the title titleToCheckIn.
            // If such a book exists, assign it to checkedInBook. If no such book exists, assign null to checkedInBook
            Book checkedInBook = library.getBooks().stream().filter(book -> book.getTitle().equals(titleToCheckIn)).findFirst().orElse(null);
            assertNotNull(checkedInBook, "The book was not found after check-in");
            assertEquals("checkedIn", checkedInBook.getCheckStatus(), "The book's check status is not 'checkedIn'");

            //print list
            System.out.print("\n");
            System.out.println("After remove List:");
            library.listBooks(BooksTest_Database);
        }

    }
