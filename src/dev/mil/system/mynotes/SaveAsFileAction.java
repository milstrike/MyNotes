package dev.mil.system.mynotes;

import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.charset.Charset;

class SaveAsFileAction extends AbstractAction {
    private final MyNotesFrame frame;

    public SaveAsFileAction(MyNotesFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setSelectedFile(new File("newtextfile.txt"));

        JComboBox<String> encodingCombo = new JComboBox<>(new String[] {
            "UTF-8", "UTF-16", "ISO-8859-1", "US-ASCII", "windows-1252"
        });

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Comma Separated Values (*.csv)", "csv"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("HTML Files (*.html)", "html"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Java Source Files (*.java)", "java"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Markdown Files (*.md)", "md"));

        fileChooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                javax.swing.filechooser.FileNameExtensionFilter filter =
                        (javax.swing.filechooser.FileNameExtensionFilter) fileChooser.getFileFilter();
                if (filter != null) {
                    String ext = filter.getExtensions()[0];
                    File file = fileChooser.getSelectedFile();
                    if (file == null || file.getName().equals("")) {
                        file = new File("newtextfile." + ext);
                    } else {
                        String name = file.getName();
                        int dot = name.lastIndexOf(".");
                        if (dot != -1) name = name.substring(0, dot);
                        file = new File(file.getParent(), name + "." + ext);
                    }
                    fileChooser.setSelectedFile(file);
                }
            }
        });

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalStrut(140));
        JPanel encodingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        encodingPanel.add(new JLabel("Encoding:"));
        encodingPanel.add(encodingCombo);
        wrapper.add(encodingPanel);
        fileChooser.setAccessory(wrapper);

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = ((javax.swing.filechooser.FileNameExtensionFilter)
                    fileChooser.getFileFilter()).getExtensions()[0];
            if (!selectedFile.getName().toLowerCase().endsWith("." + extension)) {
                selectedFile = new File(selectedFile.getAbsolutePath() + "." + extension);
            }

            String charsetName = (String) encodingCombo.getSelectedItem();
            Charset encoding = Charset.forName(charsetName);

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(selectedFile), encoding)) {
                frame.getTextArea().write(writer);
                frame.setCurrentFile(selectedFile, encoding);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        }
    }
}