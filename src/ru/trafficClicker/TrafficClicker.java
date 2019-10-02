package ru.trafficClicker;

import resources.FileChooserRus;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.avalon.java.ui.AbstractFrame; // подключенная собственная библиотека

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.quinto.swing.table.view.JBroTable;
import ru.AddVideoPanel;
import ru.CreateLeftControlPanel;
import ru.CreateMenuBar;
import ru.CreatePopupVideoMenu;
import ru.CreateRightControlPanel;
import ru.CreateVideoPlayerControlPanel;
import ru.Overlay;
import ru.Excel.Save.FileSave;
import ru.jtable.Table;
import ru.videoOpen;

import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class TrafficClicker extends AbstractFrame {

    private Overlay overlay;
    private AddVideoPanel addVideoPanel = new AddVideoPanel();
    private JBroTable table = new JBroTable(); // создание таблицы
    private Canvas canvas = new Canvas();

    private String filePath;

    // Создание плеера. Он сам создает mediaPlayerComponent для себя. Сам является панелью JPanel (не нужна canvas)!
    private EmbeddedMediaPlayerComponent emp = new EmbeddedMediaPlayerComponent();

    // Переменная для менюБара
    private final CreateMenuBar jBar = new CreateMenuBar();

    // Переменная для всплывающего меню
    private final CreatePopupVideoMenu popupMenu = new CreatePopupVideoMenu();

    // Переменные для создания Панелей видео плеера
    private CreateVideoPlayerControlPanel vPCPanel = new CreateVideoPlayerControlPanel(); // VideoPlayer Control Panel (Контрольная Панель Видео Плеера)
    private CreateLeftControlPanel leftCPanel = new CreateLeftControlPanel(); // Left Control Panel (Левая Контрольная Панель)
    private CreateRightControlPanel rightCPanel = new CreateRightControlPanel(); // Right Control Panel (Правая Контрольная Панель)

    // Переменная для активации overlay слоя "Добавления видео"
    private boolean overlayAddVideoBoolean = true;

    // Конструктор с созданием окна поверх видео. Отдельный конструктор, потому что Exception
    public TrafficClicker() throws IOException {
        this.overlay = new Overlay(this);
    }

    // Что происходит при создании окна
    @Override
    protected void onCreate() {
        // Установка Windows Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTitle("Traffic Clicker"); // установка названия окна
        setExtendedState(JFrame.MAXIMIZED_BOTH); // установка размера окна на максимально возможное (не полноэкранный режим, нижняя панель windows присутствует)
        setMinimumSize(new Dimension(1100, 700));

        // Установка Менеджера Компоновки окна
        setLayout(new BorderLayout());

        // Установка Меню Бара
        setJMenuBar(jBar.CreateBar());

        // НАСТРОЙКА ВИДЕО И ПОВЕРХНОСТИ ПОД ВИДЕО
        // Установка canvas (подосновы для видео)
        emp.mediaPlayer().videoSurface().set(emp.mediaPlayerFactory().videoSurfaces().newVideoSurface(canvas));
        canvas.setBackground(Color.black); // установка цвета заднего фона для canvas (подосновы для видео)
        canvas.setMinimumSize(new Dimension((int) this.getMinimumSize().getWidth() - 300, (int) this.getMinimumSize().getHeight() - 100)); // задаем минимальные размеры панели с видео
        // Явно отключаем встроенную обработку событий от мыши и клавиатуры
        emp.mediaPlayer().input().enableMouseInputHandling(false);
        emp.mediaPlayer().input().enableKeyInputHandling(false);
        // Указываем стратегию Полноэкранного режима
        emp.mediaPlayer().fullScreen().strategy(new AdaptiveFullScreenStrategy(this));

        // Добавление всплывающего меню
        emp.setComponentPopupMenu(popupMenu.createPopupMenu());

        // Добавление панелей
        add(vPCPanel.createVPCPanel(), BorderLayout.SOUTH); // Контрольная панель видеоплеера (снизу)
        add(createTabbedPane(), BorderLayout.CENTER); // панель вкладок, внутри которой есть панели с разделением на области (SplitPanel) - видео, таблица

// Добавление СЛУШАТЕЛЕЙ СОБЫТИЙ. В случае нажатия на кнопку - вызывается метод в скобках. Создается объект нужного типа в котором вызывается метод данного типа. :: - ссылка на метод.
        // Слушатели панели управления видео:
        vPCPanel.getB1().addActionListener(this::onStopButtonClick); // Добавление слушателя действия для кнопки B1
        vPCPanel.getB2().addActionListener(this::onPlayPauseButtonClick); // Добавление слушателя действия для кнопки B2

        // Слушатели на видео поверхность
        canvas.addMouseListener(videoMouseClick); // Добавление слушателя событий к поверхности плеера (подоснове для видео) - то есть к самому видео
        canvas.addKeyListener(keyVideoTabeAdapter); // Добавление слушателя для действий клавиатуры

        canvas.addMouseWheelListener(wheelSound);  // Слушатель изменения звука
        rightCPanel.getLabel().setText("volume: " + emp.mediaPlayer().audio().volume() + " %"); // отображение состояния звука

        // Слушатели КНОПОК В БАРЕ (MenuBar)
        jBar.getFileItem5().addActionListener(this::onAddVideoButtonClick); // Слушатель кнопки "Выбор видео" в Баре
        jBar.getViewItem3().addActionListener(this::onFullScreenButtonClick); // Слушатель кнопки "Полноэкранного режима" в Баре
        jBar.getFileItem4().addActionListener(this::onSaveAsButtonClick); // Слушатель кнопки "Сохранить как..."

        // Слушатели ПЕРЕМЕЩЕНИ ПАНЕЛЕЙ с кнопками к Лэйблам (направление движения транспорта)
        for (JLabel label : overlay.getLabelsUp()) {
            new ComponentMover(overlay.getPanelUp(), label); // добавление слушателя (что перемещать, на что для этого нажимать)
        }
        for (JLabel label : overlay.getLabelsLeft()) {
            new ComponentMover(overlay.getPanelLeft(), label);
        }
        for (JLabel label : overlay.getLabelsDown()) {
            new ComponentMover(overlay.getPanelDown(), label);
        }
        for (JLabel label : overlay.getLabelsRight()) {
            new ComponentMover(overlay.getPanelRight(), label);
        }

        // Слушатель окна приложения (освобождаем ресурсы при закрывании)
        addWindowListener(winAdapter);

        // Слушатель изменения размера окна
        canvas.addComponentListener(frameSizeAdapter);

        // Слушатель установления фокуса на canvas = если фокус на canvas, то он устанавливает фокус на canvas (почему то при изменении размера это не дает фокусу переходить на overlay, чего и хотелось). Как бы КОСТЫЛЬ
        // Здесь добавление СТАРТОВОГО overlay слоя Добавления видео
        canvas.addFocusListener(canvasFocusAdapter);

        // Слушатель событий для видеоДорожки
        emp.mediaPlayer().events().addMediaPlayerEventListener(mpEventAdapter);

        // Overlay слой добавления видео
        addVideoPanel.getButton().addActionListener(this::onAddVideoButtonClick);
    }

    // Подготовка видео, запуск и постановка на паузу. Также установка всплывающей панели
    private void prepareVideo(String filePath) {
        // Подготавливаем видео, но не запускаем его
        emp.mediaPlayer().media().prepare(filePath, (String) null);
        // Запускаем видео
        emp.mediaPlayer().controls().play();
        // Устанавливаем паузу
        emp.mediaPlayer().controls().setPause(true);
        // Marguee (всплывающая) панель. Можно ей управлять и до и после включения видео
        setMarquee(filePath);
        canvas.requestFocus(); // фокус на canvas (видео)
    }

    // Нажатие кнопки СТОП.
    private void onStopButtonClick(ActionEvent e) {
        // Связка из трех операций для правильного отображения времени на панели времени (и для правильного состояния видео)
        emp.mediaPlayer().controls().setPosition(0); // когда видео заканчивается, оно переходит в начальное состояние (stop)
        emp.mediaPlayer().controls().play(); // запуск видео
        emp.mediaPlayer().controls().setPause(true); // пауза
        canvas.requestFocus(); // установка фокуса на canvas (видео)
        tableFocusOnVPCPanel(); // переключение фокуса на панель управления видео, если фокус не получилось переключить на canvas (т.е. если сейчас в фокусе/открыта другая вкладка)
    }

    // Нажатие кнопки ПАУЗА/ПЛЭЙ.
    private void onPlayPauseButtonClick(ActionEvent e) {
        if (emp.mediaPlayer().status().isPlaying() == true) { // Если медиа проигрывается (play)
            emp.mediaPlayer().controls().pause(); // то - пауза (pause)
            canvas.requestFocus(); // установка фокуса на canvas (видео)
        } else {
            emp.mediaPlayer().controls().play(); // Если медиа не проигрывается, то - play
            canvas.requestFocus(); // установка фокуса на canvas (видео)
        }
        tableFocusOnVPCPanel(); // переключение фокуса на панель управления видео, если фокус не получилось переключить на canvas (т.е. если сейчас в фокусе/открыта другая вкладка)
    }

    // Нажатие на кнопку "Сохранить как..."
    private void onSaveAsButtonClick(ActionEvent e) {
        try {
            new FileSave(table);
        } catch (IOException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Нажатие кнопки "Полноэкранный режим" в Меню Баре (Alt+Shift+Enter)
    private void onFullScreenButtonClick(ActionEvent e) {
        emp.mediaPlayer().fullScreen().set(!emp.mediaPlayer().fullScreen().isFullScreen());
        canvas.requestFocus(); // установка фокуса на canvas (видео)
        tableFocusOnVPCPanel(); // переключение фокуса на панель управления видео, если фокус не получилось переключить на canvas (т.е. если сейчас в фокусе/открыта другая вкладка)
    }

    // Нажатие кнопки "Открыть видео.." в Меню Баре (Alt+Shift+Enter). МОЖНО ДОБАВИТЬ ФИЛЬТР ФАЙЛОВ, и к другим кнопкам добавить действий (сохранение и т.п.)
    // Также это стартовая кнопка для выбора файла видео
    private File file = null; // если была открыто какое то видео, то в следующий раз FileChooser откроет эту же директорию (в котором было это видео)

    private void onAddVideoButtonClick(ActionEvent e) {
        new FileChooserRus(); // Локализация компонентов окна JFileChooser
        JFileChooser videoOpen = new videoOpen().videoOpen(null);
        if (file == null) {  // если еще никакой файл не был открыт, то открываются "Мои документы"
            videoOpen.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory()); //new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // создаем объект ВыбораФайлов с первоначальным месторасположением на РАБОЧЕМ СТОЛЕ  
            int ret = videoOpen.showDialog(null, "Открыть видеофайл"); // показываем диалог с названием "Открыть файл"
            if (ret == JFileChooser.APPROVE_OPTION) { //  
                file = videoOpen.getSelectedFile(); // выбираем этот файл (получаем на него ссылку)
                filePath = file.getAbsolutePath(); // берем текстовый абсолютный путь к файлу
                splitMain2.setLeftComponent(canvas); // на вкладку видео панели добавляем наше видео (canvas - подоснова для видео, на ней далее будет отображено видео)
                // Подготавливаем новое видео         
                prepareVideo(filePath);
                // Установка overlay слоя над видео (слой КНОПОК)
                emp.mediaPlayer().overlay().set(overlay);
                emp.mediaPlayer().overlay().enable(false);
            }
            if (ret == JFileChooser.CANCEL_OPTION) {
                // выходим из режима выбора файлов            
            }
        } else { // если какая то директория уже была выбрана ранее, то теперь откроется эта же директория
            videoOpen.setCurrentDirectory(file); // создаем объект ВыбораФайлов
            int ret = videoOpen.showDialog(null, "Открыть файл"); // показываем диалог с названием "Открыть файл"
            if (ret == JFileChooser.APPROVE_OPTION) { //  
                file = videoOpen.getSelectedFile(); // выбираем этот файл (получаем на него ссылку)
                filePath = file.getAbsolutePath(); // берем текстовый абсолютный путь к файлу
                // Подготавливаем новое видео         
                prepareVideo(filePath);
            }
        }
    }

    // АДАПТЕРЫ (СЛУШАТЕЛИ)
    // ДЕЙСТВИЯ С МЫШЬЮ на canvas (видео)
    private MouseAdapter videoMouseClick = new MouseAdapter() {
        // Pressed - только НАЖАТИЕ на кнопку мыши (отпускание не отслеживается
        @Override
        public void mousePressed(MouseEvent e) {
            // Метод отслеживает Клик ЛЕВОЙ (button1) кнопкой мыши по видео
            if (e.getButton() == MouseEvent.BUTTON1) {
                actionPlayPause(); // Включение|Пауза  видео
            }
            // Метод отслеживает Клик ПРАВОЙ (button3) кнопкой мыши по видео
            if (e.getButton() == MouseEvent.BUTTON3) {
                popupMenu.getJpm().show(e.getComponent(), e.getX(), e.getY()); // Отображение всплывающего меню (popup menu) в том месте, где нажимается кнопка мыши
            }
        }
    };

    // ДЕЙСТВИЯ С КЛАВИАТУРОЙ, если фокус на canvas (видео)!
    private KeyAdapter keyVideoTabeAdapter = new KeyAdapter() {
        // Pressed - только нажал (отпускание обрабатывается отдельно)
        @Override
        public void keyPressed(KeyEvent e) {
            // Space (пробел) - проигрывание | пауза
            if (e.getExtendedKeyCode() == e.VK_SPACE) {
                actionPlayPause();
            }
            // F11 - отображение кнопок на canvas (видео)
            if (e.getExtendedKeyCode() == e.VK_F11) {
                // Если canvas отображается на экране (окно отображается), то устанавливаем расположение кнопок
//                if (canvas.isShowing()) {
//                    upButtonOnVideoLayout();
//                    downButtonOnVideoLayout();
//                    leftButtonOnVideoLayout();
//                    rightButtonOnVideoLayout();
//                }
                // активация слоя overlay
                emp.mediaPlayer().overlay().enable(!emp.mediaPlayer().overlay().enabled()); // если overlay неактивен, то активировать и наоборот
                canvas.requestFocus(); // Установка фокуса на canvas
            }
            // Переход по видео ВЛЕВО на 1 секунду
            if ((e.getExtendedKeyCode() == e.VK_LEFT)) {
                emp.mediaPlayer().controls().skipTime(-1000);
            }
            // Переход по видео ВПРАВО на 1 секунду
            if ((e.getExtendedKeyCode() == e.VK_RIGHT)) {
                emp.mediaPlayer().controls().skipTime(1000);
            }
        }
    };

    // ИЗМЕНЕНИЕ РАЗМЕРА ОКНА
    private ComponentAdapter frameSizeAdapter = new ComponentAdapter() {

        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            if (emp.mediaPlayer().overlay().enabled() == true) { // если слой над видео Виден, то:
                // выполняем конфигурацию (размещение) кнопок в относительных координатах внутри canvas
//                upButtonOnVideoLayout();
//                downButtonOnVideoLayout();
//                leftButtonOnVideoLayout();
//                rightButtonOnVideoLayout();
                // Для предотвращения мигающего заднего фона overlay поверхности:
                overlayReload(); // отключение и включение overlay
            }
            // Чтобы при изменение размера окна фокус переходил на canvas (видео). Если этого не делать, то фокус переходит на overlay
            canvas.requestFocus(); // фокус на canvas (видео) 
        }
    };

    // ИЗМЕНЕНИЕ РАЗМЕРОВ панелей на JSplitPanel. Обновление overlay слоя 
    ComponentAdapter dividerChange = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            if (emp.mediaPlayer().overlay().enabled() == true) {
                overlayReload();
            }
            canvas.requestFocus(); // переключение фокуса на canvas
        }
    };

    // АДАПТЕР ВИДЕО (видеоплеера). Пока что для правильной реализации метода stop() плеера после окончания видео И запуска таймера (привязки к видеоплееру)
    private MediaPlayerEventAdapter mpEventAdapter = new MediaPlayerEventAdapter() {
        @Override // При открытии видео - отключаем overlay слой (кнопок)
        public void opening(MediaPlayer mediaPlayer) {
            super.opening(mediaPlayer);
            emp.mediaPlayer().overlay().enable(false);
        }

        @Override // при постановке видео на паузу - устанавливаем иконку play/pause в положение play
        public void paused(MediaPlayer mediaPlayer) {
            super.paused(mediaPlayer);
            vPCPanel.getB2().setIcon(vPCPanel.getPlayIcon()); // иконка кнопки меняется на Play
        }

        @Override // при запуске видео - устанавливаем иконку play/pause в положение pause и конфигурируем и запускаем таймер (для учета времени видео)
        public void playing(MediaPlayer mediaPlayer) {
            super.playing(mediaPlayer);
            timer(); // конфигурация и запуск панели отображения времени видео
            vPCPanel.getB2().setIcon(vPCPanel.getPauseIcon()); // иконка кнопки меняется на Pause
        }

        @Override // при окончании видео - в новом потоке исполнения открытое видео переводим в начальную позицию, запускаем и ставим на паузу. Устанавливаем иконку play/pause в положение play 
        public void finished(MediaPlayer mediaPlayer) {
            super.finished(mediaPlayer);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Связка из трех операций для правильного отображения времени на панели времени (и для правильного состояния видео)
                    mediaPlayer.controls().setPosition(0); // когда видео заканчивается, оно переходит в начальное состояние (stop)
                    mediaPlayer.controls().play(); // запуск видео
                    mediaPlayer.controls().setPause(true); // пауза
                    vPCPanel.getB2().setIcon(vPCPanel.getPlayIcon()); // иконка кнопки меняется на Play
                }
            });
        }
    };

    // СЛУШАТЕЛЬ КОЛЕСА МЫШИ (если мышь на canvas = видео). Для изменения звука видео
    MouseAdapter wheelSound = new MouseAdapter() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            // вращение колеса вверх (увеличение громкости)
            if (e.getWheelRotation() < 0) {
                if (emp.mediaPlayer().audio().volume() <= 200 && emp.mediaPlayer().audio().volume() >= 0) { // если звук в пределах допустимого, то
                    emp.mediaPlayer().audio().setVolume(emp.mediaPlayer().audio().volume() + 10); // увеличиваем звук на 10
                    rightCPanel.getLabel().setText("volume: " + emp.mediaPlayer().audio().volume() + " %");
                } else {
                    if (emp.mediaPlayer().audio().volume() > 200) {
                        emp.mediaPlayer().audio().setVolume(200);
                    }
                }
            }
            // вращение колеса вниз (уменьшение громкости)
            if (e.getWheelRotation() > 0) {
                if (emp.mediaPlayer().audio().volume() <= 200 && emp.mediaPlayer().audio().volume() >= 0) { // если звук в пределах допустимого, то
                    emp.mediaPlayer().audio().setVolume(emp.mediaPlayer().audio().volume() - 10); // уменьшаем звук на 10
                    rightCPanel.getLabel().setText("volume: " + emp.mediaPlayer().audio().volume() + " %");
                } else {
                    if (emp.mediaPlayer().audio().volume() > 200) {
                        emp.mediaPlayer().audio().setVolume(200);
                    }
                }
            }
        }
    };

    // ЗАКРЫТИЕ ОКНА (освобождение ресурсов)
    WindowAdapter winAdapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            emp.mediaPlayer().release();
            emp.mediaPlayerFactory().release();
            overlay.dispose();
        }
    };

    // ПЕРЕХОД (дополнительный) ФОКУСА на canvas (видео), если фокус перешел на canvas (видео)
    private FocusAdapter canvasFocusAdapter = new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            canvas.requestFocus();
        }
    };

    // РАЗМЕЩЕНИЕ КНОПОК НА ВИДЕО ПОВЕРХНОСТИ (на overlay) с присвязкой к canvas (видео)
