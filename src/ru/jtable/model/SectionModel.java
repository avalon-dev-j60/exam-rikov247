package ru.jtable.model;

import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.model.ModelFieldGroup;

/**
 * Класс для описания модели заголовка таблицы для 2 направлений (сечение)
 */
public class SectionModel {

    private IModelFieldGroup groups[];

    // Превращаем полученную модель в модель заголовка для описания Х перекрестка
    public IModelFieldGroup[] getModel() {
        groups = new IModelFieldGroup[]{
            // Устанавливаем область расширения на 4 сроки. Устанавливаем фиксацию при пролистывании вправо для столбца. Запрещаем передвижение столбцов
            new ModelFieldGroup("Транспорт", "Вид транспорта").withRowspan(4).withFixed(true).withManageable(false)
            .withChild(new ModelField("Тип транспорта", " ").withDefaultWidth(90)) // Установка стандартной ширины столбца
            .withChild(new ModelField("Вид транспорта", " ").withDefaultWidth(180)),
            new ModelField("Категория транспорта", "Транспортный индентификатор").withVisible(false), // для обединения ячеек по ID

            new ModelFieldGroup("Направление 2-4", "Направление 2-4").withManageable(false)
            .withChild(new ModelFieldGroup("Направление 2", "Направление 2").withManageable(false)
            .withChild(new ModelFieldGroup("Прямо 2", "Прямо 2").withManageable(false)
            .withChild(new ModelField("ФЕ Прямо 2", "ФЕ"))
            .withChild(new ModelField("ПЕ Прямо 2", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Итого2", "Итого")
            .withChild(new ModelField("ФЕ Итого2", "ФЕ"))
            .withChild(new ModelField("ПЕ Итого2", "ПЕ"))
            )
            )
            .withChild(new ModelFieldGroup("Направление 4", "Направление 4").withManageable(false)
            .withChild(new ModelFieldGroup("Прямо 4", "Прямо 4").withManageable(false)
            .withChild(new ModelField("ФЕ Прямо 4", "ФЕ"))
            .withChild(new ModelField("ПЕ Прямо 4", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Итого4", "Итого").withManageable(false)
            .withChild(new ModelField("ФЕ Итого4", "ФЕ"))
            .withChild(new ModelField("ПЕ Итого4", "ПЕ"))
            )
            ),
            new ModelFieldGroup("Итого за перекресток", "Всего за перекресток").withManageable(false)
            .withChild(new ModelField("ФЕ Всего", "ФЕ").withDefaultWidth(60))
            .withChild(new ModelField("ПЕ Всего", "ПЕ").withDefaultWidth(60))
        };
        return groups; // возвращаем заполненную модель Х перекрестка
    }

}
