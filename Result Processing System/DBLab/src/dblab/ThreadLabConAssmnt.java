/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dblab;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TOSHIBA
 */
public class ThreadLabConAssmnt implements Runnable {

    Default def;
    MarksInserter mi;
    public int value = 0;

    public ThreadLabConAssmnt(Default d, MarksInserter m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        while (true) {
            if (mi.lca10.isSelected()) {
                value = 10;
            } else if (mi.lca20.isSelected()) {
                value = 20;
            } else if (mi.lca30.isSelected()) {
                value = 30;
            } else if (mi.lca40.isSelected()) {
                value = 40;
            } else if (mi.lca50.isSelected()) {
                value = 50;
            }
            DefaultTableModel model = (DefaultTableModel) mi.labTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    float v = Float.parseFloat("" + mi.labTable.getValueAt(i, 2));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.labTable.setValueAt("", i, 2);
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }
}
