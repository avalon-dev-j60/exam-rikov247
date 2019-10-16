/**
 * Рабочая область с кнопками поверх видео
 */
package tests.popupButton;

import com.sun.jna.platform.WindowUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import net.miginfocom.swing.MigLayout;
import org.quinto.swing.table.view.JBroTable;
import ru.trafficClicker.OnButtonClick;
import ru.trafficClicker.imageBackground.SimpleBackground;

public class OverlayTest extends JWindow {

    private JBroTable table;
    // ИКОНКИ
    // Машина
    private ImageIcon iconCar = new ImageIcon(this.getClass().getResource("/icons/car/car.png"));
    // Грузовик
    private ImageIcon iconTruck = new ImageIcon(this.getClass().getResource("/icons/car/truck1.png"));
    // Автобус
    private ImageIcon iconBus = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    // Троллейбус
    private ImageIcon iconTrolleybus = new ImageIcon(this.getClass().getResource("/icons/car/trolleybus.png"));
    // Трамвай
    private ImageIcon iconTram = new ImageIcon(this.getClass().getResource("/icons/car/tram.png"));
    // Автопоезд
    private ImageIcon iconTrainBus = new ImageIcon(this.getClass().getResource("/icons/car/trainBus.png"));

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
    private Component[][] leftContainerComponent;

    private JLabel aroundLeftLabel = new JLabel(aroundIcon);
    private JLabel leftLeftLabel = new JLabel(leftIcon);
    private JLabel forwardLeftLabel = new JLabel(forwardIcon);
    private JLabel rightLeftLabel = new JLabel(rightIcon);

    private JButton bAroundLeftCar = new JButton(iconCar);
    private JButton bAroundLeftTruck1 = new JButton(iconTruck);
    private JButton bAroundLeftBus1 = new JButton(iconBus);

    private JButton bLeftLeftCar = new JButton(iconCar);
    private JButton bLeftLeftTruck1 = new JButton(iconTruck);
    private JButton bLeftLeftBus1 = new JButton(iconBus);

    private JButton bForwardLeftCar = new JButton(iconCar);
    private JButton bForwardLeftTruck1 = new JButton(iconTruck);
    private JButton bForwardLeftBus1 = new JButton(iconBus);

    private JButton bRightLeftCar = new JButton(iconCar);
    private JButton bRightLeftTruck1 = new JButton(iconTruck);
    private JButton bRightLeftBus1 = new JButton(iconBus);

    private JButton bManLeft = new JButton(manIcon);
    private JButton bBikeLeft = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsLeft = {
        aroundLeftLabel, leftLeftLabel, forwardLeftLabel, rightLeftLabel,
        bAroundLeftCar, bLeftLeftCar, bForwardLeftCar, bRightLeftCar,
        bAroundLeftTruck1, bLeftLeftTruck1, bForwardLeftTruck1, bRightLeftTruck1,
        bAroundLeftBus1, bLeftLeftBus1, bForwardLeftBus1, bRightLeftBus1
    };

    private JLabel[] labelsLeft = {
        aroundLeftLabel, leftLeftLabel, forwardLeftLabel, rightLeftLabel
    };

    private JButton[] buttonsLeft = {
        bAroundLeftCar, bLeftLeftCar, bForwardLeftCar, bRightLeftCar,
        bAroundLeftTruck1, bLeftLeftTruck1, bForwardLeftTruck1, bRightLeftTruck1,
        bAroundLeftBus1, bLeftLeftBus1, bForwardLeftBus1, bRightLeftBus1
    //        createButtonWithIcon(bManLeft, manIcon),       
    //        createButtonWithIcon(bBikeLeft, bikeIcon)
    };

    //НИЖНЯЯ панель
    private Component[][] downContainerComponent;

    private JLabel aroundDownLabel = new JLabel(aroundIcon);
    private JLabel leftDownLabel = new JLabel(leftIcon);
    private JLabel forwardDownLabel = new JLabel(forwardIcon);
    private JLabel rightDownLabel = new JLabel(rightIcon);

