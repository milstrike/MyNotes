package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class FontFormatAction extends AbstractAction {
    private final JTextArea textArea;

    public FontFormatAction(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = ge.getAvailableFontFamilyNames();

        JComboBox<String> fontBox = new JComboBox<>(fonts);
        JComboBox<String> styleBox = new JComboBox<>(new String[] { "Plain", "Bold", "Italic", "Bold Italic" });
        JComboBox<Integer> sizeBox = new JComboBox<>(new Integer[] { 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40 });

        JLabel previewLabel = new JLabel("AaBbCcDdEe");
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setBorder(BorderFactory.createTitledBorder("Preview"));
        previewLabel.setPreferredSize(new Dimension(300, 50));

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        dialogPanel.add(new JLabel("Font:"), gbc);
        gbc.gridx = 1;
        dialogPanel.add(fontBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialogPanel.add(new JLabel("Style:"), gbc);
        gbc.gridx = 1;
        dialogPanel.add(styleBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialogPanel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1;
        dialogPanel.add(sizeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        dialogPanel.add(previewLabel, gbc);

        Runnable updatePreview = () -> {
            String fontName = (String) fontBox.getSelectedItem();
            int fontSize = (Integer) sizeBox.getSelectedItem();
            String styleName = (String) styleBox.getSelectedItem();
            int style = Font.PLAIN;
            if ("Bold".equals(styleName)) style = Font.BOLD;
            else if ("Italic".equals(styleName)) style = Font.ITALIC;
            else if ("Bold Italic".equals(styleName)) style = Font.BOLD | Font.ITALIC;
            previewLabel.setFont(new Font(fontName, style, fontSize));
        };

        fontBox.addActionListener(ev -> updatePreview.run());
        styleBox.addActionListener(ev -> updatePreview.run());
        sizeBox.addActionListener(ev -> updatePreview.run());
        updatePreview.run();

        JDialog fontDialog = new JDialog((Frame) null, "Font", true);
        fontDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        fontDialog.setIconImage(null);
        fontDialog.setResizable(false);
        fontDialog.getContentPane().add(dialogPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttons.add(okButton);
        buttons.add(cancelButton);
        fontDialog.getContentPane().add(buttons, BorderLayout.SOUTH);

        final boolean[] approved = {false};
        okButton.addActionListener(ev -> {
            approved[0] = true;
            fontDialog.dispose();
        });
        cancelButton.addActionListener(ev -> fontDialog.dispose());

        fontDialog.pack();
        fontDialog.setLocationRelativeTo(null);
        fontDialog.setVisible(true);

        int result = approved[0] ? JOptionPane.OK_OPTION : JOptionPane.CANCEL_OPTION;
        if (result == JOptionPane.OK_OPTION) {
            String fontName = (String) fontBox.getSelectedItem();
            int fontSize = (Integer) sizeBox.getSelectedItem();
            String styleName = (String) styleBox.getSelectedItem();
            int style = Font.PLAIN;
            if ("Bold".equals(styleName)) style = Font.BOLD;
            else if ("Italic".equals(styleName)) style = Font.ITALIC;
            else if ("Bold Italic".equals(styleName)) style = Font.BOLD | Font.ITALIC;
            textArea.setFont(new Font(fontName, style, fontSize));
        }
    }
}
