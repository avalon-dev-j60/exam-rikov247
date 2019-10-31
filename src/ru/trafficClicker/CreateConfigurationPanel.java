package ru.trafficClicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.view.JBroTable;
import ru.Excel.Save.templates.FileSaveWithPattern;
import ru.Excel.Save.templates.savePattern.SaveInExistingFile;
import ru.JCheckBoxTree;
import ru.cartogram.CreateCartogram;
import ru.jtable.Table;
import ru.jtable.model.*;

/**
 * Класс для создания Панели (в виде таблицы) Настройки подсчета.<n>
 * На вкладке таблицы.
 */
public class CreateConfigurationPanel {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this); // переменная позволяющая добавить в этот слушатель изменения свойств 

    private JPanel cartogramPanel;
    private CreateCartogram cartogram;

    private FileSaveWithPattern fileSaveWithPattern;
    private String fullFileName;
    private String fullName;
    private String kindOfStatement;
    private String typeOfDirection;
    private int page = 0;

    private JBroTable table = new JBroTable(); // Таблица

    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JCheckBoxTree checkBox;
    private TreePath[] paths;
    private JScrollPane scrollPane;

    private JSplitPane vertSplit1; // (вертикальная панель с разделением по горизонтали) - вертикальная потому что она вытянута по вертикали
    private JSplitPane horizSplit1; // (горизонтальная панель с разделением по вертикали) - горизонтальная потому что она вытянута по горизонтали
    private JSplitPane horizSplit2;
    private JSplitPane vertSplit2;
    private JSplitPane vertSplit3;
    private JSplitPane horizSplit3;
    private JSplitPane vertSplitRight1;
    private JSplitPane horizSplitRight1;
    private JSplitPane vertSplitRight2;
    private JSplitPane horizSplitRight2;
    private JSplitPane vertSplitRight3;
    private JSplitPane horizSplitRight3;
    private JSplitPane vertSplitRight4;
    private JSplitPane horizSplitRight4;
    private JSplitPane vertSplitRight5;
    private JSplitPane horizSplitRight5;
    private JSplitPane vertSplitRight6;
    private JSplitPane horizSplitRight6;
    private JSplitPane vertSplitRight7;
    private JSplitPane horizSplitRight7;
    private JSplitPane vertSplitRight8;
    private JSplitPane horizSplitRight8;
    private JSplitPane vertSplitRight9;
    private JSplitPane horizSplitRight9;

    // Правая панель
    private JLabel label1;

    private JButton save15 = new JButton();
    private JButton save30 = new JButton();
    private JButton save45 = new JButton();
    private JButton save60 = new JButton();

    private JLabel label2;

    private JButton buttonReset = new JButton();

    private JRadioButton morningRadio = new JRadioButton("", true);
    private JRadioButton dayRadio = new JRadioButton();
    private JRadioButton eveningRadio = new JRadioButton();

    // Панель управления панелями кнопок
    // В вертикальную панель наверх устанавливаем горизонтальную панель, а вниз устанавливаем вертикальную панель.
    // В новую вертикальную панель навеох добавляем горизонитальную панель, а вниз ...
    public JComponent CreateLeftConfigurationPanel() {
        vertSplit1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true); // устанавливаем в положение true continuousLayout - для постоянного отображения divider (разделительной линии) при ее перемещении
        vertSplit1.setDividerSize(3);

        horizSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit1.setDividerSize(3);

        horizSplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit2.setDividerSize(3);

        vertSplit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplit2.setDividerSize(3);

        vertSplit3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplit3.setDividerSize(3);
        vertSplit3.setDividerLocation(150); // установка положения разделительной линии первоначальное

        horizSplit3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit3.setDividerSize(3);

        // Верхняя панель
        vertSplit1.setTopComponent(horizSplit3);
        vertSplit1.setBottomComponent(vertSplit2);
        vertSplit2.setTopComponent(horizSplit1);
        vertSplit2.setBottomComponent(vertSplit3);
        vertSplit3.setTopComponent(horizSplit2);
        vertSplit3.setBottomComponent(acceptedParametersPanel());

        // Первая строка
        horizontalPanel1();

        // Вторая строка
        horizontalPanel2();

        // Третья строка
        horizontalPanel3();

        // Отслеживание положения divider разделенной панели для синхронизации вертикальных divider (в горизонтальных панелях) всех уровней
        dividerTracking();

        // Убрать границу (тень) вокруг divider
        // UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder()); // Убрать границу (тень) вокруг divider для всех SplitPane в приложении
        vertSplit1.setBorder(BorderFactory.createEmptyBorder());
        vertSplit2.setBorder(BorderFactory.createEmptyBorder());
        horizSplit1.setBorder(BorderFactory.createEmptyBorder());
        horizSplit2.setBorder(BorderFactory.createEmptyBorder());
        vertSplit3.setBorder(BorderFactory.createEmptyBorder());
        horizSplit3.setBorder(BorderFactory.createEmptyBorder());

        return vertSplit1;
    }

    public JComponent CreateRightConfigurationPanel() {
        vertSplitRight1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight1.setDividerSize(3);
        horizSplitRight1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight1.setDividerSize(3);

        vertSplitRight2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight2.setDividerSize(3);
        horizSplitRight2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight2.setDividerSize(3);

        vertSplitRight3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight3.setDividerSize(3);
        horizSplitRight3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight3.setDividerSize(3);

        vertSplitRight4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight4.setDividerSize(3);
        horizSplitRight4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight4.setDividerSize(3);

        vertSplitRight5 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight5.setDividerSize(3);
        horizSplitRight5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight5.setDividerSize(3);

        vertSplitRight6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight6.setDividerSize(3);
        horizSplitRight6 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight6.setDividerSize(3);

        vertSplitRight7 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight7.setDividerSize(3);
        horizSplitRight7 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight7.setDividerSize(3);

        vertSplitRight8 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight8.setDividerSize(3);
        horizSplitRight8 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight8.setDividerSize(3);

        vertSplitRight9 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplitRight9.setDividerSize(3);
        horizSplitRight9 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplitRight9.setDividerSize(3);

        createButton(save15, "Сохранить 15 минут");
        createButton(save30, "Сохранить 30 минут");
        createButton(save45, "Сохранить 45 минут");
        createButton(save60, "Сохранить 60 минут");

        vertSplitRight1.setTopComponent(createLabelOnPanel(label1, "Конфигурация сохранения:"));
        vertSplitRight1.setBottomComponent(vertSplitRight2);
        vertSplitRight2.setTopComponent(createGroupButton(save15, save30, save45, save60));
        vertSplitRight2.setBottomComponent(vertSplitRight3);
        vertSplitRight3.setTopComponent(createLabelOnPanel(label2, "Время дня:"));
        vertSplitRight3.setBottomComponent(vertSplitRight4);

        // Панель нижняя заглушка
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);

        // Панель группы кнопок
        createRadioButton(morningRadio, "Утро");
        createRadioButton(dayRadio, "День");
        createRadioButton(eveningRadio, "Вечер");

        // Добавляем панель, в которой создаем группу кнопок
        vertSplitRight4.setTopComponent(createGroupButton(morningRadio, dayRadio, eveningRadio));
        vertSplitRight4.setBottomComponent(vertSplitRight5);

        buttonReset.addActionListener(reset);
        vertSplitRight5.setTopComponent(createButton(buttonReset, "Очистить таблицу"));
        vertSplitRight5.setBottomComponent(p);

        // Убрать границу (тень) вокруг divider
        // UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder()); // Убрать границу (тень) вокруг divider для всех SplitPane в приложении
        vertSplitRight1.setBorder(BorderFactory.createEmptyBorder());
        vertSplitRight2.setBorder(BorderFactory.createEmptyBorder());
        vertSplitRight3.setBorder(BorderFactory.createEmptyBorder());
        vertSplitRight4.setBorder(BorderFactory.createEmptyBorder());
        horizSplitRight1.setBorder(BorderFactory.createEmptyBorder());
        horizSplitRight2.setBorder(BorderFactory.createEmptyBorder());
        horizSplitRight3.setBorder(BorderFactory.createEmptyBorder());
        horizSplitRight4.setBorder(BorderFactory.createEmptyBorder());

        return vertSplitRight1;
    }

    // Возвращает текст выбранной кнопки из группы кнопок
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "утро";
    }

    private JButton createButton(JButton button, String text) {
        button.setText(text);
        button.setToolTipText(text);
        button.setFocusable(false);

        return button;
    }

    private JRadioButton createRadioButton(JRadioButton button, String text) {
        button.setText(text);
        button.setToolTipText(text);
        button.setBackground(Color.white);
        button.setFocusable(false);

        return button;
    }

    // Группа кнопок
    private ButtonGroup groupPeriodOfDay = new ButtonGroup();

    // Конфигурируем группу кнопок
    private JPanel createGroupButton(JRadioButton button1, JRadioButton button2, JRadioButton button3) {
        JPanel pp = new JPanel(new GridLayout(0, 1, 0, 3));
        pp.setBackground(Color.WHITE);
        pp.add(button1);
        button1.addActionListener(timeOfDayListener);
        pp.add(button2);
        button2.addActionListener(timeOfDayListener);
        pp.add(button3);
        button3.addActionListener(timeOfDayListener);
        groupPeriodOfDay.add(button1);
        groupPeriodOfDay.add(button2);
        groupPeriodOfDay.add(button3);

        return pp;
    }

    private JPanel createGroupButton(JButton b1, JButton b2, JButton b3, JButton b4) {
        JPanel pp = new JPanel(new GridLayout(0, 1, 0, 4));
        pp.setBackground(Color.WHITE);
        pp.add(b1);
        pp.add(b2);
        pp.add(b3);
        pp.add(b4);
        b1.addActionListener(saveTime);
        b2.addActionListener(saveTime);
        b3.addActionListener(saveTime);
        b4.addActionListener(saveTime);

        return pp;
    }

    // Сохраняем промежуточные результаты таблицы в нужную таблицу по 15 минуткам
    private ActionListener saveTime = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (((JButton) e.getSource()).getText().equalsIgnoreCase(save15.getText())) {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, 12, cartogram);
                }
                if (((JButton) e.getSource()).getText().equalsIgnoreCase(save30.getText())) {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, 33, cartogram);
                }
                if (((JButton) e.getSource()).getText().equalsIgnoreCase(save45.getText())) {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, 54, cartogram);
                }
                if (((JButton) e.getSource()).getText().equalsIgnoreCase(save60.getText())) {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, 84, cartogram);
                }
            } catch (IOException ex) {
                Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    // Слушатель выбора времени Дня (утро, день, вечер)
    private ActionListener timeOfDayListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getSelectedButtonText(groupPeriodOfDay).equalsIgnoreCase("утро")) {
                page = 0;
            }
            if (getSelectedButtonText(groupPeriodOfDay).equalsIgnoreCase("день")) {
                page = 1;
            }
            if (getSelectedButtonText(groupPeriodOfDay).equalsIgnoreCase("вечер")) {
                page = 2;
            }
        }
    };

    // Сброс всей таблицы (во всех ячейках устанавливаем 0)
    private ActionListener reset = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table != null) {
                // по строкам
                for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
                    // по столбцам
                    int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
                    for (int j = unAccountedColumns; j < table.getModel().getData().getFieldsCount(); j++) {
                        table.getModel().setValueAt("0", i, j);
                    }
                }
            }
        }
    };

    // Создаем label на панеле
    private JPanel createLabelOnPanel(JLabel label, String text) {
        label = new JLabel(text);
        label.setToolTipText(text);
        label.setFocusable(false);

        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.WHITE);
        p.add(label);

        return p;
    }

    private void createHorizontalPanel(JSplitPane horizSplit, String discription, String button) {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        // Левый компонент
        JLabel label = new JLabel(discription); // Лэйбл
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFocusable(false);
        label.setToolTipText(label.getText()); // Установка всплывающей подсказки

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.WHITE);
        p.add(label);

        JScrollPane sc = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        sc.setHorizontalScrollBar(scrollBar);
        sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        horizSplit.setLeftComponent(sc); //Добавление конфигурированного лэйбла в панель

        // TextField
        JButton jbutton = new JButton(button);

        horizSplit.setRightComponent(jbutton);
    }

    // описание дерева checkBoxов (наполнения) для модели Now
    private TreeModel createTreeModelNow() {
        final String ROOT = "Учитываемые единицы";
        // Массив листьев деревьев
        final String[] nodes = new String[]{"Троллейбусы", "Автобусы", "Легковой транспорт", "Грузовые", "Трамвай", "Пешеходы", "Велотранспорт"};
        final String[][] leafs = new String[][]{
            {"Большой автобус", "Средний автобус", "Микроавтобус"},
            {"До 2-х тонн", "От 2 до 6 тонн", "От 6 до 12 тонн", "От 12 до 20 тонн", "Более 20 тонн"}
        };
        // Корневой узел дерева
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Добавление ветвей - потомков 1-го уровня
        DefaultMutableTreeNode tral = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode bus = new DefaultMutableTreeNode(nodes[1]);
        DefaultMutableTreeNode car = new DefaultMutableTreeNode(nodes[2]);
        DefaultMutableTreeNode truck = new DefaultMutableTreeNode(nodes[3]);
        DefaultMutableTreeNode tram = new DefaultMutableTreeNode(nodes[4]);
        DefaultMutableTreeNode people = new DefaultMutableTreeNode(nodes[5]);
        DefaultMutableTreeNode bicycle = new DefaultMutableTreeNode(nodes[6]);
        // Добавление ветвей к корневой записи
        root.add(tral);
        root.add(bus);
        root.add(car);
        root.add(truck);
        root.add(tram);
//        root.add(people);
//        root.add(bicycle);
        // Добавление листьев - потомков 2-го уровня
//        for (int i = 0; i < leafs[0].length; i++) {
//            bus.add(new DefaultMutableTreeNode(leafs[0][i]));
//        }
//        for (int i = 0; i < leafs[1].length; i++) {
//            truck.add(new DefaultMutableTreeNode(leafs[1][i]));
//        }
        // Создание стандартной модели
        return new DefaultTreeModel(root);
    }

    // описание дерева checkBoxов (наполнения) для модели Future
    private TreeModel createTreeModelFuture() {
        final String ROOT = "Учитываемые единицы";
        // Массив листьев деревьев
        final String[] nodes = new String[]{"Легковые, фургоны", "Автобусы", "Грузовые", "Автопоезда", "Пешеходы", "Велотранспорт", "Трамвай", "Троллейбусы"};
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
        DefaultMutableTreeNode trolleybus = new DefaultMutableTreeNode(nodes[7]);
        // Добавление ветвей к корневой записи
        root.add(car);
        root.add(bus);
        root.add(truck);
        root.add(roadTrains);
//        root.add(people);
//        root.add(bicycle);
        root.add(tram);
        root.add(trolleybus);
        // Добавление листьев - потомков 2-го уровня
//        for (int i = 0; i < leafs[0].length; i++) {
//            bus.add(new DefaultMutableTreeNode(leafs[0][i]));
//        }
//        for (int i = 0; i < leafs[1].length; i++) {
//            truck.add(new DefaultMutableTreeNode(leafs[1][i]));
//        }
//        for (int i = 0; i < leafs[2].length; i++) {
//            roadTrains.add(new DefaultMutableTreeNode(leafs[2][i]));
//        }
        // Создание стандартной модели
        return new DefaultTreeModel(root);
    }

    private JPanel acceptedParametersPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);

        JButton bP = new JButton("Создать проект");
        bP.setFocusable(false); // отключаем возможность получения фокуса кнопкой
        bP.setToolTipText(bP.getText());
        bP.addActionListener(this::onButtonClick);
        p.add(bP, BorderLayout.NORTH);

        return p;
    }

    // Первая строка
    private void horizontalPanel1() {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        // Левый компонент
        JLabel label = new JLabel("Количество направлений: "); // Лэйбл
        // Установка фона лэйбла:
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFocusable(false);
        label.setToolTipText(label.getText()); // Установка всплывающей подсказки

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.WHITE);
        p.add(label);

        JScrollPane sc = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        sc.setHorizontalScrollBar(scrollBar);
        sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        horizSplit1.setLeftComponent(sc);

        // ComboBox
        String[] items = {"3 вправо", "3 вверх", "4", "4 кольцо"};
        comboBox1 = new JComboBox(items);
        comboBox1.setSelectedItem("4");
        comboBox1.setFocusable(false);
        comboBox1.setBackground(Color.WHITE); // установка цвета заднего фона JComboBox
        ((JLabel) comboBox1.getRenderer()).setHorizontalAlignment(JLabel.CENTER); // выравнивание текста внутри JComboBox

        // Правый компонент
        horizSplit1.setRightComponent(comboBox1);
    }

    private void horizontalPanel2() {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        // Левый компонент
        JLabel label = new JLabel("Единицы подсчета: "); // Лэйбл
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFocusable(false);
        label.setToolTipText(label.getText()); // Установка всплывающей подсказки

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.WHITE);
        p.add(label);

        JScrollPane sc2 = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        sc2.setHorizontalScrollBar(scrollBar);
        sc2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        horizSplit2.setLeftComponent(sc2); //Добавление конфигурированного лэйбла в панель

        // Правый компонент
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Дерево checkBoxов
        checkBox = new JCheckBoxTree(); // создание дерева checkboxов
        checkBox.setFocusable(false);
        checkBox.setModel(createTreeModelNow()); // добавление нужных компонентов в него

        scrollPane = new JScrollPane(checkBox);
        scrollPane.setVisible(true); // исходно дерево checkboxов видно

        // Кнопка для отображения/сокрытия дерева checkboxов
        JButton button = new JButton("Свернуть") {
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
        // Добавление слушателя к кнопке: отображения и сокрытие дерева checkboxов (а также установка позиции вертикальной divider
        button.addActionListener((e) -> {
            if (scrollPane.isVisible()) {
                scrollPane.setVisible(false);
                button.setText("Показать");
                vertSplit3.setDividerLocation(button.getHeight() + 4);
            } else {
                scrollPane.setVisible(true);
                button.setText("Свернуть");
                vertSplit3.setDividerLocation(150);
            }
        });

        panel.add(button, BorderLayout.NORTH); // добавляем кнопку
        panel.add(scrollPane, BorderLayout.CENTER); // добавляем дерево checkboxов внутри скролящейся панели
        horizSplit2.setRightComponent(panel);
    }

    private void horizontalPanel3() {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        // Левый компонент
        JLabel label = new JLabel("Вид ведомости: "); // Лэйбл
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFocusable(false);
        label.setToolTipText(label.getText()); // Установка всплывающей подсказки

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.WHITE);
        p.add(label);

        JScrollPane scPanel = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        scPanel.setHorizontalScrollBar(scrollBar);
        scPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        horizSplit3.setLeftComponent(scPanel); //Добавление конфигурированного лэйбла в панель

        // Правый компонент
        // ComboBox
        String[] items = {"Старая", "Новая"};
        comboBox3 = new JComboBox(items);
        comboBox3.setSelectedIndex(0);
        comboBox3.setFocusable(false);
        comboBox3.setBackground(Color.WHITE); // установка цвета заднего фона JComboBox
        ((JLabel) comboBox3.getRenderer()).setHorizontalAlignment(JLabel.CENTER); // выравнивание текста внутри JComboBox

        comboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(comboBox3.getSelectedItem()).equalsIgnoreCase("Старая")) {
                    checkBox.setModel(createTreeModelNow()); // меняем дерево выбора того, что считать
                    checkBox.updateUI(); // обновляем его
                }
                if (String.valueOf(comboBox3.getSelectedItem()).equalsIgnoreCase("Новая")) {
                    checkBox.setModel(createTreeModelFuture()); // меняем дерево выбора того, что считать
                    checkBox.updateUI(); // обновляем его
                }
            }
        });

        horizSplit3.setRightComponent(comboBox3);
    }

    private void dividerTracking() {
        // Отслеживание положения divider разделенной панели для синхронизации вертикальных divider (в горизонтальных панелях) всех уровней
        horizSplit1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit2.setDividerLocation((int) e.getNewValue());
            horizSplit3.setDividerLocation((int) e.getNewValue());
        });

        horizSplit2.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit1.setDividerLocation((int) e.getNewValue());
            horizSplit3.setDividerLocation((int) e.getNewValue());
        });

        horizSplit3.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit1.setDividerLocation((int) e.getNewValue());
            horizSplit2.setDividerLocation((int) e.getNewValue());
        });
    }

    // НУЖНО УБРАТЬ КУЧУ РАЗНЫХ ШАБЛОНОВ И ОСТАВИТЬ ТОЛЬКО ОДИН - ДЛЯ 4 НАПРАВЛЕНИЙ. ПРОСТО ПРОПИСАТЬ,
    // что нужно удалить все из ячейки ИСХОДЯ ИЗ ТИПА ВЕДОМОСТИ
    //___________________________________________________________
    // Клик по кнопке принятия всех параметров конфигурации и инициализации нового проекта
    // Создаем Excel файл, в котором далее сохраним результат подсчета. Нужно копировать excel файл и далее просто для него выбрать имя и место.
    // В зависимости от выбранных параметров (в общем то только количества подсчитываемых направлений и их тип - Т-левый, Т-правый и т.п.)
    public void onButtonClick(ActionEvent e) {
        String oldValue = fullFileName + "_"; // старое имя файла (путь к нему + имя файла)

        paths = checkBox.getCheckedPaths(); // Получаем путь для каждого выбранного узла

        // Если вид таблицы СТАРЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("старая")) {

            // Если выбрано 4 направления, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4")) {

                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/4"); // клонируем шаблон в путь и с названием файла которые указывает пользователь
                    fullFileName = fileSaveWithPattern.getFullFileName(); // если сохранил успешно - имя есть; не успешно - имя null
                    if (fullFileName != null) { // если успешно сохранился клон, то
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4";
                        createTable((new CrossRoadModel()).getModel(), "Now", typeOfDirection); // создаем новую таблицу
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            // Если выбрано 4 направления кольцо, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4 кольцо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/4Circle");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4 кольцо";
                        createTable((new CrossRoadModel()).getModel(), "Now", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 3 направления вверх, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вверх")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/3Up");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вверх";
                        createTable((new TUpRoadModel()).getModel(), "Now", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 3 направления вправо, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вправо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/3Up");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вправо";
                        createTable((new TUpRoadModel()).getModel(), "Now", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Если вид таблицы НОВЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("новая")) {
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/4");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4";
                        createTable((new CrossRoadModel()).getModel(), "Future", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4 кольцо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/4");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4 кольцо";
                        createTable((new CrossRoadModel()).getModel(), "Future", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вправо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/3Right");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вправо";
                        createTable((new TRightRoadModel()).getModel(), "Future", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вверх")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/3Right");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вправо";
                        createTable((new TRightRoadModel()).getModel(), "Future", typeOfDirection);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        pcs.firePropertyChange("fullFileName", oldValue, fullFileName); // уведомляем об изменении пути к файлу
    }

    public void onSaveButtonClick(ActionEvent e) {
        // Если вид таблицы СТАРЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("старая")) {
            // Если выбрано 4 направления, то:
            if (typeOfDirection.equalsIgnoreCase("4")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 4 направления кольцо, то:
            if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 3 направления вверх, то:
            if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 3 направления вправо, то:
            if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Если вид таблицы НОВЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("новая")) {

            if (typeOfDirection.equalsIgnoreCase("4")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 4 направления кольцо, то:
            if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection, page, cartogram);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Создание таблицы и картограммы с переданными параметрами
    private void createTable(IModelFieldGroup[] modelGroup, String kindOfStatement, String typeOfDirection) {
        Table tableModel = new Table();
        cartogram = new CreateCartogram(fullFileName, typeOfDirection);
        try {
            this.cartogramPanel = cartogram.initialize();
            table = tableModel.doTable(modelGroup, kindOfStatement, typeOfDirection, cartogram);
        } catch (Exception ex) {
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Метод добавляющий слушатель изменения свойств в этот класс
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public String getFullFileName() {
        return fullFileName;
    }

    public JBroTable getTable() {
        return table;
    }

    private String getFullName() {
        if (fullFileName != null) {
            this.fullName = fullFileName;
        }
        return this.fullName;
    }

    // Вид ведомости (старая, новая)
    public String getKindOfStatement() {
        return kindOfStatement;
    }

    // Количество направлений
    public String getTypeOfDirection() {
        return typeOfDirection;
    }

    public TreePath[] getPaths() {
        return paths;
    }

    public JPanel getCartogramPanel() {
        return cartogramPanel;
    }

    public CreateCartogram getCartogram() {
        return cartogram;
    }

}
