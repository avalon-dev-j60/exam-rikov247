package ru.trafficClicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Это панель для конфигурации подсчета, настройки и добавления нужной таблицы
 */
public class AddTablePanel {

    private JLabel label = new JLabel();

    // Основная overlay панель, на которую помечаются кнопки. LayoutManager = null
    private JPanel addTablePanel = new JPanel(new BorderLayout());

    public JPanel AddTable() {
        addTablePanel.setBackground(Color.WHITE);
        // Настройка отображения label (текст)
        label.setBackground(Color.white);
        label.setText("<html>"
                + "<b>"
                + "<i>"
                + "<div style='text-align: center;'>"
                + "<span style='color:black; font-size:40pt; font-family:Times New Roman;'>"
                + "Здесь будут таблицы данных" + "<br/>" + "Создайте проект!"
                + "</span></div></b></i></html>");
        label.setHorizontalAlignment(JLabel.CENTER);

        // Настройка отображения самой label (текст)
        // Размещение кнопки ровно в центр области
        Box bv = Box.createVerticalBox(); // вертикальная коробка
        Box bh = Box.createHorizontalBox(); // горизонтальная коробка
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СЛЕВА)
        bh.add(label); // добавление кнопки
        bh.add(Box.createGlue()); // заполнитель "клей" - чтобы от краев отставала кнопка (СПРАВА)
        bv.add(bh); // добавляем горизонтальную коробку в вертикальную
        addTablePanel.add(bv); // добавляем коробку с кнопкой  на экран

        return addTablePanel;
    }

}
