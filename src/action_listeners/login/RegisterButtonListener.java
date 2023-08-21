package action_listeners.login;

import screens.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

// create a class named RegisterButtonListener that implements ActionListener
public class RegisterButtonListener implements ActionListener {

    private final JPasswordField passwordField;
    // declare private fields for the connection, name field, username field, password field, age field and balance field
    private final Connection connection;
    private final JTextField nameField;
    private final JTextField usernameField;
    private final JTextField ageField;
    private final JTextField balanceField;
    private final CardLayout cardLayout;
    private final Login loginFrame;

    // create a constructor that takes the connection and the text fields as parameters
    public RegisterButtonListener(Connection connection, JTextField nameField, JTextField usernameField,
                                  JPasswordField passwordField, JTextField ageField, JTextField balanceField, CardLayout cardLayout, Login loginFrame) {
        // assign the parameters to the fields
        this.connection = connection;
        this.nameField = nameField;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.ageField = ageField;
        this.balanceField = balanceField;
        this.cardLayout = cardLayout;
        this.loginFrame = loginFrame;
    }


    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the name, username and password from the text fields
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // get the age from the text field
        String age = ageField.getText();
        // get the balance from the text field
        String balance = balanceField.getText();
        // check if the name, username and password are not empty
        if (!(name.isEmpty() || username.isEmpty() || password.isEmpty() || balance.isEmpty() || !balance.matches("\\d+(\\.\\d{1,2})?"))) {
                try {
                    // create a prepared statement to insert into the user account table with an
                    // additional parameter for balance
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO user_account (username, password, name, age, balance) VALUES (?, ?, ?, ?, ?)");
                    // set the parameters of the prepared statement including balance
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, name);
                    statement.setString(4, age);
                    statement.setString(5, balance);
                    // execute the update and get the number of affected rows
                    int rows = statement.executeUpdate();
                    // check if the update was successful
                    if (rows > 0) {
                        // user account was inserted
                        // insert a new row into the theme table with the desired color and username
                        // values
                        statement = connection.prepareStatement("INSERT INTO theme (color, username) VALUES (?, ?)");
                        statement.setString(1, "#41A87E"); // replace with desired color value
                        statement.setString(2, username);
                        statement.executeUpdate();
                        // show a success message and switch to the login panel
                        JOptionPane.showMessageDialog(null, "You have registered successfully.");
                        cardLayout.show(loginFrame.getContentPane(), "Login");
                    }
                } catch (Exception ex) {
                    // handle any exception
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Duplicate username or some inputs were invalid. Not saved.");
                }
        } else {
            // name, username or password is empty, show an error message
            JOptionPane.showMessageDialog(null, "Some inputs were invalid. Not saved.");
        }
    }
}