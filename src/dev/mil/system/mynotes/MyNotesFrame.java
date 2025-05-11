package dev.mil.system.mynotes;

import javax.swing.*;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.nio.charset.Charset;
import javax.swing.text.DefaultEditorKit;
import java.io.File;

class MyNotesFrame extends JFrame {
    private JTextArea textArea;
    private StatusBar statusBar;
    private File currentFile = null;
    private Charset currentEncoding = Charset.defaultCharset();
    private JMenuItem saveItem;

    private JMenuItem undoItem;
    private JMenuItem redoItem;
    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem deleteItem;
    private JMenuItem goToItem;
    
    private JScrollPane scrollPane;


    private UndoManager undoManager = new UndoManager();

    public MyNotesFrame() {
        setTitle("MyNotes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setMargin(new Insets(10, 10, 10, 10));

        textArea.getDocument().addUndoableEditListener(undoManager);

        textArea.getDocument().addUndoableEditListener(e -> {
            updateMenuItems();
            updateFindMenuItems();
            updateStatusBar();
            if (saveItem != null) saveItem.setEnabled(true);
        });

        setJMenuBar(new MyNotesMenuBar(this));
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(new TextLineNumber(textArea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(scrollPane, BorderLayout.CENTER);

        statusBar = new StatusBar(currentEncoding.displayName());
        add(statusBar, BorderLayout.SOUTH);

        textArea.addCaretListener(e -> {
            updateMenuItems();
            updateStatusBar();
        });

        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(e -> updateMenuItems());

        setupKeyBindings();
        setupContextMenu();

        setVisible(true);
        updateMenuItems();
        updateStatusBar();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public void setCurrentFile(File file, Charset encoding) {
        this.currentFile = file;
        this.currentEncoding = encoding;
        setTitle("MyNotes - " + file.getAbsolutePath());
        statusBar.setEncoding(encoding.displayName());
        updateMenuItems();
        updateStatusBar();
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public Charset getCurrentEncoding() {
        return currentEncoding;
    }

    public void setGoToItem(JMenuItem goToItem) {
        this.goToItem = goToItem;
    }

    public void setCutItem(JMenuItem item) {
        this.cutItem = item;
    }

    public void setCopyItem(JMenuItem item) {
        this.copyItem = item;
    }

    public void setDeleteItem(JMenuItem item) {
        this.deleteItem = item;
    }

    public void setPasteItem(JMenuItem item) {
        this.pasteItem = item;
    }

    public void setUndoItem(JMenuItem item) {
        this.undoItem = item;
    }

    public void setRedoItem(JMenuItem item) {
        this.redoItem = item;
    }

    public void setSaveItem(JMenuItem saveItem) {
        this.saveItem = saveItem;
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }


    private void setupKeyBindings() {
        InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textArea.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "Undo");
        am.put("Undo", new EditUndoRedoAction.Undo(textArea, undoManager));

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "Redo");
        am.put("Redo", new EditUndoRedoAction.Redo(textArea, undoManager));

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), DefaultEditorKit.cutAction);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), DefaultEditorKit.copyAction);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), DefaultEditorKit.pasteAction);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK), "GoToLine");
        am.put("GoToLine", new GoToLineAction(textArea));
    }

    private void setupContextMenu() {
        JPopupMenu contextMenu = new JPopupMenu();

        undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(e -> {
            if (undoManager.canUndo()) undoManager.undo();
        });
        contextMenu.add(undoItem);

        redoItem = new JMenuItem("Redo");
        redoItem.addActionListener(e -> {
            if (undoManager.canRedo()) undoManager.redo();
        });
        contextMenu.add(redoItem);

        contextMenu.addSeparator();

        cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e -> textArea.cut());
        contextMenu.add(cutItem);

        copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(e -> textArea.copy());
        contextMenu.add(copyItem);

        pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(e -> textArea.paste());
        contextMenu.add(pasteItem);

        textArea.setComponentPopupMenu(contextMenu);

        updateMenuItems();
    }

    public void updateMenuItems() {
        boolean hasSelection = textArea.getSelectionStart() != textArea.getSelectionEnd();
        if (cutItem != null) cutItem.setEnabled(hasSelection);
        if (copyItem != null) copyItem.setEnabled(hasSelection);
        if (deleteItem != null) deleteItem.setEnabled(hasSelection);
        if (goToItem != null) goToItem.setEnabled(!textArea.getText().isEmpty());
        if (undoItem != null) undoItem.setEnabled(undoManager.canUndo());
        if (redoItem != null) redoItem.setEnabled(undoManager.canRedo());

        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            boolean hasText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            if (pasteItem != null) pasteItem.setEnabled(hasText);
        } catch (Exception ex) {
            if (pasteItem != null) pasteItem.setEnabled(false);
        }
    }

    void updateFindMenuItems() {
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            JMenu editMenu = menuBar.getMenu(1); 
            for (int i = 0; i < editMenu.getItemCount(); i++) {
                JMenuItem item = editMenu.getItem(i);
                if (item != null) {
                    String text = item.getText();
                    if ("Find".equals(text) || "Find and Replace".equals(text)) {
                        item.setEnabled(!textArea.getText().isEmpty());
                    }
                }
            }
        }
    }

    private void updateStatusBar() {
        int caretPos = textArea.getCaretPosition();
        int line = 1, column = 1;
        try {
            line = textArea.getLineOfOffset(caretPos);
            column = caretPos - textArea.getLineStartOffset(line);
            line += 1;
            column += 1;
        } catch (Exception e) {
            // Ignore
        }
        String content = textArea.getText();
        int totalChars = content.length();
        int totalWords = content.trim().isEmpty() ? 0 : content.trim().split("\\s+").length;
        int totalLines = textArea.getLineCount();

        statusBar.updateStatus(line, column, totalChars, totalWords, totalLines);
    }
}
