package action_listeners.expenses_screen;

import dialogs.AddExpenseDialog;
import screens.ExpenseScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class AddExpenseListener implements ActionListener {
    private final String username; // parameter from the file
    private final Connection connection; // parameter from the file
    private final ExpenseScreen expenseScreen;
    private PreparedStatement statement; // parameter from the file

    // constructor that takes the necessary parameters from the file
    public AddExpenseListener(String username, Connection connection, PreparedStatement statement, ExpenseScreen expenseScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.expenseScreen = expenseScreen;
    }

    // override the actionPerformed method with the same logic as the original listener
    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a dialog to get the expense details from the user
        AddExpenseDialog dialog = new AddExpenseDialog();
        dialog.setVisible(true);
        // If the user clicked the save button, get the expense details
        if (dialog.isSaved()) {
            String name = dialog.getName();
            double amount = dialog.getAmount();
            Date date = dialog.getDate();
            boolean paid = dialog.ispaid();
            if (amount < 0) {
                JOptionPane.showMessageDialog(null, "Invalid or negative input. Not saved.");
            } else {
                try {
                    // Execute an update to insert a new expense for the current user
                    statement = connection.prepareStatement("INSERT INTO expense_categories (name, amount, date, username, paid) VALUES (?, ?, ?, ?, ?)");
                    statement.setString(1, name);
                    statement.setDouble(2, amount);
                    statement.setDate(3, date);
                    statement.setString(4, username);
                    statement.setBoolean(5, paid);
                    statement.executeUpdate();
                    // Refresh the table with the updated data
                    expenseScreen.populateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Not saved.");
                    ex.printStackTrace();
                }
            }
        }
    }
}
