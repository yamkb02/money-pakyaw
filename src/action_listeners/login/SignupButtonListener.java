package action_listeners.login;

import screens.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// create a class named SignupButtonListener that implements ActionListener
public class SignupButtonListener implements ActionListener {

    // declare a private field for the card layout
    private final CardLayout cardLayout;

    // declare a private field for the timer
    private final Timer timer;
    private final Login login;


    // create a constructor that takes the card layout and the timer as parameters
    public SignupButtonListener(CardLayout cardLayout, Timer timer, Login login) {        // assign the parameters to the fields
        this.cardLayout = cardLayout;
        this.timer = timer;
        this.login = login;
    }


    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // switch to the signup panel
        cardLayout.show(login.getContentPane(), "Signup");
        // stop the timer
        timer.stop();
    }
}