    private JButton bAroundDownCar = new JButton(iconCar);
    private JButton bAroundDownTruck1 = new JButton(iconTruck);
    private JButton bAroundDownBus1 = new JButton(iconBus);

    private JButton bLeftDownCar = new JButton(iconCar);
    private JButton bLeftDownTruck1 = new JButton(iconTruck);
    private JButton bLeftDownBus1 = new JButton(iconBus);

    private JButton bForwardDownCar = new JButton(iconCar);
    private JButton bForwardDownTruck1 = new JButton(iconTruck);
    private JButton bForwardDownBus1 = new JButton(iconBus);

    private JButton bRightDownCar = new JButton(iconCar);
    private JButton bRightDownTruck1 = new JButton(iconTruck);
    private JButton bRightDownBus1 = new JButton(iconBus);

    private JButton bManDown = new JButton(manIcon);
    private JButton bBikeDown = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsDown = {
        aroundDownLabel, leftDownLabel, forwardDownLabel, rightDownLabel,
        bAroundDownCar, bLeftDownCar, bForwardDownCar, bRightDownCar,
        bAroundDownTruck1, bLeftDownTruck1, bForwardDownTruck1, bRightDownTruck1,
        bAroundDownBus1, bLeftDownBus1, bForwardDownBus1, bRightDownBus1
    };

    private JLabel[] labelsDown = {
        aroundDownLabel, leftDownLabel, forwardDownLabel, rightDownLabel
    };

    private JButton[] buttonsDown = {
        bAroundDownCar, bLeftDownCar, bForwardDownCar, bRightDownCar,
        bAroundDownTruck1, bLeftDownTruck1, bForwardDownTruck1, bRightDownTruck1,
        bAroundDownBus1, bLeftDownBus1, bForwardDownBus1, bRightDownBus1
//        createButtonWithIcon(bManDown, manIcon),
//        createButtonWithIcon(bBikeDown, bikeIcon)
    };

    //ПРАВАЯ панель
    private Component[][] rightContainerComponent;

    private JLabel aroundRightLabel = new JLabel(aroundIcon);
    private JLabel leftRightLabel = new JLabel(leftIcon);
    private JLabel forwardRightLabel = new JLabel(forwardIcon);
    private JLabel rightRightLabel = new JLabel(rightIcon);

    private JButton bAroundRightCar = new JButton(iconCar);
    private JButton bAroundRightTruck1 = new JButton(iconTruck);
    private JButton bAroundRightBus1 = new JButton(iconBus);

    private JButton bLeftRightCar = new JButton(iconCar);
    private JButton bLeftRightTruck1 = new JButton(iconTruck);
    private JButton bLeftRightBus1 = new JButton(iconBus);

    private JButton bForwardRightCar = new JButton(iconCar);
    private JButton bForwardRightTruck1 = new JButton(iconTruck);
    private JButton bForwardRightBus1 = new JButton(iconBus);

    private JButton bRightRightCar = new JButton(iconCar);
    private JButton bRightRightTruck1 = new JButton(iconTruck);
    private JButton bRightRightBus1 = new JButton(iconBus);

    private JButton bManRight = new JButton(manIcon);
    private JButton bBikeRight = new JButton(bikeIcon);

    // исходно заполняем панель этими компонентами
    private JComponent[] componentsRight = {
        aroundRightLabel, leftRightLabel, forwardRightLabel, rightRightLabel,
        bAroundRightCar, bLeftRightCar, bForwardRightCar, bRightRightCar,
        bAroundRightTruck1, bLeftRightTruck1, bForwardRightTruck1, bRightRightTruck1,
        bAroundRightBus1, bLeftRightBus1, bForwardRightBus1, bRightRightBus1
    };

    private JLabel[] labelsRight = {
        aroundRightLabel, leftRightLabel, forwardRightLabel, rightRightLabel
    };

