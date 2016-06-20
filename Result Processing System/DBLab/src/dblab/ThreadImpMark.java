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
public class ThreadImpMark implements Runnable {

    Default def;
    InsertImprovementMarks mi;
    public int value = 0;

    public ThreadImpMark(Default d, InsertImprovementMarks m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        while (true) {
            if (mi.m10.isSelected()) {
                value = 10;
            } else if (mi.m20.isSelected()) {
                value = 20;
            } else if (mi.m30.isSelected()) {
                value = 30;
            } else if (mi.m40.isSelected()) {
                value = 40;
            } else if (mi.m50.isSelected()) {
                value = 50;
            } else if (mi.m60.isSelected()) {
                value = 60;
            } else if (mi.m70.isSelected()) {
                value = 70;
            } else if (mi.m80.isSelected()) {
                value = 80;
            } else if (mi.m90.isSelected()) {
                value = 90;
            } else if (mi.m100.isSelected()) {
                value = 100;
            }
            DefaultTableModel model = (DefaultTableModel) mi.viewtable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    float v = Float.parseFloat("" + mi.viewtable.getValueAt(i, 1));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.viewtable.setValueAt("", i, 1);
                    }
                } catch (Exception ex) {
                    continue;
                }
                try {
                    float v = Float.parseFloat("" + mi.viewtable.getValueAt(i, 2));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.viewtable.setValueAt("", i, 2);
                    }
                } catch (Exception ex) {
                    continue;
                }
                try {
                    float v = Float.parseFloat("" + mi.viewtable.getValueAt(i, 3));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.viewtable.setValueAt("", i, 3);
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }
}
