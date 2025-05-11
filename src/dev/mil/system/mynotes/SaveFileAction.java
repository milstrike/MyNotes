package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

class SaveFileAction implements ActionListener {
    private MyNotesFrame frame;

    public SaveFileAction(MyNotesFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = frame.getCurrentFile();
        if (file == null) {
            new SaveAsFileAction(frame).actionPerformed(e);
        } else {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), frame.getCurrentEncoding())) {
                frame.getTextArea().write(writer);
                ((JMenuItem) e.getSource()).setEnabled(false);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        }
    }
}
