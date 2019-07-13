/**
 * JavaDOC v.4.1.0 = http://caprica.github.io/vlcj/javadoc/4.1.0/index.html
 * JavaDOC v.3.12.1= http://caprica.github.io/vlcj/javadoc/3.12.1/overview-summary.html
 */
package ru;

import com.sun.jna.NativeLibrary;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.RuntimeUtil;

public class Main {

    private static final String vlcLibraryPath = "./vlc_plugins";

    public static void main(String[] args) throws Exception {
        // Подгрузка VLC библиотек
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibraryPath);

        // Создание окна (зачем делать через invoke later - ВЫЯСНИТЬ)
        // Чтобы не обновлять компоненты Swing из собственного потока - все обновления компонент Swing должны проходить через поток диспетчеризации событий Swing (EDT).
        // Это достигается использованием SwingUtilities.invokeLater в обработчике событий:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Start();
            }
        });
    }

    public static void Start() {
        JFrame frame = new ExampleVLC(); // создание окна SliderExample
        frame.setVisible(true); // делаем это окно видимым
    }

}
