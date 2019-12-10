package ru.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
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

    private String[] smallDirections = {"Разворот", "Налево", "Прямо", "Направо"};
    private String[] bigDirections = {"Направление 1", "Направление 2", "Направление 3", "Направление 4"};

    private String[] allSmallDirections = {"Легковой транспорт",
        smallDirections[0], smallDirections[1], smallDirections[2], smallDirections[3],
        smallDirections[0], smallDirections[1], smallDirections[2], smallDirections[3],
        smallDirections[0], smallDirections[1], smallDirections[2], smallDirections[3],
        smallDirections[0], smallDirections[1], smallDirections[2], smallDirections[3]};
    private String[] allBigDirections = {"Легковой транспорт",
        bigDirections[0], bigDirections[0], bigDirections[0], bigDirections[0],
        bigDirections[1], bigDirections[1], bigDirections[1], bigDirections[1],
        bigDirections[2], bigDirections[2], bigDirections[2], bigDirections[2],
        bigDirections[3], bigDirections[3], bigDirections[3], bigDirections[3]};

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
        okButton.addActionListener(hoyKeyListener);
        okButton.addActionListener(closeWindow);

        cancelButton = new JButton("Отменить"); // Выходим из настроек ничего не делая
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(closeWindow);

        acceptButton = new JButton("Применить"); // Применяем изменения и остаемся в настройках
        acceptButton.setFocusable(false);
        acceptButton.addActionListener(activePlayPauseCheckBoxListener);
        acceptButton.addActionListener(hoyKeyListener);
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
//                if (column == 0 || column == 1 || (column == 2 && row == 0)) {
                return false; // делаем его не редактируемым     
//                } else {
//                    return true; // редактируемая ячейка
//                }
            }
        };
        hotKeyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); // включаем фиксацию введенных данных и выход из режима редактирования ячейки если фокус переключается на другую вкладку
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

            rows[i].setValue(0, allBigDirections[i]);
            rows[i].setValue(1, allSmallDirections[i]);
        }

        rows[0].setValue(2, allSmallDirections[0]);
        rows[0].setValue(3, allSmallDirections[0]);
        rows[0].setValue(4, allSmallDirections[0]);
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

//        TableColumn tableColumn2 = hotKeyTable.getColumnModel().getColumn(2);
//        TableColumn tableColumn3 = hotKeyTable.getColumnModel().getColumn(3);
        // Установка отображения ячейки при ее редактировании
