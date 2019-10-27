package ru.cartogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Класс для создания Панели (в виде таблицы) Настройки картограммы.<n>
 * На вкладке картограммы.
 */
public class CreateConfigurationPanelMap {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this); // переменная позволяющая добавить в этот слушатель изменения свойств 

//    private FileSaveWithPattern fileSaveWithPattern;
//    private String kindOfStatement;
//    private String fullFileName;
//    private String fullName;
//    private String typeOfDirection; // Количество направлений
//    private String typeOfStatement; // Вид ведомости (старая, новая)
//    private JBroTable table = new JBroTable(); // Таблица
//    private JComboBox comboBox1;
//    private JComboBox comboBox3;
//    private JCheckBoxTree checkBox;
//    private TreePath[] paths;
//    private JScrollPane scrollPane;
    private CreateCartogram cartogram;

    private JTextField direction1 = new JTextField();
    private JTextField direction2 = new JTextField();
    private JTextField direction3 = new JTextField();
    private JTextField direction4 = new JTextField();
    private JLabel direction1label = new JLabel();
    private JLabel direction2label = new JLabel();
    private JLabel direction3label = new JLabel();
    private JLabel direction4label = new JLabel();

    private JTextField streetHoriz = new JTextField();
    private JTextField streetVertic = new JTextField();
    private JLabel streetHorizlabel = new JLabel();
    private JLabel streetVerticlabel = new JLabel();

    private JSplitPane vertSplit1; // (вертикальная панель с разделением по горизонтали) - вертикальная потому что она вытянута по вертикали
    private JSplitPane vertSplit2;
    private JSplitPane vertSplit3;
    private JSplitPane vertSplit4;
    private JSplitPane horizSplit1; // (горизонтальная панель с разделением по вертикали) - горизонтальная потому что она вытянута по горизонтали
    private JSplitPane horizSplit2;
    private JSplitPane horizSplit3;
    private JSplitPane horizSplit4;

    public CreateConfigurationPanelMap(CreateCartogram cartogram) {
        this.cartogram = cartogram;
    }

    public JComponent CreateConfigurationPanel() {
        vertSplit1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true); // устанавливаем в положение true continuousLayout - для постоянного отображения divider (разделительной линии) при ее перемещении
        vertSplit1.setDividerSize(3);

        horizSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit1.setDividerSize(3);
        horizSplit1.setDividerLocation(80); // установка положения разделительной линии первоначальное

//        horizSplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
//        horizSplit2.setDividerSize(3);
//
//        vertSplit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
//        vertSplit2.setDividerSize(3);
//
//        vertSplit3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
//        vertSplit3.setDividerSize(3);
//
//        vertSplit4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
//        vertSplit4.setDividerSize(3);
//
//        horizSplit3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
//        horizSplit3.setDividerSize(3);
//
//        horizSplit4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
//        horizSplit4.setDividerSize(3);
        // Конфигурируем Лэйблы
        createLabel(direction1label, "Направление 1: ");
        createLabel(direction2label, "Направление 2: ");
        createLabel(direction3label, "Направление 3: ");
        createLabel(direction4label, "Направление 4: ");
        createLabel(streetHorizlabel, "Улица горизонтальная: ");
        createLabel(streetVerticlabel, "Улица вертикальная: ");

        // Конфигурируем Текстовые поля
        createTextField(direction1, direction1label, "StreetName_Up");
        createTextField(direction2, direction2label, "StreetName_Right");
        createTextField(direction3, direction3label, "StreetName_Down");
        createTextField(direction4, direction4label, "StreetName_Left");
        createTextField(streetHoriz, streetHorizlabel, "StreetName_Horizontal1", "StreetName_Horizontal2");
        createTextField(streetVertic, streetVerticlabel, "StreetName_Vertical1", "StreetName_Vertical2");

        // Создаем горизонтальную панель (с двумя половинами, разделенными вертикальной линией)
        createHorizPanel(horizSplit1,
                createLabelPanel(direction1label, direction2label, direction3label, direction4label, streetHorizlabel, streetVerticlabel),
                createTextFieldPanel(direction1, direction2, direction3, direction4, streetHoriz, streetVertic));

        vertSplit1.setTopComponent(horizSplit1);
