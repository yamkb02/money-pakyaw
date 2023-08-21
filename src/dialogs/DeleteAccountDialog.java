package dialogs;

import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAccountDialog extends JDialog {
    // The GUI components
    private final JPasswordField passwordField;
    private final JButton confirmButton;
    private final JButton cancelButton;

    // The flag to indicate whether the user clicked the confirm button or not
    private boolean confirmed;

    // The constructor that takes no arguments
    public DeleteAccountDialog() {
        // Call the super constructor with a title and a modal flag
        super((Dialog) null, "Delete Account", true);

        // Initialize the GUI components
        passwordField = new JPasswordField();
        confirmButton = new ResponsiveButton("Confirm Delete");
        cancelButton = new ResponsiveButton("Cancel");

        // Add action listeners to the buttons
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDelete();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDelete();
            }
        });

        // Create a label for the password field
        JLabel passwordLabel = new CustomJLabel("Please enter your password:");

        // Create a panel for the label and password field using a grid layout
        JPanel passwordPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Create a panel for the buttons using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // Set the layout of this dialog to a border layout
        this.setLayout(new BorderLayout());

        // Add the panels to this dialog using border layout constraints
        this.add(passwordPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and location of this dialog
        this.setSize(700, 100);
        this.setLocationRelativeTo(null);

        // Set the confirmed flag to false initially
        confirmed = false;
    }

    // A method to get the password from the password field
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // A method to get the confirmed flag value
    public boolean isConfirmed() {
        return confirmed;
    }

    // A method to confirm the deletion and close this dialog
    private void confirmDelete() {
        // Set the confirmed flag to true
        confirmed = true;

        // Dispose this dialog
        this.dispose();
    }

    // A method to cancel the deletion and close this dialog
    private void cancelDelete() {
        // Set the confirmed flag to false
        confirmed = false;

        // Dispose this dialog
        this.dispose();
    }
}
