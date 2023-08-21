package screens;


// import sql components

import database_connector.Connector;
import ui_extras.CustomJLabel2;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// create a class that extends JPanel
public class MainScreen extends JLabel {

    // declare private fields for the components
    private final JLabel balanceLabel;
    private final JLabel savingsLabel;
    private final JLabel incomeLabel;
    private final JLabel expensesLabel;
    private final String username;

    private final Connection connection;
    private PreparedStatement statement;

    // create a constructor that takes a username as a parameter
    public MainScreen(String username) {
        super();
        ImageIcon bg = new ImageIcon("res/main.png");
        Image scaledImage = bg.getImage().getScaledInstance(Login.width, Login.height, Image.SCALE_SMOOTH);
        // set the new image icon to the label
        this.setIcon(new ImageIcon(scaledImage));
        // scale the image icon to fit the frame
        setLayout(new BorderLayout());

        JPanel innerPanel = new JPanel(new GridLayout(4, 2));

        connection = Connector.getInstance().getConnection();

        this.username = username;
        balanceLabel = new CustomJLabel2("Balance: " + getBalance());
        savingsLabel = new CustomJLabel2("Total Savings: " + getTotalSavings());
        incomeLabel = new CustomJLabel2("Total Income Earned: " + getIncomeEarned());
        expensesLabel = new CustomJLabel2("Total Expenses Paid: " + getExpensesPaid());

        // add the labels to the panel
        innerPanel.add(balanceLabel);
        innerPanel.add(savingsLabel);
        innerPanel.add(incomeLabel);
        innerPanel.add(expensesLabel);

        add(innerPanel, BorderLayout.WEST);
    }


    // create a method that returns the balance of a user from the database
    private double getBalance() {
        // declare a variable to store the balance
        double balance = 0.0;
        double transactions = 0.0;
        double savings = 0.0;
        double incomeEarned = getIncomeEarned();
        double expensesPaid = getExpensesPaid();

        // create a try-catch block to handle exceptions
        try {
            // create a query string to get the balance of a user from the user_account
            // table
            String queryBalance = "SELECT balance FROM user_account WHERE username = ?";
            statement = connection.prepareStatement(queryBalance);
            statement.setString(1, this.username);

// execute the query and store the result set
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // get the balance from the result set and assign it to the variable
                balance = rs.getDouble("balance");
            }

            String queryTransactions = "SELECT SUM(amount) AS transactionsTotal FROM transactions WHERE username = ?";
            statement = connection.prepareStatement(queryTransactions);
            statement.setString(1, this.username);

            // execute the query and store the result set
            rs = statement.executeQuery();

            if (rs.next()) {
                transactions = rs.getDouble("transactionsTotal");
            }

            String querySavings = "SELECT SUM(amount) AS savingsTotal FROM savings WHERE username = ?";
            statement = connection.prepareStatement(querySavings);
            statement.setString(1, this.username);

            // execute the query and store the result set
            rs = statement.executeQuery();

            if (rs.next()) {
                savings = rs.getDouble("savingsTotal");
            }

            // close the result set, statement and connection objects
            rs.close();
        } catch (Exception e) {
            // print the stack trace of the exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in getting balance.");
        }

        // return the balance
        return balance + transactions - savings + incomeEarned - expensesPaid;
    }

    // create a method that returns the total savings of a user from the database
    private double getTotalSavings() {
        // declare a variable to store the total savings
        double totalSavings = 0.0;

        // create a try-catch block to handle exceptions
        try {
            // create a query string to get the sum of savings amount of a user from the
            // savings table
            String query = "SELECT SUM(amount) AS total_savings FROM savings WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.username);

            // execute the query and store the result set
            ResultSet rs = statement.executeQuery();

            // check if the result set has any data
            if (rs.next()) {
                // get the total savings from the result set and assign it to the variable
                totalSavings = rs.getDouble("total_savings");
            }

            // close the result set, statement and connection objects
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            // print the stack trace of the exception
            JOptionPane.showMessageDialog(null, "Error in getting total savings.");
        }

        // return the total savings
        return totalSavings;
    }

    // create a method that returns the total income of a user from the database
    private double getIncomeEarned() {
        // declare a variable to store the total savings
        double incomeEarned = 0.0;

        // create a try-catch block to handle exceptions
        try {
            statement = connection.prepareStatement(
                    "SELECT SUM(amount) AS income_earned FROM income_sources WHERE username = ? AND earned = ?");
            statement.setString(1, this.username);
            statement.setBoolean(2, true);
            ResultSet rs = statement.executeQuery();

            // check if the result set has any data
            if (rs.next()) {
                // get the total savings from the result set and assign it to the variable
                incomeEarned = rs.getDouble("income_earned");
            }

            // close the result set, statement and connection objects
            rs.close();
        } catch (Exception e) {
            // print the stack trace of the exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in getting total savings.");
        }

        // return the total savings
        return incomeEarned;
    }

    private double getExpensesPaid() {
        // declare a variable to store the total expenses
        double expensesPaid = 0.0;

        // create a try-catch block to handle exceptions
        try {

            // create a query string to get the sum of expenses amount of a user from the
            // expense_sources table
            String query = "SELECT SUM(amount) AS expenses_paid FROM expense_categories WHERE username = ? AND paid = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.username);
            statement.setBoolean(2, true);

            // execute the query and store the result set
            ResultSet rs = statement.executeQuery();
//
//            String query = "SELECT SUM(amount) AS expenses_paid FROM expense_categories WHERE username = '" + this.username
//                    + "' AND paid = 1";
//
//            // execute the query and store the result set
//            ResultSet rs = statement.executeQuery(query);

            // check if the result set has any data
            if (rs.next()) {
                // get the total expenses from the result set and assign it to the variable
                expensesPaid = rs.getDouble("expenses_paid");
            }

            // close the result set, statement and connection objects
            rs.close();
        } catch (Exception e) {
            // print the stack trace of the exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in getting total expenses.");
        }

        // return the total expenses
        return expensesPaid;
    }

    public void refresh() {
        // query the database for the user's balance, income, expenses, etc.
        // double totalExpenses = getTotalExpenses();
        // update the labels, charts, tables, etc. with the new data
        balanceLabel.setText("Balance: " + getBalance());
        savingsLabel.setText("Total Savings: " + getTotalSavings());
        incomeLabel.setText("Total Income Earned: " + getIncomeEarned());
        expensesLabel.setText("Total Expenses Paid: " + getExpensesPaid());
    }
}