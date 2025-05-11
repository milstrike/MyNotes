package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    public SplashScreen() {
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(240, 240, 240);
                Color color2 = new Color(200, 200, 255);
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, getHeight(), color2));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        content.setLayout(null);
        setContentPane(content);

        try {
            ImageIcon icon = new ImageIcon("me.png");
            Image scaled = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            JLabel photo = new JLabel(new ImageIcon(scaled));
            photo.setBounds(20, 20, 48, 48);
            content.add(photo);
        } catch (Exception ex) {
            // Ignore if image not found
        }

        JLabel appName = new JLabel("MyNotes");
        appName.setFont(new Font("SansSerif", Font.BOLD, 18));
        appName.setForeground(new Color(60, 60, 100));

        JLabel version = new JLabel("Version 1.0");
        version.setFont(new Font("SansSerif", Font.PLAIN, 12));
        version.setForeground(new Color(90, 90, 120));

        appName.setBounds(260, 180, 120, 25);
        version.setBounds(275, 205, 120, 20);

        content.add(appName);
        content.add(version);
    }

    public void showSplashThen(Runnable onFinish) {
        setVisible(true);
        Timer timer = new Timer(5000, e -> {
            setVisible(false);
            dispose();
            onFinish.run();
        });
        timer.setRepeats(false);
        timer.start();
    }

}
