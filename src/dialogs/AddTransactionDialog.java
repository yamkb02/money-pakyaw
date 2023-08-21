package dialogs;

import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

// A dialog to get the transaction details from the user
public class AddTransactionDialog extends JDialog {

    // The GUI components
    private final JLabel nameLabel;
    private final JLabel amountLabel;
    private final JLabel dateLabel;
    private final JTextField nameField;
    private final JTextField amountField;
    private final JTextField dateField;
    private final JLabel typeLabel;
    private final JRadioButton earnedButton;
    private final JRadioButton spentButton;
    private final ButtonGroup typeGroup;
    private final JButton saveButton;
    private final JButton cancelButton;

    // A flag to indicate whether the user clicked the save button or not
    private boolean saved;

    // The constructor that creates the dialog
    public AddTransactionDialog() {
        // Set the dialog title, size, and modality
        setTitle("Add Transaction");
        setSize(700, 500);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setModal(true);

        // Initialize the GUI components
        nameLabel = new CustomJLabel("Name:");
        amountLabel = new CustomJLabel("Amount:");
        dateLabel = new CustomJLabel("Date:");
        nameField = new JTextField(20);
        amountField = new JTextField(20);
        dateField = new JTextField(20);
        typeLabel = new CustomJLabel("Type:");
        earnedButton = new JRadioButton("Earned");
        spentButton = new JRadioButton("Spent");
        typeGroup = new ButtonGroup();

        typeGroup.add(earnedButton);
        typeGroup.add(spentButton);

        saveButton = new ResponsiveButton("Save");
        cancelButton = new ResponsiveButton("Cancel");

        // Add action listeners to the buttons

        earnedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the amount to positive if earned is selected
                amountField.setText(String.valueOf(Math.abs(getAmount())));
            }
        });

        spentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the amount to negative if spent is selected
                amountField.setText(String.valueOf(-Math.abs(getAmount())));
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the saved flag to true and dispose the dialog
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
        JPanel inputPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
        inputPanel.setLayout(boxLayout);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(typeLabel);
        inputPanel.add(earnedButton);
        inputPanel.add(spentButton);

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

    // A method to get the name of the transaction from the text field
    public String getName() {
        return nameField.getText();
    }

    // A method to get the amount of the transaction from the text field
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

    // A method to get the date of the transaction from the text field
    public java.sql.Date getDate() {
        LocalDate today = LocalDate.now(); // current date in default timezone
        java.sql.Date sqlDate = java.sql.Date.valueOf(today); // convert to java.sql.Date
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
        }
        return sqlDate;
    }
}