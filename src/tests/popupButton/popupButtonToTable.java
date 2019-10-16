package tests.popupButton;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.quinto.swing.table.view.JBroTable;
import ru.trafficClicker.OnButtonClick;

/**
 * Этот класс добавляет к нужным кнопкам нужные всплывающие панели (popup) и
 * добавляет слушателей - которые направляют нужный menuitem в нужную ячейку
 * таблицы.
 */
public class popupButtonToTable {

    public popupButtonToTable(JButton button, JBroTable table, String typeOfStatement, String fieldName, String typeOfTransport) {
        // Если вид таблицы - старый
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            // Если указан нужный столбец, то
            // Создаем объект в котором создается меню и его дети
            CreatePopupButtonMenu popupMenu = new CreatePopupButtonMenu(typeOfStatement);
            if (typeOfTransport.equalsIgnoreCase("Truck")) {
                // Привязываем popupMenu к определенной кнопке (Грузовой транспорт)
                popupButton(button, popupMenu.getTruck());
                // К элементам этого popupMenu привязываем слушателей. Передаем таблицу, строку и столбец
                popupMenu.getTruck2().addActionListener(new OnButtonClick(table, "До 2-х тонн", fieldName)::onButtonClick);
                popupMenu.getTruck2to6().addActionListener(new OnButtonClick(table, "От 2 до 6 тонн", fieldName)::onButtonClick);
                popupMenu.getTruck6to12().addActionListener(new OnButtonClick(table, "От 6 до 12 тонн", fieldName)::onButtonClick);
                popupMenu.getTruck12to20().addActionListener(new OnButtonClick(table, "От 12 до 20 тонн", fieldName)::onButtonClick);
                popupMenu.getTruckMore20().addActionListener(new OnButtonClick(table, "Более 20 тонн", fieldName)::onButtonClick);
            }
            if (typeOfTransport.equalsIgnoreCase("Bus")) {
                // Привязываем popupMenu к определенной кнопке (Автобусы)
                popupButton(button, popupMenu.getBus());
                // К элементам этого popupMenu привязываем слушателей
                popupMenu.getBigBus().addActionListener(new OnButtonClick(table, "Большой автобус", fieldName)::onButtonClick);
                popupMenu.getAverageBus().addActionListener(new OnButtonClick(table, "Средний автобус", fieldName)::onButtonClick);
                popupMenu.getMicroBus().addActionListener(new OnButtonClick(table, "Микроавтобус", fieldName)::onButtonClick);
            }
        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            // Если указан нужный столбец, то
            // Создаем объект в котором создается меню и его дети
            CreatePopupButtonMenu popupMenu = new CreatePopupButtonMenu(typeOfStatement);
            if (typeOfTransport.equalsIgnoreCase("Truck")) {
                // Привязываем popupMenu к определенной кнопке (Грузовой транспорт)
                popupButton(button, popupMenu.getTruck());
                // К элементам этого popupMenu привязываем слушателей
                popupMenu.getTruck2f().addActionListener(new OnButtonClick(table, "2-осные", fieldName)::onButtonClick);
                popupMenu.getTruck3f().addActionListener(new OnButtonClick(table, "3-осные", fieldName)::onButtonClick);
                popupMenu.getTruck4f().addActionListener(new OnButtonClick(table, "4-осные", fieldName)::onButtonClick);
                popupMenu.getTruck4with2f().addActionListener(new OnButtonClick(table, "4-осные (2 оси+прицеп)", fieldName)::onButtonClick);
                popupMenu.getTruck5with3f().addActionListener(new OnButtonClick(table, "5-осные (3 оси+прицеп)", fieldName)::onButtonClick);
            }
            if (typeOfTransport.equalsIgnoreCase("Bus")) {
                // Привязываем popupMenu к определенной кнопке (Автобусы)
                popupButton(button, popupMenu.getBus());
                // К элементам этого popupMenu привязываем слушателей
                popupMenu.getVerySmallBusf().addActionListener(new OnButtonClick(table, "Особо малого класса", fieldName)::onButtonClick);
                popupMenu.getSmallBusf().addActionListener(new OnButtonClick(table, "Малого класса", fieldName)::onButtonClick);
                popupMenu.getAverageBusf().addActionListener(new OnButtonClick(table, "Среднего класса", fieldName)::onButtonClick);
                popupMenu.getBigBusf().addActionListener(new OnButtonClick(table, "Большого класса", fieldName)::onButtonClick);
                popupMenu.getVeryBigBusf().addActionListener(new OnButtonClick(table, "Особо большого класса", fieldName)::onButtonClick);
            }
            if (typeOfTransport.equalsIgnoreCase("TrainBus")) {
                // Привязываем popupMenu к определенной кнопке (Автопоезда)
                popupButton(button, popupMenu.getTrainBus());
                // К элементам этого popupMenu привязываем слушателей
                popupMenu.getTrainBus3with2().addActionListener(new OnButtonClick(table, "3 осные (2 оси+полуприцеп)", fieldName)::onButtonClick);
                popupMenu.getTrainBus4with2().addActionListener(new OnButtonClick(table, "4 осные (2 оси+полуприцеп)", fieldName)::onButtonClick);
                popupMenu.getTrainBus5with2().addActionListener(new OnButtonClick(table, "5 осные (2 оси+полуприцеп)", fieldName)::onButtonClick);
                popupMenu.getTrainBus5with3().addActionListener(new OnButtonClick(table, "5 осные (3 оси+полуприцеп)", fieldName)::onButtonClick);
                popupMenu.getTrainBus6().addActionListener(new OnButtonClick(table, "6 осные", fieldName)::onButtonClick);
                popupMenu.getTrainBus7more().addActionListener(new OnButtonClick(table, "7 осные и более", fieldName)::onButtonClick);
            }
        }
    }

    // Добавляем к кнопке выбранное popupMenu и указываем место отображения
    private void popupButton(JButton button, JPopupMenu popMenu) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) { // если кликнули по правой кнопке мыши
                    Component b = (Component) e.getSource(); // получаем компонент, по которому кликнули - кнопка
                    Point p = b.getLocationOnScreen(); // получаем положение на экране компонента по которому кликнули - кнопки

                    popMenu.show(e.getComponent(), 0, 0); // отображаем меню (указываем: компонет, в пространстве которого показываем popupMenu; и координаты popupMenu в системе координат компонента по которому кликнули - кнопки
                    popMenu.setLocation(p.x, p.y + b.getHeight()); // Устанавливаем положение popupMenu в системе координат области, в которой находится компонент, по которому кликнули - кнопка. по x = левый угол кнопки; по y - низ кнопки
                }
            }
        });
    }

}
