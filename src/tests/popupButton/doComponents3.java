package tests.popupButton;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Класс для заполнения переданного массива компонентами в нужном количестве и в
 * нужном порядке. Для 4 направлений!
 */
public class doComponents3 {

    public JComponent[] doComponents(JComponent[] firstRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2]
        };
        return container;
    }
    
    public JComponent[] doComponents(JComponent[] firstRow, JComponent[] secondRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2]
        };
        return container;
    }

    public JComponent[] doComponents(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2], 
            secondRow[0], secondRow[1], secondRow[2], 
            thirdRow[0], thirdRow[1], thirdRow[2], 
        };
        return container;
    }

    public JComponent[] doComponents(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow, JComponent[] forthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2], 
            secondRow[0], secondRow[1], secondRow[2], 
            thirdRow[0], thirdRow[1], thirdRow[2], 
            forthRow[0], forthRow[1], forthRow[2], 
        };
        return container;
    }

    public JComponent[] doComponents(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow, JComponent[] forthRow, JComponent[] fifthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2], 
            forthRow[0], forthRow[1], forthRow[2],
            fifthRow[0], fifthRow[1], fifthRow[2],
        };
        return container;
    }

    public JComponent[] doComponents(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2],
            forthRow[0], forthRow[1], forthRow[2],
            fifthRow[0], fifthRow[1], fifthRow[2],
            sixthRow[0], sixthRow[1], sixthRow[2],
        };
        return container;
    }

    public JComponent[] doComponents(JComponent[] firstRow, JComponent[] secondRow, JComponent[] thirdRow,
            JComponent[] forthRow, JComponent[] fifthRow, JComponent[] sixthRow, JComponent[] sevenRow) {
        JComponent[] container = new JComponent[]{
            firstRow[0], firstRow[1], firstRow[2],
            secondRow[0], secondRow[1], secondRow[2],
            thirdRow[0], thirdRow[1], thirdRow[2],
            forthRow[0], forthRow[1], forthRow[2],
            fifthRow[0], fifthRow[1], fifthRow[2],
            sixthRow[0], sixthRow[1], sixthRow[2],
            sevenRow[0], sevenRow[1], sevenRow[2],
        };
        return container;
    }

}
