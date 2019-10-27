/**
 * Всплывающее меню
 */
package ru;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class CreatePopupVideoMenu {

    private JPopupMenu jpm = new JPopupMenu();
    
    private JMenuItem menuItem1;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenuItem menuItem7;
    private JMenuItem menuItem8;
    private JMenuItem menuItem9;

    public JPopupMenu createPopupMenu() {

        menuItem1 = new JMenuItem("Открыть файл...");
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); // установка клавишного сочетания

        JMenu menu1 = new JMenu("Воспроизведение");
        menuItem4 = new JMenuItem("Воспроизвести | Приостановить");
        menuItem4.setAccelerator(KeyStroke.getKeyStroke("SPACE")); // установка клавишного сочетания
        menuItem5 = new JMenuItem("Полноэкранный режим");
        menuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK)); // установка клавишного сочетания

        JMenu menu2 = new JMenu("Скорость воспроизведения");
        menuItem7 = new JRadioButtonMenuItem("Обычная скорость");
        menuItem7.setAccelerator(KeyStroke.getKeyStroke('z')); // установка клавишного сочетания
        menuItem8 = new JRadioButtonMenuItem("Уменьшить скорость");
        menuItem8.setAccelerator(KeyStroke.getKeyStroke('x')); // установка клавишного сочетания
        menuItem9 = new JRadioButtonMenuItem("Увеличить скорость");
        menuItem9.setAccelerator(KeyStroke.getKeyStroke('c')); // установка клавишного сочетания
        JMenuItem menuItem10 = new JMenuItem("Настройки...");
        menuItem10.setAccelerator(KeyStroke.getKeyStroke("F5")); // установка клавишного сочетания
        menuItem10.setEnabled(false);

        jpm.add(menuItem1);
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
        menu1.add(menu2);
        jpm.add(menu1);
        jpm.addSeparator();

        jpm.add(menuItem10);

        return jpm;
    }

    public JPopupMenu getJpm() {
        return jpm;
    }

    public JMenuItem getMenuItem4() {
        return menuItem4;
    }

    public JMenuItem getMenuItem1() {
        return menuItem1;
    }

    public JMenuItem getMenuItem5() {
        return menuItem5;
    }

    public JMenuItem getMenuItem7() {
        return menuItem7;
    }

    public JMenuItem getMenuItem8() {
        return menuItem8;
    }

    public JMenuItem getMenuItem9() {
        return menuItem9;
    }
    
}
