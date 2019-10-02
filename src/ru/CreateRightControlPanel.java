package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateRightControlPanel {

    private JPanel RightCPanel = new JPanel(new BorderLayout()); // Панель управления видео

    private JLabel label = new JLabel("");

    public JPanel createRightCPanel() {
        RightCPanel.add(label, BorderLayout.CENTER);
        RightCPanel.setBackground(Color.white);

        return RightCPanel;
    }

    public JPanel getRightCPanel() {
        return RightCPanel;
    }

    public JLabel getLabel() {
        return label;
    }

}
