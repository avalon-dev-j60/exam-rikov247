package ru.jtable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.TableColumn;
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
import ru.cartogram.CreateCartogram;

public class Table {

    private ModelListener modelListener;

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
        "Троллейбусы",
        "Трамвай",
        "ИТОГО"};
    private String[] kindOfTransportFuture = {"Легковые, фургоны",
        "2-осные", "3-осные", "4-осные", "4-осные (2 оси+прицеп)", "5-осные (3 оси+прицеп)",
        "3 осные (2 оси+полуприцеп)", "4 осные (2 оси+полуприцеп)", "5 осные (2 оси+полуприцеп)", "5 осные (3 оси+полуприцеп)", "6 осные", "7 осные и более",
        "Особо малого класса", "Малого класса", "Среднего класса", "Большого класса", "Особо большого класса",
        "Троллейбусы",
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

    public void cellRenderer(JBroTable table, int column, String typeOfStatement) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);

        CellRenderer cellRenderer = new CellRenderer(table, typeOfStatement);
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

    public JBroTable doTable(IModelFieldGroup[] modelGroup, String typeOfStatement, CreateCartogram cartogram, JBroTable totalTable, JBroTable[] arrayTable) throws Exception {
        // Создаем переменную класса, в котором для таблицы создается требуемый хэдер
        groups = modelGroup; // создаем требуемый хэдер и передаем его

        // Получаем информацию о модели хэдера
        ModelField fields[] = ModelFieldGroup.getBottomFields(groups);

        // Данные в таблице
        // Временно
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            typeOfTransport = typeOfTransportNow;
            kindOfTransport = kindOfTransportNow;
        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            typeOfTransport = typeOfTransportFuture;
            kindOfTransport = kindOfTransportFuture;
        }

        // Подключение слушателя изменений в таблице 
        // Если картограмма передана (значит инициализируем итоговую таблицу) то ее передаем дальше
        if (cartogram != null) {
            table.getModel().removeTableModelListener(modelListener);  // удаляем старый слушатель (так как используем одну и туже таблицу
            modelListener = new ModelListener(table, kindOfTransport, typeOfStatement, cartogram);
        } else {
            // Если картограмма не преедана (значит это 15 минутные таблицы - не итоговая), тогда Если передали Итоговую таблицу и массив таблиц по 15 минут, то
            if (arrayTable != null && totalTable != null) {
                // Передаем их дальше, чтобы эти таблицы передали информацию в Итоговую таблицу
                modelListener = new ModelListener(table, kindOfTransport, typeOfStatement, totalTable, arrayTable);
            } else {
                // Если не передали, то это просто стандартная таблица (ни на картограмму, ни в другие таблицы ничего не передает)
                modelListener = new ModelListener(table, kindOfTransport, typeOfStatement);
            }
        }

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
                cellRenderer(fixed, i, typeOfStatement);
            }
        }

        table.getColumnModel().setColumnSelectionAllowed(true); // включаем возможность выделения не целых строк, а областей ячеек
        table.setSurrendersFocusOnKeystroke(true); // если фокус на ячейке, то появляется курсор ввода текста в ячейке

        // ЯЧЕЙКИ. Установка отображения для всех ячеек 
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            cellRenderer(table, i, typeOfStatement); // устанавливаем выравнивание ячеек по центру
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

        // Добавляем слушателей подсчета значений в нужных нередактируемых ячейках (имитируем формулы excel)
        // Слушатель модели Таблицы - отслеживает изменение данных в ней
        table.getModel().addTableModelListener(modelListener);

        return table;
    }

    // Если картограмму не указывать, то с создаваемой таблицей не будет ассоциироваться никакой картограммы
    public JBroTable doTable(IModelFieldGroup[] modelGroup, String kindOfStatement) throws Exception {
        return doTable(modelGroup, kindOfStatement, null, null, null);
    }

    public JBroTable doTable(IModelFieldGroup[] modelGroup, String kindOfStatement, CreateCartogram cartogram) throws Exception {
        return doTable(modelGroup, kindOfStatement, cartogram, null, null);
    }

    public JBroTable doTable(IModelFieldGroup[] modelGroup, String kindOfStatement, JBroTable totalTable, JBroTable[] arrayTable) throws Exception {
        return doTable(modelGroup, kindOfStatement, null, totalTable, arrayTable);
    }

    public void setGroups(IModelFieldGroup[] groups) {
        this.groups = groups;
    }

    public IModelFieldGroup[] getGroups() {
        return groups;
    }

    public void removeAndSetModelListener(String typeOfStatement, CreateCartogram cartogram) {
        table.getModel().removeTableModelListener(modelListener);
        modelListener = new ModelListener(table, kindOfTransport, typeOfStatement, cartogram);
        table.getModel().addTableModelListener(modelListener);
    }

    public void removeModelListener() {
        table.getModel().removeTableModelListener(modelListener);
    }

    public void setModelListener(String typeOfStatement, CreateCartogram cartogram) {
        modelListener = new ModelListener(table, kindOfTransport, typeOfStatement, cartogram);
        table.getModel().addTableModelListener(modelListener);
    }
    
    public void setModelListener(String typeOfStatement) {
        modelListener = new ModelListener(table, kindOfTransport, typeOfStatement);
        table.getModel().addTableModelListener(modelListener);
    }
}