//        vertSplit1.setBottomComponent(vertSplit2);
//        vertSplit2.setTopComponent(horizSplit2);
//        vertSplit2.setBottomComponent(vertSplit3);
//        vertSplit3.setTopComponent(horizSplit3);
//        vertSplit3.setBottomComponent(vertSplit4);
//        vertSplit4.setTopComponent(horizSplit4);
        vertSplit1.setBottomComponent(acceptedParametersPanel());

        // Отслеживание положения divider разделенной панели для синхронизации вертикальных divider (в горизонтальных панелях) всех уровней
        dividerTracking();

        // Убрать границу (тень) вокруг divider
        // UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder()); // Убрать границу (тень) вокруг divider для всех SplitPane в приложении
        vertSplit1.setBorder(BorderFactory.createEmptyBorder());
//        vertSplit2.setBorder(BorderFactory.createEmptyBorder());
//        vertSplit3.setBorder(BorderFactory.createEmptyBorder());
//        vertSplit4.setBorder(BorderFactory.createEmptyBorder());
        horizSplit1.setBorder(BorderFactory.createEmptyBorder());
//        horizSplit2.setBorder(BorderFactory.createEmptyBorder());
//        horizSplit3.setBorder(BorderFactory.createEmptyBorder());
//        horizSplit4.setBorder(BorderFactory.createEmptyBorder());

        return vertSplit1;
    }

    // Слушатель изменения текста в SVG документе при нажатии на Enter
    private void addListenerToTextField(JTextField textField, String ID) {
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cartogram.changeValueWithoutSave(ID, textField.getText());
                cartogram.saveChangeValue();
            }
        });
    }

    // Конфигурируем группу текстовых полей
    private JPanel createTextFieldPanel(JTextField textField1, JTextField textField2, JTextField textField3,
            JTextField textField4, JTextField textField5, JTextField textField6) {
        JPanel p = new JPanel(new GridLayout(0, 1, 0, 4));
        p.setBackground(Color.WHITE);
        p.add(textField1);
        p.add(textField2);
        p.add(textField3);
        p.add(textField4);
        p.add(textField5);
        p.add(textField6);
        // Пока что не нужные слушатели. Для реагирования на нажатие на Enter 
        addListenerToTextField(textField1, "StreetName_Up");
        addListenerToTextField(textField2, "StreetName_Right");
        addListenerToTextField(textField3, "StreetName_Down");
        addListenerToTextField(textField4, "StreetName_Left");

        return p;
    }

    // Конфигурируем группу текстовых полей
    private JScrollPane createLabelPanel(JComponent c1, JComponent c2, JComponent c3, JComponent c4, JComponent c5, JComponent c6) {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };

        JPanel p = new JPanel(new GridLayout(0, 1, 0, 4));
        p.setBackground(Color.WHITE);
        p.add(c1);
        p.add(c2);
        p.add(c3);
        p.add(c4);
        p.add(c5);
        p.add(c6);

        JScrollPane s = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        s.setHorizontalScrollBar(scrollBar);
        s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        s.setBorder(BorderFactory.createEmptyBorder()); // убираем границу (стандартную тень)

        return s;
    }

    private JLabel createLabel(JLabel label, String startText) {
        label.setText("<html><b>" + startText + "</b></html>"); // Лэйбл с жирным текстом
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Граница вокруг Лэйбла
        // Установка цвета заднего фона
        label.setOpaque(true); // нужно чтобы была отключена прозрачность
        label.setBackground(Color.WHITE); // задаем цвет фона

        label.setFocusable(false); // отключаем получение фокуса
        label.setToolTipText(label.getText()); // Установка всплывающей подсказки

        return label;
    }

    // Метод для изменения одного элемента в документе SVG
    private JTextField createTextField(JTextField textField, JLabel label, String ID) {
        return createTextField(textField, label, ID, null);
    }

    // Метод для изменения сразу двух элементов в документе SVG одновременно
    private JTextField createTextField(JTextField textField, JLabel label, String ID1, String ID2) {
        textField.setText(label.getText().replaceAll("[:,<,/,h,t,m,l,>,b]", "").trim()); // Удаляем все лишние символы
        textField.setToolTipText(textField.getText()); // Устанавливаем всплывающую подсказку
        // Слушатель изменения положения каретки для обновления всплывающей подсказки
        textField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                textField.setToolTipText(textField.getText()); // Устанавливаем всплывающую подсказку с новым текстом
            }
        });
        // Слушатель изменения текста, добавления его на картинку SVG и обновления SVGCanvas
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                cartogram.changeValueWithoutSave(ID1, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                cartogram.changeValueWithoutSave(ID2, textField.getText());
                cartogram.saveChangeValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cartogram.changeValueWithoutSave(ID1, textField.getText());
                cartogram.changeValueWithoutSave(ID2, textField.getText());
                cartogram.saveChangeValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                cartogram.changeValueWithoutSave(ID1, textField.getText());
                cartogram.changeValueWithoutSave(ID2, textField.getText());
                cartogram.saveChangeValue();
            }
        });
        return textField;
    }

    private JPanel acceptedParametersPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);

        JButton bP = new JButton("Конфигурация картограммы");
        bP.setFocusable(false); // отключаем возможность получения фокуса кнопкой
        bP.setToolTipText(bP.getText());
