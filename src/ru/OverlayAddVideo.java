/**
 * Область добавления видео
 */
package ru;

import com.sun.jna.platform.WindowUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OverlayAddVideo extends Window {

    private ImageIcon beforeIcon = new ImageIcon(this.getClass().getResource("/icons/before.png"));
    private ImageIcon afterIcon = new ImageIcon(this.getClass().getResource("/icons/after.png"));
    private JButton button = new JButton(beforeIcon);

    public OverlayAddVideo(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        setBackground(Color.WHITE); // установка цвета задника overlay панели
        setLayout(new BorderLayout()); // менеджер компоновки

        button.setBorderPainted(false); // отключение прорисовки рамки кнопки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
        button.setPreferredSize(new Dimension(beforeIcon.getIconWidth(), beforeIcon.getIconHeight())); // задание размеров для кнопки = равных размеру иконки

        // Размещение кнопки ровно в центр области
        Box bv = Box.createVerticalBox(); // вертикальная коробка
        Box bh = Box.createHorizontalBox(); // горизонтальная коробка
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СЛЕВА)
        bh.add(button); // добавление кнопки
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СПРАВА)
        bv.add(bh); // добавляем горизонтальную коробку в вертикальную
        add(bv); // добавляем коробку с кнопкой  на экран
    }

    public JButton getButton() {
        return button;
    }

    public ImageIcon getBeforeIcon() {
        return beforeIcon;
    }

    public ImageIcon getAfterIcon() {
        return afterIcon;
    }

}
