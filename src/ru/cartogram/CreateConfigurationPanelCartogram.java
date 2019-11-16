package ru.cartogram;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

/**
 * Класс для создания Панели (в виде таблицы) Настройки картограммы.<n>
 * На вкладке картограммы.
 */
public class CreateConfigurationPanelCartogram {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this); // переменная позволяющая добавить в этот слушатель изменения свойств 

    private CreateCartogram cartogram;
    private CreateCartogram cartogramMorning;
    private CreateCartogram cartogramDay;
    private CreateCartogram cartogramEvening;
    private CreateCartogram[] cartograms; // массив с картограммами
    private String typeOfDirection; // получаем количество направлений движения

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

    private JTextField sectionOrIntersection = new JTextField();
    private JTextField dayOfWeek = new JTextField();
    private JFormattedTextField date = new JFormattedTextField();
    private JFormattedTextField timeMorning = new JFormattedTextField();
    private JFormattedTextField timeDay = new JFormattedTextField();
    private JFormattedTextField timeEvening = new JFormattedTextField();

    private JLabel sectionOrIntersectionlabel = new JLabel();
    private JLabel datelabel = new JLabel();
    private JLabel dayOfWeeklabel = new JLabel();
    private JLabel timeMorninglabel = new JLabel();
    private JLabel timeDaylabel = new JLabel();
    private JLabel timeEveninglabel = new JLabel();

    private JSplitPane vertSplit1; // (вертикальная панель с разделением по горизонтали) - вертикальная потому что она вытянута по вертикали
    private JSplitPane vertSplit2;
    private JSplitPane vertSplit3;
    private JSplitPane vertSplit4;
    private JSplitPane horizSplit1; // (горизонтальная панель с разделением по вертикали) - горизонтальная потому что она вытянута по горизонтали
    private JSplitPane horizSplit2;
    private JSplitPane horizSplit3;
    private JSplitPane horizSplit4;

    // Получаем картограмму и количество направлений движения
    public CreateConfigurationPanelCartogram(CreateCartogram cartogram, String typeOfDirection) {
        this.cartogram = cartogram;
        this.typeOfDirection = typeOfDirection;
    }

    public CreateConfigurationPanelCartogram(CreateCartogram cartogramMorning, CreateCartogram cartogramDay, CreateCartogram cartogramEvening, String typeOfDirection) {
        cartograms = new CreateCartogram[]{cartogramMorning, cartogramDay, cartogramEvening};
        this.cartogramMorning = cartogramMorning;
        this.cartogramDay = cartogramDay;
        this.cartogramEvening = cartogramEvening;
        this.typeOfDirection = typeOfDirection;
    }

    public JComponent CreateConfigurationPanel() {
        vertSplit1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true); // устанавливаем в положение true continuousLayout - для постоянного отображения divider (разделительной линии) при ее перемещении
        vertSplit1.setDividerSize(10);

        horizSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit1.setDividerSize(3);
        horizSplit1.setDividerLocation(80); // установка положения разделительной линии первоначальное

        horizSplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        horizSplit2.setDividerSize(3);

        vertSplit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        vertSplit2.setDividerSize(3);
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
        // Для первого блока
        createLabel(direction1label, "Направление 1: ");
        createLabel(direction2label, "Направление 2: ");
        createLabel(direction3label, "Направление 3: ");
        createLabel(direction4label, "Направление 4: ");
        createLabel(streetHorizlabel, "Улица горизонтальная: ");
        createLabel(streetVerticlabel, "Улица вертикальная: ");
        // Для второго блока
        createLabel(sectionOrIntersectionlabel, "Участок/перекресток: ");
        createLabel(datelabel, "Дата: ");
        createLabel(dayOfWeeklabel, "День недели: ");
        createLabel(timeMorninglabel, "Время утро: ");
        createLabel(timeDaylabel, "Время день: ");
        createLabel(timeEveninglabel, "Время вечер: ");

        // Конфигурируем Текстовые поля
        // Для первого блока
        createTextField(direction1, direction1label, "StreetName_Up");
        createTextField(direction2, direction2label, "StreetName_Right");
        createTextField(direction3, direction3label, "StreetName_Down");
        createTextField(direction4, direction4label, "StreetName_Left");
        createTextField(streetHoriz, streetHorizlabel, "StreetName_Horizontal1", "StreetName_Horizontal2");
        createTextField(streetVertic, streetVerticlabel, "StreetName_Vertical1", "StreetName_Vertical2");
        // Для второго блока
        createTextFieldData(sectionOrIntersection, "SectionOrIntersection");
        createTextFieldData(dayOfWeek, "DayOfWeek");
        createFormattedTextFieldDataDate(date, "Date");
        createFormattedTextFieldDataTime(timeMorning, "Time", cartogramMorning);
        createFormattedTextFieldDataTime(timeDay, "Time", cartogramDay);
        createFormattedTextFieldDataTime(timeEvening, "Time", cartogramEvening);

        JScrollPane leftPanel1 = new JScrollPane();
        JScrollPane rightPanel1 = new JScrollPane();
        // Создаем первую горизонтальную панель (с двумя половинами, разделенными вертикальной линией)
        if (typeOfDirection.equalsIgnoreCase("4")) {
            leftPanel1 = createLabelPanel(direction1label, direction2label, direction3label, direction4label, streetHorizlabel, streetVerticlabel);
            rightPanel1 = createTextFieldPanel(direction1, direction2, direction3, direction4, streetHoriz, streetVertic);
        }
        if (typeOfDirection.equalsIgnoreCase("4Circle")) {
            leftPanel1 = createLabelPanel(direction1label, direction2label, direction3label, direction4label, streetHorizlabel, streetVerticlabel);
            rightPanel1 = createTextFieldPanel(direction1, direction2, direction3, direction4, streetHoriz, streetVertic);
        }
        if (typeOfDirection.equalsIgnoreCase("3Up")) {
            leftPanel1 = createLabelPanel(direction1label, direction2label, direction4label, streetHorizlabel, streetVerticlabel);
            rightPanel1 = createTextFieldPanel(direction1, direction2, direction4, streetHoriz, streetVertic);
        }
        if (typeOfDirection.equalsIgnoreCase("3Right")) {
            leftPanel1 = createLabelPanel(direction1label, direction2label, direction3label, streetHorizlabel, streetVerticlabel);
            rightPanel1 = createTextFieldPanel(direction1, direction2, direction3, streetHoriz, streetVertic);
        }
        createHorizPanel(horizSplit1, leftPanel1, rightPanel1);
        // Создаем вторую горизонтальную панель (с двумя половинами, разделенными вертикальной линией)
        createHorizPanel(horizSplit2,
                createLabelPanel(sectionOrIntersectionlabel, datelabel, dayOfWeeklabel, timeMorninglabel, timeDaylabel, timeEveninglabel),
                createTextFieldPanel(sectionOrIntersection, date, createComboboxDayOfWeek(), timeMorning, timeDay, timeEvening));

        vertSplit1.setTopComponent(horizSplit1);
        vertSplit1.setBottomComponent(vertSplit2);
        vertSplit2.setTopComponent(horizSplit2);
