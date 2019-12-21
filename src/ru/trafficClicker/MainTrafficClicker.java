/**
 * JavaDOC v.4.1.0 = http://caprica.github.io/vlcj/javadoc/4.1.0/index.html
 * JavaDOC v.3.12.1= http://caprica.github.io/vlcj/javadoc/3.12.1/overview-summary.html
 */
package ru.trafficClicker;

import com.sun.jna.NativeLibrary;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.RuntimeUtil;

public class MainTrafficClicker {

    // Путь к библиотекам VLC (.dll library), находящимся в корне папке проекта в указанной ниже папке
    private static String vlcLibraryPath = "vlc_plugins";

    public static void main(String[] args) throws Exception {
        // Подгрузка VLC библиотек
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibraryPath);
        // Чтобы не обновлять компоненты Swing из собственного потока - все обновления компонент Swing должны проходить через поток диспетчеризации событий Swing (EDT).
        // Это достигается использованием SwingUtilities.invokeLater в обработчике событий:
//        JFrame frame = new TrafficClicker(); // создание окна SliderExample
//        frame.setVisible(true); // делаем это окно видимым
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Start();
                } catch (IOException ex) {
                    Logger.getLogger(MainTrafficClicker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void Start() throws IOException {
        JFrame frame = new TrafficClicker(); // создание окна SliderExample
        frame.setVisible(true); // делаем это окно видимым
    }

}
