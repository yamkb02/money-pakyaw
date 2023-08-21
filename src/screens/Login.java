package screens;

// import necessary packages

import action_listeners.login.BackButtonListener;
import action_listeners.login.LoginButtonListener;
import action_listeners.login.RegisterButtonListener;
import action_listeners.login.SignupButtonListener;
import database_connector.Connector;
import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;
import ui_extras.RoundedPasswordField;
import ui_extras.RoundedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

// create login class that extends JFrame
public class Login extends JFrame {

    public static int width;
    public static int height;
    // create text fields for username and password
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    // create card layout to switch between panels
    private final CardLayout cardLayout;
    private final JTextField ageField = new RoundedTextField(20);
    private final JTextField balanceField = new RoundedTextField(20);
    // create connection object for database
    private final Connection connection;
    private final JLabel messageLabel;
    private PreparedStatement statement;

    // create constructor
    public Login() {
        // set title, size, location and default close operation
        setTitle("Money Pakyaw");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); // get the default screen device
        width = gd.getDisplayMode().getWidth(); // get the width of the screen
        height = gd.getDisplayMode().getHeight(); // get the height of the screen
        // scale the image icon to fit the frame

        // create an image icon from the file path
        ImageIcon logoIcon = new ImageIcon("res/logo.png");
        // get the image from the image icon
        Image logoImage = logoIcon.getImage();
        // set the icon of the title bar to the logo image
        setIconImage(logoImage);

