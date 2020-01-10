package ru.jtable.modelListener.cartogram;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.view.JBroTable;
import ru.cartogram.CreateCartogram;

/**
 * Подсчет суммы внутри одного направления по видам движения ФЕ. Графа ИТОГО.
 */
public class CountingOneDirectionFuture {

    // Переменные для хранения номера строки для конкретного вида транспорта
    private int rowCar;
    private int rowTruck1, rowTruck2, rowTruck3, rowTruck4, rowTruck5;
    private int rowBus1, rowBus2, rowBus3, rowBus4, rowBus5;
    private int rowBusTrain1, rowBusTrain2, rowBusTrain3, rowBusTrain4, rowBusTrain5, rowBusTrain6;
    private int rowTrolleyBus;
    private int rowTram;
    private int[] rowTruckFuture = {rowTruck1, rowTruck2, rowTruck3, rowTruck4, rowTruck5};
    private int[] rowBusFuture = {rowBus1, rowBus2, rowBus3, rowBus4, rowBus5};
    private int[] rowBusTrainFuture = {rowBusTrain1, rowBusTrain2, rowBusTrain3, rowBusTrain4, rowBusTrain5, rowBusTrain6};

    // ФЕ
    private int columnAroundFE = -1, columnLeftFE = -1, columnForwardFE = -1, columnRightFE = -1, columnTotalFE = -1;
    // ПЕ
    private int columnAroundPE = -1, columnLeftPE = -1, columnForwardPE = -1, columnRightPE = -1, columnTotalPE = -1;

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
                IdentifierRow(table);
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
        int TotalSum = 0;
        for (int i = 0; i < row.length; i++) {
            Object TotalInsideObj = model.getValueAt(row[i], columnTotal);
            int TotalInside = Integer.valueOf(String.valueOf(TotalInsideObj));
            TotalSum = TotalSum + TotalInside;
        }
        return TotalSum;
    }

    // Считаем сумму ПЕ для вида транспорта (грузовики, автобусы и т.п.)
    private double TotalPE(TableModel model, int[] row, int columnTotal) {
        double TotalSum = 0;
        for (int i = 0; i < row.length; i++) {
            Object TotalInsideObj = model.getValueAt(row[i], columnTotal);
            double TotalInside = Double.valueOf(String.valueOf(TotalInsideObj));
            TotalSum = TotalSum + TotalInside;
        }
        return TotalSum;
    }

    // Рассчет для стрелки ВНУТРЬ перекрестка. Цифры СНИЗУ
    private void TotalInside(TableModel model, CreateCartogram cartogram, String idName) {
        // Таким образом, если направлений меньше, чем 4, то такого столбца не будет в таблице и считать значит не нужно
        if (columnTotalFE >= 0) {
            // Легковые авто
            Object TotalInsideCarObj = model.getValueAt(rowCar, columnTotalFE); // получаем данные из ячейки
            int TotalInsideCarFE = Integer.valueOf(String.valueOf(TotalInsideCarObj)); // переводим значение из текста в числовой формат
            double TotalInsideCarPE = Double.valueOf(String.valueOf(TotalInsideCarObj));
            // Грузовые
            int TotalInsideTruckSumFE = TotalFE(model, rowTruckFuture, columnTotalFE);
            double TotalInsideTruckSumPE = TotalPE(model, rowTruckFuture, columnTotalPE);
            // Автопоезда
            int TotalInsideBusTrainSumFE = TotalFE(model, rowBusTrainFuture, columnTotalFE);
            double TotalInsideBusTrainSumPE = TotalPE(model, rowBusTrainFuture, columnTotalFE);
            // Автобусы
            int TotalInsideBusSumFE = TotalFE(model, rowBusFuture, columnTotalFE);
            double TotalInsideBusSumPE = TotalPE(model, rowBusFuture, columnTotalPE);
            // Троллейбусы
            int TotalInsideTrolleyBusFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnTotalFE)));
            double TotalInsideTrolleyBusPE = Double.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnTotalPE)));
            // Трамваи
            int TotalInsideTramFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTram, columnTotalFE)));
            double TotalInsideTramPE = Double.valueOf(String.valueOf(model.getValueAt(rowTram, columnTotalPE)));

            // Устанавливаем значение для стрелки "в перекресток" НАД ней
            cartogram.changeValueWithoutSave("Total_inside_" + idName,
                    String.valueOf(TotalInsideCarFE) + "-"
                    + String.valueOf(TotalInsideTruckSumFE + TotalInsideBusTrainSumFE) + "-"
                    + String.valueOf(TotalInsideBusSumFE + TotalInsideTrolleyBusFE + TotalInsideTramFE));

            int TotalAutoSumFE = TotalInsideCarFE + TotalInsideTruckSumFE + TotalInsideBusTrainSumFE + TotalInsideBusSumFE + TotalInsideTrolleyBusFE + TotalInsideTramFE;
            double TotalAutoSumPE = TotalInsideCarPE + TotalInsideTruckSumPE + TotalInsideBusTrainSumPE + TotalInsideBusSumPE + TotalInsideTrolleyBusPE + TotalInsideTramPE;
            // Устанавливаем значение для стрелки "в перекресток" ПОД ней
            cartogram.changeValueWithoutSave("TotalSum_inside_" + idName,
                    String.valueOf(TotalAutoSumFE),
                    " (" + String.valueOf(fmt(TotalAutoSumPE)) + ")");
        }
    }

    // Рассчет для одной стрелки (разворот, налево, прямо, направо) в одном направлении. Определяется столбцами 
    private void TotalSmallArrow(TableModel model, CreateCartogram cartogram, int columnFE, int columnPE, String smallDirection) {
        if (columnFE >= 0) {
            // Легковые авто
            Object CarObj = model.getValueAt(rowCar, columnFE); // получаем данные из ячейки
            int CarFE = Integer.valueOf(String.valueOf(CarObj)); // переводим значение из текста в числовой формат
            double CarPE = Double.valueOf(String.valueOf(CarObj));
            // Грузовые авто
            int TruckSumFE = TotalFE(model, rowTruckFuture, columnFE);
            double TruckSumPE = TotalPE(model, rowTruckFuture, columnPE);
            // Автопоезда
            int BusTrainSumFE = TotalFE(model, rowBusTrainFuture, columnFE);
            double BusTrainSumPE = TotalPE(model, rowBusTrainFuture, columnPE);
            // Автобусы
            int BusSumFE = TotalFE(model, rowBusFuture, columnFE);
            double BusSumPE = TotalPE(model, rowBusFuture, columnPE);
            // Троллейбусы
            int TrolleyBusSumFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnFE)));
            double TrolleyBusSumPE = Double.valueOf(String.valueOf(model.getValueAt(rowTrolleyBus, columnPE)));
            // Трамваи
            int TramSumFE = Integer.valueOf(String.valueOf(model.getValueAt(rowTram, columnFE)));
            double TramSumPE = Double.valueOf(String.valueOf(model.getValueAt(rowTram, columnPE)));

            double AutoSumPE = CarPE + TruckSumPE + BusTrainSumPE + BusSumPE + TrolleyBusSumPE + TramSumPE;
            cartogram.changeValueWithoutSave(idName + "_" + smallDirection,
                    String.valueOf(CarFE) + "-" + String.valueOf(TruckSumFE + BusTrainSumFE) + "-" + String.valueOf(BusSumFE + TrolleyBusSumFE + TramSumFE),
                    " (" + String.valueOf(fmt(AutoSumPE)) + ")");
        }
    }

    // Рассчет для одной стрелки (разворот, налево, прямо, направо) в одном направлении. Определяется столбцами 
    private void TotalOutside(CreateCartogram cartogram, String id0AlsoID, String id1, String id2, String id3) {
        // Верх
        // Считываем значения с файла SVG
        String id0Value = cartogram.getFullValue(id0AlsoID);
        String carUparound = !id0Value.trim().isEmpty() ? id0Value.substring(0, id0Value.indexOf("-")).trim() : "0";
        String truckUparound = !id0Value.trim().isEmpty() ? id0Value.substring(id0Value.indexOf("-") + 1, id0Value.lastIndexOf("-")).trim() : "0";
        String busUparound = !id0Value.trim().isEmpty() ? id0Value.substring(id0Value.lastIndexOf("-") + 1, id0Value.indexOf("(")).trim() : "0";
        String sumPEUparound = !id0Value.trim().isEmpty() ? id0Value.substring(id0Value.indexOf("(") + 1, id0Value.indexOf(")")).trim() : "0";

        String id1Value = cartogram.getFullValue(id1);
        String carLeftleft = !id1Value.trim().isEmpty() ? id1Value.substring(0, id1Value.indexOf("-")).trim() : "0";
        String truckLeftleft = !id1Value.trim().isEmpty() ? id1Value.substring(id1Value.indexOf("-") + 1, id1Value.lastIndexOf("-")).trim() : "0";
        String busLeftleft = !id1Value.trim().isEmpty() ? id1Value.substring(id1Value.lastIndexOf("-") + 1, id1Value.indexOf("(")).trim() : "0";
        String sumPELeftleft = !id1Value.trim().isEmpty() ? id1Value.substring(id1Value.indexOf("(") + 1, id1Value.indexOf(")")).trim() : "0";

        String id2Value = cartogram.getFullValue(id2);
        String carDownforward = !id2Value.trim().isEmpty() ? id2Value.substring(0, id2Value.indexOf("-")).trim() : "0";
        String truckDownforward = !id2Value.trim().isEmpty() ? id2Value.substring(id2Value.indexOf("-") + 1, id2Value.lastIndexOf("-")).trim() : "0";
        String busDownforward = !id2Value.trim().isEmpty() ? id2Value.substring(id2Value.lastIndexOf("-") + 1, id2Value.indexOf("(")).trim() : "0";
        String sumPEDownforward = !id2Value.trim().isEmpty() ? id2Value.substring(id2Value.indexOf("(") + 1, id2Value.indexOf(")")).trim() : "0";

        String id3Value = cartogram.getFullValue(id3);
        String carRightright = !id3Value.trim().isEmpty() ? cartogram.getFullValue(id3).substring(0, cartogram.getFullValue(id3).indexOf("-")).trim() : "0";
        String truckRightright = !id3Value.trim().isEmpty() ? cartogram.getFullValue(id3).substring(cartogram.getFullValue(id3).indexOf("-") + 1, cartogram.getFullValue(id3).lastIndexOf("-")).trim() : "0";
        String busRightright = !id3Value.trim().isEmpty() ? cartogram.getFullValue(id3).substring(cartogram.getFullValue(id3).lastIndexOf("-") + 1, cartogram.getFullValue(id3).indexOf("(")).trim() : "0";
        String sumPERightright = !id3Value.trim().isEmpty() ? cartogram.getFullValue(id3).substring(cartogram.getFullValue(id3).indexOf("(") + 1, cartogram.getFullValue(id3).indexOf(")")).trim() : "0";

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
    private void IdentifierRow(JBroTable table) {
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
            String rowValue = (String.valueOf(table.getModel().getData().getRows()[i].getValue(1))); // значение во 2 столбце в проверяемой ячейке
            if (rowValue.equalsIgnoreCase("Легковые, фургоны")) { // находим строку,
                rowCar = i; // сохраняем номер этой строки
            }
            // Грузовые
            if (rowValue.equalsIgnoreCase("2-осные")) { // находим строку,
                rowTruck1 = rowTruckFuture[0] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("3-осные")) { // находим строку,
                rowTruck2 = rowTruckFuture[1] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("4-осные")) { // находим строку,
                rowTruck3 = rowTruckFuture[2] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("4-осные (2 оси+прицеп)")) { // находим строку,
                rowTruck4 = rowTruckFuture[3] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("5-осные (3 оси+прицеп)")) { // находим строку,
                rowTruck5 = rowTruckFuture[4] = i; // сохраняем номер этой строки
            }
            // Автопоезда 
            if (rowValue.equalsIgnoreCase("3 осные (2 оси+полуприцеп)")) { // находим строку,
                rowBusTrain1 = rowBusTrainFuture[0] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("4 осные (2 оси+полуприцеп)")) { // находим строку,
                rowBusTrain2 = rowBusTrainFuture[1] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("5 осные (2 оси+полуприцеп)")) { // находим строку,
                rowBusTrain3 = rowBusTrainFuture[2] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("5 осные (3 оси+полуприцеп)")) { // находим строку,
                rowBusTrain4 = rowBusTrainFuture[3] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("6 осные")) { // находим строку,
                rowBusTrain5 = rowBusTrainFuture[4] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("7 осные и более")) { // находим строку,
                rowBusTrain6 = rowBusTrainFuture[5] = i; // сохраняем номер этой строки
            }
            // Автобусы
            if (rowValue.equalsIgnoreCase("Особо малого класса")) { // находим строку,
                rowBus1 = rowBusFuture[0] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Малого класса")) { // находим строку,
                rowBus2 = rowBusFuture[1] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Среднего класса")) { // находим строку,
                rowBus3 = rowBusFuture[2] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Большого класса")) { // находим строку,
                rowBus4 = rowBusFuture[3] = i; // сохраняем номер этой строки
            }
            if (rowValue.equalsIgnoreCase("Особо большого класса")) { // находим строку,
                rowBus5 = rowBusFuture[4] = i; // сохраняем номер этой строки
            }
            // Троллейбус
            if (rowValue.equalsIgnoreCase("Троллейбусы")) { // находим строку,
                rowTrolleyBus = i; // сохраняем номер этой строки
            }
            // Трамвай
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
