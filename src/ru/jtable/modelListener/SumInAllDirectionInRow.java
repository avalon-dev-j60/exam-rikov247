package ru.jtable.modelListener;

import org.quinto.swing.table.view.JBroTable;

/**
 * Подсчет суммы по всем рядам (в каждом ряду все направления). Графа ИТОГО. <n>
 * Для улиц с > 4 количеством направлений нужно добавлять еще
 * переменных
 */
public class SumInAllDirectionInRow {

    // Переменные для подсчета суммы по одному направлению в строке (FE - физические единицы, PE - приведенные единицы)
    private SumInOneDirectionInOneRowFE direction1FE = new SumInOneDirectionInOneRowFE();
    private SumInOneDirectionInOneRowPE direction1PE = new SumInOneDirectionInOneRowPE();

    private SumInOneDirectionInOneRowFE direction2FE = new SumInOneDirectionInOneRowFE();
    private SumInOneDirectionInOneRowPE direction2PE = new SumInOneDirectionInOneRowPE();

    private SumInOneDirectionInOneRowFE direction3FE = new SumInOneDirectionInOneRowFE();
    private SumInOneDirectionInOneRowPE direction3PE = new SumInOneDirectionInOneRowPE();

    private SumInOneDirectionInOneRowFE direction4FE = new SumInOneDirectionInOneRowFE();
    private SumInOneDirectionInOneRowPE direction4PE = new SumInOneDirectionInOneRowPE();

    // Передаем сюда вид таблицы для правильного выбора коэффициента для ПЕ
    public void sumInAllDirectionInRow(JBroTable table, int row, int column, String rowName, String typeOfStatement) {
        // Считаем сумму по выбранному направлению.
        direction1FE.counting(table, row, column, rowName, "Направление 1");
        direction1PE.counting(table, row, column, rowName, "Направление 1", typeOfStatement);

        direction2FE.counting(table, row, column, rowName, "Направление 2");
        direction2PE.counting(table, row, column, rowName, "Направление 2", typeOfStatement);

        direction3FE.counting(table, row, column, rowName, "Направление 3");
        direction3PE.counting(table, row, column, rowName, "Направление 3", typeOfStatement);

        direction4FE.counting(table, row, column, rowName, "Направление 4");
        direction4PE.counting(table, row, column, rowName, "Направление 4", typeOfStatement);
    }

}
