package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GoToLineAction extends AbstractAction {
    private final JTextArea textArea;

    public GoToLineAction(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int totalLines = textArea.getLineCount();

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, totalLines, 1);
        JSpinner spinner = new JSpinner(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Go to line number:"), BorderLayout.NORTH);
        panel.add(spinner, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Go To Line", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int line = (Integer) spinner.getValue();
            try {
                int startOffset = textArea.getLineStartOffset(line - 1);
                int endOffset = textArea.getLineEndOffset(line - 1);
                textArea.setCaretPosition(startOffset);
                textArea.select(startOffset, endOffset);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }
}
