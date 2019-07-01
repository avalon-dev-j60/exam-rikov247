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
    private EmbeddedMediaPlayerComponent emp = new EmbeddedMediaPlayerComponent();

    // Переменная для менюБара
    private CreateMenuBar jBar = new CreateMenuBar();

    // Переменная для всплывающего меню
    private CreatePopupMenu popupMenu = new CreatePopupMenu();

    // Переменные для создания Панелей видео плеера
    CreateVideoPlayerControlPanel vPCPanel = new CreateVideoPlayerControlPanel(); // VideoPlayer Control Panel (Контрольная Панель Видео Плеера)
    CreateLeftControlPanel leftCPanel = new CreateLeftControlPanel(); // Left Control Panel (Левая Контрольная Панель)
    CreateRightControlPanel rightCPanel = new CreateRightControlPanel(); // Right Control Panel (Правая Контрольная Панель)

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
        add(vPCPanel.createVPCPanel(), BorderLayout.SOUTH);
        add(leftCPanel.createLeftCPanel(), BorderLayout.WEST);
        add(rightCPanel.createRightCPanel(), BorderLayout.EAST);
        add(canvas, BorderLayout.CENTER); // canvas для видео

        // Добавление слушателей событий
        vPCPanel.getB1().addActionListener(this::onStopButtonClick); // В случае нажатия на кнопку - вызывается метод в скобках. Создается объект нужного типа в котором вызывается метод данного типа. :: - ссылка на метод.
        vPCPanel.getB2().addActionListener(this::onPlayPauseButtonClick);
        canvas.addMouseListener(mouseAdapter); // Добавление слушателя событий к поверхности плеера (подоснове для видео) - то есть к самому видео
        canvas.addKeyListener(keyAdapter);
        emp.mediaPlayer().events().addMediaPlayerEventListener(mpEventAdapter); // Добавление Адаптера Мероприятий МедиаПлеера
        jBar.getViewItem3().addActionListener(this::onFullScreenButtonClick);

        leftCPanel.getLabel().addMouseListener(mouseAdapter);
        // Marguee (всплывающая) панель. Можно ей управлять и до и после включения видео
        setMarquee();

        Play(); //Подготовка и запуск видео

    }

    private void Play() {
        // Установка canvas (подосновы для видео)
        emp.mediaPlayer().videoSurface().set(emp.mediaPlayerFactory().videoSurfaces().newVideoSurface(canvas));
        // Явно отключаем встроенную обработку событий от мыши и клавиатуры
        emp.mediaPlayer().input().enableMouseInputHandling(false);
        emp.mediaPlayer().input().enableKeyInputHandling(false);
        // Указываем стратегию Полноэкранного режима
        emp.mediaPlayer().fullScreen().strategy(new AdaptiveFullScreenStrategy(this));
        // Установка слоя над видео
        emp.mediaPlayer().overlay().set(overlay);
        canvas.requestFocus(); // Установка фокуса на canvas
        // Подготавливаем видео
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
    }

    // Обработчик нажатия кнопки ПАУЗА/ПЛЭЙ.
    private void onPlayPauseButtonClick(ActionEvent e) {
        if (emp.mediaPlayer().status().isPlaying() == true) { // Если медиа проигрывается (play)
            emp.mediaPlayer().controls().pause(); // то - пауза (pause)
            vPCPanel.getB2().setText("play"); // текст кнопки меняется на Play
        } else {
            emp.mediaPlayer().controls().play(); // Если медиа не проигрывается, то - play
            vPCPanel.getB2().setText("pause"); // текст кнопки меняется на Play
        }
    }

    // Обработчик нажатия кнопки "Полноэкранный режим" в Меню Баре
    private boolean temp;

    private void onFullScreenButtonClick(ActionEvent e) {
        if (temp == false) {
            emp.mediaPlayer().fullScreen().set(true);
            temp = true;
        } else {
            emp.mediaPlayer().fullScreen().set(false);
            temp = false;
        }
    }

    // Метод отслеживает действия с мышью
    MouseAdapter mouseAdapter = new MouseAdapter() {
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

    // Метод отслеживает действия с клавиатурой
    KeyAdapter keyAdapter = new KeyAdapter() {
        // Pressed - только нажал (отпускание обрабатывается отдельно)
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == e.VK_SPACE) {
                actionPlayPause();
            }
            if (e.getExtendedKeyCode() == e.VK_F11) {
                // Если canvas отображается на экране (окно отображается), то устанавливаем расположение кнопок
                if (canvas.isShowing()) {
                    upButtonOnVideoLayout();
                    downButtonOnVideoLayout();
                }
                // активация слоя overlay
                emp.mediaPlayer().overlay().enable(!emp.mediaPlayer().overlay().enabled());
                canvas.requestFocus(); // Установка фокуса на canvas

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

    private void leftButtonOnVideoLayout() {
        double indentFromTheMiddle = 1.75;
        double indentBetweenButtons = 0.2; // отступ между кнопками
        // координата X кнопки - это её нижний левый угол, поэтому: ширина canvas/2 - ширина кнопки/2
        int x = (int) (canvas.getSize().getWidth() - 200); // координата X кнопки
        int y = (int) ((canvas.getSize().getHeight() / 2) // середина видео
                - (overlay.getWidthButton() / 2) // отступ на половину кнопки
                - indentFromTheMiddle * overlay.getHeightButton()); // отступ от середины  

        overlay.getB9().setLocation(x, y);
        y = (int) (y + overlay.getWidthButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB10().setLocation(x, y);
        y = (int) (y + overlay.getWidthButton() + indentBetweenButtons * (overlay.getWidthButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB11().setLocation(x, y);
        y = (int) (y + overlay.getWidthButton() + indentBetweenButtons * (overlay.getHeightButton())); // прошлая координата + ширина кнопки + расстояние между кнопками
        overlay.getB12().setLocation(x, y);
    }

    // Адаптер Мероприятий МедиаПлеера. Адаптер - говорит, что делать (пытается решить проблемы). 
    MediaPlayerEventAdapter mpEventAdapter = new MediaPlayerEventAdapter() {
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

    private void actionPlayPause() {
        if (emp.mediaPlayer().status().isPlaying() == true) {
            emp.mediaPlayer().controls().pause();
            vPCPanel.getB2().setText("play");
        } else {
            emp.mediaPlayer().controls().play();
            vPCPanel.getB2().setText("pause");
        }
    }

}
