/**
 * Рабочая область с кнопками поверх видео
 */
package ru;

import com.sun.jna.platform.WindowUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import net.miginfocom.swing.MigLayout;

public class Overlay extends JWindow {

    // ИКОНКИ
    // Машина
    private ImageIcon IconCar = new ImageIcon(this.getClass().getResource("/icons/car/car.png"));
    // Грузовики
    private ImageIcon IconTruck1 = new ImageIcon(this.getClass().getResource("/icons/car/truck1.png"));
    private ImageIcon IconTruck2 = new ImageIcon(this.getClass().getResource("/icons/car/truck2.png"));
    private ImageIcon IconTruck3 = new ImageIcon(this.getClass().getResource("/icons/car/truck3.png"));
    private ImageIcon IconTruck4 = new ImageIcon(this.getClass().getResource("/icons/car/truck4.png"));
    // Автобусы
    private ImageIcon IconBus1 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    private ImageIcon IconBus2 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    private ImageIcon IconBus3 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    private ImageIcon IconBus4 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));

    // Иконки направления движения
    private ImageIcon aroundIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_around.png"));
    private ImageIcon leftIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_turn_left_90.png"));
    private ImageIcon forwardIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_forward.png"));
    private ImageIcon rightIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_turn_right_90.png"));

    // Иконки пешехода и велосипедиста
    private ImageIcon manIcon = new ImageIcon(this.getClass().getResource("/icons/roads/walk.png"));
    private ImageIcon bikeIcon = new ImageIcon(this.getClass().getResource("/icons/roads/bicycle.png"));

    // ИСПОЛЬЗУЕМЫЕ КОМПОНЕНТЫ
    // ВЕРХНЯЯ панель
    private Component[][] upContainerComponent;

    private JLabel aroundUpLabel = new JLabel(aroundIcon);
    private JLabel leftUpLabel = new JLabel(leftIcon);
    private JLabel forwardUpLabel = new JLabel(forwardIcon);
    private JLabel rightUpLabel = new JLabel(rightIcon);

    private JButton bAroundUpCar = new JButton(IconCar);
    private JButton bAroundUpTruck1 = new JButton(IconTruck1);
    private JButton bAroundUpTruck2 = new JButton(IconTruck2);
    private JButton bAroundUpTruck3 = new JButton(IconTruck3);
    private JButton bAroundUpTruck4 = new JButton(IconTruck4);
    private JButton bAroundUpBus1 = new JButton(IconBus1);
    private JButton bAroundUpBus2 = new JButton(IconBus2);
    private JButton bAroundUpBus3 = new JButton(IconBus3);
    private JButton bAroundUpBus4 = new JButton(IconBus4);

    private JButton bLeftUpCar = new JButton(IconCar);
    private JButton bLeftUpTruck1 = new JButton(IconTruck1);
    private JButton bLeftUpTruck2 = new JButton(IconTruck2);
    private JButton bLeftUpTruck3 = new JButton(IconTruck3);
    private JButton bLeftUpTruck4 = new JButton(IconTruck4);
    private JButton bLeftUpBus1 = new JButton(IconBus1);
    private JButton bLeftUpBus2 = new JButton(IconBus2);
    private JButton bLeftUpBus3 = new JButton(IconBus3);
    private JButton bLeftUpBus4 = new JButton(IconBus4);

    private JButton bForwardUpCar = new JButton(IconCar);
    private JButton bForwardUpTruck1 = new JButton(IconTruck1);
    private JButton bForwardUpTruck2 = new JButton(IconTruck2);
    private JButton bForwardUpTruck3 = new JButton(IconTruck3);
    private JButton bForwardUpTruck4 = new JButton(IconTruck4);
    private JButton bForwardUpBus1 = new JButton(IconBus1);
    private JButton bForwardUpBus2 = new JButton(IconBus2);
    private JButton bForwardUpBus3 = new JButton(IconBus3);
    private JButton bForwardUpBus4 = new JButton(IconBus4);

    private JButton bRightUpCar = new JButton(IconCar);
    private JButton bRightUpTruck1 = new JButton(IconTruck1);
    private JButton bRightUpTruck2 = new JButton(IconTruck2);
    private JButton bRightUpTruck3 = new JButton(IconTruck3);
    private JButton bRightUpTruck4 = new JButton(IconTruck4);
    private JButton bRightUpBus1 = new JButton(IconBus1);
    private JButton bRightUpBus2 = new JButton(IconBus2);
    private JButton bRightUpBus3 = new JButton(IconBus3);
    private JButton bRightUpBus4 = new JButton(IconBus4);

    private JButton bManUp = new JButton(manIcon);
    private JButton bBikeUp = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsUp = {
        aroundUpLabel, leftUpLabel, forwardUpLabel, rightUpLabel,
        bAroundUpCar, bLeftUpCar, bForwardUpCar, bRightUpCar,
        bAroundUpTruck1, bLeftUpTruck1, bForwardUpTruck1, bRightUpTruck1,
        bAroundUpBus1, bLeftUpBus1, bForwardUpBus1, bRightUpBus1
    };

    // Для перемещения панели с кнопками
    private JLabel[] labelsUp = {
        aroundUpLabel, leftUpLabel, forwardUpLabel, rightUpLabel
    };

    // Компоненты в комбобоксы (кнопка с выбором вариантов)
    private JComponent[] comboBoxComponentAroundUpTruck = {
        bAroundUpTruck1, bAroundUpTruck2, bAroundUpTruck3, bAroundUpTruck4
    };
    private JComponent[] comboBoxComponentLeftUpTruck = {
        bLeftUpTruck1, bLeftUpTruck2, bLeftUpTruck3, bLeftUpTruck4
    };
    private JComponent[] comboBoxComponentForwardUpTruck = {
        bForwardUpTruck1, bForwardUpTruck2, bForwardUpTruck3, bForwardUpTruck4
    };
    private JComponent[] comboBoxComponentRightUpTruck = {
        bRightUpTruck1, bRightUpTruck2, bRightUpTruck3, bRightUpTruck4
    };

    // Все кнопки, используемые в панели и превращаемые из обычной кнопки в кнопку-иконку
    private JButton[] buttonsUp = {
        bAroundUpCar, bLeftUpCar, bForwardUpCar, bRightUpCar,
        ////////////////////////////////////////////////////////////////////////
        bAroundUpTruck1, bAroundUpTruck2, bAroundUpTruck3, bAroundUpTruck4,
        bLeftUpTruck1, bLeftUpTruck2, bLeftUpTruck3, bLeftUpTruck4,
        bForwardUpTruck1, bForwardUpTruck2, bForwardUpTruck3, bForwardUpTruck4,
        bRightUpTruck1, bRightUpTruck2, bRightUpTruck3, bRightUpTruck4,
        ////////////////////////////////////////////////////////////////////////
        bAroundUpBus1, bAroundUpBus2, bAroundUpBus3, bAroundUpBus4,
        bLeftUpBus1, bLeftUpBus2, bLeftUpBus3, bLeftUpBus4,
        bForwardUpBus1, bForwardUpBus2, bForwardUpBus3, bForwardUpBus4,
        bRightUpBus1, bRightUpBus2, bRightUpBus3, bRightUpBus4
    //        createButtonWithIcon(bManUp, manIcon),
    //        createButtonWithIcon(bBikeUp, bikeIcon)
    };

    // ЛЕВАЯ панель
    private JLabel aroundLeftLabel = new JLabel(aroundIcon);
    private JLabel leftLeftLabel = new JLabel(leftIcon);
    private JLabel forwardLeftLabel = new JLabel(forwardIcon);
    private JLabel rightLeftLabel = new JLabel(rightIcon);

    private JButton bAroundLeftCar = new JButton(IconCar);
    private JButton bAroundLeftTruck1 = new JButton(IconTruck1);
    private JButton bAroundLeftTruck2 = new JButton(IconTruck2);
    private JButton bAroundLeftTruck3 = new JButton(IconTruck3);
    private JButton bAroundLeftTruck4 = new JButton(IconTruck4);
    private JButton bAroundLeftBus1 = new JButton(IconBus1);
    private JButton bAroundLeftBus2 = new JButton(IconBus2);
    private JButton bAroundLeftBus3 = new JButton(IconBus3);
    private JButton bAroundLeftBus4 = new JButton(IconBus4);

    private JButton bLeftLeftCar = new JButton(IconCar);
    private JButton bLeftLeftTruck1 = new JButton(IconTruck1);
    private JButton bLeftLeftTruck2 = new JButton(IconTruck2);
    private JButton bLeftLeftTruck3 = new JButton(IconTruck3);
    private JButton bLeftLeftTruck4 = new JButton(IconTruck4);
    private JButton bLeftLeftBus1 = new JButton(IconBus1);
    private JButton bLeftLeftBus2 = new JButton(IconBus2);
    private JButton bLeftLeftBus3 = new JButton(IconBus3);
    private JButton bLeftLeftBus4 = new JButton(IconBus4);

    private JButton bForwardLeftCar = new JButton(IconCar);
    private JButton bForwardLeftTruck1 = new JButton(IconTruck1);
    private JButton bForwardLeftTruck2 = new JButton(IconTruck2);
    private JButton bForwardLeftTruck3 = new JButton(IconTruck3);
    private JButton bForwardLeftTruck4 = new JButton(IconTruck4);
    private JButton bForwardLeftBus1 = new JButton(IconBus1);
    private JButton bForwardLeftBus2 = new JButton(IconBus2);
    private JButton bForwardLeftBus3 = new JButton(IconBus3);
    private JButton bForwardLeftBus4 = new JButton(IconBus4);

    private JButton bRightLeftCar = new JButton(IconCar);
    private JButton bRightLeftTruck1 = new JButton(IconTruck1);
    private JButton bRightLeftTruck2 = new JButton(IconTruck2);
    private JButton bRightLeftTruck3 = new JButton(IconTruck3);
    private JButton bRightLeftTruck4 = new JButton(IconTruck4);
    private JButton bRightLeftBus1 = new JButton(IconBus1);
    private JButton bRightLeftBus2 = new JButton(IconBus2);
    private JButton bRightLeftBus3 = new JButton(IconBus3);
    private JButton bRightLeftBus4 = new JButton(IconBus4);

    private JButton bManLeft = new JButton(manIcon);
    private JButton bBikeLeft = new JButton(bikeIcon);

    private JLabel[] labelsLeft = {
        aroundLeftLabel, leftLeftLabel, forwardLeftLabel, rightLeftLabel
    };

    private JButton[] buttonsLeft = {
        bAroundLeftCar, bLeftLeftCar, bForwardLeftCar, bRightLeftCar,
        ////////////////////////////////////////////////////////////////////////
        bAroundLeftTruck1, bAroundLeftTruck2, bAroundLeftTruck3, bAroundLeftTruck4,
        bLeftLeftTruck1, bLeftLeftTruck2, bLeftLeftTruck3, bLeftLeftTruck4,
        bForwardLeftTruck1, bForwardLeftTruck2, bForwardLeftTruck3, bForwardLeftTruck4,
        bRightLeftTruck1, bRightLeftTruck2, bRightLeftTruck3, bRightLeftTruck4,
        ////////////////////////////////////////////////////////////////////////
        bAroundLeftBus1, bAroundLeftBus2, bAroundLeftBus3, bAroundLeftBus4,
        bLeftLeftBus1, bLeftLeftBus2, bLeftLeftBus3, bLeftLeftBus4,
        bForwardLeftBus1, bForwardLeftBus2, bForwardLeftBus3, bForwardLeftBus4,
        bRightLeftBus1, bRightLeftBus2, bRightLeftBus3, bRightLeftBus4
    //        createButtonWithIcon(bManLeft, manIcon),       
    //        createButtonWithIcon(bBikeLeft, bikeIcon)
    };

    //НИЖНЯЯ панель
    private JLabel aroundDownLabel = new JLabel(aroundIcon);
    private JLabel leftDownLabel = new JLabel(leftIcon);
    private JLabel forwardDownLabel = new JLabel(forwardIcon);
    private JLabel rightDownLabel = new JLabel(rightIcon);

    private JButton bAroundDownCar = new JButton(IconCar);
    private JButton bAroundDownTruck1 = new JButton(IconTruck1);
    private JButton bAroundDownTruck2 = new JButton(IconTruck2);
    private JButton bAroundDownTruck3 = new JButton(IconTruck3);
    private JButton bAroundDownTruck4 = new JButton(IconTruck4);
    private JButton bAroundDownBus1 = new JButton(IconBus1);
    private JButton bAroundDownBus2 = new JButton(IconBus2);
    private JButton bAroundDownBus3 = new JButton(IconBus3);
    private JButton bAroundDownBus4 = new JButton(IconBus4);

    private JButton bLeftDownCar = new JButton(IconCar);
    private JButton bLeftDownTruck1 = new JButton(IconTruck1);
    private JButton bLeftDownTruck2 = new JButton(IconTruck2);
    private JButton bLeftDownTruck3 = new JButton(IconTruck3);
    private JButton bLeftDownTruck4 = new JButton(IconTruck4);
    private JButton bLeftDownBus1 = new JButton(IconBus1);
    private JButton bLeftDownBus2 = new JButton(IconBus2);
    private JButton bLeftDownBus3 = new JButton(IconBus3);
    private JButton bLeftDownBus4 = new JButton(IconBus4);

    private JButton bForwardDownCar = new JButton(IconCar);
    private JButton bForwardDownTruck1 = new JButton(IconTruck1);
    private JButton bForwardDownTruck2 = new JButton(IconTruck2);
    private JButton bForwardDownTruck3 = new JButton(IconTruck3);
    private JButton bForwardDownTruck4 = new JButton(IconTruck4);
    private JButton bForwardDownBus1 = new JButton(IconBus1);
    private JButton bForwardDownBus2 = new JButton(IconBus2);
    private JButton bForwardDownBus3 = new JButton(IconBus3);
    private JButton bForwardDownBus4 = new JButton(IconBus4);

    private JButton bRightDownCar = new JButton(IconCar);
    private JButton bRightDownTruck1 = new JButton(IconTruck1);
    private JButton bRightDownTruck2 = new JButton(IconTruck2);
    private JButton bRightDownTruck3 = new JButton(IconTruck3);
    private JButton bRightDownTruck4 = new JButton(IconTruck4);
    private JButton bRightDownBus1 = new JButton(IconBus1);
    private JButton bRightDownBus2 = new JButton(IconBus2);
    private JButton bRightDownBus3 = new JButton(IconBus3);
    private JButton bRightDownBus4 = new JButton(IconBus4);

    private JButton bManDown = new JButton(manIcon);
    private JButton bBikeDown = new JButton(bikeIcon);

    private JLabel[] labelsDown = {
        aroundDownLabel, leftDownLabel, forwardDownLabel, rightDownLabel
    };

    private JButton[] buttonsDown = {
        bAroundDownCar, bLeftDownCar, bForwardDownCar, bRightDownCar,
        ////////////////////////////////////////////////////////////////////////
        bAroundDownTruck1, bAroundDownTruck2, bAroundDownTruck3, bAroundDownTruck4,
        bLeftDownTruck1, bLeftDownTruck2, bLeftDownTruck3, bLeftDownTruck4,
        bForwardDownTruck1, bForwardDownTruck2, bForwardDownTruck3, bForwardDownTruck4,
        bRightDownTruck1, bRightDownTruck2, bRightDownTruck3, bRightDownTruck4,
        ////////////////////////////////////////////////////////////////////////
        bAroundDownBus1, bAroundDownBus2, bAroundDownBus3, bAroundDownBus4,
        bLeftDownBus1, bLeftDownBus2, bLeftDownBus3, bLeftDownBus4,
        bForwardDownBus1, bForwardDownBus2, bForwardDownBus3, bForwardDownBus4,
        bRightDownBus1, bRightDownBus2, bRightDownBus3, bRightDownBus4
//        createButtonWithIcon(bManDown, manIcon),
//        createButtonWithIcon(bBikeDown, bikeIcon)
    };

    //ПРАВАЯ панель
    private JLabel aroundRightLabel = new JLabel(aroundIcon);
    private JLabel leftRightLabel = new JLabel(leftIcon);
    private JLabel forwardRightLabel = new JLabel(forwardIcon);
    private JLabel rightRightLabel = new JLabel(rightIcon);

    private JButton bAroundRightCar = new JButton(IconCar);
    private JButton bAroundRightTruck1 = new JButton(IconTruck1);
    private JButton bAroundRightTruck2 = new JButton(IconTruck2);
    private JButton bAroundRightTruck3 = new JButton(IconTruck3);
    private JButton bAroundRightTruck4 = new JButton(IconTruck4);
    private JButton bAroundRightBus1 = new JButton(IconBus1);
    private JButton bAroundRightBus2 = new JButton(IconBus2);
    private JButton bAroundRightBus3 = new JButton(IconBus3);
    private JButton bAroundRightBus4 = new JButton(IconBus4);

    private JButton bLeftRightCar = new JButton(IconCar);
    private JButton bLeftRightTruck1 = new JButton(IconTruck1);
    private JButton bLeftRightTruck2 = new JButton(IconTruck2);
    private JButton bLeftRightTruck3 = new JButton(IconTruck3);
    private JButton bLeftRightTruck4 = new JButton(IconTruck4);
    private JButton bLeftRightBus1 = new JButton(IconBus1);
    private JButton bLeftRightBus2 = new JButton(IconBus2);
    private JButton bLeftRightBus3 = new JButton(IconBus3);
    private JButton bLeftRightBus4 = new JButton(IconBus4);

    private JButton bForwardRightCar = new JButton(IconCar);
    private JButton bForwardRightTruck1 = new JButton(IconTruck1);
    private JButton bForwardRightTruck2 = new JButton(IconTruck2);
    private JButton bForwardRightTruck3 = new JButton(IconTruck3);
    private JButton bForwardRightTruck4 = new JButton(IconTruck4);
    private JButton bForwardRightBus1 = new JButton(IconBus1);
    private JButton bForwardRightBus2 = new JButton(IconBus2);
    private JButton bForwardRightBus3 = new JButton(IconBus3);
    private JButton bForwardRightBus4 = new JButton(IconBus4);

    private JButton bRightRightCar = new JButton(IconCar);
    private JButton bRightRightTruck1 = new JButton(IconTruck1);
    private JButton bRightRightTruck2 = new JButton(IconTruck2);
    private JButton bRightRightTruck3 = new JButton(IconTruck3);
    private JButton bRightRightTruck4 = new JButton(IconTruck4);
    private JButton bRightRightBus1 = new JButton(IconBus1);
    private JButton bRightRightBus2 = new JButton(IconBus2);
    private JButton bRightRightBus3 = new JButton(IconBus3);
    private JButton bRightRightBus4 = new JButton(IconBus4);

    private JButton bManRight = new JButton(manIcon);
    private JButton bBikeRight = new JButton(bikeIcon);

    private JLabel[] labelsRight = {
        aroundRightLabel, leftRightLabel, forwardRightLabel, rightRightLabel
    };

    private JButton[] buttonsRight = {
        bAroundRightCar, bLeftRightCar, bForwardRightCar, bRightRightCar,
        ////////////////////////////////////////////////////////////////////////
        bAroundRightTruck1, bAroundRightTruck2, bAroundRightTruck3, bAroundRightTruck4,
        bLeftRightTruck1, bLeftRightTruck2, bLeftRightTruck3, bLeftRightTruck4,
        bForwardRightTruck1, bForwardRightTruck2, bForwardRightTruck3, bForwardRightTruck4,
        bRightRightTruck1, bRightRightTruck2, bRightRightTruck3, bRightRightTruck4,
        ////////////////////////////////////////////////////////////////////////
        bAroundRightBus1, bAroundRightBus2, bAroundRightBus3, bAroundRightBus4,
        bLeftRightBus1, bLeftRightBus2, bLeftRightBus3, bLeftRightBus4,
        bForwardRightBus1, bForwardRightBus2, bForwardRightBus3, bForwardRightBus4,
        bRightRightBus1, bRightRightBus2, bRightRightBus3, bRightRightBus4
//        createButtonWithIcon(bManRight, manIcon),
//        createButtonWithIcon(bBikeRight, bikeIcon)
    };

    // Создание абстрактного заполнения для комбобокса для использования во всех комбобоксах
    private String component0 = new String("0");
    private String component1 = new String("1");
    private String component2 = new String("2");
    private String component3 = new String("3");
    private String component4 = new String("4");
    private String component5 = new String("5");
    private String component6 = new String("6");
    private String[] componentsComboBox = new String[]{component0, component1, component2, component3, component4, component5, component6};

    // Создание ссылок на комбобоксы
    private JComboBox comboBoxAroundUpTruck = new JComboBox(componentsComboBox);
    private JComboBox comboBoxLeftUpTruck = new JComboBox(componentsComboBox);
    private JComboBox comboBoxForwardUpTruck = new JComboBox(componentsComboBox);
    private JComboBox comboBoxRightUpTruck = new JComboBox(componentsComboBox);

    // Ссылка на Layout менеджер, который конфигурирует 4 столбца и сколько нужно строк
    MigLayout panelUpLayout = new MigLayout("wrap 4");

    private JPanel panelUp = new JPanel(panelUpLayout) {
//        GridLayout(4, 4, 0, 0)) {
        // для правильной установки прозрачности кнопок переопределяем метод отрисовки компонента
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    };

    private JPanel panelLeft = new JPanel(new GridLayout(4, 4, 0, 0)) {
        // для правильной установки прозрачности кнопок переопределяем метод отрисовки компонента
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    };

    private JPanel panelDown = new JPanel(new GridLayout(4, 4, 0, 0)) {
        // для правильной установки прозрачности кнопок переопределяем метод отрисовки компонента
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    };

    private JPanel panelRight = new JPanel(new GridLayout(4, 4, 0, 0)) {
        // для правильной установки прозрачности кнопок переопределяем метод отрисовки компонента
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    };

    // Основная overlay панель, на которую помещаются кнопки. LayoutManager = null
    private JPanel overlayPanel = new JPanel(null);

    private int component0Click = 0;
    private int component1Click = 0;
    private int component2Click = 0;
    private int component3Click = 0;
    private int component0Click0 = 0;
    private int component1Click1 = 0;
    private int component2Click2 = 0;
    private int component3Click3 = 0;

    public Overlay(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        // установка прозрачности overlay панели
        setBackground(new Color(0, 0, 0, 0));
        overlayPanel.setOpaque(false); // прозрачность включена
        overlayPanel.setBackground(new Color(0, 0, 0, 0)); // установка цвета задней поверхности (первые три нуля - цвет, последний 0 - прозрачность)
        // добавление панели на окно overlay (основная рабочая прозрачная панель)
        add(overlayPanel);
        // Применение ко всем кнопкам (которые хранятся в массиве) свойств ("привращение" кнопки в иконку)
        createButtonsWithIcon(buttonsUp);
        createButtonsWithIcon(buttonsLeft);
        createButtonsWithIcon(buttonsDown);
        createButtonsWithIcon(buttonsRight);

        // Конфигурация (заполнение) кнопки с возможностью выбора вариантов (комбобокс)
        // Верхняя панель
        fillingComboBox(comboBoxAroundUpTruck, comboBoxComponentAroundUpTruck);
        fillingComboBox(comboBoxLeftUpTruck, comboBoxComponentLeftUpTruck);
        fillingComboBox(comboBoxForwardUpTruck, comboBoxComponentForwardUpTruck);
        fillingComboBox(comboBoxRightUpTruck, comboBoxComponentRightUpTruck);

        // Конфигурация панелей и добавление на панели требуемых элементов
        panelUpLayout.setColumnConstraints("0[]0[]0[]0[]0"); // отступы между строками
        panelUpLayout.setRowConstraints("0[]0[]0[]0[]0"); // отступы между столбцами
//        addJLabelOnButtonsPanel(panelUp, labelsUp); // добавление Лэйблов с указанием направления движения
//        createPanelOfButton(panelUp, buttonsUp); // конфигурация панели и добавление на панель требуемых кнопок (панель 4х4)

        // Добавление исходных компонентов 
        upContainerComponent = new Component[4][4]; // указание размеров панели
        createEmptyPanelOfButtons(panelUp, upContainerComponent); // заполнение панели пустыми элементами 
        createPanelOfButtons(panelUp, upContainerComponent, componentsUp); // заполнение панели нужными элементами (componentsUp)
        panelUp.setLocation(200, 0); // установка изначального местоположения панели

        addJLabelOnButtonsPanel(panelLeft, labelsLeft);
        createPanelOfButton(panelLeft, buttonsLeft);
        panelLeft.setLocation(0, 100);

        addJLabelOnButtonsPanel(panelDown, labelsDown);
        createPanelOfButton(panelDown, buttonsDown);
        panelDown.setLocation(500, 400);

        addJLabelOnButtonsPanel(panelRight, labelsRight);
        createPanelOfButton(panelRight, buttonsRight);
        panelRight.setLocation(650, 0);

        overlayPanel.add(panelUp);
        overlayPanel.add(panelLeft);
        overlayPanel.add(panelDown);
        overlayPanel.add(panelRight);

        // замена элемента на панеле
        upContainerComponent[2][0] = comboBoxAroundUpTruck; // заменяем элемент
        upContainerComponent[2][1] = comboBoxLeftUpTruck; // заменяем элемент
        upContainerComponent[2][2] = comboBoxForwardUpTruck; // заменяем элемент
        upContainerComponent[2][3] = comboBoxRightUpTruck; // заменяем элемент

        // динамическое изменение компонентов комбобокса
//        componentAroundUpTruck = new String[]{component0, component2, component3};
//        comboBoxAroundUpTruck.setModel(new DefaultComboBoxModel(componentAroundUpTruck));
        // добавление слушателя к комбобоксу
        comboBoxButtonsOnPanelActionListener comboBoxAroundUpTruckActionList = new comboBoxButtonsOnPanelActionListener(component0Click, component1Click, component2Click, component3Click);
        comboBoxAroundUpTruck.addActionListener(comboBoxAroundUpTruckActionList);

        // установка прозрачности Комбобокса (ЗАРАБОТАЛО!?)
        comboBoxAroundUpTruck.setBackground(new Color(0, 0, 0, 0));
        comboBoxAroundUpTruck.getComponents()[0].setBackground(new JComboBox().getBackground());

        addOnPanelOfButtons(panelUp, upContainerComponent); // выполняем метод для применения обновления панели: указываем панель, на которой расположен элемент и массив элементов (контейнеров под них)
    }

    // Класс подсчета количества кликов мышью по кнопкам (абстрактный, можно применять к любым кнопкам, но нужно указать переменнуые хранения количества кликов
    private class comboBoxButtonsOnPanelActionListener implements ActionListener {

        private int component0Click;
        private int component1Click;
        private int component2Click;
        private int component3Click;

        public comboBoxButtonsOnPanelActionListener(int component0Click, int component1Click, int component2Click, int component3Click) {
            this.component0Click = component0Click;
            this.component1Click = component1Click;
            this.component2Click = component2Click;
            this.component3Click = component3Click;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String componentName = (String) ((JComboBox) e.getSource()).getSelectedItem();
            if (componentName.equalsIgnoreCase(component0)) {
                component0Click = component0Click + 1;
                System.out.println(component0Click + " №0");
            }
            if (componentName.equalsIgnoreCase(component1)) {
                component1Click = component1Click + 1;
                System.out.println(component1Click + " №1");
            }
            if (componentName.equalsIgnoreCase(component2)) {
                component2Click = component2Click + 1;
                System.out.println(component2Click + " №2");
            }
            if (componentName.equalsIgnoreCase(component3)) {
                component3Click = component3Click + 1;
                System.out.println(component3Click + " №3");
            }
        }

    }

    // Метод заполнения комбобокса компонентами, через переопределенный рендерер
    private void fillingComboBox(JComboBox comboBox, JComponent[] comboBoxComponents) {
        ComboBoxRenderer renderer = new ComboBoxRenderer(); // создаем рендерер (который поможет правильно отображать (представлять) компоненты (кнопки, лэйблы и т.п.) в комбобоксе
        renderer.ComboBoxRenderer(comboBoxComponents); // указываем, что добавить в комбобокс
        createComboBox(comboBox, renderer); // конфигурация комбобокса и указание для него рендерера
    }

    // Заполняем панель компонентами (абсрактными) и заменяем компоненты (ссылки на них) нужными нам элементами. Чтобы затем иметь возможность заменять элементы в сконфигурированной панели
    private JPanel createEmptyPanelOfButtons(JPanel panel, Component[][] containerComponents) {
        // Настройка панели
        panel.setSize(new Dimension(leftIcon.getIconWidth() * 4 + 8, leftIcon.getIconHeight() * 4 + 8)); // размер панели с кнопками
        panel.setOpaque(false); // для правильной установки прозрачности кнопок
        panel.setBackground(new Color(.0f, .0f, .0f, .1f)); // установка цвета фона и прозрачности панели с кнопками (чтобы можно было тыкать в видео между кнопок нужно сделать прозрачность нулевой!
        panel.setFocusable(false); // панель не получает фокуса никогда

        // Исходно заполняем массив пустыми элементами (чтобы не было null в массиве) и добавляем их в панель
        for (int i = 0; i < containerComponents.length; i++) {
            for (int j = 0; j < containerComponents.length; j++) {
                containerComponents[i][j] = new JLabel("");
                panel.add(containerComponents[i][j]);
            }
        }

        return panel; // возвращаем панель с добавленными элементами
    }

    private JPanel createPanelOfButtons(JPanel panel, Component[][] containerComponents, JComponent[] injectedComponents) {
        panel.removeAll(); // удаляем все элементы с панели
        // компоненты заменяем на нужные элементы (JLabel, JButton, JComboBox и т.п.)
        mainLoop:
        for (int i = 0; i < containerComponents.length; i++) {
            injectedComponents[i].setFocusable(false); // лэйбл не получает фокуса никогда
            for (int j = 0; j < containerComponents.length; j++) {
                if (j + (containerComponents.length) * i == injectedComponents.length) {
                    break mainLoop;
                }
                containerComponents[i][j] = injectedComponents[j + (containerComponents.length) * i];
            }
        }
        // Добавляем компоненты на панель
        for (int i = 0; i < containerComponents.length; i++) {
            for (int j = 0; j < containerComponents.length; j++) {
                panel.add(containerComponents[i][j]);
            }
        }
        return panel; // возвращаем панель с добавленными элементами
    }

    // Этот метод используется после обновления компонентов на панели. Он удаляет элементы и добавляет
    public JPanel addOnPanelOfButtons(JPanel panel, Component[][] containerComponents) {
        panel.removeAll(); // удаляем все элементы с панели
        // Исходно заполняем массив пустыми элементами (чтобы не было null в массиве) и добавляем их в панель
        for (int i = 0; i < containerComponents.length; i++) {
            for (int j = 0; j < containerComponents.length; j++) {
                panel.add(containerComponents[i][j]);
            }
        }
        return panel; // возвращаем панель с добавленными элементами
    }

    /////////////////////////////////////////////////////////////////////
    //Старый вариант. Под замену.
    private JPanel createPanelOfButton(JPanel panel, JButton[] buttons) {
        panel.setSize(new Dimension(leftIcon.getIconWidth() * 4 + 8, leftIcon.getIconHeight() * 4 + 8)); // размер панели с кнопками
        panel.setOpaque(false); // для правильной установки прозрачности кнопок
        panel.setBackground(new Color(.0f, .0f, .0f, .1f)); // установка цвета фона и прозрачности панели с кнопками (чтобы можно было тыкать в видео между кнопок нужно сделать прозрачность нулевой!
        panel.setFocusable(false); // панель не получает фокуса никогда
//        panel.setBorder(new LineBorder(Color.lightGray)); // граница панели

        for (JButton button : buttons) {
            button.setFocusable(false); // кнопка не получает фокуса никогда
            panel.add(button);
        }

        return panel;
    }

    private void addJLabelOnButtonsPanel(JPanel panel, JLabel[] labels) {
        for (JLabel label : labels) {
            label.setFocusable(false); // лэйбл не получает фокуса никогда
            panel.add(label);
        }
    }
    //////////////////////////////////////////////////////////////////////

    // Для абстрактной кнопки устанавливается размер и убирается отображение стандартных качеств кнопки
    // Если указать иконку, то размер кнопки будет установлен как размер иконки
    private JButton createButtonWithIcon(JButton button, ImageIcon icon) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
