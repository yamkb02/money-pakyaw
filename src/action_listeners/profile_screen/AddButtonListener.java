package action_listeners.profile_screen;

import dialogs.AddThemeDialog;
import screens.ProfileScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final ProfileScreen profileScreen;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public AddButtonListener(String username, Connection connection, PreparedStatement statement, ProfileScreen profileScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.profileScreen = profileScreen;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // create a dialog to get the color value from the user
        AddThemeDialog dialog = new AddThemeDialog();
        dialog.setVisible(true);
        // if the user clicked the save button, get the color value
        if (dialog.isSaved()) {
            String name = dialog.getName();
            String color = dialog.getColor();
            try {
                // execute an update to insert a new theme for the current user
                statement = connection.prepareStatement("INSERT INTO theme (name, color, username) VALUES (?, ?, ?)");
                statement.setString(1, name);
                statement.setString(2, color);
                statement.setString(3, username);
                statement.executeUpdate();                // refresh the table with the updated data
                profileScreen.populateTable();
            } catch (Exception f) {
                f.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in adding theme.");
            }
        }
    }
}
