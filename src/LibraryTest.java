import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    File tempFile;
    Library library;
    String BooksTest_Database;

    //Override Backup from original methods to not interfere with test
    class TestableLibrary extends Library {
        @Override
        public void backupDatabase(String Books_Database, String databaseBackup) {
            // Do nothing
        }


        @Override
        public void updateDatabase(String filename) throws IOException {
            super.updateDatabase(tempFile.getPath());
        }

    }

        //setUp method creates a new temporary file and loads some books into the library before each test.
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


   /* @AfterEach
    void tearDown() throws IOException {
        Files.delete(tempFile.toPath());
    }*/


        @Test
        void loadBooksFromFileTest() {
            // Verify that the books were loaded correctly
            assertEquals(2, library.getBooks().size());

            //print list
            List<Book> books = library.getBooks();
            for (Book book : books) {
                System.out.println(book);
            }
        }


        @Test
        void testAddBookToFile() throws IOException {
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
            library.addBookToFile(tempFile.getPath()); // Changed to call the correct method

            // Load the books from the file
            library.loadBooksFromFile(tempFile.getPath());

            // Assert
            Book actualBook = library.getBooks().get(library.getBooks().size() - 1);
            assertEquals(expectedBook.getId(), actualBook.getId());
            assertEquals(expectedBook.getTitle(), actualBook.getTitle());
            assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
            assertEquals(expectedBook.getBarcode(), actualBook.getBarcode());

            //print list
            List<Book> books = library.getBooks();
            for (Book book : books) {
                System.out.println(book);
            }
        }


        /**
         * testRemoveBookID
         * this test is essentially comparing the state of the library before and after the removeBook method is called.
         * Before the method is called, there is a book with ID removedBookId in the library
         * After the method is called, there should be no books with that ID in the library.
         * If there are still books with that ID in the library, then the test will fail.
         *
         * @throws IOException
         */
        @Test
        void testRemoveBookID() throws IOException {
            //print list
            List<Book> books = library.getBooks();
            System.out.println("Before remove List:");
            for (Book book : books) {
                System.out.println(book);
            }
            System.out.print("\n");

            // Get the book to be removed
            Book bookToRemove = library.getBooks().get(0);
            int removedBookId = bookToRemove.getId();

            // Act
            library.removeBookID(removedBookId, tempFile.getPath());

            // Load the books from the file again
            library.loadBooksFromFile(tempFile.getPath());

            // Assert
            boolean isRemoved = library.getBooks().stream().noneMatch(book -> book.getId() == removedBookId);
            assertTrue(isRemoved, "The book was not removed successfully");

            System.out.print("\n");
            //print list
            System.out.println("After remove List:");
            for (Book book : books) {
                System.out.println(book);
            }
        }

        @Test
        void testRemoveBookBarcode() throws IOException {
            // Print list
            List<Book> books = library.getBooks();
            System.out.println("Before remove List:");
            for (Book book : books) {
                System.out.println(book);
            }
            System.out.print("\n");

            // Set the barcode of the book to be removed
            String removedBookBarcode = "56789012";

            // Act
            library.removeBookBarcode(removedBookBarcode, true);

            // Load the books from the file again
            library.loadBooksFromFile(tempFile.getPath());

            // Assert
            boolean isRemoved = library.getBooks().stream().noneMatch(book -> book.getBarcode().equals(removedBookBarcode));
            assertTrue(isRemoved, "The book was not removed successfully");

            System.out.print("\n");
            // Print list
            System.out.println("After remove List:");
            for (Book book : books) {
                System.out.println(book);
            }
        }



/*
    @Test
    void removeBookBarcode() {
    }

    @Test
    void removeBookByTitle() {
    }

    @Test
    void checkoutBook() {
    }

    @Test
    void checkInBook() {
    }

*/
    }
