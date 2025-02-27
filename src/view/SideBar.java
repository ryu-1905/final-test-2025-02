package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.Note;
import services.FileService;
import view.components.ButtonBuilder;

public final class SideBar extends JPanel {

    private static SideBar instance;

    private FileService fileService = new FileService();

    private JPanel notesListPanel = new JPanel();
    private JPanel functionButtonPanel = new JPanel();

    public void init() {
        setLayout(new BorderLayout());

        functionButtonPanel.setLayout(new BorderLayout());

        initAddNewNoteButton();

        initNotesListPanel();

        functionButtonPanel.removeAll();
        initSaveButton();
        initDeleteButton();

        add(functionButtonPanel, BorderLayout.SOUTH);
    }

    // Singleton design pattern
    public static SideBar getInstance() {
        if (instance == null) {
            instance = new SideBar();
        }
        return instance;
    }

    private void initAddNewNoteButton() {
        JButton newNoteButton = new ButtonBuilder()
                .icon(FileService.iconFolder + "plus.png", (int) (getPreferredSize().width * 0.25),
                        (int) (getPreferredSize().height * 0.03))
                .text("New Note").border(BorderFactory.createMatteBorder(0, 0, 10, 0, Color.gray))
                .backgroundColor(new Color(177, 240, 247))
                .actionListener(_ -> createNote())
                .build();

        add(newNoteButton, BorderLayout.NORTH);

    }

    private void initNotesListPanel() {

        notesListPanel.setLayout(new BoxLayout(notesListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(showNotesList(fileService.getNotesList()));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initSaveButton() {
        JButton saveButton = new ButtonBuilder()
                .icon(FileService.iconFolder + "save.png", (int) (getPreferredSize().width * 0.25),
                        (int) (getPreferredSize().height * 0.03 + 1))
                .text("Save")
                .backgroundColor(new Color(177, 240, 247))
                .maximumWidth(getPreferredSize().width)
                .actionListener(_ -> save())
                .build();

        functionButtonPanel.add(saveButton, BorderLayout.NORTH);
    }

    private void initDeleteButton() {
        JButton deleteButton = new ButtonBuilder()
                .icon(FileService.iconFolder + "delete.png", (int) (getPreferredSize().width * 0.25 + 1),
                        (int) (getPreferredSize().height * 0.03 + 1))
                .text("Delete")
                .backgroundColor(new Color(177, 240, 247))
                .maximumWidth(getPreferredSize().width)
                .actionListener(_ -> delete())
                .build();

        functionButtonPanel.add(deleteButton, BorderLayout.CENTER);
    }

    private JPanel showNotesList(List<Note> notes) {
        notesListPanel.removeAll();

        for (Note note : notes) {
            JButton noteButton = new ButtonBuilder()
                    .text(note.getTitle())
                    .backgroundColor(new Color(232, 249, 255))
                    .actionCommand(String.valueOf(note.getId()))
                    .border(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(197, 186, 255)))
                    .maximumWidth(getPreferredSize().width)
                    .actionListener((ActionListener) e -> openNote(Long.parseLong(e.getActionCommand())))
                    .build();

            notesListPanel.add(noteButton);
        }

        // reload panel
        notesListPanel.revalidate();
        notesListPanel.repaint();

        return notesListPanel;
    }

    private void createNote() {
        String title = JOptionPane.showInputDialog("Enter the title of the note", "New Note");

        fileService.createNote(fileService.getNotesList().size(), title);

        showNotesList(fileService.getNotesList());
    }

    private void openNote(long id) {
        Editor.getInstance().openNote(id);
    }

    private void save() {
        Editor editor = Editor.getInstance();

        fileService.updateNote(editor.getId(), editor.getText());
    }

    private void delete() {
        fileService.deleteNote(Editor.getInstance().getId());

        showNotesList(fileService.getNotesList());
    }
}
