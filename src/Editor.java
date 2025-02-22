import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JPanel {

    public Editor(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        // setBackground(Color.white);

        JTextArea textArea = new JTextArea((864 / 17), width / 10);
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries

        JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        add(scroll);
    }
}
