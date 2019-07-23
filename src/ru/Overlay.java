/**
 * Рабочая область с кнопками поверх видео
 */
package ru;

import com.sun.jna.platform.WindowUtils;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.OverlayLayout;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.plaf.multi.MultiComboBoxUI;

public class Overlay extends Window {

    private ImageIcon IconCar = new ImageIcon(this.getClass().getResource("/icons/car/car.png"));
    private ImageIcon IconTruck1 = new ImageIcon(this.getClass().getResource("/icons/car/truck1.png"));
    private ImageIcon IconTruck2 = new ImageIcon(this.getClass().getResource("/icons/car/truck2.png"));
    private ImageIcon IconTruck3 = new ImageIcon(this.getClass().getResource("/icons/car/truck3.png"));
    private ImageIcon IconTruck4 = new ImageIcon(this.getClass().getResource("/icons/car/truck4.png"));
    private ImageIcon IconBus1 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    private ImageIcon IconBus2 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    private ImageIcon IconBus3 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    private ImageIcon IconBus4 = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));

    private ImageIcon aroundIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_around.png"));
    private ImageIcon leftIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_turn_left_90.png"));
    private ImageIcon forwardIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_forward.png"));
    private ImageIcon rightIcon = new ImageIcon(this.getClass().getResource("/icons/roads/routing_turn_right_90.png"));

    private ImageIcon manIcon = new ImageIcon(this.getClass().getResource("/icons/roads/walk.png"));
    private ImageIcon bikeIcon = new ImageIcon(this.getClass().getResource("/icons/roads/bicycle.png"));

    //КНОПКИ
    // ВЕРХНЯЯ панель
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

    private JLabel[] labelsUp = {
        aroundUpLabel, leftUpLabel, forwardUpLabel, rightUpLabel
    };

    private JButton[] buttonsUp = {
        bAroundUpCar, bLeftUpCar, bForwardUpCar, bRightUpCar,
        bAroundUpTruck1,
        //        bAroundUpTruck2, bAroundUpTruck3, bAroundUpTruck4,
        bLeftUpTruck1,
        //        bLeftUpTruck2, bLeftUpTruck3, bLeftUpTruck4,
        bForwardUpTruck1,
        //        bForwardUpTruck2, bForwardUpTruck3, bForwardUpTruck4,
        bRightUpTruck1,
        //        bRightUpTruck2, bRightUpTruck3, bRightUpTruck4,

        bAroundUpBus1,
        //        bAroundUpBus2, bAroundUpBus3, bAroundUpBus4,
        bLeftUpBus1,
        //        bLeftUpBus2, bLeftUpBus3, bLeftUpBus4,
        bForwardUpBus1,
        //        bForwardUpBus2, bForwardUpBus3, bForwardUpBus4,
        bRightUpBus1
    //        bRightUpBus2, bRightUpBus3, bRightUpBus4

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
        bAroundLeftTruck1,
        //        bAroundLeftTruck2, bAroundLeftTruck3, bAroundLeftTruck4,
        bLeftLeftTruck1,
        //        bLeftLeftTruck2, bLeftLeftTruck3, bLeftLeftTruck4,
        bForwardLeftTruck1,
        //        bForwardLeftTruck2, bForwardLeftTruck3, bForwardLeftTruck4,
        bRightLeftTruck1,
        //        bRightLeftTruck2, bRightLeftTruck3, bRightLeftTruck4,

        bAroundLeftBus1,
        //        bAroundLeftBus2, bAroundLeftBus3, bAroundLeftBus4,
        bLeftLeftBus1,
        //        bLeftLeftBus2, bLeftLeftBus3, bLeftLeftBus4,
        bForwardLeftBus1,
        //        bForwardLeftBus2, bForwardLeftBus3, bForwardLeftBus4,
        bRightLeftBus1
//        bRightLeftBus2, bRightLeftBus3, bRightLeftBus4

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
        bAroundDownTruck1,
        //        bAroundDownTruck2, bAroundDownTruck3, bAroundDownTruck4,
        bLeftDownTruck1,
        //        bLeftDownTruck2, bLeftDownTruck3, bLeftDownTruck4,
        bForwardDownTruck1,
        //        bForwardDownTruck2, bForwardDownTruck3, bForwardDownTruck4,
        bRightDownTruck1,
        //        bRightDownTruck2, bRightDownTruck3, bRightDownTruck4,

        bAroundDownBus1,
        //        bAroundDownBus2, bAroundDownBus3, bAroundDownBus4,
        bLeftDownBus1,
        //        bLeftDownBus2, bLeftDownBus3, bLeftDownBus4,
        bForwardDownBus1,
        //        bForwardDownBus2, bForwardDownBus3, bForwardDownBus4,
        bRightDownBus1
//        bRightDownBus2, bRightDownBus3, bRightDownBus4

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
        bAroundRightTruck1,
        //        bAroundRightTruck2, bAroundRightTruck3, bAroundRightTruck4,
        bLeftRightTruck1,
        //        bLeftRightTruck2, bLeftRightTruck3, bLeftRightTruck4,
        bForwardRightTruck1,
        //        bForwardRightTruck2, bForwardRightTruck3, bForwardRightTruck4,
        bRightRightTruck1,
        //        bRightRightTruck2, bRightRightTruck3, bRightRightTruck4,

        bAroundRightBus1,
        //        bAroundRightBus2, bAroundRightBus3, bAroundRightBus4,
        bLeftRightBus1,
        //        bLeftRightBus2, bLeftRightBus3, bLeftRightBus4,
        bForwardRightBus1,
        //        bForwardRightBus2, bForwardRightBus3, bForwardRightBus4,
        bRightRightBus1
//        bRightRightBus2, bRightRightBus3, bRightRightBus4

//        createButtonWithIcon(bManRight, manIcon),
//        createButtonWithIcon(bBikeRight, bikeIcon)
    };

    private JComboBox comboBoxLeftUpTruck1 = new JComboBox(new String[]{"bLeftUpTruck1", "bLeftUpTruck2", "bLeftUpTruck3", "bLeftUpTruck4"});

    private JPanel panelUp = new JPanel(new GridLayout(4, 4, 0, 0)) {
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

    public Overlay(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        setBackground(new Color(0, 0, 0, 0)); // установка прозрачности overlay панели
        setLayout(null); // LayoutManager = null

        // Применение ко всем кнопкам (которые хранятся в массиве) свойств ("привращение" кнопки в иконку)
        createButtonsWithIcon(buttonsUp);
        createButtonsWithIcon(buttonsLeft);
        createButtonsWithIcon(buttonsDown);
        createButtonsWithIcon(buttonsRight);

        // Конфигурация панелей и добавление на панели требуемых элементов
        addJLabelOnButtonsPanel(panelUp, labelsUp); // добавление Лэйблов с указанием направления движения
        createPanelOfButton(panelUp, buttonsUp); // конфигурация панели и добавление на панель требуемых кнопок (панель 4х4)
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

        add(panelUp);
        add(panelLeft);
        add(panelDown);
        add(panelRight);

        // Опробование кнопки с возможностью выбора вариантов
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        comboBoxLeftUpTruck1.setRenderer(renderer);
        comboBoxLeftUpTruck1.setBounds(300, 300, 90, 40);
        comboBoxLeftUpTruck1.setLayout(null);
        comboBoxLeftUpTruck1.setBackground(Color.lightGray);
//      add(comboBoxLeftUpTruck1);
    }

    private JPanel createPanelOfButton(JPanel panel, JButton[] buttons) {
        panel.setBounds(0, 0, leftIcon.getIconWidth() * 4 + 15, leftIcon.getIconHeight() * 4 + 15); // размер панели с кнопками
        panel.setOpaque(false); // для правильной установки прозрачности кнопок
        panel.setBackground(new Color(.0f, .0f, .0f, .1f)); // установка цвета фона и прозрачности панели с кнопками
        panel.setFocusable(false); // панель не получает фокуса никогда
//        panel.setBorder(new LineBorder(Color.lightGray)); // граница панели

        for (JButton button : buttons) {
            panel.add(button);
            button.setFocusable(false); // кнопка не получает фокуса никогда
        }

        return panel;
    }

    private void addJLabelOnButtonsPanel(JPanel panel, JLabel[] labels) {
        for (JLabel label : labels) {
            panel.add(label);
            label.setFocusable(false); // лэйбл не получает фокуса никогда
        }
    }

    // Для абстрактной кнопки устанавливается размер и убирается отображение стандартных качеств кнопки
    // Если указать иконку, то размер кнопки будет установлен как размер иконки
    private JButton createButtonWithIcon(JButton button, ImageIcon icon) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
//        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
        button.setSize(new Dimension(icon.getIconWidth() + 2, icon.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setBackground(new Color(0, 0, 0, 0));
        return button;
    }

    // Для абстрактной кнопки устанавливается размер и убирается отображение стандартных качеств кнопки
    private JButton createButtonWithIcon(JButton button) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
//        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
        button.setSize(new Dimension(leftIcon.getIconWidth() + 2, leftIcon.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setBackground(new Color(0, 0, 0, 0));
        return button;
    }

    private void createButtonsWithIcon(JButton[] buttons) {
        for (JButton button : buttons) {
            createButtonWithIcon(button);
        }
    }

    // Установка месторасположения кнопки в массиве кнопок (массив для каждого направления свой)
    private JButton[] setButtonLocation(JButton[] buttons, int location) {
        for (int i = 0; i < buttons.length; i++) { // Каждой кнопке из массива кнопок

            if (location == 0) {
                buttons[i].setLocation((leftIcon.getIconWidth() + leftIcon.getIconHeight() / 2) * i + 1, 2 * leftIcon.getIconHeight() * location + 1);
            }
            if (location == 1) {
                buttons[i].setLocation((leftIcon.getIconWidth() + leftIcon.getIconHeight() / 2) * i + 1, 2 * leftIcon.getIconHeight() * location + 1);
            }
            if (location == 2) {
                buttons[i].setLocation((leftIcon.getIconWidth() + leftIcon.getIconHeight() / 2) * i + 1, 2 * leftIcon.getIconHeight() * location + 1);
            }
            if (location == 3) {
                buttons[i].setLocation((leftIcon.getIconWidth() + leftIcon.getIconHeight() / 2) * i + 1, 2 * leftIcon.getIconHeight() * location + 1);
            }

        }
        return buttons;
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
        return comboBoxLeftUpTruck1;
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

    class ComboBoxRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value.equals("bLeftUpTruck1")) {
                return createButtonWithIcon(bLeftUpTruck1, IconTruck1);
            }
            if (value.equals("bLeftUpTruck2")) {
                return createButtonWithIcon(bLeftUpTruck2, IconTruck2);
            }
            if (value.equals("bLeftUpTruck3")) {
                return createButtonWithIcon(bLeftUpTruck3, IconTruck3);
            }
            if (value.equals("bLeftUpTruck4")) {
                return createButtonWithIcon(bLeftUpTruck4, IconTruck4);
            } else {
                JLabel lbl = new JLabel(value.toString());
                lbl.setOpaque(true);
                return lbl;
            }

        }
    }
}
