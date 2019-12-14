package ru;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import javax.swing.JButton;
import javax.swing.JPanel;
import ru.overlay.Overlay;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Класс для создания и конфигурации левой панели на вкладке с видео
 */
public class CreateLeftControlPanel {

    private JPanel LeftCPanel = new JPanel();
    private Overlay overlay;
    private EmbeddedMediaPlayerComponent emp;

    JButton button;

    public JPanel createLeftCPanel(Overlay overlay, EmbeddedMediaPlayerComponent emp) {

        this.overlay = overlay;
        this.emp = emp;

        LeftCPanel.setBackground(Color.white);
        button = new JButton("Показать кнопки");
        button.setFocusable(false);
        button.setToolTipText("Сначала создайте проект");
        LeftCPanel.add(button);

        button.addActionListener(actList);

        return LeftCPanel;
    }

    private ActionListener actList = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (overlay != null) {
                button.setToolTipText("Отображение слоя подсчета транспорта");
                if (!overlay.isShowing()) {
                    button.setText("Скрыть кнопки");
                }
                if (overlay.isShowing()) {
                    button.setText("Показать кнопки");
                }
                emp.mediaPlayer().overlay().enable(!emp.mediaPlayer().overlay().enabled()); // если overlay неактивен, то активировать и наоборот
            }
        }
    };

    public JPanel getLeftCPanel() {
        return LeftCPanel;
    }

    public JButton getButton() {
        return button;
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
        button.setText("Показать кнопки");
        button.setToolTipText("Отображение слоя подсчета транспорта");
    }

}
