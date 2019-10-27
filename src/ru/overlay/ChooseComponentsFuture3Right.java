package ru.overlay;

import javax.swing.JComponent;
import javax.swing.tree.TreePath;

/**
 * Класс для выбора того, что отображать на панелях кнопок. <n>
 * Сделано для варианта с 3 направлениями.
 */
public class ChooseComponentsFuture3Right {

    private TreePath[] paths; // массив путей к узлам (видам транспорта), которые были выбраны для подсчета
    private int row; // количество строк на панели кнопок (количество видов транспорта для подсчета, не считая labels направлений)

    private JComponent[] labels;
    private JComponent[] bCar;
    private JComponent[] bBus;
    private JComponent[] bTruck;
    private JComponent[] bTrainBus;
    private JComponent[] bTrolleybus;
    private JComponent[] bTram;
    private String sideIdentifier;

    public ChooseComponentsFuture3Right(String sideIdentifier, TreePath[] paths, JComponent[] labels, JComponent[] bCar, JComponent[] bBus, JComponent[] bTruck, JComponent[] bTrainBus, JComponent[] bTrolleybus, JComponent[] bTram) {
        this.sideIdentifier = sideIdentifier;
        this.paths = paths;
        this.labels = labels;
        this.bCar = bCar;
        this.bBus = bBus;
        this.bTruck = bTruck;
        this.bTrainBus = bTrainBus;
        this.bTrolleybus = bTrolleybus;
        this.bTram = bTram;
    }

