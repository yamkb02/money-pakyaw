package dialogs;

import database_connector.Connector;
import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateProfileDialog extends JDialog {
    // The GUI components
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField nameField;
    private final JTextField ageField;
    private final JButton saveButton;
    private final JButton cancelButton;

    // The flag to indicate whether the user clicked the save button or not
    private boolean saved;

    // The constructor that receives a username string
    public UpdateProfileDialog(String username) {
        // Call the super constructor with a title and a modal flag
        super((Dialog) null, "Update Profile", true);

        // Initialize the GUI components
        usernameField = new JTextField(username);
        usernameField.setEditable(false);
        passwordField = new JPasswordField();
        nameField = new JTextField();
        ageField = new JTextField();
        saveButton = new ResponsiveButton("Save");
        cancelButton = new ResponsiveButton("Cancel");

        // Add action listeners to the buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelProfile();
            }
        });

        // Create labels for the components
        JLabel usernameLabel = new CustomJLabel("Username:");
        JLabel passwordLabel = new CustomJLabel("Password:");
        JLabel nameLabel = new CustomJLabel("Name:");
        JLabel ageLabel = new CustomJLabel("Age:");

        // Create a panel for the form using a grid layout
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);

        // Create a panel for the buttons using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Set the layout of this dialog to a border layout
        this.setLayout(new BorderLayout());

        // Add the panels to this dialog using border layout constraints
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and location of this dialog
        this.setSize(700, 200);
        this.setLocationRelativeTo(null);

        // Get the existing user details from the database using the username parameter
        try {
            // Create a prepared statement with a parameterized query
            PreparedStatement statement = Connector.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM user_account WHERE username = ?");
            // Set the username parameter to the value of the username variable
            statement.setString(1, username);
            // Execute the query and get the result set
            ResultSet rs = statement.executeQuery();
            // If the query returns a result, get the user details and set the
            // fields[^2^][2]
            if (rs.next()) {
                String password = rs.getString("password");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                passwordField.setText(password);
                nameField.setText(name);
                ageField.setText(String.valueOf(age));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the saved flag to false initially
        saved = false;
    }

    // A method to get the password from the password field
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // A method to get the name from the name field
    public String getName() {
        return nameField.getText();
    }

    // A method to get the age from the age field as an integer
    public int getAge() {
        return Integer.parseInt(ageField.getText());
    }

    // A method to get the saved flag value
    public boolean isSaved() {
        return saved;
    }

    // A method to save the profile and close this dialog
    private void saveProfile() {
        // Set the saved flag to true
        saved = true;

        // Dispose this dialog
        this.dispose();
    }

    // A method to cancel the profile and close this dialog
    private void cancelProfile() {
        // Set the saved flag to false
        saved = false;

        // Dispose this dialog
        this.dispose();
    }
}