package ru.trafficClicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.tree.TreePath;
import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.view.JBroTable;
import ru.Excel.Save.FileSave;
import ru.Excel.Save.templates.FileSaveWithPattern;
import ru.Excel.Save.templates.savePattern.SaveInExistingFile;
import ru.JCheckBoxTree;
import ru.Overlay;
import ru.jtable.Table;
import ru.jtable.model.*;

/**
 * Класс для создания Панели (в виде таблицы) Настройки подсчета.<n>
 * На вкладке таблицы.
 */
public class CreateConfigurationPanel {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this); // переменная позволяющая добавить в этот слушатель изменения свойств 

    private FileSaveWithPattern fileSaveWithPattern;
    private String fullFileName;
    private String fullName;
    private String kindOfStatement;
    private String typeOfDirection;
    private String typeOfStatement;

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

    // Панель управления панелями кнопок
    // В вертикальную панель наверх устанавливаем горизонтальную панель, а вниз устанавливаем вертикальную панель.
    // В новую вертикальную панель навеох добавляем горизонитальную панель, а вниз ...
    public JComponent CreateConfigurationPanel() {
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
        vertSplit3.setDividerLocation(250); // установка положения разделительной линии первоначальное

        horizSplit3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit3.setDividerSize(3);

        // Самая нижняя панель
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
        final String[] nodes = new String[]{"Легковые", "Автобусы", "Грузовые", "Автопоезда", "Пешеходы", "Велотранспорт", "Трамвай", "Троллейбусы"};
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

//        checkBox.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
//
//            @Override
//            public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
//                System.out.println("event");
//                TreePath[] paths = checkBox.getCheckedPaths();
//                for (TreePath tp : paths) {
////                    System.out.println("d: " + String.valueOf(tp.getLastPathComponent()));
//                    for (Object pathPart : tp.getPath()) {
//                        System.out.println("1 " + tp.getLastPathComponent() );
//                    }
//                    System.out.println();
//                }
//            }
//        });
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
                vertSplit3.setDividerLocation(250);
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
                        createTable((new CrossRoadModel()).getModel(), "Now"); // создаем новую таблицу
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            // Если выбрано 4 направления кольцо, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4 кольцо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/4 кольцо");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new CrossRoadModel()).getModel(), "Now");
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4 кольцо";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            // Если выбрано 3 направления вверх, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вверх")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/3 вверх");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new TUpRoadModel()).getModel(), "Now");
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вверх";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            // Если выбрано 3 направления вправо, то:
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вправо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Now/3 вверх");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new TUpRoadModel()).getModel(), "Now");
                        kindOfStatement = "старая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вверх";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        // Если вид таблицы НОВЫЙ, то:
        if (((String) (comboBox3.getSelectedItem())).equalsIgnoreCase("новая")) {
            // Определяем, какие кнопки отображать и что будем считать
//            for (TreePath tp : paths) {
//                String temp = String.valueOf(tp.getLastPathComponent());
//                if (temp.equalsIgnoreCase("Легковые")) {
//
//                }
//                if (temp.equalsIgnoreCase("Автобусы")) {
//
//                }
//                if (temp.equalsIgnoreCase("Грузовые")) {
//
//                }
//                if (temp.equalsIgnoreCase("Автопоезда")) {
//
//                }
//                if (temp.equalsIgnoreCase("Трамвай")) {
//
//                }
//                if (temp.equalsIgnoreCase("Троллейбусы")) {
//
//                }
//            }

            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/4");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new CrossRoadModel()).getModel(), "Future");
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("4 кольцо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/4");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new CrossRoadModel()).getModel(), "Future");
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "4 кольцо";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вправо")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/3 вправо");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new TRightRoadModel()).getModel(), "Future");
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вправо";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (((String) (comboBox1.getSelectedItem())).equalsIgnoreCase("3 вверх")) {
                try {
                    fileSaveWithPattern = new FileSaveWithPattern("Future/3 вправо");
                    fullFileName = fileSaveWithPattern.getFullFileName();
                    if (fullFileName != null) {
                        createTable((new TRightRoadModel()).getModel(), "Future");
                        kindOfStatement = "новая"; // передаем параметры для дальнейшего определения как сохранять данные
                        typeOfDirection = "3 вправо";
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
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
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 4 направления кольцо, то:
            if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 3 направления вверх, то:
            if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 3 направления вправо, то:
            if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, "3 вверх");
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Если вид таблицы НОВЫЙ, то:
        if (kindOfStatement.equalsIgnoreCase("новая")) {

            if (typeOfDirection.equalsIgnoreCase("4")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Если выбрано 4 направления кольцо, то:
            if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, typeOfDirection);
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                try {
                    new SaveInExistingFile(getFullName(), table, kindOfStatement, "3 вверх");
                } catch (IOException ex) {
                    Logger.getLogger(CreateConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Создание таблицы с переданными параметрами
    private void createTable(IModelFieldGroup[] modelGroup, String typeOfStatement) {
        Table tableModel = new Table();
        try {
            table = tableModel.doTable(modelGroup, typeOfStatement);
            this.typeOfStatement = typeOfStatement;
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

    public String getTypeOfStatement() {
        return typeOfStatement;
    }

    public TreePath[] getPaths() {
        return paths;
    }

}
