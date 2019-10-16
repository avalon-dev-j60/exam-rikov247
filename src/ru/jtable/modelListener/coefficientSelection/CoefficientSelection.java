package ru.jtable.modelListener.coefficientSelection;

import org.quinto.swing.table.view.JBroTable;

/**
 * Метод для выбора значения коэффициента
 */
public class CoefficientSelection {

    private KindOfTransportNow transportNow = new KindOfTransportNow();
    private KindOfTransportFuture transportFuture = new KindOfTransportFuture();

    // Получаем коэффиуиент в зависимости от названия строки. Правила выбора подключаем внутри отдельными классами
    public double getCoefficient(JBroTable table, int row, String typeOfStatement) {
        // Определяем строку
        String rowValue = String.valueOf(table.getData().getRows()[row].getValue(1)); // значение во 2 столбце в проверяемой ячейке (вид транспорта)

        // Выбор модели расчета коэффициента в зависимости от переданного вида таблицы (разное количество строк
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            return transportNow.getCoefficient(rowValue); // метод возвращает рассчитанный коэффициент исходя из вида таблицы и строки
        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            return transportFuture.getCoefficient(rowValue);
        } else {
            return 1.0; // для непредвиденных случаев
        }
    }

}
