package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;

class StatusBar extends JPanel {
    private JLabel statusLabel;
    private JLabel encodingLabel;

    public StatusBar(String encoding) {
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Ln 1, Col 1 | Chars: 0 | Words: 0 | Lines: 1");
        encodingLabel = new JLabel("Encoding: " + encoding);

        add(statusLabel, BorderLayout.WEST);
        add(encodingLabel, BorderLayout.EAST);
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    }

    public void updateStatus(int line, int column, int totalChars, int totalWords, int totalLines) {
        statusLabel.setText(String.format("Ln %d, Col %d | Chars: %d | Words: %d | Lines: %d",
                line, column, totalChars, totalWords, totalLines));
    }

    public void setEncoding(String encoding) {
        encodingLabel.setText("Encoding: " + encoding);
    }
}
