package action_listeners.profile_screen;

import dialogs.DeleteAccountDialog;
import screens.Login;
import screens.Mainframe;
import screens.ProfileScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteButtonListener implements ActionListener {

    private final String username;
    private final Connection connection;
    private final ProfileScreen profileScreen;
    private PreparedStatement statement;

    // constructor that takes the username and other parameters as needed
    public DeleteButtonListener(String username, Connection connection, PreparedStatement statement, ProfileScreen profileScreen) {
        this.username = username;
        this.connection = connection;
        this.statement = statement;
        this.profileScreen = profileScreen;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // create a dialog to confirm the deletion of the user account by the user
        DeleteAccountDialog dialog = new DeleteAccountDialog();
        dialog.setMinimumSize(new Dimension(700, 150));
        dialog.setVisible(true);
        // if the user clicked the confirm button, get the password from the user
        if (dialog.isConfirmed()) {
            String password = dialog.getPassword();
            try {
                // execute a query to get the password for the current username
                statement = connection.prepareStatement("SELECT password FROM user_account WHERE username = ?");
                statement.setString(1, username);
                ResultSet rs = statement.executeQuery();
                // if the query returns a result, get the password from the result set
                if (rs.next()) {
                    String correctPassword = rs.getString("password");
                    // check if the password entered by the user matches the correct password
                    if (password.equals(correctPassword)) {
                        // execute an update to delete the user account for the current username
                        // Assume conn is an active connection
                        String[] tables = {"expense_categories", "income_sources", "savings", "theme", "transactions", "user_account"};
                        String username = this.username; // Replace with the username you want to delete
                        try {
                            for (String table : tables) {
                                String sql = "DELETE FROM " + table + " WHERE username = ?";
                                statement = connection.prepareStatement(sql);
                                statement.setString(1, username);
                                statement.executeUpdate();
                            }
                        } catch (SQLException f) {
                            f.printStackTrace();
                        }
                        // dispose this panel and close the database connection and statement
                        dialog.dispose();
                        // show a message that the account has been deleted
                        JOptionPane.showMessageDialog(null, "Your account has been deleted.");
                        Mainframe frame = (Mainframe) profileScreen.getParent().getParent().getParent().getParent().getParent();
                        frame.dispose();
                        // exit the app
                        new Login();
                    } else {
                        // show a message that the password is incorrect
                        JOptionPane.showMessageDialog(null, "Incorrect password.");
                    }
                }
            } catch (Exception f) {
                f.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in deleting account.");
            }
        }
    }
}
