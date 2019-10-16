package ru.jtable.modelListener.coefficientSelection;

/**
 * Класс для указания соответствия вида транспорта и коэффициента.
 */
public class KindOfTransportNow {


    private String[] kindOfTransportNow = {"Троллейбусы",
        "Большой автобус", "Средний автобус", "Микроавтобус",
        "Легковой транспорт",
        "До 2-х тонн", "От 2 до 6 тонн", "От 6 до 12 тонн", "От 12 до 20 тонн", "Более 20 тонн",
        "Трамвай",
        "ИТОГО"};

    public double getCoefficient(String rowValue) {
        double coefficient = 0;
        // Троллейбус
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[0])) {
            coefficient = 2.5;
        }
        // Автобусы
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[1])) {
            coefficient = 2.2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[2])) {
            coefficient = 1.6;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[3])) {
            coefficient = 1.3;
        }
        // Легковой транспорт
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[4])) {
            coefficient = 1;
        }
        // Грузовики
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[5])) {
            coefficient = 1.3;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[6])) {
            coefficient = 1.5;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[7])) {
            coefficient = 1.8;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[8])) {
            coefficient = 2.2;
        }
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[9])) {
            coefficient = 3;
        }
        // Трамвай
        if (rowValue.equalsIgnoreCase(kindOfTransportNow[10])) {
            coefficient = 5;
        }

        return coefficient;
    }

}
