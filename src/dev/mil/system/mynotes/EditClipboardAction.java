package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.event.ActionEvent;

class EditClipboardAction {

    public static class Paste extends AbstractAction {
        private final JTextArea textArea;

        public Paste(JTextArea textArea) {
            super("Paste");
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.paste();
        }
    }

    public static class Copy extends AbstractAction {
        private final JTextArea textArea;

        public Copy(JTextArea textArea) {
            super("Copy");
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.copy();
        }
    }

    public static class Cut extends AbstractAction {
        private final JTextArea textArea;

        public Cut(JTextArea textArea) {
            super("Cut");
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.cut();
        }
    }

    public static class Delete extends AbstractAction {
        private final JTextArea textArea;

        public Delete(JTextArea textArea) {
            super("Delete");
            this.textArea = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.replaceSelection("");
        }
    }
}