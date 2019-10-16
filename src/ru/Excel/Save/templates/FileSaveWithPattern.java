package ru.Excel.Save.templates;

import ru.Excel.Save.templates.SaveAsPattern;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import org.quinto.swing.table.view.JBroTable;

/**
 * Класс для клонирования существующего шаблона в директорию и с именем,
 * выбранными с помощью JFileChooser (компонента выбора файлов). Далее в него
 * запишется таблица уже в других классах.
 */
public class FileSaveWithPattern extends JPanel {

    SaveAsPattern saveAswithPattern;

    public FileSaveWithPattern(String pattern) throws IOException {
        JFileChooser fileSave = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // создаем объект ВыбораФайлов с первоначальным месторасположением на РАБОЧЕМ СТОЛЕ
        // Фильтры файлов
        FileNameExtensionFilter xlsFilter = new FileNameExtensionFilter("Книга Excel 97—2003 (*.xls)", "xls"); // Фильтр: название фильтра, Расширения файлов которые видны
        FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Книга Excel (*.xlsx)", "xlsx"); // Фильтр: название фильтра, Расширения файлов которые видны
        fileSave.addChoosableFileFilter(xlsFilter);
        fileSave.addChoosableFileFilter(xlsxFilter);
        fileSave.setFileSelectionMode(JFileChooser.FILES_ONLY); // Выбор только файлов (не директорий)
        fileSave.setAcceptAllFileFilterUsed(false); // Убираем фильтр всех файлов
        fileSave.setSelectedFile(new File("Новый документ")); // Изначальное указание имени файла

        // Следим, что файл был правильно назван - решает ошибку с введением в имя символов ? и *
        fileSave.addPropertyChangeListener(JFileChooser.CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Если в названии файла присутствует некорректный символ (?, * и т.п.) - 
                // то почему то автоматически создается фильтр с названием файла.
                // Отслеживаем это, удаляем такой фильтр и кидаем всплывающую подсказку
                if (fileSave.isShowing()) {
                    fileSave.removeChoosableFileFilter(fileSave.getFileFilter()); // удаляем все фильтры (отдельно фильтр удалить не получилось
                    fileSave.addChoosableFileFilter(xlsFilter); // добавляем фильтры, которые были
                    fileSave.addChoosableFileFilter(xlsxFilter);
                    fileSave.setFileFilter(xlsFilter); // устанавливаем в качестве выбранного фильтр по умолчанию
                    fileSave.setAcceptAllFileFilterUsed(false); // Убираем фильтр всех файлов
//                    optionPane.showMessageDialog(fileSave, "В имени файла не должно быть символов: ?,*,>,<,:,/,|,'\',\""); // всплывающая подсказка
                    fileSave.setApproveButtonToolTipText("В имени файла не должно быть символов: ?,*,>,<,:,/,|,'\',\"");
                }
            }
        });

        // метод клонирования существующего шаблона в директорию и с именем, выбранными с помощью JFileChooser (компонента выбора файлов). Далее в него запишется таблица
        saveAswithPattern = new SaveAsPattern(fileSave, pattern);
    }

    public String getFullFileName() {
        return saveAswithPattern.getFullFileName();
    }

}
