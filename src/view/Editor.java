package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import services.FileService;

public final class Editor extends JPanel {
    private static Editor instance;

    private long id;

    private FileService fileService = new FileService();

    JTextArea textArea;

    public void init() {
        setBackground(Color.white);
        initTextArea();
    }

    // Singleton design pattern
    public static Editor getInstance() {
        if (instance == null) {
            instance = new Editor();
        }
        return instance;
    }

    private void initTextArea() {
        textArea = new JTextArea(getPreferredSize().height, (int) (getPreferredSize().width * 0.095));
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        textArea.setEditable(false); // Disable editing

        JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);

        add(scroll);
    }

    public void openNote(long id) {
        this.id = id;

        textArea.setText(fileService.getNodeById(id).getContent());
        textArea.setEditable(true);
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return textArea.getText();
    }

}
