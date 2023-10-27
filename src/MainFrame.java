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
        RoundedButton buttonRemoveBookID = new RoundedButton("Remove Book by ID");
        buttonRemoveBookID.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the buttonListBooks square
        buttonRemoveBookID.setMaximumSize(new Dimension(120, 120));
        buttonRemoveBookID.setFont(new Font("Arial", Font.BOLD, 15)); // Set the font to Arial, bold, and 18pt
        buttonRemoveBookID.setForeground(Color.decode("#b8a9b3"));
        buttonRemoveBookID.setBackground(Color.decode("#ede6ea"));
        buttonRemoveBookID.setBorder(BorderFactory.createEmptyBorder());
        buttonPanel.add(buttonRemoveBookID); // Add the buttonListBooks to the buttonListBooks panel

        panel.add(buttonPanel); // Add the buttonListBooks panel to the main panel
        frame.getContentPane().add(panel);
        frame.setVisible(true);



        // Update the action listener for your buttonListBooks
        buttonListBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookList = library.listBooks(Path_to_Database.database);
                bookListArea.setText(bookList);
            }
        });

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

        buttonRemoveBookID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialog for the book ID
                String idStr = JOptionPane.showInputDialog(frame, "Enter the ID of the book to remove:");
                int id = Integer.parseInt(idStr);

                // Call the removeBookID method
                try {
                    String result = library.removeBookID(id, Path_to_Database.database);
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


    }

    // Getter method for your button
    public RoundedButton getButton() {
        return button;
    }

}