//        tableColumn2.setCellEditor(new DefaultCellEditor(formTextField())); // в качестве отображателя ячейки в режиме редактирования передаем новый дефолтный с нашим текстовым полем
//        tableColumn3.setCellEditor(new DefaultCellEditor(formTextField())); // в качестве отображателя ячейки в режиме редактирования передаем новый дефолтный с нашим текстовым полем
        pnl.add(hotKeyTable.getScrollPane());
        pnl.setBorder(BorderFactory.createTitledBorder("Горячие клавиши"));

        hotKeyTable.addMouseListener(new MyMouseAdapter());
        return pnl;
    }

    class MyMouseAdapter extends MouseAdapter {

        String key;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                int col = hotKeyTable.columnAtPoint(e.getPoint());
                int row = hotKeyTable.rowAtPoint(e.getPoint());
                if (col == 2 || col == 3 && row != 0) {
                    key = String.valueOf(hotKeyTable.getValueAt(row, col)); // Извлечение горячей кнопки из ячейки

                    hotKeyFrame(col, row, key); // Отображаем окно и добавляем к нему требуемые слушатели. При нажатии на ОК или закрытии окна - окно удаляется полностью вместе со всеми слушателями
                }
            }
        }
    }

    private String hotKeyText(int col, int row, String hotKeyText) {
        String text = "<html><p></p>Горячая "
                + "<u>" + hotKeyTable.getTableHeader().getColumnModel().getColumn(col).getIdentifier() + "</u>"
                + "  для кнопки "
                + "<b>" + hotKeyTable.getValueAt(row, 0) + ", " + hotKeyTable.getValueAt(row, 1) + ": </b>"
                + hotKeyText
                + "</html>";
        return text;
    }

    private String tempHotKeyFrameText = ""; // переменная для хранения нажатой новой горячей кнопки (внутрь метода hotKeyFrame не получается засунуть)

    private JFrame hotKeyFrame(int col, int row, String key) {

        JFrame frame = new JFrame("Горячие клавиши"); //Создания объекта на класс - свое диалоговое окно.
        frame.setLayout(new MigLayout());
        frame.setLocationRelativeTo(null); // Расположение окна на экране

        JLabel label = new JLabel(hotKeyText(col, row, key));
        label.setFocusable(false);
        frame.add(label, "align center, push, wrap");

        JButton license = new JButton("ОК");
        license.setFocusable(false);
        frame.add(license, "newline push, align center");
        license.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hotKeyTable.setValueAt(tempHotKeyFrameText, row, col);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        frame.setSize(400, 122); // размер окна
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        // Слушатель нажатия на кнопку клавиатуры - меняем текст в окне
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                tempHotKeyFrameText = KeyEvent.getKeyText(code);
                label.setText(hotKeyText(col, row, tempHotKeyFrameText));
            }
        });
        // Если окно закрылось, то уничтожаем его и все что с ним связано
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.dispose();
            }
        });
        return frame;
    }

    private JTextField formTextField() {
//        field.setBackground(Color.LIGHT_GRAY);
        JTextField field = new JTextField(); // Текстовое поле внутри ячейки
        field.setHorizontalAlignment(JLabel.CENTER);

        return field;
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
    private ActionListener activePlayPauseCheckBoxListener = new ActionListener() {
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

    private ActionListener hoyKeyListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oldValue = "old"; // старое значение 
            String newValue = "new"; // новое значение 
            try {
                hotKeyInSettingsFile("Направление 1");
                hotKeyInSettingsFile("Направление 2");
                hotKeyInSettingsFile("Направление 3");
                hotKeyInSettingsFile("Направление 4");
                settings.writeDocument(); // записываем все изменения в файл настроек

            } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
                Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            hotKeyInitialize(bigDirections[0]); // Направление 1
            hotKeyInitialize(bigDirections[1]);
            hotKeyInitialize(bigDirections[2]);
            hotKeyInitialize(bigDirections[3]);
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

    // МЕТОДЫ ДЛЯ ТАБЛИЦЫ ГОРЯЧИХ КЛАВИШ
    private int getRowHotKey(JBroTable table, String IdRow1, String IdRow2) {
        // Указываем таблицу, текст в ячейке, который является ID этой ячейки и указываем номер столбца
        int rowTemp = - 1;
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
            String rowValueColumn1 = (String.valueOf(table.getModel().getData().getRows()[i].getValue(0)));
            String rowValueColumn2 = (String.valueOf(table.getModel().getData().getRows()[i].getValue(1)));
            if (rowValueColumn1.equalsIgnoreCase(IdRow1) && rowValueColumn2.equalsIgnoreCase(IdRow2)) { // находим строку,
                rowTemp = i; // сохраняем номер этой строки
            }
        }
        return rowTemp;
    }

    private int getRow(JBroTable table, String IdRow1, int column1, String IdRow2, int column2) {
        // Указываем таблицу, текст в ячейке, который является ID этой ячейки и указываем номер столбца
        int rowTemp = - 1;
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
            String rowValueColumn1 = (String.valueOf(table.getModel().getData().getRows()[i].getValue(column1)));
            String rowValueColumn2 = (String.valueOf(table.getModel().getData().getRows()[i].getValue(column2)));
            if (rowValueColumn1.equalsIgnoreCase(IdRow1) && rowValueColumn2.equalsIgnoreCase(IdRow2)) { // находим строку,
                rowTemp = i; // сохраняем номер этой строки
            }
        }
        return rowTemp;
    }

    private int getColumn(JBroTable table, String idColumn) {
        // Определяем столбец
        int columnTemp = -1;
        for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) {
            if (table.getModel().getData().getFields()[i].getIdentifier().equalsIgnoreCase(idColumn)) {
                columnTemp = i;
            }
        }
        return columnTemp;
    }

    // Метод для преобразования числа (int кода клавиши) из файла в текст!
    private String keyText(String id) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
        String valueTable;
        try {
            valueTable = KeyEvent.getKeyText(Integer.valueOf(settings.getValueNode(id)));
        } catch (NumberFormatException e) {
            valueTable = "";
        }
        return valueTable;
    }

    // Запись значений из файла настроек в таблицу по переданному направлению
    private void hotKeyInitialize(String bigDirection) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
        String idDirection = getIdDirection(bigDirection);
        // Разворот
        hotKeyTable.setValueAt(
                keyText("bAround" + idDirection + "CarListenerKey1"),
                getRowHotKey(hotKeyTable, bigDirection, smallDirections[0]),
                getColumn(hotKeyTable, "Клавиша 1")
        );
        hotKeyTable.setValueAt(keyText("bAround" + idDirection + "CarListenerKey2"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[0]), getColumn(hotKeyTable, "Клавиша 2"));
        // Налево
        hotKeyTable.setValueAt(keyText("bLeft" + idDirection + "CarListenerKey1"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[1]), getColumn(hotKeyTable, "Клавиша 1"));
        hotKeyTable.setValueAt(keyText("bLeft" + idDirection + "CarListenerKey2"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[1]), getColumn(hotKeyTable, "Клавиша 2"));
        // Прямо
        hotKeyTable.setValueAt(keyText("bForward" + idDirection + "CarListenerKey1"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[2]), getColumn(hotKeyTable, "Клавиша 1"));
        hotKeyTable.setValueAt(keyText("bForward" + idDirection + "CarListenerKey2"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[2]), getColumn(hotKeyTable, "Клавиша 2"));
        // Направо
        hotKeyTable.setValueAt(keyText("bRight" + idDirection + "CarListenerKey1"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[3]), getColumn(hotKeyTable, "Клавиша 1"));
        hotKeyTable.setValueAt(keyText("bRight" + idDirection + "CarListenerKey2"), getRowHotKey(hotKeyTable, bigDirection, smallDirections[3]), getColumn(hotKeyTable, "Клавиша 2"));
    }

    // Перенос значений из таблицы Горячих клавиш в файл настроек по переданному направлению.
    // Требуется еще отдельно записать изменения в ФАЙЛ!
    private void hotKeyInSettingsFile(String bigDirection) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
        String idDirection = getIdDirection(bigDirection);
        // Разворот
        // Указывается node в файле и значение, которое следует записать
        settings.setValueNode("bAround" + idDirection + "CarListenerKey1",
                String.valueOf(hotKeyTable.getValueAt(
                        getRowHotKey(hotKeyTable, bigDirection, smallDirections[0]),
                        getColumn(hotKeyTable, "Клавиша 1")))
        );

        settings.setValueNode("bAround" + idDirection + "CarListenerKey2", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[0]), getColumn(hotKeyTable, "Клавиша 2"))));
        // Налево
        settings.setValueNode("bLeft" + idDirection + "CarListenerKey1", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[1]), getColumn(hotKeyTable, "Клавиша 1"))));
        settings.setValueNode("bLeft" + idDirection + "CarListenerKey2", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[1]), getColumn(hotKeyTable, "Клавиша 2"))));
        // Прямо
        settings.setValueNode("bForward" + idDirection + "CarListenerKey1", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[2]), getColumn(hotKeyTable, "Клавиша 1"))));
        settings.setValueNode("bForward" + idDirection + "CarListenerKey2", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[2]), getColumn(hotKeyTable, "Клавиша 2"))));
        // Направо
        settings.setValueNode("bRight" + idDirection + "CarListenerKey1", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[3]), getColumn(hotKeyTable, "Клавиша 1"))));
        settings.setValueNode("bRight" + idDirection + "CarListenerKey2", String.valueOf(hotKeyTable.getValueAt(getRowHotKey(hotKeyTable, bigDirection, smallDirections[3]), getColumn(hotKeyTable, "Клавиша 2"))));
    }

    // Преобразование Направления в ID
    private String getIdDirection(String bigDirection) {
        String idDirection = "";
        if (bigDirection.equalsIgnoreCase("Направление 1")) {
            idDirection = "Up";
        }
        if (bigDirection.equalsIgnoreCase("Направление 2")) {
            idDirection = "Right";
        }
        if (bigDirection.equalsIgnoreCase("Направление 3")) {
            idDirection = "Down";
        }
        if (bigDirection.equalsIgnoreCase("Направление 4")) {
            idDirection = "Left";
        }
        return idDirection;
    }

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
