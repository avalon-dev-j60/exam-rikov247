/**
 * Рабочая область с кнопками поверх видео
 */
package ru.overlay;

import com.sun.jna.platform.WindowUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import org.quinto.swing.table.view.JBroTable;
import ru.trafficClicker.OnButtonClick;
import ru.trafficClicker.imageBackground.SimpleBackground;

public class Overlay extends JWindow {

    private JBroTable table;
    private TreePath[] paths; // массив путей к узлам (видам транспорта), которые были выбраны для подсчета
    private int row; // количество строк на панели кнопок (количество видов транспорта для подсчета, не считая labels направлений)

    private SimpleBackground panelUp;
    private SimpleBackground panelLeft;
    private SimpleBackground panelDown;
    private SimpleBackground panelRight;

    private String background1 = Overlay.class.getResource("/resources/icons/numbers/11.png").getPath();
    private String background4 = Overlay.class.getResource("/resources/icons/numbers/44.png").getPath();
    private String background3 = Overlay.class.getResource("/resources/icons/numbers/33.png").getPath();
    private String background2 = Overlay.class.getResource("/resources/icons/numbers/22.png").getPath();
    // ИКОНКИ
    // Машина
    private ImageIcon iconCar = new ImageIcon(this.getClass().getResource("/resources/icons/car/car.png"));
    // Грузовик
    private ImageIcon iconTruck = new ImageIcon(this.getClass().getResource("/resources/icons/car/truck1.png"));
    // Автобус
    private ImageIcon iconBus = new ImageIcon(this.getClass().getResource("/resources/icons/car/bus1.png"));
    // Троллейбус
    private ImageIcon iconTrolleybus = new ImageIcon(this.getClass().getResource("/resources/icons/car/trolleybus.png"));
    // Трамвай
    private ImageIcon iconTram = new ImageIcon(this.getClass().getResource("/resources/icons/car/tram.png"));
    // Автопоезд
    private ImageIcon iconTrainBus = new ImageIcon(this.getClass().getResource("/resources/icons/car/trainBus.png"));

    // Иконки направления движения
    private ImageIcon aroundIcon = new ImageIcon(this.getClass().getResource("/resources/icons/roads/routing_around.png"));
    private ImageIcon leftIcon = new ImageIcon(this.getClass().getResource("/resources/icons/roads/routing_turn_left_90.png"));
    private ImageIcon forwardIcon = new ImageIcon(this.getClass().getResource("/resources/icons/roads/routing_forward.png"));
    private ImageIcon rightIcon = new ImageIcon(this.getClass().getResource("/resources/icons/roads/routing_turn_right_90.png"));

    // Иконки пешехода и велосипедиста
    private ImageIcon manIcon = new ImageIcon(this.getClass().getResource("/resources/icons/roads/walk.png"));
    private ImageIcon bikeIcon = new ImageIcon(this.getClass().getResource("/resources/icons/roads/bicycle.png"));

    // ИСПОЛЬЗУЕМЫЕ КОМПОНЕНТЫ
    // ВЕРХНЯЯ панель
    private JLabel aroundUpLabel = new JLabel(aroundIcon);
    private JLabel leftUpLabel = new JLabel(leftIcon);
    private JLabel forwardUpLabel = new JLabel(forwardIcon);
    private JLabel rightUpLabel = new JLabel(rightIcon);

    private JButton bAroundUpCar = new JButton(iconCar);
    private JButton bAroundUpTruck = new JButton(iconTruck);
    private JButton bAroundUpBus = new JButton(iconBus);
    private JButton bAroundUpTrainBus = new JButton(iconTrainBus);
    private JButton bAroundUpTrolleyBus = new JButton(iconTrolleybus);
    private JButton bAroundUpTram = new JButton(iconTram);

    private JButton bLeftUpCar = new JButton(iconCar);
    private JButton bLeftUpTruck = new JButton(iconTruck);
    private JButton bLeftUpBus = new JButton(iconBus);
    private JButton bLeftUpTrainBus = new JButton(iconTrainBus);
    private JButton bLeftUpTrolleyBus = new JButton(iconTrolleybus);
    private JButton bLeftUpTram = new JButton(iconTram);

    private JButton bForwardUpCar = new JButton(iconCar);
    private JButton bForwardUpTruck = new JButton(iconTruck);
    private JButton bForwardUpBus = new JButton(iconBus);
    private JButton bForwardUpTrainBus = new JButton(iconTrainBus);
    private JButton bForwardUpTrolleyBus = new JButton(iconTrolleybus);
    private JButton bForwardUpTram = new JButton(iconTram);

    private JButton bRightUpCar = new JButton(iconCar);
    private JButton bRightUpTruck = new JButton(iconTruck);
    private JButton bRightUpBus = new JButton(iconBus);
    private JButton bRightUpTrainBus = new JButton(iconTrainBus);
    private JButton bRightUpTrolleyBus = new JButton(iconTrolleybus);
    private JButton bRightUpTram = new JButton(iconTram);

    private JButton bManUp = new JButton(manIcon);
    private JButton bBikeUp = new JButton(bikeIcon);

    // Для перемещения панели с кнопками
    private JLabel[] labelsUp = {
        aroundUpLabel, leftUpLabel, forwardUpLabel, rightUpLabel
    };

    // Массив лекговых машин
    private JButton[] bUpCar = {
        bAroundUpCar, bLeftUpCar, bForwardUpCar, bRightUpCar
    };

    // Массив грузовиков
    private JButton[] bUpTruck = {
        bAroundUpTruck, bLeftUpTruck, bForwardUpTruck, bRightUpTruck
    };

    // Массив автобусов
    private JButton[] bUpBus = {
        bAroundUpBus, bLeftUpBus, bForwardUpBus, bRightUpBus
    };

    // Массив автопоездов
    private JButton[] bUpTrainBus = {
        bAroundUpTrainBus, bLeftUpTrainBus, bForwardUpTrainBus, bRightUpTrainBus
    };

    // Массив троллейбусов
    private JButton[] bUpTrolleybus = {
        bAroundUpTrolleyBus, bLeftUpTrolleyBus, bForwardUpTrolleyBus, bRightUpTrolleyBus
    };

    // Массив троллейбусов
    private JButton[] bUpTram = {
        bAroundUpTram, bLeftUpTram, bForwardUpTram, bRightUpTram
    };

