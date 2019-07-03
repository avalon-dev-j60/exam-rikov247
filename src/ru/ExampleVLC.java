package ru;

import java.awt.*;
import java.awt.event.*;

import ru.avalon.java.ui.AbstractFrame; // подключенная собственная библиотека
import ru.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.Marquee;
import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class ExampleVLC extends AbstractFrame {

    private Overlay overlay = new Overlay(this);

    private Canvas canvas = new Canvas();

    private final String filePath = "../../Video/Project.avi";

    // Создание плеера. Он сам создает mediaPlayerComponent для себя. Сам является панелью JPanel (не нужна canvas)!
    private final EmbeddedMediaPlayerComponent emp = new EmbeddedMediaPlayerComponent();

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
        setSize(1100, 700); // установка размером окна
        setMinimumSize(new Dimension(900, 500));

        // Установка Менеджера Компоновки окна
        setLayout(new BorderLayout());

        // Установка Меню Бара
        setJMenuBar(jBar.CreateBar());

        // Добавление всплывающего меню
        emp.setComponentPopupMenu(popupMenu.createPopupMenu());

        // Добавление панелей
        add(vPCPanel.createVPCPanel(), BorderLayout.SOUTH); // Контрольная панель видеоплеера
        add(leftCPanel.createLeftCPanel(), BorderLayout.WEST); // Левая панель настроек
        add(rightCPanel.createRightCPanel(), BorderLayout.EAST); // Правая панель настроек
        add(canvas, BorderLayout.CENTER); // Canvas для видео

// СЛУШАТЕЛИ СОБЫТИЙ. В случае нажатия на кнопку - вызывается метод в скобках. Создается объект нужного типа в котором вызывается метод данного типа. :: - ссылка на метод.
        // Слушатели кнопок на панели управления видео:
        vPCPanel.getB1().addActionListener(this::onStopButtonClick); // Добавление слушателя действия для кнопки B1
        vPCPanel.getB2().addActionListener(this::onPlayPauseButtonClick); // Добавление слушателя действия для кнопки B2

        // Слушатели на видео поверхность
        canvas.addMouseListener(videoMouseClick); // Добавление слушателя событий к поверхности плеера (подоснове для видео) - то есть к самому видео
        canvas.addKeyListener(keyAdapter); // Добавление слушателя для действий клавиатуры

        // Слушатели кнопок в Баре
        jBar.getViewItem3().addActionListener(this::onFullScreenButtonClick); // Слушатель кнопки "Полноэкранного режима" в Баре

        // Слушатели перемещения кнопок по экрану (Overlay)
        overlay.getB2().addMouseListener(buttonDragged);
        overlay.getB2().addMouseMotionListener(buttonDragged);

        // Слушатель изменения размера окна
        addComponentListener(frameSizeAdapter);

        // Слушатель установления фокуса на canvas = если фокус на canvas, то он устанавливает фокус на canvas (почему то при изменении размера это не дает фокусу переходить на overlay, чего и хотелось). Как бы КОСТЫЛЬ
        canvas.addFocusListener(canvasFocusAdapter);

        // ВРЕМЕННЫЙ слушатель количества кликов по кнопке
        overlay.getB2().addMouseListener(sumOfMouseClick);

        // Слушатель событий для видеоДорожки
        emp.mediaPlayer().events().addMediaPlayerEventListener(mpEventAdapter);

        // Marguee (всплывающая) панель. Можно ей управлять и до и после включения видео
        setMarquee();

        //Подготовка и запуск видео
        Play();

    }

    private void Play() {
        // Установка canvas (подосновы для видео)
        emp.mediaPlayer().videoSurface().set(emp.mediaPlayerFactory().videoSurfaces().newVideoSurface(canvas));
        canvas.setBackground(Color.black); // установка цвета заднего фона для canvas (подосновы для видео)
        // Явно отключаем встроенную обработку событий от мыши и клавиатуры
        emp.mediaPlayer().input().enableMouseInputHandling(false);
        emp.mediaPlayer().input().enableKeyInputHandling(false);
        // Указываем стратегию Полноэкранного режима
        emp.mediaPlayer().fullScreen().strategy(new AdaptiveFullScreenStrategy(this));
        // Установка слоя над видео
        emp.mediaPlayer().overlay().set(overlay);
        // Установка фокуса на canvas
        canvas.requestFocus();
        // Подготавливаем видео, но не запускаем его
        emp.mediaPlayer().media().prepare(filePath, (String) null);
        // Запускаем видео
        emp.mediaPlayer().controls().play();
    }

    private void setMarquee() {
        emp.mediaPlayer().marquee().setText(filePath);
        emp.mediaPlayer().marquee().setSize(30);
        emp.mediaPlayer().marquee().setColour(Color.WHITE);
        emp.mediaPlayer().marquee().setTimeout(3000);
        emp.mediaPlayer().marquee().setPosition(MarqueePosition.BOTTOM);
        emp.mediaPlayer().marquee().setOpacity(0.8f);
        emp.mediaPlayer().marquee().enable(true);
    }

    // Обработчик нажатия кнопки СТОП.
    private void onStopButtonClick(ActionEvent e) {
        emp.mediaPlayer().controls().stop();
        vPCPanel.getB2().setText("play");
        canvas.requestFocus(); // установка фокуса на canvas (видео)
    }

    // Обработчик нажатия кнопки ПАУЗА/ПЛЭЙ.
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

    // Обработчик нажатия кнопки "Полноэкранный режим" в Меню Баре (Alt+Shift+Enter)
    private void onFullScreenButtonClick(ActionEvent e) {
        emp.mediaPlayer().fullScreen().set(!emp.mediaPlayer().fullScreen().isFullScreen());
        canvas.requestFocus(); // установка фокуса на canvas (видео)
    }

    // Метод отслеживает действия с мышью на canvas (видео)
    private MouseAdapter videoMouseClick = new MouseAdapter() {
        // Pressed - только НАЖАТИЕ на кнопку мыши (отпускание не отслеживается
        @Override
        public void mousePressed(MouseEvent e) {
            // Метод отслеживает Клик ЛЕВОЙ (button1) кнопкой мышкой по видео
            if (e.getButton() == MouseEvent.BUTTON1) {
                actionPlayPause(); // Включение|Пауза  видео
            }
            // Метод отслеживает Клик ПРАВОЙ (button3) кнопкой мышкой по видео
            if (e.getButton() == MouseEvent.BUTTON3) {
                popupMenu.getJpm().show(e.getComponent(), e.getX(), e.getY()); // Отображение всплывающего меню (popup menu) в том месте, где нажимается кнопка мыши
            }
        }

    };

    // Отслеживает количество кликов левой кнопкной мыши и меняет текст (label) в панели СЛЕВА
    private MouseAdapter sumOfMouseClick = new MouseAdapter() {
        int sumClick = 0;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                sumClick = sumClick + 1;
                leftCPanel.getLabel().setText("Количество кликов: " + sumClick);
            }
        }
    };

    // Метод отслеживает действия с клавиатурой
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
                    upButtonOnVideoLayout();
                    downButtonOnVideoLayout();
                    leftButtonOnVideoLayout();
                    rightButtonOnVideoLayout();
                }
                // активация слоя overlay
                emp.mediaPlayer().overlay().enable(!emp.mediaPlayer().overlay().enabled()); // если overlay неактивен, то активировать и наоборот
                canvas.requestFocus(); // Установка фокуса на canvas
            }
        }
    };

    // Слушатель перемещения кнопки
    private MouseAdapter buttonDragged = new MouseAdapter() {
        int x;
        int y;

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX(); // координата Х нажатия клавишей мыши 
            y = e.getY(); // координата Y нажатия клавишей мыши 
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int xButton = overlay.getB2().getX(); // координата Х кнопки
            int yButton = overlay.getB2().getY(); // координата Y кнопки
            // Установка новых координат кнопки
            // Новая координата Х кнопки (левого верхнего угла) = координата места, куда хотим перенести кнопку (внутрення координата кнопки) +
            // + координата старого угла - координата места нажатия на кнопку (внутрення координата кнопки). 
            // Координата Y - аналогично.
            overlay.getB2().setLocation(e.getX() + xButton - x, e.getY() + yButton - y);
            canvas.requestFocus(); // передача фокуса на canvas (видео)

            // Установка ограничений для невозможности выноса кнопки за область видео
            if (xButton < 0) {
                overlay.getB2().setLocation(0, yButton);
                overlayReload(); // отключение и включение overlay - для предотвращения возможного исчезновения кнопок
            } else {
                if (xButton > canvas.getWidth() - overlay.getB2().getWidth()) {
                    overlay.getB2().setLocation(canvas.getWidth() - overlay.getB2().getWidth(), yButton);
                    overlayReload(); // отключение и включение overlay - для предотвращения возможного исчезновения кнопок
                }
            };
            if (yButton < 0) {
                overlay.getB2().setLocation(xButton, 0);
                overlayReload(); // отключение и включение overlay - для предотвращения возможного исчезновения кнопок
            } else {
                if (yButton > canvas.getHeight() - overlay.getB2().getHeight()) {
                    overlay.getB2().setLocation(xButton, canvas.getHeight() - overlay.getB2().getHeight());
                    overlayReload(); // отключение и включение overlay - для предотвращения возможного исчезновения кнопок
                }
            };
        }

        // Установка ограничений для невозможности выноса кнопки за область видео (дополнительные меры)
        @Override
        public void mouseReleased(MouseEvent e) {
            int xButton = overlay.getB2().getX();
            int yButton = overlay.getB2().getY();

            if (xButton < 0) {
                overlay.getB2().setLocation(0, yButton);
            } else {
                if (xButton > canvas.getWidth() - overlay.getB2().getWidth()) {
                    overlay.getB2().setLocation(canvas.getWidth() - overlay.getB2().getWidth(), yButton);
                }
            };
            if (yButton < 0) {
                overlay.getB2().setLocation(xButton, 0);
            } else {
                if (yButton > canvas.getHeight() - overlay.getB2().getHeight()) {
                    overlay.getB2().setLocation(xButton, canvas.getHeight() - overlay.getB2().getHeight());
                }
            };
            canvas.requestFocus(); // фокус на canvas (видео) 
        }

    };

    // Слушатель изменения размера окна
    private ComponentAdapter frameSizeAdapter = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            if (overlay.isVisible()) { // если слой над видео Виден, то:
                // выполняем конфигурацию (размещение) кнопок в относительных координатах внутри canvas
                upButtonOnVideoLayout();
                downButtonOnVideoLayout();
                leftButtonOnVideoLayout();
                rightButtonOnVideoLayout();
                // Для предотвращения мигающего заднего фона overlay поверхности:
                overlayReload(); // отключение и включение overlay
                canvas.requestFocus(); // фокус на canvas (видео) 
                // Чтобы при изменение размера окна фокус переходил на canvas (видео). Если этого не делать, то фокус переходит на overlay
            }
        }
    };

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

    FocusAdapter canvasFocusAdapter = new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            canvas.requestFocus();
        }
    };

    private void actionPlayPause() {
        if (emp.mediaPlayer().status().isPlaying() == true) {
            emp.mediaPlayer().controls().pause();
            vPCPanel.getB2().setText("play");
        } else {
            emp.mediaPlayer().controls().play();
            vPCPanel.getB2().setText("pause");
        }
    }

    private void overlayReload() {
        emp.mediaPlayer().overlay().enable(false); // overlay не активен
        emp.mediaPlayer().overlay().enable(true); // overlay активен
    }

    // Адаптер Мероприятий МедиаПлеера. Адаптер - говорит, что делать (пытается решить проблемы). 
    private MediaPlayerEventAdapter mpEventAdapter = new MediaPlayerEventAdapter() {
        @Override
        public void playing(MediaPlayer mediaPlayer) {
            super.playing(mediaPlayer); // Что делать, когда видео играет
        }

        @Override
        public void finished(MediaPlayer mediaPlayer) {
            super.finished(mediaPlayer); // Что делать, когда видео заканчивается. Наверное запускать следующее по названию из папки с видео
        }

        @Override
        public void error(MediaPlayer mediaPlayer) {
            super.error(mediaPlayer); // Что делать при возникновении ошибки
        }

    };
}
