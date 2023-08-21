package action_listeners.savings_screen;

import dialogs.EditSavingsDialog;
import screens.SavingsScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final SavingsScreen savingsScreen;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public EditButtonListener(String username, Connection connection, PreparedStatement statement, JTable table, DefaultTableModel tableModel, SavingsScreen savingsScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.table = table;
        this.tableModel = tableModel;
        this.savingsScreen = savingsScreen;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the selected row index from the table
        int rowIndex = table.getSelectedRow();
        // if a row is selected, get the saving id from the table model
        if (rowIndex != -1) {
            int id = (int) tableModel.getValueAt(rowIndex, 0);
            try {
                // execute a query to get the saving details for the given id
                statement = connection.prepareStatement("SELECT * FROM savings WHERE id = ?");
                statement.setString(1, String.valueOf(id));
                ResultSet rs = statement.executeQuery();
                // if the query returns a result, get the saving details
                if (rs.next()) {
                    double amount = rs.getDouble("amount");
                    Date date = rs.getDate("date");
                    // create a dialog to edit the saving details by the user
                    EditSavingsDialog dialog = new EditSavingsDialog(amount, date);
                    dialog.setVisible(true);
                    // if the user clicked the save button, get the updated saving details
                    if (dialog.isSaved()) {
                        amount = dialog.getAmount();
                        date = dialog.getDate();

                        if (amount + getTotalSavings() < 0) {
                            JOptionPane.showMessageDialog(null, "Your savings cannot be negative.");
                        } else {
                            // execute an update to modify the saving for the given id
                            statement = connection.prepareStatement("UPDATE savings SET amount = ?, date = ? WHERE id = ?");
                            statement.setDouble(1, amount);
                            statement.setDate(2, date);
                            statement.setInt(3, id);
                            statement.executeUpdate();                        // refresh the table with the updated data
                            savingsScreen.populateTable();
                        }
                    }
                }
            } catch (Exception f) {
                f.printStackTrace();
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
