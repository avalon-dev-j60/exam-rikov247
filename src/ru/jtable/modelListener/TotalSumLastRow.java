package ru.jtable.modelListener;

import javax.swing.table.TableModel;
import org.quinto.swing.table.view.JBroTable;

/**
 * Подсчет суммы по всем строкам в каждом столбце (заполнение последней строки
 * таблицы)
 */
public class TotalSumLastRow {

    // Переменные для считывания значения в выбранном столбце по каждой строке. Одно значение - одна строка.
    private double value1 = 0;
    private double value2 = 0;
    private double value3 = 0;
    private double value4 = 0;
    private double value5 = 0;
    private double value6 = 0;
    private double value7 = 0;
    private double value8 = 0;
    private double value9 = 0;
    private double value10 = 0;
    private double value11 = 0;
    private double value12 = 0;
    private double value13 = 0;
    private double value14 = 0;
    private double value15 = 0;
    private double value16 = 0;
    private double value17 = 0;
    private double value18 = 0;
    private double[] values = {
        value1, value2, value3, value4, value5, value6, value7,
        value8, value9, value10, value11, value12, value13, value14,
        value15, value16, value17, value18
    };

    private double sum; // переменная для хранения суммы расчета внутри одного направления

    public void getSum(JBroTable table, int column) {
        TableModel model = table.getModel();
        // column - передаваемый столбец для подсчета

        // Перебираем строки не считая последнюю
        for (int i = 0; i < table.getModel().getData().getRows().length - 1; i++) {
            Object data = model.getValueAt(i, column); // получаем данные из ячейки
            values[i] = Double.valueOf(String.valueOf(data)); // переводим значение из текста в числовой формат
            sum = 0;
            for (int k = 0; k < values.length; k++) {
                sum += values[k]; // суммируем все значения (по всем строкам) в выбранном столбце
            }
        }
        table.getModel().getData().getRows()[table.getModel().getData().getRows().length - 1].setValue(column, fmt(sum)); // записываем значение в ячейку в переданном столбце, в последней строке
    }

    // Форматирование значения. Если можно представить как целое число - представляем, если нет, то округляем до двух значящих чисел после запятой
    public static String fmt(double d) {
        if (d == (long) d) { // если число можно представить как long (int), то есть целое число, то
            return String.format("%d", (long) d); // записываем число как целое и выводим в виде строки
        } else {
            return String.format("%s", (double) Math.round(d * 100) / 100); // Округляем число до двух знаков после запятой и записываем как строку (%s)
        }
    }

}
