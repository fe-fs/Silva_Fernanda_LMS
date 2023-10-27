import javax.swing.*;
import java.awt.*;

public class RoundedTextArea extends JTextArea {
    public RoundedTextArea(int rows, int columns) {
        super(rows, columns);
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.WHITE);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}