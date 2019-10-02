/**
 * Класс для добавления встречающей пользователя панели добавления видео
 */
package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AddVideoPanel {

    private ImageIcon beforeIcon = new ImageIcon(this.getClass().getResource("/icons/before.png"));
    private ImageIcon afterIcon = new ImageIcon(this.getClass().getResource("/icons/after.png"));
    private JButton button = new JButton(beforeIcon);

    // Основная overlay панель, на которую помечаются кнопки. LayoutManager = null
    private JPanel addVideoPanel = new JPanel(new BorderLayout());

    public JPanel AddVideo() {
        addVideoPanel.setBackground(Color.WHITE);

        // Настройка отображения самой кнопки
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
        addVideoPanel.add(bv); // добавляем коробку с кнопкой  на экран
        
        // Добавление слушателей
        button.addMouseListener(addVideoAdapter);

        return addVideoPanel;
    }

    // ВЫБОР ВИДЕО НА СТАРТОВОЙ СТРАНИЦЕ
    private MouseAdapter addVideoAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setIcon(afterIcon);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setIcon(beforeIcon);
        }
    };

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
