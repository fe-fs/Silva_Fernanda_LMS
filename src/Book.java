public class Book {
    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    //This getId method returns the value of the id field, allowing other classes to access it.
    // Once you have created this method, you can use it with Collections.sort
    public int getId() {
        return id;
    }

    //overridden the toString method to return a string that represents the contents of the Book object in a more readable format.
    @Override
    public String toString() {
        return "Book: " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'';
    }
}


