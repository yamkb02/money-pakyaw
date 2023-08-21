package action_listeners.transaction_screen;

import screens.TransactionScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteButtonActionListener implements ActionListener {

    private final TransactionScreen transactionScreen;
    private final Connection connection;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private PreparedStatement statement;

    // constructor that takes the transactionScreen, connection, statement, table and tableModel as parameters
    public DeleteButtonActionListener(TransactionScreen transactionScreen, Connection connection, PreparedStatement statement, JTable table, DefaultTableModel tableModel) {
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
                // execute an update to delete the transaction for the given id
                statement = connection.prepareStatement("DELETE FROM transactions WHERE id = ?");
                statement.setInt(1, id);
                statement.executeUpdate();
                // refresh the table with the updated data
                transactionScreen.populateTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in deleting the transaction.");
            }
        }
    }
}