//        vertSplit2.setBottomComponent(vertSplit3);
//        vertSplit3.setTopComponent(horizSplit3);
//        vertSplit3.setBottomComponent(vertSplit4);
//        vertSplit4.setTopComponent(horizSplit4);
        vertSplit2.setBottomComponent(acceptedParametersPanel());

        // Отслеживание положения divider разделенной панели для синхронизации вертикальных divider (в горизонтальных панелях) всех уровней
        dividerTracking();

        // Убрать границу (тень) вокруг divider
        // UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder()); // Убрать границу (тень) вокруг divider для всех SplitPane в приложении
        vertSplit1.setBorder(BorderFactory.createEmptyBorder());
        vertSplit2.setBorder(BorderFactory.createEmptyBorder());
//        vertSplit3.setBorder(BorderFactory.createEmptyBorder());
//        vertSplit4.setBorder(BorderFactory.createEmptyBorder());
        horizSplit1.setBorder(BorderFactory.createEmptyBorder());
        horizSplit2.setBorder(BorderFactory.createEmptyBorder());
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

    // Конструкторы для возможности добавления на панель меньше, чем исходное количество направлений (6)
    private JScrollPane createTextFieldPanel(JComponent tF1) {
        return createTextFieldPanel(tF1, null, null, null, null, null);
    }

    private JScrollPane createTextFieldPanel(JComponent tF1, JComponent tF2) {
        return createTextFieldPanel(tF1, tF2, null, null, null, null);
    }

    private JScrollPane createTextFieldPanel(JComponent tF1, JComponent tF2, JComponent tF3) {
        return createTextFieldPanel(tF1, tF2, tF3, null, null, null);
    }

    private JScrollPane createTextFieldPanel(JComponent tF1, JComponent tF2, JComponent tF3, JComponent tF4) {
        return createTextFieldPanel(tF1, tF2, tF3, tF4, null, null);
    }

    private JScrollPane createTextFieldPanel(JComponent tF1, JComponent tF2, JComponent tF3, JComponent tF4, JComponent tF5) {
        return createTextFieldPanel(tF1, tF2, tF3, tF4, tF5, null);
    }

    // Конфигурируем группу текстовых полей
    private JScrollPane createTextFieldPanel(JComponent tF1, JComponent tF2, JComponent tF3,
            JComponent tF4, JComponent tF5, JComponent tF6) {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };

        JPanel p = new JPanel(new GridLayout(0, 1, 0, 3));
        p.setBackground(Color.WHITE);

        // Добавляем только те компоненты, которые существуют (не null)
        JComponent[] components = {tF1, tF2, tF3, tF4, tF5, tF6};
        for (int i = 0; i < components.length; i++) {
            if (components[i] != null) {
                p.add(components[i]);
            }
        }

        // Пока что не нужные слушатели. Для реагирования на нажатие на Enter 
