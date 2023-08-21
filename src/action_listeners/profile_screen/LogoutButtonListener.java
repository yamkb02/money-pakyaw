package action_listeners.profile_screen;

import screens.Login;
import screens.Mainframe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A public class that implements the ActionListener interface
public class LogoutButtonListener implements ActionListener {

    // Declare instance variables for the parameters
    private final JPanel panel;
    private final Login login;

    // Modify the constructor to take those parameters as arguments
    public LogoutButtonListener(JPanel panel, Login login) {
        // Assign the arguments to the instance variables
        this.panel = panel;
        this.login = login;
    }

    // An overridden actionPerformed method that defines the logic for the logout button
    @Override
    public void actionPerformed(ActionEvent e) {
        // Dispose the current JFrame object that contains the profile screen panel
        Mainframe frame = (Mainframe) panel.getParent().getParent().getParent().getParent().getParent();
        frame.dispose();

        // Create a new JFrame object that contains the login screen panel
        login.setVisible(true);
    }
}
