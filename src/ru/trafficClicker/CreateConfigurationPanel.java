package ru.trafficClicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import ru.JCheckBoxTree;

/**
 * Класс для создания Панели (в виде таблицы) Настройки подсчета.<n>
 * На вкладке таблицы.
 */
public class CreateConfigurationPanel {

    // Панель управления панелями кнопок
    public JComponent CreateConfigurationPanel() {
        JSplitPane vertSplit1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        vertSplit1.setDividerSize(3);
        vertSplit1.setContinuousLayout(true);

        JSplitPane horizSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizSplit1.setContinuousLayout(true);
        horizSplit1.setDividerSize(3);

        JSplitPane horizSplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizSplit2.setDividerSize(3);
        horizSplit2.setContinuousLayout(true);
        vertSplit1.setTopComponent(horizSplit1);

        JSplitPane vertSplit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        vertSplit2.setDividerSize(3);
        vertSplit2.setContinuousLayout(true);
        vertSplit1.setBottomComponent(vertSplit2);
        vertSplit2.setTopComponent(horizSplit2);

        // нижняя пустая панель для имитации нужных свойств панели (правильное отображение вертикальной divider)
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        vertSplit2.setBottomComponent(p);

        // Первая строка
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar1 = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        // Левый компонент
        JLabel label1 = new JLabel("Количество направлений: "); // Лэйбл
        // Установка фона лэйбла:
        label1.setOpaque(true);
        label1.setBackground(Color.WHITE);
        label1.setFocusable(false);
        label1.setToolTipText(label1.getText()); // Установка всплывающей подсказки

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p1.setBackground(Color.WHITE);
        p1.add(label1);

        JScrollPane sc1 = new JScrollPane(p1); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        sc1.setHorizontalScrollBar(scrollBar1);
        sc1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        horizSplit1.setLeftComponent(sc1);

        // ComboBox
        String[] items1 = {"1", "2", "3", "4", "5", "6"};
        JComboBox comboBox1 = new JComboBox(items1);
        comboBox1.setSelectedIndex(3);
        comboBox1.setFocusable(false);
        comboBox1.setBackground(Color.WHITE); // установка цвета заднего фона JComboBox
        ((JLabel) comboBox1.getRenderer()).setHorizontalAlignment(JLabel.CENTER); // выравнивание текста внутри JComboBox

        // Правый компонент
        horizSplit1.setRightComponent(comboBox1);

        // Вторая строка
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar2 = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        // Левый компонент
        JLabel label2 = new JLabel("Единицы подсчета: "); // Лэйбл
        label2.setOpaque(true);
        label2.setBackground(Color.WHITE);
        label2.setFocusable(false);
        label2.setToolTipText(label2.getText()); // Установка всплывающей подсказки

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.setBackground(Color.WHITE);
        p2.add(label2);

        JScrollPane sc2 = new JScrollPane(p2); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        sc2.setHorizontalScrollBar(scrollBar2);
        sc2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        horizSplit2.setLeftComponent(sc2); //Добавление конфигурированного лэйбла в панель

        // Правый компонент
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Дерево checkBoxов
        JCheckBoxTree checkBox = new JCheckBoxTree(); // создание дерева checkboxов
        checkBox.setFocusable(false);
        checkBox.setModel(createTreeModel()); // добавление нужных компонентов в него
        JScrollPane scrollPane = new JScrollPane(checkBox);
        scrollPane.setVisible(false); // исходно дерево checkboxов не видно

        // Кнопка для отображения/сокрытия дерева checkboxов
        JButton button = new JButton("Показать") {
            // для правильного отображения прозрачности кнопки
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        // настройка параметров отображения кнопки
        button.setFocusable(false); // отключаем возможность получения фокуса кнопкой
        button.setBorderPainted(false); // отключаем границу отображения кнопки
        button.setBackground(new Color(0, 0, 0, 0)); // устанавливаем прозрачный фон для кнопки
        button.setOpaque(false); // устанавливаем прозрачность кнопки
        // добавление слушателя к кнопке: отображения и сокрытие дерева checkboxов (а также установка позиции вертикальной divider
        button.addActionListener((e) -> {
            if (scrollPane.isVisible()) {
                scrollPane.setVisible(false);
                button.setText("Показать");
                vertSplit2.setDividerLocation(button.getHeight() + 4);
            } else {
                scrollPane.setVisible(true);
                button.setText("Свернуть");
                vertSplit2.setDividerLocation(200);
            }
        });

        panel.add(button, BorderLayout.NORTH); // добавляем кнопку
        panel.add(scrollPane, BorderLayout.CENTER); // добавляем дерево checkboxов внутри скролящейся панели
        horizSplit2.setRightComponent(panel);

        // Отслеживание положения divider разделенной панели для синхронизации нижней divider и верхней
        horizSplit1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit2.setDividerLocation((int) e.getNewValue());
        });

