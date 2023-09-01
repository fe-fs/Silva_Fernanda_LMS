import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void loadBooksFromFile(String Books_Database) throws IOException {
        // clear any existing books before loading new books from the text file.
        books.clear();

        // Load new books from the text file
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
            System.out.println("File Loaded Successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + Books_Database + "\n ---- Check if path for Books_Database.txt has changed! ---- ");
        }

        Collections.sort(books, Comparator.comparingInt(Book::getId));
    }

    public void addBookToFile(String Books_Database) throws IOException {
        // Backup the original database
        backupDatabase(Path_to_Database.database, Path_to_Database.databaseBackup);

        // Load existing books from the text file
        List<Book> books = new ArrayList<>();
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
            System.out.println("File not found " + Books_Database + "\n ---- Check if path for Books_Database.txt has changed! ---- ");
        }

        // Generate a new ID by checking the .txt file for existing ID numbers and continuing from the last ID saved on it
        int newId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;

        // Prompt the user to enter the name of the book and the author
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter the title of the book: ");
        String title = inputScanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = inputScanner.nextLine();

        // Add new book to list
        books.add(new Book(newId, title, author));

        // Save updated list of books to text file
        try (PrintWriter writer = new PrintWriter(new FileWriter(Books_Database))) {
            for (Book book : books) {
                writer.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor());
            }
            System.out.println("Book added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file " + Books_Database);
        }
    }

    public void removeBook(int id) throws IOException {
        // Backup the original database
        backupDatabase(Path_to_Database.database, Path_to_Database.databaseBackup);

        int index = binarySearch(id, 0, books.size()-1);
        if (index !=-1){
            System.out.println("Book ID!" + id + " removed!");
            books.remove(index);

            // Update the original database
            updateDatabase(Path_to_Database.database);
        } else {
            System.out.println("Book ID: " + id +  " not found");
        }
    }

    //create a backup from the last loaded file before update the database content
    public void backupDatabase(String originalFile, String backupFile) throws IOException {
        Path src = Paths.get(originalFile);
        Path dest = Paths.get(backupFile);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    //save changes from the software to the database text file
    public void updateDatabase(String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (Book book : books) {
                out.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor());
            }
        }
    }

    private int binarySearch(int id, int left, int right){
        if(left > right){
            return -1;
        }
        int mid = left + (right - left)/2;
        if (books.get(mid).getId()==id){
            return mid;
        }else if(books.get(mid).getId()<id){
            return binarySearch(id, mid+1, right);
        }else{
            return binarySearch(id,left,mid-1);
        }
    }

    public void listBooks(){
        System.out.println("\n---------All Books in Database----------");
        for(Book book:books){
            System.out.println(book);
        }
    }

}
