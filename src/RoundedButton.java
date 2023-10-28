/*
 * Library Management System
 * Name: Fernanda Frederico Ribeiro da Silva
 * Class: Software Development I CEN-3024C-16046
 * Professor: Walauskis
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * This class represents a custom JButton with a rounded shape.
 * It extends the JButton class and overrides some of its methods to
 * provide a rounded look and feel.
 */

public class RoundedButton extends JButton {

    /**
     * Constructs a new RoundedButton with the specified label.
     *
     * @param label the text of the button
     */
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

    /**
     * Paints the component with a custom background color and shape.
     *
     * @param g the Graphics object to protect
     */
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

    /**
     * Paints the border of the component with a custom color and thickness.
     *
     * @param g the Graphics object to protect
     */
    protected void paintBorder(Graphics g) {
        g.setColor(Color.decode("#ede6ea")); // Set color of button border (same as button to look clean)
        ((Graphics2D) g).setStroke(new BasicStroke(5)); // Set thickness to 5
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
    }

    // Click detection.
    Shape shape;

    /**
     * Determines whether a point is within this component's bounds.
     *
     * @param x the x-coordinate of the point to check
     * @param y the y-coordinate of the point to check
     * @return true if the point is within this component's bounds; false otherwise
     */
    public boolean contains(int x, int y) {
        // this code ensures that whenever you check if a point is within the button,
        // it always uses an up-to-date shape that matches the current size of the button
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}