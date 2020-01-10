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

//        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого1") && columnTotalFE > 0) {
//            Object data = model.getValueAt(row, column); // получаем данные из ячейки
//            value1 = Integer.valueOf(String.valueOf(data)); // переводим значение из текста в числовой формат
//            sum = (value1 + value2 + value3 + value4); // рассчитываем значение суммы 
//            // устанавливаем значение суммы, умноженной на подходящий коэффициент, в ячейку ПЕ, соответствующую выбранной ФЕ
//            model.setValueAt(
//                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
//                    row, columnTotalPE);
//            model.setValueAt(sum, row, columnTotalFE); // устанавливаем значение в ячейку Итого для ФЕ, округленное до 2 знаков после запятой 
//            System.out.println("1: " + sum);
//        }
//        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого2") && columnTotalFE > 0) {
//            Object data = model.getValueAt(row, column);
//            value2 = Integer.valueOf(String.valueOf(data));
//            sum = (value1 + value2 + value3 + value4);
//            model.setValueAt(
//                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
//                    row, columnTotalPE);
//            model.setValueAt(sum, row, columnTotalFE);
//            System.out.println("2: " + sum);
//        }
//        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого3") && columnTotalFE > 0) {
//            Object data = model.getValueAt(row, column);
//            value3 = Integer.valueOf(String.valueOf(data));
//            sum = (value1 + value2 + value3 + value4);
//            model.setValueAt(
//                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
//                    row, columnTotalPE);
//            model.setValueAt(sum, row, columnTotalFE);
//            System.out.println("3: " + sum);
//            System.out.println(value1);
//            System.out.println(value2);
//            System.out.println(value3);
//            System.out.println(value4);
//        }
//        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого4") && columnTotalFE > 0) {
//            Object data = model.getValueAt(row, column);
//            value4 = Integer.valueOf(String.valueOf(data));
//            sum = (value1 + value2 + value3 + value4);
//            model.setValueAt(
//                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
//                    row, columnTotalPE);
//            model.setValueAt(sum, row, columnTotalFE);
//            System.out.println("4: " + sum);
//        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Итого1")
                || columnToModelField.getIdentifier().startsWith("ФЕ Итого2")
                || columnToModelField.getIdentifier().startsWith("ФЕ Итого3")
                || columnToModelField.getIdentifier().startsWith("ФЕ Итого4")) {
            
            value1 = getColumnFromIdentifier(table, "ФЕ Итого1") > 0 ? Integer.valueOf(String.valueOf(model.getValueAt(row, getColumnFromIdentifier(table, "ФЕ Итого1")))) : 0;
            value2 = getColumnFromIdentifier(table, "ФЕ Итого2") > 0 ? Integer.valueOf(String.valueOf(model.getValueAt(row, getColumnFromIdentifier(table, "ФЕ Итого2")))) : 0;
            value3 = getColumnFromIdentifier(table, "ФЕ Итого3") > 0 ? Integer.valueOf(String.valueOf(model.getValueAt(row, getColumnFromIdentifier(table, "ФЕ Итого3")))) : 0;
            value4 = getColumnFromIdentifier(table, "ФЕ Итого4") > 0 ? Integer.valueOf(String.valueOf(model.getValueAt(row, getColumnFromIdentifier(table, "ФЕ Итого4")))) : 0;
            sum = (value1 + value2 + value3 + value4);
            model.setValueAt(
                    String.valueOf(fmt(sum * pe.getCoefficient(table, row, typeOfStatement))),
                    row, getColumnFromIdentifier(table, "ПЕ Всего"));
            model.setValueAt(sum, row, getColumnFromIdentifier(table, "ФЕ Всего"));
        }

    }

//    // Форматирование значения. Если можно представить как целое число - представляем, если нет, то округляем до двух значящих чисел после запятой
//    public static String fmt(double d) {
//        if (d == (long) d) { // если число можно представить как long (int), то есть целое число, то
//            return String.format("%d", (long) d); // записываем число как целое и выводим в виде строки
//        } else {
//            return String.format("%s", (double) Math.round(d * 100) / 100); // Округляем число до двух знаков после запятой и записываем как строку (%s)
//        }
//    }
    
    // Округление
    private String fmt(double d) {
        double dFormat = (double) Math.round(d * 100) / 100; // Округление до двух чисел после запятой
        int iFormat = (int) Math.ceil(dFormat);
        return String.valueOf(iFormat);
    }

    // Получаем столбец по его идентификатору
    private int getColumnFromIdentifier(JBroTable table, String columnIdentifier) {
        int columnTemp = -1;
        for (int i = 0; i < table.getData().getFieldsCount(); i++) {
            if (table.getData().getFields()[i].getIdentifier().startsWith(columnIdentifier)) {
                columnTemp = i;
            }
        }
        return columnTemp;
    }

}
