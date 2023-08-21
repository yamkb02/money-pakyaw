package ui_extras;

import javax.swing.*;
import java.awt.*;

public class CustomJLabel2 extends JLabel {

    // A constructor that takes a string and an alignment and calls the super constructor
    public CustomJLabel2(String text) {
        super(text);
        // Set the font size to 20
        setFont(getFont().deriveFont(40f));
        setForeground(Color.decode("#41A87E"));
    }

    public CustomJLabel2() {
        super();
        // Set the font size to 20
        setFont(getFont().deriveFont(40f));
        setForeground(Color.decode("#41A87E"));
    }

    public CustomJLabel2(ImageIcon icon) {
        super(icon);
        // Set the font size to 20
        setFont(getFont().deriveFont(40f));
        setForeground(Color.decode("#41A87E"));
    }
}
