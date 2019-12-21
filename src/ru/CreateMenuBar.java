package ru;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.KeyStroke;

public class CreateMenuBar {

    private JMenuBar mbar = new JMenuBar();
    private JMenu fileMenu = new JMenu("Файл");
    private JMenu editMenu = new JMenu("Правка");
    private JMenu viewMenu = new JMenu("Вид");
    private JMenu toolsMenu = new JMenu("Инструменты");
    private JMenu helpMenu = new JMenu("Помощь");

    public JMenuBar CreateBar() {
        mbar.add(createFileMenu());
//        mbar.add(createEditMenu());
        mbar.add(createViewMenu());
        mbar.add(createToolsMenu());
        mbar.add(createHelpMenu());

        return mbar;
    }

    private JMenuItem fileItem2 = new JMenuItem("Открыть проект...");
    private ImageIcon fileItem2Icon = new ImageIcon(CreateMenuBar.class.getResource("/toolbarButtonGraphics/general/Open16.gif"));
    private JMenuItem fileItem3 = new JMenuItem("Создать проект");
    private ImageIcon fileItem3Icon = new ImageIcon(CreateMenuBar.class.getResource("/toolbarButtonGraphics/general/Add16.gif"));
    private JMenuItem fileItem4 = new JMenuItem("Сохранить");
    private ImageIcon fileItem4Icon = new ImageIcon(CreateMenuBar.class.getResource("/toolbarButtonGraphics/general/Save16.gif"));
    private JMenuItem fileItem5 = new JMenuItem("Сохранить как..");
    private ImageIcon fileItem5Icon = new ImageIcon(CreateMenuBar.class.getResource("/toolbarButtonGraphics/general/SaveAs16.gif"));
    private JMenuItem fileItem6 = new JMenuItem("Открыть видео");
    private ImageIcon fileItem6Icon = new ImageIcon(CreateMenuBar.class.getResource("/toolbarButtonGraphics/media/Movie16.gif"));

    private JMenu createFileMenu() {
        fileMenu.add(fileItem2);
        fileItem2.setIcon(fileItem2Icon); // устанавливаем иконку
        fileMenu.add(fileItem3);
        fileItem3.setIcon(fileItem3Icon); // устанавливаем иконку
        fileMenu.add(fileItem4);
        fileItem4.setIcon(fileItem4Icon); // устанавливаем иконку
        fileItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); // установка клавишного сочетания
        fileItem4.setEnabled(false);
        fileMenu.add(fileItem5);
        fileItem5.setIcon(fileItem5Icon); // устанавливаем иконку
        fileItem5.setEnabled(false);
        fileMenu.addSeparator(); // разделитель
        fileMenu.add(fileItem6);
        fileItem6.setIcon(fileItem6Icon); // устанавливаем иконку
        fileItem6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

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
//        viewMenu1.add(viewItem1);
//        viewMenu1.add(viewItem2);
//        viewMenu.add(viewMenu1);
//        viewMenu.addSeparator(); // разделитель
        viewItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK)); // установка клавишного сочетания
        viewMenu.add(viewItem3);

        return viewMenu;
    }
    
    private JMenuItem toolsItem1 = new JMenuItem("Настройки...");
    
    private JMenu createToolsMenu() {
//        viewMenu1.add(viewItem1);
//        viewMenu1.add(viewItem2);
//        viewMenu.add(viewMenu1);
//        viewMenu.addSeparator(); // разделитель
        toolsMenu.add(toolsItem1);

        return toolsMenu;
    }

    private JMenuItem helpItem1 = new JMenuItem("Горячие клавиши");
    private JMenuItem helpItem2 = new JMenuItem("Приступая к работе");
    private JMenuItem helpItem3 = new JMenuItem("О нас");

    private JMenu createHelpMenu() {
        helpMenu.add(helpItem1);
        helpItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(helpItem1,
                        new String[]{
                            "Горячие клавиши:",
                            "F11 - показать панель кнопок поверх видео (если создан проект)",
                            "Space - включить/выключить воспроизведение видео",
                            "M - включить/выключить звук",
                            "Z - нормальная скорость воспроизведения",
                            "X - уменьшить скорость воспроизведения",
                            "C - увеличить скорость воспроизведения",
                            "<-  переход по видео на 5 секунд назад",
                            "->  переход по видео на 5 секунд вперед",
                            "Ctrl + S  сохранить созданный проект",
                            "Ctrl + O  открыть видео",
                            "Alt + Shift + Enter  полноэкранный режим"},
                        "Горячие клавиши", INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(helpItem2);
        helpItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(helpItem2,
                        new String[]{
                            "1. Добавьте видео.",
                            "2. На вкладке \"Таблица результатов подсчета\" настройте свой проект:",
                            "    2.1. Выберите вид таблицы (старая/нынешняя или новая/будущая)",
                            "    2.2. Выберите количество направлений движения транспорта",
                            "    2.3. Выберите типы транспорта для подсчета (для них создадутся соответствующие кнопки над видео)",
                            "    2.4. Создайте свой проект, выбрав имя вашего будущего файла и место его хранения!",
                            "3. Теперь для вас были созданы: ",
                            "    - файл-шаблон с таблицами результатов подсчета в формате excel в указанной вами директории;",
                            "    - таблицы по времени дня и 15-ти минуткам, и панель с кнопками над видео внутри приложения;",
                            "    - картограммы часовые по времени дня внутри приложения.",
                            "4. Считайте транспорт!",
                            "    4.1. Во вкладке \"Таблица результатов подсчета\" на панели справа выберите нужную таблицу 15-ти минутку и время дня;",
                            "    4.2. Теперь информация с кнопок будет поступать в вами указанную таблицу. Итоговая таблица за час заполняется автоматически.",
                            "5. Картограммы.",
                            "    5.1. Во вкладке \"Картограммы\" на панели слева настройте ваш проект. Информация отобразится на картограммах и в файле excel.",
                            " ",
                            "Все таблицы сохранятся в вами созданный файл excel.",
                            "Картограммы сохранятся каждая в отдельном файле формата .svg. Это векторный формат, который легко редактировать, например, в любом текстовом редакторе.",
                            "Не забывайте периодически сохранять свой проект."},
                        "Приступая к работе", INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(helpItem3);
        helpItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(helpItem3,
                        new String[]{
                            "Тестовая версия приложения v 1.0.0.",
                            "По возникающим проблемам, вопросам или пожеланиям просьба писать по адресу:",
                            "rikov247@gmail.com"
                        },
                        "О нас", INFORMATION_MESSAGE);
            }
        });

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

    public JMenuItem getToolsItem1() {
        return toolsItem1;
    }

}