    // Исходно заполняем панель этими компонентами
    private JComponent[] componentsUp;

    // Все кнопки, используемые в панели и превращаемые из обычной кнопки в кнопку-иконку
    private JButton[] buttonsUp = {
        bAroundUpCar, bLeftUpCar, bForwardUpCar, bRightUpCar,
        bAroundUpTruck, bLeftUpTruck, bForwardUpTruck, bRightUpTruck,
        bAroundUpBus, bLeftUpBus, bForwardUpBus, bRightUpBus,
        bAroundUpTrainBus, bLeftUpTrainBus, bForwardUpTrainBus, bRightUpTrainBus,
        bAroundUpTrolleyBus, bLeftUpTrolleyBus, bForwardUpTrolleyBus, bRightUpTrolleyBus,
        bAroundUpTram, bLeftUpTram, bForwardUpTram, bRightUpTram,
        bManUp, bBikeUp
    };

    // ЛЕВАЯ панель
    private JLabel aroundLeftLabel = new JLabel(aroundIcon);
    private JLabel leftLeftLabel = new JLabel(leftIcon);
    private JLabel forwardLeftLabel = new JLabel(forwardIcon);
    private JLabel rightLeftLabel = new JLabel(rightIcon);

    private JButton bAroundLeftCar = new JButton(iconCar);
    private JButton bAroundLeftTruck = new JButton(iconTruck);
    private JButton bAroundLeftBus = new JButton(iconBus);
    private JButton bAroundLeftTrainBus = new JButton(iconTrainBus);
    private JButton bAroundLeftTrolleyBus = new JButton(iconTrolleybus);
    private JButton bAroundLeftTram = new JButton(iconTram);

    private JButton bLeftLeftCar = new JButton(iconCar);
    private JButton bLeftLeftTruck = new JButton(iconTruck);
    private JButton bLeftLeftBus = new JButton(iconBus);
    private JButton bLeftLeftTrainBus = new JButton(iconTrainBus);
    private JButton bLeftLeftTrolleyBus = new JButton(iconTrolleybus);
    private JButton bLeftLeftTram = new JButton(iconTram);

    private JButton bForwardLeftCar = new JButton(iconCar);
    private JButton bForwardLeftTruck = new JButton(iconTruck);
    private JButton bForwardLeftBus = new JButton(iconBus);
    private JButton bForwardLeftTrainBus = new JButton(iconTrainBus);
    private JButton bForwardLeftTrolleyBus = new JButton(iconTrolleybus);
    private JButton bForwardLeftTram = new JButton(iconTram);

    private JButton bRightLeftCar = new JButton(iconCar);
    private JButton bRightLeftTruck = new JButton(iconTruck);
    private JButton bRightLeftBus = new JButton(iconBus);
    private JButton bRightLeftTrainBus = new JButton(iconTrainBus);
    private JButton bRightLeftTrolleyBus = new JButton(iconTrolleybus);
    private JButton bRightLeftTram = new JButton(iconTram);

    private JButton bManLeft = new JButton(manIcon);
    private JButton bBikeLeft = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsLeft;

    private JLabel[] labelsLeft = {
        aroundLeftLabel, leftLeftLabel, forwardLeftLabel, rightLeftLabel
    };

    // Массив лекговых машин
    private JButton[] bLeftCar = {
        bAroundLeftCar, bLeftLeftCar, bForwardLeftCar, bRightLeftCar
    };

    // Массив грузовиков
    private JButton[] bLeftTruck = {
        bAroundLeftTruck, bLeftLeftTruck, bForwardLeftTruck, bRightLeftTruck
    };

    // Массив автобусов
    private JButton[] bLeftBus = {
        bAroundLeftBus, bLeftLeftBus, bForwardLeftBus, bRightLeftBus
    };

    // Массив автопоездов
    private JButton[] bLeftTrainBus = {
        bAroundLeftTrainBus, bLeftLeftTrainBus, bForwardLeftTrainBus, bRightLeftTrainBus
    };

    // Массив троллейбусов
    private JButton[] bLeftTrolleybus = {
        bAroundLeftTrolleyBus, bLeftLeftTrolleyBus, bForwardLeftTrolleyBus, bRightLeftTrolleyBus
    };

    // Массив троллейбусов
    private JButton[] bLeftTram = {
        bAroundLeftTram, bLeftLeftTram, bForwardLeftTram, bRightLeftTram
    };

    private JButton[] buttonsLeft = {
        bAroundLeftCar, bLeftLeftCar, bForwardLeftCar, bRightLeftCar,
        bAroundLeftTruck, bLeftLeftTruck, bForwardLeftTruck, bRightLeftTruck,
        bAroundLeftBus, bLeftLeftBus, bForwardLeftBus, bRightLeftBus,
        bAroundLeftTrainBus, bLeftLeftTrainBus, bForwardLeftTrainBus, bRightLeftTrainBus,
        bAroundLeftTrolleyBus, bLeftLeftTrolleyBus, bForwardLeftTrolleyBus, bRightLeftTrolleyBus,
        bAroundLeftTram, bLeftLeftTram, bForwardLeftTram, bRightLeftTram,
        bManLeft, bBikeLeft
    };

    //НИЖНЯЯ панель
    private JLabel aroundDownLabel = new JLabel(aroundIcon);
    private JLabel leftDownLabel = new JLabel(leftIcon);
    private JLabel forwardDownLabel = new JLabel(forwardIcon);
    private JLabel rightDownLabel = new JLabel(rightIcon);

    private JButton bAroundDownCar = new JButton(iconCar);
    private JButton bAroundDownTruck = new JButton(iconTruck);
    private JButton bAroundDownBus = new JButton(iconBus);
    private JButton bAroundDownTrainBus = new JButton(iconTrainBus);
    private JButton bAroundDownTrolleyBus = new JButton(iconTrolleybus);
    private JButton bAroundDownTram = new JButton(iconTram);

    private JButton bLeftDownCar = new JButton(iconCar);
    private JButton bLeftDownTruck = new JButton(iconTruck);
    private JButton bLeftDownBus = new JButton(iconBus);
    private JButton bLeftDownTrainBus = new JButton(iconTrainBus);
    private JButton bLeftDownTrolleyBus = new JButton(iconTrolleybus);
    private JButton bLeftDownTram = new JButton(iconTram);

