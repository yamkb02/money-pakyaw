package action_listeners.transaction_screen;

import dialogs.EditTransactionDialog;
import screens.TransactionScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditButtonActionListener implements ActionListener {

    private final TransactionScreen transactionScreen;
    private final Connection connection;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private PreparedStatement statement;

    // constructor that takes the transactionScreen, connection, statement, table and tableModel as parameters
    public EditButtonActionListener(TransactionScreen transactionScreen, Connection connection, PreparedStatement statement, JTable table, DefaultTableModel tableModel) {
        this.transactionScreen = transactionScreen;
        // assign the parameters to the fields
        this.connection = connection;
        this.statement = statement;
        this.table = table;
        this.tableModel = tableModel;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the selected row index 
        int rowIndex = table.getSelectedRow();
        // if a row is selected, get the transaction id from the table model
        if (rowIndex != -1) {
            int id = (int) tableModel.getValueAt(rowIndex, 0);
            try {
                // execute a query to get the transaction details for the given id
                statement = connection.prepareStatement("SELECT * FROM transactions WHERE id = ?");
                // pass the id parameter
                statement.setInt(1, id);
                // use rs to store the result set
                ResultSet rs = statement.executeQuery();
                // if the query returns a result, get the transaction details
                if (rs.next()) {
                    String name = rs.getString("name");
                    double amount = rs.getDouble("amount");
                    Date date = rs.getDate("date");
                    // create a dialog to edit the transaction details by the user
                    EditTransactionDialog dialog = new EditTransactionDialog(name, amount, date);
                    dialog.setVisible(true);
                    // if the user clicked the save button, get the updated transaction details
                    if (dialog.isSaved()) {
                        name = dialog.getName();
                        amount = 0.00;
                        try {
                            amount = dialog.getAmount();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Amount inputted is invalid. Please edit.");
                        }
                        date = dialog.getDate();
                        // execute an update to modify the transaction for the given id
                        statement = connection.prepareStatement("UPDATE transactions SET name = ?, amount = ?, date = ? WHERE id = ?");
                        statement.setString(1, name);
                        statement.setDouble(2, amount);
                        statement.setDate(3, date);
                        statement.setInt(4, id);
                        statement.executeUpdate();                        // refresh the table with the updated data
                        transactionScreen.populateTable();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in editing the transaction.");
            }
        }
    }
}