//    private void upButtonOnVideoLayout() {
//        double indentFromTheMiddle = 1.75;
//        double indentBetweenButtnos = 0.2; // отступ между кнопками
//        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
//        int x = (int) ((canvas.getSize().getWidth() / 2) // середина видео
//                - (overlay.getWidthButton() / 2) // отступ на половину кнопки
//                - indentFromTheMiddle * overlay.getWidthButton()); // отступ от середины  
//        int y = 50; // координата Y кнопки
//
//        overlay.getB1().setLocation(x, y);
//        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB2().setLocation(x, y);
//        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB3().setLocation(x, y);
//        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB4().setLocation(x, y);
//    }
//
//    private void downButtonOnVideoLayout() {
//        double indentFromTheMiddle = 1.75;
//        double indentBetweenButtnos = 0.2; // отступ между кнопками
//        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
//        int x = (int) ((canvas.getSize().getWidth() / 2) // середина видео
//                - (overlay.getWidthButton() / 2) // отступ на половину кнопки
//                - indentFromTheMiddle * overlay.getWidthButton()); // отступ от середины  
//        int y = (int) (canvas.getSize().getHeight() - 50); // координата Y кнопки
//
//        overlay.getB5().setLocation(x, y);
//        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB6().setLocation(x, y);
//        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB7().setLocation(x, y);
//        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB8().setLocation(x, y);
//    }
//
//    private void rightButtonOnVideoLayout() {
//        double indentFromTheMiddle = 1.75;
//        double indentBetweenButtons = 0.2; // отступ между кнопками
//        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
//        int x = (int) (canvas.getSize().getWidth() - 200); // координата X кнопки
//        int y = (int) ((canvas.getSize().getHeight() / 2) // середина видео
//                - (2 * overlay.getHeightButton()) // отступ на половину кнопки
//                - indentFromTheMiddle * overlay.getHeightButton()); // отступ от середины  
//
//        overlay.getB9().setLocation(x, y);
//        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB10().setLocation(x, y);
//        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB11().setLocation(x, y);
//        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB12().setLocation(x, y);
//    }
//
//    private void leftButtonOnVideoLayout() {
//        double indentFromTheMiddle = 1.75;
//        double indentBetweenButtons = 0.2; // отступ между кнопками
//        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
//        int x = (int) (200 - overlay.getWidthButton()); // координата X кнопки
//        int y = (int) ((canvas.getSize().getHeight() / 2) // середина видео
//                - (2 * overlay.getHeightButton()) // отступ на половину кнопки
//                - indentFromTheMiddle * overlay.getHeightButton()); // отступ от середины  
//
//        overlay.getB13().setLocation(x, y);
//        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB14().setLocation(x, y);
//        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB15().setLocation(x, y);
//        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
//        overlay.getB16().setLocation(x, y);
//    }
// ВСПОМОГАТЕЛЬНЫЕ методы
    // Включение и выключение overlay слоя - как бы его обновление
    private void overlayReload() {
        emp.mediaPlayer().overlay().enable(false); // overlay не активен
        emp.mediaPlayer().overlay().enable(true); // overlay активен
    }

    // Установка прозрачности для overlay слоя
    private void overlayBackgroundZero() {
        overlay.setBackground(new Color(0, 0, 0, 0));
    }

    // переключение фокуса на панель управления видео - сделано для вкладки Таблицы (возможно и для других вкладок)
    private void tableFocusOnVPCPanel() {
        if (canvas.isFocusOwner() == false) { // если не получилось (открыта другая вкладка, не видео вкладка), то
            vPCPanel.getvPCPanel().requestFocus(); // переключаем фокус на панель контроля видео
        }
    }

    // Переключение состояния видео и текста кнопки
    private void actionPlayPause() {
        if (emp.mediaPlayer().status().isPlaying() == true) {
            emp.mediaPlayer().controls().pause();
        } else {
            emp.mediaPlayer().controls().play();
        }
    }