    private JButton bForwardDownCar = new JButton(iconCar);
    private JButton bForwardDownTruck = new JButton(iconTruck);
    private JButton bForwardDownBus = new JButton(iconBus);
    private JButton bForwardDownTrainBus = new JButton(iconTrainBus);
    private JButton bForwardDownTrolleyBus = new JButton(iconTrolleybus);
    private JButton bForwardDownTram = new JButton(iconTram);

    private JButton bRightDownCar = new JButton(iconCar);
    private JButton bRightDownTruck = new JButton(iconTruck);
    private JButton bRightDownBus = new JButton(iconBus);
    private JButton bRightDownTrainBus = new JButton(iconTrainBus);
    private JButton bRightDownTrolleyBus = new JButton(iconTrolleybus);
    private JButton bRightDownTram = new JButton(iconTram);

    private JButton bManDown = new JButton(manIcon);
    private JButton bBikeDown = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsDown;

    private JLabel[] labelsDown = {
        aroundDownLabel, leftDownLabel, forwardDownLabel, rightDownLabel
    };

    // Массив лекговых машин
    private JButton[] bDownCar = {
        bAroundDownCar, bLeftDownCar, bForwardDownCar, bRightDownCar
    };

    // Массив грузовиков
    private JButton[] bDownTruck = {
        bAroundDownTruck, bLeftDownTruck, bForwardDownTruck, bRightDownTruck
    };

    // Массив автобусов
    private JButton[] bDownBus = {
        bAroundDownBus, bLeftDownBus, bForwardDownBus, bRightDownBus
    };

    // Массив автопоездов
    private JButton[] bDownTrainBus = {
        bAroundDownTrainBus, bLeftDownTrainBus, bForwardDownTrainBus, bRightDownTrainBus
    };

    // Массив троллейбусов
    private JButton[] bDownTrolleybus = {
        bAroundDownTrolleyBus, bLeftDownTrolleyBus, bForwardDownTrolleyBus, bRightDownTrolleyBus
    };

    // Массив троллейбусов
    private JButton[] bDownTram = {
        bAroundDownTram, bLeftDownTram, bForwardDownTram, bRightDownTram
    };

    private JButton[] buttonsDown = {
        bAroundDownCar, bLeftDownCar, bForwardDownCar, bRightDownCar,
        bAroundDownTruck, bLeftDownTruck, bForwardDownTruck, bRightDownTruck,
        bAroundDownBus, bLeftDownBus, bForwardDownBus, bRightDownBus,
        bAroundDownTrainBus, bLeftDownTrainBus, bForwardDownTrainBus, bRightDownTrainBus,
        bAroundDownTrolleyBus, bLeftDownTrolleyBus, bForwardDownTrolleyBus, bRightDownTrolleyBus,
        bAroundDownTram, bLeftDownTram, bForwardDownTram, bRightDownTram,
        bManDown, bBikeDown
    };

    //ПРАВАЯ панель
    private JLabel aroundRightLabel = new JLabel(aroundIcon);
    private JLabel leftRightLabel = new JLabel(leftIcon);
    private JLabel forwardRightLabel = new JLabel(forwardIcon);
    private JLabel rightRightLabel = new JLabel(rightIcon);

    private JButton bAroundRightCar = new JButton(iconCar);
    private JButton bAroundRightTruck = new JButton(iconTruck);
    private JButton bAroundRightBus = new JButton(iconBus);
    private JButton bAroundRightTrainBus = new JButton(iconTrainBus);
    private JButton bAroundRightTrolleyBus = new JButton(iconTrolleybus);
    private JButton bAroundRightTram = new JButton(iconTram);

    private JButton bLeftRightCar = new JButton(iconCar);
    private JButton bLeftRightTruck = new JButton(iconTruck);
    private JButton bLeftRightBus = new JButton(iconBus);
    private JButton bLeftRightTrainBus = new JButton(iconTrainBus);
    private JButton bLeftRightTrolleyBus = new JButton(iconTrolleybus);
    private JButton bLeftRightTram = new JButton(iconTram);

    private JButton bForwardRightCar = new JButton(iconCar);
    private JButton bForwardRightTruck = new JButton(iconTruck);
    private JButton bForwardRightBus = new JButton(iconBus);
    private JButton bForwardRightTrainBus = new JButton(iconTrainBus);
    private JButton bForwardRightTrolleyBus = new JButton(iconTrolleybus);
    private JButton bForwardRightTram = new JButton(iconTram);

    private JButton bRightRightCar = new JButton(iconCar);
    private JButton bRightRightTruck = new JButton(iconTruck);
    private JButton bRightRightBus = new JButton(iconBus);
    private JButton bRightRightTrainBus = new JButton(iconTrainBus);
    private JButton bRightRightTrolleyBus = new JButton(iconTrolleybus);
    private JButton bRightRightTram = new JButton(iconTram);

    private JButton bManRight = new JButton(manIcon);
    private JButton bBikeRight = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsRight;

    private JLabel[] labelsRight = {
        aroundRightLabel, leftRightLabel, forwardRightLabel, rightRightLabel
    };

    // Массив лекговых машин
    private JButton[] bRightCar = {
        bAroundRightCar, bLeftRightCar, bForwardRightCar, bRightRightCar
    };

    // Массив грузовиков
    private JButton[] bRightTruck = {
        bAroundRightTruck, bLeftRightTruck, bForwardRightTruck, bRightRightTruck
    };

    // Массив автобусов
    private JButton[] bRightBus = {
        bAroundRightBus, bLeftRightBus, bForwardRightBus, bRightRightBus
    };

    // Массив автопоездов
    private JButton[] bRightTrainBus = {
        bAroundRightTrainBus, bLeftRightTrainBus, bForwardRightTrainBus, bRightRightTrainBus
    };

    // Массив троллейбусов
    private JButton[] bRightTrolleybus = {
        bAroundRightTrolleyBus, bLeftRightTrolleyBus, bForwardRightTrolleyBus, bRightRightTrolleyBus
    };

    // Массив троллейбусов
    private JButton[] bRightTram = {
        bAroundRightTram, bLeftRightTram, bForwardRightTram, bRightRightTram
    };

