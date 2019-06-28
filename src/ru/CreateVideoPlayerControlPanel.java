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

/**
 * @author rikov247
 */
public class CreateVideoPlayerControlPanel {

    protected JPanel vPCPanel = new JPanel(new FlowLayout()); // Панель управления видео

    protected JButton b1 = new JButton("stop");
    protected JButton b2 = new JButton("pause");

    public JPanel createVPCPanel() {
        vPCPanel.add(b1);
        vPCPanel.add(b2);

        vPCPanel.setBackground(Color.white);

        return vPCPanel;
    }

    public JPanel getVPCPanel() {
        return vPCPanel;
    }

}
