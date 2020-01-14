package ru.trafficClicker;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Класс для создания панели вкладок с таблицами 15минутками и общей таблицей
 */
public class TabbedPaneCartogram {

    private JTabbedPane cartogramTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

    public JTabbedPane createTabbedPane(JComponent cartogramMorning, JComponent cartogramDay, JComponent cartogramEvening) throws IOException {
        cartogramTabs.setFocusable(false);
        JComponent[] panes = {cartogramMorning, cartogramDay, cartogramEvening};
        for (int i = 0; i < panes.length; i++) {
            if (panes[i] == null) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.white);
                panes[i] = panel;
            }
        }
        // Вкладка 1 (index = 0).
        cartogramTabs.addTab("Картограмма утро", panes[0]);

        // Вкладка 2 (index = 1).
        cartogramTabs.addTab("Картограмма день", panes[1]);

        // Вкладка 3 (index = 2).
        cartogramTabs.addTab("Картограмма вечер", panes[2]);

        // При изменении вкладки перекидываем фокус куда нужно
        cartogramTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (cartogramTabs.getSelectedIndex() == 0) {
                    panes[0].requestFocus(); // переключаем фокус
                }
                if (cartogramTabs.getSelectedIndex() == 1) {
                    panes[1].requestFocus(); // переключаем фокус
                }
                if (cartogramTabs.getSelectedIndex() == 2) {
                    panes[2].requestFocus(); // переключаем фокус
                }
            }
        });

        return cartogramTabs;
    }
    
    public JTabbedPane createTabbedPane(JComponent cartogramMorning, JComponent cartogramDay, JComponent cartogramEvening, JComponent cartogramTotalDay) throws IOException {
        cartogramTabs.setFocusable(false);
        JComponent[] panes = {cartogramMorning, cartogramDay, cartogramEvening, cartogramTotalDay};
        for (int i = 0; i < panes.length; i++) {
            if (panes[i] == null) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.white);
                panes[i] = panel;
            }
        }
        // Вкладка 1 (index = 0).
        cartogramTabs.addTab("Картограмма утро", panes[0]);

        // Вкладка 2 (index = 1).
        cartogramTabs.addTab("Картограмма день", panes[1]);

        // Вкладка 3 (index = 2).
        cartogramTabs.addTab("Картограмма вечер", panes[2]);
        
        // Вкладка 4 (index = 3).
        cartogramTabs.addTab("Картограмма итог", panes[3]);

        // При изменении вкладки перекидываем фокус куда нужно
        cartogramTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (cartogramTabs.getSelectedIndex() == 0) {
                    panes[0].requestFocus(); // переключаем фокус
                }
                if (cartogramTabs.getSelectedIndex() == 1) {
                    panes[1].requestFocus(); // переключаем фокус
                }
                if (cartogramTabs.getSelectedIndex() == 2) {
                    panes[2].requestFocus(); // переключаем фокус
                }
                if (cartogramTabs.getSelectedIndex() == 3) {
                    panes[3].requestFocus(); // переключаем фокус
                }
            }
        });

        return cartogramTabs;
    }
    
}
