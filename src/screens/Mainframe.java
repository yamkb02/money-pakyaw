package screens;


// import the necessary packages

import change_listeners.TabChangeListener;
import database_connector.Connector;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// create a class for the mainframe
public class Mainframe extends JFrame {

    private Color theme_color;
    private final String username;
    private final JTabbedPane tabbedPane;
    private final MainScreen mainScreen;
    private final TransactionScreen transactionScreen;
    private final SavingsScreen savingsScreen;
    private final IncomeScreen incomeScreen;
    private final ExpenseScreen expenseScreen;
    private final ProfileScreen profileScreen;
    private final AboutScreen aboutScreen;
    private final Connection connection;
    private PreparedStatement statement;

    // create a constructor
    public Mainframe(String username) {
        // set the title and size of the frame
        super("Money Pakyaw");
        setSize(Login.width, Login.height);
        setMinimumSize(new Dimension(Login.width - 200, Login.height - 200));
        setLocationRelativeTo(null);
        this.username = username;

        // Initialize the database connection and statement
        connection = Connector.getInstance().getConnection();

        // theme
        try {
            // create a prepared statement with a parameterized query
            statement = connection.prepareStatement("SELECT theme_in_use FROM user_account WHERE username = ?");
            // set the username parameter to the value of the username variable
            statement.setString(1, username);
            // execute the query and get the result set
            ResultSet rs = statement.executeQuery();
            // if the query returns a result, get the theme in use and assign it to the
            // theme variable
            if (rs.next()) {
                String theme_in_use = rs.getString("theme_in_use");
                // parse the theme string as an integer value using radix 16
                int value = Integer.parseInt(theme_in_use.substring(1), 16);

                // conversion

                // extract the red, green, and blue values from the integer value using bit
                // operations
                int red = (value >> 16) & 0xFF;
                int green = (value >> 8) & 0xFF;
                int blue = value & 0xFF;
                // create a new color object using the extracted values and assign it to the
                // theme_color variable
                theme_color = new Color(red, green, blue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            theme_color = Color.green;
        }

        ResponsiveButton.color = theme_color;

        // create an image icon from the file path
        ImageIcon logoIcon = new ImageIcon("res/logo.png");
        // get the image from the image icon
        Image logoImage = logoIcon.getImage();
        // set the icon of the title bar to the logo image
        setIconImage(logoImage);

        // initialize the components
        tabbedPane = new JTabbedPane();

        mainScreen = new MainScreen(this.username);
        transactionScreen = new TransactionScreen(this.username);
        savingsScreen = new SavingsScreen(this.username);
        incomeScreen = new IncomeScreen(this.username);
        expenseScreen = new ExpenseScreen(this.username);
        profileScreen = new ProfileScreen(this.username);
        aboutScreen = new AboutScreen();

        // add the components to the tabbed pane
        tabbedPane.addTab("Main", mainScreen);
        tabbedPane.addTab("Transactions", transactionScreen);
        tabbedPane.addTab("Savings", savingsScreen);
        tabbedPane.addTab("Income", incomeScreen);
        tabbedPane.addTab("Expenses", expenseScreen);
        tabbedPane.addTab("Profile", profileScreen);
        tabbedPane.addTab("About", aboutScreen);

        for (int i = 0; i < 7; i++) {
            tabbedPane.setBackgroundAt(i, theme_color);
            tabbedPane.setForegroundAt(i, Color.BLACK);

            // Increase the font size for the tab titles
            Font f = new Font("serif", Font.PLAIN, 20); // create a new font with size 20
            tabbedPane.setFont(f);
        }
        // add the tabbed pane to the frame
        add(tabbedPane);

        // set the default close operation and make the frame visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // To reinitialize the screen after switching to that tab,
        // add a listener to the tabbedPane that calls the refresh()
        // method of the screen class.
        tabbedPane.addChangeListener(new TabChangeListener(this.username, this.tabbedPane, this, this.mainScreen));

        setVisible(true);
    }
}