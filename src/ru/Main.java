package ru;

import javax.swing.*;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;

public class Main {

    private static String vlcLibraryPath = "/Portable_programs/netbeans-11.0-bin/vlc_plugins";

    public static void main(String[] args) {
        // Подгрузка VLC библиотек
       NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcLibraryPath);
//        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        // Создание окна (зачем делать через invoke later - ВЫЯСНИТЬ)
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
