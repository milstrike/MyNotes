package dev.mil.system.mynotes;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class FindAction extends AbstractAction {
    private final JTextArea textArea;
    private JDialog dialog;
    private JTextField inputField;
    private Highlighter.HighlightPainter painter;
    private int lastIndex = 0;

    public FindAction(JTextArea textArea) {
        this.textArea = textArea;
        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null) {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(textArea), "Find", false);
            dialog.setSize(350, 120);
            dialog.setLayout(new BorderLayout());
            dialog.setLocationRelativeTo(null);

            JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
            inputPanel.add(new JLabel("Find:"), BorderLayout.WEST);
            inputField = new JTextField();
            inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
            inputField.setPreferredSize(new Dimension(200, 30));
            inputField.setMinimumSize(new Dimension(200, 30));
            inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            inputField.setFont(new Font("SansSerif", Font.PLAIN, 12));
            inputPanel.add(inputField, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton findNext = new JButton("Find Next");
            JButton findPrev = new JButton("Find Before");
            findNext.addActionListener(a -> find(true));
            findPrev.addActionListener(a -> find(false));
            buttonPanel.add(findNext);
            buttonPanel.add(findPrev);

            dialog.add(inputPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
        }

        inputField.setText("");
        clearHighlights();
        lastIndex = 0;
        dialog.setVisible(true);
    }

    private void find(boolean forward) {
        String keyword = inputField.getText();
        if (keyword.isEmpty()) return;

        try {
            String text = textArea.getText();
            int pos = forward
                    ? text.indexOf(keyword, lastIndex + 1)
                    : text.lastIndexOf(keyword, lastIndex - 1);

            if (pos >= 0) {
                int end = pos + keyword.length();
                textArea.setCaretPosition(pos);
                textArea.select(pos, end);
                lastIndex = pos;
                clearHighlights();
                textArea.getHighlighter().addHighlight(pos, end, painter);
            } else {
                JOptionPane.showMessageDialog(dialog, "No more matches found.");
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    private void clearHighlights() {
        Highlighter hilite = textArea.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();
        for (Highlighter.Highlight h : hilites) {
            if (h.getPainter() == painter) {
                hilite.removeHighlight(h);
            }
        }
    }
}