//        addListenerToTextField(tF1, "StreetName_Up");
//        addListenerToTextField(tF2, "StreetName_Right");
//        addListenerToTextField(tF3, "StreetName_Down");
//        addListenerToTextField(tF4, "StreetName_Left");
        JScrollPane s = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        s.setHorizontalScrollBar(scrollBar);
        s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        s.setBorder(BorderFactory.createEmptyBorder()); // убираем границу (стандартную тень)

        return s;
    }

    // Конструкторы для возможности добавления на панель меньше, чем исходное количество направлений (6)
    private JScrollPane createLabelPanel(JComponent c1) {
        return createLabelPanel(c1, null, null, null, null, null);
    }

    private JScrollPane createLabelPanel(JComponent c1, JComponent c2) {
        return createLabelPanel(c1, c2, null, null, null, null);
    }

    private JScrollPane createLabelPanel(JComponent c1, JComponent c2, JComponent c3) {
        return createLabelPanel(c1, c2, c3, null, null, null);
    }

    private JScrollPane createLabelPanel(JComponent c1, JComponent c2, JComponent c3, JComponent c4) {
        return createLabelPanel(c1, c2, c3, c4, null, null);
    }

    private JScrollPane createLabelPanel(JComponent c1, JComponent c2, JComponent c3, JComponent c4, JComponent c5) {
        return createLabelPanel(c1, c2, c3, c4, c5, null);
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

        JPanel p = new JPanel(new GridLayout(0, 1, 0, 3));
        p.setBackground(Color.WHITE);

        // Добавляем только те компоненты, которые существуют (не null)
        JComponent[] components = {c1, c2, c3, c4, c5, c6};
        for (int i = 0; i < components.length; i++) {
            if (components[i] != null) {
                p.add(components[i]);
            }
        }

        JScrollPane s = new JScrollPane(p); // Добавление лэйбла в прокручивающуюся панель
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом
        s.setHorizontalScrollBar(scrollBar);
        s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        s.setBorder(BorderFactory.createEmptyBorder()); // убираем границу (стандартную тень)

        return s;
    }

    // Метод создания Лэйбла
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

    // Создание текстового поля для панели изменяемой информации по самой картограмме (элементы изначально в SVG пустые)
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
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSave(ID1, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].changeValueWithoutSave(ID2, textField.getText());
                        cartograms[i].saveChangeValue();
                    }
                }
