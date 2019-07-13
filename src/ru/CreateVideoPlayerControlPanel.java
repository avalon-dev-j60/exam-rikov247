/*
 * Класс для конфигурации контрольной панели видеоплеера
 */
package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import ru.*;

public class CreateVideoPlayerControlPanel {

    private JPanel vPCPanel = new JPanel(new GridLayout(2, 1)); // Панель управления видео
    private JPanel buttonsPanel = new JPanel(new FlowLayout());
    private JPanel timePanel = new JPanel(new BorderLayout());

    private ImageIcon stopIcon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/media/Stop24.gif"));
    private ImageIcon pauseIcon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/media/Pause24.gif"));
    private ImageIcon playIcon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/media/Play24.gif"));
    private JButton b1 = new JButton(stopIcon);
    private JButton b2 = new JButton(playIcon);

    private JLabel timeLabel = new JLabel(" "); // время в данный момент
    private JLabel durationLabel = new JLabel(" "); // продолжительность видео
    private JSlider js = new JSlider(); // слайдер времени

    public JPanel createVPCPanel() {
        timePanel.add(timeLabel, BorderLayout.LINE_START);
        timePanel.add(js);
        js.setValue(0);
        timePanel.add(durationLabel, BorderLayout.LINE_END);
        vPCPanel.add(timePanel);

        buttonsPanel.add(createButtonWithIcon(b1, stopIcon)); // добавляем кнопку СТОП
        buttonsPanel.add(createButtonWithIcon(b2, playIcon)); // добавляем кнопку Пазуа/Плэй
        
        vPCPanel.add(buttonsPanel); // добавляем панель кнопок 

        vPCPanel.setBackground(Color.white); // устанавливаем задний фон панели управления видео

        return vPCPanel;
    }
    
    private JButton createButtonWithIcon(JButton button, ImageIcon icon) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        
        return button;
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

    public ImageIcon getPauseIcon() {
        return pauseIcon;
    }

    public ImageIcon getPlayIcon() {
        return playIcon;
    }

}
