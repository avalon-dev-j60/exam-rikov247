/**
 * Рабочая область поверх видео
 */
package ru;

import com.sun.jna.platform.WindowUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;

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

    private JButton b13 = new JButton("Налево");
    private JButton b14 = new JButton("Прямо");
    private JButton b15 = new JButton("Направо");
    private JButton b16 = new JButton("Разворот");

    private int widthButton = 90; // Ширина кнопки
    private int heightButton = 24; // Высота кнопки

    public Overlay(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        setBackground(new Color(0, 0, 0, 0)); // установка прозрачности overlay панели

        setLayout(null); // LayoutManager = null

        b1.setBounds(0, 0, widthButton, heightButton);
        b2.setBounds(widthButton + heightButton, 0, widthButton, heightButton);
        b3.setBounds(2*(widthButton + heightButton), 0, widthButton, heightButton);
        b4.setBounds(3*(widthButton + heightButton), 0, widthButton, heightButton);
        
//        b1.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
//        b2.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
//        b3.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
//        b4.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)

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

        b13.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b14.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b15.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)
        b16.setSize(widthButton, heightButton); // установка размеров кнопки (ширина, высота)

        add(b13);
        add(b14);
        add(b15);
        add(b16);

        // Панель с прозрачным задником и кнопкой
        JPanel upButtonPanel = new JPanel(new FlowLayout());
        upButtonPanel.add(new JButton("Налево"), FlowLayout.LEFT);
        upButtonPanel.add(new JButton("Прямо"));
        upButtonPanel.setBounds(300, 150, 200, (int) (1.5 * heightButton));
//        upButtonPanel.setBackground(new Color(0, 0, 0, 0));
//        add(upButtonPanel);
    }

    public int getWidthButton() {
        return widthButton;
    }

    public int getHeightButton() {
        return heightButton;
    }

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

    public JButton getB13() {
        return b13;
    }

    public JButton getB14() {
        return b14;
    }

    public JButton getB15() {
        return b15;
    }

    public JButton getB16() {
        return b16;
    }

}
