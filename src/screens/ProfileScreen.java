package screens;

import action_listeners.profile_screen.*;
import database_connector.Connector;
import list_selection_listeners.TableValueChangeListener_Profile;
import ui_extras.CustomJLabel2;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// A GUI component that extends JPanel and displays the user's profile and theme
public class ProfileScreen extends JPanel {
    private final String username;
    // The JDBC connection and statement objects
    private final Connection connection;
    // The GUI components
    private final JLabel nameLabel;
    private final JLabel ageLabel;
    private final JLabel themeLabel;
    private final JButton updateButton;
    private final JButton deleteButton;
    private final JButton logoutButton;
    private final JButton addButton;
    private final JButton editButton;
    // Create a new JButton object with the text "Logout"
    private final JButton setAsThemeButton;
    private PreparedStatement statement;
    private JTable table;
    private DefaultTableModel tableModel;

    // The constructor that receives a username string
    public ProfileScreen(String username) {
        this.username = username;
        // Initialize the database connection and statement
        connection = Connector.getInstance().getConnection();

        // Initialize the GUI components
        nameLabel = new CustomJLabel2();
        ageLabel = new CustomJLabel2();
        themeLabel = new CustomJLabel2();
        updateButton = new ResponsiveButton("Update Profile");
        deleteButton = new ResponsiveButton("Delete Account");
        logoutButton = new ResponsiveButton("Logout");
        // Add action listeners to the buttons
        updateButton.addActionListener(new UpdateButtonListener(this.username, this.nameLabel, this.ageLabel, this.themeLabel, this.table, this.tableModel, connection, statement));
        deleteButton.addActionListener(new DeleteButtonListener(this.username, connection, statement, this));
        // Create an instance of the external class and pass the necessary parameters
        Login login = new Login();
        login.setVisible(false);
        logoutButton.addActionListener(new LogoutButtonListener(ProfileScreen.this, login));


        // Ari sugod ang theme
        // Create a table model with column names
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Color"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };

        // Create a table with the table model
        table = new JTable(tableModel);

        Font headerFont = new Font("serif", Font.BOLD, 20); // create a new font with size 20 and bold style
        table.getTableHeader().setFont(headerFont); // set the font of the table header to headerFont

        JTableHeader header = table.getTableHeader(); // get the table header
        Dimension dim = header.getPreferredSize(); // get the preferred size of the header
        dim.height = 50; // set the height to 50 pixels
        header.setPreferredSize(dim); // set the preferred size of the header

        Font f = new Font("serif", Font.PLAIN, 20); // create a new font with size 20
        table.setFont(f); // set the font of the table to f

        table.setRowHeight(40); // set the height of all rows to 40 pixels

        // hide sa id
        TableColumnModel columnModel1 = table.getColumnModel();
        columnModel1.removeColumn(columnModel1.getColumn(0));

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create buttons for adding and editing themes
        addButton = new ResponsiveButton("Add Theme");
        editButton = new ResponsiveButton("Edit Theme");
        setAsThemeButton = new ResponsiveButton("Set as Theme");

        // Set the layout of this panel to a grid bag layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Add the name label to this panel using grid bag constraints
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(nameLabel, c);
        // Add the age label to this panel using grid bag constraints
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(ageLabel, c);

        // set the grid position and fill mode for the theme label
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        // add the theme label to this panel using the grid bag constraint
        this.add(themeLabel, c);
        // Add the update button to this panel using grid bag constraints
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(updateButton, c);
        // Add the delete button to this panel using grid bag constraints
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(deleteButton, c);
        // Add the button to the profile screen panel using grid bag constraints
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(logoutButton, c);
        // Add the scroll pane to this panel using grid bag constraints
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(scrollPane, c);

        // Create a panel for the theme buttons using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(setAsThemeButton);

        // Add the button panel to this panel using grid bag constraints
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(buttonPanel, c);

        // Add a selection listener to the table
        table.getSelectionModel().addListSelectionListener(new TableValueChangeListener_Profile(this.table, this.editButton));

        // Add action listeners to the buttons
        addButton.addActionListener(new AddButtonListener(this.username, connection, statement, this));
        editButton.addActionListener(new EditButtonListener(this.username, connection, statement, table, tableModel, this));
        setAsThemeButton.addActionListener(new SetAsThemeButtonListener(this.username, connection, statement, themeLabel, table, tableModel));

        populateProfile();
        populateTable();
    }

    // A method to populate the labels with data from the database
    private void populateProfile() {
        try {
            // Execute a query to get the user details for the current username
            statement = connection.prepareStatement("SELECT * FROM user_account WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            // If the query returns a result, get the user details and set the labels
            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String theme_in_use = rs.getString("theme_in_use");
                nameLabel.setText("Name: " + name);
                nameLabel.setText("Name: " + name);
                ageLabel.setText("Age: " + age);
                themeLabel.setText("Theme: " + theme_in_use);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // A method to populate the table with data from the database
    public void populateTable() {
        try {
            // Clear the table model
            tableModel.setRowCount(0);
            // Execute a query to get all themes for the current user
            statement = connection.prepareStatement("SELECT * FROM theme WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            // Loop through the result set and add rows to the table model
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
