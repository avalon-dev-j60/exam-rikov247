package ru.cartogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Это панель для конфигурации подсчета, настройки и добавления нужной таблицы
 */
public class AddCartogramPanel {

    private JLabel label = new JLabel();

    JPanel panel = new JPanel(new BorderLayout());

    public JPanel AddMap() throws IOException {
        panel.setBackground(Color.white);
        // Настройка отображения label (текст)
        label.setBackground(Color.white);
        label.setText("<html>"
                + "<b>"
                + "<i>"
                + "<div style='text-align: center;'>"
                + "<span style='color:black; font-size:50pt; font-family:Times New Roman;'>"
                + "Здесь будет картограмма"
                + "</span></div></b></i></html>");
        label.setHorizontalAlignment(JLabel.CENTER);

        // Размещение кнопки ровно в центр области
        Box bv = Box.createVerticalBox(); // вертикальная коробка
        Box bh = Box.createHorizontalBox(); // горизонтальная коробка
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СЛЕВА)
        bh.add(label); // добавление кнопки
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СПРАВА)
        bv.add(bh); // добавляем горизонтальную коробку в вертикальную
        panel.add(bv); // добавляем коробку с кнопкой  на экран

        return panel;
    }

}
