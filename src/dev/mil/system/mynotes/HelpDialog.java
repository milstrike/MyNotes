package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class HelpDialog extends JDialog {
    private final JTextArea explanationArea;
    private final Map<String, String> helpTopics;

    public HelpDialog(JFrame parent) {
        super(parent, "Help", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        helpTopics = new LinkedHashMap<>();
        helpTopics.put("Open", 
            "What it is:\nOpen allows you to load an existing text file into the editor.\n\n" +
            "How to do it:\nGo to File > Open, select a .txt file from your computer, and click Open.");

        helpTopics.put("Save", 
            "What it is:\nSaves your current text into the existing file without changing the name.\n\n" +
            "How to do it:\nClick File > Save or press Ctrl+S. If no file is associated yet, use Save As instead.");

        helpTopics.put("Save As", 
            "What it is:\nCreates a new file with the contents of the current editor.\n\n" +
            "How to do it:\nGo to File > Save As, choose a location and filename, then click Save.");

        helpTopics.put("Print", 
            "What it is:\nPrints the entire content of the editor to a connected printer.\n\n" +
            "How to do it:\nChoose File > Print, then follow the system's print dialog.");

        helpTopics.put("Undo/Redo", 
            "What it is:\nUndo reverts your last change, Redo re-applies it.\n\n" +
            "How to do it:\nUse Edit > Undo/Redo or press Ctrl+Z / Ctrl+Y. Also available in right-click menu.");

        helpTopics.put("Cut/Copy/Paste/Delete", 
            "What it is:\nStandard text editing operations for managing content.\n\n" +
            "How to do it:\nSelect text and use Edit menu or right-click to Cut (Ctrl+X), Copy (Ctrl+C), Paste (Ctrl+V), or Delete.");

        helpTopics.put("Find and Replace", 
            "What it is:\nFind locates specific words, Replace allows you to change them.\n\n" +
            "How to do it:\nGo to Edit > Find or Edit > Find and Replace. Use the input boxes and click Find/Replace.");

        helpTopics.put("Go To", 
            "What it is:\nJump directly to a specific line number.\n\n" +
            "How to do it:\nSelect Edit > Go To and enter the desired line number. Press Enter or click OK.");

        helpTopics.put("Word Wrap", 
            "What it is:\nToggles long lines to wrap inside the editor window instead of scrolling.\n\n" +
            "How to do it:\nUse View > Word Wrap. ✓ means active (no horizontal scroll), ✗ means disabled (scroll appears).");

        helpTopics.put("Font", 
            "What it is:\nChanges the font style and size used in the editor.\n\n" +
            "How to do it:\nGo to Edit > Font and choose from the font selection dialog.");

        helpTopics.put("Status Bar", 
            "What it is:\nDisplays real-time info like line, column, character, word, and line count.\n\n" +
            "How to do it:\nCheck the bottom of the window. Automatically updates while typing or selecting text.");

        helpTopics.put("Line Number", 
            "What it is:\nDisplays line numbers next to the text area.\n\n" +
            "How to do it:\nLine numbers appear automatically on the left. They adjust when text changes.");

        helpTopics.put("Context Menu", 
            "What it is:\nA quick-access menu when you right-click in the editor.\n\n" +
            "How to do it:\nRight-click inside the editor to access options like Undo, Redo, Cut, Copy, Paste.");


        JList<String> topicList = new JList<>(helpTopics.keySet().toArray(new String[0]));
        topicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(topicList);
        listScroll.setPreferredSize(new Dimension(150, 0));

        explanationArea = new JTextArea();
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane explanationScroll = new JScrollPane(explanationArea);
        explanationScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        topicList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = topicList.getSelectedValue();
                if (selected != null) {
                    explanationArea.setText(helpTopics.get(selected));
                    explanationArea.setCaretPosition(0);
                }
            }
        });

        topicList.setSelectedIndex(0);

        add(listScroll, BorderLayout.WEST);
        add(explanationScroll, BorderLayout.CENTER);
    }
}
