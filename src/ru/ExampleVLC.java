package ru;

import java.awt.*;
import java.awt.event.*;

import ru.avalon.java.ui.AbstractFrame; // подключенная собственная библиотека
import ru.*;

import javax.swing.*;
import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.player.Marquee;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;

public class ExampleVLC extends AbstractFrame {

    String filePath = "F:\\Video/Project.avi";

    // Создание плеера
    protected MediaPlayerFactory mpf = new MediaPlayerFactory();
    protected EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(this));

    // Переменные для менюБара
    private MenuBar bar = new MenuBar();
    private CreateMenuBar cmbar = new CreateMenuBar(bar);

    // Переменные для создания Панелей видео плеера
    CreateVideoPlayerControlPanel vPCPanel = new CreateVideoPlayerControlPanel(); // VideoPlayer Control Panel (Контрольная Панель Видео Плеера)
    CreateLeftControlPanel leftCPanel = new CreateLeftControlPanel(); // Left Control Panel (Левая Контрольная Панель)
    CreateRightControlPanel rightCPanel = new CreateRightControlPanel(); // Right Control Panel (Правая Контрольная Панель)
    CreateVideoPanel vPanel = new CreateVideoPanel(); // Video Panel (Панель с Видео)

    // Что происходит при создании окна
    @Override
    protected void onCreate() {
        setTitle("VLC Player");
        setSize(1000, 600);

        // Установка Меню Бара
        setMenuBar(bar);
        cmbar.CreateBar();

        setLayout(new BorderLayout());

        // Добавление Панелей
        add(vPCPanel.createVPCPanel(), BorderLayout.SOUTH);
        add(leftCPanel.createLeftCPanel(), BorderLayout.WEST);
        add(rightCPanel.createRightCPanel(), BorderLayout.EAST);
        add(vPanel.createVPanel(), BorderLayout.CENTER);

        // Добавление слушателей событий к кнопкам
        vPCPanel.b1.addActionListener(this::onStopButtonClick); // В случае нажатия на кнопку - вызывается метод в скобках. Создается объект нужного типа в котором вызывается метод данного типа. :: - ссылка на метод.
        vPCPanel.b2.addActionListener(this::onPlayPauseButtonClick);
        vPanel.videoCanvas.addMouseListener(click);         // Добавление слушателя событий к canvas (подоснове для видео) - то есть к самому видео

        //Подготовка и запуск видео
        Play();

  
        
        
        
        // Marguee (всплывающая) панель
        emp.setMarqueeText(filePath);
        emp.setMarqueeSize(30);
        emp.setMarqueeColour(Color.WHITE);
        emp.setMarqueeTimeout(3000);
        emp.setMarqueePosition(libvlc_marquee_position_e.bottom);
        emp.setMarqueeOpacity(0.8f);
        emp.enableMarquee(true);
    }

    protected void Play() {
        emp.setVideoSurface(mpf.newVideoSurface(vPanel.videoCanvas));
        // Явно отключаем встроенную обработку событий от мыши и клавиатуры
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
        // Подготавливаем видео
        emp.prepareMedia(filePath);
        emp.play();
    }

    
    
    //Обработчик нажатия кнопки СТОП.
    private void onStopButtonClick(ActionEvent e) {
        emp.stop();
        vPCPanel.b2.setText("play");
    }

    //Обработчик нажатия кнопки ПАУЗА/ПЛЭЙ.
    private void onPlayPauseButtonClick(ActionEvent e) {
        if (emp.isPlaying() == true) {
            emp.pause();
            vPCPanel.b2.setText("play");
        } else {
            emp.play();
            vPCPanel.b2.setText("pause");
        }
    }

    // Метод отслеживает Клик ЛЕВОЙ (button1) мышкой (нажал и в этом же месте отпустил) по видео (canvas)
    MouseAdapter click = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (emp.isPlaying() == true) {
                    emp.pause();
                    vPCPanel.b2.setText("play");
                } else {
                    emp.play();
                    vPCPanel.b2.setText("pause");
                }
            }
        }
    };

}
