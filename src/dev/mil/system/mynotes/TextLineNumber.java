package dev.mil.system.mynotes;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class TextLineNumber extends JPanel implements DocumentListener {
    private final JTextArea textArea;
    private final int padding = 5;

    public TextLineNumber(JTextArea textArea) {
        this.textArea = textArea;
        setFont(new Font("Monospaced", Font.PLAIN, 12));
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.BLACK);
        textArea.getDocument().addDocumentListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        int lines = textArea.getLineCount();
        int digits = Math.max(3, String.valueOf(lines).length());
        int width = getFontMetrics(getFont()).charWidth('0') * digits + padding * 2;
        return new Dimension(width, textArea.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fm = getFontMetrics(getFont());
        Insets insets = textArea.getInsets();
        int availableWidth = getWidth() - padding;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getForeground());

        Rectangle clip = g.getClipBounds();
        int startOffset, endOffset;
        try {
            startOffset = textArea.viewToModel(new Point(0, clip.y));
            endOffset = textArea.viewToModel(new Point(0, clip.y + clip.height));
        } catch (Exception e) {
            return;
        }

        try {
            int startLine = textArea.getLineOfOffset(startOffset);
            int endLine = textArea.getLineOfOffset(endOffset);

            for (int line = startLine; line <= endLine; line++) {
                int lineStartOffset = textArea.getLineStartOffset(line);
                Rectangle r = textArea.modelToView(lineStartOffset);
                if (r == null) continue;

                String lineNumber = String.valueOf(line + 1);
                int x = availableWidth - fm.stringWidth(lineNumber);
                int y = r.y + r.height - fm.getDescent();

                g2d.drawString(lineNumber, x, y);
            }
        } catch (BadLocationException e) {
            // Ignore bad offset
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        repaint();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        repaint();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        repaint();
    }
}
