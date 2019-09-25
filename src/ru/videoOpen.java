package ru;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Метод открытия видео
 */
public class videoOpen extends JPanel {

    private JFileChooser videoOpen = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // создаем объект ВыбораФайлов с первоначальным месторасположением на РАБОЧЕМ СТОЛЕ
    private String[] extensions = new String[]{
        "3g2", "3gp", "3gp2", "3gpp", "amv", "asf", "avi", "bik", "bin", "crf",
        "divx", "drc", "dv", "dvr-mc", "evo", "f4v", "flv", "gvi", "gxf", "iso",
        "m1v", "m2v", "m2t", "m2ts", "m4v", "mkv", "mov", "mp2", "mp2v", "mp4",
        "mp4v", "mpe", "mpeg", "mpeg1", "mpeg2", "mpeg3", "mpeg4", "mpg", "mpv2",
        "mts", "mtv", "mxf", "mxg", "nsv", "nuv", "ogg", "ogm", "ogv", "ogx",
        "ps", "rec", "rm", "rmvb", "rpl", "thp", "tod", "tp", "ts", "tts", "txd",
        "vob", "vro", "webm", "wm", "wmv", "wtv", "xesc"};
    private FileNameExtensionFilter videoFilter = new FileNameExtensionFilter("Видеофайлы", extensions); // Фильтр: название фильтра, Расширения файлов которые видны

    // Отключение возможности редактирования текстового поля (JTextField) JFileChooser
    private boolean disableTF(Container c) {
        // Получаем компоненты, содержащиеся в JFileChooser
        Component[] cmps = c.getComponents();
        // Перебираем компоненты, содержащиеся в JFileChooser
        for (Component cmp : cmps) {
            // если компонет, это текстовое поле, то: 
            if (cmp instanceof JTextField) {
                // текстовой поле становится НЕ РЕДАКТИРУЕМЫМ - нельзя изменить (но можно выделить и скопировать)
                ((JTextField) cmp).setEditable(false);//Enabled(false);
                return true; // возвращаем true
            }
            // компонент из JFileChooser сам является JFileChooser и при этом
            if (cmp instanceof Container) {
                // уже найдено тексовое поле в JFileChooser, то:
                if (disableTF((Container) cmp)) {
                    // возвращаем true
                    return true;
                }
            }
        }
        return false;
    }

    public JFileChooser videoOpen(File directory) {
        videoOpen.setFileSelectionMode(JFileChooser.FILES_ONLY); // Выбор только файлов (не директорий)
        videoOpen.setAcceptAllFileFilterUsed(false); // Убираем фильтр всех файлов
        videoOpen.setFileFilter(videoFilter);
        videoOpen.setCurrentDirectory(directory);
        disableTF(videoOpen);

        return videoOpen;
    }
}
