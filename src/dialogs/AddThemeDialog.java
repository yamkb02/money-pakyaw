package dialogs;

import ui_extras.CustomJLabel;
import ui_extras.ResponsiveButton;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddThemeDialog extends JDialog {
    // The GUI components
    private final JLabel nameLabel;
    private final JTextField nameField;
    private final JSlider redSlider;
    private final JSlider greenSlider;
    private final JSlider blueSlider;
    private final JButton saveButton;

    // The flag to indicate whether the user clicked the save button or not
    private boolean saved;

    // The constructor that takes no arguments
    public AddThemeDialog() {
        // Call the super constructor with a title and a modal flag
        super((Dialog) null, "Add Theme", true);

        // Initialize the GUI components
        nameLabel = new CustomJLabel("Name:");
        nameField = new JTextField(20);
        redSlider = new JSlider(100, 255, 100);
        greenSlider = new JSlider(100, 255, 100);
        blueSlider = new JSlider(100, 255, 100);
        saveButton = new ResponsiveButton("Save Theme");

        // Add change listeners to update color preview when sliders change values
        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColor();
            }
        });
        greenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColor();
            }
        });
        blueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColor();
            }
        });

        // Add action listener to save theme when save button is clicked
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTheme();
            }
        });

        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        // Create labels for each slider using HTML color codes
        JLabel redLabel = new CustomJLabel("<html><font color=red>Red</font></html>");
        JLabel greenLabel = new CustomJLabel("<html><font color=green>Green</font></html>");
        JLabel blueLabel = new CustomJLabel("<html><font color=blue>Blue</font></html>");

        // Create a panel for each slider and label pair using a grid layout
        JPanel redPanel = new JPanel(new GridLayout(1, 2));
        redPanel.add(redLabel);
        redPanel.add(redSlider);

        JPanel greenPanel = new JPanel(new GridLayout(1, 2));
        greenPanel.add(greenLabel);
        greenPanel.add(greenSlider);

        JPanel bluePanel = new JPanel(new GridLayout(1, 2));
        bluePanel.add(blueLabel);

        // ok mark
        // i will continue the code for the AddThemeDialog class
        // here is the rest of the code

        bluePanel.add(blueSlider);

        // Create a panel for all sliders using a grid layout
        JPanel sliderPanel = new JPanel(new GridLayout(3, 1));
        sliderPanel.add(redPanel);
        sliderPanel.add(greenPanel);
        sliderPanel.add(bluePanel);

        // Create a panel for the save button using a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);

        // Set the layout of this dialog to a border layout
        this.setLayout(new BorderLayout());

        // Add the panels to this dialog using border layout constraints
        this.add(formPanel, BorderLayout.NORTH);
        this.add(sliderPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and location of this dialog
        this.setSize(700, 200);
        this.setLocationRelativeTo(null);

        // Set the saved flag to false initially
        saved = false;
    }

    public String getName() {
        return nameField.getText();
    }

    // A method to get the color value from the sliders as a hex string
    public String getColor() {
        // Get the red, green, and blue values from the sliders
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();

        // Convert the values to a hex string using String.format
        String color = String.format("#%02x%02x%02x", red, green, blue);

        // Return the color string
        return color;
    }

    // A method to get the saved flag value
    public boolean isSaved() {
        return saved;
    }

    // A method to update the color preview when sliders change values
    private void updateColor() {
        // Get the color value from the sliders
        String color = getColor();

        // Set the background color of the save button to the color value
        saveButton.setBackground(Color.decode(color));
    }

    // A method to save the theme and close this dialog
    private void saveTheme() {
        // Set the saved flag to true
        saved = true;

        // Dispose this dialog
        this.dispose();
    }
}
