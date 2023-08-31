import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBooksFromFile(String Books_Database) {
        try {
            Scanner scanner = new Scanner(new File(Books_Database));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bookInfo = line.split(",");

                int id = Integer.parseInt(bookInfo[0]);
                String title = bookInfo[1];
                String author = bookInfo[2];
                books.add(new Book(id, title, author));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + Books_Database);
        }

        Collections.sort(books, Comparator.comparingInt(Book::getId));
    }

    public void listBooks(){
        for(Book book:books){
            System.out.println(book);
        }
    }

}
