// http://java-course.ru/articles/preferences-api/
// http://forum.vingrad.ru/articles/topic-157995.html
// https://docs.oracle.com/javase/7/docs/api/java/util/prefs/Preferences.html
package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.PreferenceChangeEvent;

import javax.swing.JFrame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserPreferences extends JFrame implements PreferenceChangeListener {

    private Preferences userPrefs;

    public UserPreferences() {
        // Здесь мы создаем объект класса Preferences. Создаем запись в реестре по адресу: 
        // HKEY_CURRENT_USER\Software\JavaSoft\Prefs\prefexample
        userPrefs = Preferences.userRoot().node("trafficclicker");
        // Указываем слушателя для отслеживания изменений параметров
        userPrefs.addPreferenceChangeListener(this);

        // Устанавливаем первоначальные размеры
        setToPreferredSize();

        // Форма при закрытии завершает приложение и делаем форму видимой          
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        clearPreferences();
    }

    public void setToPreferredSize() {
        // Получить значение параметров и установить размеры формы
        int width = userPrefs.getInt("width", 100);
        int height = userPrefs.getInt("height", 200);
        setSize(width, height);
        System.out.println("Width = " + getWidth() + " Height = " + getHeight());
    }

    // Это наш обработчик событий при изменении параметров в реестре
    @Override
    public void preferenceChange(PreferenceChangeEvent e) {
        setToPreferredSize();
    }

    // Основная рабочая функция
    public void resetDimensionsManyTimes() {
//        for (int i = 0; i<10; i++) {
        // Сохраняем случайные значения для высоты и ширины
        putRandomDimensions();

        try {
            // И «засыпаем» на 5 секунд – можно быстро посмотреть в реестр, что там
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
// Экспорт данных из реестра в xml.
            userPrefs.exportNode(new FileOutputStream(getUserDataDirectory() + "config.xml"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BackingStoreException ex) {
            Logger.getLogger(UserPreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Сохраняем параметры в виде случайных чисел
    private void putRandomDimensions() {
        userPrefs.putInt("width", getRandomInt());
        userPrefs.putInt("height", getRandomInt());
    }

    // Возвращаем случайное челое число от 100 до 400
    private int getRandomInt() {
        return (int) (Math.random() * 300 + 100);
    }

    // Данная функция нигде не вызывается. но если ее вызвать,
    // то все данные будут удалены из реестра
    public void clearPreferences() {
        try {
            userPrefs.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    // Получаем директорию Пользователя (User/nameUser)
    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator;
    }

    public static void main(String[] args) {
        // Создаем форму
        UserPreferences up = new UserPreferences();

        up.resetDimensionsManyTimes();
    }
}
