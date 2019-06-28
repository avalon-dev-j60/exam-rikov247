package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.*;

public class CreateLeftControlPanel {

    protected JPanel LeftCPanel = new JPanel(new BorderLayout()); // Панель управления видео

    protected JLabel label = new JLabel("This is a LEFT Control Panel");

    public JPanel createLeftCPanel() {
        LeftCPanel.add(label, BorderLayout.CENTER);

        LeftCPanel.setBackground(Color.white);

        return LeftCPanel;
    }

    public JPanel getLeftCPanel() {
        return LeftCPanel;
    }
}
