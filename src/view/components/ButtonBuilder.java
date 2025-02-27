package view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

// Builder design pattern
public class ButtonBuilder {
    private JButton button = new JButton();

    public JButton build() {
        return button;
    }

    public ButtonBuilder text(String text) {
        button.setFocusPainted(false);

        button.setText(text);
        return this;
    }

    public ButtonBuilder icon(String path, int width, int height) {
        button.setFocusPainted(false);

        // resize icon
        Image newImage = new ImageIcon(path).getImage().getScaledInstance(width, height,
                java.awt.Image.SCALE_SMOOTH);

        button.setIcon(new ImageIcon(newImage));
        return this;
    }

    public ButtonBuilder maximumWidth(int maximumSizeWidth) {

        // default values if negative
        if (maximumSizeWidth <= 0) {
            maximumSizeWidth = button.getMaximumSize().width;
        }

        button.setMaximumSize(new Dimension(maximumSizeWidth, button.getMaximumSize().height));
        return this;
    }

    public ButtonBuilder border(Border border) {
        button.setBorder(border);
        return this;
    }

    public ButtonBuilder actionListener(ActionListener e) {
        button.addActionListener(e);
        return this;
    }

    public ButtonBuilder backgroundColor(Color color) {
        button.setBackground(color);
        return this;
    }

    public ButtonBuilder actionCommand(String actionCommand) {
        button.setActionCommand(actionCommand);
        return this;
    }
}
