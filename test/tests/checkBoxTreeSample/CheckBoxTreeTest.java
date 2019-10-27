package tests.checkBoxTreeSample;

import javax.swing.*;
import javax.swing.tree.*;

public class CheckBoxTreeTest extends JFrame {

    private static final long serialVersionUID = 1L;
    final String ROOT = "Учитываемые единицы";
    // Массив листьев деревьев
    final String[] nodes = new String[]{"Легковые", "Автобусы", "Грузовые", "Пешеходы", "Велотранспорт"};
    final String[][] leafs = new String[][]{
        {"Микроавтобусы", "Автопоезда 2-8т.", "Автопоезда > 8т."},
        {"Грузовые < 2т.", "Грузовые 2 - 5т.", "Грузовые 5 - 8т.", "Грузовые > 8т."}};

    public CheckBoxTreeTest() {
        super("Пример JTree с флажками");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание модели дерева
        TreeModel model = createTreeModel();
        // Создание дерева
        CheckBoxTree tree = new CheckBoxTree(model);
        // Размещение дерева в интерфейсе
        getContentPane().add(new JScrollPane(tree));
        // Вывод окна на экран
        setSize(400, 300);
        setVisible(true);
    }
    // Иерархическая модель данных TreeModel для деревьев

    private TreeModel createTreeModel() {
        // Корневой узел дерева
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Добавление ветвей - потомков 1-го уровня
        DefaultMutableTreeNode car = new DefaultMutableTreeNode(new CheckBoxElement(false, nodes[0]));
        DefaultMutableTreeNode bus = new DefaultMutableTreeNode(new CheckBoxElement(false, nodes[1]));
        DefaultMutableTreeNode truck = new DefaultMutableTreeNode(new CheckBoxElement(false, nodes[2]));
        DefaultMutableTreeNode people = new DefaultMutableTreeNode(new CheckBoxElement(false, nodes[3]));
        DefaultMutableTreeNode bicycle = new DefaultMutableTreeNode(new CheckBoxElement(false, nodes[4]));
        // Добавление ветвей к корневой записи
        root.add(car); 
        root.add(bus);
        root.add(truck);
        root.add(people);
        root.add(bicycle);
        // Добавление листьев - потомков 2-го уровня
        for (int i = 0; i < leafs[0].length; i++) {
            bus.add(new DefaultMutableTreeNode(new CheckBoxElement(false, leafs[0][i])));
        }
        for (int i = 0; i < leafs[1].length; i++) {
            truck.add(new DefaultMutableTreeNode(new CheckBoxElement(false, leafs[1][i])));
        }
        // Создание стандартной модели
        return new DefaultTreeModel(root);
    }

    public static void main(String[] args) {
        new CheckBoxTreeTest();
    }
}
