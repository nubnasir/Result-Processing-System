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
public class ThreadNormalFinal implements Runnable {

    Default def;
    MarksInserter mi;
    public int value = 0;

    public ThreadNormalFinal(Default d, MarksInserter m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        while (true) {
            if (mi.nf10.isSelected()) {
                value = 10;
            } else if (mi.nf20.isSelected()) {
                value = 20;
            } else if (mi.nf30.isSelected()) {
                value = 30;
            } else if (mi.nf40.isSelected()) {
                value = 40;
            } else if (mi.nf50.isSelected()) {
                value = 50;
            } else if (mi.nf60.isSelected()) {
                value = 60;
            } else if (mi.nf70.isSelected()) {
                value = 70;
            } else if (mi.nf80.isSelected()) {
                value = 80;
            } else if (mi.nf90.isSelected()) {
                value = 90;
            } else if (mi.nf100.isSelected()) {
                value = 100;
            }
            DefaultTableModel model = (DefaultTableModel) mi.markTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    float v = Float.parseFloat("" + mi.markTable.getValueAt(i, 2));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.markTable.setValueAt("", i, 2);
                    }
                } catch (Exception ex) {
                    continue;
                }
                try {
                    float v = Float.parseFloat("" + mi.markTable.getValueAt(i, 3));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.markTable.setValueAt("", i, 3);
                    }
                } catch (Exception ex) {
                    continue;
                }
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
