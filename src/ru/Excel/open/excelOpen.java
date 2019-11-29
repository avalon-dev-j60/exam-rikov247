package ru.Excel.open;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Метод выбора и открытия файла excel = шаблона для таблицы данных
 */
public class excelOpen extends JPanel {

    private JFileChooser excelOpen = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // создаем объект ВыбораФайлов с первоначальным месторасположением на РАБОЧЕМ СТОЛЕ
    private String[] extensions = new String[]{"xls", "xlsx"};
    private FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Файлы excel", extensions); // Фильтр: название фильтра, Расширения файлов которые видны
    private File file = null; // если была открыто какое то видео, то в следующий раз FileChooser откроет эту же директорию (в котором было это видео)

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

    public String getSelectExcelFile() {
        excelOpen.setFileSelectionMode(JFileChooser.FILES_ONLY); // Выбор только файлов (не директорий)
        excelOpen.setAcceptAllFileFilterUsed(false); // Убираем фильтр всех файлов
        excelOpen.setFileFilter(excelFilter);
        if (file == null) { // если еще никакой файл не был открыт, то открывается Рабочий стол
            excelOpen.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory()); // рабочий стол
        } else { // если какая то директория уже была выбрана ранее, то теперь откроется эта же директория
            excelOpen.setCurrentDirectory(file);
        }
        disableTF(excelOpen); // отключаем возможность вписать файл, оставляем только просмотр

        int ret = excelOpen.showDialog(null, "Открыть проект"); // показываем диалог с названием "Открыть файл"
        if (ret == JFileChooser.APPROVE_OPTION) { //  
            file = excelOpen.getSelectedFile();
            return file.getAbsolutePath(); // получаем абсолютный путь к выбранному файлу и возвращаем его
        }
        if (ret == JFileChooser.CANCEL_OPTION || ret == JFileChooser.ERROR_OPTION) {
            return null;// выходим из режима выбора файлов            
        }

        return null;
    }
}
