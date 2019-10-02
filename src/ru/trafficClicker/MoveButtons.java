package ru.trafficClicker;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Собственный класс для перемещения кнопок поверх видео. Только для одного
 * компонента. Нужно дорабатывать.<n>
 * Работает только для ЛЕВОЙ кнопки мыши. Если пытаться другими кнопками мыши -
 * то ничего не происходит и фокус перемещается на видео
 */
public class MoveButtons extends MouseAdapter {

    private int x; // внутрення координата Х кнопки = место нажатия на кнопку
    private int y; // внутрення координата Y кнопки = место нажатия на кнопку
    private JComponent component; // абстрактная кнопка, которая перемещается
    private Canvas canvas;

    // Конструктор класса (способ передать адрес на кнопку, которую нужно двигать)
    public MoveButtons(JComponent component, Canvas canvas) {
        this.component = component;
        this.canvas = canvas;
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
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) { // Проверяем, что нажата левая кнопка мыши
            int xButton = component.getX();
            int yButton = component.getY();

            if (xButton < 0) {
                component.setLocation(0, yButton);
            } else {
                if (xButton > canvas.getWidth() - component.getWidth()) {
                    component.setLocation(canvas.getWidth() - component.getWidth(), yButton);
                }
            };
            if (yButton < 0) {
                component.setLocation(xButton, 0);
            } else {
                if (yButton > canvas.getHeight() - component.getHeight()) {
                    component.setLocation(xButton, canvas.getHeight() - component.getHeight());
                }
            };
            canvas.requestFocus(); // фокус на canvas (видео) 
        } else {
            canvas.requestFocus();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) { // Проверяем, что нажата левая кнопка мыши
            int xButton = component.getX(); // координата Х кнопки
            int yButton = component.getY(); // координата Y кнопки
            // Установка новых координат кнопки
            // Новая координата Х кнопки (левого верхнего угла) = координата места, куда хотим перенести кнопку (внутрення координата кнопки) +
            // + координата старого угла - координата места нажатия на кнопку (внутрення координата кнопки). 
            // Координата Y - аналогично.
            component.setLocation(e.getX() + xButton - x, e.getY() + yButton - y);
            canvas.requestFocus(); // передача фокуса на canvas (видео)
            // Установка ограничений для невозможности выноса кнопки за область видео
            if (xButton < 0) {
                component.setLocation(0, yButton);
            } else {
                if (xButton > canvas.getWidth() - component.getWidth()) {
                    component.setLocation(canvas.getWidth() - component.getWidth(), yButton);
                }
            };
            if (yButton < 0) {
                component.setLocation(xButton, 0);
            } else {
                if (yButton > canvas.getHeight() - component.getHeight()) {
                    component.setLocation(xButton, canvas.getHeight() - component.getHeight());
                }
            };
        } else {
            canvas.requestFocus();
        }
    }
}