    private JButton[] buttonsRight = {
        bAroundRightCar, bLeftRightCar, bForwardRightCar, bRightRightCar,
        bAroundRightTruck, bLeftRightTruck, bForwardRightTruck, bRightRightTruck,
        bAroundRightBus, bLeftRightBus, bForwardRightBus, bRightRightBus,
        bAroundRightTrainBus, bLeftRightTrainBus, bForwardRightTrainBus, bRightRightTrainBus,
        bAroundRightTrolleyBus, bLeftRightTrolleyBus, bForwardRightTrolleyBus, bRightRightTrolleyBus,
        bAroundRightTram, bLeftRightTram, bForwardRightTram, bRightRightTram,
        bManRight, bBikeRight
    };

    // Основная overlay панель, на которую помещаются кнопки. LayoutManager = null
    private JPanel overlayPanel = new JPanel(null);

    public Overlay(Window owner, JBroTable table, String typeOfStatement, String typeOfDirection, TreePath[] paths) throws IOException {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        this.table = table;
        this.paths = paths;
        // установка прозрачности overlay панели
        setBackground(new Color(0, 0, 0, 0));
        overlayPanel.setOpaque(false); // прозрачность панели включена. На этой панели размещаем панели с кнопками
        // добавление панели на окно overlay (основная рабочая прозрачная панель)
        add(overlayPanel);
        // Применение ко всем кнопкам (которые хранятся в массиве) свойств ("привращение" кнопки в иконку)
        createButtonsWithIcon(buttonsUp);
        createButtonsWithIcon(buttonsLeft);
        createButtonsWithIcon(buttonsDown);
        createButtonsWithIcon(buttonsRight);

        // Добавление исходных компонентов 
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            // Если выбрано 4 направления, то:
            if (typeOfDirection.equalsIgnoreCase("4") || typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                int numOfDir = Integer.valueOf(typeOfDirection.substring(0, 1));
                // Конфигурируем 4 столбца и сколько нужно строк
                panelUp = new SimpleBackground(numOfDir);
                panelLeft = new SimpleBackground(numOfDir);
                panelDown = new SimpleBackground(numOfDir);
                panelRight = new SimpleBackground(numOfDir);

                // Устанавливаем картинку на фон панели с кнопками
                panelUp.setBackground(ImageIO.read(new File(background1)));
                panelLeft.setBackground(ImageIO.read(new File(background4)));
                panelDown.setBackground(ImageIO.read(new File(background3)));
                panelRight.setBackground(ImageIO.read(new File(background2)));

                // Конфигурируем и наполняем панели
                ChooseComponentsNow4 compUpNow4 = new ChooseComponentsNow4(paths, labelsUp, bUpCar, bUpBus, bUpTruck, bUpTrolleybus, bUpTram); // в зависимости от переданных узлов (видов транспорта, которые считаем) конфигурируем компонентную панель
                componentsUp = compUpNow4.chooseComponentsNow4();
                row = compUpNow4.getRow();
                createPanelOfButtons(panelUp, row, numOfDir, componentsUp); // заполнение панели нужными элементами из собранного контейнера (componentsUp)
                panelUp.setLocation(200, 0); // установка изначального местоположения панели

                ChooseComponentsNow4 compLeftNow4 = new ChooseComponentsNow4(paths, labelsLeft, bLeftCar, bLeftBus, bLeftTruck, bLeftTrolleybus, bLeftTram);
                componentsLeft = compLeftNow4.chooseComponentsNow4();
                createPanelOfButtons(panelLeft, row, numOfDir, componentsLeft); // заполнение панели нужными элементами
                panelLeft.setLocation(0, 100); // установка изначального местоположения панели

                ChooseComponentsNow4 compDownNow4 = new ChooseComponentsNow4(paths, labelsDown, bDownCar, bDownBus, bDownTruck, bDownTrolleybus, bDownTram);
                componentsDown = compDownNow4.chooseComponentsNow4();
                createPanelOfButtons(panelDown, row, numOfDir, componentsDown); // заполнение панели нужными элементами
                panelDown.setLocation(500, 400); // установка изначального местоположения панели

                ChooseComponentsNow4 compRightNow4 = new ChooseComponentsNow4(paths, labelsRight, bRightCar, bRightBus, bRightTruck, bRightTrolleybus, bRightTram);
                componentsRight = compRightNow4.chooseComponentsNow4();
                createPanelOfButtons(panelRight, row, numOfDir, componentsRight); // заполнение панели нужными элементами
                panelRight.setLocation(650, 0); // установка изначального местоположения панели

                overlayPanel.add(panelUp);
                overlayPanel.add(panelLeft);
                overlayPanel.add(panelDown);
                overlayPanel.add(panelRight);
            }

            // Если выбрано 3 направления вверх (пока что реализовано только оно, так что вызываем его в любом случае), то:
            if (typeOfDirection.equalsIgnoreCase("3 вверх") || typeOfDirection.equalsIgnoreCase("3 вправо")) {
                int numOfDir = Integer.valueOf(typeOfDirection.substring(0, 1));
                // Конфигурируем 3 столбца и сколько нужно строк
                panelLeft = new SimpleBackground(numOfDir);
                panelDown = new SimpleBackground(numOfDir);
                panelRight = new SimpleBackground(numOfDir);

                // Устанавливаем картинку на фон панели с кнопками
                panelLeft.setBackground(ImageIO.read(new File(background4)));
                panelDown.setBackground(ImageIO.read(new File(background3)));
                panelRight.setBackground(ImageIO.read(new File(background2)));

                // Конфигурируем и наполняем панели
                // Левая (4)
                ChooseComponentsNow3Up compLeftNow3 = new ChooseComponentsNow3Up("4", paths, labelsLeft, bLeftCar, bLeftBus, bLeftTruck, bLeftTrolleybus, bLeftTram);
                componentsLeft = compLeftNow3.chooseCompNow3withoutForward();
                row = compLeftNow3.getRow();
                createPanelOfButtons(panelLeft, row, numOfDir, componentsLeft); // заполнение панели нужными элементами
                panelLeft.setLocation(0, 100); // установка изначального местоположения панели
                // Нижняя (3)
                ChooseComponentsNow3Up compDownNow3 = new ChooseComponentsNow3Up("3", paths, labelsDown, bDownCar, bDownBus, bDownTruck, bDownTrolleybus, bDownTram);
                componentsDown = compDownNow3.chooseCompNow3withoutForward();
                createPanelOfButtons(panelDown, row, numOfDir, componentsDown); // заполнение панели нужными элементами
                panelDown.setLocation(500, 400); // установка изначального местоположения панели
                // Правая (2)
                ChooseComponentsNow3Up compRightNow3 = new ChooseComponentsNow3Up("2", paths, labelsRight, bRightCar, bRightBus, bRightTruck, bRightTrolleybus, bRightTram);
                componentsRight = compRightNow3.chooseCompNow3withoutForward();
                createPanelOfButtons(panelRight, row, numOfDir, componentsRight); // заполнение панели нужными элементами
                panelRight.setLocation(650, 0); // установка изначального местоположения панели

                overlayPanel.add(panelLeft);
                overlayPanel.add(panelDown);
                overlayPanel.add(panelRight);
            }

        }

