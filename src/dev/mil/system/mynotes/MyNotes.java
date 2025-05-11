
package dev.mil.system.mynotes;

import javax.swing.SwingUtilities;

public class MyNotes {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SplashScreen().showSplashThen(() -> new MyNotesFrame());
        });
    }

}