//        bP.addActionListener(this::onButtonClick);
        p.add(bP, BorderLayout.NORTH);

        return p;
    }

    private void createHorizPanel(JSplitPane horizSplit, JComponent panel1, JComponent panel2) {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        horizSplit.setLeftComponent(panel1);
        horizSplit.setRightComponent(panel2);

        horizSplit.setBorder(BorderFactory.createEmptyBorder()); // убираем границу (стандартную тень)
    }

    private void dividerTracking() {
        // Отслеживание положения divider разделенной панели для синхронизации вертикальных divider (в горизонтальных панелях) всех уровней
//        horizSplit1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
//            horizSplit2.setDividerLocation((int) e.getNewValue());
//            horizSplit3.setDividerLocation((int) e.getNewValue());
//            horizSplit4.setDividerLocation((int) e.getNewValue());
//        });

//        horizSplit2.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
//            horizSplit1.setDividerLocation((int) e.getNewValue());
//            horizSplit3.setDividerLocation((int) e.getNewValue());
//            horizSplit4.setDividerLocation((int) e.getNewValue());
//        });
//
//        horizSplit3.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
//            horizSplit1.setDividerLocation((int) e.getNewValue());
//            horizSplit2.setDividerLocation((int) e.getNewValue());
//            horizSplit4.setDividerLocation((int) e.getNewValue());
//        });
//
//        horizSplit4.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
//            horizSplit1.setDividerLocation((int) e.getNewValue());
//            horizSplit2.setDividerLocation((int) e.getNewValue());
//            horizSplit3.setDividerLocation((int) e.getNewValue());
//        });
    }

    // Клик по кнопке принятия всех параметров конфигурации и инициализации нового проекта
    // В зависимости от выбранных параметров (в общем то только количества подсчитываемых направлений и их тип - Т-левый, Т-правый и т.п.)
//    public void onButtonClick(ActionEvent e) {
//        String oldValue = fullFileName + "_"; // старое имя файла (путь к нему + имя файла)
//
//        pcs.firePropertyChange("fullFileName", oldValue, fullFileName); // уведомляем об изменении пути к файлу
//    }
    // Метод добавляющий слушатель изменения свойств в этот класс
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

//    private String getFullName() {
//        if (fullFileName != null) {
//            this.fullName = fullFileName;
//        }
//        return this.fullName;
//    }
}
