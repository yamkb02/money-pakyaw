package action_listeners.income_screen;


import dialogs.EditIncomeDialog;
import screens.IncomeScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditIncomeListener implements ActionListener {
    private final JTable table1;
    private final JTable table2;
    private final String username;
    private final Connection connection;
    private final DefaultTableModel tableModel1;
    private final DefaultTableModel tableModel2;
    private final IncomeScreen incomeScreen;
    private PreparedStatement statement;

    // OK, you want to include table1 and table2 as parameters as well
// You can modify the constructor of the EditIncomeListener class as follows:

    public EditIncomeListener(
            String username,
            Connection connection,
            JTable table1,
            JTable table2,
            DefaultTableModel tableModel1,
            DefaultTableModel tableModel2,
            PreparedStatement statement,
            IncomeScreen incomeScreen) {
        this.username = username;
        this.connection = connection;
        this.table1 = table1;
        this.table2 = table2;
        this.tableModel1 = tableModel1;
        this.tableModel2 = tableModel2;
        this.statement = statement;
        this.incomeScreen = incomeScreen;
    }

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
                statement = connection.prepareStatement("SELECT * FROM income_sources WHERE id = ?");
                statement.setString(1, String.valueOf(id));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    double amount = rs.getDouble("amount");
                    Date date = rs.getDate("date");
                    boolean earned = rs.getBoolean("earned");
                    EditIncomeDialog dialog =
                            new EditIncomeDialog(name, amount, date, earned);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        name = dialog.getName();
                        amount = dialog.getAmount();
                        date = dialog.getDate();
                        earned = dialog.isEarned();
                        if (amount < 0) {
                            JOptionPane.showMessageDialog(null, "Invalid or negative input. Not saved.");
                        } else {
                            statement = connection.prepareStatement("UPDATE income_sources SET name = ?, amount = ?, date = ?, earned = ? WHERE id = ?");
                            statement.setString(1, name);
                            statement.setDouble(2, amount);
                            statement.setDate(3, date);
                            statement.setBoolean(4, earned);
                            statement.setInt(5, id);
                            statement.executeUpdate();
                            incomeScreen.populateTable();
                        }
                    } else if (dialog.isDeleted()) {
                        statement = connection.prepareStatement("DELETE FROM income_sources WHERE id = ?");
                        statement.setInt(1, id);
                        statement.executeUpdate();
                        incomeScreen.populateTable();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Not saved.");
                ex.printStackTrace();
            }
        }
    }
}
