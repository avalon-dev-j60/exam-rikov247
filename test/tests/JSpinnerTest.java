package tests;

// Использование счетчиков JSpinner и моделей SpinnerModel 
import javax.swing.*;

import java.util.Date;
import java.util.Calendar;

public class JSpinnerTest extends JFrame {

    private String[] data = {"Понедельник", "Вторник", "Среда", "Четверг",
        "Пятница", "Суббота", "Воскресенье"};
    private Calendar calendar = Calendar.getInstance();

    public JSpinnerTest() {
        super("Пример использования JSpinner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание модели списка из массива строк
        SpinnerModel week = new SpinnerListModel(data);
        // Создание модели списка целых чисел
        SpinnerModel numbers = new SpinnerNumberModel(8, 0, 16, 2);

        // Модель выбора дня месяца
        SpinnerModel dayModel = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);
        // Модель выбора дня текущего месяца
        SpinnerModel monthModel = new SpinnerDateModel(new Date(),
                new MinDate(),
                new MaxDate(),
                Calendar.MONTH);

        // Создание счетчиков
        JSpinner spinInt = new JSpinner(numbers);
        JSpinner spinWeek = new JSpinner(week);
        JSpinner spinDay = new JSpinner(dayModel);
        JSpinner spinMonth = new JSpinner(monthModel);

        // Размещение счетчиков в интерфейсе
        JPanel contents = new JPanel();
        contents.add(spinInt);
        contents.add(spinWeek);
        contents.add(spinDay);
        contents.add(spinMonth);

        setContentPane(contents);

        // Вывод окна на экран
        setSize(500, 90);
        setVisible(true);
    }

    // Проверка минимальной даты
    class MinDate extends Date implements Comparable<Date> {

        private static final long serialVersionUID = 1L;

        public int compareTo(Date o) {
            Date d = (Date) o;
            calendar.setTime(d);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            Calendar prev = Calendar.getInstance();
            prev.add(Calendar.MONTH, -1);
            int year_pred = prev.get(Calendar.YEAR);
            int month_pred = prev.get(Calendar.MONTH);
            // Только текущий месяц
            return (((year == year_pred) && (month > month_pred))
                    || ((year > year_pred) && (month < month_pred))) ? -1 : 1;
        }
    }

    // Проверка максимальной даты
    class MaxDate extends Date implements Comparable<Date> {

        private static final long serialVersionUID = 1L;

        public int compareTo(Date o) {
            Date d = (Date) o;
            calendar.setTime(d);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            Calendar next = Calendar.getInstance();
            next.add(Calendar.MONTH, 1);
            int year_next = next.get(Calendar.YEAR);
            int month_next = next.get(Calendar.MONTH);
            // Только текущий месяц
            return (((year == year_next) && (month < month_next))
                    || ((year < year_next) && (month > month_next))) ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        new JSpinnerTest();
    }
}
