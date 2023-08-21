package action_listeners.expenses_screen;

import dialogs.EditExpenseDialog;
import screens.ExpenseScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditExpenseListener implements ActionListener {
    // parameter from the file
    private final Connection connection; // parameter from the file
    private final JTable table1; // parameter from the file
    private final JTable table2; // parameter from the file
    private final DefaultTableModel tableModel1; // parameter from the file
    private final DefaultTableModel tableModel2; // parameter from the file
    private final ExpenseScreen expenseScreen;
    private PreparedStatement statement; // parameter from the file

    // constructor that takes the necessary parameters from the file
    public EditExpenseListener(String username, Connection connection, PreparedStatement statement, JTable table1, JTable table2, DefaultTableModel tableModel1, DefaultTableModel tableModel2, ExpenseScreen expenseScreen) {
        this.connection = connection;
        this.statement = statement;
        this.table1 = table1;
        this.table2 = table2;
        this.tableModel1 = tableModel1;
        this.tableModel2 = tableModel2;
        this.expenseScreen = expenseScreen;
    }

    // override the actionPerformed method with the same logic as the original listener
    @Override
    public void actionPerformed(ActionEvent e) {
        int rowIndex1 = table1.getSelectedRow();
        int rowIndex2 = table2.getSelectedRow();
        int rowIndex = -1;
        DefaultTableModel tableModel = null;
        if (rowIndex1 != -1) {
            rowIndex = rowIndex1;
            tableModel = tableModel1;
        } else if (rowIndex2 != -1) {
            rowIndex = rowIndex2;
            tableModel = tableModel2;
        }
        if (rowIndex != -1) {
            int id = (int) tableModel.getValueAt(rowIndex, 0);
            try {
                statement = connection.prepareStatement("SELECT * FROM expense_categories WHERE id = ?");
                statement.setString(1, String.valueOf(id));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    double amount = rs.getDouble("amount");
                    Date date = rs.getDate("date");
                    boolean paid = rs.getBoolean("paid");
                    EditExpenseDialog dialog = new EditExpenseDialog(name, amount, date, paid);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        name = dialog.getName();
                        amount = dialog.getAmount();
                        date = dialog.getDate();
                        paid = dialog.ispaid();
                        if (amount < 0) {
                            JOptionPane.showMessageDialog(null, "Invalid or negative input. Not saved.");
                        } else {
                            statement = connection.prepareStatement("UPDATE expense_categories SET name = ?, amount = ?, date = ?, paid = ? WHERE id = ?");
                            statement.setString(1, name);
                            statement.setDouble(2, amount);
                            statement.setDate(3, date);
                            statement.setBoolean(4, paid);
                            statement.setInt(5, id);
                            statement.executeUpdate();
                            expenseScreen.populateTable();
                        }
                    } else if (dialog.isDeleted()) {
                        statement = connection.prepareStatement("DELETE FROM expense_categories WHERE id = ?");
                        statement.setInt(1, id);
                        statement.executeUpdate();
                        expenseScreen.populateTable();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Not saved.");
                ex.printStackTrace();
            }
        }
    }
}