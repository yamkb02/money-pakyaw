package action_listeners.savings_screen;

import dialogs.AddSavingsDialog;
import screens.SavingsScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final SavingsScreen savingsScreen;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public AddButtonListener(String username, Connection connection, PreparedStatement statement, SavingsScreen savingsScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.savingsScreen = savingsScreen;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // create a dialog to get the saving details from the user
        AddSavingsDialog dialog = new AddSavingsDialog();
        dialog.setVisible(true);
        // if the user clicked the save button, get the saving details
        if (dialog.isSaved()) {
            double amount = dialog.getAmount();
            java.sql.Date sqlDate = dialog.getDate();

            if (amount + getTotalSavings() < 0) {
                JOptionPane.showMessageDialog(null, "Your savings cannot be negative.");
            } else {
                try {
                    // execute an update to insert a new saving for the current user
                    statement = connection.prepareStatement("INSERT INTO savings (amount, date, username) VALUES (?, ?, ?)");
                    statement.setDouble(1, amount);
                    statement.setDate(2, sqlDate);
                    statement.setString(3, username);
                    statement.executeUpdate();                // refresh the table with the updated data
                    savingsScreen.populateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error in adding savings.");
                }
            }
        }
    }

    private double getTotalSavings() {
        // declare a variable to store the total savings
        double totalSavings = 0.0;

        // create a try-catch block to handle exceptions
        try {
            // create a query string to get the sum of savings amount of a user from the
            String query = "SELECT SUM(amount) AS total_savings FROM savings WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.username);

// execute the query and store the result set
            ResultSet rs = statement.executeQuery();

            // check if the result set has any data
            if (rs.next()) {
                // get the total savings from the result set and assign it to the variable
                totalSavings = rs.getDouble("total_savings");
            }

            // close the result set, statement and connection objects
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            // print the stack trace of the exception
        }

        // return the total savings
        return totalSavings;
    }
}

