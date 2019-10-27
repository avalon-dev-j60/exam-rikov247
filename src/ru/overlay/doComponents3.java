package ru.overlay;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Класс для заполнения переданного массива компонентами в нужном количестве и в
 * нужном порядке. Для 4 направлений!
 */
public class doComponents3 {

    // Только Labels
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2]
        };
        return container;
    }

    // Labels + 1 ряд
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow, JComponent[] secondRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3],
            secondRow[1], secondRow[2], secondRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow, JComponent[] secondRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3],
            secondRow[0], secondRow[2], secondRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow, JComponent[] secondRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3],
            secondRow[0], secondRow[1], secondRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow, JComponent[] secondRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2]
        };
        return container;
    }

    // Labels + 2 ряда
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3],
            secondRow[1], secondRow[2], secondRow[3],
            thirdRow[1], thirdRow[2], thirdRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3],
            secondRow[0], secondRow[2], secondRow[3],
            thirdRow[0], thirdRow[2], thirdRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3],
            secondRow[0], secondRow[1], secondRow[3],
            thirdRow[0], thirdRow[1], thirdRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2]
        };
        return container;
    }

    // Labels + 3 ряда
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3],
            secondRow[1], secondRow[2], secondRow[3],
            thirdRow[1], thirdRow[2], thirdRow[3],
            forthRow[1], forthRow[2], forthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3],
            secondRow[0], secondRow[2], secondRow[3],
            thirdRow[0], thirdRow[2], thirdRow[3],
            forthRow[0], forthRow[2], forthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3],
            secondRow[0], secondRow[1], secondRow[3],
            thirdRow[0], thirdRow[1], thirdRow[3],
            forthRow[0], forthRow[1], forthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2],
            forthRow[0], forthRow[1], forthRow[2]
        };
        return container;
    }

    // Labels + 4 ряда
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3],
            secondRow[1], secondRow[2], secondRow[3],
            thirdRow[1], thirdRow[2], thirdRow[3],
            forthRow[1], forthRow[2], forthRow[3],
            fifthRow[1], fifthRow[2], fifthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3],
            secondRow[0], secondRow[2], secondRow[3],
            thirdRow[0], thirdRow[2], thirdRow[3],
            forthRow[0], forthRow[2], forthRow[3],
            fifthRow[0], fifthRow[2], fifthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3],
            secondRow[0], secondRow[1], secondRow[3],
            thirdRow[0], thirdRow[1], thirdRow[3],
            forthRow[0], forthRow[1], forthRow[3],
            fifthRow[0], fifthRow[1], fifthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2],
            forthRow[0], forthRow[1], forthRow[2],
            fifthRow[0], fifthRow[1], fifthRow[2]
        };
        return container;
    }

    // Labels + 5 рядов
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3],
            secondRow[1], secondRow[2], secondRow[3],
            thirdRow[1], thirdRow[2], thirdRow[3],
            forthRow[1], forthRow[2], forthRow[3],
            fifthRow[1], fifthRow[2], fifthRow[3],
            sixthRow[1], sixthRow[2], sixthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3],
            secondRow[0], secondRow[2], secondRow[3],
            thirdRow[0], thirdRow[2], thirdRow[3],
            forthRow[0], forthRow[2], forthRow[3],
            fifthRow[0], fifthRow[2], fifthRow[3],
            sixthRow[0], sixthRow[2], sixthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3],
            secondRow[0], secondRow[1], secondRow[3],
            thirdRow[0], thirdRow[1], thirdRow[3],
            forthRow[0], forthRow[1], forthRow[3],
            fifthRow[0], fifthRow[1], fifthRow[3],
            sixthRow[0], sixthRow[1], sixthRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2],
            forthRow[0], forthRow[1], forthRow[2],
            fifthRow[0], fifthRow[1], fifthRow[2],
            sixthRow[0], sixthRow[1], sixthRow[2]
        };
        return container;
    }

    // Labels + 6 рядов
    public JComponent[] doComponentsWithoutAround(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow, JComponent[] sevenRow) {
        JComponent[] container = new JComponent[]{
            firstRow[1], firstRow[2], firstRow[3],
            secondRow[1], secondRow[2], secondRow[3],
            thirdRow[1], thirdRow[2], thirdRow[3],
            forthRow[1], forthRow[2], forthRow[3],
            fifthRow[1], fifthRow[2], fifthRow[3],
            sixthRow[1], sixthRow[2], sixthRow[3],
            sevenRow[1], sevenRow[2], sevenRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutLeft(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow, JComponent[] sevenRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[2], firstRow[3],
            secondRow[0], secondRow[2], secondRow[3],
            thirdRow[0], thirdRow[2], thirdRow[3],
            forthRow[0], forthRow[2], forthRow[3],
            fifthRow[0], fifthRow[2], fifthRow[3],
            sixthRow[0], sixthRow[2], sixthRow[3],
            sevenRow[0], sevenRow[2], sevenRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutForward(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow, JComponent[] sevenRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[3],
            secondRow[0], secondRow[1], secondRow[3],
            thirdRow[0], thirdRow[1], thirdRow[3],
            forthRow[0], forthRow[1], forthRow[3],
            fifthRow[0], fifthRow[1], fifthRow[3],
            sixthRow[0], sixthRow[1], sixthRow[3],
            sevenRow[0], sevenRow[1], sevenRow[3]
        };
        return container;
    }

    public JComponent[] doComponentsWithoutRight(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow, JComponent[] sevenRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2],
            forthRow[0], forthRow[1], forthRow[2],
            fifthRow[0], fifthRow[1], fifthRow[2],
            sixthRow[0], sixthRow[1], sixthRow[2],
            sevenRow[0], sevenRow[1], sevenRow[2]
        };
        return container;
    }

}
