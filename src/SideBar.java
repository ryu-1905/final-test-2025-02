import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class SideBar extends JPanel {

    public SideBar(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.darkGray);
    }

}
