/*
 * Класс для конфигурации контрольной панели видеоплеера
 */
package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import ru.*;

public class CreateVideoPlayerControlPanel {

    private JPanel vPCPanel = new JPanel(new GridLayout(2, 1)); // Панель управления видео
    private JPanel buttonsPanel = new JPanel(new FlowLayout());
    private JPanel timePanel = new JPanel(new BorderLayout());
    private JButton b1 = new JButton("stop");
    private JButton b2 = new JButton("play");
    private JLabel timeLabel = new JLabel("0"); // время
    private JLabel durationLabel = new JLabel(" "); // время
    private JSlider js = new JSlider();


    public JPanel createVPCPanel() {
        timePanel.add(timeLabel, BorderLayout.LINE_START);
        timePanel.add(js);
        js.setValue(0);
        timePanel.add(durationLabel, BorderLayout.LINE_END);
        vPCPanel.add(timePanel);

        buttonsPanel.add(b1);
        buttonsPanel.add(b2);
        vPCPanel.add(buttonsPanel);

        vPCPanel.setBackground(Color.white);

        return vPCPanel;
    }

    public JPanel getVPCPanel() {
        return vPCPanel;
    }

    public JButton getB1() {
        return b1;
    }

    public JButton getB2() {
        return b2;
    }

    public JSlider getJs() {
        return js;
    }

    public JPanel getvPCPanel() {
        return vPCPanel;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public JPanel getTimePanel() {
        return timePanel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getDurationLabel() {
        return durationLabel;
    }

}
