import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @Test
    void loadBooksFromFileTest() throws IOException {
        // Create a temporary file
        File tempFile = null;
        try {
            tempFile = Files.createTempFile("test_books", ".txt").toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write some known data to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
            writer.println("1,Pride and Prejudice,Jane Austen,23456789,2023-10-26");
            writer.println("2,War and Peace,Leo Tolstoy,56789012,null");
        }

        // Load the books from the file
        Library library = new Library();
        library.loadBooksFromFile(tempFile.getPath());

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
        Library library = new Library();
        String title = "The Great Gatsby";
        String author = "F. Scott Fitzgerald";
        String barcode = "23456789";
        int newId = library.getBooks().stream().mapToInt(Book::getId).max().orElse(0) + 1;
        Book expectedBook = new Book(newId, title, author, barcode);

        // Create a temporary file for the test
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        String Books_Database = tempFile.getPath();

        // Prepare the simulated user input
        String input = title + "\n" + author + "\n" + barcode + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        library.addBookToFile(Books_Database);

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

        // Clean up the temporary file
        Files.delete(tempFile.toPath());
    }

/*
    @Test
    void removeBook() {
    }

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