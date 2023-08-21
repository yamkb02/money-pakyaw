package action_listeners.login;

import screens.Mainframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginButtonListener implements ActionListener {
    // declare a connection object for database
    private final Connection connection;
    // declare text fields for username and password
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    // declare a label for message
    private final JLabel messageLabel;
    private final JFrame loginFrame;
    // declare a prepared statement object
    private PreparedStatement statement;

    public LoginButtonListener(JFrame loginFrame, Connection connection, JTextField usernameField, JPasswordField passwordField, JLabel messageLabel) {
        // assign the parameters to the fields
        this.loginFrame = loginFrame;
        this.connection = connection;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.messageLabel = messageLabel;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the username and password from the text fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // check if the username and password are not empty
        if (!username.isEmpty() && !password.isEmpty()) {
            // try to connect to the database and verify the credentials
            try {
                // create a prepared statement to query the user account table
                statement = connection.prepareStatement("SELECT * FROM user_account WHERE username = ? AND password = ?");
                // set the parameters of the prepared statement
                statement.setString(1, username);
                statement.setString(2, password);
                // execute the query and get the result set
                ResultSet rs = statement.executeQuery();
                // check if the result set has any row
                if (rs.next()) {
                    new Mainframe(username);
                    loginFrame.dispose();
                } else {
                    // credentials are invalid, show an error message
                    messageLabel.setText("Invalid username or password.");
                    messageLabel.setForeground(Color.RED);
                }
                // close the result set, prepared statement and connection
                rs.close();
            } catch (Exception ex) {
                // handle any exception
                ex.printStackTrace();
            }
        } else {
            // username or password is empty, show an error message
            messageLabel.setText("Please enter username and password.");
            messageLabel.setForeground(Color.RED);
        }
    }
}