package list_selection_listeners;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableValueChangeListener_Profile implements ListSelectionListener {

    private final JTable table;
    private final JButton editButton;

    // constructor that takes the table and editButton as parameters
    public TableValueChangeListener_Profile(JTable table, JButton editButton) {
        this.table = table;
        this.editButton = editButton;
    }

    // override the valueChanged method
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // enable or disable the edit button based on the selection
        boolean selected = table.getSelectedRow() != -1;
        editButton.setEnabled(selected);
    }
}