package ru.overlay;

import javax.swing.JComponent;
import javax.swing.tree.TreePath;

/**
 * Класс для выбора того, что отображать на панелях кнопок. <n>
 * Сделано для варианта с 2 направлениями.
 */
public class ChooseComponentsFuture2 {

    private TreePath[] paths; // массив путей к узлам (видам транспорта), которые были выбраны для подсчета
    private int row; // количество строк на панели кнопок (количество видов транспорта для подсчета, не считая labels направлений)

    private JComponent label;
    private JComponent bCar;
    private JComponent bBus;
    private JComponent bTruck;
    private JComponent bTrainBus;
    private JComponent bTrolleybus;
    private JComponent bTram;

    public ChooseComponentsFuture2(TreePath[] paths, JComponent label, JComponent bCar, JComponent bBus, JComponent bTruck, JComponent bTrainBus, JComponent bTrolleybus, JComponent bTram) {
        this.paths = paths;
        this.label = label;
        this.bCar = bCar;
        this.bBus = bBus;
        this.bTruck = bTruck;
        this.bTrainBus = bTrainBus;
        this.bTrolleybus = bTrolleybus;
        this.bTram = bTram;
    }

    public JComponent[] chooseComponentsFuture2() {

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

        doComponents2 doComp2 = new doComponents2();
        JComponent[] components = doComp2.doComponentsForward(label);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
        // 1. Есть все
        if (car && bus && truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        // 2. Нет одного из всех (6)
        if (car && bus && truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTrainBus, bTrolleybus);
        }
        if (car && bus && truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTrainBus, bTram);
        }
        if (car && bus && truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTrolleybus, bTram);
        }
        if (car && bus && !truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTrainBus, bTrolleybus, bTram);
        }
        if (car && !bus && truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        if (!car && bus && truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        // 3. Нет двух из всех (15)
        // ТРАМВАЙ и
        if (car && bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTrainBus);
        }
        if (car && bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTrolleybus);
        }
        if (car && bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTrainBus, bTrolleybus);
        }
        if (car && !bus && truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck, bTrainBus, bTrolleybus);
        }
        if (!car && bus && truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTrainBus, bTrolleybus);
        }
        // ТРОЛЛЕЙБУС и
        if (car && bus && truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck, bTram);
        }
        if (car && bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTrainBus, bTram);
        }
        if (car && !bus && truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck, bTrainBus, bTram);
        }
        if (!car && bus && truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTrainBus, bTram);
        }
        // АВТОПОЕЗДА и
        if (car && bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTrolleybus, bTram);
        }
        if (car && !bus && truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck, bTrolleybus, bTram);
        }
        if (!car && bus && truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTrolleybus, bTram);
        }
        // ГРУЗОВЫЕ и
        if (car && !bus && !truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTrainBus, bTrolleybus, bTram);
        }
        if (!car && bus && !truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTrainBus, bTrolleybus, bTram);
        }
        // АВТОБУСЫ и
        if (!car && !bus && truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTrainBus, bTrolleybus, bTram);
        }
        // 4. Нет трех из всех (19)
        // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
        if (car && bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTruck);
        }
        if (car && bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTrainBus);
        }
        if (car && !bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck, bTrainBus);
        }
        if (!car && bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTrainBus);
        }
        // ТРАМВАЕВ, АВТОПОЕЗДОВ и
        if (car && bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTrolleybus);
        }
        if (car && !bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck, bTrolleybus);
        }
        if (!car && bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTrolleybus);
        }
        // ТРАМВАЕВ, ГРУЗОВЫХ и
        if (car && !bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTrainBus, bTrolleybus);
        }
        if (!car && bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTrainBus, bTrolleybus);
        }
        // ТРАМВАЕВ, АВТОБУСОВ и
        if (!car && !bus && truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTrainBus, bTrolleybus);
        }
        // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
        if (car && !bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTrainBus, bTram);
        }
        if (!car && bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTrainBus, bTram);
        }
        // ГРУЗОВЫХ, АВТОБУСОВ и
        if (!car && !bus && !truck && trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTrainBus, bTrolleybus, bTram);
        }
        // ТРОЛЛЕЙБУСОВ, АВТОБУСОВ и
        if (!car && !bus && truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTrainBus, bTram);
        }
        // Ещё
        if (car && bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus, bTram);
        }
        if (car && !bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTrolleybus, bTram);
        }
        if (!car && bus && truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck, bTram);
        }
        if (!car && bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTrolleybus, bTram);
        }
        if (!car && !bus && truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTrolleybus, bTram);
        }
        // 5. Нет четырех из всех (15)
        if (car && bus && !truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bBus);
        }
        if (car && !bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTruck);
        }
        if (car && !bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTrainBus);
        }
        if (car && !bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar, bTrolleybus);
        }
        if (car && !bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bCar, bTram);
        }
        if (!car && bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTruck);
        }
        if (!car && bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTrainBus);
        }
        if (!car && bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus, bTrolleybus);
        }
        if (!car && bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bBus, bTram);
        }
        if (!car && !bus && truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTrainBus);
        }
        if (!car && !bus && truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTrolleybus);
        }
        if (!car && !bus && truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTruck, bTram);
        }
        if (!car && !bus && !truck && trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTrainBus, bTrolleybus);
        }
        if (!car && !bus && !truck && trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTrainBus, bTram);
        }
        if (!car && !bus && !truck && !trainbus && trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTrolleybus, bTram);
        }
        // 6. Нет пяти из всех (6)
        if (!car && !bus && !truck && !trainbus && !trolleybus && tram) {
            components = doComp2.doComponentsForward(label, bTram);
        }
        if (!car && !bus && !truck && !trainbus && trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTrolleybus);
        }
        if (!car && !bus && !truck && trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTrainBus);
        }
        if (!car && !bus && truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bTruck);
        }
        if (!car && bus && !truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bBus);
        }
        if (car && !bus && !truck && !trainbus && !trolleybus && !tram) {
            components = doComp2.doComponentsForward(label, bCar);
        }
        return components;
    }

    public int getRow() {
        return row;
    }

}
