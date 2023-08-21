package action_listeners.profile_screen;

import dialogs.UpdateProfileDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateButtonListener implements ActionListener {

    private final String username;
    private final JLabel nameLabel;
    private final JLabel ageLabel;
    private final JLabel themeLabel;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final Connection connection;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public UpdateButtonListener(String username, JLabel nameLabel, JLabel ageLabel, JLabel themeLabel, JTable table, DefaultTableModel tableModel, Connection connection, PreparedStatement statement) {
        this.username = username;
        this.nameLabel = nameLabel;
        this.ageLabel = ageLabel;
        this.themeLabel = themeLabel;
        this.table = table;
        this.tableModel = tableModel;
        this.connection = connection;
        this.statement = statement;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // create a dialog to get the updated user details from the user
        UpdateProfileDialog dialog = new UpdateProfileDialog(username);
        dialog.setVisible(true);
        // if the user clicked the save button, get the updated user details
        if (dialog.isSaved()) {
            try {
                String password = dialog.getPassword();
                String name = dialog.getName();
                int age = dialog.getAge();
                // create a prepared statement to update the user account for the current username
                // use executeUpdate() instead of executeQuery() for update statements
                statement = connection.prepareStatement("UPDATE user_account SET password = ?, name = ?, age = ? WHERE username = ?");
                // set the parameters for the prepared statement
                statement.setString(1, password);
                statement.setString(2, name);
                statement.setInt(3, age);
                statement.setString(4, username);
                // execute the update statement
                statement.executeUpdate();
                // refresh the labels and table with the updated data
                nameLabel.setText("Name: " + name);
                ageLabel.setText("Age: " + age);
                populateTable();
            } catch (Exception f) {
                f.printStackTrace();
                JOptionPane.showMessageDialog(null, "Some inputs were invalid. Please try again.");
            }
        }
    }

    // a method to populate the table with data from the database
    private void populateTable() {
        try {
            // clear the table model
            tableModel.setRowCount(0);
            // execute a query to get all themes for the current user
            statement = connection.prepareStatement("SELECT * FROM theme WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            // loop through the result set and add rows to the table model
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String color = rs.getString("color");
                tableModel.addRow(new Object[]{id, name, color});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}