        setSize(Login.width, Login.height);
        setMinimumSize(new Dimension(Login.width, Login.height - 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        connection = Connector.getInstance().getConnection();

        // initialize panels
        // create panels for splash screen, login and signup
        JPanel splashPanel = new JPanel();
        JPanel loginPanel = new JPanel();
        JPanel signupPanel = new JPanel();

        // initialize labels
        // create a JLabel object with the ImageIcon object as the parameter
        ImageIcon signInBg = new ImageIcon("res/signInBg.png");
        Image scaledImage = signInBg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // set the new image icon to the label
        JLabel loginBgLabel = new JLabel();
        loginBgLabel.setIcon(new ImageIcon(scaledImage));

        // create a label with the image icon
        // create labels for logo, username, password and messages
        JLabel logoLabel = new JLabel(new ImageIcon("res/splashBg.png"));
        JLabel usernameLabel = new CustomJLabel("Username:");
        JLabel passwordLabel = new CustomJLabel("Password:");
        messageLabel = new CustomJLabel("");

        // initialize text fields
        usernameField = new RoundedTextField(20);
        passwordField = new RoundedPasswordField(20);

        // initialize buttons
        // create buttons for login and signup
        JButton loginButton = new ResponsiveButton("Login");
        JButton signupButton = new ResponsiveButton("No account? Sign up");
        // create a back button for the signup panel
        JButton backButton = new ResponsiveButton("Back");

        // initialize card layout and set it to the frame
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // add logo label to splash panel
        splashPanel.setLayout(new GridLayout(1, 1));
        splashPanel.add(logoLabel);

        // set layout of login panel to border layout
        loginPanel.setLayout(new BorderLayout());
        // add loginBgLabel to login panel at the CENTER position
        loginPanel.add(loginBgLabel, BorderLayout.CENTER);
        // set layout of loginBgLabel to grid bag layout
        loginBgLabel.setLayout(new GridBagLayout());

        // create grid bag constraints object
        GridBagConstraints gbc = new GridBagConstraints();

        // set insets for components
        gbc.insets = new Insets(10, 10, 10, 10);

        // add username label to loginBgLabel at (0, 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginBgLabel.add(usernameLabel, gbc);

        // add username field to loginBgLabel at (1, 0)
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginBgLabel.add(usernameField, gbc);

        // add password label to loginBgLabel at (0, 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginBgLabel.add(passwordLabel, gbc);

        // add password field to loginBgLabel at (1, 1)
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginBgLabel.add(passwordField, gbc);

        // add login button to loginBgLabel at (0, 2)
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginBgLabel.add(loginButton, gbc);

        // add signup button to loginBgLabel at (1, 2)
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginBgLabel.add(signupButton, gbc);

        // add message label to loginBgLabel at (0, 3) with grid width of 2
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginBgLabel.add(messageLabel, gbc);

        // create a JLabel object with the ImageIcon object as the parameter
        ImageIcon signUpBg = new ImageIcon("res/signUpBg.png");
        scaledImage = signUpBg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // set the new image icon to the label
        JLabel signupBgLabel = new JLabel();
        signupBgLabel.setIcon(new ImageIcon(scaledImage));

        // set layout of login panel to border layout
        signupPanel.setLayout(new BorderLayout());
        // add loginBgLabel to login panel at the CENTER position
        signupPanel.add(signupBgLabel, BorderLayout.CENTER);
        // set layout of loginBgLabel to grid bag layout
        signupBgLabel.setLayout(new GridBagLayout());

        // create labels, text fields and button for signup panel
        JLabel nameLabel = new CustomJLabel("Name:");
        JLabel usernameLabel2 = new CustomJLabel("Username:");
        JLabel passwordLabel2 = new CustomJLabel("Password:");
        RoundedTextField nameField = new RoundedTextField(20);
        RoundedTextField usernameField2 = new RoundedTextField(20);
        RoundedPasswordField passwordField2 = new RoundedPasswordField(20);
        ResponsiveButton registerButton = new ResponsiveButton("Register");

        // add name label to signup panel at (0, 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        signupBgLabel.add(nameLabel, gbc);

        // add name field to signup panel at (1, 0)
        gbc.gridx = 3;
        gbc.gridy = 0;
        signupBgLabel.add(nameField, gbc);

        // add username label to signup panel at (0, 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        signupBgLabel.add(usernameLabel2, gbc);

        // add username field to signup panel at (1, 1)
        gbc.gridx = 3;
        gbc.gridy = 1;
        signupBgLabel.add(usernameField2, gbc);

        // add password label to signup panel at (0, 2)
        gbc.gridx = 0;
        gbc.gridy = 2;
        signupBgLabel.add(passwordLabel2, gbc);

        // add password field to signup panel at (1, 2)
        gbc.gridx = 3;
        gbc.gridy = 2;
        signupBgLabel.add(passwordField2, gbc);

        // add age label to signup panel at (0, 4)
        gbc.gridx = 0;
        gbc.gridy = 3;
        // create label and text field for age
        JLabel ageLabel = new CustomJLabel("Age:");
        signupBgLabel.add(ageLabel, gbc);

        // add age field to signup panel at (1, 4)
        gbc.gridx = 3;
        gbc.gridy = 3;
        signupBgLabel.add(ageField, gbc);

        // add balance label to signup panel at (0, 5)
        gbc.gridx = 0;
        gbc.gridy = 4;
        // create label and text field for balance
        JLabel balanceLabel = new CustomJLabel("Balance:");
        signupBgLabel.add(balanceLabel, gbc);

        // add balance field to signup panel at (1, 5)
        gbc.gridx = 3;
        gbc.gridy = 4;
        signupBgLabel.add(balanceField, gbc);

        // add register button to signup panel at (0, 3) with grid width of 2
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signupBgLabel.add(registerButton, gbc);

        // add the back button to the signup panel at (0, 4) with grid width of 2
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signupBgLabel.add(backButton, gbc);
        // create a timer to switch from splash panel to login panel after 3 seconds

        // add panels to the frame with corresponding names
        add(splashPanel, "Splash");
        add(loginPanel, "Login");
        add(signupPanel, "Signup");

        // show the splash panel first
        cardLayout.show(getContentPane(), "Splash");

        Timer timer = new Timer(7000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "Login");
            }
        });

        // start the timer
        timer.start();

        // Initialize the messageLabel in the Login constructor and add it to the login panel
        JLabel messageLabel2 = new CustomJLabel("");
        loginPanel.add(messageLabel2, BorderLayout.SOUTH);

        // Use the messageLabel to display the connection status in the do while loop


        // in your main class, create an instance of LoginButtonListener and pass the frame as an argument
        loginButton.addActionListener(new LoginButtonListener(this, connection, usernameField, passwordField, messageLabel));

        // create an action listener for the signup button
        signupButton.addActionListener(new SignupButtonListener(cardLayout, timer, this));

// add the action listener to the back button
        backButton.addActionListener(new BackButtonListener(cardLayout, timer, this));
        registerButton.addActionListener(new RegisterButtonListener(connection, nameField, usernameField2, passwordField2, ageField, balanceField, cardLayout, this));


        if (connection != null) {
            // Set the messageLabel text to green and show a success message
            messageLabel2.setForeground(Color.GREEN);
            messageLabel2.setText("Connection established.");
        } else {
            // Set the messageLabel text to red and show a failure message
            messageLabel2.setForeground(Color.RED);
            messageLabel2.setText("Connection failed. Please restart the app.");
        }

        // make the frame visible
        setVisible(true);
    }
    

    // main method to run the program
    public static void main(String[] args) {
        new Login();
    }
}