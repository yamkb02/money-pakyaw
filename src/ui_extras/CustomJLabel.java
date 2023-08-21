package ui_extras;

import javax.swing.*;

public class CustomJLabel extends JLabel {

    // A constructor that takes a string and an alignment and calls the super constructor
    public CustomJLabel(String text) {
        super(text);
        // Set the font size to 20
        setFont(getFont().deriveFont(20f));
    }

    public CustomJLabel() {
        super();
        // Set the font size to 20
        setFont(getFont().deriveFont(20f));
    }

    public CustomJLabel(ImageIcon icon) {
        super(icon);
        // Set the font size to 20
        setFont(getFont().deriveFont(20f));
    }
}
