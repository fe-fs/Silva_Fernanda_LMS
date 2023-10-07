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
    private String checkStatus;
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

    /**
     * Constructor name: Book
     * This constructor creates a new Book object with the given title. The book is not checked out by default.
     *
     * @param title The title of the book.
     */
    public Book(String title) {
        this.title = title;
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
     * Method name: setDueDate
     * This method sets the due date of the book. If the due date is not null, it also marks the book as checked out.
     *
     * @param dueDate The due date to be set for the book.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        if (dueDate != null) {
            this.checkStatus = "checkedOut";
        }
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
                ", checkStatus=" + checkStatus +
                ", dueDate=" + (dueDate != null ? dueDate : "null");
    }


    public void setId(int id) {
        this.id = id;
    }

    //checkIn and Out

    /**
     * Sets the check status of the book.
     * If the book is checked out, the check status is set to "checkedOut".
     * If the book is checked in, the check status is set to "checkedIn".
     *
     * @param checkedOut A boolean indicating whether the book is checked out.
     */
    public void setCheckedOut(boolean checkedOut) {
        if (checkedOut) {
            this.checkStatus = "checkedOut";
        } else {
            this.checkStatus = "checkedIn";
        }
    }

    /**
     * Checks if the book is checked out.
     *
     * @return A boolean indicating whether the book is checked out.
     */
    public boolean isCheckedOut() {
        return "checkedOut".equals(this.checkStatus);
    }

    /**
     * Gets the check status of the book.
     *
     * @return A string representing the check status of the book.
     */
    public String getCheckStatus() {
        return this.checkStatus;
    }

    /**
     * Sets the check status of the book.
     *
     * @param checkStatus A string representing the check status of the book.
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * Gets the due date of the book.
     *
     * @return A LocalDate representing the due date of the book.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }
}


