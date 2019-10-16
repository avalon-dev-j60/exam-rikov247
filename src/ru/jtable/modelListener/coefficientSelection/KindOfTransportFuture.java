package ru.jtable.modelListener.coefficientSelection;

/**
 * Класс для указания соответствия вида транспорта и коэффициента.
 */
public class KindOfTransportFuture {

    private String[] kindOfTransportFuture = {"Легковые, фургоны",
        "2-осные", "3-осные", "4-осные", "4-осные (2 оси+прицеп)", "5-осные (3 оси+прицеп)",
        "3 осные (2 оси+полуприцеп)", "4 осные (2 оси+полуприцеп)", "5 осные (2 оси+полуприцеп)", "5 осные (3 оси+полуприцеп)", "6 осные", "7 осные и более",
        "Особо малого класса", "Малого класса", "Среднего класса", "Большого класса", "Особо большого класса",
        "Трамвай",
        "ИТОГО"};

    public double getCoefficient(String rowValue) {
        double coefficient = 0;
        // Легковые
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[0])) {
            coefficient = 1;
        }
        // Грузовые
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[1])) {
            coefficient = 1.5;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[2])) {
            coefficient = 1.8;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[3])) {
            coefficient = 2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[4])) {
            coefficient = 2.2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[5])) {
            coefficient = 2.7;
        }
        // Автопоезда
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[6])) {
            coefficient = 2.2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[7])) {
            coefficient = 2.7;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[8])) {
            coefficient = 2.7;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[9])) {
            coefficient = 2.7;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[10])) {
            coefficient = 3.2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[11])) {
            coefficient = 3.2;
        }
        // Автобусы
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[12])) {
            coefficient = 1.5;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[13])) {
            coefficient = 1.8;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[14])) {
            coefficient = 2.2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[15])) {
            coefficient = 3;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[16])) {
            coefficient = 3.2;
        }
        // Трамвай
        if (rowValue.equalsIgnoreCase(kindOfTransportFuture[17])) {
            coefficient = 3.2;
        }

        return coefficient;
    }

}
