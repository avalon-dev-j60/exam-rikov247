/*
 * Класс для конфигурации контрольной панели видеоплеера
 */
package ru;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import ru.*;

public class CreateVideoPlayerControlPanel {

    private JPanel vPCPanel = new JPanel(new FlowLayout()); // Панель управления видео

    private JButton b1 = new JButton("stop");
    private JButton b2 = new JButton("pause");

    public JPanel createVPCPanel() {
        vPCPanel.add(b1);
        vPCPanel.add(b2);

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

}
