import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JPanel {

    public Editor(int width, int height) {
        setPreferredSize(new Dimension(width, height));

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(width, height));

        JScrollPane scroll = new JScrollPane(textArea);
        System.out.println(height + textArea.getPreferredSize().height);

        add(scroll);
    }
}
