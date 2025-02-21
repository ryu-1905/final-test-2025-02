import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UI extends JFrame {
    public UI() {
        setTitle("Note");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        add(new SideBar((int) (screenSize.getWidth() * 0.2), (int) screenSize.getHeight()), BorderLayout.WEST);
        add(new Editor((int) (screenSize.getWidth() * 0.8), (int) screenSize.getHeight()), BorderLayout.CENTER);

        setVisible(true);
    }

}
