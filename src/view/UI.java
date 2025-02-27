package view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class UI extends JFrame implements ComponentListener {
    private SideBar sideBar = SideBar.getInstance();

    private Editor editor = Editor.getInstance();

    public UI() {
        setTitle("Note");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();

        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Center the window on the screen
        setLocationRelativeTo(null);

        // set size and init for components
        updateSizeBarSize();
        updateEditorSize();

        add(sideBar, BorderLayout.WEST);
        add(editor, BorderLayout.CENTER);

        addComponentListener(this);

        setVisible(true);
    }

    @Override
    // Update the size of the components when the window is resized
    public void componentResized(ComponentEvent e) {

        // Setup for responsive design
        updateSizeBarSize();
        updateEditorSize();
        editor.openNote(editor.getId());

        revalidate();
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void updateSizeBarSize() {
        sideBar.setPreferredSize(new Dimension((int) (getWidth() * 0.1), getHeight()));
        sideBar.removeAll();
        sideBar.init();
    }

    public void updateEditorSize() {
        editor.setPreferredSize(new Dimension((int) (getWidth() * 0.9), getHeight()));
        editor.removeAll();
        editor.init();

    }

}
