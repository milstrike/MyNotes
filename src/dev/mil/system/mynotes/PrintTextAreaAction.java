package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

class PrintTextAreaAction extends AbstractAction {
    private final JTextArea textArea;

    public PrintTextAreaAction(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            boolean complete = textArea.print();
            if (!complete) {
                JOptionPane.showMessageDialog(null, "Printing was canceled.");
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Error during printing: " + ex.getMessage());
        }
    }
}
