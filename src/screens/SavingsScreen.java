package screens;

import action_listeners.savings_screen.AddButtonListener;
import action_listeners.savings_screen.DeleteButtonListener;
import action_listeners.savings_screen.EditButtonListener;
import action_listeners.savings_screen.SpendButtonListener;

import database_connector.Connector;

import list_selection_listeners.TableValueChangeListener_Savings;

import ui_extras.ResponsiveButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// A GUI component that extends JPanel and displays a table of savings for a given user
public class SavingsScreen extends JPanel {
    // The username of the current user
    private final String username;
    private final Connection connection;
    // The GUI components
    private final JButton addButton;
    private final JButton spendButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private PreparedStatement statement;

    // The constructor that receives a username string
    public SavingsScreen(String username) {
        this.username = username;

        // Initialize the database connection and statement
        connection = Connector.getInstance().getConnection();

        // Initialize the GUI components
        addButton = new ResponsiveButton("Add Savings");
        spendButton = new ResponsiveButton("Spend Savings");
        editButton = new ResponsiveButton("Edit Savings");
        deleteButton = new ResponsiveButton("Delete Savings");

        // Create a table model with column names
        tableModel = new DefaultTableModel(new String[]{"ID", "Amount", "Date"}, 0) {
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

        // Add a selection listener to the table
        table.getSelectionModel().addListSelectionListener(new TableValueChangeListener_Savings(this.table, this.editButton, this.deleteButton));

        // Make the ID and Earned columns invisible
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0));

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the components to this panel using a border layout
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(spendButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        // Add the button panel to this panel using a south border layout
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        addButton.addActionListener(new AddButtonListener(this.username, connection, statement, this));
        spendButton.addActionListener(new SpendButtonListener(this.username, connection, statement, table, tableModel, this));
        editButton.addActionListener(new EditButtonListener(this.username, connection, statement, table, tableModel, this));
        deleteButton.addActionListener(new DeleteButtonListener(this.username, connection, statement, table, tableModel, this));

        // Populate the table with data from the database
        populateTable();
    }

    // A method to populate the table with data from the database
    public void populateTable() {
        try {
            // Clear the table model
            tableModel.setRowCount(0);
            // Execute a query to get all savings for the current user
            statement = connection.prepareStatement("SELECT * FROM savings WHERE username = ?");
            statement.setString(1, this.username);
            ResultSet rs = statement.executeQuery();
            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                Date date = rs.getDate("date");
                tableModel.addRow(new Object[]{id, amount, date});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}