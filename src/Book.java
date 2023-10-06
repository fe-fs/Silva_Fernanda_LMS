/*
 * Library Management System
 * Name: Fernanda Frederico Ribeiro da Silva
 * Class: Software Development I CEN-3024C-16046
 * Professor: Walauskis
 */

import java.time.LocalDate;

/**
 * Class name: Book
 * This class represents a book with an ID, title, and author, barcode with 8 digits, checkIn and checkOut booleans and dueDate.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String barcode;
    private boolean checkedOut;
    private LocalDate dueDate;

    /**
     * Constructor for the Book class.
     * Initializes a new book with the given ID, title, and author.
     *
     * @param id      The ID of the book.
     * @param title   The title of the book.
     * @param author  The author of the book.
     * @param barcode The barcode of the book.
     */
    public Book(int id, String title, String author, String barcode) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.barcode = barcode;
    }

    //for checkOut by title
    public Book(String title) {
        this.title = title;
        this.checkedOut = false;
    }

    /**
     * Method name: getId
     * This getId method returns the value of the id field, allowing other classes to access it.
     * Once you have created this method, you can use it with Collections.sort
     *
     * @return The ID of the book.
     */
    public int getId() {
        return id;
    }

    /**
     * Method name: getTitle
     * This method returns the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method name: getAuthor
     * This method returns the author of the book.
     *
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    public String getBarcode() {
        return barcode;
    }

    /**
     * Method name: toString
     * This method returns a string representation of the book.
     * overridden the toString method to return a string that represents the contents of the Book object in a more readable format.
     *
     * @return A string representation of the book.
     */
    @Override
    public String toString() {
        return "Book: " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", barcode=" + barcode +
                ", checkedOut=" + checkedOut +
                ", dueDate=" + (dueDate != null ? dueDate : "null");
    }


    public void setId(int i) {
    }


    //checkIn and Out
    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    /**DueDate
     *
     * @param dueDate
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        if (dueDate != null) {
            this.checkedOut = true;
        }
    }

}


