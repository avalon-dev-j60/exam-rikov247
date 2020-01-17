package ru.trafficClicker;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Класс для создания панели вкладок с таблицами 15минутками и общей таблицей
 */
public class TabbedPaneTableAllDay {

    private JTabbedPane tableTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

    public JTabbedPane createTabbedPane(JTabbedPane tableMorning, JTabbedPane tableDay, JTabbedPane tableEvening) throws IOException {
        tableTabs.setFocusable(false);
        JTabbedPane[] panes = {tableMorning, tableDay, tableEvening};
        for (int i = 0; i < panes.length; i++) {
            if (panes[i] == null) {
                JTabbedPane tabbedPane = new JTabbedPane();
                JPanel panel = new JPanel();
                panel.setBackground(Color.white);
                tabbedPane.addTab("Пустая вкладка", panel);
                panes[i] = tabbedPane;
            }
        }
        // Вкладка 1 (index = 0).
        tableTabs.addTab("Таблицы утро", panes[0]);

        // Вкладка 2 (index = 1).
        tableTabs.addTab("Таблицы день", panes[1]);

        // Вкладка 3 (index = 2).
        tableTabs.addTab("Таблицы вечер", panes[2]);

        // При изменении вкладки перекидываем фокус куда нужно
        tableTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tableTabs.getSelectedIndex() == 0) {
                    panes[0].requestFocus(); // переключаем фокус
                }
                if (tableTabs.getSelectedIndex() == 1) {
                    panes[1].requestFocus(); // переключаем фокус
                }
                if (tableTabs.getSelectedIndex() == 2) {
                    panes[2].requestFocus(); // переключаем фокус
                }
            }
        });

        return tableTabs;
    }
    
    public JTabbedPane createTabbedPane(JTabbedPane tableMorning, JTabbedPane tableDay, JTabbedPane tableEvening, JComponent tableTotal) throws IOException {
        tableTabs.setFocusable(false);
        JComponent[] panes = {tableMorning, tableDay, tableEvening, tableTotal};
        for (int i = 0; i < panes.length; i++) {
            if (panes[i] == null) {
                JTabbedPane tabbedPane = new JTabbedPane();
                JPanel panel = new JPanel();
                panel.setBackground(Color.white);
                tabbedPane.addTab("Пустая вкладка", panel);
                panes[i] = tabbedPane;
            }
        }
        // Вкладка 1 (index = 0).
        tableTabs.addTab("Таблицы утро", panes[0]);

        // Вкладка 2 (index = 1).
        tableTabs.addTab("Таблицы день", panes[1]);

        // Вкладка 3 (index = 2).
        tableTabs.addTab("Таблицы вечер", panes[2]);

        // Вкладка 4 (index = 3).
        tableTabs.addTab("Таблица итог", panes[3]);

        // При изменении вкладки перекидываем фокус куда нужно
        tableTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tableTabs.getSelectedIndex() == 0) {
                    panes[0].requestFocus(); // переключаем фокус
                }
                if (tableTabs.getSelectedIndex() == 1) {
                    panes[1].requestFocus(); // переключаем фокус
                }
                if (tableTabs.getSelectedIndex() == 2) {
                    panes[2].requestFocus(); // переключаем фокус
                }
                if (tableTabs.getSelectedIndex() == 3) {
                    panes[3].requestFocus(); // переключаем фокус
                }
            }
        });

        return tableTabs;
    }
}
