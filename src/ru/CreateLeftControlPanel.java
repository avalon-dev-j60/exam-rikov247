package ru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.*;

public class CreateLeftControlPanel {

    private JPanel LeftCPanel = new JPanel(new GridLayout(16, 1)); // Панель управления видео

    private JLabel l1 = new JLabel("ВЕРХ_Налево: ");
    private JLabel l2 = new JLabel("ВЕРХ_Прямо: ");
    private JLabel l3 = new JLabel("ВЕРХ_Направо: ");
    private JLabel l4 = new JLabel("ВЕРХ_Разворот: ");

    private JLabel l5 = new JLabel("НИЗ_Налево: ");
    private JLabel l6 = new JLabel("НИЗ_Прямо: ");
    private JLabel l7 = new JLabel("НИЗ_Направо: ");
    private JLabel l8 = new JLabel("НИЗ_Разворот: ");

    private JLabel l9 = new JLabel("ПРАВО_Налево: ");
    private JLabel l10 = new JLabel("ПРАВО_Прямо: ");
    private JLabel l11 = new JLabel("ПРАВО_Направо: ");
    private JLabel l12 = new JLabel("ПРАВО_Разворот: ");

    private JLabel l13 = new JLabel("ЛЕВО_Налево: ");
    private JLabel l14 = new JLabel("ЛЕВО_Прямо: ");
    private JLabel l15 = new JLabel("ЛЕВО_Направо: ");
    private JLabel l16 = new JLabel("ЛЕВО_Разворот: ");

    public JPanel createLeftCPanel() {
        LeftCPanel.add(l1);
        LeftCPanel.add(l2);
        LeftCPanel.add(l3);
        LeftCPanel.add(l4);
        LeftCPanel.add(l5);
        LeftCPanel.add(l6);
        LeftCPanel.add(l7);
        LeftCPanel.add(l8);
        LeftCPanel.add(l9);
        LeftCPanel.add(l10);
        LeftCPanel.add(l11);
        LeftCPanel.add(l12);
        LeftCPanel.add(l13);
        LeftCPanel.add(l14);
        LeftCPanel.add(l15);
        LeftCPanel.add(l16);

        LeftCPanel.setBackground(Color.white);

        return LeftCPanel;
    }

    public JPanel getLeftCPanel() {
        return LeftCPanel;
    }

    public JLabel getL1() {
        return l1;
    }

    public JLabel getL2() {
        return l2;
    }

    public JLabel getL3() {
        return l3;
    }

    public JLabel getL4() {
        return l4;
    }

    public JLabel getL5() {
        return l5;
    }

    public JLabel getL6() {
        return l6;
    }

    public JLabel getL7() {
        return l7;
    }

    public JLabel getL8() {
        return l8;
    }

    public JLabel getL9() {
        return l9;
    }

    public JLabel getL10() {
        return l10;
    }

    public JLabel getL11() {
        return l11;
    }

    public JLabel getL12() {
        return l12;
    }

    public JLabel getL13() {
        return l13;
    }

    public JLabel getL14() {
        return l14;
    }

    public JLabel getL15() {
        return l15;
    }

    public JLabel getL16() {
        return l16;
    }

}
