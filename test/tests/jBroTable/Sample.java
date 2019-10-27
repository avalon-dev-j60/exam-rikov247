package tests.jBroTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
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

public class Sample {

    private static String[] typeOfTransport = {"Легковые, фургоны",
        "Грузовые", "Грузовые", "Грузовые", "Грузовые", "Грузовые",
        "Автопоезда", "Автопоезда", "Автопоезда", "Автопоезда", "Автопоезда", "Автопоезда",
        "Автобусы", "Автобусы", "Автобусы", "Автобусы", "Автобусы",
        "Трамвай",
        "ИТОГО"};
    private static String[] kindOfTransport = {"Легковые, фургоны",
        "2-осные", "3-осные", "4-осные", "4-осные (2 оси+прицеп)", "5-осные (3 оси+прицеп)",
        "3 осные (2 оси+полуприцеп)", "4 осные (2 оси+полуприцеп)", "5 осные (2 оси+полуприцеп)", "5 осные (3 оси+полуприцеп)", "6 осные", "7 осные и более",
        "Особо малого класса", "Малого класса", "Среднего класса", "Большого класса", "Особо большого класса",
        "Трамвай",
        "ИТОГО"};

    // Текстовое поле для каждой ячейки таблицы с фильтром по цифрам. Реализуется, когда ячейка в режиме редактирования
    private static JTextField formTextField() {
        JTextField field = new JTextField();
//        field.setBackground(Color.LIGHT_GRAY);
        field.setHorizontalAlignment(JLabel.CENTER);
        // установка фильтра
        PlainDocument document = (PlainDocument) field.getDocument(); // получаем документ текстового поля таблицы
        DigitFilter digitFilter = new DigitFilter(field); // создаем экземпляр Фильтра по Цифрам и передаем текстовое поле для проверки
        document.setDocumentFilter(digitFilter); // устанавливаем фильтр для нашего текстового поля

        return field;
    }

    // Создание таблицы данных
    private static JBroTable table = new JBroTable() {
        // для возможности редактирования ячеек
        @Override
        public boolean isCellEditable(int row, int column) {
            int modelColumn = convertColumnIndexToModel(column); // получаем номер столбца, для которого хотим изменить что-либо
            // Первый солбец - не редактируемый
            if (modelColumn == 0) {
                return false;
            } else { // Остальные столбцы редактируемые
                return true;
            }
        }

        @Override
        public TableCellEditor getCellEditor(int row, int column) {
//            int modelColumn = convertColumnIndexToModel(column); // получаем номер столбца, для которого хотим изменить что-либо
//            if (modelColumn == 0) { // если это первый столбец, то оставляем редактор ячейки
            return super.getCellEditor(row, column);
//            } else { // если это НЕ первый столбец, то подключаем свой редактор ячейки
//                return new DefaultCellEditor(formTextField());
//            }
        }
    };

    public static void cellEditing(JBroTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        // Установка отображения ячейки при ее редактировании (устанавливается фильтр только на цифры
        tableColumn.setCellEditor(new DefaultCellEditor(formTextField()));
    }

    public static void cellRenderer(JBroTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);

        CellRenderer cellRenderer = new CellRenderer(table);
        tableColumn.setCellRenderer(cellRenderer); // Устанавливаем новый рендерер для ячейки
    }

    public static void headerRenderer(JBroTable table, int levelHeader, int column) {
        // Получаем конкретный отдельный элемент хэдера (подстолбец)
        TableColumn tableSuperColumn = table.getTableHeader().getUI().getHeader(levelHeader).getColumnModel().getColumn(column);

        HeaderRenderer headerRenderer = new HeaderRenderer(table);
        tableSuperColumn.setHeaderRenderer(headerRenderer); // устанавливаем Рендерер с выравниванием текста
    }

    private static IModelFieldGroup groups[];

    public static void main(String args[]) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        // Создаем переменную класса, в котором для таблицы создается требуемый хэдер
        CrossRoadModel xModel = new CrossRoadModel();
        groups = xModel.getXModel(); // создаем требуемый хэдер и передаем его

        // Получаем информацию о модели хэдера
        ModelField fields[] = ModelFieldGroup.getBottomFields(groups);

        // Данные в таблице
        ModelRow rows[] = new ModelRow[19];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new ModelRow(fields.length);
            rows[i].setValue(0, typeOfTransport[i]);
            rows[i].setValue(1, kindOfTransport[i]);
            for (int j = 3; j < fields.length; j++) {
                rows[i].setValue(j, 0); //fields[j].getCaption() + i);
            }
        }
        // Объединяем ячейки в строке 0, 17 и 18. Столбец 2. Ставим либо 0, либо 1 - это индикатор того, что нужно объединить эти ячейки.
        // Ставятся 0 или 1, чтобы не объединились две строки, находящиеся рядом
        rows[0].setValue(2, 0);
        rows[17].setValue(2, 0);
        rows[18].setValue(2, 1);

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
                cellRenderer(fixed, i); // устанавливаем выравнивание ячеек по центру
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
        // Выравнивание для всех столбцов и подстолбцов по ЦЕНТРУ
        for (int i = 0; i < header.getLevelsQuantity(); i++) {
            for (int j = 0; j < header.getUI().getHeader(i).getColumnModel().getColumnCount(); j++) {
                headerRenderer(table, i, j);
            }
            if (fixed != null) {
                headerRenderer(fixed, 0, 0); // Для первого столбца выравнивание текста СЛЕВА
            }
        }

        // Установка автораспределения ширины столбцов
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // убираем автоматическое изменение размера
        table.setFillsViewportHeight(true); // высота строк подстраивается под содержимое
        AutoResize autoResize = new AutoResize();
        autoResize.update(table); // Метод установки автораспределения места в таблице

        // Установка Активных Команд для таблицы
        ActionMap actionMap = new ActionMap(table);
        actionMap.setActionMap();

//        header.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                Point p = e.getPoint();
//                int column = header.columnAtPoint(p);
//                int row = header.getRowAtPoint(p);
//                System.out.println(column + ", " + row);
//                System.out.println(header.getColumnModel().getColumnAtAbsolutePosition(column, row));
//                System.out.println(header.getUI().getColumnAtPoint(p));
//            }
//        });
        // Window.
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(table.getScrollPane());
        table.getScrollPane().setPreferredSize(new Dimension(1300, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
