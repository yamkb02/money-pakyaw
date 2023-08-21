package screens;

import action_listeners.income_screen.AddIncomeListener;
import action_listeners.income_screen.EditIncomeListener;
import action_listeners.income_screen.IncomeReceivedListener;
import action_listeners.income_screen.SelectedIncomeEarnedListener;
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

public class IncomeScreen extends JPanel {
    private final String username;
    private final Connection connection;
    private final JButton addIncomeButton;
    private final JButton editButton;
    private final JButton incomeReceivedButton;
    private final JButton selectedIncomeEarnedButton;
    private final JTable table1;
    private final JTable table2;
    private final DefaultTableModel tableModel1;
    private final DefaultTableModel tableModel2;
    private PreparedStatement statement;

    public IncomeScreen(String username) {
        this.username = username;

        connection = Connector.getInstance().getConnection();

        addIncomeButton = new ResponsiveButton("Add Income");
        editButton = new ResponsiveButton("Edit Income");
        incomeReceivedButton = new ResponsiveButton("Income Received");
        selectedIncomeEarnedButton = new ResponsiveButton("Selected Income Earned");
        tableModel1 = new DefaultTableModel(new String[]{"ID", "Name", "Amount", "Date", "Earned"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel2 = new DefaultTableModel(new String[]{"ID", "Name", "Amount", "Date", "Earned"}, 0) {
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

        // Make the ID and Earned columns invisible
        TableColumnModel columnModel1 = table1.getColumnModel();
        columnModel1.removeColumn(columnModel1.getColumn(0));
        columnModel1.removeColumn(columnModel1.getColumn(3));

        TableColumnModel columnModel2 = table2.getColumnModel();
        columnModel2.removeColumn(columnModel2.getColumn(0));
        columnModel2.removeColumn(columnModel2.getColumn(3));

        JScrollPane scrollPane1 = new JScrollPane(table1);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        JPanel tablePanel = new JPanel(new GridLayout(2, 1));
        tablePanel.add(new CustomJLabel2("Income Sources"));
        tablePanel.add(scrollPane1);

        tablePanel.add(new CustomJLabel2("Income History"));
        tablePanel.add(scrollPane2);

        this.setLayout(new BorderLayout());
        this.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addIncomeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(incomeReceivedButton);
        buttonPanel.add(selectedIncomeEarnedButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        addIncomeButton.addActionListener(
                new AddIncomeListener(username, connection, statement, this) {
                    // You can override any methods here if needed
                });

        editButton.addActionListener(
                new EditIncomeListener(username, connection, table1, table2, tableModel1, tableModel2, statement, this) {
                    // You can override any methods here if needed
                });

        incomeReceivedButton.addActionListener(
                new IncomeReceivedListener(username, connection, table1, table2, tableModel1, tableModel2, statement, this) {
                    // You can override any methods here if needed
                });

        selectedIncomeEarnedButton.addActionListener(
                new SelectedIncomeEarnedListener(username, connection, table1, table2, tableModel1, tableModel2, statement, this) {
                    // You can override any methods here if needed
                });

        // Add code for populating tables with data from database
        populateTable();
    }

    public void populateTable() {
        try {
            tableModel1.setRowCount(0);
            statement = connection.prepareStatement("SELECT * FROM income_sources WHERE username = ? AND earned = 0");
            statement.setString(1, username);
            ResultSet rs1 = statement.executeQuery();
            while (rs1.next()) {
                int id = rs1.getInt("id");
                String name = rs1.getString("name");
                double amount = rs1.getDouble("amount");
                Date date = rs1.getDate("date");
                boolean earned = rs1.getBoolean("earned");
                tableModel1.addRow(new Object[]{id, name, amount, date, earned});
            }

            tableModel2.setRowCount(0);
            statement = connection.prepareStatement("SELECT * FROM income_sources WHERE username = ? AND earned = 1");
            statement.setString(1, username);
            ResultSet rs2 = statement.executeQuery();
            while (rs2.next()) {
                int id = rs2.getInt("id");
                String name = rs2.getString("name");
                double amount = rs2.getDouble("amount");
                Date date = rs2.getDate("date");
                boolean earned = rs2.getBoolean("earned");
                tableModel2.addRow(new Object[]{id, name, amount, date, earned});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}