import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundedButton extends JButton {
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