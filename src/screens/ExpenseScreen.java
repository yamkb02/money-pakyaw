package screens;

import action_listeners.expenses_screen.AddExpenseListener;
import action_listeners.expenses_screen.EditExpenseListener;
import action_listeners.expenses_screen.ExpensePaidListener;
import action_listeners.expenses_screen.SelectedExpensePaidListener;
import database_connector.Connector;
import ui_extras.CustomJLabel2;
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

public class ExpenseScreen extends JPanel {
    private final String username;
    private final Connection connection;
    private final JButton addExpenseButton;
    private final JButton editButton;
    private final JButton expensePaidButton;
    private final JButton selectedExpensePaidButton;
    private final JTable table1;
    private final JTable table2;
    private final DefaultTableModel tableModel1;
    private final DefaultTableModel tableModel2;
    private PreparedStatement statement;

    public ExpenseScreen(String username) {
        this.username = username;
        // Initialize the database connection

        connection = Connector.getInstance().getConnection();

        addExpenseButton = new ResponsiveButton("Add Expense");
        editButton = new ResponsiveButton("Edit Expense");
        expensePaidButton = new ResponsiveButton("Expense Paid");
        selectedExpensePaidButton = new ResponsiveButton("Selected Expense Paid");

        // create new table models
        tableModel1 = new DefaultTableModel(new String[]{"ID", "Name", "Amount", "Date", "paid"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel2 = new DefaultTableModel(new String[]{"ID", "Name", "Amount", "Date", "paid"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1 = new JTable(tableModel1);
        table2 = new JTable(tableModel2);

        Font headerFont = new Font("serif", Font.BOLD, 20); // create a new font with size 20 and bold style
        table1.getTableHeader().setFont(headerFont); // set the font of the table header to headerFont

        JTableHeader header = table1.getTableHeader(); // get the table header
        Dimension dim = header.getPreferredSize(); // get the preferred size of the header
        dim.height = 50; // set the height to 50 pixels
        header.setPreferredSize(dim); // set the preferred size of the header

        Font f = new Font("serif", Font.PLAIN, 20); // create a new font with size 20
        table1.setFont(f); // set the font of the table to f
        table1.setRowHeight(40); // set the height of all rows to 40 pixels


        table2.getTableHeader().setFont(headerFont); // set the font of the table header to headerFont
        header = table2.getTableHeader(); // get the table header
        header.setPreferredSize(dim); // set the preferred size of the header

        table2.setFont(f); // set the font of the table to f
        table2.setRowHeight(40); // set the height of all rows to 40 pixels

        // Make the ID and paid columns invisible
        TableColumnModel columnModel1 = table1.getColumnModel();
        columnModel1.removeColumn(columnModel1.getColumn(0));
        columnModel1.removeColumn(columnModel1.getColumn(3));
        TableColumnModel columnModel2 = table2.getColumnModel();
        columnModel2.removeColumn(columnModel2.getColumn(0));
        columnModel2.removeColumn(columnModel2.getColumn(3));

        addExpenseButton.addActionListener(new AddExpenseListener(username, connection, statement, this));
        editButton.addActionListener(new EditExpenseListener(username, connection, statement, table1, table2, tableModel1, tableModel2, this));
        expensePaidButton.addActionListener(new ExpensePaidListener(username, connection, statement, this));
        selectedExpensePaidButton.addActionListener(new SelectedExpensePaidListener(username, connection, statement, table1, table2, tableModel1, tableModel2, this));

        JScrollPane scrollPane1 = new JScrollPane(table1);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        JPanel tablePanel = new JPanel(new GridLayout(2, 1));
        tablePanel.add(new CustomJLabel2("Expense Categories"));
        tablePanel.add(scrollPane1);
        tablePanel.add(new CustomJLabel2("Expense History"));
        tablePanel.add(scrollPane2);
        this.setLayout(new BorderLayout());
        this.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(editButton);
        buttonPanel.add(expensePaidButton);
        buttonPanel.add(selectedExpensePaidButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
        // Add code for populating tables with data from database
        populateTable();
    }

    public void populateTable() {
        try {
            tableModel1.setRowCount(0);
            statement = connection.prepareStatement("SELECT * FROM expense_categories WHERE username=? AND paid=0");
            statement.setString(1, username);
            ResultSet rs1 = statement.executeQuery();
            while (rs1.next()) {
                int id = rs1.getInt("id");
                String name = rs1.getString("name");
                double amount = rs1.getDouble("amount");
                Date date = rs1.getDate("date");
                boolean paid = rs1.getBoolean("paid");
                tableModel1.addRow(new Object[]{id, name, amount, date, paid});
            }

            tableModel2.setRowCount(0);
            statement = connection.prepareStatement("SELECT * FROM expense_categories WHERE username=? AND paid=1");
            statement.setString(1, username);
            ResultSet rs2 = statement.executeQuery();
            while (rs2.next()) {
                int id = rs2.getInt("id");
                String name = rs2.getString("name");
                double amount = rs2.getDouble("amount");
                Date date = rs2.getDate("date");
                boolean paid = rs2.getBoolean("paid");
                tableModel2.addRow(new Object[]{id, name, amount, date, paid});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}