    private JButton[] buttonsRight = {
        bAroundRightCar, bLeftRightCar, bForwardRightCar, bRightRightCar,
        bAroundRightTruck1, bLeftRightTruck1, bForwardRightTruck1, bRightRightTruck1,
        bAroundRightBus1, bLeftRightBus1, bForwardRightBus1, bRightRightBus1
    };

    // Layout менеджер, который конфигурирует 4 столбца и сколько нужно строк
    SimpleBackground panelUp = new SimpleBackground(new MigLayout()) {
        // для правильной установки прозрачности кнопок переопределяем метод отрисовки компонента
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    };
    SimpleBackground panelLeft = new SimpleBackground(new MigLayout());
    SimpleBackground panelDown = new SimpleBackground(new MigLayout());
    SimpleBackground panelRight = new SimpleBackground(new MigLayout());

    // Основная overlay панель, на которую помещаются кнопки. LayoutManager = null
    private JPanel overlayPanel = new JPanel(null);

    public OverlayTest(Window owner, JBroTable table, String typeOfStatement, TreePath[] paths) throws IOException {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        this.table = table;
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

        // Устанавливаем картинку на фон панели с кнопками
        panelUp.setBackground(ImageIO.read(new File("src/icons/numbers/11.png")));
        panelLeft.setBackground(ImageIO.read(new File("src/icons/numbers/44.png")));
        panelDown.setBackground(ImageIO.read(new File("src/icons/numbers/33.png")));
        panelRight.setBackground(ImageIO.read(new File("src/icons/numbers/22.png")));

        // Добавление исходных компонентов 
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            int row = 1;
            // Определяем, какие кнопки отображать и что будем считать
            boolean car = false;
            boolean bus = false;
            boolean truck = false;
            boolean trolleybus = false;
            boolean tram = false;
            for (TreePath tp : paths) {
                String temp = String.valueOf(tp.getLastPathComponent());
                System.out.println(temp);
                if (temp.equalsIgnoreCase("Легковой транспорт")) {
                    car = true;
                    row = row + 1;
                }
                if (temp.equalsIgnoreCase("Автобусы")) {
                    bus = true;
                    row = row + 1;
                }
                if (temp.equalsIgnoreCase("Грузовые")) {
                    truck = true;
                    row = row + 1;
                }
                if (temp.equalsIgnoreCase("Троллейбусы")) {
                    trolleybus = true;
                    row = row + 1;
                }
                if (temp.equalsIgnoreCase("Трамвай")) {
                    tram = true;
                    row = row + 1;
                }
            }
            System.out.println(row);

            doComponents up = new doComponents();
            // 1. Есть все
            if (car && bus && truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTruck, bUpTrolleybus, bUpTram);
            }
            // 2. Нет одного из всех:
            // 2.1. нет трамвая
            if (car && bus && truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTruck, bUpTrolleybus);
            }
            // 2.2. нет троллейбуса
            if (car && bus && truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTruck, bUpTram);
            }
            // 2.3. нет грузовых
            if (car && bus && !truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTrolleybus, bUpTram);
            }
            // 2.4. нет автобусов
            if (car && !bus && truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTruck, bUpTrolleybus, bUpTram);
            }
            // 2.5. нет машин
            if (!car && bus && truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTruck, bUpTrolleybus, bUpTram);
            }
            // 3. Нет двух из всех
            // ТРАМВАЙ и
            // 3.1. нет трамвая и троллейбуса
            if (car && bus && truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTruck);
            }
            // 3.2. нет трамвая и грузовых
            if (car && bus && !truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTrolleybus);
            }
            // 3.3. нет трамвая и автобусов
            if (car && !bus && truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTruck, bUpTrolleybus);
            }
            // 3.4. нет трамвая и машин
            if (!car && bus && truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTruck, bUpTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            // 3.5. нет троллейбуса и грузовых
            if (car && bus && !truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTram);
            }
            // 3.6. нет троллейбуса и автобусов
            if (car && !bus && truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTruck, bUpTram);
            }
            // 3.7. нет троллейбуса и машин
            if (!car && bus && truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTruck, bUpTram);
            }
            // ГРУЗОВЫЕ и
            // 3.8. нет грузовых и автобусов
            if (car && !bus && !truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTrolleybus, bUpTram);
            }
            // 3.9. нет грузовых и машин
            if (!car && bus && !truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTrolleybus, bUpTram);
            }
            // АВТОБУСЫ и
            // 3.10. нет автобусов и машин
            if (!car && !bus && truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpTruck, bUpTrolleybus, bUpTram);
            }
            // 4. Нет трех из всех
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            // 4.1. нет трамваев,троллейбусов и грузовых
            if (car && bus && !truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus);
            }
            // 4.2. нет трамваев, троллейбусов и автобусов
            if (car && !bus && truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTruck);
            }
            // 4.3. нет трамваев, троллейбусов и машин
            if (!car && bus && truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTruck);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            // 4.4. нет трамваев, грузовых и автобусов
            if (car && !bus && !truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTrolleybus);
            }
            // 4.5. нет трамваев, грузовых и машин
            if (!car && bus && !truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            // 4.6. нет трамваев, автобусов и машин
            if (!car && !bus && truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpTruck, bUpTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            // 4.7. нет троллейбусов, грузовых и автобусов
            if (car && !bus && !truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar, bUpTram);
            }
            // 4.8. нет троллейбусов, грузовых и машин
            if (!car && bus && !truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus, bUpTram);
            }
            // ГРУЗОВЫХ,АВТОБУСОВ и
            // 4.9. нет грузовых, автобусов и машин
            if (!car && !bus && !truck && trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpTrolleybus, bUpTram);
            }
            // ТРОЛЛЕЙБУСОВ,АВТОБУСОВ и
            // 4.10. нет троллейбусов, автобусов и машин
            if (!car && !bus && truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpTruck, bUpTram);
            }
            // 5. Нет четырех из всех
            // 5.1. есть только трамваи
            if (!car && !bus && !truck && !trolleybus && tram) {
                componentsUp = up.doComponents(labelsUp, bUpTram);
            }
            // 5.2. есть только троллейбусы
            if (!car && !bus && !truck && trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpTrolleybus);
            }
            // 5.3. есть только грузовые
            if (!car && !bus && truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpTruck);
            }
            // 5.4. есть только автобусы
            if (!car && bus && !truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpBus);
            }
            // 5.5. есть только машины
            if (car && !bus && !truck && !trolleybus && !tram) {
                componentsUp = up.doComponents(labelsUp, bUpCar);
            }

