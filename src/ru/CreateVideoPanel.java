package ru;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class CreateVideoPanel {

    protected JPanel vPanel = new JPanel(new BorderLayout()); // Панель управления видео

    protected Canvas videoCanvas = new Canvas();

    public JPanel createVPanel() {
        vPanel.add(videoCanvas);
        videoCanvas.setBackground(Color.black);

        return vPanel;
    }
}
