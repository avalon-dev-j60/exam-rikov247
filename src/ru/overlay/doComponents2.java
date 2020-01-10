package ru.overlay;

import javax.swing.JComponent;

/**
 * Класс для заполнения переданного массива компонентами в нужном количестве и в
 * нужном порядке. Для 2 направлений!
 */
public class doComponents2 {

    // Только Label
    public JComponent[] doComponentsForward(JComponent label) {
        JComponent[] container = new JComponent[]{
            label
        };
        return container;
    }

    // Label + 1 ряд
    public JComponent[] doComponentsForward(JComponent label, JComponent firstButton) {
        JComponent[] container = new JComponent[]{
            label,
            firstButton
        };
        return container;
    }

    // Label + 2 ряда
    public JComponent[] doComponentsForward(JComponent label, JComponent firstButton, JComponent secondButton) {
        JComponent[] container = new JComponent[]{
            label,
            firstButton,
            secondButton
        };
        return container;
    }

    // Label + 3 ряда
    public JComponent[] doComponentsForward(JComponent label, JComponent firstButton, JComponent secondButton, JComponent thirdButton) {
        JComponent[] container = new JComponent[]{
            label,
            firstButton,
            secondButton,
            thirdButton
        };
        return container;
    }

    // Label + 4 ряда
    public JComponent[] doComponentsForward(JComponent label, JComponent firstButton, JComponent secondButton,
            JComponent thirdButton, JComponent forthButton) {
        JComponent[] container = new JComponent[]{
            label,
            firstButton,
            secondButton,
            thirdButton,
            forthButton
        };
        return container;
    }

    // Label + 5 рядов
    public JComponent[] doComponentsForward(JComponent label, JComponent firstButton, JComponent secondButton,
            JComponent thirdButton, JComponent forthButton, JComponent fifthButton) {
        JComponent[] container = new JComponent[]{
            label,
            firstButton,
            secondButton,
            thirdButton,
            forthButton,
            fifthButton
        };
        return container;
    }

    // Label + 6 рядов
    public JComponent[] doComponentsForward(JComponent label, JComponent firstButton, JComponent secondButton,
            JComponent thirdButton, JComponent forthButton, JComponent fifthButton, JComponent sixthButton) {
        JComponent[] container = new JComponent[]{
            label,
            firstButton,
            secondButton,
            thirdButton,
            forthButton,
            fifthButton,
            sixthButton
        };
        return container;
    }

}
