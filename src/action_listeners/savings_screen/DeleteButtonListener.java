package action_listeners.savings_screen;

import screens.SavingsScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final SavingsScreen savingsScreen;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public DeleteButtonListener(String username, Connection connection, PreparedStatement statement, JTable table, DefaultTableModel tableModel, SavingsScreen savingsScreen) {
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
                // execute an update to delete the saving for the given id
                statement = connection.prepareStatement("DELETE FROM savings WHERE id = ?");
                statement.setInt(1, id);
                statement.executeUpdate();                // refresh the table with the updated data
                savingsScreen.populateTable();
            } catch (Exception f) {
                f.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in deleting savings.");
            }
        }
    }
}
