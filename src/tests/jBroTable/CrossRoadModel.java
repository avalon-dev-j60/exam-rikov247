package tests.jBroTable;

import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.model.ModelFieldGroup;

/**
 * Класс для описания модели заголовка таблицы для Х образного перекрестка
 */
public class CrossRoadModel {

    private IModelFieldGroup groups[];

    // Превращаем полученную модель в модель заголовка для описания Х перекрестка
    public IModelFieldGroup[] getXModel() {
        groups = new IModelFieldGroup[]{
            new ModelFieldGroup("Транспорт", "Вид транспорта").withRowspan(4).withFixed(true) // Устанавливаем область расширения на 4 сроки. Устанавливаем фиксацию при пролистывании для столбца
            .withChild(new ModelField("Тип транспорта", " ").withDefaultWidth(70)) // Установка стандартной ширины столбца
            .withChild(new ModelField("Вид транспорта", " ").withDefaultWidth(160)),
            new ModelField("Категория транспорта", "Транспортный индентификатор")
            .withVisible(false), // для обединения ячеек по ID
            new ModelFieldGroup("Направление 1-3", "Направление 1-3")
            .withChild(new ModelFieldGroup("Направление 1", "Направление 1")
            .withChild(new ModelFieldGroup("Налево 12", "Налево 12")
            .withChild(new ModelField("ФЕ Налево 12", "ФЕ"))
            .withChild(new ModelField("ПЕ Налево 12", "ПЕ")))
            .withChild(new ModelFieldGroup("Прямо 1", "Прямо 1")
            .withChild(new ModelField("ФЕ Прямо 1", "ФЕ"))
            .withChild(new ModelField("ПЕ Прямо 1", "ПЕ")))
            .withChild(new ModelFieldGroup("Направо 14", "Направо 14")
            .withChild(new ModelField("ФЕ Направо 14", "ФЕ"))
            .withChild(new ModelField("ПЕ Направо 14", "ПЕ")))
            .withChild(new ModelFieldGroup("Разворот 11", "Разворот 11")
            .withChild(new ModelField("ФЕ Разворот 11", "ФЕ"))
            .withChild(new ModelField("ПЕ Разворот 11", "ПЕ")))
            .withChild(new ModelFieldGroup("Итого1", "Итого")
            .withChild(new ModelField("ФЕ Итого1", "ФЕ"))
            .withChild(new ModelField("ПЕ Итого1", "ПЕ")))
            )
            .withChild(new ModelFieldGroup("Направление 3", "Направление 3")
            .withChild(new ModelFieldGroup("Налево 34", "Налево 34")
            .withChild(new ModelField("ФЕ Налево 34", "ФЕ"))
            .withChild(new ModelField("ПЕ Налево 34", "ПЕ")))
            .withChild(new ModelFieldGroup("Прямо 3", "Прямо 3")
            .withChild(new ModelField("ФЕ Прямо 3", "ФЕ"))
            .withChild(new ModelField("ПЕ Прямо 3", "ПЕ")))
            .withChild(new ModelFieldGroup("Направо 32", "Направо 32")
            .withChild(new ModelField("ФЕ Направо 32", "ФЕ"))
            .withChild(new ModelField("ПЕ Направо 32", "ПЕ")))
            .withChild(new ModelFieldGroup("Разворот 33", "Разворот 33")
            .withChild(new ModelField("ФЕ Разворот 33", "ФЕ"))
            .withChild(new ModelField("ПЕ Разворот 33", "ПЕ")))
            .withChild(new ModelFieldGroup("Итого3", "Итого")
            .withChild(new ModelField("ФЕ Итого3", "ФЕ"))
            .withChild(new ModelField("ПЕ Итого3", "ПЕ")))),
            new ModelFieldGroup("Направление 2-4", "Направление 2-4")
            .withChild(new ModelFieldGroup("Направление 2", "Направление 2")
            .withChild(new ModelFieldGroup("Налево 23", "Налево 23")
            .withChild(new ModelField("ФЕ Налево 23", "ФЕ"))
            .withChild(new ModelField("ПЕ Налево 23", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Прямо 2", "Прямо 2")
            .withChild(new ModelField("ФЕ Прямо 2", "ФЕ"))
            .withChild(new ModelField("ПЕ Прямо 2", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Направо 21", "Направо 21")
            .withChild(new ModelField("ФЕ Направо 21", "ФЕ"))
            .withChild(new ModelField("ПЕ Направо 21", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Разворот 22", "Разворот 22")
            .withChild(new ModelField("ФЕ Разворот 22", "ФЕ"))
            .withChild(new ModelField("ПЕ Разворот 22", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Итого2", "Итого")
            .withChild(new ModelField("ФЕ Итого2", "ФЕ"))
            .withChild(new ModelField("ПЕ Итого2", "ПЕ"))
            )
            )
            .withChild(new ModelFieldGroup("Направление 4", "Направление 4")
            .withChild(new ModelFieldGroup("Налево 41", "Налево 41")
            .withChild(new ModelField("ФЕ Налево 41", "ФЕ"))
            .withChild(new ModelField("ПЕ Налево 41", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Прямо 4", "Прямо 4")
            .withChild(new ModelField("ФЕ Прямо 4", "ФЕ"))
            .withChild(new ModelField("ПЕ Прямо 4", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Направо 43", "Направо 43")
            .withChild(new ModelField("ФЕ Направо 43", "ФЕ"))
            .withChild(new ModelField("ПЕ Направо 43", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Разворот 44", "Разворот 44")
            .withChild(new ModelField("ФЕ Разворот 44", "ФЕ"))
            .withChild(new ModelField("ПЕ Разворот 44", "ПЕ"))
            )
            .withChild(new ModelFieldGroup("Итого4", "Итого")
            .withChild(new ModelField("ФЕ Итого4", "ФЕ"))
            .withChild(new ModelField("ПЕ Итого4", "ПЕ"))
            )
            ),
            new ModelFieldGroup("Всего за перекресток", "Всего за перекресток")
            .withChild(new ModelField("ФЕ Всего", "ФЕ").withDefaultWidth(60))
            .withChild(new ModelField("ПЕ Всего", "ПЕ").withDefaultWidth(60))
        };
        return groups; // возвращаем заполненную модель Х перекрестка
    }

}
