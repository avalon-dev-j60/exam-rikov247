package ru.jtable;

import java.awt.BorderLayout;
import ru.jtable.model.CrossRoadModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.PlainDocument;
import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.model.ModelData;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.model.ModelFieldGroup;
import org.quinto.swing.table.model.ModelRow;
import org.quinto.swing.table.model.ModelSpan;
import org.quinto.swing.table.view.JBroTable;
import org.quinto.swing.table.view.JBroTableHeader;
import org.quinto.swing.table.view.JBroTableUI;
import ru.jtable.model.TRightRoadModel;

public class Table implements TableModelListener {

    private String[] typeOfTransport;
    private String[] kindOfTransport;

    private String[] typeOfTransportNow = {"Троллейбусы",
        "Автобусы", "Автобусы", "Автобусы",
        "Легковой транспорт",
        "Грузовые", "Грузовые", "Грузовые", "Грузовые", "Грузовые",
        "Трамвай",
        "ИТОГО"};
    private String[] kindOfTransportNow = {"Троллейбусы",
        "Большой автобус", "Средний автобус", "Микроавтобус",
        "Легковой транспорт",
        "До 2-х тонн", "От 2 до 6 тонн", "От 6 до 12 тонн", "От 12 до 20 тонн", "Более 20 тонн",
        "Трамвай",
        "ИТОГО"};
    private String[] typeOfTransportFuture = {"Легковые, фургоны",
        "Грузовые", "Грузовые", "Грузовые", "Грузовые", "Грузовые",
        "Автопоезда", "Автопоезда", "Автопоезда", "Автопоезда", "Автопоезда", "Автопоезда",
        "Автобусы", "Автобусы", "Автобусы", "Автобусы", "Автобусы",
        "Трамвай",
        "ИТОГО"};
    private String[] kindOfTransportFuture = {"Легковые, фургоны",
        "2-осные", "3-осные", "4-осные", "4-осные (2 оси+прицеп)", "5-осные (3 оси+прицеп)",
        "3 осные (2 оси+полуприцеп)", "4 осные (2 оси+полуприцеп)", "5 осные (2 оси+полуприцеп)", "5 осные (3 оси+полуприцеп)", "6 осные", "7 осные и более",
        "Особо малого класса", "Малого класса", "Среднего класса", "Большого класса", "Особо большого класса",
        "Трамвай",
        "ИТОГО"};

    private IModelFieldGroup groups[]; // Основная модель группированных field (полей / столбцов)

    // Текстовое поле для каждой ячейки таблицы с фильтром по цифрам. Реализуется, когда ячейка в режиме редактирования
    private JTextField formTextField() {
//        field.setBackground(Color.LIGHT_GRAY);
        JTextField field = new JTextField(); // Текстовое поле внутри ячейки
        field.setHorizontalAlignment(JLabel.CENTER);

        // установка фильтра
        PlainDocument document = (PlainDocument) field.getDocument(); // получаем документ текстового поля таблицы
        DigitFilter digitFilter = new DigitFilter(field); // создаем экземпляр Фильтра по Цифрам и передаем текстовое поле для проверки
        document.setDocumentFilter(digitFilter); // устанавливаем фильтр для нашего текстового поля

        return field;
    }

    // Создание таблицы данных
    private JBroTable table = new JBroTable() {
        // Для возможности редактирования ячеек
        @Override
        public boolean isCellEditable(int row, int column) {
            // Условия при которых ячейки будут не редактируемыми:
            int modelColumn = convertColumnIndexToModel(column); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера
            ModelField columnToModelField = table.getData().getFields()[modelColumn];
            // Все fields (это столбцы конечные, которые уже не расщепляются) и groupFields (их как parent - родителей fields) перебираем
            //  (чтобы перебирать передаем column переведенный в модель хэдера). Берем идентификатор и оцениваем:
            // - если field начинается с "ПЕ"
            // - если groupField начинается с "Итого"
            // - если есть строка "Итого"
            int temp = 0;
            for (int i = 0; i < getData().getRows().length; i++) {
                String rowValue = ((String) getData().getRows()[i].getValue(0)); // значение в 1 столбце в проверяемой ячейке
                if (rowValue.equalsIgnoreCase("Итого")) { // если в этой ячейке записано "Итого", то
                    temp = i; // сохраняем номер этой строки
                }
            }
            if (columnToModelField.getIdentifier().startsWith("ПЕ")
                    || columnToModelField.getParent().getIdentifier().startsWith("Итого")
                    || row == temp) {
                return false; // делаем его не редактируемым     
            } else {
                return true; // редактируемая ячейка
            }
        }
    };

