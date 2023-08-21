package list_selection_listeners;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableValueChangeListener_Savings implements ListSelectionListener {

    private final JTable table;
    private final JButton editButton;
    private final JButton deleteButton;

    // constructor that takes the table and editButton and deleteButton as parameters
    public TableValueChangeListener_Savings(JTable table, JButton editButton, JButton deleteButton) {
        this.table = table;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
    }

    // override the valueChanged method
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // enable or disable the edit and delete buttons based on the selection
        boolean selected = table.getSelectedRow() != -1;
        editButton.setEnabled(selected);
        deleteButton.setEnabled(selected);
    }
}