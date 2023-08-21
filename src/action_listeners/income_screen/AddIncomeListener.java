package action_listeners.income_screen;

import dialogs.AddIncomeDialog;
import screens.IncomeScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;


public class AddIncomeListener implements ActionListener {
    private final String username;
    private final Connection connection;
    private final IncomeScreen incomeScreen;
    private PreparedStatement statement;

    public AddIncomeListener(
            String username,
            Connection connection,
            PreparedStatement statement, IncomeScreen incomeScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.incomeScreen = incomeScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a dialog to get the income details from the user
        AddIncomeDialog dialog = new AddIncomeDialog();
        dialog.setVisible(true);
        // If the user clicked the save button, get the income details
        if (dialog.isSaved()) {
            String name = dialog.getName();
            double amount = dialog.getAmount();
            Date date = dialog.getDate();
            boolean earned = dialog.isEarned();
            if (amount < 0) {
                JOptionPane.showMessageDialog(null, "Invalid or negative input. Not saved.");
            } else {
                try {
                    // Execute an update to insert a new income for the current user
                    statement = connection.prepareStatement("INSERT INTO income_sources (name, amount, date, username, earned) VALUES (?, ?, ?, ?, ?)");
                    statement.setString(1, name);
                    statement.setDouble(2, amount);
                    statement.setDate(3, date);
                    statement.setString(4, username);
                    statement.setBoolean(5, earned);
                    statement.executeUpdate();
                    incomeScreen.populateTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
