package ru.jtable.modelListener;

import javax.swing.table.TableModel;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.view.JBroTable;

/**
 * Подсчет суммы внутри одного направления по видам движения ФЕ. Графа ИТОГО.
 */
public class SumInOneDirectionInOneRowFE {

    // Переменные в которые будем записывать значения из промежуточных столбцов по 4 направлениям для дальнейшего подсчета суммы
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private int sum; // переменная для хранения суммы расчета внутри одного направления

    // !!! Не работает при перемене местами столбцов !!! Ни ПЕ, ни ФЕ. Проблема в неправильной передаче данных о смене положения столбца. Не устранено
    public void counting(JBroTable table, int row, int column, String rowName, String destinationName) {
        // Все fields (это столбцы конечные, которые уже не расщепляются) и groupFields (их как parent - родителей fields) перебираем
        //  (чтобы перебирать передаем column переведенный в модель хэдера и вычитаем не учитываемы столбцы)

        TableModel model = table.getModel();
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
        int modelColumn = table.convertColumnIndexToModel(column - unAccountedColumns); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера
        ModelField columnToModelField = table.getData().getFields()[modelColumn]; // получаем field, который соответствует столбцу ячейки, в которой что то было изменено

        // Определяем строку в которой изменилось значение
        int rowTemp = - 1;
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
            String rowValue = (String.valueOf(table.getModel().getData().getRows()[i].getValue(1))); // значение во 2 столбце в проверяемой ячейке
            if (rowValue.equalsIgnoreCase(rowName)) { // находим строку,
                rowTemp = i; // сохраняем номер этой строки
            }
        }
        // Если ячейка, в которой изменилась информация, находится в группе нужного нам направления destinationName (например Направление 1) b
        // Выбрана определенная строка. Также выполняется проверка на наличие родителя и родителя родителя (для последних столбцов это актуально)
        if (columnToModelField.getParent() != null && columnToModelField.getParent().getParent() != null) {
            if (columnToModelField.getParent().getParent().getIdentifier().startsWith(destinationName) && rowTemp == row) {
                // Определяем столбец, куда записать итоговую сумму подсчетов. Это будет столбец "Итого" в переданной группе Направления (destinationName)
                int columnTemp = -1;
                for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) {
                    if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Итого")
                            && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                        columnTemp = i;
                    }
                }

                // Рассчет
                // Идентифицируем столбец внутри направления
                if (columnToModelField.getIdentifier().startsWith("ФЕ Налево") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column); // получаем данные из ячейки
                    value1 = Integer.valueOf(String.valueOf(data)); // переводим значение из текста в числовой формат
                    sum = value1 + value2 + value3 + value4; // рассчитываем значение суммы
                    model.setValueAt(sum, row, columnTemp);
                }
                if (columnToModelField.getIdentifier().startsWith("ФЕ Прямо") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column);
                    value2 = Integer.valueOf(String.valueOf(data));
                    sum = value1 + value2 + value3 + value4;
                    model.setValueAt(sum, row, columnTemp);
                }
                if (columnToModelField.getIdentifier().startsWith("ФЕ Направо") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column);
                    value3 = Integer.valueOf(String.valueOf(data));
                    sum = value1 + value2 + value3 + value4;
                    model.setValueAt(sum, row, columnTemp);
                }
                if (columnToModelField.getIdentifier().startsWith("ФЕ Разворот") && columnTemp > 0) {
                    Object data = model.getValueAt(row, column);
                    value4 = Integer.valueOf(String.valueOf(data));
                    sum = value1 + value2 + value3 + value4;
                    model.setValueAt(sum, row, columnTemp);
                }
            }
        }
    }

}
