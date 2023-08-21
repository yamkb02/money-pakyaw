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
import java.util.ArrayList;
import java.util.List;

public class IncomeReceivedListener implements ActionListener {
    private final String username;
    private final Connection connection;
    private final JTable table1;
    private final JTable table2;
    private final DefaultTableModel tableModel1;
    private final DefaultTableModel tableModel2;
    private final IncomeScreen incomeScreen;
    private PreparedStatement statement;


    public IncomeReceivedListener(
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
            List<Income> incomes = new ArrayList<>();
            statement = connection.prepareStatement("SELECT * FROM income_sources WHERE username = ? AND earned = ?");
            statement.setString(1, username);
            statement.setBoolean(2, false);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                incomes.add(new Income(name, amount));
            }
            rs.close();
            for (Income income : incomes) {
                Date date = Date.valueOf(LocalDate.now());
                boolean earned = true;
                statement = connection.prepareStatement("INSERT INTO income_sources (name, amount, date, username, earned) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, income.name);
                statement.setDouble(2, income.amount);
                statement.setDate(3, date);
                statement.setString(4, username);
                statement.setBoolean(5, earned);
                statement.executeUpdate();
            }
            incomeScreen.populateTable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}