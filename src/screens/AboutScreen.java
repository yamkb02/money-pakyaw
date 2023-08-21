package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AboutScreen extends JPanel {

    private final JScrollPane scrollPane;
    private final JLabel imageLabel;

    public AboutScreen() {
        // Create a scroll pane with a vertical scroll bar
        scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Create a label with an image icon
        imageLabel = new JLabel(new ImageIcon("res/about.png"));

        // Add the label to the scroll pane
        scrollPane.setViewportView(imageLabel);

        // Add the scroll pane to this panel
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        // Add a component listener to resize the image when the frame is resized
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeImage();
            }
        });
    }

    // A method to resize the image to fit the width of the frame
    private void resizeImage() {
        // Get the width of the scroll pane's viewport
        int width = scrollPane.getViewport().getWidth();

        // Get the original image icon
        ImageIcon icon = (ImageIcon) imageLabel.getIcon();

        // Get the original image
        Image image = icon.getImage();

        // Get the original width and height of the image
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        // Calculate the new height based on the aspect ratio
        int newHeight = (int) (originalHeight * ((double) width / originalWidth));

        // Resize the image using smooth scaling
        Image resizedImage = image.getScaledInstance(width, newHeight, Image.SCALE_SMOOTH);

        // Set the new image icon to the label
        imageLabel.setIcon(new ImageIcon(resizedImage));
    }
}
