package ru;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import ru.avalon.java.ui.AbstractFrame; // подключенная собственная библиотека
import ru.*;

import javax.swing.*;

import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class ExampleVLC extends AbstractFrame {

    private Overlay overlay = new Overlay(this);

    private Canvas canvas = new Canvas();
    private int oldWidthCanvas;
    private int oldHeightCanvas;
    private int newWidth;
    private int newHeight;

    private String filePath = "../../Video/Project.avi";

    // Создание плеера. Он сам создает mediaPlayerComponent для себя. Сам является панелью JPanel (не нужна canvas)!
    private EmbeddedMediaPlayerComponent emp = new EmbeddedMediaPlayerComponent();

    // Переменная для менюБара
    private final CreateMenuBar jBar = new CreateMenuBar();

    // Переменная для всплывающего меню
    private final CreatePopupMenu popupMenu = new CreatePopupMenu();

    // Переменные для создания Панелей видео плеера
    private CreateVideoPlayerControlPanel vPCPanel = new CreateVideoPlayerControlPanel(); // VideoPlayer Control Panel (Контрольная Панель Видео Плеера)
    private CreateLeftControlPanel leftCPanel = new CreateLeftControlPanel(); // Left Control Panel (Левая Контрольная Панель)
    private CreateRightControlPanel rightCPanel = new CreateRightControlPanel(); // Right Control Panel (Правая Контрольная Панель)

    // Что происходит при создании окна
    @Override
    protected void onCreate() {
        setTitle("VLC Player"); // установка названия окна
        setSize(1000, 650); // установка размером окна
        setMinimumSize(new Dimension(1000, 650));

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
        // Установка слоя над видео
        emp.mediaPlayer().overlay().set(overlay);

        // Добавление всплывающего меню
        emp.setComponentPopupMenu(popupMenu.createPopupMenu());

        // Добавление панелей
        add(vPCPanel.createVPCPanel(), BorderLayout.SOUTH); // Контрольная панель видеоплеера
        add(createWorkSplitPanel(), BorderLayout.CENTER); // Левая панель настроек

// Добавление СЛУШАТЕЛЕЙ СОБЫТИЙ. В случае нажатия на кнопку - вызывается метод в скобках. Создается объект нужного типа в котором вызывается метод данного типа. :: - ссылка на метод.
        // Слушатели кнопок на панели управления видео:
        vPCPanel.getB1().addActionListener(this::onStopButtonClick); // Добавление слушателя действия для кнопки B1
        vPCPanel.getB2().addActionListener(this::onPlayPauseButtonClick); // Добавление слушателя действия для кнопки B2

        // Слушатели на видео поверхность
        canvas.addMouseListener(videoMouseClick); // Добавление слушателя событий к поверхности плеера (подоснове для видео) - то есть к самому видео
        canvas.addKeyListener(keyAdapter); // Добавление слушателя для действий клавиатуры

        canvas.addMouseWheelListener(whellSound);  // Слушатель изменения звука
        rightCPanel.getLabel().setText("volume: " + emp.mediaPlayer().audio().volume() + " %"); // отображение состояния звука

        // Слушатели КНОПОК В БАРЕ (MenuBar)
        jBar.getFileItem5().addActionListener(this::onFileChooserButtonClick); // Слушатель кнопки "Выбор видео" в Баре
        jBar.getViewItem3().addActionListener(this::onFullScreenButtonClick); // Слушатель кнопки "Полноэкранного режима" в Баре

        // Слушатели ПЕРЕМЕЩЕНИЯ КНОПОК по видео (Overlay)
        JButton[] buttons = {
            overlay.getB1(), overlay.getB2(), overlay.getB3(), overlay.getB4(),
            overlay.getB5(), overlay.getB6(), overlay.getB7(), overlay.getB8(),
            overlay.getB9(), overlay.getB10(), overlay.getB11(), overlay.getB12(),
            overlay.getB13(), overlay.getB14(), overlay.getB15(), overlay.getB16()};
        addMoveButton(buttons); // ко всем кнопкам из массива добавляем слушателей

        // Слушатель окна приложения (освобождаем ресурсы при закрывании)
        addWindowListener(winAdapter);

        // Слушатель изменения размера окна
        canvas.addComponentListener(frameSizeAdapter);

        // Слушатель установления фокуса на canvas = если фокус на canvas, то он устанавливает фокус на canvas (почему то при изменении размера это не дает фокусу переходить на overlay, чего и хотелось). Как бы КОСТЫЛЬ
        canvas.addFocusListener(canvasFocusAdapter);

        // ВРЕМЕННЫЙ слушатель количества кликов по кнопке
        overlay.getB2().addMouseListener(sumOfMouseClick);

        // Слушатель событий для видеоДорожки
        emp.mediaPlayer().events().addMediaPlayerEventListener(mpEventAdapter);

        //Подготовка и запуск видео
        prepareVideo(filePath);
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
        vPCPanel.getB2().setText("play");
        canvas.requestFocus(); // установка фокуса на canvas (видео)
    }

    // Нажатие кнопки ПАУЗА/ПЛЭЙ.
    private void onPlayPauseButtonClick(ActionEvent e) {
        if (emp.mediaPlayer().status().isPlaying() == true) { // Если медиа проигрывается (play)
            emp.mediaPlayer().controls().pause(); // то - пауза (pause)
            vPCPanel.getB2().setText("play"); // текст кнопки меняется на Play
            canvas.requestFocus(); // установка фокуса на canvas (видео)
        } else {
            emp.mediaPlayer().controls().play(); // Если медиа не проигрывается, то - play
            vPCPanel.getB2().setText("pause"); // текст кнопки меняется на Play
            canvas.requestFocus(); // установка фокуса на canvas (видео)
        }
    }

    // Нажатие кнопки "Полноэкранный режим" в Меню Баре (Alt+Shift+Enter)
    private void onFullScreenButtonClick(ActionEvent e) {
        emp.mediaPlayer().fullScreen().set(!emp.mediaPlayer().fullScreen().isFullScreen());
        canvas.requestFocus(); // установка фокуса на canvas (видео)
    }

    // Нажатие кнопки "Открыь видео.." в Меню Баре (Alt+Shift+Enter). МОЖНО ДОБАВИТЬ ФИЛЬТР ФАЙЛОВ, и к другим кнопкам добавить действий (сохранение и т.п.)
    private void onFileChooserButtonClick(ActionEvent e) {
        new FileChooserRus(); // Локализация компонентов окна JFileChooser
        JFileChooser fileopen = new JFileChooser(); // создаем объект ВыбораФайлов
        int ret = fileopen.showDialog(null, "Открыть файл"); // показываем диалог с названием "Открыть файл"
        if (ret == JFileChooser.APPROVE_OPTION) { //  
            File file = fileopen.getSelectedFile(); // выбираем этот файл (получаем на него ссылку
            filePath = file.getAbsolutePath(); // берем текстовый абсолютный путь к файлу
            // Подготавливаем новое видео         
            prepareVideo(filePath);
            vPCPanel.getB2().setText("play"); // устанавливаем для кнопки play/pause новое значение
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

    // КОЛИЧЕСТВО КЛИКОВ ЛКМ по кнопке и ВРЕМЕННО: изменение текста (label) в панели СЛЕВА
    private MouseAdapter sumOfMouseClick = new MouseAdapter() {
        int sumClick = 0;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                sumClick = sumClick + 1;
                leftCPanel.getL2().setText("ВЕРХ_Прямо: " + sumClick);
            }
        }
    };

    // ДЕЙСТВИЯ С КЛАВИАТУРОЙ, если фокус на canvas (видео)!
    private KeyAdapter keyAdapter = new KeyAdapter() {
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
                if (canvas.isShowing()) {
                    oldWidthCanvas = canvas.getWidth();
                    oldHeightCanvas = canvas.getHeight();
//                    upButtonOnVideoLayout();
                    downButtonOnVideoLayout();
                    leftButtonOnVideoLayout();
                    rightButtonOnVideoLayout();
                }
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

    // ПЕРЕМЕЩЕНИЕ КНОПОК по панели видео (передается абсрактная кнопка на canvas). 
    // Работает только для ЛЕВОЙ кнопки мыши. Если пытаться другими кнопками мыши - то ничего не происходит и фокус перемещается на видео
    private class MoveButtons extends MouseAdapter {

        private int x; // внутрення координата Х кнопки = место нажатия на кнопку
        private int y; // внутрення координата Y кнопки = место нажатия на кнопку
        private JButton button; // абстрактная кнопка, которая перемещается

        // Конструктор класса (способ передать адрес на кнопку, которую нужно двигать)
        public MoveButtons(JButton button) {
            this.button = button;
        }

        // Сохранение позиции нажатия мышкой на кнопку
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) { // Проверяем, что нажата левая кнопка мыши
                x = e.getX(); // координата Х нажатия клавишей мыши 
                y = e.getY(); // координата Y нажатия клавишей мыши 
            } else {
                canvas.requestFocus();
            }
        }

        // Установка ограничений для невозможности выноса кнопки за область видео (дополнительные меры). Отпускание кнопки мыши
        @Override
        public void mouseReleased(MouseEvent e
        ) {
            if (SwingUtilities.isLeftMouseButton(e)) { // Проверяем, что нажата левая кнопка мыши
                int xButton = button.getX();
                int yButton = button.getY();

                if (xButton < 0) {
                    button.setLocation(0, yButton);
                } else {
                    if (xButton > canvas.getWidth() - button.getWidth()) {
                        button.setLocation(canvas.getWidth() - button.getWidth(), yButton);
                    }
                };
                if (yButton < 0) {
                    button.setLocation(xButton, 0);
                } else {
                    if (yButton > canvas.getHeight() - button.getHeight()) {
                        button.setLocation(xButton, canvas.getHeight() - button.getHeight());
                    }
                };
                canvas.requestFocus(); // фокус на canvas (видео) 
                overlayBackgroundZero(); // установка фона в прозрачное состояние
            } else {
                canvas.requestFocus();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) { // Проверяем, что нажата левая кнопка мыши
                int xButton = button.getX(); // координата Х кнопки
                int yButton = button.getY(); // координата Y кнопки
                // Установка новых координат кнопки
                // Новая координата Х кнопки (левого верхнего угла) = координата места, куда хотим перенести кнопку (внутрення координата кнопки) +
                // + координата старого угла - координата места нажатия на кнопку (внутрення координата кнопки). 
                // Координата Y - аналогично.
                button.setLocation(e.getX() + xButton - x, e.getY() + yButton - y);
                canvas.requestFocus(); // передача фокуса на canvas (видео)
                // Установка ограничений для невозможности выноса кнопки за область видео
                if (xButton < 0) {
                    button.setLocation(0, yButton);
                    overlayBackgroundZero(); // установка фона в прозрачное состояние
                } else {
                    if (xButton > canvas.getWidth() - button.getWidth()) {
                        button.setLocation(canvas.getWidth() - button.getWidth(), yButton);
                        overlayBackgroundZero(); // установка фона в прозрачное состояние
                    }
                };
                if (yButton < 0) {
                    button.setLocation(xButton, 0);
                    overlayBackgroundZero(); // установка фона в прозрачное состояние
                } else {
                    if (yButton > canvas.getHeight() - button.getHeight()) {
                        button.setLocation(xButton, canvas.getHeight() - button.getHeight());
                        overlayBackgroundZero(); // установка фона в прозрачное состояние
                    }
                };
                overlayBackgroundZero(); // установка фона в прозрачное состояние
            } else {
                canvas.requestFocus();
            }
        }
    }

    // ИЗМЕНЕНИЕ РАЗМЕРА ОКНА
    private ComponentAdapter frameSizeAdapter = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            if (overlay.isShowing()) { // если слой над видео Виден, то:
                // выполняем конфигурацию (размещение) кнопок в относительных координатах внутри canvas
//                newWidth = e.getComponent().getWidth();
//                newHeight = e.getComponent().getHeight();
//                upButtonOnVideoLayout2(overlay.getB1(), newWidth, newHeight);
//                upButtonOnVideoLayout2(overlay.getB2(), newWidth, newHeight);
//                upButtonOnVideoLayout2(overlay.getB3(), newWidth, newHeight);
//                upButtonOnVideoLayout2(overlay.getB4(), newWidth, newHeight);
                upButtonOnVideoLayout();
//                leftCPanel.getLabel().setText(newWidth + " + " + newHeight + " || " + oldWidthCanvas + " + " + oldHeightCanvas);
                downButtonOnVideoLayout();
                leftButtonOnVideoLayout();
                rightButtonOnVideoLayout();
                // Для предотвращения мигающего заднего фона overlay поверхности:
                overlayReload(); // отключение и включение overlay
                // Чтобы при изменение размера окна фокус переходил на canvas (видео). Если этого не делать, то фокус переходит на overlay
                canvas.requestFocus(); // фокус на canvas (видео) 
                oldWidthCanvas = canvas.getWidth();
                oldHeightCanvas = canvas.getHeight();
            }
        }
    };

    // АДАПТЕР ВИДЕО (видеоплеера). Пока что для правильной реализации метода stop() плеера после окончания видео И запуска таймера (привязки к видеоплееру)
    private MediaPlayerEventAdapter mpEventAdapter = new MediaPlayerEventAdapter() {
        @Override
        public void playing(MediaPlayer mediaPlayer) {
            super.playing(mediaPlayer);
            timer(); // конфигурация и запуск панели отображения времени видео
        }

        @Override
        public void finished(MediaPlayer mediaPlayer) {
            super.finished(mediaPlayer);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Связка из трех операций для правильного отображения времени на панели времени (и для правильного состояния видео)
                    mediaPlayer.controls().setPosition(0); // когда видео заканчивается, оно переходит в начальное состояние (stop)
                    mediaPlayer.controls().play(); // запуск видео
                    mediaPlayer.controls().setPause(true); // пауза
                    vPCPanel.getB2().setText("play");
                }
            });
        }
    };

    // СЛУШАТЕЛЬ КОЛЕСА МЫШИ (если мышь на canvas = видео). Для изменения звука видео
    MouseAdapter whellSound = new MouseAdapter() {
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
    private void upButtonOnVideoLayout() {
        double indentFromTheMiddle = 1.75;
        double indentBetweenButtnos = 0.2; // отступ между кнопками
        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
        int x = (int) ((canvas.getSize().getWidth() / 2) // середина видео
                - (overlay.getWidthButton() / 2) // отступ на половину кнопки
                - indentFromTheMiddle * overlay.getWidthButton()); // отступ от середины  
        int y = 50; // координата Y кнопки

        overlay.getB1().setLocation(x, y);
        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB2().setLocation(x, y);
        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB3().setLocation(x, y);
        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB4().setLocation(x, y);
    }

    private void upButtonOnVideoLayout2(JButton button, int newWidth, int newHeight) {
        int x = button.getX() + (newWidth - oldWidthCanvas); // новая координата X кнопки
//        int y = button.getY() + (newHeight - oldHeightCanvas); // новая координата Y кнопки
        button.setLocation(x, button.getY());
    }

    private void downButtonOnVideoLayout() {
        double indentFromTheMiddle = 1.75;
        double indentBetweenButtnos = 0.2; // отступ между кнопками
        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
        int x = (int) ((canvas.getSize().getWidth() / 2) // середина видео
                - (overlay.getWidthButton() / 2) // отступ на половину кнопки
                - indentFromTheMiddle * overlay.getWidthButton()); // отступ от середины  
        int y = (int) (canvas.getSize().getHeight() - 50); // координата Y кнопки

        overlay.getB5().setLocation(x, y);
        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB6().setLocation(x, y);
        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB7().setLocation(x, y);
        x = (int) (x + overlay.getWidthButton() + indentBetweenButtnos * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB8().setLocation(x, y);
    }

    private void rightButtonOnVideoLayout() {
        double indentFromTheMiddle = 1.75;
        double indentBetweenButtons = 0.2; // отступ между кнопками
        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
        int x = (int) (canvas.getSize().getWidth() - 200); // координата X кнопки
        int y = (int) ((canvas.getSize().getHeight() / 2) // середина видео
                - (2 * overlay.getHeightButton()) // отступ на половину кнопки
                - indentFromTheMiddle * overlay.getHeightButton()); // отступ от середины  

        overlay.getB9().setLocation(x, y);
        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB10().setLocation(x, y);
        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB11().setLocation(x, y);
        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB12().setLocation(x, y);
    }

    private void leftButtonOnVideoLayout() {
        double indentFromTheMiddle = 1.75;
        double indentBetweenButtons = 0.2; // отступ между кнопками
        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
        int x = (int) (200 - overlay.getWidthButton()); // координата X кнопки
        int y = (int) ((canvas.getSize().getHeight() / 2) // середина видео
                - (2 * overlay.getHeightButton()) // отступ на половину кнопки
                - indentFromTheMiddle * overlay.getHeightButton()); // отступ от середины  

        overlay.getB13().setLocation(x, y);
        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB14().setLocation(x, y);
        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB15().setLocation(x, y);
        y = (int) (y + overlay.getHeightButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB16().setLocation(x, y);
    }

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

    // Добавление слушателей к кнопкам, для реализации их перемещения по видео поверхности
    private void addMoveButton(JButton[] buttons) {
        for (JButton button : buttons) { // Каждой кнопке из массива кнопок
            MouseAdapter mouseAdapter = new MoveButtons(button); // создаем свой адаптер
            button.addMouseListener(mouseAdapter); // Добавляем слушателей кнопке
            button.addMouseMotionListener(mouseAdapter);
        }
    }

    // Переключение состояния видео и текста кнопки
    private void actionPlayPause() {
        if (emp.mediaPlayer().status().isPlaying() == true) {
            emp.mediaPlayer().controls().pause();
            vPCPanel.getB2().setText("play");
        } else {
            emp.mediaPlayer().controls().play();
            vPCPanel.getB2().setText("pause");
        }
    }

// МЕТОДЫ СОЗДАНИЯ: панелей, сущностей
    // ОСНОВНАЯ РАБОЧАЯ ПАНЕЛЬ с тремя областями
    private JSplitPane createWorkSplitPanel() {
        JSplitPane splitMain1 = new JSplitPane(); // создание разделяющейся панели (левая панель / видео + правая панель)
        JSplitPane splitMain2 = new JSplitPane(); // создание разделяющейся панели(видео / правая панель)
        splitMain1.setDividerSize(3); // ширина разделительной области
        splitMain2.setDividerSize(3); // ширина разделительной области
        splitMain1.setContinuousLayout(true); // поддержка LayoutManagerа помещенного в панель объекта
        splitMain2.setContinuousLayout(true); // поддержка LayoutManagerа помещенного в панель объекта 
        splitMain1.setResizeWeight(0); // установка приоритета для соотношения размеров панелей при изменении размеров = Правая половина (видео + правая панель) в приоритете
        splitMain2.setResizeWeight(1); // установка приоритета для соотношения размеров панелей при изменении размеров = Левая половина (видео) в приоритете

        // МИНИМАЛЬНЫЙ размер для левой (правой тоже) панели: по ширине = (ширина окна - ширина видео)/3 ; по высоте = такой же, как у видео  
        Dimension leftRightPanelMinimumSize = new Dimension(
                (int) ((this.getMinimumSize().getWidth() - canvas.getMinimumSize().getWidth()) / 3),
                (int) (canvas.getMinimumSize().getHeight()));

        JScrollPane leftPanel = new JScrollPane(leftCPanel.createLeftCPanel()); // Создание панели прокрутки для ЛЕВОЙ панели
        leftPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
        leftPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели        

        JScrollPane rightPanel = new JScrollPane(rightCPanel.createRightCPanel()); // Создание панели прокрутки для ПРАВОЙ панели
        rightPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши
        rightPanel.setMinimumSize(leftRightPanelMinimumSize); // Минимальный размер панели

        // Добавление компонент в разделяющуюся панель
        splitMain1.setLeftComponent(leftPanel);
        splitMain1.setRightComponent(splitMain2);
        splitMain2.setLeftComponent(canvas);
        splitMain2.setRightComponent(rightPanel);

        return splitMain1;
    }

    // ВСПЛЫВАЮЩЯЯ ПАНЕЛЬ. Метод включения и конфигурации с текстом = путь к файлу (реализовать отображение только названия)
    private void setMarquee(String filePath) {
        emp.mediaPlayer().marquee().setText(filePath);
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
