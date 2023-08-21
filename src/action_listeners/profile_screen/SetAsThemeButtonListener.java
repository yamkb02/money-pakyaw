package action_listeners.profile_screen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SetAsThemeButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final JLabel themeLabel;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public SetAsThemeButtonListener(String username, Connection connection, PreparedStatement statement, JLabel themeLabel, JTable table, DefaultTableModel tableModel) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.themeLabel = themeLabel;
        this.table = table;
        this.tableModel = tableModel;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // get the selected row index from the table
            int rowIndex = table.getSelectedRow();
            // get the color value from the table model using the row index
            String color = (String) tableModel.getValueAt(rowIndex, 2);
            // create a prepared statement to update the profile table with the theme value
            statement = connection.prepareStatement("UPDATE user_account SET theme_in_use = ? WHERE username = ?");
            // set the parameters for the prepared statement
            statement.setString(1, color);
            statement.setString(2, username);
            // execute the update statement
            statement.executeUpdate();
            // refresh the theme label with the updated data
            themeLabel.setText("Theme: " + color);
        } catch (Exception f) {
            f.printStackTrace();
            JOptionPane.showMessageDialog(null, "No theme selected.");
        }
    }
}