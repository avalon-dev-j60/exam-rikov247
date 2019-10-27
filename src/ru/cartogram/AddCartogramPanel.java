package ru.cartogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Это панель для конфигурации подсчета, настройки и добавления нужной таблицы
 */
public class AddCartogramPanel {

    private ImageIcon background = new ImageIcon(this.getClass().getResource("/resources/icons/cartogram/map.png"));
    private JButton button = new JButton(background);

    JPanel panel = new JPanel(new BorderLayout());

    public JPanel AddMap() throws IOException {
        panel.setBackground(Color.white);
        // Настройка отображения самой кнопки
        button.setBorderPainted(false); // отключение прорисовки рамки кнопки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
        button.setPreferredSize(new Dimension(background.getIconWidth(), background.getIconHeight())); // задание размеров для кнопки = равных размеру иконки

        // Размещение кнопки ровно в центр области
        Box bv = Box.createVerticalBox(); // вертикальная коробка
        Box bh = Box.createHorizontalBox(); // горизонтальная коробка
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СЛЕВА)
        bh.add(button); // добавление кнопки
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СПРАВА)
        bv.add(bh); // добавляем горизонтальную коробку в вертикальную
        panel.add(bv); // добавляем коробку с кнопкой  на экран

        return panel;
    }

    public JButton getButton() {
        return button;
    }

}
