package ru.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import net.miginfocom.swing.MigLayout;
import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.model.ModelData;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.model.ModelFieldGroup;
import org.quinto.swing.table.model.ModelRow;
import org.quinto.swing.table.model.ModelSpan;
import org.quinto.swing.table.view.JBroTable;
import org.quinto.swing.table.view.JBroTableHeader;
import org.quinto.swing.table.view.JBroTableUI;
import org.xml.sax.SAXException;
import ru.jtable.HeaderRenderer;

/**
 * Класс для создания окна Настроек. Настройки берутся из файла. Сама реализация
 * настройки в другом месте. Меняется настройка - записываем изменения в файл
 * настроек. Этот класс уведомляет (fireChanged) кого то, где переменная этого
 * класса создана.
 */
public class SettingsFrame {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this); // переменная позволяющая добавить в этот слушатель изменения свойств 

    private int weightFrame = 400;
    private int heightFrame = 460;
    private JFrame frame;
    private JButton okButton, acceptButton, cancelButton;
    private JCheckBox activePlayPauseCheckBox = new JCheckBox("Play/pause по нажатию");
    private JBroTable hotKeyTable;

    private Settings settings;

    private JTabbedPane tableTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

    private String[] smallDirections = {"Легковой транспорт",
        "Разворот", "Налево", "Прямо", "Направо",
        "Разворот", "Налево", "Прямо", "Направо",
        "Разворот", "Налево", "Прямо", "Направо",
        "Разворот", "Налево", "Прямо", "Направо"};
    private String[] bigDirections = {"Легковой транспорт",
        "Направление 1", "Направление 1", "Направление 1", "Направление 1",
        "Направление 2", "Направление 2", "Направление 2", "Направление 2",
        "Направление 3", "Направление 3", "Направление 3", "Направление 3",
        "Направление 4", "Направление 4", "Направление 4", "Направление 4"};

    public SettingsFrame(Settings settings) {
        this.settings = settings;
        makeNewWindow(); // Создаем окно и НЕ ОТОБРАЖАЕМ его
        firstInitialize(); // Считываем настройки из файла настроек
    }

    private Component createTabbedPane() throws IOException {
        tableTabs.setFocusable(false);

        JPanel panelOfButton = new JPanel(new MigLayout());
        panelOfButton.setBackground(Color.white);

        panelOfButton.add(createButtonsOverlaySettingPanel(), "growx, push, wrap"); // протягиваем по х, выталкиваем лишнее и уведомляем об окончании столбца
        panelOfButton.add(createHotKeyPanel(), "growx, push, wrap");
        panelOfButton.add(createTestPanel("Что то еще"), "growx, push, wrap");
        panelOfButton.add(createOkAcceptCancelPanel(), "newline push, align right");

        // Вкладка 1 (index = 0).
        tableTabs.addTab("Панель кнопок", panelOfButton);
        // Вкладка 2 (index = 1).
        tableTabs.addTab("Панель видео", new JPanel());

        // При изменении вкладки перекидываем фокус куда нужно
        tableTabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tableTabs.getSelectedIndex() == 0) {
                    tableTabs.getComponentAt(0).requestFocus(); // переключаем фокус
                }
            }
        });

        return tableTabs;
    }

    // Панель настройки слоя кнопок
    private JPanel createButtonsOverlaySettingPanel() {
        JPanel panelOfButtons = new JPanel(new MigLayout());
        panelOfButtons.setBackground(Color.white);
        Border buttonsBorder = BorderFactory.createTitledBorder("Настройка кнопок");

        activePlayPauseCheckBox.setBackground(Color.white);
        activePlayPauseCheckBox.setFocusable(false);
        panelOfButtons.add(activePlayPauseCheckBox, "wrap");
//        panelOfButtons.add(activePlayPause, "pushx");
//        panelOfButtons.add(activePlayPauseCheckBox, "wrap");
        panelOfButtons.setBorder(buttonsBorder);

        return panelOfButtons;
    }

    // Панель с кнопками: ОК, Отмена и Применить
    private JPanel createOkAcceptCancelPanel() {
        JPanel okAcceptCancelPanel = new JPanel(new MigLayout());
        okAcceptCancelPanel.setBackground(Color.white);

        okButton = new JButton("ОК"); // Применяем и выходим из настроек
        okButton.setFocusable(false);
        okButton.addActionListener(activePlayPauseCheckBoxListener);
        okButton.addActionListener(closeWindow);
        cancelButton = new JButton("Отменить"); // Выходим из настроек ничего не делая
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(closeWindow);
        acceptButton = new JButton("Применить"); // Применяем изменения и остаемся в настройках
        acceptButton.setFocusable(false);
        acceptButton.addActionListener(activePlayPauseCheckBoxListener);
        okAcceptCancelPanel.add(okButton);
        okAcceptCancelPanel.add(cancelButton);
        okAcceptCancelPanel.add(acceptButton);

        return okAcceptCancelPanel;
    }

    // Какая то еще панель (тестовая)
    private JPanel createTestPanel(String text) {
        JPanel pnl = new JPanel(new MigLayout());
        pnl.setBackground(Color.white);

        JButton a = new JButton("A");
        a.setFocusable(false);
        JButton b = new JButton("B");
        b.setFocusable(false);
        JButton c = new JButton("C");
        c.setFocusable(false);
        pnl.add(a);
        pnl.add(b);
        pnl.add(c);
        pnl.setBorder(BorderFactory.createTitledBorder(text));

        return pnl;
    }

    private JPanel createHotKeyPanel() {
        JPanel pnl = new JPanel(new MigLayout());
        pnl.setBackground(Color.white);

        hotKeyTable = new JBroTable() {
            // Для возможности редактирования ячеек
            @Override
            public boolean isCellEditable(int row, int column) {
                // Условия при которых ячейки будут не редактируемыми:
                if (column == 0 || column == 1 || (column == 2 && row == 0)) {
                    return false; // делаем его не редактируемым     
                } else {
                    return true; // редактируемая ячейка
                }
            }
        };
        // Модель столбцов
        IModelFieldGroup[] groups = new IModelFieldGroup[]{
            new ModelFieldGroup("Действие", "Действие").withManageable(false)
            .withChild(new ModelField("Направление", "").withDefaultWidth(40))
            .withChild(new ModelField("Действие_подзаголовок", "").withDefaultWidth(10)),
            new ModelFieldGroup("Горячие клавиши", "Горячие клавиши").withManageable(false)
            .withChild(new ModelField("Клавиша 1", "Клавиша 1").withDefaultWidth(30))
            .withChild(new ModelField("Клавиша 2", "Клавиша 2").withDefaultWidth(30)),
            new ModelField("Тип_транспорта_ID", "").withVisible(false)
        };

        // Объединение ячеек
        hotKeyTable.setUI(new JBroTableUI()
                .withSpan(new ModelSpan("Направление", "Направление").withColumns("Направление"))
                .withSpan(new ModelSpan("Тип_транспорта_ID", "Направление").withColumns("Направление", "Действие_подзаголовок", "Клавиша 1", "Клавиша 2")));

        ModelField fields[] = ModelFieldGroup.getBottomFields(groups);
        ModelRow rows[] = new ModelRow[17]; // количество строк (не от 0, а от 1)
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new ModelRow(fields.length);

            rows[i].setValue(0, bigDirections[i]);
            rows[i].setValue(1, smallDirections[i]);
        }

        rows[0].setValue(2, smallDirections[0]);
        rows[0].setValue(3, smallDirections[0]);
        rows[0].setValue(4, smallDirections[0]);
        rows[0].setValue(5, 0); // значение в невидимый столбец для идентификации строки, которую стоит объединять по столбцам

        hotKeyTable.setSurrendersFocusOnKeystroke(true); // если фокус на ячейке, то появляется курсор ввода текста в ячейке

        // Таблица
        ModelData data = new ModelData(groups); // Установка header
        data.setRows(rows); // Установка данных в ячейки
        hotKeyTable.setData(data); // Добавление header и ячеек в таблицу

        // ЯЧЕЙКИ. Установка отображения для всех ячеек 
        for (int i = 1; i < hotKeyTable.getColumnModel().getColumnCount(); i++) {
            cellRenderer(hotKeyTable, i); // устанавливаем выравнивание ячеек по центру
        }

        // HEADER. Установка отображения для header (header состоит из уровней): Renderer
        final JBroTableHeader header = hotKeyTable.getTableHeader();
        for (int i = 0; i < header.getLevelsQuantity(); i++) { // перебираем уровни заголовка (строки)
            for (int j = 0; j < header.getUI().getHeader(i).getColumnModel().getColumnCount(); j++) { // перебираем столбцы на этом уровне (строке)
                headerRenderer(hotKeyTable, i, j);
            }
        }

        pnl.add(hotKeyTable.getScrollPane());
        pnl.setBorder(BorderFactory.createTitledBorder("Горячие клавиши"));

        return pnl;
    }

    public void headerRenderer(JBroTable table, int levelHeader, int column) {
        // Получаем конкретный отдельный элемент хэдера (подстолбец)
        TableColumn tableSuperColumn = table.getTableHeader().getUI().getHeader(levelHeader).getColumnModel().getColumn(column);

        HeaderRenderer headerRenderer = new HeaderRenderer(table);
        tableSuperColumn.setHeaderRenderer(headerRenderer); // устанавливаем Рендерер с выравниванием текста
    }

    public void cellRenderer(JBroTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);

        CellRendererHotButtonTable cellRenderer = new CellRendererHotButtonTable(table);
        tableColumn.setCellRenderer(cellRenderer); // Устанавливаем новый рендерер для ячейки
    }

    // Метод создания окна настроек. Окно создается при инициализации класса. Далее управляем только его видимостью
    private void makeNewWindow() {
        // Установка Windows Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame = new JFrame("Настройки");
        frame.setAlwaysOnTop(true); // Окно всегда остается поверх всего (но его можно свернуть)
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(weightFrame, heightFrame));
        // Установка стартового местоположения окна
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // получаем размеры монитора пользователя
        int X = screenSize.width / 4;
        int Y = screenSize.height / 4;
        frame.setLocation(X, Y);
        try {
            frame.add(createTabbedPane());
        } catch (IOException ex) {
            Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Получаем директорию Пользователя (User/nameUser)
    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator;
    }

    // Слушатель состояния CheckBox. Устанавливается значение в документ, документ сохраняется.
    ActionListener activePlayPauseCheckBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oldValue = ""; // старое значение 
            String newValue = ""; // новое значение 
            if (activePlayPauseCheckBox.isSelected()) {
                try {
                    oldValue = settings.getValueNode("ActionPlayPause"); // Считываем значение "ДО"
                    settings.setValueNode("ActionPlayPause", "Yes");
                    settings.writeDocument();
                    newValue = settings.getValueNode("ActionPlayPause"); // Считываем значение "ПОСЛЕ"
                } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
                    Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!activePlayPauseCheckBox.isSelected()) {
                try {
                    oldValue = settings.getValueNode("ActionPlayPause"); // Считываем значение "ДО"
                    settings.setValueNode("ActionPlayPause", "No");
                    settings.writeDocument();
                    newValue = settings.getValueNode("ActionPlayPause"); // Считываем значение "ПОСЛЕ"
                } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
                    Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            pcs.firePropertyChange("ActionPlayPauseChange", oldValue, newValue); // Если значение этой настройки в файле изменилось, то сообщаем об этом куда нужно
            pcs.firePropertyChange("HotKeyChanged", oldValue, newValue);
        }
    };

    // Метод считывания исходных настроек из файла настроек (если его нет, то из дефолтного файла настроек)
    private void firstInitialize() {
        try {
            // CheckBox
            if (settings.getValueNode("ActionPlayPause").equalsIgnoreCase("Yes")) {
                activePlayPauseCheckBox.setSelected(true);
            }
            if (settings.getValueNode("ActionPlayPause").equalsIgnoreCase("No")) {
                activePlayPauseCheckBox.setSelected(false);
            }
            //HotKeyTable
            
            hotKeyTable.setValueAt(KeyEvent.getKeyText(Integer.valueOf(settings.getValueNode("bAroundUpCarListenerKey1"))), 1, 2);
            hotKeyTable.setValueAt(KeyEvent.getKeyText(Integer.valueOf(settings.getValueNode("bAroundUpCarListenerKey2"))), 1, 3);
        } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Закрытие окна настроек
    ActionListener closeWindow = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false); // Скрываем окно
//            frame.dispose(); // уничтожаем все ресурсы связанные с окном и само окно
        }
    };

    public void setFrameVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JCheckBox getActivePlayPauseCheckBox() {
        return activePlayPauseCheckBox;
    }

    // Метод добавляющий слушатель изменения свойств в этот класс
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}
