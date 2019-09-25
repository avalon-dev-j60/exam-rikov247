package tests.jBro;

import javax.swing.JTextField;
import javax.swing.text.*;

/**
 * Этот класс фильтрует значения в ячейках таблицы. Разрешает вводить только
 * цифры и не более 4 символов (т.е. число не больше 9999).
 */
class DigitFilter extends DocumentFilter {

    // текстовое поле, в котором будет проверятся количество символов
    JTextField field = new JTextField();

    // конструктор данного класса, для получения текстового поля на проверку
    public DigitFilter(JTextField field) {
        this.field = field;
    }

    // 
    @Override
    public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
            throws BadLocationException {
        int length = field.getDocument().getLength(); // проверяем длину строки в текстовом поле
        // Если символов меньше, чем 4 - то вводим текст по фильтру. Если больше 4, то ничего ввести нельзя
        if (length + str.length() <= 4) {
            fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // убираем символы не цифры 
        }
    }

    @Override
    public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
            throws BadLocationException {
        int length = field.getDocument().getLength();
        if (length + str.length() <= 4) {
            fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // убираем символы не цифры
        }
    }

}
