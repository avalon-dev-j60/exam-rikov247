package ru.overlay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Класс слушатель нажатия на кнопки клавиатуры - переводим это в клик по
 * передаваемой кнопке overlay
 */
public class ButtonKeyListenerWithEMP extends KeyAdapter {

    private EmbeddedMediaPlayerComponent emp;

    private boolean keyCode1Boolean = false;
    private boolean keyCode2Boolean = false;

    // Если кнопку нажать и держать, то благодаря temp она сработает только один раз, если ее отпустить
    private JButton button; // кнопка, на которую хотим нажать
    private int keyCode1; // кнопка на клавиатуре, которую отслеживаем
    private int keyCode2;
    private boolean temp = true;
    private boolean switchBoolean = false; // логическая переменная для возможность динамически включат и выключать видео

    // Каждые 10 милисекунд таймер срабатывает и убирает закраску области вокруг кнопки
    private Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            button.setContentAreaFilled(false);
        }
    });

    // Конструктор
    public ButtonKeyListenerWithEMP(JButton button, int keyCode1, EmbeddedMediaPlayerComponent emp, JPanel overlayPanel) {
        this(button, keyCode1, -1, emp);
    }

    // Конструктор
    public ButtonKeyListenerWithEMP(JButton button, int keyCode1, int keyCode2, EmbeddedMediaPlayerComponent emp) {
        this.button = button;
        this.keyCode1 = keyCode1;
        this.keyCode2 = keyCode2;
        this.emp = emp;
    }

    // Если кнопка нажата (если кнопку зажать, то она будет многократно нажиматься, но не отпускаться)
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == keyCode1) {
            keyCode1Boolean = true;
        }
        // keyCode2 == -1 Это для варианта с одной кнопкой, а не двумя
        if (e.getKeyCode() == keyCode2 || keyCode2 == -1) {
            keyCode2Boolean = true;
        }
        if (keyCode1Boolean && keyCode2Boolean && temp) {
            button.setContentAreaFilled(true); // отображаем область вокруг кнопки, когда она нажата

            // делаем клик по кнопке - триггерим action слушатель
            button.doClick();
            temp = false; // логическая переменная для возможности удерживать кнопку нажатой и чтобы на не срабатывала много раз

            // Если при нажатии на кнопку нужно выключить видео, то выключаем его
            if (emp.mediaPlayer().status().isPlaying() == true) {
                emp.mediaPlayer().controls().pause(); // ставим на паузу видео
                switchBoolean = true; // переключаем логическую единицу, чтобы уведомить, что при отпускании кнопки нужно включить видео
            }
        }
    }

    // Если отпускаем кнопку
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == keyCode1) {
            timerRepaintEMP();
            keyCode1Boolean = false;
        }
        if (e.getKeyCode() == keyCode2) {
            timerRepaintEMP();
            keyCode2Boolean = false;
        }
    }

    private void timerRepaintEMP() {
        // Запускаем таймер
        timer.start();
        timer.setRepeats(false); // говорим, чтобы таймер сработал только один раз!
        temp = true;

        // Если при отпускании кнопки нужно включить видео, то включаем его
        if (switchBoolean) {
            emp.mediaPlayer().controls().start();
            switchBoolean = false;
        }
    }
}