//                cartogram.changeValueWithoutSave(ID1, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
//                cartogram.changeValueWithoutSave(ID2, textField.getText());
//                cartogram.saveChangeValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSave(ID1, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].changeValueWithoutSave(ID2, textField.getText());
                        cartograms[i].saveChangeValue();
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSave(ID1, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].changeValueWithoutSave(ID2, textField.getText());
                        cartograms[i].saveChangeValue();
                    }
                }
            }
        });
        return textField;
    }

    // Создание текстового поля для панели Данных о картограмме (здесь изначально элемент в SVG пустой)
    private JTextField createTextFieldData(JTextField textField, String ID) {
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
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
//                cartogram.changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
//                cartogram.saveChangeValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
            }
        });
        return textField;
    }

    private JTextField createFormattedTextFieldDataTime(JFormattedTextField textField, String ID, CreateCartogram cartogram) {
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("##:##-##:##");
            phoneFormatter.setPlaceholderCharacter('_');
            phoneFormatter.install(textField); // устанавливаем MaskFormatted для textField
            textField.setColumns(11);
        } catch (ParseException ex) {
            Logger.getLogger(CreateConfigurationPanelCartogram.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                cartogram.changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                cartogram.saveChangeValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cartogram.changeValueWithoutSaveTspan2(ID, textField.getText());
                cartogram.saveChangeValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                cartogram.changeValueWithoutSaveTspan2(ID, textField.getText());
                cartogram.saveChangeValue();
            }
        });
        return textField;
    }

    private JTextField createFormattedTextFieldDataDate(JFormattedTextField textField, String ID) {
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("##.##.##");
            phoneFormatter.setPlaceholderCharacter('_');
            phoneFormatter.install(textField); // устанавливаем MaskFormatted для textField
            textField.setColumns(8);
        } catch (ParseException ex) {
            Logger.getLogger(CreateConfigurationPanelCartogram.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
//                cartogram.changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
//                cartogram.saveChangeValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2(ID, textField.getText()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
            }
        });
        return textField;
    }

    // ПОКА НЕ ИСПОЛЬЗУЕМАЯ ПАНЕЛЬ (в перспективе какая то кнопка для чего либо)  
    private JPanel acceptedParametersPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);

        JButton bP = new JButton("Конфигурация картограммы");
        bP.setFocusable(false); // отключаем возможность получения фокуса кнопкой
        bP.setToolTipText(bP.getText());
//        bP.addActionListener(this::onButtonClick);
//        p.add(bP, BorderLayout.NORTH);

        return p;
    }

    //  Метод для конфигурации горизонтальной панели с разделением налево и право
    private void createHorizPanel(JSplitPane horizSplit, JComponent panel1, JComponent panel2) {
        // Для сокрытия прокручивающей полоски, но оставления функции прокрутки содержимого панели колесом (обманываем scrollBar)
        horizSplit.setLeftComponent(panel1);
        horizSplit.setRightComponent(panel2);

        horizSplit.setBorder(BorderFactory.createEmptyBorder()); // убираем границу (стандартную тень)
    }

    // Метод для отслеживания положения divider разделенной панели для синхронизации вертикальных divider (в горизонтальных панелях) всех уровней
    private void dividerTracking() {
        horizSplit1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit2.setDividerLocation((int) e.getNewValue());
//            horizSplit3.setDividerLocation((int) e.getNewValue());
//            horizSplit4.setDividerLocation((int) e.getNewValue());
        });

        horizSplit2.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            horizSplit1.setDividerLocation((int) e.getNewValue());
//            horizSplit3.setDividerLocation((int) e.getNewValue());
//            horizSplit4.setDividerLocation((int) e.getNewValue());
        });

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

    private JComboBox createComboboxDayOfWeek() {
        String[] items = {"понедельник", "вторник", "среда", "четверг", "пятница", "суббота", "воскресенье"};
        JComboBox comboBox = new JComboBox(items);
        comboBox.setFocusable(false);
        comboBox.setBackground(Color.WHITE); // установка цвета заднего фона JComboBox
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER); // выравнивание текста внутри JComboBox

        // Исходная установка значения в SVG файл
        for (int i = 0; i < cartograms.length; i++) {
            if (cartograms[i] != null) {
                cartograms[i].changeValueWithoutSaveTspan2("DayOfWeek", (String) comboBox.getSelectedItem()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                cartograms[i].saveChangeValue();
            }
        }
//        cartogram.changeValueWithoutSaveTspan2("DayOfWeek", (String) comboBox.getSelectedItem());
//        cartogram.saveChangeValue();
        comboBox.setToolTipText((String) comboBox.getSelectedItem());
        // Слушатель изменения состояния комбоБокса для переноса данных в SVG файл
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < cartograms.length; i++) {
                    if (cartograms[i] != null) {
                        cartograms[i].changeValueWithoutSaveTspan2("DayOfWeek", (String) comboBox.getSelectedItem()); // меняем текст по ID в SVG файле и обновляем SVGCanvas
                        cartograms[i].saveChangeValue();
                    }
                }
                comboBox.setToolTipText((String) comboBox.getSelectedItem());
            }
        });

        return comboBox;
    }
//    private String getFullName() {
//        if (fullFileName != null) {
//            this.fullName = fullFileName;
//        }
//        return this.fullName;
//    }
}
