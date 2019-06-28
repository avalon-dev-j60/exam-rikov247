package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.*;

public class CreateRightControlPanel {

    protected JPanel RightCPanel = new JPanel(new BorderLayout()); // Панель управления видео

    protected JLabel label = new JLabel("This is a RIGHT Control Panel");

    public JPanel createRightCPanel() {
        RightCPanel.add(label, BorderLayout.CENTER);

        RightCPanel.setBackground(Color.white);

        return RightCPanel;
    }

    public JPanel getLeftCPanel() {
        return RightCPanel;
    }

}
