package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

 class FindReplaceAction extends AbstractAction {
    private final JTextArea textArea;
    private JDialog dialog;
    private JTextField findField;
    private JTextField replaceField;
    private int lastIndex = 0;

    public FindReplaceAction(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null) {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(textArea), "Find and Replace", false);
            dialog.setSize(400, 150);
            dialog.setLayout(new BorderLayout());
            dialog.setLocationRelativeTo(null);

            JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            fieldsPanel.add(new JLabel("Find:"));
            findField = new JTextField();
            findField.setFont(new Font("SansSerif", Font.PLAIN, 12));
            fieldsPanel.add(findField);
            fieldsPanel.add(new JLabel("Replace with:"));
            replaceField = new JTextField();
            replaceField.setFont(new Font("SansSerif", Font.PLAIN, 12));
            fieldsPanel.add(replaceField);

            JPanel buttonPanel = new JPanel();
            JButton findNextBtn = new JButton("Find Next");
            JButton replaceBtn = new JButton("Replace");
            JButton replaceAllBtn = new JButton("Replace All");

            findNextBtn.addActionListener(ev -> findNext());
            replaceBtn.addActionListener(ev -> replace());
            replaceAllBtn.addActionListener(ev -> replaceAll());

            buttonPanel.add(findNextBtn);
            buttonPanel.add(replaceBtn);
            buttonPanel.add(replaceAllBtn);

            dialog.add(fieldsPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
        }

        lastIndex = 0;
        findField.setText("");
        replaceField.setText("");
        dialog.setVisible(true);
    }

    private void findNext() {
        String keyword = findField.getText();
        if (keyword.isEmpty()) return;

        String text = textArea.getText();
        int index = text.indexOf(keyword, lastIndex + 1);
        if (index >= 0) {
            textArea.setCaretPosition(index);
            textArea.select(index, index + keyword.length());
            lastIndex = index;
        } else {
            JOptionPane.showMessageDialog(dialog, "No more matches found.");
        }
    }

    private void replace() {
        String keyword = findField.getText();
        String replacement = replaceField.getText();
        if (keyword.isEmpty()) return;

        int start = textArea.getSelectionStart();
        int end = textArea.getSelectionEnd();

        if (start != end && textArea.getSelectedText().equals(keyword)) {
            textArea.replaceRange(replacement, start, end);
            lastIndex = start + replacement.length() - 1;
        }
        findNext();
    }

    private void replaceAll() {
        String keyword = findField.getText();
        String replacement = replaceField.getText();
        if (keyword.isEmpty()) return;

        String text = textArea.getText();
        textArea.setText(text.replace(keyword, replacement));
        lastIndex = 0;
    }
}
