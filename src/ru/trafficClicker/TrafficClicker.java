package ru.trafficClicker;

import resources.FileChooserRus;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.quinto.swing.table.view.JBroTable;
import org.xml.sax.SAXException;
import ru.AddVideoPanel;
import ru.CreateLeftControlPanel;
import ru.CreateMenuBar;
import ru.CreatePopupVideoMenu;
import ru.CreateRightControlPanel;
import ru.CreateVideoPlayerControlPanel;
import ru.videoOpen;
import ru.overlay.Overlay;
import ru.AbstractFrame;
import ru.cartogram.AddCartogramPanel;
import ru.cartogram.CreateConfigurationPanelCartogram;

import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class TrafficClicker extends AbstractFrame {
// ПОХОЖЕ, ЧТО ПРОБЛЕМА java.lang.stackoverflow java.awt.awteventmulticaster.mousemoved вызвана Component Mover !!! (двигает панель с кнопками)!!!

    private Overlay overlay; // слой кнопок поверх видео
    private AddVideoPanel addVideoPanel = new AddVideoPanel(); // панель с кнопкой добавления видео
    private AddTablePanel addTablePanel = new AddTablePanel(); // панель с кнопкой добавления таблицы
    // ТАБЛИЦЫ
    // Утро
    private JBroTable table15Morning = new JBroTable();
    private JBroTable table30Morning = new JBroTable();
    private JBroTable table45Morning = new JBroTable();
    private JBroTable table60Morning = new JBroTable();
    private JBroTable tableSumMorning = new JBroTable();
    // Обед
    private JBroTable table15Day = new JBroTable();
    private JBroTable table30Day = new JBroTable();
    private JBroTable table45Day = new JBroTable();
    private JBroTable table60Day = new JBroTable();
    private JBroTable tableSumDay = new JBroTable();
    // Вечер
    private JBroTable table15Evening = new JBroTable();
    private JBroTable table30Evening = new JBroTable();
    private JBroTable table45Evening = new JBroTable();
    private JBroTable table60Evening = new JBroTable();
    private JBroTable tableSumEvening = new JBroTable();
    // Ссылки на перемещатели панелей с кнопками
    ComponentMover componentMoverUp;
    ComponentMover componentMoverLeft;
    ComponentMover componentMoverDown;
    ComponentMover componentMoverRight;

    private Canvas canvas = new Canvas(); // подоснова для видео
    private CreateConfigurationPanel configurationPanel; // инициализация панели для конфигурации таблицы подсчета
    private CreateConfigurationPanelCartogram configPanelCartogram;

    private String filePath; // переменная для хранения полного пути к Видео

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

    private Settings settings = new Settings(); // класс работы с файлом настроек;
    private SettingsFrame settingsFrame = new SettingsFrame(settings); // окно настроек

    // Что происходит при создании окна
    @Override
    protected void onCreate() {
        // Установка Windows Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
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

        // Добавление панелейю configurationPanel создается внутри createdTabbedPane()
        add(vPCPanel.createVPCPanel(), BorderLayout.SOUTH); // Контрольная панель видеоплеера (снизу)
        try {
            add(createTabbedPane(), BorderLayout.CENTER); // панель вкладок, внутри которой есть панели с разделением на области (SplitPanel) - видео, таблица
            // Подключаем слушателей радио кнопок выбора нужной таблицы для подсчета
            configurationPanel.getMorningRadio().addActionListener(this::onRadioButtonClick);
            configurationPanel.getDayRadio().addActionListener(this::onRadioButtonClick);
            configurationPanel.getEveningRadio().addActionListener(this::onRadioButtonClick);

            configurationPanel.getTable0_15Radio().addActionListener(this::onRadioButtonClick);
            configurationPanel.getTable15_30Radio().addActionListener(this::onRadioButtonClick);
            configurationPanel.getTable30_45Radio().addActionListener(this::onRadioButtonClick);
            configurationPanel.getTable45_60Radio().addActionListener(this::onRadioButtonClick);
        } catch (IOException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        }

// Добавление СЛУШАТЕЛЕЙ СОБЫТИЙ. В случае нажатия на кнопку - вызывается метод в скобках. Создается объект нужного типа в котором вызывается метод данного типа. :: - ссылка на метод.
        // Слушатели панели управления видео:
        vPCPanel.getB1().addActionListener(this::onStopButtonClick); // Добавление слушателя действия для кнопки B1
        vPCPanel.getB2().addActionListener(this::onPlayPauseButtonClick); // Добавление слушателя действия для кнопки B2

        // Слушатели на видео поверхность
        canvas.addMouseListener(videoMouseClick); // Добавление слушателя событий к поверхности плеера (подоснове для видео) - то есть к самому видео
        canvas.addKeyListener(keyVideoTabeAdapter); // Добавление слушателя для действий клавиатуры

        canvas.addMouseWheelListener(wheelSound);  // Слушатель изменения звука
        // Слушатели КНОПОК В БАРЕ (MenuBar)
        jBar.getFileItem2().addActionListener(configurationPanel::onOpenProjectButtonClick); // Слушатель открытия проекта
        jBar.getFileItem3().addActionListener(configurationPanel::onCreateProjectButtonClick); // Слушатель Создание проекта
        jBar.getFileItem6().addActionListener(this::onAddVideoButtonClick); // Слушатель кнопки "Выбор видео" в Баре
        jBar.getViewItem3().addActionListener(this::onFullScreenButtonClick); // Слушатель кнопки "Полноэкранного режима" в Баре
        jBar.getFileItem4().addActionListener(configurationPanel::onSaveButtonClick); // Слушатель кнопки "Сохранить"
        jBar.getFileItem5().addActionListener(configurationPanel::onSaveAsButtonClick); // Слушатель кнопки "Сохранить как..."
        jBar.getToolsItem1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.setFrameVisible(true);
            }
        });

        // Слушатели КНОПОК ВО ВСПЛЫВАЮЩЕЙ ПАНЕЛИ НА ВИДЕО (MenuBar)
        popupMenu.getMenuItem4().addActionListener(this::onPlayPauseButtonClick);
        popupMenu.getMenuItem1().addActionListener(this::onAddVideoButtonClick);
        popupMenu.getMenuItem5().addActionListener(this::onFullScreenButtonClick); // Слушатель кнопки "Полноэкранного режима"
        popupMenu.getMenuItem7().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Стандартная скорость воспроизведения
                emp.mediaPlayer().controls().setRate(1.0f);
                setMarquee("Скорость воспроизведения: " + fmt(emp.mediaPlayer().status().rate()) + "x", 20, MarqueePosition.TOP_LEFT);
            }
        });
        popupMenu.getMenuItem8().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Уменьшение скорости воспроизведения на 10%. Минимум 0.2х
                if (emp.mediaPlayer().status().rate() >= 0.2) {
                    emp.mediaPlayer().controls().setRate(emp.mediaPlayer().status().rate() - 0.1f);
                    setMarquee("Скорость воспроизведения: " + fmt(emp.mediaPlayer().status().rate()) + "x", 20, MarqueePosition.TOP_LEFT);
                }
            }
        });
        popupMenu.getMenuItem9().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Увеличение скорости воспроизведения на 10%. Максимум 12.0х
                if (emp.mediaPlayer().status().rate() <= 12) {
                    emp.mediaPlayer().controls().setRate(emp.mediaPlayer().status().rate() + 0.1f);
                    setMarquee("Скорость воспроизведения: " + fmt(emp.mediaPlayer().status().rate()) + "x", 20, MarqueePosition.TOP_LEFT);
                }
            }
        });

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

        // Слушатель изменения полного имени файла. На этом реализован слушатель того, что нужно сконфигурировать новую таблицу и слой overlay.
        // Все параметры для таблицы получаем из configurationPanel.
        configurationPanel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Если fileSave был открыт, но файл не был сохранен, то новое значение становится null - новая таблица не создается
                // Если имя не null (изменено или осталось прежним (для этого некоторые махинации проводим внутри configurationPanel)), то делаем, что нужно
                if (evt.getPropertyName().equalsIgnoreCase("fullFileName") && evt.getNewValue() != null) {
                    try {
                        doOverlayAndGetTable();
                    } catch (IOException ex) {
                        Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // Если поменяли картограммы (файлы картограммы) - например Сохранили как, то приходит уведомление и мы пытаемся обновить картограммы на панели
                if (evt.getPropertyName().equalsIgnoreCase("cartogramChange") && evt.getNewValue() != null) {
                    updateCartogramPanels();
                }
                // Если поменяли картограммы (файлы картограммы) - например Сохранили как, то приходит уведомление и мы пытаемся обновить картограммы на панели
                if (evt.getPropertyName().equalsIgnoreCase("openProjectDoCartogramConfigPanel") && evt.getNewValue() != null) {
                    configPanelCartogram.setValuesOnConfigPanelFromCartogram(configurationPanel.getCartogramMorning(), "Morning");
                    configPanelCartogram.setValuesOnConfigPanelFromCartogram(configurationPanel.getCartogramDay(), "Day");
                    configPanelCartogram.setValuesOnConfigPanelFromCartogram(configurationPanel.getCartogramEvening(), "Evening");
                }
            }
        });

        // Инициализация Настроек
        try {
            settings.writeFirstDocument(); // Записываем первоначальный файл настроек в папку пользователя (если он уже создан, то он перезаписывается на себя же)
        } catch (TransformerFactoryConfigurationError | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        setMarqueeStart(filePath);
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
                splitMain2Tab1.setLeftComponent(canvas); // на вкладку видео панели добавляем наше видео (canvas - подоснова для видео, на ней далее будет отображено видео)
                // Подготавливаем новое видео         
                prepareVideo(filePath);
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
            if (e.getKeyCode() == e.VK_SPACE) {
                actionPlayPause();
            }
            // F11 - отображение кнопок на canvas (видео)
            if (e.getKeyCode() == e.VK_F11) {
                // активация слоя overlay
                emp.mediaPlayer().overlay().enable(!emp.mediaPlayer().overlay().enabled()); // если overlay неактивен, то активировать и наоборот
                canvas.requestFocus(); // Установка фокуса на canvas
            }
// СКОРОСТЬ ВОСПРОИЗВЕДЕНИЯ
            // Установка скорости воспроизведения в нормальное сосояние
            if ((e.getKeyCode() == e.VK_Z)) {
                emp.mediaPlayer().controls().setRate(1.0f);
                setMarquee("Скорость воспроизведения: " + fmt(emp.mediaPlayer().status().rate()) + "x", 20, MarqueePosition.TOP_LEFT);
            }
            // Уменьшение скорости воспроизведения на 10%. Минимум 0.2х
            if ((e.getKeyCode() == e.VK_X)) {
                if (emp.mediaPlayer().status().rate() >= 0.2) {
                    emp.mediaPlayer().controls().setRate(emp.mediaPlayer().status().rate() - 0.1f);
                    setMarquee("Скорость воспроизведения: " + fmt(emp.mediaPlayer().status().rate()) + "x", 20, MarqueePosition.TOP_LEFT);
                }
            }
            // Увеличение скорости воспроизведения на 10%. Максимум 12.0х
            if ((e.getKeyCode() == e.VK_C)) {
                if (emp.mediaPlayer().status().rate() <= 12) {
                    emp.mediaPlayer().controls().setRate(emp.mediaPlayer().status().rate() + 0.1f);
                    setMarquee("Скорость воспроизведения: " + fmt(emp.mediaPlayer().status().rate()) + "x", 20, MarqueePosition.TOP_LEFT);
                }
            }
// ПРОЛИСТЫВАНИЕ ВИДЕО
            // Переход по видео ВЛЕВО на 1 секунду
            int skipTime = 5;
            if ((e.getKeyCode() == e.VK_LEFT)) {
                emp.mediaPlayer().controls().skipTime(-skipTime * 1000);
                setMarquee("- " + skipTime + " сек.", 20, MarqueePosition.BOTTOM_LEFT);
            }
            // Переход по видео ВПРАВО на 1 секунду
            if ((e.getKeyCode() == e.VK_RIGHT)) {
                emp.mediaPlayer().controls().skipTime(skipTime * 1000);
                setMarquee("+ " + skipTime + " сек.", 20, MarqueePosition.BOTTOM_LEFT);
            }
// УБРАТЬ ЗВУК
            if ((e.getKeyCode() == e.VK_M)) {
                if (!emp.mediaPlayer().audio().isMute()) { // если звук выключен, то
                    emp.mediaPlayer().audio().setMute(true); // выключаем
                    setMarquee("Звук выключен", 20, MarqueePosition.BOTTOM);
                }
                if (emp.mediaPlayer().audio().isMute()) {
                    emp.mediaPlayer().audio().setMute(false); // если выключен - включаем
                    setMarquee("Звук включен", 20, MarqueePosition.BOTTOM);
                }
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
            // Чтобы при изменении размера окна фокус переходил на canvas (видео). Если этого не делать, то фокус переходит на overlay
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
            if (overlay != null) {
                emp.mediaPlayer().overlay().set(overlay);
                emp.mediaPlayer().overlay().enable(false);
            }
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
                } else {
                    if (emp.mediaPlayer().audio().volume() > 200) {
                        emp.mediaPlayer().audio().setVolume(200);
                    }
                }
            }
            // Отображение громкости. БЫЛА ПРОБЛЕМА С ОТОБРАЖЕНИЕМ "%" !нужно его экранировать!
            setMarquee("Громкость: " + String.valueOf(emp.mediaPlayer().audio().volume()) + " %%", 20, MarqueePosition.BOTTOM_RIGHT);
        }
    };

    // ЗАКРЫТИЕ ОКНА (освобождение ресурсов)
    WindowAdapter winAdapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            emp.mediaPlayer().release();
            emp.mediaPlayerFactory().release();
            if (overlay != null) {
                overlay.dispose();
            }
        }
    };

    // ПЕРЕХОД (дополнительный) ФОКУСА на canvas (видео), если фокус перешел на canvas (видео).
    // Отображаем или нет кнопки над видео
    private FocusAdapter canvasFocusAdapter = new FocusAdapter() {
        boolean overlayState = false; // "флаг" для отслеживания того, был ли включен overlay (кнопки) на панели с видео

        @Override
        public void focusGained(FocusEvent e) {
            canvas.requestFocus();
            if (overlayState == true) {
                emp.mediaPlayer().overlay().enable(true);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (!emp.mediaPlayer().overlay().enabled()) { // если overlay не виден, то
                overlayState = false; // говорим, что он не виден
            }
            if (emp.mediaPlayer().overlay().enabled()) { // если overlay виден (остался виден после панели с видео), то
                emp.mediaPlayer().overlay().enable(false); // убираем видимость панели кнопок (overlay)
                overlayState = true; // переключаем "флаг", который говорит о том, что панель с кнопками (overlay) была видна и при переходе на панель с видео её снова стоит показать
            }
        }

    };

    // РАЗМЕЩЕНИЕ КНОПОК НА ВИДЕО ПОВЕРХНОСТИ (на overlay) с привязкой к canvas (видео)
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
    private JSplitPane splitMain1Tab1 = new JSplitPane(); // создание разделяющейся панели (левая панель / видео + правая панель)
    private JSplitPane splitMain2Tab1 = new JSplitPane(); // создание разделяющейся панели(видео / правая панель)

    private JSplitPane createWorkSplitVideoPanel() {
        splitMain1Tab1.setDividerSize(4); // ширина разделительной области
        splitMain2Tab1.setDividerSize(4); // ширина разделительной области
        splitMain1Tab1.setContinuousLayout(true); //  компоненты при перемещении разделительной полосы будут непрерывно обновляться (перерисовываться и, если это сложный компонент, проводить проверку корректности).
        splitMain2Tab1.setContinuousLayout(true);
        splitMain1Tab1.setResizeWeight(0); // установка приоритета для соотношения размеров панелей при изменении размеров = Правая половина (видео + правая панель) в приоритете
        splitMain2Tab1.setResizeWeight(1); // установка приоритета для соотношения размеров панелей при изменении размеров = Левая половина (видео) в приоритете

        // МИНИМАЛЬНЫЙ размер для левой (правой тоже) панели: по ширине = (ширина окна - ширина видео)/3 ; по высоте = такой же, как у видео  
        Dimension leftRightPanelMinimumSize = new Dimension(
                (int) ((this.getMinimumSize().getWidth() - canvas.getMinimumSize().getWidth()) / 3),
                (int) (canvas.getMinimumSize().getHeight()));

        // Создание панели прокрутки для ЛЕВОЙ панели
        JScrollPane leftPanel = new JScrollPane(leftCPanel.createLeftCPanel());
        leftPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
        leftPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели        

        // Создание панели прокрутки для ПРАВОЙ панели (видео + еще одна панель)
//        JScrollPane rightPanel = new JScrollPane(rightCPanel.createRightCPanel());
//        rightPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
//        rightPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели
        // Добавление компонент в разделяющуюся панель
        splitMain1Tab1.setLeftComponent(null); // левая панель
        splitMain1Tab1.setRightComponent(splitMain2Tab1); // в качестве парвой панели - еще две панели
        splitMain2Tab1.setLeftComponent(addVideoPanel.AddVideo()); // панель с кнопкой добавления видео
        splitMain2Tab1.setRightComponent(null); // правая конфигурационная панель. Если не нужна - ставим null

        // Добавление слушателей изменения положения разделительной линии (для правильного отображения overlay слоя (если он включен)
        leftPanel.addComponentListener(dividerChange);
//        rightPanel.addComponentListener(dividerChange);

        return splitMain1Tab1;
    }

    // ОСНОВНАЯ РАБОЧАЯ ПАНЕЛЬ с Таблицей 
    JSplitPane splitMain1Tab2 = new JSplitPane(); // создание разделяющейся панели (левая панель / видео + правая панель)
    JSplitPane splitMain2Tab2 = new JSplitPane(); // создание разделяющейся панели(видео / правая панель)
    JScrollPane rightPanel;

    private JSplitPane createWorkSplitTablePanel(JComponent leftComponent, JComponent centerComponent, JComponent rightComponent) {
        splitMain1Tab2.setDividerSize(4); // ширина разделительной области
        splitMain2Tab2.setDividerSize(4); // ширина разделительной области
        splitMain1Tab2.setContinuousLayout(true); //  компоненты при перемещении разделительной полосы будут непрерывно обновляться (перерисовываться и, если это сложный компонент, проводить проверку корректности).
        splitMain2Tab2.setContinuousLayout(true);
        splitMain1Tab2.setResizeWeight(0); // установка приоритета для соотношения размеров панелей при изменении размеров = Правая половина (видео + правая панель) в приоритете
        splitMain2Tab2.setResizeWeight(1); // установка приоритета для соотношения размеров панелей при изменении размеров = Левая половина (видео) в приоритете

        // Размер для левой (правой тоже) панели: по ширине = (ширина окна - ширина видео)/3 ; по высоте = такой же, как у видео  
        Dimension leftRightPanelMinimumSize = new Dimension(
                (int) ((this.getMinimumSize().getWidth() - canvas.getMinimumSize().getWidth()) / 3),
                (int) (canvas.getMinimumSize().getHeight()));

        // Создание панели прокрутки для ЛЕВОЙ панели
        JScrollPane leftPanel = new JScrollPane(leftComponent);
        leftPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши (стандартно включена)
        leftPanel.setPreferredSize(new Dimension((int) (leftRightPanelMinimumSize.getWidth() * 2.7), (int) this.getHeight())); // Предпочтительный размер (при создании панели)
        leftPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели        

        // Создание панели прокрутки для ПРАВОЙ панели (видео + еще одна панель)
        rightPanel = new JScrollPane(rightComponent);
        rightPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
        rightPanel.setPreferredSize(new Dimension((int) (leftRightPanelMinimumSize.getWidth() * 1.4), (int) this.getHeight())); // Предпочтительный размер (при создании панели)
        rightPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели

        // Добавление компонент в разделяющуюся панель
        splitMain1Tab2.setLeftComponent(leftPanel); // левая конфигурационная панель
        splitMain1Tab2.setRightComponent(splitMain2Tab2);
        splitMain2Tab2.setLeftComponent(centerComponent); // добавляем таблицу 
        splitMain2Tab2.setRightComponent(null); // правая конфигурационная панель (если не нужна, то удобно сделать её null

        return splitMain1Tab2;
    }

    // ПАНЕЛЬ ВКЛАДОК
    private JTabbedPane videoTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

    // Панель вкладок с картограммами
    private TabbedPaneCartogram cartogramPaneLink = new TabbedPaneCartogram();
    private JTabbedPane cartogramPane;
    // Разделенные панели с картограмми и панелями управления ими
    private SplitCartogramPanel cartogramPanel = new SplitCartogramPanel();

    private JTabbedPane createTabbedPane() throws IOException {
        // Вкладка 1 (index = 0). Видео с панелями настроек
        videoTabs.addTab("Видео панель", createWorkSplitVideoPanel());

        // Вкладка 2 (index = 1). Таблица данных
        // на вкладку таблицы добавляем левую панель с настройками и правую панель 
        configurationPanel = new CreateConfigurationPanel();
        videoTabs.addTab("Таблица результатов подсчета", createWorkSplitTablePanel(configurationPanel.CreateLeftConfigurationPanel(), addTablePanel.AddTable(), configurationPanel.CreateRightConfigurationPanel()));

        // Вкладка 3 (index = 2). Картограмма
        cartogramPane = cartogramPaneLink.createTabbedPane(
                new AddCartogramPanel().AddMap(),
                new AddCartogramPanel().AddMap(),
                new AddCartogramPanel().AddMap()); // инициализируем панель вкладок, на которую помещаем разделенную панель с картограммой и панелбю управления ею
        videoTabs.addTab("Картограмма", cartogramPanel.createCartogramSplitPanel(new JLabel("Панель"), cartogramPane, new JLabel("Панель")));

        // Подключение слушателя мыши
        videoTabs.setFocusable(false);

        // При изменении вкладки перекидываем фокус куда нужно
        videoTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (videoTabs.getSelectedIndex() == 0) {
                    canvas.requestFocus(); // переключаем фокус на canvas
                }
                if (videoTabs.getSelectedIndex() == 1) {
                    splitMain2Tab2.getLeftComponent().requestFocus(); // переключаем фокус на canvas
                }
                if (videoTabs.getSelectedIndex() == 2) {
                    // обновляем SVGCanvas
                    if (configurationPanel.getCartogramMorning() != null) {
                        // Делаем паузу для потока на Х мс перед обновлением SVGCanvas (перед каждым сохранением)
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        configurationPanel.getCartogramMorning().saveChangeValue(); // обновляем SVGCanvas таким образом (для предотвращения смещения картинки в сторону при ее добавлении)
                    }
                    if (configurationPanel.getCartogramDay() != null) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        configurationPanel.getCartogramDay().saveChangeValue(); // обновляем SVGCanvas таким образом (для предотвращения смещения картинки в сторону при ее добавлении)
                    }
                    if (configurationPanel.getCartogramEvening() != null) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        configurationPanel.getCartogramEvening().saveChangeValue(); // обновляем SVGCanvas таким образом (для предотвращения смещения картинки в сторону при ее добавлении)
                    }
                    cartogramPane.getSelectedComponent().requestFocus(); // переключаем фокус на canvas
                }
            }
        });

        return videoTabs;
    }

    // ВСПЛЫВАЮЩЯЯ ПАНЕЛЬ. Метод включения и конфигурации с текстом = путь к файлу (реализовать отображение только названия)
    private void setMarqueeStart(String filePath) {
        int index = filePath.lastIndexOf("\\") + 1; // из пути к файлу получаем индекс, после которого будет только название файла
        emp.mediaPlayer().marquee().setText(filePath.substring(index)); // получаем название файла из пути к нему
        emp.mediaPlayer().marquee().setSize(30);
        emp.mediaPlayer().marquee().setColour(Color.WHITE);
        emp.mediaPlayer().marquee().setTimeout(3000);
        emp.mediaPlayer().marquee().setPosition(MarqueePosition.BOTTOM);
        emp.mediaPlayer().marquee().setOpacity(0.8f);
        emp.mediaPlayer().marquee().enable(true);
    }

    private void setMarquee(String text, int size, MarqueePosition position) {
        emp.mediaPlayer().marquee().setColour(Color.WHITE);
        emp.mediaPlayer().marquee().setTimeout(2000);
        emp.mediaPlayer().marquee().setOpacity(0.8f);

        emp.mediaPlayer().marquee().setText(text); // получаем название файла из пути к нему
        emp.mediaPlayer().marquee().setSize(size);
        emp.mediaPlayer().marquee().setPosition(position);

        emp.mediaPlayer().marquee().enable(true);
    }

    // ТАЙМЕР для реализации линии времени видео. Точно не виноват в ошибки вылета java машины после окончания видео
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
                // Изменение позиции (времени) видео по клику на линию времени
                vPCPanel.getJs().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JSlider sourceSlider = (JSlider) e.getSource(); // получаем Слайдер
                        BasicSliderUI ui = (BasicSliderUI) sourceSlider.getUI(); // получаем UI слайдера
                        int value = ui.valueForXPosition(e.getX()); // получаем значение слайдера по клику
                        vPCPanel.getJs().setValue(value); // устанавливаем новое значение для слайдера
                        emp.mediaPlayer().controls().setPosition((float) value / (duration));
                        canvas.requestFocus();
                    }
                });
            }
        });
        timer.start(); // запуск таймера
    }

    // Во вкладке Таблицы отображаем новую сконфигурированную таблицу (для этого ее сначала получаем и создаем - в другом слушателе). 
    // Добавляем слушатели перемещения панелей кнопок. 
    public void doOverlayAndGetTable() throws IOException {
        // УДАЛЕНИЕ СТАРОГО
        // Уничтожаем предыдущий overlay, если он существовал (для правильного освобождения ресурсов и отображения нового overlay)
        if (overlay != null) {
            overlay.dispose();
            // Удаляем старые слушатели перемещения панелей с кнопками
            deregisterOldListenerMovePanel();
        }

        // СЧИТЫВАНИЕ ПАРАМЕТРОВ НОВОГО. Считываем идентификаторы выбранного режима подсчета
        String kinfOfStatement = configurationPanel.getKindOfStatement(); // получаем вид таблицы - старая или новая
        String typeOfDirection = configurationPanel.getTypeOfDirection(); // получаем количество направлений движения
        TreePath[] paths = configurationPanel.getPaths(); // получаем массив выбранных узлов в дереве выбора того, что считаем
        overlay = new Overlay(this, kinfOfStatement, typeOfDirection, paths, emp); // создаем новый overlay слой кнопок

        // OVERLAY (кнопки)
        // Установка overlay слоя над видео (слой КНОПОК) если видео подгружено на canvas
        if (emp.mediaPlayer().media().isValid()) {
            emp.mediaPlayer().overlay().set(overlay);
            emp.mediaPlayer().overlay().enable(false);
        }
        // Добавление слушателей ПЕРЕМЕЩЕНИ ПАНЕЛЕЙ с кнопками к Лэйблам (направление движения транспорта)
        regiterNewListenerMovePanel();

        // ТАБЛИЦЫ (получение сконфигурированных таблиц)
        getTable();
        // Первоначальная установка радио кнопок при создании таблиц (создании нового проекта подсчета). Для УТРА - общий и первая 15минутка
        configurationPanel.getMorningRadio().doClick();
        configurationPanel.getTable0_15Radio().doClick();
        // Вкладка "таблицы"
        // Добавляем панель со вкладками (каждая из них со своими вкладками) во вкладку "Таблицы" приложения
        splitMain2Tab2.setLeftComponent(getTablePaneAllDay());
        splitMain2Tab2.setRightComponent(rightPanel);

        // КАРТОГРАММЫ. 
        // Вкладка "картограммы"
        // Внутри configurationPanel создаются картограммы
        // Инициализация панели конфигурации картограммы
        configPanelCartogram = new CreateConfigurationPanelCartogram(
                configurationPanel.getCartogramMorning(),
                configurationPanel.getCartogramDay(),
                configurationPanel.getCartogramEvening(),
                typeOfDirection);
        cartogramPanel.getSplitTab1().setLeftComponent(configPanelCartogram.CreateConfigurationPanel()); // Добавляем Панель конфигурации на панель
        cartogramPane.removeAll(); // удаляем ранее добавленные вкладки
        cartogramPane = cartogramPaneLink.createTabbedPane(
                configurationPanel.getCartogramPanelMorning(),
                configurationPanel.getCartogramPanelDay(),
                configurationPanel.getCartogramPanelEvening()
        ); // Добавляем нужные вкладки с картограммами

        // ПРОЧЕЕ
        if (!jBar.getFileItem4().isEnabled()) {
            jBar.getFileItem4().setEnabled(true); // Делаем кнопку "Сохранить" активной
        }
        if (!jBar.getFileItem5().isEnabled()) {
            jBar.getFileItem5().setEnabled(true); // Делаем кнопку "Сохранить" активной
        }

    }

    // Выбор для кнопок, в какую таблицу сохранять данные
    // Возвращает текст выбранной кнопки из группы кнопок
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }

    private void onRadioButtonClick(ActionEvent e) {
        // Слушатель выбора времени Дня (утро, день, вечер)
        if (getSelectedButtonText(configurationPanel.getGroupPeriodOfDay()).equalsIgnoreCase(configurationPanel.getMorningRadio().getText())) {
            // Выбираем 15минутку в выбранном времени дня
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable0_15Radio().getText())) {
                overlay.chooseTable(table15Morning);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable15_30Radio().getText())) {
                overlay.chooseTable(table30Morning);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable30_45Radio().getText())) {
                overlay.chooseTable(table45Morning);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable45_60Radio().getText())) {
                overlay.chooseTable(table60Morning);
            }
        }
        if (getSelectedButtonText(configurationPanel.getGroupPeriodOfDay()).equalsIgnoreCase(configurationPanel.getDayRadio().getText())) {
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable0_15Radio().getText())) {
                overlay.chooseTable(table15Day);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable15_30Radio().getText())) {
                overlay.chooseTable(table30Day);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable30_45Radio().getText())) {
                overlay.chooseTable(table45Day);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable45_60Radio().getText())) {
                overlay.chooseTable(table60Day);
            }
        }
        if (getSelectedButtonText(configurationPanel.getGroupPeriodOfDay()).equalsIgnoreCase(configurationPanel.getEveningRadio().getText())) {
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable0_15Radio().getText())) {
                overlay.chooseTable(table15Evening);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable15_30Radio().getText())) {
                overlay.chooseTable(table30Evening);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable30_45Radio().getText())) {
                overlay.chooseTable(table45Evening);
            }
            if (getSelectedButtonText(configurationPanel.getGroup15MinuteTable()).equalsIgnoreCase(configurationPanel.getTable45_60Radio().getText())) {
                overlay.chooseTable(table60Evening);
            }
        }
    }

    private void updateCartogramPanels() {
        // таким образом обновляем (добавляем новые) панели с картограммами
        cartogramPane.removeAll(); // удаляем ранее добавленные вкладки
        try {
            cartogramPane = cartogramPaneLink.createTabbedPane(
                    configurationPanel.getCartogramPanelMorning(),
                    configurationPanel.getCartogramPanelDay(),
                    configurationPanel.getCartogramPanelEvening());
        } catch (IOException ex) {
            Logger.getLogger(TrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Таким образом ОБНОВЛЯЕМ таблицы (чтобы сработал ModelListener, который перенесет данные из старых таблиц в новые картограммы)
        tableSumMorning.setValueAt(tableSumMorning.getValueAt(0, 0), 0, 0);
        tableSumDay.setValueAt(tableSumDay.getValueAt(0, 0), 0, 0);
        tableSumEvening.setValueAt(tableSumEvening.getValueAt(0, 0), 0, 0);

        // Инициализация панели конфигурации картограммы - данные считываются с загруженной картограммы (предварительно туда переносят из старой)
        configPanelCartogram.setCartogram(configurationPanel.getCartogramMorning(), configurationPanel.getCartogramDay(), configurationPanel.getCartogramEvening());

        // Переносим данные с панели конфигурации для картограммы на картограмму
        configPanelCartogram.setValuesOnCartogramFromConfigPanel(configurationPanel.getCartogramMorning(), "Morning");
        configPanelCartogram.setValuesOnCartogramFromConfigPanel(configurationPanel.getCartogramDay(), "Day");
        configPanelCartogram.setValuesOnCartogramFromConfigPanel(configurationPanel.getCartogramEvening(), "Evening");
    }

    // Получаем таблицы из конфигурационной панели (в которой они создаются)
    private void getTable() {
        // Утро
        tableSumMorning = configurationPanel.getTableSumMorning(); // получаем таблицу (до этого она перестраиваетя в другом слушателе)
        table15Morning = configurationPanel.getTable15Morning();
        table30Morning = configurationPanel.getTable30Morning();
        table45Morning = configurationPanel.getTable45Morning();
        table60Morning = configurationPanel.getTable60Morning();
        // День
        tableSumDay = configurationPanel.getTableSumDay(); // получаем таблицу (до этого она перестраиваетя в другом слушателе)
        table15Day = configurationPanel.getTable15Day();
        table30Day = configurationPanel.getTable30Day();
        table45Day = configurationPanel.getTable45Day();
        table60Day = configurationPanel.getTable60Day();
        // Вечер
        tableSumEvening = configurationPanel.getTableSumEvening(); // получаем таблицу (до этого она перестраиваетя в другом слушателе)
        table15Evening = configurationPanel.getTable15Evening();
        table30Evening = configurationPanel.getTable30Evening();
        table45Evening = configurationPanel.getTable45Evening();
        table60Evening = configurationPanel.getTable60Evening();
    }

    // Формирование и наполнение вкладок Таблиц
    private JTabbedPane getTablePaneAllDay() throws IOException {
        // Утро
        TabbedPaneTableOnePartOfDay tablePaneLinkMorning = new TabbedPaneTableOnePartOfDay(); // Панель вкладок для одного времени дня
        JTabbedPane tablePaneMorning = tablePaneLinkMorning.createTabbedPane(
                table15Morning.getScrollPane(), table30Morning.getScrollPane(), table45Morning.getScrollPane(), table60Morning.getScrollPane(), tableSumMorning.getScrollPane());
        // День
        TabbedPaneTableOnePartOfDay tablePaneLinkDay = new TabbedPaneTableOnePartOfDay(); // Панель вкладок для одного времени дня
        JTabbedPane tablePaneDay = tablePaneLinkDay.createTabbedPane(
                table15Day.getScrollPane(), table30Day.getScrollPane(), table45Day.getScrollPane(), table60Day.getScrollPane(), tableSumDay.getScrollPane());
        // Вечер
        TabbedPaneTableOnePartOfDay tablePaneLinkEvening = new TabbedPaneTableOnePartOfDay(); // Панель вкладок для одного времени дня
        JTabbedPane tablePaneEvening = tablePaneLinkEvening.createTabbedPane(
                table15Evening.getScrollPane(), table30Evening.getScrollPane(), table45Evening.getScrollPane(), table60Evening.getScrollPane(), tableSumEvening.getScrollPane());
        // Весь день
        TabbedPaneTableAllDay tablePaneAllDayLink = new TabbedPaneTableAllDay(); // Панель вкладок для всего дня
        JTabbedPane tablePaneAllDay = tablePaneAllDayLink.createTabbedPane(
                tablePaneMorning, tablePaneDay, tablePaneEvening);

        return tablePaneAllDay;
    }

    // Удаляем старые слушатели перемещения панелей с кноками
    private void deregisterOldListenerMovePanel() {
        if (componentMoverUp != null || componentMoverRight != null || componentMoverDown != null || componentMoverLeft != null) {
            for (JLabel label : overlay.getLabelsUp()) {
                componentMoverUp.deregisterComponent(label);
            }
            for (JLabel label : overlay.getLabelsLeft()) {
                componentMoverLeft.deregisterComponent(label);
            }
            for (JLabel label : overlay.getLabelsDown()) {
                componentMoverDown.deregisterComponent(label);
            }
            for (JLabel label : overlay.getLabelsRight()) {
                componentMoverRight.deregisterComponent(label);
            }
        }
    }

    // Добавление слушателей ПЕРЕМЕЩЕНИ ПАНЕЛЕЙ с кнопками к Лэйблам (направление движения транспорта)
    private void regiterNewListenerMovePanel() {
        for (JLabel label : overlay.getLabelsUp()) {
            componentMoverUp = new ComponentMover(overlay.getPanelUp(), label); // добавление слушателя (что перемещать, на что для этого нажимать)
        }
        for (JLabel label : overlay.getLabelsLeft()) {
            componentMoverLeft = new ComponentMover(overlay.getPanelLeft(), label);
        }
        for (JLabel label : overlay.getLabelsDown()) {
            componentMoverDown = new ComponentMover(overlay.getPanelDown(), label);
        }
        for (JLabel label : overlay.getLabelsRight()) {
            componentMoverRight = new ComponentMover(overlay.getPanelRight(), label);
        }
    }

    // Округление до двух чисел после запятой
    private String fmt(double d) {
        double dFormat = (double) Math.round(d * 100) / 100;
        return String.valueOf(dFormat);
    }

}
