package action_listeners.transaction_screen;

import dialogs.AddTransactionDialog;
import screens.TransactionScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddButtonActionListener implements ActionListener {

    private final TransactionScreen transactionScreen;
    private final Connection connection;
    private final String username;
    private PreparedStatement statement;

    // constructor that takes the transactionScreen, connection, statement and username as parameters
    public AddButtonActionListener(TransactionScreen transactionScreen, Connection connection, PreparedStatement statement, String username) {
        this.transactionScreen = transactionScreen;
        // assign the parameters to the fields
        this.connection = connection;
        this.statement = statement;
        this.username = username;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // create a dialog to get the transaction details from the user
        AddTransactionDialog dialog = new AddTransactionDialog();
        dialog.setVisible(true);
        // if the user clicked the save button, get the transaction details
        if (dialog.isSaved()) {
            String name = dialog.getName();
            double amount = dialog.getAmount();
            java.sql.Date sqlDate = dialog.getDate();
            try {
                // execute an update to insert a new transaction for the current user
                statement = connection.prepareStatement("INSERT INTO transactions (name, amount, date, username) VALUES (?, ?, ?, ?)");

                // Set the values for the parameters
                statement.setString(1, name); // name is a String variable
                statement.setDouble(2, amount); // amount is a double variable
                statement.setDate(3, sqlDate); // sqlDate is a java.sql.Date variable
                statement.setString(4, username); // username is a String variable

                // Execute the query
                statement.executeUpdate();                // refresh the table with the updated data
                transactionScreen.populateTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in adding the transaction.");
            }
        }
    }
}