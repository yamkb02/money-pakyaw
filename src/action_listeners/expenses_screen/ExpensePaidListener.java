package action_listeners.expenses_screen;

import screens.ExpenseScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpensePaidListener implements ActionListener {
    private final String username; // parameter from the file
    private final Connection connection; // parameter from the file
    private final ExpenseScreen expenseScreen;
    private PreparedStatement statement; // parameter from the file

    // constructor that takes the necessary parameters from the file
    public ExpensePaidListener(String username, Connection connection, PreparedStatement statement, ExpenseScreen expenseScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.expenseScreen = expenseScreen;
    }

    // override the actionPerformed method with the same logic as the original listener
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            List<Expense> expenses = new ArrayList<>();
            statement = connection.prepareStatement("SELECT * FROM expense_categories WHERE username = ? AND paid = ?");
            statement.setString(1, username);
            statement.setBoolean(2, false);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                expenses.add(new Expense(name, amount));
            }
            rs.close();
            for (Expense expense : expenses) {
                Date date = Date.valueOf(LocalDate.now());
                boolean paid = true;
                statement = connection.prepareStatement("INSERT INTO expense_categories (name, amount, date, username, paid) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, expense.name);
                statement.setDouble(2, expense.amount);
                statement.setDate(3, date);
                statement.setString(4, username);
                statement.setBoolean(5, paid);
                statement.executeUpdate();
            }
            expenseScreen.populateTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Not saved.");
            ex.printStackTrace();
        }
    }
}
