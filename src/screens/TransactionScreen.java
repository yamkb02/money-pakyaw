//package name gi declare
package screens;

//import gikan sa lain folder
import action_listeners.transaction_screen.AddButtonActionListener;
import action_listeners.transaction_screen.DeleteButtonActionListener;
import action_listeners.transaction_screen.EditButtonActionListener;

//import sa connector padung sa database
import database_connector.Connector;

//import sa listener para mabaw an ang giselect nga row sa table
import list_selection_listeners.TableValueChangeListener_Transaction;

//import sa bag o nga button
import ui_extras.ResponsiveButton;

//normal imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// A GUI component that extends JPanel and displays a table of transactions for a given user
public class TransactionScreen extends JPanel {

    // The username of the current user
    private final String username;

    // The JDBC connection and statement objects
    private final Connection connection;
    // The GUI components
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private PreparedStatement statement;

    // The constructor that receives a username string
    public TransactionScreen(String username) {
        this.username = username;

        // connect to database
        connection = Connector.getInstance().getConnection();

        // Initialize the GUI components
        addButton = new ResponsiveButton("Add Transaction");
        editButton = new ResponsiveButton("Edit Transaction");
        deleteButton = new ResponsiveButton("Delete Transaction");

        // Create a table model with column names - plan para sa table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Amount", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };

        // Create the table based on the table model
        table = new JTable(tableModel);

        // e set ang font sa header
        Font headerFont = new Font("serif", Font.BOLD, 20); // create a new font with size 20 and bold style
        table.getTableHeader().setFont(headerFont); // set the font of the table header to headerFont
        JTableHeader header = table.getTableHeader(); // get the table header
        Dimension dim = header.getPreferredSize(); // get the preferred size of the header
        dim.height = 50; // set the height to 50 pixels
        header.setPreferredSize(dim); // set the preferred size of the header

        Font f = new Font("serif", Font.PLAIN, 20); // create a new font with size 20
        table.setFont(f); // set the font of the table to f
        table.setRowHeight(40); // set the height of all rows to 40 pixels

        // para hide sa id
        TableColumnModel columnModel1 = table.getColumnModel();
        columnModel1.removeColumn(columnModel1.getColumn(0));

        // Add a selection listener to the table
        table.getSelectionModel().addListSelectionListener(new TableValueChangeListener_Transaction(this.table, this.editButton, this.deleteButton));

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the components to this panel using a border layout
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to this panel using a south border layout
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        addButton.addActionListener(new AddButtonActionListener(this, this.connection, this.statement, this.username));

        editButton.addActionListener(new EditButtonActionListener(this, this.connection, this.statement, this.table, this.tableModel));

        deleteButton.addActionListener(new DeleteButtonActionListener(this, this.connection, this.statement, this.table, this.tableModel));


        // Populate the table with data from the database
        populateTable();
    }

    // A method to populate the table with data from the database
    public void populateTable() {
        try {
            // Clear the table model
            tableModel.setRowCount(0);

            statement = connection.prepareStatement("SELECT * FROM transactions WHERE username = ?");
            statement.setString(1, this.username);
            ResultSet rs = statement.executeQuery();

            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                Date date = rs.getDate("date");
                tableModel.addRow(new Object[]{id, name, amount, date});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in populating the table.");
        }
    }
}