        if (typeOfStatement.equalsIgnoreCase("Future")) {
            // Если выбрано 4 направления, то:
            if (typeOfDirection.equalsIgnoreCase("4") || typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                int numOfDir = Integer.valueOf(typeOfDirection.substring(0, 1));
                // Конфигурируем 4 столбца и сколько нужно строк
                panelUp = new SimpleBackground(numOfDir);
                panelLeft = new SimpleBackground(numOfDir);
                panelDown = new SimpleBackground(numOfDir);
                panelRight = new SimpleBackground(numOfDir);

                // Устанавливаем картинку на фон панели с кнопками
                panelUp.setBackground(ImageIO.read(new File(background1)));
                panelLeft.setBackground(ImageIO.read(new File(background4)));
                panelDown.setBackground(ImageIO.read(new File(background3)));
                panelRight.setBackground(ImageIO.read(new File(background2)));

                // Конфигурируем и наполняем панели
                ChooseComponentsFuture4 compUpFuture4 = new ChooseComponentsFuture4(paths, labelsUp, bUpCar, bUpBus, bUpTruck, bUpTrainBus, bUpTrolleybus, bUpTram); // в зависимости от переданных узлов (видов транспорта, которые считаем) конфигурируем компонентную панель
                componentsUp = compUpFuture4.chooseComponentsFuture4();
                row = compUpFuture4.getRow();
                createPanelOfButtons(panelUp, row, numOfDir, componentsUp); // заполнение панели нужными элементами из собранного контейнера (componentsUp)
                panelUp.setLocation(200, 0); // установка изначального местоположения панели

                ChooseComponentsFuture4 compLeftFuture4 = new ChooseComponentsFuture4(paths, labelsLeft, bLeftCar, bLeftBus, bLeftTruck, bLeftTrainBus, bLeftTrolleybus, bLeftTram);
                componentsLeft = compLeftFuture4.chooseComponentsFuture4();
                createPanelOfButtons(panelLeft, row, numOfDir, componentsLeft); // заполнение панели нужными элементами
                panelLeft.setLocation(0, 100); // установка изначального местоположения панели

                ChooseComponentsFuture4 compDownFuture4 = new ChooseComponentsFuture4(paths, labelsDown, bDownCar, bDownBus, bDownTruck, bDownTrainBus, bDownTrolleybus, bDownTram);
                componentsDown = compDownFuture4.chooseComponentsFuture4();
                createPanelOfButtons(panelDown, row, numOfDir, componentsDown); // заполнение панели нужными элементами
                panelDown.setLocation(500, 400); // установка изначального местоположения панели

                ChooseComponentsFuture4 compRightFuture4 = new ChooseComponentsFuture4(paths, labelsRight, bRightCar, bRightBus, bRightTruck, bRightTrainBus, bRightTrolleybus, bRightTram);
                componentsRight = compRightFuture4.chooseComponentsFuture4();
                createPanelOfButtons(panelRight, row, numOfDir, componentsRight); // заполнение панели нужными элементами
                panelRight.setLocation(650, 0); // установка изначального местоположения панели

                overlayPanel.add(panelUp);
                overlayPanel.add(panelLeft);
                overlayPanel.add(panelDown);
                overlayPanel.add(panelRight);
            }

            // Если выбрано 3 направления вверх (пока что реализовано только оно, так что вызываем его в любом случае), то:
            if (typeOfDirection.equalsIgnoreCase("3 вправо") || typeOfDirection.equalsIgnoreCase("3 вверх")) {
                int numOfDir = Integer.valueOf(typeOfDirection.substring(0, 1));
                // Конфигурируем 3 столбца и сколько нужно строк
                panelUp = new SimpleBackground(numOfDir);
                panelLeft = new SimpleBackground(numOfDir);
                panelDown = new SimpleBackground(numOfDir);

                // Устанавливаем картинку на фон панели с кнопками
                panelUp.setBackground(ImageIO.read(new File(background1)));
                panelLeft.setBackground(ImageIO.read(new File(background4)));
                panelDown.setBackground(ImageIO.read(new File(background3)));

                // Конфигурируем и наполняем панели
                ChooseComponentsFuture3Right compUpNow3 = new ChooseComponentsFuture3Right("1", paths, labelsUp, bUpCar, bUpBus, bUpTruck, bUpTrainBus, bUpTrolleybus, bUpTram); // в зависимости от переданных узлов (видов транспорта, которые считаем) конфигурируем компонентную панель
                componentsUp = compUpNow3.chooseComponentsFuture3();
                row = compUpNow3.getRow();
                createPanelOfButtons(panelUp, row, numOfDir, componentsUp); // заполнение панели нужными элементами из собранного контейнера (componentsUp)
                panelUp.setLocation(200, 0); // установка изначального местоположения панели

                ChooseComponentsFuture3Right compLeftNow3 = new ChooseComponentsFuture3Right("4", paths, labelsLeft, bLeftCar, bLeftBus, bLeftTruck, bLeftTrainBus, bLeftTrolleybus, bLeftTram);
                componentsLeft = compLeftNow3.chooseComponentsFuture3();
                createPanelOfButtons(panelLeft, row, numOfDir, componentsLeft); // заполнение панели нужными элементами
                panelLeft.setLocation(0, 100); // установка изначального местоположения панели

                ChooseComponentsFuture3Right compDownNow3 = new ChooseComponentsFuture3Right("3", paths, labelsDown, bDownCar, bDownBus, bDownTruck, bDownTrainBus, bDownTrolleybus, bDownTram);
                componentsDown = compDownNow3.chooseComponentsFuture3();
                createPanelOfButtons(panelDown, row, numOfDir, componentsDown); // заполнение панели нужными элементами
                panelDown.setLocation(500, 400); // установка изначального местоположения панели

                overlayPanel.add(panelUp);
                overlayPanel.add(panelLeft);
                overlayPanel.add(panelDown);
            }
        }