    public JBroTable getTable() {
        return table;
    }

    public void cellEditing(JBroTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        // Установка отображения ячейки при ее редактировании (устанавливается фильтр только на цифры
        tableColumn.setCellEditor(new DefaultCellEditor(formTextField())); // в качестве отображателя ячейки в режиме редактирования передаем новый дефолтный с нашим текстовым полем
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); // включаем фиксацию введенных данных и выход из режима редактирования ячейки если фокус переключается на другую вкладку
    }

    public void cellRenderer(JBroTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);

        CellRenderer cellRenderer = new CellRenderer(table);
        tableColumn.setCellRenderer(cellRenderer); // Устанавливаем новый рендерер для ячейки
    }

    public void headerRenderer(JBroTable table, int levelHeader, int column) {
        // Получаем конкретный отдельный элемент хэдера (подстолбец)
        TableColumn tableSuperColumn = table.getTableHeader().getUI().getHeader(levelHeader).getColumnModel().getColumn(column);

        HeaderRenderer headerRenderer = new HeaderRenderer(table);
        tableSuperColumn.setHeaderRenderer(headerRenderer); // устанавливаем Рендерер с выравниванием текста
    }

    // Метод прекращает редактирование ячейки и фиксирует введенное значение. Нужно просто определить, когда его вызывать
    public void stopEditing() {
        if (table.isEditing() || table.getTableHeader().hasFocus()) {
            // Получаем редактируемую ячейку
            int col = table.getEditingColumn();
            int row = table.getEditingRow();
            CellEditor cellEditor = table.getCellEditor(); // получае редактор ячейки
            if (cellEditor != null) { // если он есть, то (это просто защита от NullPointerException)
                cellEditor.stopCellEditing(); // прекращаем редактирование ячейки (это стандартный метод для каждого CellEditor)
            }
            table.changeSelection(row, col, false, false); // меняем значение ячейки на введенное в процессе ее редактирования
        }
    }

    // Если кликать на тот компонент, к которому применен этот адаптер - заканчиваем редактирование ячейки и фиксируем значение
    private MouseAdapter stopEditingCell = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            stopEditing();
        }
    };

    public JBroTable doTable() throws Exception {
        // Создаем переменную класса, в котором для таблицы создается требуемый хэдер
        CrossRoadModel xModel = new CrossRoadModel();
        TRightRoadModel tRightModel = new TRightRoadModel();
        groups = xModel.getModel(); // создаем требуемый хэдер и передаем его

        // Получаем информацию о модели хэдера
        ModelField fields[] = ModelFieldGroup.getBottomFields(groups);

        // Данные в таблице
        // Временно
        typeOfTransport = typeOfTransportFuture;
        kindOfTransport = kindOfTransportFuture;

        ModelRow rows[] = new ModelRow[typeOfTransport.length]; // количество строк (не от 0, а от 1)
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new ModelRow(fields.length);  // создаем модель внутри строки на количество ячеек такое, которое указано в моделе нижнего уровня столбцов
            rows[i].setValue(0, typeOfTransport[i]); // заполняем первый (0) столбец в каждой строке (то есть первую ячейку в каждой строке) 
            rows[i].setValue(1, kindOfTransport[i]); // заполняем второй (1) столбец в каждой строке (то есть вторую ячейку в каждой строке)
            // Третий столбец на самом деле есть, но он скрыт - он нужен для правильного объединения ячеек
            // Заполняем третий (четвертый) и далее столбцы в каждой строке (то есть все ячейки начиная с третьей в каждой строке)
            for (int j = 3; j < fields.length; j++) {
                rows[i].setValue(j, 0); // заполняем нулями
            }
        }
        // Объединяем ячейки в строке 0, 17 и 18. Столбец 2. Ставим либо 0, либо 1 - это индикатор того, что нужно объединить эти ячейки.
        // Ставятся 0 или 1, чтобы не объединились две строки, находящиеся рядом
        // Блок для объединения ячеек, если в соседних ячейках один и тотже текст
        // Переменная temp для того, чтобы не объединились две строки, находящиеся рядом
        int temp = 0;
        for (int i = 0; i < typeOfTransport.length; i++) { // Перебираем массив типов транспорта
            if (typeOfTransport[i].equalsIgnoreCase(kindOfTransport[i])) { // сравниваем между собой тип и вид транспорта. Если они равны, то:
                rows[i].setValue(2, temp); // устанавливаем значение в третий (индекс 2) столбец 0 или 1 для данной строки
                temp = (temp == 0) ? 1 : 0; // тернарный оператор. Если temp = 0, то он становится равен 1, если нет, то 0 (для реализации смены 0 и 1)
            }
        }

        // Таблица
        ModelData data = new ModelData(groups); // Установка header
        data.setRows(rows); // Установка данных в ячейки
        table.setData(data); // Добавление header и ячеек в таблицу

        // Из-за того, что первый столбец фиксирован при пролистывании таблицы, изменения в него приходится вносить таким способом
        JBroTable fixed = table.getSlaveTable();
        if (fixed != null) {
            // Задаем UI для правильного объединения отдельных ячеек
            fixed.setUI(new JBroTableUI()
                    .withSpan(new ModelSpan("Тип транспорта", "Тип транспорта").withColumns("Тип транспорта"))
                    // объединяются ячейки в столбцах "Тип транспорта" и "Вид транспорта". "Категория транспорта" - это ID по которому происходит определение того, что объединять
                    .withSpan(new ModelSpan("Категория транспорта", "Тип транспорта").withColumns("Тип транспорта", "Вид транспорта"))
            );
            // Делаем что то с ячейками в фиксированных столбцах
            for (int i = 0; i < fixed.getColumnModel().getColumnCount(); i++) {
                cellRenderer(fixed, i);
            }
        }

        table.getColumnModel().setColumnSelectionAllowed(true); // включаем возможность выделения не целых строк, а областей ячеек
        table.setSurrendersFocusOnKeystroke(true); // если фокус на ячейке, то появляется курсор ввода текста в ячейке

        // ЯЧЕЙКИ. Установка отображения для всех ячеек 
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            cellRenderer(table, i); // устанавливаем выравнивание ячеек по центру
            cellEditing(table, i); // Установка отображения ячейки при ее редактировании (устанавливается фильтр только на цифры)
        }

        // HEADER. Установка отображения для header (header состоит из уровней): Renderer
        final JBroTableHeader header = table.getTableHeader();
        for (int i = 0; i < header.getLevelsQuantity(); i++) { // перебираем уровни заголовка (строки)
            for (int j = 0; j < header.getUI().getHeader(i).getColumnModel().getColumnCount(); j++) { // перебираем столбцы на этом уровне (строке)
                headerRenderer(table, i, j);
            }
            if (fixed != null) { // для фиксированной части таблицы
                headerRenderer(fixed, 0, 0);
            }
        }

        // Установка автораспределения ширины столбцов
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // убираем автоматическое изменение размера
        table.setFillsViewportHeight(true); // высота строк подстраивается под содержимое
        AutoResize autoResize = new AutoResize();
        autoResize.update(table); // Метод установки автораспределения места в таблице

        // Установка Активных Команд для таблицы (копировать, вставить и т.п.)
        ActionMap actionMap = new ActionMap(table);
        actionMap.setActionMap();

        // Добавляем слушателей для отмены редактирования ячейки и фиксации значения в ячейки при клике по заголовку (хэдеру) или фиксированным столбцам (ячейкам в них)
        table.getTableHeader().addMouseListener(stopEditingCell);
        if (table.getSlaveTable() != null) {
            table.getSlaveTable().addMouseListener(stopEditingCell);
            table.getSlaveTable().getTableHeader().addMouseListener(stopEditingCell);
        }

        table.getData().getRows()[0].setValue(5, 13);
        // Добавляем слушателей подсчета значений в нужных нередактируемых ячейках (имитируем формулы excel)
