package ru.trafficClicker;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * Класс для создания разделенной панели с картограммой и панелью управления ею
 */
public class SplitCartogramPanel {

    private JSplitPane splitTab1 = new JSplitPane(); // создание разделяющейся панели (левая панель / видео + правая панель)
    private JSplitPane splitTab2 = new JSplitPane(); // создание разделяющейся панели(видео / правая панель)

    public JSplitPane createCartogramSplitPanel(JComponent leftComponent, JComponent centerComponent, JComponent rightComponent) {
        splitTab1.setDividerSize(4); // ширина разделительной области
        splitTab2.setDividerSize(4); // ширина разделительной области
        splitTab1.setContinuousLayout(true); //  компоненты при перемещении разделительной полосы будут непрерывно обновляться (перерисовываться и, если это сложный компонент, проводить проверку корректности).
        splitTab2.setContinuousLayout(true);
        splitTab1.setResizeWeight(0); // установка приоритета для соотношения размеров панелей при изменении размеров = Правая половина (видео + правая панель) в приоритете
        splitTab2.setResizeWeight(1); // установка приоритета для соотношения размеров панелей при изменении размеров = Левая половина (видео) в приоритете

        // Создание панели прокрутки для ЛЕВОЙ панели
        JScrollPane leftPanel = new JScrollPane(leftComponent);
        leftPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши (стандартно включена)      

        // Создание панели прокрутки для ПРАВОЙ панели (видео + еще одна панель)
        JScrollPane rightPanel = new JScrollPane(rightComponent);
        rightPanel.setWheelScrollingEnabled(true); // Активация прокрутки панели колесом мыши

        // Добавление компонент в разделяющуюся панель
        splitTab1.setLeftComponent(null); // левая конфигурационная панель
        splitTab1.setRightComponent(splitTab2);
        splitTab2.setLeftComponent(centerComponent); // добавляем таблицу 
        splitTab2.setRightComponent(null); // правая конфигурационная панель (если не нужна, то удобно сделать её null

        return splitTab1;
    }

    public JSplitPane getSplitTab1() {
        return splitTab1;
    }

    public JSplitPane getSplitTab2() {
        return splitTab2;
    }
    
    
}
