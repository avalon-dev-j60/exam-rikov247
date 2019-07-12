/**
 * Здесь описываем различные проверки, которые в случае ошибок - генерируют исключения
 */
package ru.avalon.java.trafficClicker.helpers;

import javax.validation.ValidationException;

public final class Validation {

    private Validation() {
    } // Чтобы предотвратить создание экземпляров данного класса

    // Проверка на обязательность значения - должен проверить, что текст не null и текст не пустой
    public static void require(String text, String error) throws ValidationException {
        if (text == null || text.trim().isEmpty()) {
            throw new ValidationException(error);
        }
    }

    // Если объекты не равны друг другу
    public static void requireEquals(Object a, Object b, String error) throws ValidationException {
        if (!a.equals(b)) {
            throw new ValidationException(error);
        }
    }

    // Проверка на null (null ли Object?)
    public static void requireNull(Object object, String error) throws ValidationException {
        if (object != null) {
            throw new ValidationException(error);
        }
    }

// Проверка на null (не null ли Object?)
    public static void requireNonNull(Object object, String error) throws ValidationException {
        if (object == null) {
            throw new ValidationException(error);
        }
    }
}