//        for (int i = 1; i < 4; i = i++) {
//            int modelColumn = table.convertColumnIndexToModel(i); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера
//            ModelField columnToModelField = table.getData().getFields()[modelColumn];
//
//            if (columnToModelField.getIdentifier().startsWith("ПЕ")) {
//                table.getData().getRows()[0].setValue(i, 13);
////                System.out.println(table.getData().getRows()[0].getValue(5));
//            }
//        }

        // Слушатель модели Таблицы - отслеживает изменение данных в ней
        table.getModel().addTableModelListener(this);

        return table;
    }

    public void setGroups(IModelFieldGroup[] groups) {
        this.groups = groups;
    }

    public IModelFieldGroup[] getGroups() {
        return groups;
    }

    int value1 = 0;
    int value2 = 0;
    int value3 = 0;
    int value4 = 0;
    int sum = value1 + value2 + value3 + value4;

    // Слушатель изменения данных в таблице
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow(); // получаем 
        // Разбираемся со столбцами
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
        int column = e.getColumn();
        int modelColumn = table.convertColumnIndexToModel(column - unAccountedColumns); // переводим стандартный индекс столбца в индекс этого столбца в моделе хэдера

        TableModel model = (TableModel) e.getSource();
        Object dat = model.getValueAt(row, column);
        if (String.valueOf(dat).isBlank()) {
            dat = "0";
            model.setValueAt(dat, row, column);
        }

        ModelField columnToModelField = table.getData().getFields()[modelColumn];
        // Все fields (это столбцы конечные, которые уже не расщепляются) и groupFields (их как parent - родителей fields) перебираем
        //  (чтобы перебирать передаем column переведенный в модель хэдера). Берем идентификатор и оцениваем:
        // - если field начинается с "ПЕ"
        // - если groupField начинается с "Итого"
        // - если есть строка "Итого"

        // Определяем строку
        int rowTemp = - 1;
        for (int i = 0; i < table.getData().getRows().length; i++) {
            String rowValue = ((String) table.getData().getRows()[i].getValue(0)); // значение в 1 столбце в проверяемой ячейке
            if (rowValue.startsWith("Легковые")) { // если в этой ячейке записано "Итого", то
                rowTemp = i; // сохраняем номер этой строки
            }
        }

        // Определяем столбец Итогов 
        int columnTemp = - 1;
        for (int i = 0; i < table.getData().getFieldsCount(); i++) {
            if (table.getData().getFields()[i].getIdentifier().startsWith("ФЕ Итого1")) {
                columnTemp = i;
            }
        }

        if (columnToModelField.getIdentifier().startsWith("ФЕ Налево 12") && row == rowTemp && columnTemp > 0) {
            Object data = model.getValueAt(row, column);
            value1 = Integer.valueOf((String) data);
            sum = value1 + value2 + value3 + value4;
            model.setValueAt(sum, row, columnTemp);
        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Прямо 1") && row == rowTemp && columnTemp > 0) {
            Object data = model.getValueAt(row, column);
            value2 = Integer.valueOf((String) data);
            sum = value1 + value2 + value3 + value4;
            model.setValueAt(sum, row, columnTemp);
        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Направо 14") && row == rowTemp && columnTemp > 0) {
            Object data = model.getValueAt(row, column);
            value3 = Integer.valueOf((String) data);
            sum = value1 + value2 + value3 + value4;
            model.setValueAt(sum, row, columnTemp);
        }
        if (columnToModelField.getIdentifier().startsWith("ФЕ Разворот 11") && row == rowTemp && columnTemp > 0) {
            Object data = model.getValueAt(row, column);
            value4 = Integer.valueOf((String) data);
            sum = value1 + value2 + value3 + value4;
            model.setValueAt(sum, row, columnTemp);
        }

    }

}
