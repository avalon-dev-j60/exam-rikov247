/**
 * Рабочая область поверх видео
 */
package ru;

import com.sun.jna.platform.WindowUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Window;
import static javax.management.Query.lt;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Overlay extends Window {

    private JButton b1 = new JButton("Налево");
    private JButton b2 = new JButton("Прямо");
    private JButton b3 = new JButton("Направо");
    private JButton b4 = new JButton("Разворот");
    private JButton b5 = new JButton("Налево");
    private JButton b6 = new JButton("Прямо");
    private JButton b7 = new JButton("Направо");
    private JButton b8 = new JButton("Разворот");
    private JButton b9 = new JButton("Налево");
    private JButton b10 = new JButton("Прямо");
    private JButton b11 = new JButton("Направо");
    private JButton b12 = new JButton("Разворот");

    private int widthButton = 110; // Ширина кнопки
    private int heightButton = 24; // Высота кнопки

    public Overlay(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());

        setBackground(new Color(0, 0, 0, 0)); // установка прозрачности overlay панели

        setLayout(null); // LayoutManager = null

        b1.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b2.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b3.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b4.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)

        add(b1);
        add(b2);
        add(b3);
        add(b4);

        b5.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b6.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b7.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b8.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)

        add(b5);
        add(b6);
        add(b7);
        add(b8);

        b9.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b10.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b11.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b12.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)

        add(b9);
        add(b10);
        add(b11);
        add(b12);
    }

    public int getWidthButton() {
        return widthButton;
    }

    public int getHeightButton() {
        return heightButton;
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        GradientPaint gp = new GradientPaint(180.0f, 280.0f, new Color(255, 255, 255, 255), 250.0f, 380.0f, new Color(255, 255, 0, 0));
//        g2.setPaint(gp);
//        for (int i = 0; i < 3; i++) {
//            g2.drawOval(150, 280, 100, 100);
//            g2.fillOval(150, 280, 100, 100);
//            g2.translate(120, 20);
//        }
//    }
//
//    private class TranslucentComponent extends JComponent {
//
//        public TranslucentComponent() {
//            setOpaque(false);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            Graphics2D g2 = (Graphics2D) g;
//
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
//
//            g2.setPaint(new Color(255, 128, 128, 64));
//
//            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//            g2.fillRect(0, 0, getWidth(), getHeight());
//
//            g2.setPaint(new Color(0, 0, 0, 128));
//            g2.setFont(new Font("Sansserif", Font.BOLD, 18));
//            g2.drawString("Translucent", 16, 26);
//        }
//    }
    public JButton getB1() {
        return b1;
    }

    public JButton getB2() {
        return b2;
    }

    public JButton getB3() {
        return b3;
    }

    public JButton getB4() {
        return b4;
    }

    public JButton getB5() {
        return b5;
    }

    public JButton getB6() {
        return b6;
    }

    public JButton getB7() {
        return b7;
    }

    public JButton getB8() {
        return b8;
    }

    public JButton getB9() {
        return b9;
    }

    public JButton getB10() {
        return b10;
    }

    public JButton getB11() {
        return b11;
    }

    public JButton getB12() {
        return b12;
    }

}
