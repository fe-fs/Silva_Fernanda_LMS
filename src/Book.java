/**
 * Class name: Book
 * This class represents a book with an ID, title, and author.
 */
public class Book {
    private int id;
    private String title;
    private String author;

    /**
     * Constructor for the Book class.
     * Initializes a new book with the given ID, title, and author.
     * @param id The ID of the book.
     * @param title The title of the book.
     * @param author The author of the book.
     */
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }


    /**
     * Method name: getId
     *This getId method returns the value of the id field, allowing other classes to access it.
     *Once you have created this method, you can use it with Collections.sort
     * @return The ID of the book.
     */
    public int getId() {
        return id;
    }
    /**
     * Method name: getTitle
     * This method returns the title of the book.
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method name: getAuthor
     * This method returns the author of the book.
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Method name: toString
     * This method returns a string representation of the book.
     * overridden the toString method to return a string that represents the contents of the Book object in a more readable format.
     * @return A string representation of the book.
     */
    @Override
    public String toString() {
        return "Book: " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'';
    }

}