//        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
        button.setPreferredSize(new Dimension(icon.getIconWidth() + 2, icon.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setBackground(new Color(0, 0, 0, 0));
        return button;
    }

    // Для абстрактной кнопки устанавливается размер и убирается отображение стандартных качеств кнопки
    private JButton createButtonWithIcon(JButton button) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии

        button.setPreferredSize(new Dimension(leftIcon.getIconWidth() + 2, leftIcon.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setMinimumSize(new Dimension(leftIcon.getIconWidth() + 2, leftIcon.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setMaximumSize(new Dimension(leftIcon.getIconWidth() + 2, leftIcon.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setBackground(new Color(0, 0, 0, 0));
        return button;
    }

    private void createButtonsWithIcon(JButton[] buttons) {
        for (JButton button : buttons) {
            createButtonWithIcon(button);
        }
    }

    // Панель управления панелями кнопок
    public JComponent createConfigurationTablePanel() {
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
        label1.setToolTipText("Количество направлений: "); // Установка всплывающей подсказки

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
        label2.setToolTipText("Единицы подсчета: "); // Установка всплывающей подсказки

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

    class ComboBoxRenderer implements ListCellRenderer {

        // массив компонентов, которые помещяются в комбобокс
        JComponent[] components = new JComponent[7];

        // конструктор, с помощью которого извне передаются компоненты, которые и должны добавиться в комбобокс
        public void ComboBoxRenderer(JComponent[] components) {
            for (int i = 0; i < components.length; i++) {
                this.components[i] = components[i];
            }
        }

        // Переопределяем метод для правильного отображения (представления) компонентов (кнопок, лэйблов и т.п.) в комбобоксе
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value.equals(component0)) {
                return components[0] != null ? components[0] : new JLabel(); // если компонент не null, то возвращаем его, если null, то возвращаем пустой label
            }
            if (value.equals(component1)) {
                return components[1] != null ? components[1] : new JLabel();
            }
            if (value.equals(component2)) {
                return components[2] != null ? components[2] : new JLabel();
            }
            if (value.equals(component3)) {
                return components[3] != null ? components[3] : new JLabel();
            }
            if (value.equals(component4)) {
                return components[4] != null ? components[4] : new JLabel();
            }
            if (value.equals(component5)) {
                return components[5] != null ? components[5] : new JLabel();
            }
            if (value.equals(component6)) {
                return components[6] != null ? components[6] : new JLabel();
            } else {
                JLabel lbl = new JLabel(value.toString());
                lbl.setOpaque(true);
                return lbl;
            }
        }
    }

    // Конфигурация комбобокса
    private void createComboBox(JComboBox comboBox, ComboBoxRenderer renderer) {
        comboBox.setRenderer(renderer);
        comboBox.setBounds(300, 300, 36, 36);
        comboBox.setPreferredSize(new Dimension(36, 36));
        comboBox.setMinimumSize(new Dimension(36, 36));
        comboBox.setMaximumSize(new Dimension(36, 36));
        comboBox.setLayout(new BorderLayout());
        comboBox.setBackground(Color.LIGHT_GRAY);
        comboBox.setBorder(null);
    }

    public JLabel[] getLabelsUp() {
        return labelsUp;
    }

    public JLabel[] getLabelsLeft() {
        return labelsLeft;
    }

    public JLabel[] getLabelsDown() {
        return labelsDown;
    }

    public JLabel[] getLabelsRight() {
        return labelsRight;
    }

    public JButton[] getButtonsUp() {
        return buttonsUp;
    }

    public JButton[] getButtonsLeft() {
        return buttonsLeft;
    }

    public JButton[] getButtonsDown() {
        return buttonsDown;
    }

    public JButton[] getButtonsRight() {
        return buttonsRight;
    }

    public JButton getbLeftUp() {
        return bLeftUpCar;
    }

    public JComboBox getComboBoxLeftUpTruck1() {
        return comboBoxAroundUpTruck;
    }

    public JPanel getPanelUp() {
        return panelUp;
    }

    public JPanel getPanelLeft() {
        return panelLeft;
    }

    public JPanel getPanelDown() {
        return panelDown;
    }

    public JPanel getPanelRight() {
        return panelRight;
    }

    public JPanel getPa() {
        return overlayPanel;
    }
}
