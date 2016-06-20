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
public class ThreadNormalConAssmnt implements Runnable {

    Default def;
    MarksInserter mi;
    public int value = 0;

    public ThreadNormalConAssmnt(Default d, MarksInserter m) {
        def = d;
        mi = m;
    }

    @Override
    public void run() {
        System.out.println("Enter NorC");
        while (true) {
            if (mi.nca10.isSelected()) {
                value = 10;
            } else if (mi.nca20.isSelected()) {
                value = 20;
            } else if (mi.nca30.isSelected()) {
                value = 30;
            } else if (mi.nca40.isSelected()) {
                value = 40;
            } else if (mi.nca50.isSelected()) {
                value = 50;
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
            }
        }
    }
}