    public JComponent[] chooseComponentsFuture3() {

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

        if (sideIdentifier.equalsIgnoreCase("4")) {
            doComponents3 doComp3 = new doComponents3();
            JComponent[] components = doComp3.doComponentsWithoutForward(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
            // 1. Есть все
            if (car && bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 2. Нет одного из всех (6)
            if (car && bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus);
            }
            if (car && bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrainBus, bTram);
            }
            if (car && bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
            }
            if (car && bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrainBus, bTrolleybus, bTram);
            }
            if (car && !bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            if (!car && bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 3. Нет двух из всех (15)
            // ТРАМВАЙ и
            if (car && bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrainBus);
            }
            if (car && bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrolleybus);
            }
            if (car && bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrainBus, bTrolleybus);
            }
            if (car && !bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrainBus, bTrolleybus);
            }
            if (!car && bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrainBus, bTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            if (car && bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTram);
            }
            if (car && bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrainBus, bTram);
            }
            if (car && !bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrainBus, bTram);
            }
            if (!car && bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrainBus, bTram);
            }
            // АВТОПОЕЗДА и
            if (car && bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrolleybus, bTram);
            }
            if (car && !bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrolleybus, bTram);
            }
            if (!car && bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrolleybus, bTram);
            }
            // ГРУЗОВЫЕ и
            if (car && !bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrainBus, bTrolleybus, bTram);
            }
            if (!car && bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrainBus, bTrolleybus, bTram);
            }
            // АВТОБУСЫ и
            if (!car && !bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 4. Нет трех из всех (19)
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            if (car && bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck);
            }
            if (car && bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrainBus);
            }
            if (car && !bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrainBus);
            }
            if (!car && bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrainBus);
            }
            // ТРАМВАЕВ, АВТОПОЕЗДОВ и
            if (car && bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrolleybus);
            }
            if (car && !bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrolleybus);
            }
            if (!car && bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrolleybus);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            if (car && !bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrainBus, bTrolleybus);
            }
            if (!car && bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrainBus, bTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            if (!car && !bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrainBus, bTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            if (car && !bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrainBus, bTram);
            }
            if (!car && bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrainBus, bTram);
            }
            // ГРУЗОВЫХ, АВТОБУСОВ и
            if (!car && !bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrainBus, bTrolleybus, bTram);
            }
            // ТРОЛЛЕЙБУСОВ, АВТОБУСОВ и
            if (!car && !bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrainBus, bTram);
            }
            // Ещё
            if (car && bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTram);
            }
            if (car && !bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrolleybus, bTram);
            }
            if (!car && bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTram);
            }
            if (!car && bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrolleybus, bTram);
            }
            if (!car && !bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrolleybus, bTram);
            }
            // 5. Нет четырех из всех (15)
            if (car && bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus);
            }
            if (car && !bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck);
            }
            if (car && !bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrainBus);
            }
            if (car && !bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrolleybus);
            }
            if (car && !bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTram);
            }
            if (!car && bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck);
            }
            if (!car && bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrainBus);
            }
            if (!car && bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrolleybus);
            }
            if (!car && bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTram);
            }
            if (!car && !bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrainBus);
            }
            if (!car && !bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrolleybus);
            }
            if (!car && !bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTram);
            }
            if (!car && !bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrainBus, bTrolleybus);
            }
            if (!car && !bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrainBus, bTram);
            }
            if (!car && !bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrolleybus, bTram);
            }
            // 6. Нет пяти из всех (6)
            if (!car && !bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTram);
            }
            if (!car && !bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrolleybus);
            }
            if (!car && !bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrainBus);
            }
            if (!car && !bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck);
            }
            if (!car && bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus);
            }
            if (car && !bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar);
            }

            return components;
        }
        if (sideIdentifier.equalsIgnoreCase("3")) {
            doComponents3 doComp3 = new doComponents3();
            JComponent[] components = doComp3.doComponentsWithoutRight(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
            // 1. Есть все
            if (car && bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 2. Нет одного из всех (6)
            if (car && bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus);
            }
            if (car && bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrainBus, bTram);
            }
            if (car && bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
            }
            if (car && bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrainBus, bTrolleybus, bTram);
            }
            if (car && !bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            if (!car && bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 3. Нет двух из всех (15)
            // ТРАМВАЙ и
            if (car && bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrainBus);
            }
            if (car && bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrolleybus);
            }
            if (car && bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrainBus, bTrolleybus);
            }
            if (car && !bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrainBus, bTrolleybus);
            }
            if (!car && bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrainBus, bTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            if (car && bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTram);
            }
            if (car && bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrainBus, bTram);
            }
            if (car && !bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrainBus, bTram);
            }
            if (!car && bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrainBus, bTram);
            }
            // АВТОПОЕЗДА и
            if (car && bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrolleybus, bTram);
            }
            if (car && !bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrolleybus, bTram);
            }
            if (!car && bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrolleybus, bTram);
            }
            // ГРУЗОВЫЕ и
            if (car && !bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrainBus, bTrolleybus, bTram);
            }
            if (!car && bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrainBus, bTrolleybus, bTram);
            }
            // АВТОБУСЫ и
            if (!car && !bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 4. Нет трех из всех (19)
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            if (car && bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck);
            }
            if (car && bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrainBus);
            }
            if (car && !bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrainBus);
            }
            if (!car && bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrainBus);
            }
            // ТРАМВАЕВ, АВТОПОЕЗДОВ и
            if (car && bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrolleybus);
            }
            if (car && !bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrolleybus);
            }
            if (!car && bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrolleybus);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            if (car && !bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrainBus, bTrolleybus);
            }
            if (!car && bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrainBus, bTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            if (!car && !bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrainBus, bTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            if (car && !bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrainBus, bTram);
            }
            if (!car && bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrainBus, bTram);
            }
            // ГРУЗОВЫХ, АВТОБУСОВ и
            if (!car && !bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrainBus, bTrolleybus, bTram);
            }
            // ТРОЛЛЕЙБУСОВ, АВТОБУСОВ и
            if (!car && !bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrainBus, bTram);
            }
            // Ещё
            if (car && bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTram);
            }
            if (car && !bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrolleybus, bTram);
            }
            if (!car && bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTram);
            }
            if (!car && bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrolleybus, bTram);
            }
            if (!car && !bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrolleybus, bTram);
            }
            // 5. Нет четырех из всех (15)
            if (car && bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus);
            }
            if (car && !bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck);
            }
            if (car && !bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrainBus);
            }
            if (car && !bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrolleybus);
            }
            if (car && !bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTram);
            }
            if (!car && bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck);
            }
            if (!car && bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrainBus);
            }
            if (!car && bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrolleybus);
            }
            if (!car && bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTram);
            }
            if (!car && !bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrainBus);
            }
            if (!car && !bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrolleybus);
            }
            if (!car && !bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTram);
            }
            if (!car && !bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrainBus, bTrolleybus);
            }
            if (!car && !bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrainBus, bTram);
            }
            if (!car && !bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrolleybus, bTram);
            }
            // 6. Нет пяти из всех (6)
            if (!car && !bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTram);
            }
            if (!car && !bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrolleybus);
            }
            if (!car && !bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrainBus);
            }
            if (!car && !bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck);
            }
            if (!car && bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus);
            }
            if (car && !bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar);
            }

            return components;
        }
        if (sideIdentifier.equalsIgnoreCase("1")) {
            doComponents3 doComp3 = new doComponents3();
            JComponent[] components = doComp3.doComponentsWithoutLeft(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
            // 1. Есть все
            if (car && bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 2. Нет одного из всех (6)
            if (car && bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrainBus, bTrolleybus);
            }
            if (car && bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrainBus, bTram);
            }
            if (car && bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
            }
            if (car && bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrainBus, bTrolleybus, bTram);
            }
            if (car && !bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            if (!car && bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 3. Нет двух из всех (15)
            // ТРАМВАЙ и
            if (car && bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrainBus);
            }
            if (car && bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrolleybus);
            }
            if (car && bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrainBus, bTrolleybus);
            }
            if (car && !bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrainBus, bTrolleybus);
            }
            if (!car && bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrainBus, bTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            if (car && bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTram);
            }
            if (car && bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrainBus, bTram);
            }
            if (car && !bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrainBus, bTram);
            }
            if (!car && bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrainBus, bTram);
            }
            // АВТОПОЕЗДА и
            if (car && bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrolleybus, bTram);
            }
            if (car && !bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrolleybus, bTram);
            }
            if (!car && bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrolleybus, bTram);
            }
            // ГРУЗОВЫЕ и
            if (car && !bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrainBus, bTrolleybus, bTram);
            }
            if (!car && bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrainBus, bTrolleybus, bTram);
            }
            // АВТОБУСЫ и
            if (!car && !bus && truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrainBus, bTrolleybus, bTram);
            }
            // 4. Нет трех из всех (19)
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            if (car && bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck);
            }
            if (car && bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrainBus);
            }
            if (car && !bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrainBus);
            }
            if (!car && bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrainBus);
            }
            // ТРАМВАЕВ, АВТОПОЕЗДОВ и
            if (car && bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrolleybus);
            }
            if (car && !bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrolleybus);
            }
            if (!car && bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrolleybus);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            if (car && !bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrainBus, bTrolleybus);
            }
            if (!car && bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrainBus, bTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            if (!car && !bus && truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrainBus, bTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            if (car && !bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrainBus, bTram);
            }
            if (!car && bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrainBus, bTram);
            }
            // ГРУЗОВЫХ, АВТОБУСОВ и
            if (!car && !bus && !truck && trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrainBus, bTrolleybus, bTram);
            }
            // ТРОЛЛЕЙБУСОВ, АВТОБУСОВ и
            if (!car && !bus && truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrainBus, bTram);
            }
            // Ещё
            if (car && bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTram);
            }
            if (car && !bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrolleybus, bTram);
            }
            if (!car && bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTram);
            }
            if (!car && bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrolleybus, bTram);
            }
            if (!car && !bus && truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrolleybus, bTram);
            }
            // 5. Нет четырех из всех (15)
            if (car && bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus);
            }
            if (car && !bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck);
            }
            if (car && !bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrainBus);
            }
            if (car && !bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrolleybus);
            }
            if (car && !bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTram);
            }
            if (!car && bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck);
            }
            if (!car && bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrainBus);
            }
            if (!car && bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrolleybus);
            }
            if (!car && bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTram);
            }
            if (!car && !bus && truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrainBus);
            }
            if (!car && !bus && truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrolleybus);
            }
            if (!car && !bus && truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTram);
            }
            if (!car && !bus && !truck && trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrainBus, bTrolleybus);
            }
            if (!car && !bus && !truck && trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrainBus, bTram);
            }
            if (!car && !bus && !truck && !trainbus && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrolleybus, bTram);
            }
            // 6. Нет пяти из всех (6)
            if (!car && !bus && !truck && !trainbus && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTram);
            }
            if (!car && !bus && !truck && !trainbus && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrolleybus);
            }
            if (!car && !bus && !truck && trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrainBus);
            }
            if (!car && !bus && truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck);
            }
            if (!car && bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus);
            }
            if (car && !bus && !truck && !trainbus && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar);
            }

            return components;
        }
        doComponents3 doComp3 = new doComponents3();
        JComponent[] components = doComp3.doComponentsWithoutForward(labels);
        return components;
    }

    public int getRow() {
        return row;
    }

}
