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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
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
import javax.swing.JOptionPane;
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
import ru.Excel.open.InfoFromProject;
import ru.Excel.open.SaveInInfoAboutProject;
import ru.Excel.open.excelOpen;
import ru.Excel.open.openPattern.FromExcelToCartogram;
import ru.JCheckBoxTree;
import ru.cartogram.CreateCartogram;
import ru.jtable.Table;
import ru.jtable.model.*;
import ru.jtable.modelListener.cartogram.CountingOneDirectionNow;

/**
 * Класс для создания Панели (в виде таблицы) Настройки подсчета.<n>
 * На вкладке таблицы.
 */
public class CreateConfigurationPanel {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this); // переменная позволяющая добавить в этот слушатель изменения свойств 

    private JPanel cartogramPanelMorning;
    private JPanel cartogramPanelDay;
    private JPanel cartogramPanelEvening;
    private CreateCartogram cartogramMorning;
    private CreateCartogram cartogramDay;
    private CreateCartogram cartogramEvening;

    private FileSaveWithPattern fileSaveAsWithPattern;
    private FileSaveWithPattern fileSaveWithPattern;
    private String fullFileName;
    private String fullName; // Если fullFileName становится null, то fullName не изменяется и сохраняет старое имя
    private String kindOfStatement;
    private String typeOfDirection;

    private Table tableSumMorningModel = new Table();
    private Table tableSumDayModel = new Table();
    private Table tableSumEveningModel = new Table();

    private JBroTable table15Morning = new JBroTable(); // Таблица
    private JBroTable table30Morning = new JBroTable(); // Таблица
    private JBroTable table45Morning = new JBroTable(); // Таблица
    private JBroTable table60Morning = new JBroTable(); // Таблица
    private JBroTable[] tablesMorning = {table15Morning, table30Morning, table45Morning, table60Morning};
    private JBroTable tableSumMorning = new JBroTable(); // Таблица
    private JBroTable table15Day = new JBroTable(); // Таблица
    private JBroTable table30Day = new JBroTable(); // Таблица
    private JBroTable table45Day = new JBroTable(); // Таблица
    private JBroTable table60Day = new JBroTable(); // Таблица
    private JBroTable[] tablesDay = {table15Day, table30Day, table45Day, table60Day};
    private JBroTable tableSumDay = new JBroTable(); // Таблица
    private JBroTable table15Evening = new JBroTable(); // Таблица
    private JBroTable table30Evening = new JBroTable(); // Таблица
    private JBroTable table45Evening = new JBroTable(); // Таблица
    private JBroTable table60Evening = new JBroTable(); // Таблица
    private JBroTable[] tablesEvening = {table15Evening, table30Evening, table45Evening, table60Evening};
    private JBroTable tableSumEvening = new JBroTable(); // Таблица

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
    private JLabel chooseTable;

    // Панель кнопок выбора конкретной 15минутной таблицы
    private JButton table0_15 = new JButton();
    private JButton table15_30 = new JButton();
    private JButton table30_45 = new JButton();
    private JButton table45_60 = new JButton();

    private JLabel timeOfDay;

    private JButton buttonReset = new JButton();

    // Группа радио кнопок выбора конкретной 15минутной таблицы
    private JRadioButton table0_15Radio = new JRadioButton("", true);
    private JRadioButton table15_30Radio = new JRadioButton();
    private JRadioButton table30_45Radio = new JRadioButton();
    private JRadioButton table45_60Radio = new JRadioButton();

    // Группа радио кнопок выбора Времени дня
    private JRadioButton morningRadio = new JRadioButton("", true);
    private JRadioButton dayRadio = new JRadioButton();
    private JRadioButton eveningRadio = new JRadioButton();

    // Группы кнопок
    private ButtonGroup groupPeriodOfDay = new ButtonGroup();
    private ButtonGroup group15MinuteTable = new ButtonGroup();

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

        createButton(table0_15, "Таблица 0-15 минут");
        createButton(table15_30, "Таблица 15-30 минут");
        createButton(table30_45, "Таблица 30-45 минут");
        createButton(table45_60, "Таблица 45-60 минут");

        // Панель группы кнопок Выбора конкретной 15минутной таблицы
        createRadioButton(table0_15Radio, "Таблица 0-15 минут");
        createRadioButton(table15_30Radio, "Таблица 15-30 минут");
        createRadioButton(table30_45Radio, "Таблица 30-45 минут");
        createRadioButton(table45_60Radio, "Таблица 45-60 минут");

        vertSplitRight1.setTopComponent(createLabelOnPanel(chooseTable, "Конфигурация подсчета:"));
        vertSplitRight1.setBottomComponent(vertSplitRight2);
        vertSplitRight2.setTopComponent(createGroupButtonInGroup(table0_15Radio, table15_30Radio, table30_45Radio, table45_60Radio, group15MinuteTable));
        vertSplitRight2.setBottomComponent(vertSplitRight3);
        vertSplitRight3.setTopComponent(createLabelOnPanel(timeOfDay, "Время дня:"));
        vertSplitRight3.setBottomComponent(vertSplitRight4);

        // Панель нижняя заглушка
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);

        // Панель группы кнопок Выбора времени дня
        createRadioButton(morningRadio, "Утро");
        createRadioButton(dayRadio, "День");
        createRadioButton(eveningRadio, "Вечер");

        // Добавляем панель, в которой создаем группу кнопок
        vertSplitRight4.setTopComponent(createGroupButtonInGroup(morningRadio, dayRadio, eveningRadio, groupPeriodOfDay));