//            componentsUp = up.doComponents(labelsUp, bUpCar, bUpBus, bUpTruck, bUpTrolleybus, bUpTram); // Заполняем передаваемый контейнер тем, что нам нужно
            createPanelOfButtonss(panelUp, row, 4, componentsUp); // заполнение панели нужными элементами из собранного контейнера (componentsUp)
            panelUp.setLocation(200, 0); // установка изначального местоположения панели

            leftContainerComponent = new Component[4][4]; // указание размеров панели
            createEmptyPanelOfButtons(panelLeft, leftContainerComponent); // заполнение панели пустыми элементами 
            createPanelOfButtons(panelLeft, leftContainerComponent, componentsLeft); // заполнение панели нужными элементами (componentsUp)
            panelLeft.setLocation(0, 100); // установка изначального местоположения панели

            downContainerComponent = new Component[4][4]; // указание размеров панели
            createEmptyPanelOfButtons(panelDown, downContainerComponent); // заполнение панели пустыми элементами 
            createPanelOfButtons(panelDown, downContainerComponent, componentsDown); // заполнение панели нужными элементами (componentsUp)
            panelDown.setLocation(500, 400); // установка изначального местоположения панели

            rightContainerComponent = new Component[4][4]; // указание размеров панели
            createEmptyPanelOfButtons(panelRight, rightContainerComponent); // заполнение панели пустыми элементами 
            createPanelOfButtons(panelRight, rightContainerComponent, componentsRight); // заполнение панели нужными элементами (componentsUp)
            panelRight.setLocation(650, 0); // установка изначального местоположения панели
        }

        overlayPanel.add(panelUp);
        overlayPanel.add(panelLeft);
        overlayPanel.add(panelDown);
        overlayPanel.add(panelRight);

        // Слушатель кликов по кнопке и занесение информации в таблицу
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            // Создаем экземпляр popupMenu для Truck направления Up (1)
            bAroundUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundUpBus, table, typeOfStatement, "ФЕ Разворот 11", "Bus");
            new popupButtonToTable(bAroundUpTruck, table, typeOfStatement, "ФЕ Разворот 11", "Truck");
            bAroundUpTrolleyBus.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bAroundUpTram.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 

            bLeftUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftUpTruck, table, typeOfStatement, "ФЕ Налево 12", "Truck");
            new popupButtonToTable(bLeftUpBus, table, typeOfStatement, "ФЕ Налево 12", "Bus");
            bLeftUpTrolleyBus.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bLeftUpTram.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            
            bForwardUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardUpTruck, table, typeOfStatement, "ФЕ Прямо 1", "Truck");
            new popupButtonToTable(bForwardUpBus, table, typeOfStatement, "ФЕ Прямо 1", "Bus");
            bForwardUpTrolleyBus.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bForwardUpTram.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            
            bRightUpCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightUpTruck, table, typeOfStatement, "ФЕ Направо 14", "Truck");
            new popupButtonToTable(bRightUpBus, table, typeOfStatement, "ФЕ Направо 14", "Bus");
            bRightUpTrolleyBus.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            bRightUpTram.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 

            // Создаем экземпляр popupMenu для Truck направления Left (4)
            bAroundLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 44")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundLeftBus1, table, typeOfStatement, "ФЕ Разворот 44", "Bus");
            new popupButtonToTable(bAroundLeftTruck1, table, typeOfStatement, "ФЕ Разворот 44", "Truck");
            bLeftLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 41")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftLeftTruck1, table, typeOfStatement, "ФЕ Налево 41", "Truck");
            new popupButtonToTable(bLeftLeftBus1, table, typeOfStatement, "ФЕ Налево 41", "Bus");
            bForwardLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 4")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardLeftTruck1, table, typeOfStatement, "ФЕ Прямо 4", "Truck");
            new popupButtonToTable(bForwardLeftBus1, table, typeOfStatement, "ФЕ Прямо 4", "Bus");
            bRightLeftCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 43")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightLeftTruck1, table, typeOfStatement, "ФЕ Направо 43", "Truck");
            new popupButtonToTable(bRightLeftBus1, table, typeOfStatement, "ФЕ Направо 43", "Bus");

            // Инициализируем слушателей кнопок. Также создаем экземпляр popupMenu для Truck направления Down (3)
            bAroundDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 33")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundDownBus1, table, typeOfStatement, "ФЕ Разворот 33", "Bus");
            new popupButtonToTable(bAroundDownTruck1, table, typeOfStatement, "ФЕ Разворот 33", "Truck");
            bLeftDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 34")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftDownTruck1, table, typeOfStatement, "ФЕ Налево 34", "Truck");
            new popupButtonToTable(bLeftDownBus1, table, typeOfStatement, "ФЕ Налево 34", "Bus");
            bForwardDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 3")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardDownTruck1, table, typeOfStatement, "ФЕ Прямо 3", "Truck");
            new popupButtonToTable(bForwardDownBus1, table, typeOfStatement, "ФЕ Прямо 3", "Bus");
            bRightDownCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 32")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightDownTruck1, table, typeOfStatement, "ФЕ Направо 32", "Truck");
            new popupButtonToTable(bRightDownBus1, table, typeOfStatement, "ФЕ Направо 32", "Bus");

            // Создаем экземпляр popupMenu для Truck направления Right (2)
            bAroundRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Разворот 22")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundRightBus1, table, typeOfStatement, "ФЕ Разворот 22", "Bus");
            new popupButtonToTable(bAroundRightTruck1, table, typeOfStatement, "ФЕ Разворот 22", "Truck");
            bLeftRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Налево 23")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftRightBus1, table, typeOfStatement, "ФЕ Налево 23", "Bus");
            new popupButtonToTable(bLeftRightTruck1, table, typeOfStatement, "ФЕ Налево 23", "Truck");
            bForwardRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Прямо 2")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardRightBus1, table, typeOfStatement, "ФЕ Прямо 2", "Bus");
            new popupButtonToTable(bForwardRightTruck1, table, typeOfStatement, "ФЕ Прямо 2", "Truck");
            bRightRightCar.addActionListener(new OnButtonClick(table, "Легковой транспорт", "ФЕ Направо 21")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightRightBus1, table, typeOfStatement, "ФЕ Направо 21", "Bus");
            new popupButtonToTable(bRightRightTruck1, table, typeOfStatement, "ФЕ Направо 21", "Truck");
        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            // Создаем экземпляр popupMenu для Truck направления Up (1)
            bAroundUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 11")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundUpBus, table, typeOfStatement, "ФЕ Разворот 11", "Bus");
            new popupButtonToTable(bAroundUpTruck, table, typeOfStatement, "ФЕ Разворот 11", "Truck");
            bLeftUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 12")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftUpTruck, table, typeOfStatement, "ФЕ Налево 12", "Truck");
            new popupButtonToTable(bLeftUpBus, table, typeOfStatement, "ФЕ Налево 12", "Bus");
            bForwardUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 1")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardUpTruck, table, typeOfStatement, "ФЕ Прямо 1", "Truck");
            new popupButtonToTable(bForwardUpBus, table, typeOfStatement, "ФЕ Прямо 1", "Bus");
            bRightUpCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 14")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightUpTruck, table, typeOfStatement, "ФЕ Направо 14", "Truck");
            new popupButtonToTable(bRightUpBus, table, typeOfStatement, "ФЕ Направо 14", "Bus");

            // Создаем экземпляр popupMenu для Truck направления Left (4)
            bAroundLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 44")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundLeftBus1, table, typeOfStatement, "ФЕ Разворот 44", "Bus");
            new popupButtonToTable(bAroundLeftTruck1, table, typeOfStatement, "ФЕ Разворот 44", "Truck");
            bLeftLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 41")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftLeftTruck1, table, typeOfStatement, "ФЕ Налево 41", "Truck");
            new popupButtonToTable(bLeftLeftBus1, table, typeOfStatement, "ФЕ Налево 41", "Bus");
            bForwardLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 4")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardLeftTruck1, table, typeOfStatement, "ФЕ Прямо 4", "Truck");
            new popupButtonToTable(bForwardLeftBus1, table, typeOfStatement, "ФЕ Прямо 4", "Bus");
            bRightLeftCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 43")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightLeftTruck1, table, typeOfStatement, "ФЕ Направо 43", "Truck");
            new popupButtonToTable(bRightLeftBus1, table, typeOfStatement, "ФЕ Направо 43", "Bus");

            // Инициализируем слушателей кнопок. Также создаем экземпляр popupMenu для Truck направления Down (3)
            bAroundDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 33")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundDownBus1, table, typeOfStatement, "ФЕ Разворот 33", "Bus");
            new popupButtonToTable(bAroundDownTruck1, table, typeOfStatement, "ФЕ Разворот 33", "Truck");
            bLeftDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 34")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftDownTruck1, table, typeOfStatement, "ФЕ Налево 34", "Truck");
            new popupButtonToTable(bLeftDownBus1, table, typeOfStatement, "ФЕ Налево 34", "Bus");
            bForwardDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 3")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardDownTruck1, table, typeOfStatement, "ФЕ Прямо 3", "Truck");
            new popupButtonToTable(bForwardDownBus1, table, typeOfStatement, "ФЕ Прямо 3", "Bus");
            bRightDownCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 32")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightDownTruck1, table, typeOfStatement, "ФЕ Направо 32", "Truck");
            new popupButtonToTable(bRightDownBus1, table, typeOfStatement, "ФЕ Направо 32", "Bus");

            // Создаем экземпляр popupMenu для Truck направления Right (2)
            bAroundRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Разворот 22")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bAroundRightBus1, table, typeOfStatement, "ФЕ Разворот 22", "Bus");
            new popupButtonToTable(bAroundRightTruck1, table, typeOfStatement, "ФЕ Разворот 22", "Truck");
            bLeftRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Налево 23")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bLeftRightBus1, table, typeOfStatement, "ФЕ Налево 23", "Bus");
            new popupButtonToTable(bLeftRightTruck1, table, typeOfStatement, "ФЕ Налево 23", "Truck");
            bForwardRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Прямо 2")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bForwardRightBus1, table, typeOfStatement, "ФЕ Прямо 2", "Bus");
            new popupButtonToTable(bForwardRightTruck1, table, typeOfStatement, "ФЕ Прямо 2", "Truck");
            bRightRightCar.addActionListener(new OnButtonClick(table, "Легковые, фургоны", "ФЕ Направо 21")::onButtonClick); // Добавляем к кнопке Листенер. Ищем листенер в переменной bClick в её методе onButtonClick 
            new popupButtonToTable(bRightRightBus1, table, typeOfStatement, "ФЕ Направо 21", "Bus");
            new popupButtonToTable(bRightRightTruck1, table, typeOfStatement, "ФЕ Направо 21", "Truck");
        }
    }

    // НЕ НУЖЕН! УБРАТЬ, КОГДА ВСЕ ПЕРЕДЕЛАЮ
    // Заполняем панель компонентами (абсрактными) и заменяем компоненты (ссылки на них) нужными нам элементами. Чтобы затем иметь возможность заменять элементы в сконфигурированной панели
    private JPanel createEmptyPanelOfButtons(JPanel panel, Component[][] containerComponents) {
        int panelWidth = 32;
        int panelHeight = 32;
        // Настройка панели
        panel.setSize(new Dimension(panelWidth * containerComponents[0].length + panelWidth / 4,
                panelHeight * containerComponents.length + panelHeight / 2)); // размер панели с кнопками
        panel.setOpaque(false); // для правильной установки прозрачности кнопок
        panel.setBackground(new Color(.0f, .0f, .0f, .0f)); // установка цвета фона и прозрачности панели с кнопками (чтобы можно было тыкать в видео между кнопок нужно сделать прозрачность нулевой!
        panel.setFocusable(false); // панель не получает фокуса никогда

        // Исходно заполняем массив пустыми элементами (чтобы не было null в массиве) и добавляем их в панель
        for (int i = 0; i < containerComponents.length; i++) {
            for (int j = 0; j < containerComponents[0].length; j++) {
                containerComponents[i][j] = new JLabel("");
                panel.add(containerComponents[i][j]);
            }
        }
        return panel; // возвращаем панель с добавленными элементами
    }

    // НЕ НУЖЕН! УБРАТЬ, КОГДА ВСЕ ПЕРЕДЕЛАЮ! ОСТАВИТЬ НИЖНИЙ МЕТОД, контейнеры больше не нужны!
    private JPanel createPanelOfButtons(JPanel panel, Component[][] containerComponents, JComponent[] injectedComponents) {
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
        int panelWidth = ((JButton) containerComponents[1][1]).getIcon().getIconWidth();
        int panelHeight = ((JLabel) containerComponents[0][0]).getIcon().getIconHeight() + (int) (((JLabel) containerComponents[0][0]).getIcon().getIconHeight() / 3);
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

    private JPanel createPanelOfButtonss(JPanel panel, int row, int column, JComponent[] injectedComponents) {
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
        int panelWidth = ((JButton) containerComponents[1][1]).getIcon().getIconWidth();
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
                panelUp.repaint();
                overlayPanel.repaint();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panelUp.repaint();
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
