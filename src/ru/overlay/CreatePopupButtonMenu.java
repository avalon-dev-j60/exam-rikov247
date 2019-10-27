/**
 * Всплывающее меню
 */
package ru.overlay;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.quinto.swing.table.view.JBroTable;

public class CreatePopupButtonMenu {

    private JBroTable table; // таблица
    private String fieldName; // идентификатор столбца
    private int row; // строка

    private JPopupMenu bus = new JPopupMenu();
    // Now
    private JMenuItem bigBus;
    private JMenuItem averageBus;
    private JMenuItem microBus;
    // Future
    private JMenuItem verySmallBusf;
    private JMenuItem smallBusf;
    private JMenuItem averageBusf;
    private JMenuItem bigBusf;
    private JMenuItem veryBigBusf;

    private JPopupMenu track = new JPopupMenu();
    // Now
    private JMenuItem track2;
    private JMenuItem track2to6;
    private JMenuItem track6to12;
    private JMenuItem track12to20;
    private JMenuItem trackMore20;
    // Future
    private JMenuItem track2f;
    private JMenuItem track3f;
    private JMenuItem track4f;
    private JMenuItem track4with2f;
    private JMenuItem track5with3f;

    private JPopupMenu trainBus = new JPopupMenu();
    //Future
    private JMenuItem trainBus3with2;
    private JMenuItem trainBus4with2;
    private JMenuItem trainBus5with2;
    private JMenuItem trainBus5with3;
    private JMenuItem trainBus6;
    private JMenuItem trainBus7more;

    public CreatePopupButtonMenu(String typeOfStatement) {

        if (typeOfStatement.equalsIgnoreCase("Now")) {
            bus.removeAll(); // очищаем все компоненты во всплывающем меню
            track.removeAll();

            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE); // создаем иконку размеров 1 на 1 с прозрачным пикселем
            bigBus = new JMenuItem("Большой автобус");
            averageBus = new JMenuItem("Средний автобус");
            microBus = new JMenuItem("Микроавтобус");

            bus.add(bigBus);
            bus.add(averageBus);
            bus.add(microBus);

            track2 = new JMenuItem("До 2-х тонн");
            track2to6 = new JMenuItem("От 2 до 6 тонн");
            track6to12 = new JMenuItem("От 6 до 12 тонн");
            track12to20 = new JMenuItem("От 12 до 20 тонн");
            trackMore20 = new JMenuItem("Более 20 тонн");
            track.add(track2);
            track.add(track2to6);
            track.add(track6to12);
            track.add(track12to20);
            track.add(trackMore20);

        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            track.removeAll(); // очищаем все компоненты во всплывающем меню
            trainBus.removeAll();
            bus.removeAll();

            track2f = new JMenuItem("2-осные");
            track3f = new JMenuItem("3-осные");
            track4f = new JMenuItem("4-осные");
            track4with2f = new JMenuItem("4-осные (2 оси + прицеп)");
            track5with3f = new JMenuItem("5-осные (3 оси + прицеп)");
            track.add(track2f);
            track.add(track3f);
            track.add(track4f);
            track.add(track4with2f);
            track.add(track5with3f);

            trainBus3with2 = new JMenuItem("3-осные (2 оси + полуприцеп)");
            trainBus4with2 = new JMenuItem("4-осные (2 оси + полуприцеп)");
            trainBus5with2 = new JMenuItem("5-осные (2 оси + полуприцеп)");
            trainBus5with3 = new JMenuItem("5-осные (3 оси + полуприцеп)");
            trainBus6 = new JMenuItem("6-осные");
            trainBus7more = new JMenuItem("7-осные");
            trainBus.add(trainBus3with2);
            trainBus.add(trainBus4with2);
            trainBus.add(trainBus5with2);
            trainBus.add(trainBus5with3);
            trainBus.add(trainBus6);
            trainBus.add(trainBus7more);

            verySmallBusf = new JMenuItem("Особо малого класса");
            smallBusf = new JMenuItem("Малого класса");
            averageBusf = new JMenuItem("Среднего класса");
            bigBusf = new JMenuItem("Большого класса");
            veryBigBusf = new JMenuItem("Особо большого класса");
            bus.add(verySmallBusf);
            bus.add(smallBusf);
            bus.add(averageBusf);
            bus.add(bigBusf);
            bus.add(veryBigBusf);
        }

//        ButtonGroup bg = new ButtonGroup(); // для добавление радиоКнопок в группу
//        bg.add(menuItem7);
//        bg.add(menuItem8);
//        bg.add(menuItem9);
//
//        menu2.add(menuItem7);
//        menu2.add(menuItem8);
//        menu2.add(menuItem9);
//        menu1.add(menuItem4);
//        menu1.add(menuItem5);
//        menu1.add(menuItem6);
//        menu1.add(menu2);
//        bus.add(menu1);
//
//        bus.add(menuItem10);
//        bus.add(menuItem11);
    }

    // Now. Автобусы
    public JPopupMenu getBus() {
        return bus;
    }

    public JMenuItem getBigBus() {
        return bigBus;
    }

    public JMenuItem getAverageBus() {
        return averageBus;
    }

    public JMenuItem getMicroBus() {
        return microBus;
    }

    // Future. Автобусы
    public JMenuItem getVerySmallBusf() {
        return verySmallBusf;
    }

    public JMenuItem getSmallBusf() {
        return smallBusf;
    }

    public JMenuItem getAverageBusf() {
        return averageBusf;
    }

    public JMenuItem getBigBusf() {
        return bigBusf;
    }

    public JMenuItem getVeryBigBusf() {
        return veryBigBusf;
    }

    // Now. Грузовые
    public JPopupMenu getTruck() {
        return track;
    }

    public JMenuItem getTruck2() {
        return track2;
    }

    public JMenuItem getTruck2to6() {
        return track2to6;
    }

    public JMenuItem getTruck6to12() {
        return track6to12;
    }

    public JMenuItem getTruck12to20() {
        return track12to20;
    }

    public JMenuItem getTruckMore20() {
        return trackMore20;
    }
    
    // Future. Грузовые

    public JMenuItem getTruck2f() {
        return track2f;
    }

    public JMenuItem getTruck3f() {
        return track3f;
    }

    public JMenuItem getTruck4f() {
        return track4f;
    }

    public JMenuItem getTruck4with2f() {
        return track4with2f;
    } 

    public JMenuItem getTruck5with3f() {
        return track5with3f;
    }    

    // Future. Автопоезда
    public JPopupMenu getTrainBus() {
        return trainBus;
    }

    public JMenuItem getTrainBus3with2() {
        return trainBus3with2;
    }

    public JMenuItem getTrainBus4with2() {
        return trainBus4with2;
    }

    public JMenuItem getTrainBus5with2() {
        return trainBus5with2;
    }

    public JMenuItem getTrainBus5with3() {
        return trainBus5with3;
    }

    public JMenuItem getTrainBus6() {
        return trainBus6;
    }

    public JMenuItem getTrainBus7more() {
        return trainBus7more;
    }

}
