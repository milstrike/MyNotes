package dev.mil.system.mynotes;

import javax.swing.*;
import javax.swing.undo.UndoManager;

class MyNotesMenuBar extends JMenuBar {
    public MyNotesMenuBar(MyNotesFrame frame) {
        JTextArea textArea = frame.getTextArea();

        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new OpenFileAction(textArea));
        fileMenu.add(openItem);
        fileMenu.addSeparator();

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveFileAction(frame));
        frame.setSaveItem(saveItem);
        fileMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(new SaveAsFileAction(frame));
        fileMenu.add(saveAsItem);

        fileMenu.addSeparator();

        JMenuItem printItem = new JMenuItem("Print");
        printItem.addActionListener(new PrintTextAreaAction(textArea));
        fileMenu.add(printItem);

        JMenu editMenu = new JMenu("Edit");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(e -> {
            UndoManager um = frame.getUndoManager();
            if (um.canUndo()) um.undo();
        });

        editMenu.add(undoMenuItem);
        frame.setUndoItem(undoMenuItem);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.addActionListener(e -> {
            UndoManager um = frame.getUndoManager();
            if (um.canRedo()) um.redo();
        });

        editMenu.add(redoMenuItem);
        frame.setRedoItem(redoMenuItem);

        editMenu.addSeparator();

        JMenuItem cutMenuItem = new JMenuItem(new EditClipboardAction.Cut(textArea));
        cutMenuItem.setEnabled(true);
        editMenu.add(cutMenuItem);
        frame.setCutItem(cutMenuItem);

        JMenuItem copyMenuItem = new JMenuItem(new EditClipboardAction.Copy(textArea));
        copyMenuItem.setEnabled(true);
        editMenu.add(copyMenuItem);
        frame.setCopyItem(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem(new EditClipboardAction.Paste(textArea));
        pasteMenuItem.setEnabled(true);
        editMenu.add(pasteMenuItem);
        frame.setPasteItem(pasteMenuItem);

        JMenuItem deleteMenuItem = new JMenuItem(new EditClipboardAction.Delete(textArea));
        deleteMenuItem.setEnabled(true);
        editMenu.add(deleteMenuItem);
        frame.setDeleteItem(deleteMenuItem);

        editMenu.addSeparator();

        JMenuItem findItem = new JMenuItem("Find");
        findItem.addActionListener(new FindAction(textArea));
        editMenu.add(findItem);

        JMenuItem findReplaceItem = new JMenuItem("Find and Replace");
        findReplaceItem.addActionListener(new FindReplaceAction(textArea));
        editMenu.add(findReplaceItem);

        editMenu.addSeparator();

        JMenuItem goToItem = new JMenuItem("Go To");
        goToItem.addActionListener(new GoToLineAction(textArea));
        editMenu.add(goToItem);
        frame.setGoToItem(goToItem);
        goToItem.setEnabled(true);

        editMenu.addSeparator();

        JMenuItem fontItem = new JMenuItem("Font");
        fontItem.addActionListener(new FontFormatAction(textArea));
        editMenu.add(fontItem);

        JMenu viewMenu = new JMenu("View");
        JMenuItem wrapItem = new JMenuItem("✗ Word Wrap"); // default is off
        ToggleWordWrapAction wrapAction = new ToggleWordWrapAction(textArea, frame, wrapItem);
        wrapItem.addActionListener(wrapAction);
        viewMenu.add(wrapItem);


        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e -> new HelpDialog(frame).setVisible(true));
        helpMenu.add(helpItem);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> new AboutDialog(frame).setVisible(true));
        helpMenu.add(aboutItem);


        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(helpMenu);
    }
}
