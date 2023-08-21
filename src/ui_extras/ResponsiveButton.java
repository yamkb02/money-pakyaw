package ui_extras;
// Import statements

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Responsive button class
public class ResponsiveButton extends JButton {

    public static Color color = Color.green;

    // Constructor 1: accepts a string parameter for the label and a color object
    // parameter for the background color
    public ResponsiveButton(String label) {
        // Call the super constructor with the label
        super(label);

        this.setFont(getFont().deriveFont(20f)); // set the font size to 20

        // Set the background color
        this.setBackground(color);
        this.setBorderPainted(false);

        // Add a mouse listener to change the color on hover
        this.addMouseListener(new MouseAdapter() {
            // When the mouse enters the button, change the background color to grey
            public void mouseEntered(MouseEvent e) {
                ResponsiveButton.this.setBackground(Color.GRAY);
            }

            // When the mouse exits the button, reset the background color
            public void mouseExited(MouseEvent e) {
                ResponsiveButton.this.setBackground(color);
            }
        });
    }

    public ResponsiveButton(String label, String image) {
        this(label);
        setMinimumSize(new Dimension(240, 30));
        setIcon(new ImageIcon(image));
        setBorderPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
    }
}