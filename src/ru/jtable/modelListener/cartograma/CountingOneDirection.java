package ru.jtable.modelListener.cartograma;

import javax.swing.table.TableModel;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.view.JBroTable;
import ru.cartogram.CreateCartogram;

/**
 * Подсчет суммы внутри одного направления по видам движения ФЕ. Графа ИТОГО.
 */
public class CountingOneDirection {

    // Переменные для хранения номера строки для конкретного вида транспорта
    private int rowCar;
    private int rowTruck1, rowTruck2, rowTruck3, rowTruck4, rowTruck5;
    private int rowBus1, rowBus2, rowBus3;
    private int[] rowTruckNow = {rowTruck1, rowTruck2, rowTruck3, rowTruck4, rowTruck5};
    private int[] rowBusNow = {rowBus1, rowBus2, rowBus3};
    private int rowTrolleyBus;
    private int rowTram;

    // ФЕ
    private int columnAroundFE, columnLeftFE, columnForwardFE, columnRightFE, columnTotalFE;
    // ПЕ
    private int columnAroundPE, columnLeftPE, columnForwardPE, columnRightPE, columnTotalPE;

    private String idName = ""; // ID переданного направления для подсчета

    // !!! Не работает при перемене местами столбцов !!! Ни ПЕ, ни ФЕ. Проблема в неправильной передаче данных о смене положения столбца. Не устранено
    public void counting(JBroTable table, int row, int column, String destinationName, CreateCartogram cartogram) {
        // Все fields (это столбцы конечные, которые уже не расщепляются) и groupFields (их как parent - родителей fields) перебираем
        //  (чтобы перебирать передаем column переведенный в модель хэдера и вычитаем не учитываемы столбцы)

        TableModel model = table.getModel();
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
        int modelColumn = table.convertColumnIndexToModel(column - unAccountedColumns); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера
        ModelField columnToModelField = table.getData().getFields()[modelColumn]; // получаем field, который соответствует столбцу ячейки, в которой что то было изменено

        // Если ячейка, в которой изменилась информация, находится в группе нужного нам направления destinationName (например Направление 1) и
        // Выбрана определенная строка. Также выполняется проверка на наличие родителя и родителя родителя (для последних столбцов это актуально)
        if (columnToModelField.getParent() != null && columnToModelField.getParent().getParent() != null) {
            if (columnToModelField.getParent().getParent().getIdentifier().startsWith(destinationName)) {
                // Определяем idName исходя из переданного destinationName (для соответствия Направление 1 = Up)
                IdentifierDestination(destinationName);
                // Определяем нужные строки
                identifierRow(table);
                // Определяем нужные столбцы
                IdentifierColumn(table, destinationName);
                // ВСЕ РАСЧЕТЫ ИДУТ БЕЗ СОХРАНЕНИЯ И ОБНОВЛЕНИЯ SVGCanvas. Поэтому в самом конце уже сохраняем все изменения в файл и его добавляем на SVGCanvas (таким образом обновляем картинку)
                // Все малые стрелки для одного направления (разворот, налево, прямо, направо). Определяется столбцами.
                TotalSmallArrow(model, cartogram, columnAroundFE, columnAroundPE, "around");
                TotalSmallArrow(model, cartogram, columnLeftFE, columnLeftPE, "left");
                TotalSmallArrow(model, cartogram, columnForwardFE, columnForwardPE, "forward");
                TotalSmallArrow(model, cartogram, columnRightFE, columnRightPE, "right");
                // Рассчет для стрелки В перекресток
                TotalInside(model, cartogram, idName);
                // Рассчет для стрелки ИЗ перекрестка. Все правильно работает.
                TotalOutside(cartogram, "Up_around", "Left_left", "Down_forward", "Right_right");
                TotalOutside(cartogram, "Right_around", "Up_left", "Left_forward", "Down_right");
                TotalOutside(cartogram, "Down_around", "Right_left", "Up_forward", "Left_right");
                TotalOutside(cartogram, "Left_around", "Down_left", "Right_forward", "Up_right");
                // сохраняем все изменения!
                cartogram.saveChangeValue();
            }
        }
    }

