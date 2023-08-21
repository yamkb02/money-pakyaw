package action_listeners.profile_screen;

import dialogs.EditThemeDialog;
import screens.ProfileScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EditButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final ProfileScreen profileScreen;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public EditButtonListener(String username, Connection connection, PreparedStatement statement, JTable table, DefaultTableModel tableModel, ProfileScreen profileScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.table = table;
        this.tableModel = tableModel;
        this.profileScreen = profileScreen;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the selected row index from the table
        int rowIndex = table.getSelectedRow();
        // if a row is selected, get the theme id and color from the table model
        if (rowIndex != -1) {
            int id = (int) tableModel.getValueAt(rowIndex, 0);
            String name = (String) tableModel.getValueAt(rowIndex, 1);
            String color = (String) tableModel.getValueAt(rowIndex, 2);
            // create a dialog to edit the color value by the user
            EditThemeDialog dialog = new EditThemeDialog(name, color);
            dialog.setVisible(true);
            // if the user clicked the save button, get the updated color value
            if (dialog.isSaved()) {
                name = dialog.getName();
                color = dialog.getColor();
                try {
                    // execute an update to modify the theme for the given id
                    statement = connection.prepareStatement("UPDATE theme SET name = ?, color = ? WHERE id = ?");
                    statement.setString(1, name);
                    statement.setString(2, color);
                    statement.setInt(3, id);
                    statement.executeUpdate();                    // refresh the table with the updated data
                    profileScreen.populateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error in editing theme.");
                }
            }
            // if the user clicked the delete button, delete the theme from the database and the table
            if (dialog.isDeleted()) {
                try {
                    // execute an update to delete the theme for the given id
                    statement = connection.prepareStatement("DELETE FROM theme WHERE id = " + id);
                    statement.executeUpdate();
                    // refresh the table with the updated data
                    profileScreen.populateTable();
                } catch (Exception f) {
                    f.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error in deleting theme.");
                }
            }
        }
    }
}