//        vertSplitRight4.setBottomComponent(vertSplitRight5);

//        buttonReset.addActionListener(reset);
//        vertSplitRight5.setTopComponent(createButton(buttonReset, "Очистить таблицу"));
        vertSplitRight4.setBottomComponent(p);

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

    // Конфигурируем группу кнопок
    private JPanel createGroupButtonInGroup(JRadioButton b1, JRadioButton b2, JRadioButton b3, ButtonGroup bGroup) {
        JPanel pp = new JPanel(new GridLayout(0, 1, 0, 3));
        pp.setBackground(Color.WHITE);
        pp.add(b1);
        pp.add(b2);
        pp.add(b3);
        bGroup.add(b1);
        bGroup.add(b2);
        bGroup.add(b3);

        return pp;
    }

    private JPanel createGroupButtonInGroup(JRadioButton b1, JRadioButton b2, JRadioButton b3, JRadioButton b4, ButtonGroup bGroup) {
        JPanel pp = new JPanel(new GridLayout(0, 1, 0, 3));
        pp.setBackground(Color.WHITE);
        pp.add(b1);
        pp.add(b2);
        pp.add(b3);
        pp.add(b4);
        bGroup.add(b1);
        bGroup.add(b2);
        bGroup.add(b3);
        bGroup.add(b4);

        return pp;
    }

    private JPanel createGroupButton(JButton b1, JButton b2, JButton b3, JButton b4) {
        JPanel pp = new JPanel(new GridLayout(0, 1, 0, 4));
        pp.setBackground(Color.WHITE);
        pp.add(b1);
        pp.add(b2);
        pp.add(b3);
        pp.add(b4);

        return pp;
    }

    // Сброс всей таблицы (во всех ячейках устанавливаем 0)
    private ActionListener reset = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
