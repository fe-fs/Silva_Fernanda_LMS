import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MainFrame {
    public static void main(String[] args) {
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
        title.setForeground(Color.decode("#ede6ea")); // Set color to #ede6ea
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        // Create an empty label to act as a spacer
        JLabel spacer1 = new JLabel();
        spacer1.setPreferredSize(new Dimension(0, 20)); // Set preferred height to 100 pixels
        panel.add(spacer1);

        // Create a panel for the buttons with a FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Set horizontal gap to 20 pixels
        buttonPanel.setBackground(Color.decode("#dbd0d6")); // Set background color to #dbd0d6
        buttonPanel.setBorder(BorderFactory.createEmptyBorder());

        for (int i = 0; i < 8; i++) {
            RoundedButton button = new RoundedButton("Button " + (i + 1));
            button.setPreferredSize(new Dimension(120, 120)); // Set width and height to 200 to make the button square
            button.setMaximumSize(new Dimension(120, 120));
            button.setBackground(Color.decode("#ede6ea"));
            button.setBorder(BorderFactory.createEmptyBorder());
            buttonPanel.add(button); // Add the button to the button panel
        }

        panel.add(buttonPanel); // Add the button panel to the main panel

       /* // Create a panel for the larger buttons with a FlowLayout
        JPanel largeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 40)); // Set horizontal gap to 20 pixels
        largeButtonPanel.setBackground(Color.decode("#dbd0d6")); // Set background color to #dbd0d6
        largeButtonPanel.setBorder(BorderFactory.createEmptyBorder());

        for (int i = 0; i < 2; i++) {
            RoundedButton button = new RoundedButton("Button " + (i + 1));
            button.setPreferredSize(new Dimension(450, 50));
            button.setMaximumSize(new Dimension(450, 50));
            button.setBackground(Color.decode("#ede6ea"));
            button.setBorder(BorderFactory.createEmptyBorder());
            largeButtonPanel.add(button); // Add the button to the large button panel
        }

        panel.add(largeButtonPanel); // Add the large button panel to the main panel

        */
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);

            // These statements enlarge the button so that it
            // becomes a circle rather than an oval.
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);

            // This call causes the JButton not to paint the background.
            // This allows us to paint a round background.
            setContentAreaFilled(false);
        }

        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                // You might want to make the highlight color
                // a property of the RoundedButton class.
                g.setColor(Color.decode("#ede6ea"));
            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);

            // This call will paint the label and the focus rectangle.
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(Color.decode("#ede6ea")); // Set color to #ede6ea
            ((Graphics2D) g).setStroke(new BasicStroke(5)); // Set thickness to 5
            g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
        }

        // Hit detection.
        Shape shape;

        public boolean contains(int x, int y) {
            // If the button has changed size, make a new shape object.
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
            }
            return shape.contains(x, y);
        }
    }
}