// МЕТОДЫ СОЗДАНИЯ: панелей, сущностей
    // ОСНОВНАЯ РАБОЧАЯ ПАНЕЛЬ с ВИДЕО с тремя областями
    private JSplitPane splitMain1 = new JSplitPane(); // создание разделяющейся панели (левая панель / видео + правая панель)
    private JSplitPane splitMain2 = new JSplitPane(); // создание разделяющейся панели(видео / правая панель)

    private JSplitPane createWorkSplitVideoPanel() {
        splitMain1.setDividerSize(4); // ширина разделительной области
        splitMain2.setDividerSize(4); // ширина разделительной области
        splitMain1.setContinuousLayout(true); //  компоненты при перемещении разделительной полосы будут непрерывно обновляться (перерисовываться и, если это сложный компонент, проводить проверку корректности).
        splitMain2.setContinuousLayout(true);
        splitMain1.setResizeWeight(0); // установка приоритета для соотношения размеров панелей при изменении размеров = Правая половина (видео + правая панель) в приоритете
        splitMain2.setResizeWeight(1); // установка приоритета для соотношения размеров панелей при изменении размеров = Левая половина (видео) в приоритете

        // МИНИМАЛЬНЫЙ размер для левой (правой тоже) панели: по ширине = (ширина окна - ширина видео)/3 ; по высоте = такой же, как у видео  
        Dimension leftRightPanelMinimumSize = new Dimension(
                (int) ((this.getMinimumSize().getWidth() - canvas.getMinimumSize().getWidth()) / 3),
                (int) (canvas.getMinimumSize().getHeight()));

        // Создание панели прокрутки для ЛЕВОЙ панели
        JScrollPane leftPanel = new JScrollPane(leftCPanel.createLeftCPanel());
        leftPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
        leftPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели        

        // Создание панели прокрутки для ПРАВОЙ панели (видео + еще одна панель)
        JScrollPane rightPanel = new JScrollPane(rightCPanel.createRightCPanel());
        rightPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
        rightPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели

        // Добавление компонент в разделяющуюся панель
        splitMain1.setLeftComponent(leftPanel); // левая панель
        splitMain1.setRightComponent(splitMain2); // в качестве парвой панели - еще две панели
        splitMain2.setLeftComponent(addVideoPanel.AddVideo()); // панель с кнопкой добавления видео
        splitMain2.setRightComponent(rightPanel); // правая конфигурационная панель

        // Добавление слушателей изменения положения разделительной линии (для правильного отображения overlay слоя (если он включен)
        leftPanel.addComponentListener(dividerChange);
        rightPanel.addComponentListener(dividerChange);

        return splitMain1;
    }

    // ОСНОВНАЯ РАБОЧАЯ ПАНЕЛЬ с Таблицей 
    private JSplitPane createWorkSplitTablePanel(JComponent leftComponent, JComponent rightComponent) {
        JSplitPane splitMain1 = new JSplitPane(); // создание разделяющейся панели (левая панель / видео + правая панель)
        JSplitPane splitMain2 = new JSplitPane(); // создание разделяющейся панели(видео / правая панель)
        splitMain1.setDividerSize(4); // ширина разделительной области
        splitMain2.setDividerSize(4); // ширина разделительной области
        splitMain1.setContinuousLayout(true); //  компоненты при перемещении разделительной полосы будут непрерывно обновляться (перерисовываться и, если это сложный компонент, проводить проверку корректности).
        splitMain2.setContinuousLayout(true);
        splitMain1.setResizeWeight(0); // установка приоритета для соотношения размеров панелей при изменении размеров = Правая половина (видео + правая панель) в приоритете
        splitMain2.setResizeWeight(1); // установка приоритета для соотношения размеров панелей при изменении размеров = Левая половина (видео) в приоритете

        // Размер для левой (правой тоже) панели: по ширине = (ширина окна - ширина видео)/3 ; по высоте = такой же, как у видео  
        Dimension leftRightPanelMinimumSize = new Dimension(
                (int) ((this.getMinimumSize().getWidth() - canvas.getMinimumSize().getWidth()) / 3),
                (int) (canvas.getMinimumSize().getHeight()));

        // Создание панели прокрутки для ЛЕВОЙ панели
        JScrollPane leftPanel = new JScrollPane(leftComponent);
        leftPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши (стандартно включена)
        leftPanel.setPreferredSize(new Dimension((int) (leftRightPanelMinimumSize.getWidth() * 3.5), (int) this.getHeight())); // Предпочтительный размер (при создании панели)
        leftPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели        

        // Создание панели прокрутки для ПРАВОЙ панели (видео + еще одна панель)
        JScrollPane rightPanel = new JScrollPane(rightComponent);
        rightPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
//        rightPanel.setPreferredSize(leftRightPanelMinimumSize); // Предпочтительный размер (при создании панели)
        rightPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели

        // Добавление компонент в разделяющуюся панель
        splitMain1.setLeftComponent(leftPanel); // левая конфигурационная панель
        splitMain1.setRightComponent(splitMain2);
        splitMain2.setLeftComponent(createTable()); // добавляем таблицу 
        splitMain2.setRightComponent(rightPanel); // правая конфигурационная панель (если не нужна, то удобно сделать её null

        // Добавление слушателей изменения положения разделительной линии (для правильного отображения overlay слоя (если он включен)
        leftPanel.addComponentListener(dividerChange);
        rightPanel.addComponentListener(dividerChange);

        return splitMain1;
    }

    // ПАНЕЛЬ ВКЛАДОК
    private JTabbedPane videoTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

    private JTabbedPane createTabbedPane() {
        // Вкладка 1 (index = 0). Видео с панелями настроек
        videoTabs.addTab("Видео панель", createWorkSplitVideoPanel());

        // Вкладка 2 (index = 1). Таблица данных
        // на вкладку таблицы добавляем левую панель с настройками и правую панель 
        CreateConfigurationPanel configPanel = new CreateConfigurationPanel();
        videoTabs.addTab("Таблица результатов подсчета", createWorkSplitTablePanel(configPanel.CreateConfigurationPanel(), new JLabel("Панель")));

        // Подключение слушателя мыши
        videoTabs.addMouseListener(new MouseAdapter() {
            boolean overlayState = false; // "флаг" для отслеживания того, был ли включен overlay (кнопки) на панели с видео

            @Override
            public void mousePressed(MouseEvent e) {
                if (((JTabbedPane) e.getSource()).getSelectedIndex() == 0) {
                    canvas.requestFocus(); // переключаем фокус на canvas
                    // если панель кнопок (overlay) отображалась до перехода на вкладку таблицы (или другую вкладку), то показываем панель кнопок (overlay)
                    if (overlayState == true) {
                        emp.mediaPlayer().overlay().enable(true);
                    }
                }
                if (((JTabbedPane) e.getSource()).getSelectedIndex() == 1) {
                    // если панель кнопок (overlay) отображается, то скрываем её (overlay)
                    if (emp.mediaPlayer().overlay().enabled()) { // если overlay виден (остался виден после панели с видео), то
                        emp.mediaPlayer().overlay().enable(false); // убираем видимость панели кнопок (overlay)
                        overlayState = true; // переключаем "флаг", который говорит о том, что панель с кнопками (overlay) была видна и при переходе на панель с видео её снова стоит показать
                    } else {
                        overlayState = false; // если панель с кнпоками (overlay) не видна (не была включена на панели с видео, то состояние "флага" не меняем - что говорит о том, что не нужно показывать панель кнопок (overlay) при переходе на панель видео (или другую)
                    }
                    // Если видео проигрывается и происходит переключение на вкладку с Таблицей, то видео ставится на паузу
                    emp.mediaPlayer().controls().setPause(true);
                }
                canvas.requestFocus();
            }

        });

        return videoTabs;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Старый вариант. Не используется
    private JComponent createConfigurationTable() {

        // Таблица
        ArrayList<TableCellEditor> editors = new ArrayList<TableCellEditor>();

        // Создаем редакторов для каждой строки свой
        String[] items1 = {"1", "2", "3", "4", "5", "6"};
        JComboBox comboBox1 = new JComboBox(items1);
        DefaultCellEditor dce1 = new DefaultCellEditor(comboBox1);

        editors.add(dce1);

        String[] items2 = {"Circle", "Square", "Triangle"};
        JComboBox comboBox2 = new JComboBox(items2);
        DefaultCellEditor dce2 = new DefaultCellEditor(comboBox2);
        editors.add(dce2);

        ////////////////////////////////////////////
//        CheckableItem[] m = {
//            new CheckableItem("Легковые", false),
//            new CheckableItem("Автобусы", true),
//            new CheckableItem("Автопоезда < 8т.", false),
//            new CheckableItem("Автопоезда > 8т.", true),
//            new CheckableItem("Грузовые < 2т.", true),
//            new CheckableItem("Грузовые 2 - 5т.", false),
//            new CheckableItem("Грузовые 5 - 8т.", true),
//            new CheckableItem("Грузовые > 8т.", true),
//            new CheckableItem("Все", false)
//        };
//
//        JComboBox cBox = new CheckedComboBox<>(new DefaultComboBoxModel<>(m));
//        DefaultCellEditor dce3 = new DefaultCellEditor(cBox);
//        editors.add(dce3);
        ////////////////////////////////////////////
        //  СОЗДАНИЕ ТАБЛИЦЫ со стандартными данными
        Object[][] data
                = {
                    {"Количество направлений подсчета:", "4"},
                    {"Shape", "Square"},
                    {"Тип автомобилей", "Все"}
                };
        String[] columnNames = {"Наименование", "Параметр"}; // Название столбцов
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

// Создание таблицы на основании МОДЕЛИ данных, созданной ранее. 
        // Здесь же добавляются comboBox для каждой строки свой
        JTable tableda = new JTable(model) {
            //  Determine editor to be used by row
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);

                if (modelColumn == 1 && row < 3) {
                    return editors.get(row);
                } else {
                    return super.getCellEditor(row, column);
                }
            }
        };
        /////////////////////////////////////////////////////////////////

        return tableda;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ТАБЛИЦА данных 
    // пытаемся инициализировать переменную класса Модифицированной Таблицы (пытаемся - потому что могут быть исключения)
    private JScrollPane createTable() {
        try {
            table = overlay.getTableModel().doTable();
        } catch (Exception ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table.getScrollPane();
    }

    // ВСПЛЫВАЮЩЯЯ ПАНЕЛЬ. Метод включения и конфигурации с текстом = путь к файлу (реализовать отображение только названия)
    private void setMarquee(String filePath) {
        int index = filePath.lastIndexOf("\\") + 1; // из пути к файлу получаем индекс, после которого будет только название файла
        emp.mediaPlayer().marquee().setText(filePath.substring(index)); // получаем название файла из пути к нему
        emp.mediaPlayer().marquee().setSize(30);
        emp.mediaPlayer().marquee().setColour(Color.WHITE);
        emp.mediaPlayer().marquee().setTimeout(3000);
        emp.mediaPlayer().marquee().setPosition(MarqueePosition.BOTTOM);
        emp.mediaPlayer().marquee().setOpacity(0.8f);
        emp.mediaPlayer().marquee().enable(true);
    }

    // ТАЙМЕР для реализации линии времени видео
    private void timer() {
        // Создание таймера с задержкой в начале работы (10 мс) и Action слушателем
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nowVideoTime = (int) (emp.mediaPlayer().status().position() * emp.mediaPlayer().media().info().duration() / 1000); // какая СЕКУНДА (для перевода в миллисекунды убрать "/1000") видео в данный момент
                int duration = (int) (emp.mediaPlayer().media().info().duration() / 1000); // продолжительность видео в СЕКУНДАХ (для перевода в миллисекунды убрать "/1000")

                vPCPanel.getJs().setMaximum(duration); // установка максимального значения ползунка (панели времени) - для правильного разбиения на СЕКУНДЫ
                vPCPanel.getJs().setValue(nowVideoTime); // В качестве значения маркера устанавливается нынешнее значение времени видео в секундах
                // Установка нынешнего значения времени около слайдера (ползунка)
                vPCPanel.getTimeLabel().setText(String.format(
                        " %02d:%02d:%02d ", // шаблон для отображения времени (02 - два знака)
                        nowVideoTime / 3600, // часы
                        nowVideoTime / 60 % 60, // минуты (с шаблонов изменения следующей цифры (часов) = 60 (60 минут в часе))
                        nowVideoTime % 60)); // секунды (с шаблонов изменения следующей цифры (минут) = 60 (60 секунд в минуте))
                // Установка общей продолжительности видео около слайдера (ползунка)
                vPCPanel.getDurationLabel().setText(String.format(
                        " %02d:%02d:%02d ",
                        duration / 3600,
                        duration / 60 % 60,
                        duration % 60));
                // Установка слушателя для изменения позиции (времени) видео в зависимости от изменения положения слайдера (ползунка)
                vPCPanel.getJs().addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        emp.mediaPlayer().controls().setPosition((float) ((JSlider) e.getComponent()).getValue() / (duration)); // в качестве позиции (времени) видео устанавливается значение от ползунка
                        canvas.requestFocus();
                    }
                });
            }
        });
        timer.start(); // запуск таймера
    }

}
