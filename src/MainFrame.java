import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame {
    private RoundedButton button;
    private TextComponent titleTextField;
    private TextComponent authorTextField;
    private TextComponent barcodeTextField;

    public void setupGUI(Library library) throws IOException {
        JFrame frame = new JFrame("Custom GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#dbd0d6"));
        frame.setSize(600, 600); // Set the size of the JFrame

        // Create a margin between the panel and the frame
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); // Set layout to BoxLayout
        panel.setBackground(Color.decode("#dbd0d6"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setMaximumSize(new Dimension(500, 500));

        JLabel title = new JLabel("Library Management System");
        title.setFont(new Font("Arial", Font.BOLD, 32)); // Set font to Arial and size to 32
        title.setForeground(Color.decode("#b8a9b3")); // Set color to #ede6ea
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        // Create an empty label to act as a spacer
        JLabel spacer1 = new JLabel();
        spacer1.setPreferredSize(new Dimension(0, 20)); // Set preferred height to 100 pixels
        panel.add(spacer1);

        // Create a JTextArea for the book list
        JTextArea bookListArea = new JTextArea(5, 10);
        bookListArea.setEditable(false);  // Make it read-only
        bookListArea.setOpaque(false);    // Make it non-opaque
        // Set the font to Arial, bold, and 18pt
        bookListArea.setFont(new Font("Arial", Font.BOLD, 15));
        // Set the font color to white
        bookListArea.setForeground(Color.WHITE);
        // Set the margin to 5
        bookListArea.setMargin(new Insets(5, 5, 5, 5));

        // Create a JScrollPane with no border and pink background
        JScrollPane scrollPane = new JScrollPane(bookListArea);
        scrollPane.setBackground(Color.decode("#b8a9b3"));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane);


        // Create a panel for the buttons with a FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Set horizontal gap to 20 pixels
        buttonPanel.setBackground(Color.decode("#dbd0d6")); // Set background color to #dbd0d6
        buttonPanel.setBorder(BorderFactory.createEmptyBorder());

        //Buttons for the GUI

        //Button1
        RoundedButton buttonAddBooks = new RoundedButton("Add Books");
        buttonAddBooks.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonAddBooks.setMaximumSize(new Dimension(120, 120));
        buttonAddBooks.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font to Arial, bold, and 18pt
        buttonAddBooks.setForeground(Color.decode("#b8a9b3"));
        buttonAddBooks.setBackground(Color.decode("#ede6ea"));
        buttonAddBooks.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonAddBooks); // Add the buttonListBooks to the buttonListBooks panel

        //Button2
        RoundedButton buttonListBooks = new RoundedButton("List Books");
        buttonListBooks.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonListBooks.setMaximumSize(new Dimension(120, 120));
        buttonListBooks.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font to Arial, bold, and 18pt
        buttonListBooks.setForeground(Color.decode("#b8a9b3"));
        buttonListBooks.setBackground(Color.decode("#ede6ea"));
        buttonListBooks.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonListBooks); // Add the buttonListBooks to the buttonListBooks panel

        //Button3
        RoundedButton buttonRemoveBookBarcode = new RoundedButton("<html>Remove Book<br>ID or Barcode</html>");
        buttonRemoveBookBarcode.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonRemoveBookBarcode.setMaximumSize(new Dimension(120, 120));
        buttonRemoveBookBarcode.setFont(new Font("Arial", Font.BOLD, 15)); // Set the font to Arial, bold, and 18pt
        buttonRemoveBookBarcode.setForeground(Color.decode("#b8a9b3"));
        buttonRemoveBookBarcode.setBackground(Color.decode("#ede6ea"));
        buttonRemoveBookBarcode.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonRemoveBookBarcode); // Add the buttonListBooks to the buttonListBooks panel

        //Button4
        RoundedButton buttonRemoveBookTitle = new RoundedButton("<html>Remove Book<br>by Title</html>");
        buttonRemoveBookTitle.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonRemoveBookTitle.setMaximumSize(new Dimension(120, 120));
        buttonRemoveBookTitle.setFont(new Font("Arial", Font.BOLD, 15)); // Set the font to Arial, bold, and 18pt
        buttonRemoveBookTitle.setForeground(Color.decode("#b8a9b3"));
        buttonRemoveBookTitle.setBackground(Color.decode("#ede6ea"));
        buttonRemoveBookTitle.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonRemoveBookTitle); // Add the buttonListBooks to the buttonListBooks panel

        //Button5
        RoundedButton buttonCheckoutBook = new RoundedButton("<html>Checkout<br>Book</html>");
        buttonCheckoutBook.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonCheckoutBook.setMaximumSize(new Dimension(120, 120));
        buttonCheckoutBook.setFont(new Font("Arial", Font.BOLD, 15)); // Set the font to Arial, bold, and 18pt
        buttonCheckoutBook.setForeground(Color.decode("#b8a9b3"));
        buttonCheckoutBook.setBackground(Color.decode("#ede6ea"));
        buttonCheckoutBook.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonCheckoutBook); // Add the buttonListBooks to the buttonListBooks panel

        //Button6
        RoundedButton buttonCheckInBook = new RoundedButton("<html>Checkout<br>Book</html>");
        buttonCheckInBook.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonCheckInBook.setMaximumSize(new Dimension(120, 120));
        buttonCheckInBook.setFont(new Font("Arial", Font.BOLD, 15)); // Set the font to Arial, bold, and 18pt
        buttonCheckInBook.setForeground(Color.decode("#b8a9b3"));
        buttonCheckInBook.setBackground(Color.decode("#ede6ea"));
        buttonCheckInBook.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonCheckInBook); // Add the buttonListBooks to the buttonListBooks panel

        panel.add(buttonPanel); // Add the buttonListBooks panel to the main panel
        frame.getContentPane().add(panel);
        frame.setVisible(true);



        // Action Listeners to connect each button to the correct methods

        //Button1
        buttonListBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookList = library.listBooks(Path_to_Database.database);
                bookListArea.setText(bookList);
            }
        });

        //Button2
        buttonAddBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialogs for the book details
                String title = JOptionPane.showInputDialog(frame, "Enter the title of the book:");
                String author = JOptionPane.showInputDialog(frame, "Enter the author of the book:");
                String barcode;
                while (true) {
                    barcode = JOptionPane.showInputDialog(frame, "Enter a barcode sequence of 8 characters without spaces or special characters:");
                    if (!barcode.matches("[\\w]{8}")) { // Checks if barcode is exactly 8 alphanumeric characters
                        JOptionPane.showMessageDialog(frame, "Invalid barcode. Please try again.");
                    } else {
                        break;
                    }
                }

                // Call the addBookToFile method
                try {
                    library.addBookToFile(Path_to_Database.database, title, author, barcode);
                    library.loadBooksFromFile(Path_to_Database.database);
                    library.listBooks(Path_to_Database.database);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Update the book list in your GUI
                String books = library.listBooks(Path_to_Database.database);
                bookListArea.setText(books);
            }
        });

        //Button3
        buttonRemoveBookBarcode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialog for the book ID or barcode
                String identifier = JOptionPane.showInputDialog(frame, "Enter the ID or barcode of the book to remove:");

                // Determine whether the identifier is a barcode or an ID
                boolean isBarcode = identifier.matches("[\\w]{8}");

                // Call the removeBookBarcode method
                try {
                    String result = library.removeBookBarcode(identifier, isBarcode);
                    // Show message dialog with the result
                    JOptionPane.showMessageDialog(frame, result);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Update the book list in your GUI
                String books = library.listBooks(Path_to_Database.database);
                bookListArea.setText(books);
            }
        });

        //Button4
        buttonRemoveBookTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialog for the book title
                String title = JOptionPane.showInputDialog(frame, "Enter the title of the book to remove:");

                // Call the removeBookByTitle method
                String result = library.removeBookByTitle(title);
                // Show message dialog with the result
                JOptionPane.showMessageDialog(frame, result);

                // Update the book list in your GUI
                String books = library.listBooks(Path_to_Database.database);
                bookListArea.setText(books);
            }
        });

        //Button5
        buttonCheckoutBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialog for the book title
                String title = JOptionPane.showInputDialog(frame, "Enter the title of the book to checkout:");

                // Call the checkoutBook method
                String result = library.checkoutBook(title, Path_to_Database.database);
                // Show message dialog with the result
                JOptionPane.showMessageDialog(frame, result);

                // Update the book list in your GUI
                String books = library.listBooks(Path_to_Database.database);
                bookListArea.setText(books);
            }
        });

        //Button6
        buttonCheckInBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialog for the book title
                String title = JOptionPane.showInputDialog(frame, "Enter the title of the book to checkIn:");

                // Call the checkoutBook method
                String result = null;
                try {
                    result = library.checkInBook(title, Path_to_Database.database);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // Show message dialog with the result
                JOptionPane.showMessageDialog(frame, result);

                // Update the book list in your GUI
                String books = library.listBooks(Path_to_Database.database);
                bookListArea.setText(books);
            }
        });
    }

    // Getter method for your button
    public RoundedButton getButton() {
        return button;
    }

}