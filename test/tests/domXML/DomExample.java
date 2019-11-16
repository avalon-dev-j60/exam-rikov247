package tests.domXML;

/**
 * Программа для разбора XML файла и печати
 */
import java.io.File;

public class DomExample {

    public static void main(String[] args) {
//        Settings.readInTerminal();
//        WriteInXMLAndWriteXML.writeInXML();
    }

    // Получаем директорию Пользователя (User/nameUser)
    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator;
    }

    // Получаем директорию Рабочего стола Пользователя
    public static String getUserTableDirectory() {
        return System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    }

}
