package ru.overlay;

import javax.swing.JComponent;
import javax.swing.tree.TreePath;

/**
 * Класс для выбора того, что отображать на панелях кнопок. <n>
 * Сделано для варианта с 3 направлениями.
 */
public class ChooseComponentsNow3Up {

    private TreePath[] paths; // массив путей к узлам (видам транспорта), которые были выбраны для подсчета
    private int row; // количество строк на панели кнопок (количество видов транспорта для подсчета, не считая labels направлений)

    private JComponent[] labels;
    private JComponent[] bCar;
    private JComponent[] bBus;
    private JComponent[] bTruck;
    private JComponent[] bTrolleybus;
    private JComponent[] bTram;
    private String sideIdentifier;

    public ChooseComponentsNow3Up(String sideIdentifier, TreePath[] paths, JComponent[] labels, JComponent[] bCar, JComponent[] bBus, JComponent[] bTruck, JComponent[] bTrolleybus, JComponent[] bTram) {
        this.sideIdentifier = sideIdentifier;
        this.paths = paths;
        this.labels = labels;
        this.bCar = bCar;
        this.bBus = bBus;
        this.bTruck = bTruck;
        this.bTrolleybus = bTrolleybus;
        this.bTram = bTram;
    }

    public JComponent[] chooseCompNow3withoutForward() {

        row = 1;
        // Определяем, какие кнопки отображать и что будем считать
        boolean car = false;
        boolean bus = false;
        boolean truck = false;
        boolean trolleybus = false;
        boolean tram = false;
        for (TreePath tp : paths) {
            String temp = String.valueOf(tp.getLastPathComponent());
            if (temp.equalsIgnoreCase("Легковой транспорт")) {
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
            JComponent[] components = doComp3.doComponentsWithoutLeft(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
            // 1. Есть все
            if (car && bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
            }
            // 2. Нет одного из всех:
            // 2.1. нет трамвая
            if (car && bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTrolleybus);
            }
            // 2.2. нет троллейбуса
            if (car && bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck, bTram);
            }
            // 2.3. нет грузовых
            if (car && bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrolleybus, bTram);
            }
            // 2.4. нет автобусов
            if (car && !bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrolleybus, bTram);
            }
            // 2.5. нет машин
            if (!car && bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrolleybus, bTram);
            }
            // 3. Нет двух из всех
            // ТРАМВАЙ и
            // 3.1. нет трамвая и троллейбуса
            if (car && bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTruck);
            }
            // 3.2. нет трамвая и грузовых
            if (car && bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTrolleybus);
            }
            // 3.3. нет трамвая и автобусов
            if (car && !bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTrolleybus);
            }
            // 3.4. нет трамвая и машин
            if (!car && bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            // 3.5. нет троллейбуса и грузовых
            if (car && bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus, bTram);
            }
            // 3.6. нет троллейбуса и автобусов
            if (car && !bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck, bTram);
            }
            // 3.7. нет троллейбуса и машин
            if (!car && bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck, bTram);
            }
            // ГРУЗОВЫЕ и
            // 3.8. нет грузовых и автобусов
            if (car && !bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrolleybus, bTram);
            }
            // 3.9. нет грузовых и машин
            if (!car && bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrolleybus, bTram);
            }
            // АВТОБУСЫ и
            // 3.10. нет автобусов и машин
            if (!car && !bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrolleybus, bTram);
            }
            // 4. Нет трех из всех
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            // 4.1. нет трамваев,троллейбусов и грузовых
            if (car && bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bBus);
            }
            // 4.2. нет трамваев, троллейбусов и автобусов
            if (car && !bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTruck);
            }
            // 4.3. нет трамваев, троллейбусов и машин
            if (!car && bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTruck);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            // 4.4. нет трамваев, грузовых и автобусов
            if (car && !bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTrolleybus);
            }
            // 4.5. нет трамваев, грузовых и машин
            if (!car && bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            // 4.6. нет трамваев, автобусов и машин
            if (!car && !bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            // 4.7. нет троллейбусов, грузовых и автобусов
            if (car && !bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar, bTram);
            }
            // 4.8. нет троллейбусов, грузовых и машин
            if (!car && bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus, bTram);
            }
            // ГРУЗОВЫХ,АВТОБУСОВ и
            // 4.9. нет грузовых, автобусов и машин
            if (!car && !bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrolleybus, bTram);
            }
            // ТРОЛЛЕЙБУСОВ,АВТОБУСОВ и
            // 4.10. нет троллейбусов, автобусов и машин
            if (!car && !bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck, bTram);
            }
            // 5. Нет четырех из всех
            // 5.1. есть только трамваи
            if (!car && !bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTram);
            }
            // 5.2. есть только троллейбусы
            if (!car && !bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTrolleybus);
            }
            // 5.3. есть только грузовые
            if (!car && !bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bTruck);
            }
            // 5.4. есть только автобусы
            if (!car && bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bBus);
            }
            // 5.5. есть только машины
            if (car && !bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutLeft(labels, bCar);
            }

            return components;
        }
        if (sideIdentifier.equalsIgnoreCase("3")) {
            doComponents3 doComp3 = new doComponents3();
            JComponent[] components = doComp3.doComponentsWithoutForward(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
            // 1. Есть все
            if (car && bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
            }
            // 2. Нет одного из всех:
            // 2.1. нет трамвая
            if (car && bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTrolleybus);
            }
            // 2.2. нет троллейбуса
            if (car && bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck, bTram);
            }
            // 2.3. нет грузовых
            if (car && bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrolleybus, bTram);
            }
            // 2.4. нет автобусов
            if (car && !bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrolleybus, bTram);
            }
            // 2.5. нет машин
            if (!car && bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrolleybus, bTram);
            }
            // 3. Нет двух из всех
            // ТРАМВАЙ и
            // 3.1. нет трамвая и троллейбуса
            if (car && bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTruck);
            }
            // 3.2. нет трамвая и грузовых
            if (car && bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTrolleybus);
            }
            // 3.3. нет трамвая и автобусов
            if (car && !bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTrolleybus);
            }
            // 3.4. нет трамвая и машин
            if (!car && bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            // 3.5. нет троллейбуса и грузовых
            if (car && bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus, bTram);
            }
            // 3.6. нет троллейбуса и автобусов
            if (car && !bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck, bTram);
            }
            // 3.7. нет троллейбуса и машин
            if (!car && bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck, bTram);
            }
            // ГРУЗОВЫЕ и
            // 3.8. нет грузовых и автобусов
            if (car && !bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrolleybus, bTram);
            }
            // 3.9. нет грузовых и машин
            if (!car && bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrolleybus, bTram);
            }
            // АВТОБУСЫ и
            // 3.10. нет автобусов и машин
            if (!car && !bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrolleybus, bTram);
            }
            // 4. Нет трех из всех
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            // 4.1. нет трамваев,троллейбусов и грузовых
            if (car && bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bBus);
            }
            // 4.2. нет трамваев, троллейбусов и автобусов
            if (car && !bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTruck);
            }
            // 4.3. нет трамваев, троллейбусов и машин
            if (!car && bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTruck);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            // 4.4. нет трамваев, грузовых и автобусов
            if (car && !bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTrolleybus);
            }
            // 4.5. нет трамваев, грузовых и машин
            if (!car && bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            // 4.6. нет трамваев, автобусов и машин
            if (!car && !bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            // 4.7. нет троллейбусов, грузовых и автобусов
            if (car && !bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar, bTram);
            }
            // 4.8. нет троллейбусов, грузовых и машин
            if (!car && bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus, bTram);
            }
            // ГРУЗОВЫХ,АВТОБУСОВ и
            // 4.9. нет грузовых, автобусов и машин
            if (!car && !bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrolleybus, bTram);
            }
            // ТРОЛЛЕЙБУСОВ,АВТОБУСОВ и
            // 4.10. нет троллейбусов, автобусов и машин
            if (!car && !bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck, bTram);
            }
            // 5. Нет четырех из всех
            // 5.1. есть только трамваи
            if (!car && !bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTram);
            }
            // 5.2. есть только троллейбусы
            if (!car && !bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTrolleybus);
            }
            // 5.3. есть только грузовые
            if (!car && !bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bTruck);
            }
            // 5.4. есть только автобусы
            if (!car && bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bBus);
            }
            // 5.5. есть только машины
            if (car && !bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutForward(labels, bCar);
            }

            return components;
        }
        if (sideIdentifier.equalsIgnoreCase("2")) {
            doComponents3 doComp3 = new doComponents3();
            JComponent[] components = doComp3.doComponentsWithoutRight(labels);  // если пользователь не выберет ничего для подсчета - то появятся только labels направлений
            // 1. Есть все
            if (car && bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrolleybus, bTram);
            }
            // 2. Нет одного из всех:
            // 2.1. нет трамвая
            if (car && bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTrolleybus);
            }
            // 2.2. нет троллейбуса
            if (car && bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck, bTram);
            }
            // 2.3. нет грузовых
            if (car && bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrolleybus, bTram);
            }
            // 2.4. нет автобусов
            if (car && !bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrolleybus, bTram);
            }
            // 2.5. нет машин
            if (!car && bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrolleybus, bTram);
            }
            // 3. Нет двух из всех
            // ТРАМВАЙ и
            // 3.1. нет трамвая и троллейбуса
            if (car && bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTruck);
            }
            // 3.2. нет трамвая и грузовых
            if (car && bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTrolleybus);
            }
            // 3.3. нет трамвая и автобусов
            if (car && !bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTrolleybus);
            }
            // 3.4. нет трамвая и машин
            if (!car && bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTrolleybus);
            }
            // ТРОЛЛЕЙБУС и
            // 3.5. нет троллейбуса и грузовых
            if (car && bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus, bTram);
            }
            // 3.6. нет троллейбуса и автобусов
            if (car && !bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck, bTram);
            }
            // 3.7. нет троллейбуса и машин
            if (!car && bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck, bTram);
            }
            // ГРУЗОВЫЕ и
            // 3.8. нет грузовых и автобусов
            if (car && !bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrolleybus, bTram);
            }
            // 3.9. нет грузовых и машин
            if (!car && bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrolleybus, bTram);
            }
            // АВТОБУСЫ и
            // 3.10. нет автобусов и машин
            if (!car && !bus && truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrolleybus, bTram);
            }
            // 4. Нет трех из всех
            // ТРАМВАЕВ, ТРОЛЛЕЙБУСОВ и
            // 4.1. нет трамваев,троллейбусов и грузовых
            if (car && bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bBus);
            }
            // 4.2. нет трамваев, троллейбусов и автобусов
            if (car && !bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTruck);
            }
            // 4.3. нет трамваев, троллейбусов и машин
            if (!car && bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTruck);
            }
            // ТРАМВАЕВ, ГРУЗОВЫХ и
            // 4.4. нет трамваев, грузовых и автобусов
            if (car && !bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTrolleybus);
            }
            // 4.5. нет трамваев, грузовых и машин
            if (!car && bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTrolleybus);
            }
            // ТРАМВАЕВ, АВТОБУСОВ и
            // 4.6. нет трамваев, автобусов и машин
            if (!car && !bus && truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTrolleybus);
            }
            // ТРОЛЛЕЙБУСОВ, ГРУЗОВЫХ и
            // 4.7. нет троллейбусов, грузовых и автобусов
            if (car && !bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar, bTram);
            }
            // 4.8. нет троллейбусов, грузовых и машин
            if (!car && bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus, bTram);
            }
            // ГРУЗОВЫХ,АВТОБУСОВ и
            // 4.9. нет грузовых, автобусов и машин
            if (!car && !bus && !truck && trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrolleybus, bTram);
            }
            // ТРОЛЛЕЙБУСОВ,АВТОБУСОВ и
            // 4.10. нет троллейбусов, автобусов и машин
            if (!car && !bus && truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck, bTram);
            }
            // 5. Нет четырех из всех
            // 5.1. есть только трамваи
            if (!car && !bus && !truck && !trolleybus && tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTram);
            }
            // 5.2. есть только троллейбусы
            if (!car && !bus && !truck && trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTrolleybus);
            }
            // 5.3. есть только грузовые
            if (!car && !bus && truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bTruck);
            }
            // 5.4. есть только автобусы
            if (!car && bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bBus);
            }
            // 5.5. есть только машины
            if (car && !bus && !truck && !trolleybus && !tram) {
                components = doComp3.doComponentsWithoutRight(labels, bCar);
            }
            return components;
        }
        doComponents3 doComp3withoutForward = new doComponents3();
        JComponent[] components = doComp3withoutForward.doComponentsWithoutForward(labels);
        return components;
    }

    public int getRow() {
        return row;
    }

}
