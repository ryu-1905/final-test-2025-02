package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import models.Note;

public class FileService {
    private Path notesListPath = Path.of("./assets/data/notes_list.csv").normalize();
    private Path notesFolderPath = Path.of("./assets/data/notes/").normalize();

    public static final String iconFolder = "./assets/icons/";

    private boolean checkHeader() {
        StringBuilder headers = new StringBuilder();
        Field[] noteClassFields = Note.class.getDeclaredFields();

        // Build the headers string
        for (Field header : noteClassFields) {
            if (header.getName().equals("content")) {
                continue;
            }

            headers.append(header.getName()).append(",");
        }
        // Remove the last comma
        headers.deleteCharAt(headers.length() - 1);

        // Create the file if it does not exist
        if (Files.notExists(notesListPath)) {
            // Create the file and write the headers
            try {
                Files.writeString(notesListPath, headers.toString() + '\n', StandardOpenOption.CREATE);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<String> lines = new ArrayList<>();
        // Store csv file data and check if the headers are correct
        try {
            lines.addAll(Files.readAllLines(notesListPath));

            // If the headers are correct, return true
            if (!lines.isEmpty() && headers.toString().equals(lines.get(0))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check if the headers are not correct, update the file with the correct
        // headers
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(notesListPath.toString()))) {

            // Write the new headers to the file
            writer.write(headers.toString() + '\n');

            // Check if data exists
            if (lines.size() == 1) {
                return true;
            }

            // Write the old lines
            lines.remove(0);
            for (String line : lines) {
                writer.write(line + '\n');
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Note> getNotesList() {
        List<Note> notes = new ArrayList<>();

        // Check the file format and create headers if necessary
        checkHeader();

        // Read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(notesListPath.toString()))) {
            String line;

            // Skip the headers
            line = reader.readLine();

            // Read the notes
            while ((line = reader.readLine()) != null && !line.isBlank()) {
                String[] values = line.split(",");

                // Create a note object
                Note note = new Note();
                note.setId(Long.parseLong(values[0]));
                note.setTitle(values[1]);
                note.setDateCreated(LocalDateTime.parse(values[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                note.setDateModified(LocalDateTime.parse(values[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                // Add the note to the list
                notes.add(note);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public Note createNote(long id, String title) {
        LocalDateTime currentTime = LocalDateTime.now();

        // Create a new note
        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setDateCreated(currentTime);
        note.setDateModified(currentTime);

        try {
            // Write the note to the csv file
            Files.writeString(notesListPath,
                    String.join(",", String.valueOf(note.getId()), note.getTitle(),
                            note.getDateCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                            note.getDateModified().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) + '\n',
                    StandardOpenOption.APPEND);

            // Create a new txt file for the note content
            Files.writeString(notesFolderPath.resolve(id + ".txt"), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return note;
    }

    public Note getNodeById(long id) {
        Note note = new Note();

        Path notePath = notesFolderPath.resolve(id + ".txt");

        // Read the notes csv file and find the note with the given id
        try (BufferedReader reader = new BufferedReader(new FileReader(notesListPath.toString()))) {
            String line;

            // Skip the headers
            line = reader.readLine();

            while ((line = reader.readLine()) != null && !line.isBlank()) {
                String[] values = line.split(",");

                if (id == Long.parseLong(values[0])) {
                    note.setId(id);
                    note.setTitle(values[1]);
                    note.setDateCreated(LocalDateTime.parse(values[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    note.setDateModified(LocalDateTime.parse(values[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check if file exists and create file if not
        if (Files.notExists(notePath)) {
            try {
                Files.writeString(notePath, "", StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return note;
        }

        try {
            // Set the note's content from the txt file
            note.setContent(Files.readString(notePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return note;
    }

    public void updateNote(long id, String text) {
        List<String> lines = new ArrayList<>();
        // Store csv file data
        try {
            lines.addAll(Files.readAllLines(notesListPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Find the note in the csv file and update it
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notesListPath.toString()))) {
            writer.write(lines.get(0) + '\n');

            // Skip the headers
            lines.remove(0);

            for (String line : lines) {
                String[] values = line.split(",");
                long noteId = Long.parseLong(values[0]);

                if (id == noteId) {
                    // DateModified column
                    values[3] = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                    line = String.join(",", values);
                }

                writer.write(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.writeString(notesFolderPath.resolve(id + ".txt"), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(long id) {
        List<String> lines = new ArrayList<>();
        // Store csv file data
        try {
            lines.addAll(Files.readAllLines(notesListPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Find the note in the csv file and update it
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notesListPath.toString()))) {
            writer.write(lines.get(0) + '\n');

            // Skip the headers
            lines.remove(0);

            for (String line : lines) {
                String[] values = line.split(",");
                long noteId = Long.parseLong(values[0]);

                if (id == noteId) {
                    continue;
                }

                writer.write(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.delete(notesFolderPath.resolve(id + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
