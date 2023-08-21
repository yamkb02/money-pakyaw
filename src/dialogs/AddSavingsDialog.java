package dialogs;

import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class AddSavingsDialog extends JDialog {
    // The GUI components
    private final JLabel amountLabel;
    private final JLabel dateLabel;
    private final JTextField amountField;
    private final JTextField dateField;
    private final JButton saveButton;
    private final JButton cancelButton;
    // A flag to indicate whether the user clicked the save button or not
    protected boolean saved;

    // The constructor that creates the dialog
    public AddSavingsDialog() {
        // Set the dialog title, size, and modality
        setTitle("Add Savings");
        setSize(700, 150);
        setLocationRelativeTo(null);
        setModal(true);
        // Initialize the GUI components
        amountLabel = new CustomJLabel("Amount:");
        dateLabel = new CustomJLabel("Date:");
        amountField = new JTextField(20);
        dateField = new JTextField(20);
        saveButton = new ResponsiveButton("Save");
        cancelButton = new ResponsiveButton("Cancel");
        // Add action listeners to the buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = true;
                dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the saved flag to false and dispose the dialog
                saved = false;
                dispose();
            }
        });
        // Create a panel for the labels and fields using a grid layout
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        // Create a panel for the buttons using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        // Add the panels to this dialog using a border layout
        this.setLayout(new BorderLayout());
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        // Set the saved flag to false initially
        saved = false;
    }

    // A method to check whether the user clicked the save button or not
    public boolean isSaved() {
        return saved;
    }

    // A method to get the amount of the saving from the text field
    public double getAmount() {
        double amount = 0.00;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Amount inputted is invalid. Please edit.");
        }
        return amount;
    }

    // A method to get the date of the saving from the text field
    public java.sql.Date getDate() {
        java.sql.Date sqlDate;
        try {
            // Create a SimpleDateFormat object with the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // Parse the text field value as a java.util.Date object
            java.util.Date utilDate = sdf.parse(dateField.getText());
            // Convert the java.util.Date object to a java.sql.Date object
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Date format invalid or left blank. Used date today, instead.");
            LocalDate today = LocalDate.now(); // current date in default timezone
            sqlDate = java.sql.Date.valueOf(today); // convert to java.sql.Date

        }
        // Return the sql date
        return sqlDate;
    }
}