        horizSplit2.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit1.setDividerLocation((int) e.getNewValue());
        });

        // Убрать границу (тень) вокруг divider
        // UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder()); // Убрать границу (тень) вокруг divider для всех SplitPane в приложении
        vertSplit1.setBorder(BorderFactory.createEmptyBorder());
        vertSplit2.setBorder(BorderFactory.createEmptyBorder());
        horizSplit1.setBorder(BorderFactory.createEmptyBorder());
        horizSplit2.setBorder(BorderFactory.createEmptyBorder());

        return vertSplit1;
    }

    // описание дерева checkBoxов (наполнения)
    private TreeModel createTreeModel() {
        final String ROOT = "Учитываемые единицы";
        // Массив листьев деревьев
        final String[] nodes = new String[]{"Легковые", "Автобусы", "Грузовые", "Автопоезда", "Пешеходы", "Велотранспорт", "Трамвай"};
        final String[][] leafs = new String[][]{
            {"Особо малого класса", "Малого класса", "Среднего класса", "Большого класса", "Особо большого класса"},
            {"2-осные", "3-осные", "4-осные", "4-осные (2 оси+прицеп)", "5-осные (3 оси+прицеп)"},
            {"3 осные (2 оси+полуприцеп)", "4 осные (2 оси+полуприцеп)", "5 осные (2 оси+полуприцеп)", "5 осные (3 оси+полуприцеп)", "6 осные", "7 осные и более"}};
        // Корневой узел дерева
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Добавление ветвей - потомков 1-го уровня
        DefaultMutableTreeNode car = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode bus = new DefaultMutableTreeNode(nodes[1]);
        DefaultMutableTreeNode truck = new DefaultMutableTreeNode(nodes[2]);
        DefaultMutableTreeNode roadTrains = new DefaultMutableTreeNode(nodes[3]);
        DefaultMutableTreeNode people = new DefaultMutableTreeNode(nodes[4]);
        DefaultMutableTreeNode bicycle = new DefaultMutableTreeNode(nodes[5]);
        DefaultMutableTreeNode tram = new DefaultMutableTreeNode(nodes[6]);
        // Добавление ветвей к корневой записи
        root.add(car);
        root.add(bus);
        root.add(truck);
        root.add(roadTrains);
        root.add(people);
        root.add(bicycle);
        root.add(tram);
        // Добавление листьев - потомков 2-го уровня
        for (int i = 0; i < leafs[0].length; i++) {
            bus.add(new DefaultMutableTreeNode(leafs[0][i]));
        }
        for (int i = 0; i < leafs[1].length; i++) {
            truck.add(new DefaultMutableTreeNode(leafs[1][i]));
        }
        for (int i = 0; i < leafs[1].length; i++) {
            roadTrains.add(new DefaultMutableTreeNode(leafs[2][i]));
        }
        // Создание стандартной модели
        return new DefaultTreeModel(root);
    }

}
