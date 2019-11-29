package ru.trafficClicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import net.miginfocom.swing.MigLayout;
import org.xml.sax.SAXException;

/**
 * Класс для создания окна Настроек
 */
public class SettingsFrame {

    private int weightFrame = 400;
    private int heightFrame = 450;
    private JFrame frame;
    private JButton okButton, acceptButton, cancelButton;
    private JCheckBox activePlayPauseCheckBox = new JCheckBox("Play/pause по нажатию");

    private Settings settings;

    private JTabbedPane tableTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // создание панели вкладок с размещением выбора вкладок вверху панели и размещением новых вкладок (если им мало места) в скролящуюся горизонтальную панель

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

    // Метод создания окна настроек. Окно создается при инициализации класса. Далее управляем только его видимостью
    private void makeNewWindow() {
        frame = new JFrame("Настройки");
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
            if (activePlayPauseCheckBox.isSelected()) {
                try {
                    settings.setValueNode("ActionPlayPause", "Yes");
                    settings.writeDocument();
                } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
                    Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!activePlayPauseCheckBox.isSelected()) {
                try {
                    settings.setValueNode("ActionPlayPause", "No");
                    settings.writeDocument();
                } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
                    Logger.getLogger(SettingsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    // Метод считывания исходных настроек из файла настроек (если его нет, то из дефолтного файла настроек)
    private void firstInitialize() {
        try {
            if (settings.getValueNode("ActionPlayPause").equalsIgnoreCase("Yes")) {
                activePlayPauseCheckBox.setSelected(true);
            }
            if (settings.getValueNode("ActionPlayPause").equalsIgnoreCase("No")) {
                activePlayPauseCheckBox.setSelected(false);
            }
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

    public JCheckBox getActivePlayPauseCheckBox() {
        return activePlayPauseCheckBox;
    }

}
