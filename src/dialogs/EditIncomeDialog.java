package dialogs;

import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class EditIncomeDialog extends JDialog {
    private final JLabel nameLabel;
    private final JLabel amountLabel;
    private final JLabel dateLabel;
    private final JTextField nameField;
    private final JTextField amountField;
    private final JTextField dateField;
    private final JCheckBox earnedCheckBox;
    private final JButton saveButton;
    private final JButton cancelButton;
    private final JButton deleteButton;
    private boolean saved;
    private boolean deleted;

    public EditIncomeDialog(String name, double amount, Date date, boolean earned) {
        setTitle("Edit Income");
        setMinimumSize(new Dimension(700, 200));
        setLocationRelativeTo(null);
        setModal(true);

        nameLabel = new CustomJLabel("Name:");
        amountLabel = new CustomJLabel("Amount:");
        dateLabel = new CustomJLabel("Date:");
        nameField = new JTextField(20);
        amountField = new JTextField(20);
        dateField = new JTextField(20);
        earnedCheckBox = new JCheckBox("Earned");
        saveButton = new ResponsiveButton("Save");
        cancelButton = new ResponsiveButton("Cancel");
        deleteButton = new ResponsiveButton("Delete");

        nameField.setText(name);
        amountField.setText(String.valueOf(amount));
        dateField.setText(date.toString());
        earnedCheckBox.setSelected(earned);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleted = true;
                dispose();
            }
        });

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
                saved = false;
                dispose();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(earnedCheckBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        this.setLayout(new BorderLayout());
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        saved = false;
        deleted = false;
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getName() {
        return nameField.getText();
    }

    public double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Invalid input. Please edit.");
            return 0;
        }
    }

    public Date getDate() {
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

    public boolean isEarned() {
        return earnedCheckBox.isSelected();
    }
}