        // Слушатель кликов по кнопке и занесение информации в таблицу
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            // Создаем экземпляр popupMenu для Truck направления Up (1)
            bAroundUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundUpBus, table, typeOfStatement, "ФЕ Разворот 11", "Bus");
            new popupButtonToTable(bAroundUpTruck, table, typeOfStatement, "ФЕ Разворот 11", "Truck");
            bAroundUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bAroundUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 

            bLeftUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftUpTruck, table, typeOfStatement, "ФЕ Налево 12", "Truck");
            new popupButtonToTable(bLeftUpBus, table, typeOfStatement, "ФЕ Налево 12", "Bus");
            bLeftUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bLeftUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 

            bForwardUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardUpTruck, table, typeOfStatement, "ФЕ Прямо 1", "Truck");
            new popupButtonToTable(bForwardUpBus, table, typeOfStatement, "ФЕ Прямо 1", "Bus");
            bForwardUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bForwardUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 

            bRightUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightUpTruck, table, typeOfStatement, "ФЕ Направо 14", "Truck");
            new popupButtonToTable(bRightUpBus, table, typeOfStatement, "ФЕ Направо 14", "Bus");
            bRightUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bRightUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 

            // Создаем экземпляр popupMenu для Truck направления Left (4)
            bAroundLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 44")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundLeftBus, table, typeOfStatement, "ФЕ Разворот 44", "Bus");
            new popupButtonToTable(bAroundLeftTruck, table, typeOfStatement, "ФЕ Разворот 44", "Truck");
            bAroundLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 44")::onButtonClick);
            bAroundLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 44")::onButtonClick);

            bLeftLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 41")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftLeftTruck, table, typeOfStatement, "ФЕ Налево 41", "Truck");
            new popupButtonToTable(bLeftLeftBus, table, typeOfStatement, "ФЕ Налево 41", "Bus");
            bLeftLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 41")::onButtonClick);
            bLeftLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 41")::onButtonClick);

            bForwardLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 4")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardLeftTruck, table, typeOfStatement, "ФЕ Прямо 4", "Truck");
            new popupButtonToTable(bForwardLeftBus, table, typeOfStatement, "ФЕ Прямо 4", "Bus");
            bForwardLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 4")::onButtonClick);
            bForwardLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 4")::onButtonClick);

            bRightLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 43")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightLeftTruck, table, typeOfStatement, "ФЕ Направо 43", "Truck");
            new popupButtonToTable(bRightLeftBus, table, typeOfStatement, "ФЕ Направо 43", "Bus");
            bRightLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 43")::onButtonClick);
            bRightLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 43")::onButtonClick);

            // Инициализируем слушателей кнопок. Также создаем экземпляр popupMenu для Truck направления Down (3)
            bAroundDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 33")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundDownBus, table, typeOfStatement, "ФЕ Разворот 33", "Bus");
            new popupButtonToTable(bAroundDownTruck, table, typeOfStatement, "ФЕ Разворот 33", "Truck");
            bAroundDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 33")::onButtonClick);
            bAroundDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 33")::onButtonClick);

            bLeftDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 34")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftDownTruck, table, typeOfStatement, "ФЕ Налево 34", "Truck");
            new popupButtonToTable(bLeftDownBus, table, typeOfStatement, "ФЕ Налево 34", "Bus");
            bLeftDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 34")::onButtonClick);
            bLeftDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 34")::onButtonClick);

            bForwardDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 3")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardDownTruck, table, typeOfStatement, "ФЕ Прямо 3", "Truck");
            new popupButtonToTable(bForwardDownBus, table, typeOfStatement, "ФЕ Прямо 3", "Bus");
            bForwardDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 3")::onButtonClick);
            bForwardDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 3")::onButtonClick);

            bRightDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 32")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightDownTruck, table, typeOfStatement, "ФЕ Направо 32", "Truck");
            new popupButtonToTable(bRightDownBus, table, typeOfStatement, "ФЕ Направо 32", "Bus");
            bRightDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 32")::onButtonClick);
            bRightDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 32")::onButtonClick);

            // Создаем экземпляр popupMenu для Truck направления Right (2)
            bAroundRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 22")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundRightBus, table, typeOfStatement, "ФЕ Разворот 22", "Bus");
            new popupButtonToTable(bAroundRightTruck, table, typeOfStatement, "ФЕ Разворот 22", "Truck");
            bAroundRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 22")::onButtonClick);
            bAroundRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 22")::onButtonClick);

            bLeftRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 23")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftRightBus, table, typeOfStatement, "ФЕ Налево 23", "Bus");
            new popupButtonToTable(bLeftRightTruck, table, typeOfStatement, "ФЕ Налево 23", "Truck");
            bLeftRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 23")::onButtonClick);
            bLeftRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 23")::onButtonClick);

            bForwardRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 2")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardRightBus, table, typeOfStatement, "ФЕ Прямо 2", "Bus");
            new popupButtonToTable(bForwardRightTruck, table, typeOfStatement, "ФЕ Прямо 2", "Truck");
            bForwardRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 2")::onButtonClick);
            bForwardRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 2")::onButtonClick);

            bRightRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 21")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightRightBus, table, typeOfStatement, "ФЕ Направо 21", "Bus");
            new popupButtonToTable(bRightRightTruck, table, typeOfStatement, "ФЕ Направо 21", "Truck");
            bRightRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 21")::onButtonClick);
            bRightRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 21")::onButtonClick);
        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            // Создаем экземпляр popupMenu для Truck направления Up (1)
            bAroundUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundUpBus, table, typeOfStatement, "ФЕ Разворот 11", "Bus");
            new popupButtonToTable(bAroundUpTruck, table, typeOfStatement, "ФЕ Разворот 11", "Truck");
            new popupButtonToTable(bAroundUpTrainBus, table, typeOfStatement, "ФЕ Разворот 11", "TrainBus");
            bAroundUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 11")::onButtonClick);
            bAroundUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 11")::onButtonClick);

            bLeftUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftUpTruck, table, typeOfStatement, "ФЕ Налево 12", "Truck");
            new popupButtonToTable(bLeftUpBus, table, typeOfStatement, "ФЕ Налево 12", "Bus");
            new popupButtonToTable(bLeftUpTrainBus, table, typeOfStatement, "ФЕ Налево 12", "TrainBus");
            bLeftUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bLeftUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 12")::onButtonClick);

            bForwardUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardUpTruck, table, typeOfStatement, "ФЕ Прямо 1", "Truck");
            new popupButtonToTable(bForwardUpBus, table, typeOfStatement, "ФЕ Прямо 1", "Bus");
            new popupButtonToTable(bForwardUpTrainBus, table, typeOfStatement, "ФЕ Прямо 1", "TrainBus");
            bForwardUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bForwardUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 1")::onButtonClick);

            bRightUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightUpTruck, table, typeOfStatement, "ФЕ Направо 14", "Truck");
            new popupButtonToTable(bRightUpBus, table, typeOfStatement, "ФЕ Направо 14", "Bus");
            new popupButtonToTable(bRightUpTrainBus, table, typeOfStatement, "ФЕ Направо 14", "TrainBus");
            bRightUpTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bRightUpTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 14")::onButtonClick);

            // Создаем экземпляр popupMenu для Truck направления Left (4)
            bAroundLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 44")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundLeftBus, table, typeOfStatement, "ФЕ Разворот 44", "Bus");
            new popupButtonToTable(bAroundLeftTruck, table, typeOfStatement, "ФЕ Разворот 44", "Truck");
            new popupButtonToTable(bAroundLeftTrainBus, table, typeOfStatement, "ФЕ Разворот 44", "TrainBus");
            bAroundLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 44")::onButtonClick);
            bAroundLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 44")::onButtonClick);

            bLeftLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 41")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftLeftTruck, table, typeOfStatement, "ФЕ Налево 41", "Truck");
            new popupButtonToTable(bLeftLeftBus, table, typeOfStatement, "ФЕ Налево 41", "Bus");
            new popupButtonToTable(bLeftLeftTrainBus, table, typeOfStatement, "ФЕ Налево 41", "TrainBus");
            bLeftLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 41")::onButtonClick);
            bLeftLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 41")::onButtonClick);

            bForwardLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 4")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardLeftTruck, table, typeOfStatement, "ФЕ Прямо 4", "Truck");
            new popupButtonToTable(bForwardLeftBus, table, typeOfStatement, "ФЕ Прямо 4", "Bus");
            new popupButtonToTable(bForwardLeftTrainBus, table, typeOfStatement, "ФЕ Прямо 4", "TrainBus");
            bForwardLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 4")::onButtonClick);
            bForwardLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 4")::onButtonClick);

            bRightLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 43")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightLeftTruck, table, typeOfStatement, "ФЕ Направо 43", "Truck");
            new popupButtonToTable(bRightLeftBus, table, typeOfStatement, "ФЕ Направо 43", "Bus");
            new popupButtonToTable(bRightLeftTrainBus, table, typeOfStatement, "ФЕ Направо 43", "TrainBus");
            bRightLeftTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 43")::onButtonClick);
            bRightLeftTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 43")::onButtonClick);

            // Инициализируем слушателей кнопок. Также создаем экземпляр popupMenu для Truck направления Down (3)
            bAroundDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 33")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundDownBus, table, typeOfStatement, "ФЕ Разворот 33", "Bus");
            new popupButtonToTable(bAroundDownTruck, table, typeOfStatement, "ФЕ Разворот 33", "Truck");
            new popupButtonToTable(bAroundDownTrainBus, table, typeOfStatement, "ФЕ Разворот 33", "TrainBus");
            bAroundDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 33")::onButtonClick);
            bAroundDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 33")::onButtonClick);

            bLeftDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 34")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftDownTruck, table, typeOfStatement, "ФЕ Налево 34", "Truck");
            new popupButtonToTable(bLeftDownBus, table, typeOfStatement, "ФЕ Налево 34", "Bus");
            new popupButtonToTable(bLeftDownTrainBus, table, typeOfStatement, "ФЕ Налево 34", "TrainBus");
            bLeftDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 34")::onButtonClick);
            bLeftDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 34")::onButtonClick);

            bForwardDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 3")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardDownTruck, table, typeOfStatement, "ФЕ Прямо 3", "Truck");
            new popupButtonToTable(bForwardDownBus, table, typeOfStatement, "ФЕ Прямо 3", "Bus");
            new popupButtonToTable(bForwardDownTrainBus, table, typeOfStatement, "ФЕ Прямо 3", "TrainBus");
            bForwardDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 3")::onButtonClick);
            bForwardDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 3")::onButtonClick);

            bRightDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 32")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightDownTruck, table, typeOfStatement, "ФЕ Направо 32", "Truck");
            new popupButtonToTable(bRightDownBus, table, typeOfStatement, "ФЕ Направо 32", "Bus");
            new popupButtonToTable(bRightDownTrainBus, table, typeOfStatement, "ФЕ Направо 32", "TrainBus");
            bRightDownTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 32")::onButtonClick);
            bRightDownTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 32")::onButtonClick);

            // Создаем экземпляр popupMenu для Truck направления Right (2)
            bAroundRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 22")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundRightBus, table, typeOfStatement, "ФЕ Разворот 22", "Bus");
            new popupButtonToTable(bAroundRightTruck, table, typeOfStatement, "ФЕ Разворот 22", "Truck");
            new popupButtonToTable(bAroundRightTrainBus, table, typeOfStatement, "ФЕ Разворот 22", "TrainBus");
            bAroundRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Разворот 22")::onButtonClick);
            bAroundRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Разворот 22")::onButtonClick);

            bLeftRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 23")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftRightBus, table, typeOfStatement, "ФЕ Налево 23", "Bus");
            new popupButtonToTable(bLeftRightTruck, table, typeOfStatement, "ФЕ Налево 23", "Truck");
            new popupButtonToTable(bLeftRightTrainBus, table, typeOfStatement, "ФЕ Налево 23", "TrainBus");
            bLeftRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Налево 23")::onButtonClick);
            bLeftRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Налево 23")::onButtonClick);

            bForwardRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 2")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardRightBus, table, typeOfStatement, "ФЕ Прямо 2", "Bus");
            new popupButtonToTable(bForwardRightTruck, table, typeOfStatement, "ФЕ Прямо 2", "Truck");
            new popupButtonToTable(bForwardRightTrainBus, table, typeOfStatement, "ФЕ Прямо 2", "TrainBus");
            bForwardRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Прямо 2")::onButtonClick);
            bForwardRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Прямо 2")::onButtonClick);

            bRightRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 21")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightRightBus, table, typeOfStatement, "ФЕ Направо 21", "Bus");
            new popupButtonToTable(bRightRightTruck, table, typeOfStatement, "ФЕ Направо 21", "Truck");
            new popupButtonToTable(bRightRightTrainBus, table, typeOfStatement, "ФЕ Направо 21", "TrainBus");
            bRightRightTrolleyBus.addActionListener(new OnButtonClick(table, "Троллейбусы", "ФЕ Направо 21")::onButtonClick);
            bRightRightTram.addActionListener(new OnButtonClick(table, "Трамвай", "ФЕ Направо 21")::onButtonClick);
        }
    }

    private JPanel createPanelOfButtons(JPanel panel, int row, int column, JComponent[] injectedComponents) {
        Component[][] containerComponents = new Component[row][column]; // инициализируем двумерный массив требуемого размера
        // Заполняем панель компонентами, какими нужно
        mainLoop:
        for (int i = 0; i < containerComponents.length; i++) { // проходим по строкам
            // проходим по столбцам (берем первую строку и смотрим сколько в ней элементов)
            for (int j = 0; j < containerComponents[0].length; j++) {
                // если если дошли до элемента, который является последним в контейнере из которого берем кнопки, то заканчиваем делать вообще все
                containerComponents[i][j] = new JLabel(""); // создаем пустой элемент (чтобы избежать nullException)
                if (j + (containerComponents[0].length) * i == injectedComponents.length) {
                    break mainLoop;
                }
                containerComponents[i][j] = injectedComponents[j + (containerComponents[0].length) * i]; // в контейнер кладем элемент
            }
        }
        // Добавляем компоненты на панель
        for (int i = 0; i < containerComponents.length; i++) {
            for (int j = 0; j < containerComponents[0].length; j++) {
                panel.add(containerComponents[i][j]);
            }
        }
        ////////////////////////////////////////////////////////////////////////
        // Устанавливаем размеры панели с кнопками, исходя из компонентов в них находящихся
        int panelWidth = containerComponents.length > 1 ? ((JButton) containerComponents[1][1]).getIcon().getIconWidth() : ((JLabel) containerComponents[0][0]).getIcon().getIconWidth();
        int panelHeight = ((JLabel) containerComponents[0][0]).getIcon().getIconHeight() + (int) (((JLabel) containerComponents[0][0]).getIcon().getIconHeight() / 4);
        for (int q = 1; q < containerComponents.length; q++) {
            for (int a = 0; a < containerComponents[0].length - 1; a++) {
                if (((JButton) containerComponents[q][a]).getIcon().getIconWidth() > ((JButton) containerComponents[q][a + 1]).getIcon().getIconWidth()) {
                    panelWidth = ((JButton) containerComponents[q][a]).getIcon().getIconWidth();
                }
                if (a == 0) {
                    panelHeight += ((JButton) containerComponents[q][a]).getIcon().getIconHeight();
                }
            }
        }
        // Настройка панели
        panel.setOpaque(false); // для правильной установки прозрачности кнопок
        panel.setBackground(new Color(.0f, .0f, .0f, .0f)); // установка цвета фона и прозрачности панели с кнопками (чтобы можно было тыкать в видео между кнопок нужно сделать прозрачность нулевой!
        panel.setFocusable(false); // панель не получает фокуса никогда

        panel.setSize(new Dimension(panelWidth * containerComponents[0].length + panelWidth / 4, panelHeight)); // размер панели с кнопками
        ///////////////////////////////////////////////////////////////////////
        return panel; // возвращаем панель с добавленными элементами
    }

    // Этот метод используется после обновления компонентов на панели. Он удаляет элементы и добавляет
    public JPanel addOnPanelOfButtons(JPanel panel, Component[][] containerComponents) {
        panel.removeAll(); // удаляем все элементы с панели
        // Исходно заполняем массив пустыми элементами (чтобы не было null в массиве) и добавляем их в панель
        for (int i = 0; i < containerComponents.length; i++) {
            for (int j = 0; j < containerComponents[0].length; j++) {
                panel.add(containerComponents[i][j]);
            }
        }
        return panel; // возвращаем панель с добавленными элементами
    }

    // Для абстрактной кнопки устанавливается размер и убирается отображение стандартных качеств кнопки
    private JButton createButtonWithIcon(JButton button) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии

        button.setBackground(new Color(130, 130, 130, 130)); // устанавливаем цвет фона кнопки

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    button.setContentAreaFilled(true); // включение закраски кнопки в нажатом состоянии
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
                }
            }

            // Оба метода: для обновления цвета и прозрачности (отрисовки в общем) фона кнопки!
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                overlayPanel.repaint();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                overlayPanel.repaint();
                repaint();
            }
        });

        button.setPreferredSize(new Dimension(button.getIcon().getIconWidth() + 2, button.getIcon().getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setMinimumSize(new Dimension(button.getIcon().getIconWidth() + 2, button.getIcon().getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setMaximumSize(new Dimension(button.getIcon().getIconWidth() + 2, button.getIcon().getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setOpaque(false);
        button.setFocusable(false); // отключаем перевод фокуса на кнопку (кнопка никогда его не получит)

        return button;
    }

    private void createButtonsWithIcon(JButton[] buttons) {
        for (JButton button : buttons) {
            createButtonWithIcon(button);
        }
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

    public JButton getbLeftUpCar() {
        return bLeftUpCar;
    }
}
