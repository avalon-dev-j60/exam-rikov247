package ru.trafficClicker;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Класс для создания панели вкладок с таблицами 15минутками и общей таблицей
 */
public class TabbedPaneTableOnePartOfDay {

    private JTabbedPane tableTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

    public JTabbedPane createTabbedPane(JScrollPane table15, JScrollPane table30, JScrollPane table45, JScrollPane table60, JScrollPane tableSum) throws IOException {
        tableTabs.setFocusable(false);
        JScrollPane[] panes = {table15, table30, table45, table60, tableSum};
        for (int i = 0; i < panes.length; i++) {
            if (panes[i] == null) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.white);
                panes[i] = new JScrollPane(panel);
            }
        }
        // Вкладка 1 (index = 0).
        tableTabs.addTab("Таблица 0-15 минут", panes[0]);

        // Вкладка 2 (index = 1).
        tableTabs.addTab("Таблица 15-30 минут", panes[1]);

        // Вкладка 3 (index = 2).
        tableTabs.addTab("Таблица 30-45 минут", panes[2]);

        // Вкладка 4 (index = 3).
        tableTabs.addTab("Таблица 45-60 минут", panes[3]);

        // Вкладка 5 (index = 4).
        tableTabs.addTab("Таблица Итог", panes[4]);

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
                if (tableTabs.getSelectedIndex() == 4) {
                    panes[4].requestFocus(); // переключаем фокус
                }
            }
        });

        return tableTabs;
    }
}
