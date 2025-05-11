package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToggleWordWrapAction extends AbstractAction {
    private final JTextArea textArea;
    private final MyNotesFrame frame;
    private final JMenuItem menuItem;
    private boolean isWrapped = false;

    public ToggleWordWrapAction(JTextArea textArea, MyNotesFrame frame, JMenuItem menuItem) {
        super("Word Wrap");
        this.textArea = textArea;
        this.frame = frame;
        this.menuItem = menuItem;
        updateLabel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isWrapped = !isWrapped;

        textArea.setLineWrap(isWrapped);
        textArea.setWrapStyleWord(isWrapped);

        JScrollPane scrollPane = frame.getScrollPane();
        if (scrollPane != null) {
            scrollPane.setHorizontalScrollBarPolicy(
                    isWrapped ? ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                              : ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
        }

        updateLabel();
    }

    private void updateLabel() {
        menuItem.setText((isWrapped ? "✓ " : "✗ ") + "Word Wrap");
    }
}
