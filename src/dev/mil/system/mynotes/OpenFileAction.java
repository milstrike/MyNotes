package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

class OpenFileAction implements ActionListener {
    private JTextArea textArea;

    public OpenFileAction(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                
                textArea.read(reader, null);

                if (textArea.getTopLevelAncestor() instanceof MyNotesFrame) {
                    MyNotesFrame frame = (MyNotesFrame) textArea.getTopLevelAncestor();
                    textArea.getDocument().addUndoableEditListener(frame.getUndoManager());
                    frame.getUndoManager().discardAllEdits();
                    frame.setCurrentFile(selectedFile, frame.getCurrentEncoding());
                    frame.updateMenuItems();
                }

                JScrollPane scrollPane = (JScrollPane) textArea.getParent().getParent();
                scrollPane.setRowHeaderView(new TextLineNumber(textArea));

                textArea.setCaretPosition(0);
                textArea.requestFocusInWindow();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + ex.getMessage());
            }
        }
    }
}
