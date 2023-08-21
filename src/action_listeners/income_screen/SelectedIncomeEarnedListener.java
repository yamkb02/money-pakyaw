package action_listeners.income_screen;

import screens.IncomeScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class SelectedIncomeEarnedListener implements ActionListener {
    private final String username;
    private final Connection connection;
    private final JTable table1;
    private final JTable table2;
    private final DefaultTableModel tableModel1;
    private final DefaultTableModel tableModel2;
    private final IncomeScreen incomeScreen;
    private PreparedStatement statement;

    public SelectedIncomeEarnedListener(
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
        try {
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
                statement = connection.prepareStatement("SELECT * FROM income_sources WHERE id = ?");
                statement.setString(1, String.valueOf(id));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    double amount = rs.getDouble("amount");
                    boolean earned = rs.getBoolean("earned");
                    // Get the value of the earned column
                    if (!earned) {
                        // Check if the income is earned
                        Date date = Date.valueOf(LocalDate.now());
                        earned = true;
                        statement = connection.prepareStatement("INSERT INTO income_sources (name, amount, date, username, earned) VALUES (?, ?, ?, ?, ?)");
                        statement.setString(1, name);
                        statement.setDouble(2, amount);
                        statement.setDate(3, date);
                        statement.setString(4, username);
                        statement.setBoolean(5, earned);
                        statement.executeUpdate();
                        incomeScreen.populateTable();
                    } else {
                        JOptionPane.showMessageDialog(
                                null, "Invalid, you have chosen an earned income. Please choose from income sources.");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
