package ru.jtable.modelListener;

import javax.swing.table.TableModel;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.view.JBroTable;
import ru.jtable.modelListener.coefficientSelection.CoefficientSelection;

/**
 * Подсчет суммы внутри одного направления по видам движения ПЕ. Графа ИТОГО.
 */
public class SumInOneDirectionInOneRowPE {

    // Переменные в которые будем записывать значения из промежуточных столбцов по 4 направлениям для дальнейшего подсчета суммы
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private double sum; // переменная для хранения суммы расчета внутри одного направления

    private CoefficientSelection pe = new CoefficientSelection(); // переменная для расчета коэффициента

    // Передаем сюда вид таблицы для правильного выбора коэффициента для ПЕ
    // !!! Не работает при перемене местами столбцов !!! Ни ПЕ, ни ФЕ. Проблема в неправильной передаче данных о смене положения столбца. Не устранено
    public void counting(JBroTable table, int row, int column, String rowName, String destinationName, String typeOfStatement) {
        // Все fields (это столбцы конечные, которые уже не расщепляются) и groupFields (их как parent - родителей fields) перебираем
        //  (чтобы перебирать передаем column переведенный в модель хэдера и вычитаем не учитываемы столбцы)
        TableModel model = table.getModel();
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
        int modelColumn = table.convertColumnIndexToModel(column - unAccountedColumns); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера
        ModelField columnToModelField = table.getData().getFields()[modelColumn]; // получаем field, который соответствует столбцу ячейки, в которой что то было изменено

        // Определяем строку в которой изменилось значение
        int rowTemp = - 1;
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
            String rowValue = ((String) table.getModel().getData().getRows()[i].getValue(1)); // значение во 2 столбце в проверяемой ячейке
            if (rowValue.equalsIgnoreCase(rowName)) { // находим строку,
                rowTemp = i; // сохраняем номер этой строки
            }
        }
        // Если ячейка, в которой изменилась информация, находится в группе нужного нам направления destinationName (например Направление 1) и
        // Выбрана определенная строка. Также выполняется проверка на наличие родителя и родителя родителя (для последних столбцов это актуально)
        if (columnToModelField.getParent() != null && columnToModelField.getParent().getParent() != null) {
            if (columnToModelField.getParent().getParent().getIdentifier().startsWith(destinationName) && rowTemp == row) {
                // Определяем столбец, куда записать итоговую сумму подсчетов. Это будет столбец "Итого" в переданной группе Направления (destinationName)
                int columnTemp = -1;
                for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) {
                    if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Итого")
                            && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                        columnTemp = i;
                    }
                }

                // Расчет 
                // Идентифицируем столбец внутри направления
                if (columnToModelField.getIdentifier().startsWith("ФЕ Налево") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column); // получаем данные из ячейки
                    value1 = Integer.valueOf(String.valueOf(data)); // переводим значение из текста в числовой формат
                    sum = (value1 + value2 + value3 + value4) * pe.getCoefficient(table, rowTemp, typeOfStatement); // рассчитываем значение суммы умноженной на подходящий коэффициент
                    // устанавливаем значение в ячейку ПЕ, соответствующую выбранной ФЕ
                    model.setValueAt(
                            String.valueOf(fmt(value1 * pe.getCoefficient(table, rowTemp, typeOfStatement))),
                            row,
                            getColumnFromIdentifier(table, destinationName, "ПЕ Налево"));
                    model.setValueAt(fmt(sum), row, columnTemp); // устанавливаем значение в ячейку Итого для ПЕ, округленное до 2 знаков после запятой 
                }
                if (columnToModelField.getIdentifier().startsWith("ФЕ Прямо") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column);
                    value2 = Integer.valueOf(String.valueOf(data));
                    sum = (value1 + value2 + value3 + value4) * pe.getCoefficient(table, rowTemp, typeOfStatement);
                    model.setValueAt(
                            String.valueOf(fmt(value2 * pe.getCoefficient(table, rowTemp, typeOfStatement))),
                            row,
                            getColumnFromIdentifier(table, destinationName, "ПЕ Прямо"));
                    model.setValueAt(fmt(sum), row, columnTemp);
                }
                if (columnToModelField.getIdentifier().startsWith("ФЕ Направо") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column);
                    value3 = Integer.valueOf(String.valueOf(data));
                    sum = (value1 + value2 + value3 + value4) * pe.getCoefficient(table, rowTemp, typeOfStatement);
                    model.setValueAt(
                            String.valueOf(fmt(value3 * pe.getCoefficient(table, rowTemp, typeOfStatement))),
                            row,
                            getColumnFromIdentifier(table, destinationName, "ПЕ Направо"));
                    model.setValueAt(fmt(sum), row, columnTemp);
                }
                if (columnToModelField.getIdentifier().startsWith("ФЕ Разворот") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column);
                    value4 = Integer.valueOf(String.valueOf(data));
                    sum = (value1 + value2 + value3 + value4) * pe.getCoefficient(table, rowTemp, typeOfStatement);
                    model.setValueAt(
                            String.valueOf(fmt(value4 * pe.getCoefficient(table, rowTemp, typeOfStatement))),
                            row,
                            getColumnFromIdentifier(table, destinationName, "ПЕ Разворот"));
                    model.setValueAt(fmt(sum), row, columnTemp);
                }
            }
        }
    }

    // Получаем столбец в нужном Направлении и внутри него выбираем столбец с требуемым идентификатором
    private int getColumnFromIdentifier(JBroTable table, String destinationName, String columnIdentifier) {
        int columnTemp = 0;
        for (int i = 0; i < table.getData().getFieldsCount(); i++) {
            if (table.getData().getFields()[i].getIdentifier().startsWith(columnIdentifier)
                    && table.getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnTemp = i;
            }
        }
        return columnTemp;
    }

    // Округление до двух чисел после запятой
    private String fmt(double d) {
        double dFormat = (double) Math.round(d * 100) / 100;
        return String.valueOf(dFormat);
    }

}
