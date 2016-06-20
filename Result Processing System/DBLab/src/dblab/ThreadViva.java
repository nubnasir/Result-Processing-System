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
public class ThreadViva implements Runnable {

    Default def;
    MarksInserter mi;
    public int value = 0;

    public ThreadViva(Default d, MarksInserter m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        while (true) {
            if (mi.v10.isSelected()) {
                value = 10;
            } else if (mi.v20.isSelected()) {
                value = 20;
            } else if (mi.v30.isSelected()) {
                value = 30;
            } else if (mi.v40.isSelected()) {
                value = 40;
            } else if (mi.v50.isSelected()) {
                value = 50;
            } else if (mi.v60.isSelected()) {
                value = 60;
            } else if (mi.v70.isSelected()) {
                value = 70;
            } else if (mi.v80.isSelected()) {
                value = 80;
            } else if (mi.v90.isSelected()) {
                value = 90;
            } else if (mi.v100.isSelected()) {
                value = 100;
            }
            DefaultTableModel model = (DefaultTableModel) mi.vivaTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    float v = Float.parseFloat("" + mi.vivaTable.getValueAt(i, 2));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.vivaTable.setValueAt("", i, 2);
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }
}
