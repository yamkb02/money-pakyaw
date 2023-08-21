package change_listeners;

import screens.MainScreen;
import screens.Mainframe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TabChangeListener implements ChangeListener {

    private final String username;
    private final JTabbedPane tabbedPane;
    private final Mainframe mainframe;
    private final MainScreen mainScreen;

    // constructor that takes the username, tabbedPane, mainframe and mainScreen as parameters
    public TabChangeListener(String username, JTabbedPane tabbedPane, Mainframe mainframe, MainScreen mainScreen) {
        this.username = username;
        this.tabbedPane = tabbedPane;
        this.mainframe = mainframe;
        this.mainScreen = mainScreen;
    }

    // override the stateChanged method
    @Override
    public void stateChanged(ChangeEvent e) {
        // get the index of the selected tab
        int index = tabbedPane.getSelectedIndex();
        // get the screen object from the tab
        Component screen = tabbedPane.getComponentAt(index);
        // get the simple name of the class of the screen object
        String className = screen.getClass().getSimpleName();
        // switch on the class name
        if (className.equals("MainScreen")) {
            mainScreen.refresh();
            tabbedPane.repaint();
            mainframe.revalidate();
            mainframe.repaint();
        }
    }
}