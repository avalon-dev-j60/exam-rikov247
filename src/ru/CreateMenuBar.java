package ru;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class CreateMenuBar {

    private JMenuBar mbar = new JMenuBar();
    private JMenu fileMenu = new JMenu("Файл");
    private JMenu editMenu = new JMenu("Правка");
    private JMenu viewMenu = new JMenu("Вид");
    private JMenu helpMenu = new JMenu("Помощь");

    public JMenuBar CreateBar() {

        mbar.add(createFileMenu());
        mbar.add(createEditMenu());
        mbar.add(createViewMenu());
        mbar.add(createHelpMenu());

        return mbar;
    }

    private JMenuItem fileItem1 = new JMenuItem("Новый проект...");
    private ImageIcon fileItem1Icon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/general/Add16.gif"));
    private JMenuItem fileItem2 = new JMenuItem("Открыть проект...");
    private ImageIcon fileItem2Icon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/general/Open16.gif"));
    private JMenuItem fileItem3 = new JMenuItem("Закрыть проект");
    private JMenuItem fileItem4 = new JMenuItem("Сохранить");
    private JMenuItem fileItem5 = new JMenuItem("Сохранить как..");
    private ImageIcon fileItem5Icon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/general/Save16.gif"));
    private JMenuItem fileItem6 = new JMenuItem("Открыть видео");
    private ImageIcon fileItem6Icon = new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/media/Movie16.gif"));
    private JMenuItem fileItem7 = new JMenuItem("Выход");

    private JMenu createFileMenu() {
        fileMenu.add(fileItem1);
        fileItem1.setIcon(fileItem1Icon); // устанавливаем иконку
        fileMenu.add(fileItem2);
        fileItem2.setIcon(fileItem2Icon); // устанавливаем иконку
        fileMenu.add(fileItem3);
        fileMenu.add(fileItem4);
        fileMenu.add(fileItem5);
        fileItem5.setIcon(fileItem5Icon); // устанавливаем иконку
        fileMenu.addSeparator(); // разделитель
        fileMenu.add(fileItem6);
        fileItem6.setIcon(fileItem6Icon); // устанавливаем иконку
        fileMenu.addSeparator(); // разделитель
        fileMenu.add(fileItem7);

        return fileMenu;
    }

    private JMenuItem editItem1 = new JMenuItem("Отменить");
    private JMenuItem editItem2 = new JMenuItem("Вернуть");
    private JMenuItem editItem3 = new JMenuItem("Вырезать");
    private JMenuItem editItem4 = new JMenuItem("Копировать");
    private JMenuItem editItem5 = new JMenuItem("Вставить");

    private JMenu createEditMenu() {
        editItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK)); // установка клавишного сочетания
        editItem2.setAccelerator(KeyStroke.getKeyStroke("ctrl Y")); // установка клавишного сочетания

        editMenu.add(editItem1);
        editMenu.add(editItem2);
        editMenu.addSeparator();
        editMenu.add(editItem3);
        editMenu.add(editItem4);
        editMenu.add(editItem5);

        return editMenu;
    }

    private JMenu viewMenu1 = new JMenu("Панель инструментов");
    private JMenuItem viewItem1 = new JCheckBoxMenuItem("Замедлить");
    private JMenuItem viewItem2 = new JCheckBoxMenuItem("Ускорить");
    private JMenuItem viewItem3 = new JMenuItem("Полноэкранный режим");

    private JMenu createViewMenu() {
        viewMenu1.add(viewItem1);
        viewMenu1.add(viewItem2);
        viewMenu.add(viewMenu1);
        viewMenu.addSeparator(); // разделитель
        viewItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK)); // установка клавишного сочетания
        viewMenu.add(viewItem3);

        return viewMenu;
    }

    private JMenuItem helpItem1 = new JMenuItem("О нас");

    private JMenu createHelpMenu() {
        helpMenu.add(helpItem1);

        return helpMenu;
    }

    public JMenuBar getMbar() {
        return mbar;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenu getEditMenu() {
        return editMenu;
    }

    public JMenu getViewMenu() {
        return viewMenu;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public JMenuItem getFileItem1() {
        return fileItem1;
    }

    public JMenuItem getFileItem2() {
        return fileItem2;
    }

    public JMenuItem getFileItem3() {
        return fileItem3;
    }

    public JMenuItem getFileItem4() {
        return fileItem4;
    }

    public JMenuItem getFileItem5() {
        return fileItem5;
    }

    public JMenuItem getFileItem6() {
        return fileItem6;
    }
    
    public JMenuItem getFileItem7() {
        return fileItem7;
    }

    public JMenuItem getEditItem1() {
        return editItem1;
    }

    public JMenuItem getEditItem2() {
        return editItem2;
    }

    public JMenuItem getEditItem3() {
        return editItem3;
    }

    public JMenuItem getEditItem4() {
        return editItem4;
    }

    public JMenuItem getEditItem5() {
        return editItem5;
    }

    public JMenu getViewMenu1() {
        return viewMenu1;
    }

    public JMenuItem getViewItem1() {
        return viewItem1;
    }

    public JMenuItem getViewItem2() {
        return viewItem2;
    }

    public JMenuItem getViewItem3() {
        return viewItem3;
    }

    public JMenuItem getHelpItem1() {
        return helpItem1;
    }

}
