/**
 * Всплывающее меню
 */
package ru;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class CreatePopupMenu {

    private JPopupMenu jpm = new JPopupMenu();

    public JPopupMenu createPopupMenu() {

        JMenuItem menuItem1 = new JMenuItem("Открыть файл...");
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); // установка клавишного сочетания
        JMenuItem menuItem2 = new JMenuItem("Открыть адрес...");
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK)); // установка клавишного сочетания
        JMenuItem menuItem3 = new JMenuItem("Открыть папку...");
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK)); // установка клавишного сочетания

        JMenu menu1 = new JMenu("Воспроизведение");
        JMenuItem menuItem4 = new JMenuItem("Воспроизвести | Приостановить");
        menuItem4.setAccelerator(KeyStroke.getKeyStroke("SPACE")); // установка клавишного сочетания
        JMenuItem menuItem5 = new JMenuItem("Предыдущий файл");
        menuItem5.setAccelerator(KeyStroke.getKeyStroke("PAGE_UP")); // установка клавишного сочетания
        JMenuItem menuItem6 = new JMenuItem("Следующий файл");
        menuItem6.setAccelerator(KeyStroke.getKeyStroke("PAGE_DOWN")); // установка клавишного сочетания
        JMenu menu2 = new JMenu("Скорость воспроизведения");
        JMenuItem menuItem7 = new JRadioButtonMenuItem("Обычная скорость");
        menuItem7.setAccelerator(KeyStroke.getKeyStroke('z')); // установка клавишного сочетания
        JMenuItem menuItem8 = new JRadioButtonMenuItem("Уменьшить скорость");
        menuItem8.setAccelerator(KeyStroke.getKeyStroke('x')); // установка клавишного сочетания
        JMenuItem menuItem9 = new JRadioButtonMenuItem("Увеличить скорость");
        menuItem9.setAccelerator(KeyStroke.getKeyStroke('c')); // установка клавишного сочетания
        JMenuItem menuItem10 = new JMenuItem("Настройки...");
        menuItem10.setAccelerator(KeyStroke.getKeyStroke("F5")); // установка клавишного сочетания
        JMenuItem menuItem11 = new JMenuItem("Выход");
        menuItem11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK)); // установка клавишного сочетания

        jpm.add(menuItem1);
        jpm.add(menuItem2);
        jpm.add(menuItem3);
        jpm.addSeparator();

        ButtonGroup bg = new ButtonGroup(); // для добавление радиоКнопок в группу
        bg.add(menuItem7);
        bg.add(menuItem8);
        bg.add(menuItem9);
        
        menu2.add(menuItem7);
        menu2.add(menuItem8);
        menu2.add(menuItem9);
        menu1.add(menuItem4);
        menu1.add(menuItem5);
        menu1.add(menuItem6);
        menu1.add(menu2);
        jpm.add(menu1);
        jpm.addSeparator();

        jpm.add(menuItem10);
        jpm.add(menuItem11);

        return jpm;
    }

    public JPopupMenu getJpm() {
        return jpm;
    }

    
    
}
