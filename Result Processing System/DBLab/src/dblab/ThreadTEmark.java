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
public class ThreadTEmark implements Runnable{
    Default def;
    InsertTEMarks mi;
    public int value = 0;

    public ThreadTEmark(Default d, InsertTEMarks m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        while (true) {
            if (mi.t10.isSelected()) {
                value = 10;
            } else if (mi.t20.isSelected()) {
                value = 20;
            } else if (mi.t30.isSelected()) {
                value = 30;
            } else if (mi.t40.isSelected()) {
                value = 40;
            } else if (mi.t50.isSelected()) {
                value = 50;
            } else if (mi.t60.isSelected()) {
                value = 60;
            } else if (mi.t70.isSelected()) {
                value = 70;
            } else if (mi.t80.isSelected()) {
                value = 80;
            } else if (mi.t90.isSelected()) {
                value = 90;
            } else if (mi.t100.isSelected()) {
                value = 100;
            }
            DefaultTableModel model = (DefaultTableModel) mi.markTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    float v = Float.parseFloat("" + mi.markTable.getValueAt(i, 4));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.markTable.setValueAt("", i, 4);
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }
}
