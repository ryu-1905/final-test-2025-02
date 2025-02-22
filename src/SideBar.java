import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SideBar extends JPanel {

    JButton newNoteButton = new JButton("New Note");

    public SideBar(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.darkGray);

        add(newNoteButton);
    }

    public void createNote(){

        Utils.fileExists(Utils.notesListFolder, true);
    }

}
