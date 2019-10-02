package tests.jBroTable;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import org.quinto.swing.table.view.JBroTable;

/**
 * Описание активных команд в таблице
 */
public class ActionMap {

    private JBroTable table;
    
    public ActionMap(JBroTable table) {
        this.table = table;
    }
    
    public void setActionMap() {
        table.getActionMap().put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                StringSelection stringSelection = new StringSelection(cellValue);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
            }
        });

        table.getActionMap().put("paste", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                try {
                    String pasteText = (String) clpbrd.getData(DataFlavor.stringFlavor); // получаемый текст из буфера обмена
                    // Здесь должна быть проверка текста - чтобы это был либо только текст, либо только цифры
                    table.getModel().setValueAt(pasteText, table.getSelectedRow(), table.getSelectedColumn());
                } catch (UnsupportedFlavorException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    
}
