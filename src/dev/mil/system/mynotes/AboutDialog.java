package dev.mil.system.mynotes;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parent) {
        super(parent, "About MyNotes", true);
        setSize(550, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        int sideMargin = 15;

        // === Header with Image + Info ===
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, sideMargin, 5, sideMargin));
        headerPanel.setBackground(Color.WHITE);

        // Profile image
        JLabel iconLabel;
        try {
            ImageIcon icon = new ImageIcon("me.png");
            Image scaled = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            iconLabel = new JLabel(new ImageIcon(scaled));
            iconLabel.setVerticalAlignment(SwingConstants.TOP); // ✅ Align to top
        } catch (Exception ex) {
            iconLabel = new JLabel();
        }

        // App info lines with tighter vertical layout
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel nameLabel = new JLabel("MyNotes");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel versionLabel = new JLabel("Version 1.0");
        JLabel descLabel = new JLabel("A school project by Muhammad Irfan Luthfi");
        JLabel linkLabel = new JLabel("github.com/milstrike");
        linkLabel.setForeground(Color.BLUE);

        Font infoFont = new Font("SansSerif", Font.PLAIN, 13);
        versionLabel.setFont(infoFont);
        descLabel.setFont(infoFont);
        linkLabel.setFont(infoFont);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(2)); // tighter spacing
        infoPanel.add(versionLabel);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(linkLabel);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.CENTER);

        // === About Description ===
        JTextArea aboutArea = new JTextArea(
            "This software was developed to help students and developers manage plain text efficiently.\n\n" +
            "It features essential editing tools, undo/redo, line numbering, word wrap, and integrated help.\n" +
            "The minimalist interface is designed to promote focus and productivity.\n\n" +
            "Built with Java Swing for cross-platform compatibility, MyNotes demonstrates clean, extensible UI architecture."
        );
        aboutArea.setEditable(false);
        aboutArea.setWrapStyleWord(true);
        aboutArea.setLineWrap(true);
        aboutArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        aboutArea.setBackground(UIManager.getColor("Panel.background"));
        aboutArea.setBorder(BorderFactory.createEmptyBorder(0, sideMargin, 0, sideMargin));

        // === License Text Area ===
        JTextArea licenseArea = new JTextArea(getLicenseText());
        licenseArea.setEditable(false);
        licenseArea.setLineWrap(true);
        licenseArea.setWrapStyleWord(true);
        licenseArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane licenseScroll = new JScrollPane(licenseArea);
        licenseScroll.setBorder(BorderFactory.createTitledBorder("License"));
        licenseScroll.setPreferredSize(new Dimension(480, 160));
        licenseScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        licenseScroll.setBorder(BorderFactory.createEmptyBorder(10, sideMargin, 10, sideMargin));

        // === Close Button ===
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanel.add(closeButton);

        // === Assemble ===
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(aboutArea, BorderLayout.NORTH);
        centerPanel.add(licenseScroll, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getLicenseText() {
        return "MIT License\n\n" +
               "Copyright (c) 2024 Muhammad Irfan Luthfi\n\n" +
               "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
               "of this software and associated documentation files (the \"Software\"), to deal\n" +
               "in the Software without restriction, including without limitation the rights\n" +
               "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
               "copies of the Software, and to permit persons to whom the Software is\n" +
               "furnished to do so, subject to the following conditions:\n\n" +
               "The above copyright notice and this permission notice shall be included in all\n" +
               "copies or substantial portions of the Software.\n\n" +
               "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
               "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
               "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
               "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
               "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
               "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
               "SOFTWARE.";
    }
}
