package ru.jtable.modelListener;

import javax.swing.table.TableModel;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.view.JBroTable;
import ru.jtable.modelListener.coefficientSelection.CoefficientSelection;

/**
 * Подсчет суммы по всем строкам в каждом столбце (заполнение последней строки
 * таблицы)
 */
public class TotalSumLastColumns {

    // Переменные в которые будем записывать значения из промежуточных столбцов ИТОГО
    private int value1 = 0;
    private int value2 = 0;
    private int value3 = 0;
    private int value4 = 0;
    private int value5 = 0;
    private int value6 = 0;

    private int sum; // переменная для хранения суммы расчета внутри одного направления

    private CoefficientSelection pe = new CoefficientSelection(); // переменная для расчета коэффициента

    // Передаем сюда вид таблицы для правильного выбора коэффициента для ПЕ
    public void getSum(JBroTable table, int row, int column, String typeOfStatement) {
        TableModel model = table.getModel();
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
        int modelColumn = table.convertColumnIndexToModel(column - unAccountedColumns); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера
        ModelField columnToModelField = table.getData().getFields()[modelColumn]; // получаем field, который соответствует столбцу ячейки, в которой что то было изменено

        // Определяем столбец, куда записать итоговую сумму подсчетов
        int columnTotalFE = -1;
        int columnTotalPE = -1;
        for (int q = 0; q < table.getModel().getData().getFieldsCount(); q++) {
            if (table.getModel().getData().getFields()[q].getIdentifier().startsWith("ФЕ Всего")) {
                columnTotalFE = q;
            }
            if (table.getModel().getData().getFields()[q].getIdentifier().startsWith("ПЕ Всего")) {
                columnTotalPE = q;
            }
        }

        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого1") && columnTotalFE > 0) {
            Object data = model.getValueAt(row, column); // получаем данные из ячейки
            value1 = Integer.valueOf(String.valueOf(data)); // переводим значение из текста в числовой формат
            sum = (value1 + value2 + value3 + value4); // рассчитываем значение суммы 
            // устанавливаем значение суммы, умноженной на подходящий коэффициент, в ячейку ПЕ, соответствующую выбранной ФЕ
            model.setValueAt(
                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
                    row, columnTotalPE);
            model.setValueAt(sum, row, columnTotalFE); // устанавливаем значение в ячейку Итого для ФЕ, округленное до 2 знаков после запятой 
        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого2") && columnTotalFE > 0) {
            Object data = model.getValueAt(row, column);
            value2 = Integer.valueOf(String.valueOf(data));
            sum = (value1 + value2 + value3 + value4);
            model.setValueAt(
                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
                    row, columnTotalPE);
            model.setValueAt(sum, row, columnTotalFE);
        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого3") && columnTotalFE > 0) {
            Object data = model.getValueAt(row, column);
            value3 = Integer.valueOf(String.valueOf(data));
            sum = (value1 + value2 + value3 + value4);
            model.setValueAt(
                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
                    row, columnTotalPE);
            model.setValueAt(sum, row, columnTotalFE);
        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого4") && columnTotalFE > 0) {
            Object data = model.getValueAt(row, column);
            value4 = Integer.valueOf(String.valueOf(data));
            sum = (value1 + value2 + value3 + value4);
            model.setValueAt(
                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
                    row, columnTotalPE);
            model.setValueAt(sum, row, columnTotalFE);
        }

    }

    // Форматирование значения. Если можно представить как целое число - представляем, если нет, то округляем до двух значящих чисел после запятой
    public static String fmt(double d) {
        if (d == (long) d) { // если число можно представить как long (int), то есть целое число, то
            return String.format("%d", (long) d); // записываем число как целое и выводим в виде строки
        } else {
            return String.format("%s", (double) Math.round(d * 100) / 100); // Округляем число до двух знаков после запятой и записываем как строку (%s)
        }
    }

    // Получаем столбец по его идентификатору
    private int getColumnFromIdentifier(JBroTable table, String columnIdentifier) {
        int columnTemp = 0;
        for (int i = 0; i < table.getData().getFieldsCount(); i++) {
            if (table.getData().getFields()[i].getIdentifier().startsWith(columnIdentifier)) {
                columnTemp = i;
            }
        }
        return columnTemp;
    }

}
