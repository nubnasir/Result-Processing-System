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
public class ThreadLabFinal implements Runnable {

    Default def;
    MarksInserter mi;
    public int value = 0;

    public ThreadLabFinal(Default d, MarksInserter m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        while (true) {
            if (mi.lf10.isSelected()) {
                value = 10;
            } else if (mi.lf20.isSelected()) {
                value = 20;
            } else if (mi.lf30.isSelected()) {
                value = 30;
            } else if (mi.lf40.isSelected()) {
                value = 40;
            } else if (mi.lf50.isSelected()) {
                value = 50;
            } else if (mi.lf60.isSelected()) {
                value = 60;
            } else if (mi.lf70.isSelected()) {
                value = 70;
            } else if (mi.lf80.isSelected()) {
                value = 80;
            } else if (mi.lf90.isSelected()) {
                value = 90;
            } else if (mi.lf100.isSelected()) {
                value = 100;
            }
            DefaultTableModel model = (DefaultTableModel) mi.labTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    float v = Float.parseFloat("" + mi.labTable.getValueAt(i, 3));
                    if (v > value || v < 0) {
                        JOptionPane.showMessageDialog(null, "Inserted value is out of range.");
                        mi.labTable.setValueAt("", i, 3);
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }
}
