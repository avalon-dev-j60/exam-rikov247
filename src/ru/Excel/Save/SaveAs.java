package ru.Excel.Save;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import org.quinto.swing.table.view.JBroTable;

/**
 * В данном классе реализован выбор места и имени сохраняемого файла (с помощью
 * JFileChooser). Реализован ввод только правильного имени файла, а также фильтр
 * файлов по расширению.<n>
 * Внутри используются отдельные классы непосредственного считывания данных с
 * таблицы Java (JTable) и переноса в excel файл.
 */
public class SaveAs extends JPanel {

    JOptionPane optionPane = null; // переменная для всплывающей подсказки

    public SaveAs(JBroTable table, JFileChooser fileSave) throws IOException {
        boolean openAgain = true; // логическая переменная для возможности циклического повторного открытия окна сохранения файла

        again:
        while (openAgain) {
            // Показываем диалог с названием "Сохранить файл"
            int ret = fileSave.showDialog(this, "Сохранить файл");

            if (ret == JFileChooser.CANCEL_OPTION || ret == JFileChooser.ERROR_OPTION) {
                break; // выходим из цикла отображения окна сохранения файлов
            }

            // Если нажали на кнопку сохранить - все хорошо и файл может быть записан в выбранную директорию, то:
            if (ret == JFileChooser.APPROVE_OPTION && fileSave.getCurrentDirectory().canWrite()) {
                String extension;
                // Если выбран фильтр с расширением xls, то добавляем к названию файла: .xls
                String pathName = fileSave.getCurrentDirectory().getAbsolutePath(); // только путь к файлу, без имени файла
                String fileName = fileSave.getSelectedFile().getName(); // имя файла, без пути к нему
                String fullFileName = fileSave.getSelectedFile().toString(); // имя файла вместе с полным путем к нему
                // ЕСЛИ УСТАНОВЛЕН ФИЛЬТР .xls
                if (fileSave.getFileFilter().getDescription().endsWith("(*.xls)")) { // если установлен фильтр .xls,
                    extension = ".xls";
                    fullFileName += extension; // дописываем расширение файла к полному пути файла
                    // ПРОВЕРКА, есть ли в выбранной директории файл с таким же именем
                    boolean equals = false;
                    File directory = new File(pathName); // объект типа File, указывающий на каталог        
                    for (int i = 0; i < directory.listFiles().length; i++) { // перебираем все файлы в указанной директории
                        // если файл из директории (имя+расширение) совпадает с сохраняемым нами (имя + расширение / имя, в котором уже написано расширение), то 
                        if (directory.listFiles()[i].getName().equalsIgnoreCase(fileName + extension)
                                || directory.listFiles()[i].getName().equalsIgnoreCase(fileName)) {
                            equals = true; // если нашли такой же файл, то (переводим лог. переменную в положение true): 
                        }
                    }
                    // ЕСЛИ ЕСТЬ, то:
                    if (equals) { // Открываем вспомогательное окно с подтверждением сохранения
                        int result = JOptionPane.showConfirmDialog(
                                fileSave,
                                new String[]{fileName + " уже существует.", "Вы хотите заменить его?"},
                                "Подтвердить сохранение в виде",
                                JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new AsHSSF(table, fullFileName.replaceAll(extension, "") + extension);  // производим сохранение таблицы Java (table) в файл (filename) с расширением ".xls" (предварительно удаляем любой текст с названием расширения и добавляем расширение - чтобы избежать повторного введения расширения;
                            // всплывающая подсказка о сохранении файла
                            optionPane.showMessageDialog(fileSave, "Файл с именем '" + fileSave.getSelectedFile().getName().replaceAll(">|<|:|/|'\'|\"|" + extension, "") + "' сохранен");
                            break; // выходим из цикла отображения окна сохранения файлов
                        } else if (result == JOptionPane.NO_OPTION) {
                            continue again;// возвращение обратно в меню сохранения файлов
                        }
                        // ЕСЛИ НЕТ, то
                    } else { // если файлов с таким же именем нет, то 
                        if (fileSave.getSelectedFile().getName().
                                equalsIgnoreCase(fileSave.getSelectedFile().getName().replaceAll(">|<|:|/|\\|\"", ""))) {
                            new AsHSSF(table, fullFileName);  // производим сохранение таблицы Java (table) в файл (filename) с расширением ".xls";
                        } else {
                            continue again;// возвращение обратно в меню сохранения файлов
                        }
                        // всплывающая подсказка о сохранении файла
                        optionPane.showMessageDialog(fileSave, "Файл с именем '" + fileSave.getSelectedFile().getName().replaceAll(">|<|:|/|'\'|\"", "") + "' сохранен");
                        break; // выходим из цикла отображения окна сохранения файлов
                    }
                }
                // Если выбран фильтр с расширением xlsx, то добавляем к названию файла: .xlsx
                if (fileSave.getFileFilter().getDescription().endsWith("(*.xlsx)")) { // если установлен фильтр .xlsx,
                    extension = ".xlsx";
                    fullFileName += extension; // дописываем расширение файла к полному пути файла
                    boolean equals = false;
                    File directory = new File(pathName); // объект типа File, указывающий на каталог        
                    for (int i = 0; i < directory.listFiles().length; i++) { // перебираем все файлы в указанной директории
                        if (directory.listFiles()[i].getName().equalsIgnoreCase(fileName + extension)
                                || directory.listFiles()[i].getName().equalsIgnoreCase(fileName)) {
                            equals = true; // если нашли такой же файл, то (переводим лог. переменную в положение true): 
                        }
                    }
                    if (equals) { // Открываем вспомогательное окно с подтверждением сохранения
                        int result = JOptionPane.showConfirmDialog(
                                fileSave,
                                new String[]{fileName + " уже существует.", "Вы хотите заменить его?"},
                                "Подтвердить сохранение в виде",
                                JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            new AsXSSF(table, fullFileName.replaceAll(extension, "") + extension);  // производим сохранение таблицы Java (table) в файл (filename) с расширением ".xls" (предварительно удаляем любой текст с названием расширения и добавляем расширение - чтобы избежать повторного введения расширения;
                            // всплывающая подсказка о сохранении файла
                            optionPane.showMessageDialog(fileSave, "Файл с именем '" + fileSave.getSelectedFile().getName().replaceAll(">|<|:|/|'\'|\"|" + extension, "") + "' сохранен");
                            break; // выходим из цикла отображения окна сохранения файлов
                        } else if (result == JOptionPane.NO_OPTION) {
                            continue again;// возвращение обратно в меню сохранения файлов
                        }
                    } else { // если файлов с таким же именем нет, то 
                        if (fileSave.getSelectedFile().getName().
                                equalsIgnoreCase(fileSave.getSelectedFile().getName().replaceAll(">|<|:|/|\\|\"", ""))) {
                            new AsXSSF(table, fullFileName);  // производим сохранение таблицы Java (table) в файл (filename) с расширением ".xlsx";
                        } else {
                            continue again;// возвращение обратно в меню сохранения файлов
                        }
                        // всплывающая подсказка о сохранении файла
                        optionPane.showMessageDialog(fileSave, "Файл с именем '" + fileSave.getSelectedFile().getName().replaceAll(">|<|:|/|'\'|\"", "") + "' сохранен");
                        break; // выходим из цикла отображения окна сохранения файлов
                    }
                }
            } // ЕСЛИ СОХРАНЯТЬ СЮДА НЕЛЬЗЯ, то
            else { // показываем всплывающую подсказку
                if (!fileSave.getCurrentDirectory().canWrite()) {
                    optionPane.showMessageDialog(fileSave, "Сюда нельзя сохранить файл! Выберите другую директорию"); // всплывающая подсказка
                }
            }
        }
    }
}
