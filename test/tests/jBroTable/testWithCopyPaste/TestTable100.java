package tests.jBroTable.testWithCopyPaste;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;

public class TestTable100 {

    JPopupMenu popup = new JPopupMenu();
    private static int rowIndex;
    private static int columnIndex;

    public static void main(String[] args) {
        new TestTable100();
    }

    public TestTable100() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Type");
                model.addColumn("Column");
                for (File file : new File(System.getProperty("user.home")).listFiles()) {
                    model.addRow(new Object[]{file, file});
                }

                popup.setName("popupMenu");
                JMenuItem menuItemCopy = new JMenuItem(new DefaultEditorKit.CopyAction());
                menuItemCopy.setText("Copy");
                menuItemCopy.setName("copy");
                popup.add(menuItemCopy);
                popup.addSeparator();
                JMenuItem menuItemPaste = new JMenuItem(new DefaultEditorKit.PasteAction());
                menuItemPaste.setText("Paste");
                menuItemPaste.setName("paste");
                popup.add(menuItemPaste);

                JTable table = new JTable(model);

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new JScrollPane(table));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                menuItemCopy.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                        StringSelection stringSelection = new StringSelection(cellValue);
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
                    }
                });

                menuItemPaste.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                        try {
                            table.getModel().setValueAt(clpbrd.getData(DataFlavor.stringFlavor), rowIndex,
                                    columnIndex);
                        } catch (UnsupportedFlavorException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                table.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == 3) {
                            rowIndex = table.rowAtPoint(e.getPoint());
                            columnIndex = table.columnAtPoint(e.getPoint());

                            popup.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }

                });

            }
        });
    }
}
