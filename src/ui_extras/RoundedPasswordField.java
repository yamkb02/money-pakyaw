package ui_extras;

import javax.swing.*;
import java.awt.*;

public class RoundedPasswordField extends JPasswordField {

    // constructor that takes one parameter int columns
    public RoundedPasswordField(int columns) {
        super(columns); // call the superclass constructor
        setOpaque(false); // make the component non-opaque
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // create some padding around the component
    }

    // override the paintComponent method to draw a rounded green border and a white
    // background
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // cast to Graphics2D for better rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // enable
        // anti-aliasing
        g2d.setColor(Color.WHITE); // set the background color to white
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // fill a rounded rectangle with the background color
        g2d.setColor(Color.GREEN); // set the border color to green
        g2d.setStroke(new BasicStroke(3)); // set the border thickness to 3 pixels
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10); // draw a rounded rectangle with the border
        // color
        super.paintComponent(g); // call the superclass method to paint the text and cursor
    }
}
