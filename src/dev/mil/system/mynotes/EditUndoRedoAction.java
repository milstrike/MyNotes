package dev.mil.system.mynotes;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;

 class EditUndoRedoAction {

    public static class Undo extends AbstractAction {
        private final JTextArea textArea;
        private final UndoManager undoManager;

        public Undo(JTextArea textArea, UndoManager undoManager) {
            super("Undo");
            this.textArea = textArea;
            this.undoManager = undoManager;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        }
    }

    public static class Redo extends AbstractAction {
        private final JTextArea textArea;
        private final UndoManager undoManager;

        public Redo(JTextArea textArea, UndoManager undoManager) {
            super("Redo");
            this.textArea = textArea;
            this.undoManager = undoManager;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        }
    }
}