//            if (table != null) {
//                // по строкам
//                for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
//                    // по столбцам
//                    int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
//                    for (int j = unAccountedColumns; j < table.getModel().getData().getFieldsCount(); j++) {
//                        table.getModel().setValueAt("0", i, j);
//                    }
//                }
//            }
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
        // Слой добавления Таблиц (сам слушатель берем из класса configurationPanel, метод называется onButtonClick
        bP.addActionListener(this::onCreateProjectButtonClick);
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
        // ComboBox. items[0] = Старая. items[1] = Новая.
        String[] items = {"Старая", "Новая"};
        comboBox3 = new JComboBox(items);
        comboBox3.setSelectedIndex(0);
        comboBox3.setFocusable(false);
        comboBox3.setBackground(Color.WHITE); // установка цвета заднего фона JComboBox
        ((JLabel) comboBox3.getRenderer()).setHorizontalAlignment(JLabel.CENTER); // выравнивание текста внутри JComboBox

        comboBox3.addActionListener(new ActionListener() {
            @Override
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
    public void onCreateProjectButtonClick(ActionEvent e) {
        String oldValue = fullFileName + "_"; // старое имя файла (путь к нему + имя файла)

        paths = checkBox.getCheckedPaths(); // Получаем путь для каждого выбранного узла

        // Если вид таблицы СТАРЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("Старая")) {
            kindOfStatement = "Now";
        }
        // Если вид таблицы НОВЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("Новая")) {
            kindOfStatement = "Future";
        }
        // Если выбрано 4 направления, то:
        if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4")) {
            saveExcelAndCreateTable(new CrossRoadModel().getModel(), "4");
        }
        // Если выбрано 4 направления кольцо, то:
        if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4 кольцо")) {
            saveExcelAndCreateTable(new CrossRoadModel().getModel(), "4Circle");
        }
        // Если выбрано 3 направления вверх, то:
        if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вверх")) {
            saveExcelAndCreateTable(new TUpRoadModel().getModel(), "3Up");
        }
        // Если выбрано 3 направления вправо, то:
        if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вправо")) {
            saveExcelAndCreateTable(new TRightRoadModel().getModel(), "3Right");
        }
        // Если выбрано 3 направления вправо, то:
        if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вниз")) {
            saveExcelAndCreateTable(new TDownRoadModel().getModel(), "3Down");
        }
        // Если выбрано 3 направления вправо, то:
        if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 влево")) {
            saveExcelAndCreateTable(new TLeftRoadModel().getModel(), "3Left");
        }

        // Переносим в файл Excel идентифицирующую информацию о созданном проекте
        if (fullFileName != null) {
            new SaveInInfoAboutProject(fullFileName, kindOfStatement, typeOfDirection, paths);
        }

        pcs.firePropertyChange("fullFileName", oldValue, fullFileName); // уведомляем об изменении пути к файлу
    }

    public void onOpenProjectButtonClick(ActionEvent e) {
        String oldValue = fullFileName + "_";

        String projectName = null;
        excelOpen openProject = null;
        // Если был открыть хоть один какой либо проект ранее, то выбрасываем сообщение с выбором
        if (getFullName() != null) {
            int ret = JOptionPane.showConfirmDialog(table0_15, "У вас уже открыт проект. Сохранить его?", "Сохранение проекта, перед открытием нового", JOptionPane.YES_NO_CANCEL_OPTION);
            // И обрабатываем варианты выбора,  
            if (ret == JOptionPane.CANCEL_OPTION) {
            }
            // Открываем новый проект без сохранения
            if (ret == JOptionPane.NO_OPTION) {
                openProject = new excelOpen(); // Вызываем FileChooser для открытия Excel файла проекта
                projectName = openProject.getSelectExcelFile(); // Если открыть файл проекта получилось - то имя не будет null
            }
            // Сохраняем прошлый проект и открываем новый
            if (ret == JOptionPane.OK_OPTION) {
                onSaveAsButtonClick(e); // Сохраняем ранее открытый проект
//                // Если не хотим сохранять, то новый файл не открываем 
//                if (fileSaveAsWithPattern.getFullFileName() != null) {
                openProject = new excelOpen(); // Вызываем FileChooser для открытия Excel файла проекта
                projectName = openProject.getSelectExcelFile(); // Если открыть файл проекта получилось - то имя не будет null
//                }
            }
            // Если ранее открытых проектов не было, то предлагаем выбрать свой проект
        } else {
            openProject = new excelOpen(); // Вызываем FileChooser для открытия Excel файла проекта
            projectName = openProject.getSelectExcelFile(); // Если открыть файл проекта получилось - то имя не будет null
        }

        // Получаем данные о видах подсчитываемого в проекте транспорта из EXCEL
        if (projectName != null) {
            // Кидаем вспомогательное сообщение
            JOptionPane.showMessageDialog(openProject, "Сохраните свой новый проект, создаваемый из старого");
            // Считываем исходные значения натроек проекта
            String tempKindOfStatement = comboBox3.getSelectedItem().toString();
            String tempTypeOfDirection = comboBox1.getSelectedItem().toString();
            TreePath[] tempTreePath = checkBox.getCheckedPaths();
            // АНАЛИЗ ДОКУМЕНТА ПРОЕКТА
            // Создаем переменную для получения данных из выбранного excel файла проекта
            InfoFromProject infoProject = new InfoFromProject(projectName);

            // Считываем вид ведомости
            comboBox3.setSelectedItem(infoProject.getKindOfStatement());

            // Считываем тип перекрестка
            comboBox1.setSelectedItem(infoProject.getTypeOfDirection());

            // Считываем данные по УЧИТЫВАЕМЫМ ЕДИНИЦАМ ТРАНСПОРТА (TreePath)
            // Перебираем виды подсчитываемого транспорта из Excel 
            for (int i = 0; i < infoProject.getTreePath().length; i++) {
                // Перебираем все элементы checkBox
                for (int j = 0; j < checkBox.getRowCount(); j++) {
                    // Сравниваем элементы из файла проекта с элементами доступными в приложении
                    // Если совпадают, то этот элемент нужно включить
                    if (infoProject.getTreePath()[i].equalsIgnoreCase(checkBox.getPath(j))) {
                        checkBox.setCheckedPaths(j, true);
                    }
                }
            }
            // Пытаемся создать проект. Но если не получилось/отказались, то ничего не меняем в настройках (оставить прежние)
            onCreateProjectButtonClick(e); // Создается пустой проект, теперь его надо наполнить информацией из excel файла
            // Если сохранить новый проект(создаваемый из старого) не получилось/не захотели, то ничего не происходит и выкидываем сообщение
            if (fullFileName == null) {
                JOptionPane.showMessageDialog(openProject, "Проект не открылся. Если не сохранить ваш новый проект (созданный из старого), то и работать с ним не получится.");
                // Возвращаем все обратно
                comboBox3.setSelectedItem(tempKindOfStatement);
                comboBox1.setSelectedItem(tempTypeOfDirection);
                for (int i = 0; i < tempTreePath.length; i++) {
                    // Перебираем все элементы checkBox
                    String tempComponent = tempTreePath[i].getLastPathComponent().toString();
                    for (int j = 0; j < checkBox.getRowCount(); j++) {
                        if (tempComponent.equalsIgnoreCase(checkBox.getPath(j)) && !tempComponent.equalsIgnoreCase("Учитываемые единицы")) {
                            checkBox.setCheckedPaths(j, true);
                        }
                    }
                }
            }
            // РЕАЛИЗАЦИЯ НАПОЛНЕНИЯ ПРОЕКТА В JAVA из EXCEL (таблицы + картограммы)
            if (fullFileName != null) {
                saveAllTableFromExcel(projectName);
                pcs.firePropertyChange("openProjectDoCartogramConfigPanel", oldValue, fullFileName); // для переноса данных из файла проекта в картограмму, а из нее на панель конфигурации картограммы
            }
        }
    }

    private int[] rowTableNow = {12, 33, 54, 75};
    private int[] rowTableFuture = {11, 38, 65, 92};

    public void onSaveButtonClick(ActionEvent e) {
        saveAllTableInExcel();
    }

    public void onSaveAsButtonClick(ActionEvent e) {
        String oldValue = fullFileName + "_";
        // Пытаемся создать файл из шаблона
        try {
            fileSaveAsWithPattern = new FileSaveWithPattern(kindOfStatement + "/4"); // клонируем шаблон в путь и с названием файла которые указывает пользователь
        } catch (IOException ex) {
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Таким образом заменяем значение в getFullFileName() этого класса - новые данные уже сохранятся в ЭТОТ новый файл
        // Если получилось, то новое имя будет если нет - то оно null и мы ничего не делаем
        if (fileSaveAsWithPattern.getFullFileName() != null) {
            fullFileName = fileSaveAsWithPattern.getFullFileName(); // если сохранил успешно - имя есть; не успешно - имя null
            saveAllTableInExcel(); // Сохраняем все таблицы в excel файл по адресу getFullFileName()

            // Инициализируем, создаем и передаем новые картограммы для сохранения
            cartogramMorning = new CreateCartogram(fullFileName, typeOfDirection, "cartogramMorning");
            cartogramDay = new CreateCartogram(fullFileName, typeOfDirection, "cartogramDay");
            cartogramEvening = new CreateCartogram(fullFileName, typeOfDirection, "cartogramEvening");
            try {
                this.cartogramPanelMorning = cartogramMorning.initialize();
                this.cartogramPanelDay = cartogramDay.initialize();
                this.cartogramPanelEvening = cartogramEvening.initialize();
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Сохраняем новые картограммы
            cartogramMorning.saveChangeValue();
            cartogramDay.saveChangeValue();
            cartogramEvening.saveChangeValue();
            // Указываем таблицам, что нужно сохранять информацию в новые картограммы
            tableSumMorningModel.removeAndSetModelListener(kindOfStatement, cartogramMorning);
            tableSumDayModel.removeAndSetModelListener(kindOfStatement, cartogramDay);
            tableSumEveningModel.removeAndSetModelListener(kindOfStatement, cartogramEvening);

            pcs.firePropertyChange("cartogramChange", oldValue, fullFileName); // уведомляем об изменении пути к файлу
        }
    }

    // Создание таблицы и картограммы с переданными параметрами
    private void createTable(IModelFieldGroup[] modelGroup, String kindOfStatement, String typeOfDirection) {
        // ТАБЛИЦЫ (модели таблиц)
        // Утро
        Table tableSumMorningModel = new Table();
        this.tableSumMorningModel = tableSumMorningModel;
        Table table15MorningModel = new Table();
        Table table30MorningModel = new Table();
        Table table45MorningModel = new Table();
        Table table60MorningModel = new Table();
        // День
        Table tableSumDayModel = new Table();
        this.tableSumDayModel = tableSumDayModel;
        Table table15DayModel = new Table();
        Table table30DayModel = new Table();
        Table table45DayModel = new Table();
        Table table60DayModel = new Table();
        // Вечер
        Table tableSumEveningModel = new Table();
        this.tableSumEveningModel = tableSumEveningModel;
        Table table15EveningModel = new Table();
        Table table30EveningModel = new Table();
        Table table45EveningModel = new Table();
        Table table60EveningModel = new Table();
        cartogramMorning = new CreateCartogram(fullFileName, typeOfDirection, "cartogramMorning");
        cartogramDay = new CreateCartogram(fullFileName, typeOfDirection, "cartogramDay");
        cartogramEvening = new CreateCartogram(fullFileName, typeOfDirection, "cartogramEvening");
        try {
            this.cartogramPanelMorning = cartogramMorning.initialize();
            this.cartogramPanelDay = cartogramDay.initialize();
            this.cartogramPanelEvening = cartogramEvening.initialize();
            // ТАБЛИЦЫ
            // Утро
            tableSumMorning = tableSumMorningModel.doTable(modelGroup, kindOfStatement, cartogramMorning);
            tablesMorning[0] = table15Morning = table15MorningModel.doTable(modelGroup, kindOfStatement, tableSumMorning, tablesMorning);
            tablesMorning[1] = table30Morning = table30MorningModel.doTable(modelGroup, kindOfStatement, tableSumMorning, tablesMorning);
            tablesMorning[2] = table45Morning = table45MorningModel.doTable(modelGroup, kindOfStatement, tableSumMorning, tablesMorning);
            tablesMorning[3] = table60Morning = table60MorningModel.doTable(modelGroup, kindOfStatement, tableSumMorning, tablesMorning);
            // День
            tableSumDay = tableSumDayModel.doTable(modelGroup, kindOfStatement, cartogramDay);
            tablesDay[0] = table15Day = table15DayModel.doTable(modelGroup, kindOfStatement, tableSumDay, tablesDay);
            tablesDay[1] = table30Day = table30DayModel.doTable(modelGroup, kindOfStatement, tableSumDay, tablesDay);
            tablesDay[2] = table45Day = table45DayModel.doTable(modelGroup, kindOfStatement, tableSumDay, tablesDay);
            tablesDay[3] = table60Day = table60DayModel.doTable(modelGroup, kindOfStatement, tableSumDay, tablesDay);
            // Вечер
            tableSumEvening = tableSumEveningModel.doTable(modelGroup, kindOfStatement, cartogramEvening);
            tablesEvening[0] = table15Evening = table15EveningModel.doTable(modelGroup, kindOfStatement, tableSumEvening, tablesEvening);
            tablesEvening[1] = table30Evening = table30EveningModel.doTable(modelGroup, kindOfStatement, tableSumEvening, tablesEvening);
            tablesEvening[2] = table45Evening = table45EveningModel.doTable(modelGroup, kindOfStatement, tableSumEvening, tablesEvening);
            tablesEvening[3] = table60Evening = table60EveningModel.doTable(modelGroup, kindOfStatement, tableSumEvening, tablesEvening);
        } catch (Exception ex) {
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveExcelAndCreateTable(IModelFieldGroup[] modelGroup, String typeOfDirection) {
        try {
            fileSaveWithPattern = new FileSaveWithPattern(kindOfStatement + "/4"); // клонируем шаблон в путь и с названием файла которые указывает пользователь
            fullFileName = fileSaveWithPattern.getFullFileName(); // если сохранил успешно - имя есть; не успешно - имя null
            if (fullFileName != null) { // если успешно сохранился клон, то передаем параметры для дальнейшего определения как сохранять данные
                this.typeOfDirection = typeOfDirection;
                createTable(modelGroup, kindOfStatement, typeOfDirection); // создаем новые таблицы
                fullName = fullFileName;
            }
        } catch (IOException ex) {
            JOptionPane.showInputDialog(fileSaveWithPattern, ex.getMessage().substring(ex.getMessage().lastIndexOf("(") - 1));
            JOptionPane.showMessageDialog(fileSaveWithPattern, ex.getMessage().substring(ex.getMessage().lastIndexOf("(") - 1));
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveAllTableInExcel() {
        int page; // страница в Excel для сохранения
        int[] rowTable = {}; // массив строк, указывающий на начало заполнения таблиц
        // Если вид таблицы СТАРЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("Now")) {
            rowTable = rowTableNow;
        }
        // Если вид таблицы НОВЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("Future")) {
            rowTable = rowTableFuture;
        }
        try {
            page = 0;
            new SaveInExistingFile(getFullName(), table15Morning, kindOfStatement, typeOfDirection, page, rowTable[0], cartogramMorning);
            new SaveInExistingFile(getFullName(), table30Morning, kindOfStatement, typeOfDirection, page, rowTable[1]);
            new SaveInExistingFile(getFullName(), table45Morning, kindOfStatement, typeOfDirection, page, rowTable[2]);
            new SaveInExistingFile(getFullName(), table60Morning, kindOfStatement, typeOfDirection, page, rowTable[3]);
            page = 1;
            new SaveInExistingFile(getFullName(), table15Day, kindOfStatement, typeOfDirection, page, rowTable[0], cartogramDay);
            new SaveInExistingFile(getFullName(), table30Day, kindOfStatement, typeOfDirection, page, rowTable[1]);
            new SaveInExistingFile(getFullName(), table45Day, kindOfStatement, typeOfDirection, page, rowTable[2]);
            new SaveInExistingFile(getFullName(), table60Day, kindOfStatement, typeOfDirection, page, rowTable[3]);
            page = 2;
            new SaveInExistingFile(getFullName(), table15Evening, kindOfStatement, typeOfDirection, page, rowTable[0], cartogramEvening);
            new SaveInExistingFile(getFullName(), table30Evening, kindOfStatement, typeOfDirection, page, rowTable[1]);
            new SaveInExistingFile(getFullName(), table45Evening, kindOfStatement, typeOfDirection, page, rowTable[2]);
            new SaveInExistingFile(getFullName(), table60Evening, kindOfStatement, typeOfDirection, page, rowTable[3]);
        } catch (NullPointerException ee) {
            // Если файл excel удалили во время работы в программе и не захотели создавать новый, то выбрасываем сообщение и все!
            JOptionPane.showMessageDialog(cartogramPanelMorning, "Сохранить не получилось! Попробуйте еще раз.");
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ee);
        } catch (FileNotFoundException e) { // FileNotFoundException входит в IOException, которые отлавливаем дальше
            String message = e.getMessage().substring(e.getMessage().lastIndexOf("(") - 1).trim(); // сообщение с ошибкой
            if (message.equals("(Процесс не может получить доступ к файлу, так как этот файл занят другим процессом)")) {
                JOptionPane.showMessageDialog(cartogramPanelMorning, message + ". Закройте его и повторите сохранение.");
            }
        } catch (IOException ex) {
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Переносим таблицы из Excel-проекта в Java
    private void saveAllTableFromExcel(String projectName) {
        int page; // страница в Excel для сохранения
        int[] rowTable = {}; // массив строк, указывающий на начало заполнения таблиц
        // Если вид таблицы СТАРЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("Now")) {
            rowTable = rowTableNow;
        }
        // Если вид таблицы НОВЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("Future")) {
            rowTable = rowTableFuture;
        }

        // Указываем таблицам, что НЕ НУЖНО сохранять информацию в новые картограммы
        tableSumMorningModel.removeModelListener();
        tableSumDayModel.removeModelListener();
        tableSumEveningModel.removeModelListener();

        // Указываем, что в Итоговых таблицах тоже нужно обновлять данные
        tableSumMorningModel.setModelListener(kindOfStatement);
        tableSumDayModel.setModelListener(kindOfStatement);
        tableSumEveningModel.setModelListener(kindOfStatement);
        try {
            page = 0;
            FromExcelToCartogram fromExcelToCartogramTable15Morning = new FromExcelToCartogram(projectName, table15Morning, kindOfStatement, typeOfDirection, page, rowTable[0], cartogramMorning);
            fromExcelToCartogramTable15Morning.doCount();
            FromExcelToCartogram fromExcelToCartogramTable30Morning = new FromExcelToCartogram(projectName, table30Morning, kindOfStatement, typeOfDirection, page, rowTable[1]);
            fromExcelToCartogramTable30Morning.doCount();
            FromExcelToCartogram fromExcelToCartogramTable45Morning = new FromExcelToCartogram(projectName, table45Morning, kindOfStatement, typeOfDirection, page, rowTable[2]);
            fromExcelToCartogramTable45Morning.doCount();
            FromExcelToCartogram fromExcelToCartogramTable60Morning = new FromExcelToCartogram(projectName, table60Morning, kindOfStatement, typeOfDirection, page, rowTable[3]);
            fromExcelToCartogramTable60Morning.doCount();

            page = 1;
            FromExcelToCartogram fromExcelToCartogramTable15Day = new FromExcelToCartogram(projectName, table15Day, kindOfStatement, typeOfDirection, page, rowTable[0], cartogramDay);
            fromExcelToCartogramTable15Day.doCount();
            FromExcelToCartogram fromExcelToCartogramTable30Day = new FromExcelToCartogram(projectName, table30Day, kindOfStatement, typeOfDirection, page, rowTable[1]);
            fromExcelToCartogramTable30Day.doCount();
            FromExcelToCartogram fromExcelToCartogramTable45Day = new FromExcelToCartogram(projectName, table45Day, kindOfStatement, typeOfDirection, page, rowTable[2]);
            fromExcelToCartogramTable45Day.doCount();
            FromExcelToCartogram fromExcelToCartogramTable60Day = new FromExcelToCartogram(projectName, table60Day, kindOfStatement, typeOfDirection, page, rowTable[3]);
            fromExcelToCartogramTable60Day.doCount();

            page = 2;
            FromExcelToCartogram fromExcelToCartogramTable15Evening = new FromExcelToCartogram(projectName, table15Evening, kindOfStatement, typeOfDirection, page, rowTable[0], cartogramEvening);
            fromExcelToCartogramTable15Evening.doCount();
            FromExcelToCartogram fromExcelToCartogramTable30Evening = new FromExcelToCartogram(projectName, table30Evening, kindOfStatement, typeOfDirection, page, rowTable[1]);
            fromExcelToCartogramTable30Evening.doCount();
            FromExcelToCartogram fromExcelToCartogramTable45Evening = new FromExcelToCartogram(projectName, table45Evening, kindOfStatement, typeOfDirection, page, rowTable[2]);
            fromExcelToCartogramTable45Evening.doCount();
            FromExcelToCartogram fromExcelToCartogramTable60Evening = new FromExcelToCartogram(projectName, table60Evening, kindOfStatement, typeOfDirection, page, rowTable[3]);
            fromExcelToCartogramTable60Evening.doCount();
        } catch (NullPointerException ee) {
            // Если файл excel удалили во время работы в программе и не захотели создавать новый, то выбрасываем сообщение и все!
            JOptionPane.showMessageDialog(cartogramPanelMorning, "Сохранить не получилось! Попробуйте еще раз.");
        } catch (FileNotFoundException e) { // FileNotFoundException входит в IOException, которые отлавливаем дальше
            String message = e.getMessage().substring(e.getMessage().lastIndexOf("(") - 1).trim(); // сообщение с ошибкой
            if (message.equals("(Процесс не может получить доступ к файлу, так как этот файл занят другим процессом)")) {
                JOptionPane.showMessageDialog(cartogramPanelMorning, message + ". Закройте его и повторите сохранение.");
            }
        } catch (IOException ex) {
            Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Удаляем слушателей изменения модели
        tableSumMorningModel.removeModelListener();
        tableSumDayModel.removeModelListener();
        tableSumEveningModel.removeModelListener();

        // Указываем, что в Итоговых таблицах тоже нужно обновлять данные и картограммы
        tableSumMorningModel.setModelListener(kindOfStatement, cartogramMorning);
        tableSumDayModel.setModelListener(kindOfStatement, cartogramDay);
        tableSumEveningModel.setModelListener(kindOfStatement, cartogramEvening);

        // Тригерим слушатель модели - обновляем картограммы
        tableSumMorningModel.getTable().setValueAt(tableSumMorningModel.getTable().getValueAt(0, 0), 0, 0);
        tableSumDayModel.getTable().setValueAt(tableSumDayModel.getTable().getValueAt(0, 0), 0, 0);
        tableSumEveningModel.getTable().setValueAt(tableSumEveningModel.getTable().getValueAt(0, 0), 0, 0);
    }

    // Метод добавляющий слушатель изменения свойств в этот класс
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    private String getKindOfStatementFromComboBox() {
        // Если вид таблицы СТАРЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("Старая")) {
            kindOfStatement = "Now";
        }
        // Если вид таблицы НОВЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("Новая")) {
            kindOfStatement = "Future";
        }
        return kindOfStatement;
    }

    public String getFullFileName() {
        return fullFileName;
    }

    public JBroTable getTable15Morning() {
        return table15Morning;
    }

    public JBroTable getTable30Morning() {
        return table30Morning;
    }

    public JBroTable getTable45Morning() {
        return table45Morning;
    }

    public JBroTable getTable60Morning() {
        return table60Morning;
    }

    public JBroTable getTableSumMorning() {
        return tableSumMorning;
    }

    public JBroTable getTable15Day() {
        return table15Day;
    }

    public JBroTable getTable30Day() {
        return table30Day;
    }

    public JBroTable getTable45Day() {
        return table45Day;
    }

    public JBroTable getTable60Day() {
        return table60Day;
    }

    public JBroTable getTableSumDay() {
        return tableSumDay;
    }

    public JBroTable getTable15Evening() {
        return table15Evening;
    }

    public JBroTable getTable30Evening() {
        return table30Evening;
    }

    public JBroTable getTable45Evening() {
        return table45Evening;
    }

    public JBroTable getTable60Evening() {
        return table60Evening;
    }

    public JBroTable getTableSumEvening() {
        return tableSumEvening;
    }

    private String getFullName() {
        if (fullFileName != null) {
            this.fullName = fullFileName;
        }
        return this.fullName;
    }

    // Вид ведомости (Now, Future)
    public String getKindOfStatement() {
        return kindOfStatement;
    }

    // Количество направлений (4, 3 вправо, 3 влево и т.п.)
    public String getTypeOfDirection() {
        return typeOfDirection;
    }

    public TreePath[] getPaths() {
        return paths;
    }

    public CreateCartogram getCartogramMorning() {
        return cartogramMorning;
    }

    public CreateCartogram getCartogramDay() {
        return cartogramDay;
    }

    public CreateCartogram getCartogramEvening() {
        return cartogramEvening;
    }

    public JPanel getCartogramPanelMorning() {
        return cartogramPanelMorning;
    }

    public JPanel getCartogramPanelDay() {
        return cartogramPanelDay;
    }

    public JPanel getCartogramPanelEvening() {
        return cartogramPanelEvening;
    }

    public JButton getTable0_15() {
        return table0_15;
    }

    public JButton getTable15_30() {
        return table15_30;
    }

    public JButton getTable30_45() {
        return table30_45;
    }

    public JButton getTable45_60() {
        return table45_60;
    }

    public JRadioButton getMorningRadio() {
        return morningRadio;
    }

    public JRadioButton getDayRadio() {
        return dayRadio;
    }

    public JRadioButton getEveningRadio() {
        return eveningRadio;
    }

    public ButtonGroup getGroupPeriodOfDay() {
        return groupPeriodOfDay;
    }

    public ButtonGroup getGroup15MinuteTable() {
        return group15MinuteTable;
    }

    public JRadioButton getTable0_15Radio() {
        return table0_15Radio;
    }

    public JRadioButton getTable15_30Radio() {
        return table15_30Radio;
    }

    public JRadioButton getTable30_45Radio() {
        return table30_45Radio;
    }

    public JRadioButton getTable45_60Radio() {
        return table45_60Radio;
    }

}
