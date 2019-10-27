package ru.overlay;

import javax.swing.JComponent;
import javax.swing.tree.TreePath;

/**
 * Класс для выбора того, что отображать на панелях кнопок. <n>
 * Сделано для варианта с 4 направлениями.
 */
public class ChooseComponentsFuture4 {

    private TreePath[] paths; // массив путей к узлам (видам транспорта), которые были выбраны для подсчета
    private int row; // количество строк на панели кнопок (количество видов транспорта для подсчета, не считая labels направлений)

    private JComponent[] labels;
    private JComponent[] bCar;
    private JComponent[] bBus;
    private JComponent[] bTruck;
    private JComponent[] bTrainBus;
    private JComponent[] bTrolleybus;
    private JComponent[] bTram;

    public ChooseComponentsFuture4(TreePath[] paths, JComponent[] labels, JComponent[] bCar, JComponent[] bBus, JComponent[] bTruck, JComponent[] bTrainBus, JComponent[] bTrolleybus, JComponent[] bTram) {
        this.paths = paths;
        this.labels = labels;
        this.bCar = bCar;
        this.bBus = bBus;
        this.bTruck = bTruck;
        this.bTrainBus = bTrainBus;
        this.bTrolleybus = bTrolleybus;
        this.bTram = bTram;
    }

    public JComponent[] chooseComponentsFuture4() {

        row = 1;
        // Определяем, какие кнопки отображать и что будем считать
        boolean car = false;
        boolean bus = false;
        boolean truck = false;
        boolean trainbus = false;
        boolean trolleybus = false;
        boolean tram = false;
        for (TreePath tp : paths) {
            String temp = String.valueOf(tp.getLastPathComponent());
            if (temp.equalsIgnoreCase("Легковые, фургоны")) {
                car = true;
                row = row + 1;
            }
            if (temp.equalsIgnoreCase("Автобусы")) {
                bus = true;
                row = row + 1;
            }
            if (temp.equalsIgnoreCase("Грузовые")) {
                truck = true;
                row = row + 1;
            }
            if (temp.equalsIgnoreCase("Автопоезда")) {
                trainbus = true;
                row = row + 1;
            }
            if (temp.equalsIgnoreCase("Троллейбусы")) {
                trolleybus = true;
                row = row + 1;
            }
            if (temp.equalsIgnoreCase("Трамвай")) {
                tram = true;
                row = row + 1;
            }
        }

        doComponents4 doComp4 = new doComponents4();
        JComponent[] components = doComp4.doComponents(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
        // 1. Есть все
        if (car && bus && truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        // 2. Нет одного из всех (6)
        if (car && bus && truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus);
        }
        if (car && bus && truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTrainBus, bTram);
        }
        if (car && bus && truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
        }
        if (car && bus && !truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTrainBus, bTrolleybus, bTram);
        }
        if (car && !bus && truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        if (!car && bus && truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        // 3. Нет двух из всех (15)
        // ТРАМВАЙ и
        if (car && bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTrainBus);
        }
        if (car && bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTrolleybus);
        }
        if (car && bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTrainBus, bTrolleybus);
        }
        if (car && !bus && truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTruck, bTrainBus, bTrolleybus);
        }
        if (!car && bus && truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTrainBus, bTrolleybus);
        }
        // ТРОЛЛЕЙБУС и
        if (car && bus && truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck, bTram);
        }
        if (car && bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTrainBus, bTram);
        }
        if (car && !bus && truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTruck, bTrainBus, bTram);
        }
        if (!car && bus && truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTrainBus, bTram);
        }
        // АВТОПОЕЗДА и
        if (car && bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTrolleybus, bTram);
        }
        if (car && !bus && truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTruck, bTrolleybus, bTram);
        }
        if (!car && bus && truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTrolleybus, bTram);
        }
        // ГРУЗОВЫЕ и
        if (car && !bus && !truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTrainBus, bTrolleybus, bTram);
        }
        if (!car && bus && !truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTrainBus, bTrolleybus, bTram);
        }
        // АВТОБУСЫ и
        if (!car && !bus && truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        // 4. Нет трех из всех (19)
        // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
        if (car && bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTruck);
        }
        if (car && bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTrainBus);
        }
        if (car && !bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTruck, bTrainBus);
        }
        if (!car && bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTrainBus);
        }
        // ТРАМВАЕВ, АВТОПОЕЗДОВ и
        if (car && bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTrolleybus);
        }
        if (car && !bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTruck, bTrolleybus);
        }
        if (!car && bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTrolleybus);
        }
        // ТРАМВАЕВ, ГРУЗОВЫХ и
        if (car && !bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTrainBus, bTrolleybus);
        }
        if (!car && bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus, bTrainBus, bTrolleybus);
        }
        // ТРАМВАЕВ, АВТОБУСОВ и
        if (!car && !bus && truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTruck, bTrainBus, bTrolleybus);
        }
        // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
        if (car && !bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTrainBus, bTram);
        }
        if (!car && bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTrainBus, bTram);
        }
        // ГРУЗОВЫХ, АВТОБУСОВ и
        if (!car && !bus && !truck && trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bTrainBus, bTrolleybus, bTram);
        }
        // ТРОЛЛЕЙБУСОВ, АВТОБУСОВ и
        if (!car && !bus && truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bTruck, bTrainBus, bTram);
        }
        // Ещё
        if (car && bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bBus, bTram);
        }
        if (car && !bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTrolleybus, bTram);
        }
        if (!car && bus && truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTruck, bTram);
        }
        if (!car && bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus, bTrolleybus, bTram);
        }
        if (!car && !bus && truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bTruck, bTrolleybus, bTram);
        }
        // 5. Нет четырех из всех (15)
        if (car && bus && !truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bBus);
        }
        if (car && !bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTruck);
        }
        if (car && !bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTrainBus);
        }
        if (car && !bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar, bTrolleybus);
        }
        if (car && !bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bCar, bTram);
        }
        if (!car && bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus, bTruck);
        }
        if (!car && bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus, bTrainBus);
        }
        if (!car && bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus,bTrolleybus);
        }
        if (!car && bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bBus,bTram);
        }
        if (!car && !bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTruck, bTrainBus);
        }
        if (!car && !bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTruck, bTrolleybus);
        }
        if (!car && !bus && truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bTruck, bTram);
        }
        if (!car && !bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTrainBus, bTrolleybus);
        }
        if (!car && !bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bTrainBus, bTram);
        }
        if (!car && !bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp4.doComponents(labels, bTrolleybus, bTram);
        }
        // 6. Нет пяти из всех (6)
        if (!car && !bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp4.doComponents(labels, bTram);
        }
        if (!car && !bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTrolleybus);
        }
        if (!car && !bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTrainBus);
        }
        if (!car && !bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bTruck);
        }
        if (!car && bus && !truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bBus);
        }
        if (car && !bus && !truck && !trainbus && !trolleybus && !tram) {
            components = doComp4.doComponents(labels, bCar);
        }

        return components;
    }

    public int getRow() {
        return row;
    }

}
