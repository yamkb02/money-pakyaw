package action_listeners.login;

import screens.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// create a class named BackButtonListener that implements ActionListener
public class BackButtonListener implements ActionListener {

    // declare private fields for the card layout, the timer and the login frame
    private final CardLayout cardLayout;
    private final Timer timer;
    private final Login loginFrame;

    // create a constructor that takes the card layout, the timer and the login frame as parameters
    public BackButtonListener(CardLayout cardLayout, Timer timer, Login loginFrame) {
        // assign the parameters to the fields
        this.cardLayout = cardLayout;
        this.timer = timer;
        this.loginFrame = loginFrame;
    }

    // override the actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        // switch to the login panel using the login frame reference
        cardLayout.show(loginFrame.getContentPane(), "Login");
        // start the timer
        timer.start();
    }
}