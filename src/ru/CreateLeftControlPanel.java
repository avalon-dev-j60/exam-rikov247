package ru;

import java.awt.Color;
import javax.swing.JPanel;

/**
 * Класс для создания и конфигурации левой панели на вкладке с видео
 */
public class CreateLeftControlPanel {

    private JPanel LeftCPanel = new JPanel();

    public JPanel createLeftCPanel() {
        LeftCPanel.setBackground(Color.white);

        return LeftCPanel;
    }

    public JPanel getLeftCPanel() {
        return LeftCPanel;
    }

}
