package ru.jtable;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.quinto.swing.table.view.JBroTable;
import ru.cartogram.CreateCartogram;
import ru.jtable.modelListener.SumInAllDirectionInRow;
import ru.jtable.modelListener.TotalSumLastColumns;
import ru.jtable.modelListener.TotalSumLastRow;
import ru.jtable.modelListener.cartograma.CountingOneDirection;

/**
 * Слушатель изменения данных в таблице. Здесь реализуем формулы как в Excel
 */
public class ModelListener implements TableModelListener {

    private JBroTable table;
    private String[] kindOfTransport; // массив с названиями строк (для последующей идентификации строк
    private String typeOfStatement;
    private CreateCartogram cartogram;

    // Переменные для инициализации подсчета суммы по каждой строке отдельно
    private SumInAllDirectionInRow carNumber1 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber2 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber3 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber4 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber5 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber6 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber7 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber8 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber9 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber10 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber11 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber12 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber13 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber14 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber15 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber16 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber17 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber18 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber19 = new SumInAllDirectionInRow();
    private SumInAllDirectionInRow carNumber20 = new SumInAllDirectionInRow();

    // Массив переменных подсчета, чтобы удобно сопоставлять название строки к каждой переменной
    private SumInAllDirectionInRow[] cars = new SumInAllDirectionInRow[]{
        carNumber1, carNumber2, carNumber3, carNumber4, carNumber5,
        carNumber6, carNumber7, carNumber8, carNumber9, carNumber10,
        carNumber11, carNumber12, carNumber13, carNumber14, carNumber15,
        carNumber16, carNumber17, carNumber18, carNumber19, carNumber20};

    private TotalSumLastRow totalSumRow = new TotalSumLastRow(); // переменная для подсчета суммы по варианту движения в данном направлении (строка ИТОГО)
    private TotalSumLastColumns totalSumColumns = new TotalSumLastColumns(); // переменная для подсчета суммы по варианту движения в данном направлении (строка ИТОГО)
    private CountingOneDirection countingOneDirection = new CountingOneDirection();

    public ModelListener(JBroTable table, String[] kindOfTransport, String typeOfStatement, CreateCartogram cartogram) {
        this.table = table;
        this.kindOfTransport = kindOfTransport;
        this.typeOfStatement = typeOfStatement;
        this.cartogram = cartogram;
    }

    // Слушатель изменения данных в таблице. Вызывается КАЖДЫЙ РАЗ, когда данные в таблице меняются (даже если ты меняешь одни данные, а они меняют другие, то метод вызовется снова   
    // Внутри не стоит инициализировать переменные через заполненный конструктор, так как он будет постоянно создаваться заново. Поэтому нужно использовать методы заранее созданных переменных
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = table.getModel();

        // Если в ячейке удалить полностью текст, то запишется 0
        Object dat = model.getValueAt(row, column);
        if (String.valueOf(dat).isBlank()) {
            dat = "0";
            model.setValueAt(dat, row, column);
        }

        // Считаем сумму для конкретной строки (kindOfTransport) по всем направления в этой строке. Строки перебираем. 
        // Итог: подсчет суммы по всем строкам.
        for (int i = 0; i < kindOfTransport.length - 1; i++) {
            cars[i].sumInAllDirectionInRow(table, row, column, kindOfTransport[i], typeOfStatement);
        }

        totalSumRow.getSum(table, column); // Подсчет суммы по всем транспортным средствам в данном направлении в данном варианте движения (налево, направо и т.п)
        totalSumColumns.getSum(table, row, column, typeOfStatement); // Подсчет суммы по конкретному транспортному средству по всем столбцам в данной строке

        // Изменяем картограмму, в соответствии с изменениями в Таблице по всем 4 направлениям
        countingOneDirection.counting(table, row, column, "Направление 1", cartogram);
        countingOneDirection.counting(table, row, column, "Направление 2", cartogram);
        countingOneDirection.counting(table, row, column, "Направление 3", cartogram);
        countingOneDirection.counting(table, row, column, "Направление 4", cartogram);
    }

}
