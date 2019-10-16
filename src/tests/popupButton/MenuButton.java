package tests.popupButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.trafficClicker.CreateConfigurationPanel;
import ru.trafficClicker.TrafficClicker;

class MenuButton extends JFrame {

    private CreatePopupButtonMenu popupMenu = new CreatePopupButtonMenu("Now");
    private ImageIcon IconCar = new ImageIcon(this.getClass().getResource("/icons/car/car.png"));
    JButton jb = new JButton();
    private ImageIcon IconTruck = new ImageIcon(this.getClass().getResource("/icons/car/truck1.png"));
    JButton jtruck = new JButton();

    private ImageIcon IconBus = new ImageIcon(this.getClass().getResource("/icons/car/bus1.png"));
    JButton jbus = new JButton();
    private ImageIcon manIcon = new ImageIcon(this.getClass().getResource("/icons/roads/walk.png"));
    private ImageIcon bikeIcon = new ImageIcon(this.getClass().getResource("/icons/roads/bicycle.png"));

    private CreateConfigurationPanel configurationPanel; // инициализация панели для конфигурации таблицы подсчета

    private JPanel panel = new JPanel(new GridBagLayout());

    public MenuButton() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MenuButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MenuButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set frame properties
        setTitle("Menu Button");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Create JButtons
        jb.setIcon(IconCar);
        createButtonWithIcon(jb);
        jtruck.setIcon(IconTruck);
        createButtonWithIcon(jtruck);
        jbus.setIcon(IconBus);
        createButtonWithIcon(jbus);

        // Create a JPopupMenu

        popupButton(jb, popupMenu.getBus());
        popupButton(jtruck, popupMenu.getBus());
        popupButton(jbus, popupMenu.getTruck());

        // Add the JButtons
        panel.setSize(new Dimension(IconCar.getIconWidth() * 4 + IconCar.getIconWidth() / 2, IconCar.getIconHeight() * 4 + IconCar.getIconWidth() / 2)); // размер панели с кнопками
//        panel.setOpaque(false); // для правильной установки прозрачности кнопок
        panel.setBackground(new Color(.5f, .0f, .0f, .5f)); // установка цвета фона и прозрачности панели с кнопками (чтобы можно было тыкать в видео между кнопок нужно сделать прозрачность нулевой!
        panel.setFocusable(false); // панель не получает фокуса никогда
        panel.setLocation(50, 50); // установка изначального местоположения панели

        panel.add(jb);
        panel.add(jbus);
        panel.add(jtruck);
        add(panel);

        // Set some size and show
        setSize(500, 500);
        setVisible(true);
    }

    private JButton createButtonWithIcon(JButton button) {
        button.setBorderPainted(false); // отключение прорисовки рамки
        button.setFocusPainted(false); // отключение прорисовки специального контура, проявляющегося, если кнопка обладает фокусом ввода
        button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии

        button.setBackground(new Color(150, 150, 150, 130)); // устанавливаем цвет фона кнопки

        button.setPreferredSize(new Dimension(IconCar.getIconWidth() + 2, IconCar.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setMinimumSize(new Dimension(IconCar.getIconWidth() + 2, IconCar.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу
        button.setMaximumSize(new Dimension(IconCar.getIconWidth() + 2, IconCar.getIconHeight() + 2)); // setPreferredSize - если в панели; setSize - если в окне сразу

        button.setOpaque(false);
        button.setFocusable(false); // отключаем перевод фокуса на кнопку (кнопка никогда его не получит)

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    button.setContentAreaFilled(true); // включение закраски кнопки в нажатом состоянии
                    panel.repaint();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    button.setContentAreaFilled(false); // отключение закраски кнопки в нажатом состоянии
                    panel.repaint();
                    repaint();
                }
            }

            // Оба метода: для обновления цвета и прозрачности (отрисовки в общем) фона кнопки!
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panel.repaint();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panel.repaint();
                repaint();
            }
        });

        return button;
    }

    private void popupButton(JButton button, JPopupMenu popMenu) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) { // если кликнули по правой кнопке мыши
                    Component b = (Component) e.getSource(); // получаем компонент, по которому кликнули - кнопка
                    Point p = b.getLocationOnScreen(); // получаем положение на экране компонента по которому кликнули - кнопки

                    popMenu.show(e.getComponent(), 0, 0); // отображаем меню (указываем: компонет, в пространстве которого показываем popupMenu; и координаты popupMenu в системе координат компонента по которому кликнули - кнопки
                    popMenu.setLocation(p.x, p.y + b.getHeight()); // Устанавливаем положение popupMenu в системе координат области, в которой находится компонент, по которому кликнули - кнопка. по x = левый угол кнопки; по y - низ кнопки
                }
            }
        });
    }

    public static void main(String args[]) {
        new MenuButton();
    }
}
