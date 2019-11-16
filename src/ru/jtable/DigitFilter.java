package ru.jtable;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.text.*;

/**
 * Этот класс фильтрует значения в ячейках таблицы. Разрешает вводить только
 * цифры и не более 4 символов (т.е. число не больше 9999).
 */
class DigitFilter extends DocumentFilter {

    // текстовое поле, в котором будет проверятся количество символов
    private JTextField field = new JTextField();
    private String fullText = "";
    private int limit = 5;

    // конструктор данного класса, для получения текстового поля на проверку
    public DigitFilter(JTextField field) {
        this.field = field;
    }

    // Проверка вводимой информации
    @Override
    public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
            throws BadLocationException {
        Document doc = field.getDocument();
        // Проверяем длину строки в текстовом поле (длина текста который уже есть + длина текста вводимого - длина текста, если он был вставлен в обход прямого редактирования ячейки?
        int length = doc.getLength() + str.length() - len;
        if (length <= limit) {
            fb.replace(off, len, str.replaceAll("[^0-9]", ""), attr);  // убираем все символы кроме цифр (заменяем их на пробелы одновременно с введением их)
        }
        fullText = doc.getText(0, length) + str; // Полностью имеющийся текст в ячейке (тот, что был + тот, что вводят)
    }

    // Проверяет вставляемую извне информацию
    @Override
    public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
            throws BadLocationException {
        Document doc = field.getDocument();
        int length = doc.getLength() + str.length(); // проверяем длину строки в текстовом поле
        // Если символов меньше, чем limit - то вводим текст по фильтру. Если больше 4, то ничего ввести нельзя
        if (length <= limit) { // Проверяем длину текста в ячейке = тот, что там уже есть + тот, что вводится
            fb.insertString(off, str.replaceAll("[^0-9]", ""), attr);  // убираем все символы кроме цифр
        }
        fullText = doc.getText(0, length) + str; // Полностью имеющийся текст в ячейке (тот, что был + тот, что вводят)
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    public String getFullText() {
        return fullText;
    }

    private String fmt(double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else {
            return String.format("%s", d);
        }
    }
}