    // Считаем сумму ФЕ для вида транспорта (грузовики, автобусы и т.п.)
    private int TotalFE(TableModel model, int[] row, int columnTotal) {
        int TotalTruckSum = 0;
        for (int i = 0; i < row.length; i++) {
            Object TotalInsideObj = model.getValueAt(row[i], columnTotal);
            int TotalInside = Integer.valueOf(String.valueOf(TotalInsideObj));
            TotalTruckSum = TotalTruckSum + TotalInside;
        }
        return TotalTruckSum;
    }

    // Считаем сумму ПЕ для вида транспорта (грузовики, автобусы и т.п.)
    private double TotalPE(TableModel model, int[] row, int columnTotal) {
        double TotalTruckSum = 0;
        for (int i = 0; i < row.length; i++) {
            Object TotalInsideObj = model.getValueAt(row[i], columnTotal);
            double TotalInside = Double.valueOf(String.valueOf(TotalInsideObj));
            TotalTruckSum = TotalTruckSum + TotalInside;
        }
        return TotalTruckSum;
    }

    // Рассчет для стрелки ВНУТРЬ перекрестка. Цифры СНИЗУ
    private void TotalInside(TableModel model, CreateCartogram cartogram, String idName) {
        // Легковые авто
        Object TotalInsideCarObj = model.getValueAt(rowCar, columnTotalFE); // получаем данные из ячейки
        int TotalInsideCarFE = Integer.valueOf(String.valueOf(TotalInsideCarObj)); // переводим значение из текста в числовой формат
        double TotalInsideCarPE = Double.valueOf(String.valueOf(TotalInsideCarObj));
        // Грузовые авто
        int TotalTruckSumFE = TotalFE(model, rowTruckNow, columnTotalFE);
        double TotalTruckSumPE = TotalPE(model, rowTruckNow, columnTotalPE);
        // Автобусы
        int TotalBusSumFE = TotalFE(model, rowBusNow, columnTotalFE);
        double TotalBusSumPE = TotalPE(model, rowBusNow, columnTotalPE);
        // Троллейбусы
        int TotalInsideTrolleyBusFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnTotalFE)));
        double TotalInsideTrolleyBusPE = Double.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnTotalPE)));
        // Трамваи
        int TotalInsideTramFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTram, columnTotalFE)));
        double TotalInsideTramPE = Double.valueOf(String.valueOf(model.getValueAt(rowTram, columnTotalPE)));

        // Устанавливаем значение для стрелки "в перекресток" НАД ней
        cartogram.changeValueWithoutSave("Total_inside_" + idName,
                String.valueOf(TotalInsideCarFE) + "-"
                + String.valueOf(TotalTruckSumFE) + "-"
                + String.valueOf(TotalBusSumFE + TotalInsideTrolleyBusFE + TotalInsideTramFE));

        int TotalAutoSumFE = TotalInsideCarFE + TotalTruckSumFE + TotalBusSumFE + TotalInsideTrolleyBusFE + TotalInsideTramFE;
        double TotalAutoSumPE = TotalInsideCarPE + TotalTruckSumPE + TotalBusSumPE + TotalInsideTrolleyBusPE + TotalInsideTramPE;
        // Устанавливаем значение для стрелки "в перекресток" ПОД ней
        cartogram.changeValueWithoutSave("TotalSum_inside_" + idName,
                String.valueOf(TotalAutoSumFE),
                " (" + String.valueOf(fmt(TotalAutoSumPE)) + ")");
    }

    // Рассчет для одной стрелки (разворот, налево, прямо, направо) в одном направлении. Определяется столбцами 
    private void TotalSmallArrow(TableModel model, CreateCartogram cartogram, int columnFE, int columnPE, String smallDirection) {
        Object CarObj = model.getValueAt(rowCar, columnFE); // получаем данные из ячейки
        int CarFE = Integer.valueOf(String.valueOf(CarObj)); // переводим значение из текста в числовой формат
        double CarPE = Double.valueOf(String.valueOf(CarObj));

        int TruckSumFE = TotalFE(model, rowTruckNow, columnFE);
        double TruckSumPE = TotalPE(model, rowTruckNow, columnPE);

        int BusSumFE = TotalFE(model, rowBusNow, columnFE);
        double BusSumPE = TotalPE(model, rowBusNow, columnPE);
        // Троллейбусы
        int TrolleyBusSumFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnFE)));
        double TrolleyBusSumPE = Double.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnPE)));
        // Трамваи
        int TramSumFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTram, columnFE)));
        double TramSumPE = Double.valueOf(String.valueOf(model.getValueAt(rowTram, columnPE)));

        double AutoSumPE = CarPE + TruckSumPE + BusSumPE + TrolleyBusSumPE + TramSumPE;
        cartogram.changeValueWithoutSave(idName + "_" + smallDirection,
                String.valueOf(CarFE) + "-" + String.valueOf(TruckSumFE) + "-" + String.valueOf(BusSumFE + TrolleyBusSumFE + TramSumFE),
                " (" + String.valueOf(fmt(AutoSumPE)) + ")");
    }

    // Рассчет для одной стрелки (разворот, налево, прямо, направо) в одном направлении. Определяется столбцами 
    private void TotalOutside(CreateCartogram cartogram, String id0AlsoID, String id1, String id2, String id3) {
        // Верх
        // Считываем значения с файла SVG
        String carUparound = cartogram.getValueTspan2(id0AlsoID).substring(0, cartogram.getValueTspan2(id0AlsoID).indexOf("-")).trim();
        String truckUparound = cartogram.getValueTspan2(id0AlsoID).substring(cartogram.getValueTspan2(id0AlsoID).indexOf("-") + 1, cartogram.getValueTspan2(id0AlsoID).lastIndexOf("-")).trim();
        String busUparound = cartogram.getValueTspan2(id0AlsoID).substring(cartogram.getValueTspan2(id0AlsoID).lastIndexOf("-") + 1, cartogram.getValueTspan2(id0AlsoID).indexOf("(")).trim();
        String sumPEUparound = cartogram.getValueTspan2(id0AlsoID).substring(cartogram.getValueTspan2(id0AlsoID).indexOf("(") + 1, cartogram.getValueTspan2(id0AlsoID).indexOf(")")).trim();

        String carLeftleft = cartogram.getValueTspan2(id1).substring(0, cartogram.getValueTspan2(id1).indexOf("-")).trim();
        String truckLeftleft = cartogram.getValueTspan2(id1).substring(cartogram.getValueTspan2(id1).indexOf("-") + 1, cartogram.getValueTspan2(id1).lastIndexOf("-")).trim();
        String busLeftleft = cartogram.getValueTspan2(id1).substring(cartogram.getValueTspan2(id1).lastIndexOf("-") + 1, cartogram.getValueTspan2(id1).indexOf("(")).trim();
        String sumPELeftleft = cartogram.getValueTspan2(id1).substring(cartogram.getValueTspan2(id1).indexOf("(") + 1, cartogram.getValueTspan2(id1).indexOf(")")).trim();

        String carDownforward = cartogram.getValueTspan2(id2).substring(0, cartogram.getValueTspan2(id2).indexOf("-")).trim();
        String truckDownforward = cartogram.getValueTspan2(id2).substring(cartogram.getValueTspan2(id2).indexOf("-") + 1, cartogram.getValueTspan2(id2).lastIndexOf("-")).trim();
        String busDownforward = cartogram.getValueTspan2(id2).substring(cartogram.getValueTspan2(id2).lastIndexOf("-") + 1, cartogram.getValueTspan2(id2).indexOf("(")).trim();
        String sumPEDownforward = cartogram.getValueTspan2(id2).substring(cartogram.getValueTspan2(id2).indexOf("(") + 1, cartogram.getValueTspan2(id2).indexOf(")")).trim();

        String carRightright = cartogram.getValueTspan2(id3).substring(0, cartogram.getValueTspan2(id3).indexOf("-")).trim();
        String truckRightright = cartogram.getValueTspan2(id3).substring(cartogram.getValueTspan2(id3).indexOf("-") + 1, cartogram.getValueTspan2(id3).lastIndexOf("-")).trim();
        String busRightright = cartogram.getValueTspan2(id3).substring(cartogram.getValueTspan2(id3).lastIndexOf("-") + 1, cartogram.getValueTspan2(id3).indexOf("(")).trim();
        String sumPERightright = cartogram.getValueTspan2(id3).substring(cartogram.getValueTspan2(id3).indexOf("(") + 1, cartogram.getValueTspan2(id3).indexOf(")")).trim();

        int carSum = Integer.valueOf(carUparound) + Integer.valueOf(carLeftleft) + Integer.valueOf(carDownforward) + Integer.valueOf(carRightright);
        int truckSum = Integer.valueOf(truckUparound) + Integer.valueOf(truckLeftleft) + Integer.valueOf(truckDownforward) + Integer.valueOf(truckRightright);
        int busSum = Integer.valueOf(busUparound) + Integer.valueOf(busLeftleft) + Integer.valueOf(busDownforward) + Integer.valueOf(busRightright);

        int sumFE = carSum + truckSum + busSum;
        double sumPE = Double.valueOf(sumPEUparound) + Double.valueOf(sumPELeftleft) + Double.valueOf(sumPEDownforward) + Double.valueOf(sumPERightright);

        // Значения НАД стрелкой
        cartogram.changeValueWithoutSave("Total_outside_" + id0AlsoID.substring(0, id0AlsoID.lastIndexOf("_")),
                String.valueOf(carSum) + "-" + String.valueOf(truckSum) + "-" + String.valueOf(busSum));
        // Значения ПОД стрелкой
        cartogram.changeValueWithoutSave("TotalSum_outside_" + id0AlsoID.substring(0, id0AlsoID.lastIndexOf("_")),
                String.valueOf(sumFE),
                " (" + String.valueOf(fmt(sumPE)) + ")");
    }

    // Округление до одного числа после запятой
    private String fmt(double d) {
        double dFormat = (double) Math.round(d * 10) / 10;
        return String.valueOf(dFormat);
    }

    // Определяем положение нужных строк
    private void identifierRow(JBroTable table) {
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
            String rowValue = (String.valueOf(table.getModel().getData().getRows()[i].getValue(1))); // значение во 2 столбце в проверяемой ячейке
            if (rowValue.equalsIgnoreCase("Легковой транспорт")) { // находим строку,
                rowCar = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("До 2-х тонн")) { // находим строку,
                rowTruck1 = rowTruckNow[0] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("От 2 до 6 тонн")) { // находим строку,
                rowTruck2 = rowTruckNow[1] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("От 6 до 12 тонн")) { // находим строку,
                rowTruck3 = rowTruckNow[2] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("От 12 до 20 тонн")) { // находим строку,
                rowTruck4 = rowTruckNow[3] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Более 20 тонн")) { // находим строку,
                rowTruck5 = rowTruckNow[4] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Большой автобус")) { // находим строку,
                rowBus1 = rowBusNow[0] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Средний автобус")) { // находим строку,
                rowBus2 = rowBusNow[1] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Микроавтобус")) { // находим строку,
                rowBus3 = rowBusNow[2] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Троллейбусы")) { // находим строку,
                rowTrolleyBus = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Трамвай")) { // находим строку,
                rowTram = i; // сохраняем номер этой строки
            }
        }
    }

    // Определяем положение нужных столбцов
    private void IdentifierColumn(JBroTable table, String destinationName) {
        for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) { // перебираем все столбцы
            // ФЕ
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Разворот") // если нужный начинается с указанной фразы
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) { // и его родитель - это какое то направление (для идентификации), то
                columnAroundFE = i; // это требуемый нам столбец
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Налево")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnLeftFE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Прямо")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnForwardFE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Направо")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnRightFE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Итого")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnTotalFE = i;
            }
        }

        for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) {
            // ПЕ
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Разворот")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnAroundPE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Налево")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnLeftPE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Прямо")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnForwardPE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Направо")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnRightPE = i;
            }
            if (table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Итого")
                    && table.getModel().getData().getFields()[i].getParent().getParent().getIdentifier().startsWith(destinationName)) {
                columnTotalPE = i;
            }
        }
    }

    // Определяем название переданного направления
    private void IdentifierDestination(String destinationName) {
        if (destinationName.equalsIgnoreCase("Направление 1")) {
            idName = "Up";
        }
        if (destinationName.equalsIgnoreCase("Направление 2")) {
            idName = "Right";
        }
        if (destinationName.equalsIgnoreCase("Направление 3")) {
            idName = "Down";
        }
        if (destinationName.equalsIgnoreCase("Направление 4")) {
            idName = "Left";
        }
    }

}
