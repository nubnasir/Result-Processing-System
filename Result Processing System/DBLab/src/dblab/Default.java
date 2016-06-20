/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dblab;

import java.awt.Color;
import java.io.File;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;
import javax.sql.RowSet;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author TOSHIBA
 */
public class Default {

    Statement statement;
    Connection connection = null;
    PreparedStatement ips = null;
    ResultSet resultset = null;
    RowSet rs = null;
    ResultSetMetaData metadata;
    public String stu_roll[];
    public String stu_name[];
    public String course_code[];
    public String course_title[];
    public int cradit[];
    public String marking_type[];
    public float continous_assessment[][];
    public float FE[][];
    public float SE[][];
    public float TE[][];
    public float difference[][];
    public float AV[][];
    public float FinalMarks[][];
    public float TotalMarks[][];
    public float SM[][];
    public float GP[][];
    public String LG[][];
    public int roll_counter = 0;
    public int name_counter = 0;
    public int code_counter = 0;
    public int title_counter = 0;
    public int cradit_counter = 0;
    public int marking_counter = 0;
    public int cgpa_counter = 0;
    public float total_cradit;
    public float sum_tgp[];
    public String comment[];
    public float cgpa[];
    public float GPA[];
    public String CLGA[];
    public String te_stu_name[];
    public String te_stu_id[];
    public String te_course_code[];
    public String te_course_title[];
    public float te_marks[];
    public String imp_stu_id;
    public float imp_cont_a = 0;
    public float imp_fe = 0;
    public float imp_se = 0;
    public float imp_te = 0;
    public int see_course_counter = 0;

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String Batch) {
        this.Batch = Batch;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int Semester) {
        this.Semester = Semester;
    }
    public String Batch;
    public int Semester;

    public int getNumber_of_courses() {
        return number_of_courses;
    }

    public void setNumber_of_courses(int number_of_courses) {
        this.number_of_courses = number_of_courses;
    }

    public int getNumber_of_students() {
        return number_of_students;
    }

    public void setNumber_of_students(int number_of_students) {
        this.number_of_students = number_of_students;
    }
    public int number_of_students;
    public int number_of_courses;
    public int course_counter = 0;

    public Default() {
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Test1", "test", "test");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Problem to connect with database.");
        }
    }

    public void insert(newBatchInformation2 n2, DefaultTableModel dtm) {
        for (int i = 0; i < dtm.getRowCount(); i++) {
            try {
                if (dtm.getValueAt(i, 0) == null || "".equals(dtm.getValueAt(i, 0)) || dtm.getValueAt(i, 1) == null || "".equals(dtm.getValueAt(i, 1))) {
                    //System.out.println("null found ><> " + dtm.getValueAt(i, 0));
                    break;
                } else {
                    ips = connection.prepareStatement("Insert into student_information values('" + dtm.getValueAt(i, 0) + "','" + dtm.getValueAt(i, 1) + "','" + dtm.getValueAt(i, 2) + "','" + n2.batch_no + "','" + n2.session + "'," + 0 + ")");
                    ips.executeUpdate();
                    ips = connection.prepareStatement("Insert into result_set values('" + dtm.getValueAt(i, 0) + "','" + n2.batch_no + "',0,0.00,0.00,0.00,0.00,'None','x')");
                    ips.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Error" + ex);
            }
        }
    }

    public void viewAllInfo(viewInformationOfStudents viewSI) {
        try {
            resultset = statement.executeQuery("Select ID, student_name, e_mail_phone, batch, cgpa from student_information");
            metadata = resultset.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            int r = 0;
            while (resultset.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (viewSI.allInfoTable.getRowCount() <= r) {
                        DefaultTableModel model = (DefaultTableModel) viewSI.allInfoTable.getModel();
                        model.addRow(new Object[]{""});
                    }
                    viewSI.allInfoTable.setValueAt(resultset.getObject(i), r, i - 1);
                }
                r++;
                //db.output.setText(db.output.getText() + "\n");
            }
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }

    public void allInfoSearch(int count, String content, viewInformationOfStudents viewSI) {
        String s = null;
        DefaultTableModel model = (DefaultTableModel) viewSI.allInfoTable.getModel();

        viewSI.progressBar.setMaximum(model.getRowCount() * 2);
        viewSI.progressBar.setValue(0);
        for (int i = model.getRowCount() - 1; i >= 1; i--) {
            model.removeRow(i);
            viewSI.progressBar.setValue(model.getRowCount() - i);
        }
        if (count == 1) {
            s = "STUDENT_NAME";
        } else if (count == 2) {
            s = "ID";
        } else if (count == 3) {
            s = "BATCH";
        } else if (count == 4) {
            s = "E_MAIL_PHONE";
        }
        try {
            resultset = statement.executeQuery("Select ID, student_name, e_mail_phone, batch, cgpa from student_information where " + s + " Like '%" + content + "%'");
            metadata = resultset.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
            }
            //db.output.setText(db.output.getText() + "\n");
            int r = 0;
            while (resultset.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (viewSI.allInfoTable.getRowCount() <= r) {
                        model.addRow(new Object[]{""});
                    }
                    viewSI.allInfoTable.setValueAt(resultset.getObject(i), r, i - 1);
                }
                r++;
                viewSI.progressBar.setValue(viewSI.progressBar.getValue() + r + 1);
                //db.output.setText(db.output.getText() + "\n");
            }
            viewSI.progressBar.setValue(viewSI.progressBar.getMaximum());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void checkNewBatch(String batch, String sess, newBatchInfoFrame n1, StartHere st) {
        String s = "";
        int k = 0;
        try {
            resultset = statement.executeQuery("Select BATCH from student_information where BATCH = '" + batch + "'");
            if (resultset.next()) {
                s = "" + resultset.getObject(1);
            }
            if (s.equals(batch)) {
                k++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        int t = 0;
        String sa = "";
        try {
            resultset = statement.executeQuery("Select SESSION from student_information where SESSION = '" + sess + "'");
            if (resultset.next()) {
                sa = "" + resultset.getObject(1);
            }
            if (sa.equals(sess)) {
                t++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        if (!s.equals(batch) && !sa.equals(sess)) {
            n1.setVisible(false);
            newBatchInformation2 nbi2 = new newBatchInformation2(this);
            nbi2.batch_no = n1.batchNumber.getText();
            nbi2.session = n1.session.getText();
            nbi2.batch.setText("   Batch: " + n1.batchNumber.getText() + " Session: " + n1.session.getText());
            nbi2.setVisible(true);
            st.setVisible(false);
        } else {
            if (k == 1 && t == 1) {
                JOptionPane.showMessageDialog(null, "Both Batch and Session already exits.");
            } else if (k == 1) {
                JOptionPane.showMessageDialog(null, "Batch already exits.");
            } else if (t == 1) {
                JOptionPane.showMessageDialog(null, "Session already exits.");
            }
        }
    }

    public void processResult1(String batch, int sem, MarksInserter mark) {
        try {
            resultset = statement.executeQuery("Select count(ID) from student_information where BATCH='" + batch + "'");
            if (resultset.next()) {
                setNumber_of_students((int) resultset.getInt(1));
            }

            resultset = statement.executeQuery("Select count(SEMESTER) from courses where SEMESTER=" + sem);
            if (resultset.next()) {
                setNumber_of_courses((int) resultset.getInt(1));
            }
            processResultInitializer(getNumber_of_students(), getNumber_of_courses());


            //Taking ID and Name
            resultset = statement.executeQuery("Select ID, STUDENT_NAME, CGPA from student_information where batch='" + batch + "'");
            metadata = resultset.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
            }
            int r = 0;

            // Here Normal Subject

            while (resultset.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (mark.markTable.getRowCount() <= r) {
                        DefaultTableModel model = (DefaultTableModel) mark.markTable.getModel();
                        model.addRow(new Object[]{""});
                    }
                    if (mark.labTable.getRowCount() <= r) {
                        DefaultTableModel model = (DefaultTableModel) mark.labTable.getModel();
                        model.addRow(new Object[]{""});
                    }
                    if (mark.vivaTable.getRowCount() <= r) {
                        DefaultTableModel model = (DefaultTableModel) mark.vivaTable.getModel();
                        model.addRow(new Object[]{""});
                    }
                    if (i % 3 == 0) {
                        cgpa[cgpa_counter] = Float.parseFloat("" + resultset.getObject(i));
                    } else if (i % 2 == 0) {
                        stu_name[name_counter] = "" + resultset.getObject(i);
                        mark.markTable.setValueAt(resultset.getObject(i), r, i - 1);
                        mark.labTable.setValueAt(resultset.getObject(i), r, i - 1);
                        mark.vivaTable.setValueAt(resultset.getObject(i), r, i - 1);
                        name_counter++;
                    } else if (i % 1 == 0) {
                        stu_roll[roll_counter] = "" + resultset.getObject(i);
                        mark.markTable.setValueAt(resultset.getObject(i), r, i - 1);
                        mark.labTable.setValueAt(resultset.getObject(i), r, i - 1);
                        mark.vivaTable.setValueAt(resultset.getObject(i), r, i - 1);
                        roll_counter++;
                    }
                }
                r++;
            }


            //Taking Courses
            resultset = statement.executeQuery("Select COURSE_CODE, TITLE, CRADIT, MARKING_TYPE from COURSES where SEMESTER=" + sem + " order by CRADIT desc, MARKING_TYPE");
            metadata = resultset.getMetaData();
            int nC = metadata.getColumnCount();
            for (int i = 1; i <= nC; i++) {
            }
            int c = 0;
            while (resultset.next()) {
                for (int i = 1; i <= nC; i++) {
                    if (i % 4 == 0) {
                        marking_type[marking_counter] = "" + resultset.getObject(i);
                        marking_counter++;
                    } else if (i % 3 == 0) {
                        cradit[cradit_counter] = (int) resultset.getInt(i);
                        cradit_counter++;
                    } else if (i % 2 == 0) {
                        course_title[title_counter] = "" + resultset.getObject(i);
                        title_counter++;
                    } else {
                        course_code[code_counter] = "" + resultset.getObject(i);
                        code_counter++;
                    }
                }
                c++;
            }

            code_counter = 0;
            title_counter = 0;
            cradit_counter = 0;
            marking_counter = 0;

            mark.infoLebel.setText("Processing Result of Batch: " + batch + ", Semester: " + sem);
            mark.subjectField.setText("Course Title: " + course_title[title_counter]);
            mark.subjectCodeField.setText("Course Code = " + course_code[code_counter] + " and Cradit = " + cradit[cradit_counter]);


            code_counter++;
            title_counter++;
            cradit_counter++;
            marking_counter++;

            //Here Noraml

            if ("NORMAL".equals(marking_type[course_counter])) {
                //normalSubjectTableSet(mark);
                mark.markPanel.setVisible(true);
                mark.labPanel.setVisible(false);
                mark.vivaPanel.setVisible(false);
            } else if ("LAB".equals(marking_type[course_counter])) {
                //labSubjectTableSet(mark);
                mark.markPanel.setVisible(false);
                mark.labPanel.setVisible(true);
                mark.vivaPanel.setVisible(false);
            } else if ("VIVA".equals(marking_type[course_counter])) {
                //vivaSubjectTableSet(mark);
                mark.markPanel.setVisible(false);
                mark.labPanel.setVisible(false);
                mark.vivaPanel.setVisible(true);
            }

        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }

    public void processResultInitializer(int stu, int cous) {
        stu_roll = new String[stu];
        stu_name = new String[stu];
        course_code = new String[cous];
        course_title = new String[cous];
        cradit = new int[cous];
        marking_type = new String[cous];
        continous_assessment = new float[stu][cous];
        FE = new float[stu][cous];
        SE = new float[stu][cous];
        TE = new float[stu][cous];
        difference = new float[stu][cous];
        AV = new float[stu][cous];
        TotalMarks = new float[stu][cous];
        FinalMarks = new float[stu][cous];
        SM = new float[stu][cous];
        GP = new float[stu][cous];
        LG = new String[stu][cous];
        sum_tgp = new float[stu];
        comment = new String[stu];
        cgpa = new float[stu];
        GPA = new float[stu];
        CLGA = new String[stu];
    }

    public void nextCourseMarks(MarksInserter mark) {
        saveCourseMarks(mark);
        if ("NORMAL".equals(marking_type[course_counter])) {
            //normalSubjectTableSet(mark);
            mark.markPanel.setVisible(true);
            mark.labPanel.setVisible(false);
            mark.vivaPanel.setVisible(false);
        } else if ("LAB".equals(marking_type[course_counter])) {
            //labSubjectTableSet(mark);
            mark.markPanel.setVisible(false);
            mark.labPanel.setVisible(true);
            mark.vivaPanel.setVisible(false);
        } else if ("VIVA".equals(marking_type[course_counter])) {
            //vivaSubjectTableSet(mark);
            mark.markPanel.setVisible(false);
            mark.labPanel.setVisible(false);
            mark.vivaPanel.setVisible(true);
        }
    }

    public void preCourseMarks() {
    }

    public void saveCourseMarks(MarksInserter mark) {
        //Error Check needed

        //Result memorizing

        if ("NORMAL".equals(marking_type[course_counter])) {
            DefaultTableModel mod = (DefaultTableModel) mark.markTable.getModel();
            System.out.println(" mod.geRC " + mod.getRowCount());
            for (int i = 0; i < mod.getRowCount(); i++) {
                System.out.println("i=" + i + " mod.geRC " + mod.getRowCount());
                try {

                    continous_assessment[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 2));

                    FE[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 3));

                    SE[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 4));
                    if (FE[i][course_counter] > SE[i][course_counter]) {
                        difference[i][course_counter] = FE[i][course_counter] - SE[i][course_counter];
                    } else if (SE[i][course_counter] > FE[i][course_counter]) {
                        difference[i][course_counter] = SE[i][course_counter] - FE[i][course_counter];
                    }

                    AV[i][course_counter] = (FE[i][course_counter] + SE[i][course_counter]) / 2;

                    if (difference[i][course_counter] >= 12) {
                        try {
                            TE[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 5));

                            float diff1 = 0, diff2 = 0;
                            if (TE[i][course_counter] > FE[i][course_counter]) {
                                diff1 = TE[i][course_counter] - FE[i][course_counter];
                            } else {
                                diff1 = FE[i][course_counter] - TE[i][course_counter];
                            }

                            if (TE[i][course_counter] > SE[i][course_counter]) {
                                diff2 = TE[i][course_counter] - SE[i][course_counter];
                            } else {
                                diff2 = SE[i][course_counter] - TE[i][course_counter];
                            }

                            if (diff1 < diff2) {
                                FinalMarks[i][course_counter] = (FE[i][course_counter] + TE[i][course_counter]) / 2;
                                TotalMarks[i][course_counter] = FinalMarks[i][course_counter] + continous_assessment[i][course_counter];
                            } else {
                                FinalMarks[i][course_counter] = (SE[i][course_counter] + TE[i][course_counter]) / 2;
                                TotalMarks[i][course_counter] = FinalMarks[i][course_counter] + continous_assessment[i][course_counter];
                            }
                        } catch (Exception ex) {
                            TE[i][course_counter] = -1;
                            FinalMarks[i][course_counter] = -1;//AV[i][course_counter];
                            TotalMarks[i][course_counter] = -1;//AV[i][course_counter] + continous_assessment[i][course_counter];
                        }
                    } else {
                        FinalMarks[i][course_counter] = AV[i][course_counter];
                        TotalMarks[i][course_counter] = AV[i][course_counter] + continous_assessment[i][course_counter];
                    }

                    System.out.println("TM:" + TotalMarks[i][course_counter]);

                } catch (Exception ex) {
                    System.out.println("Error1" + ex);
                }
                if (TotalMarks[i][course_counter] != -1.0) {
                    letterGrade(i, TotalMarks[i][course_counter]);
                } else {
                    GP[i][course_counter] = -1;
                    LG[i][course_counter] = "UK";
                    System.out.println("LG : " + LG[i][course_counter] + " i " + i + " code_counter " + course_counter);
                }
            }
        } else if ("LAB".equals(marking_type[course_counter])) {


            DefaultTableModel mod = (DefaultTableModel) mark.labTable.getModel();
            for (int i = 0; i < mod.getRowCount(); i++) {
                try {
                    continous_assessment[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 2));
                    System.out.println("col: 3" + mod.getValueAt(i, 3));
                    FE[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 3));
                    FinalMarks[i][course_counter] = FE[i][course_counter] + continous_assessment[i][course_counter];
                    TotalMarks[i][course_counter] = FinalMarks[i][course_counter];
                    letterGrade(i, TotalMarks[i][course_counter]);
                    SE[i][course_counter] = -1;
                    difference[i][course_counter] = -1;
                    AV[i][course_counter] = -1;
                    TE[i][course_counter] = -1;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Marks should be float type or integer.");
                }
            }
        } else if ("VIVA".equals(marking_type[course_counter])) {

            DefaultTableModel mod = (DefaultTableModel) mark.vivaTable.getModel();
            for (int i = 0; i < mod.getRowCount(); i++) {
                try {
                    FinalMarks[i][course_counter] = Float.parseFloat("" + mod.getValueAt(i, 2));
                    TotalMarks[i][course_counter] = FinalMarks[i][course_counter];
                    letterGrade(i, TotalMarks[i][course_counter]);
                    continous_assessment[i][course_counter] = -1;
                    FE[i][course_counter] = -1;
                    SE[i][course_counter] = -1;
                    difference[i][course_counter] = -1;
                    AV[i][course_counter] = -1;
                    TE[i][course_counter] = -1;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Marks should be float type or integer.");
                }
            }
        }

        course_counter++;
        see_course_counter = course_counter;
        //Refreshing Table

        if (course_counter < number_of_courses) {
            DefaultTableModel model = (DefaultTableModel) mark.markTable.getModel();
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }

            try {
                for (int p = 0; p < number_of_students; p++) {
                    if (mark.markTable.getRowCount() <= p) {
                        model.addRow(new Object[]{""});
                    }
                    for (int i = 1; i <= 2; i++) {
                        if (i == 1) {
                            mark.markTable.setValueAt(stu_roll[p], p, i - 1);
                        } else {
                            mark.markTable.setValueAt(stu_name[p], p, i - 1);
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error2: " + ex);
            }

            mark.subjectField.setText("Course Title: " + course_title[course_counter]);
            mark.subjectCodeField.setText("Course Code = " + course_code[course_counter] + " and Cradit = " + cradit[course_counter]);
        }
    }

    public void letterGrade(int i, float mark) {
        try {
            System.out.println("" + course_code[course_counter]);
            resultset = statement.executeQuery("Select Total_Mark from course_mark where course_code='" + course_code[course_counter] + "'");
            int v = 0;
            if (resultset.next()) {
                v = Integer.parseInt("" + resultset.getObject(1));
            }
            System.out.println("" + v);
            resultset = statement.executeQuery("Select Letter_grade from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + mark + " and end_mark>=" + mark);
            if (resultset.next()) {
                LG[i][course_counter] = "" + resultset.getObject(1);
            }

            resultset = statement.executeQuery("Select Grade_point from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + mark + " and end_mark>=" + mark);
            if (resultset.next()) {
                GP[i][course_counter] = Float.parseFloat("" + resultset.getObject(1));
            }
            System.out.println("LG=" + LG[i][course_counter] + "\nGP=" + GP[i][course_counter]);
        } catch (Exception ex) {
            System.out.println("Error3: " + ex);
        }
    }

    public void normalSubjectTableSet(MarksInserter mark) {
        DefaultTableModel model = (DefaultTableModel) mark.markTable.getModel();

        if (course_counter != 0) {
            if ("LAB".equals(marking_type[course_counter - 1])) {
                mark.markTable.getColumnModel().getColumn(3).setHeaderValue("First Examiner");
                model.addColumn("Second Examiner");
                model.addColumn("Third Examiner");
                mark.delColumn(7);
                mark.delColumn(6);
            } else if ("VIVA".equals(marking_type[course_counter - 1])) {
                mark.markTable.getColumnModel().getColumn(2).setHeaderValue("Continous Assessment");
                model.addColumn("First Examiner");
                model.addColumn("Second Examiner");
                model.addColumn("Third Examiner");
            }
        }
    }

    public void labSubjectTableSet(MarksInserter mark) {
        DefaultTableModel model = (DefaultTableModel) mark.markTable.getModel();

        if (course_counter != 0) {
            if ("NORMAL".equals(marking_type[course_counter - 1])) {
                mark.delColumn(5);
                mark.delColumn(4);
                mark.markTable.getColumnModel().getColumn(3).setHeaderValue("Final Mark");
            } else if ("VIVA".equals(marking_type[course_counter - 1])) {
                mark.markTable.getColumnModel().getColumn(2).setHeaderValue("Continous Assessment");
                model.addColumn("Final Mark");
            }
        } else {
            mark.delColumn(5);
            mark.delColumn(4);
            mark.markTable.getColumnModel().getColumn(3).setHeaderValue("Final Mark");
        }
    }

    public void vivaSubjectTableSet(MarksInserter mark) {
        DefaultTableModel model = (DefaultTableModel) mark.markTable.getModel();

        if (course_counter != 0) {
            if ("NORMAL".equals(marking_type[course_counter - 1])) {
                mark.delColumn(5);
                mark.delColumn(4);
                mark.delColumn(3);
                mark.markTable.getColumnModel().getColumn(2).setHeaderValue("Final Mark");
            } else if ("LAB".equals(marking_type[course_counter - 1])) {
                mark.delColumn(3);
                mark.markTable.getColumnModel().getColumn(2).setHeaderValue("Final Mark");
            }
        } else {
            mark.delColumn(5);
            mark.delColumn(4);
            mark.delColumn(3);
            mark.markTable.getColumnModel().getColumn(2).setHeaderValue("Final Mark");
        }
    }

    public void checkerOfNullInputMark(MarksInserter mark) {
        int c1;
        for (int i = 0; i < mark.markTable.getRowCount(); i++) {
            if (mark.markTable.getColumnCount() == 6) {
                c1 = mark.markTable.getColumnCount() - 1;
            } else {
                c1 = mark.markTable.getColumnCount();
            }
            for (int j = 2; j < c1; j++) {
                if (mark.markTable.getValueAt(i, j) == null) {
                    JOptionPane.showMessageDialog(null, "Null value found at row:" + (i + 1) + " column:" + (j + 1));
                    return;
                } else {
                    try {
                        float num = Float.parseFloat("" + mark.markTable.getValueAt(i, j));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: Inputed number is not integer or float.\n" + ex);
                        return;
                    }
                }
            }
        }
        mark.setCheckNull(1);
    }

    public void checkerOfNullInputLab(MarksInserter mark) {
        int c1;
        for (int i = 0; i < mark.labTable.getRowCount(); i++) {
            if (mark.labTable.getColumnCount() == 6) {
                c1 = mark.labTable.getColumnCount() - 1;
            } else {
                c1 = mark.labTable.getColumnCount();
            }
            for (int j = 2; j < c1; j++) {
                if (mark.labTable.getValueAt(i, j) == null) {
                    JOptionPane.showMessageDialog(null, "Null value found at row:" + (i + 1) + " column:" + (j + 1));
                    return;
                } else {
                    try {
                        float num = Float.parseFloat("" + mark.labTable.getValueAt(i, j));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: Inputed number is not integer or float.\n" + ex);
                        return;
                    }
                }
            }
        }
        mark.setCheckNull(1);
    }

    public void checkerOfNullInputViva(MarksInserter mark) {
        int c1;
        for (int i = 0; i < mark.vivaTable.getRowCount(); i++) {
            if (mark.vivaTable.getColumnCount() == 6) {
                c1 = mark.vivaTable.getColumnCount() - 1;
            } else {
                c1 = mark.vivaTable.getColumnCount();
            }
            for (int j = 2; j < c1; j++) {
                if (mark.vivaTable.getValueAt(i, j) == null) {
                    JOptionPane.showMessageDialog(null, "Null value found at row:" + (i + 1) + " column:" + (j + 1));
                    return;
                } else {
                    try {
                        float num = Float.parseFloat("" + mark.vivaTable.getValueAt(i, j));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: Inputed number is not integer or float.\n" + ex);
                        return;
                    }
                }
            }
        }
        mark.setCheckNull(1);
    }

    public void resultCalculation() {
        try {
            resultset = statement.executeQuery("Select sum(CRADIT) from COURSES where SEMESTER=" + getSemester());
            if (resultset.next()) {
                total_cradit = Float.parseFloat("" + resultset.getObject(1));
            }
            for (int i = 0; i < number_of_students; i++) {
                int sum_gp_cr = 0;
                int check_TE = 0;
                for (int j = 0; j < number_of_courses; j++) {
                    if (GP[i][j] != -1) {
                        sum_gp_cr += GP[i][j] * cradit[j];
                        sum_tgp[i] = sum_gp_cr;
                    } else {
                        check_TE = -1;
                    }
                }
                if (check_TE != -1) {
                    System.out.println("SUM_TGP=" + sum_tgp[i]);
                    GPA[i] = sum_tgp[i] / total_cradit;
                    System.out.println("GPA=" + GPA[i]);
                    //if (cgpa[i] == 0) {
                    //   cgpa[i] = GPA[i];
                    //} else {
                    resultset = statement.executeQuery("Select CGPA from student_information where ID='" + stu_roll[i] + "'");
                    if (resultset.next()) {
                        float sum = Float.parseFloat("" + resultset.getObject(1));
                        System.out.println("Pre CGPA i= " + i + " :" + sum);
                        cgpa[i] = (sum + GPA[i]) / getSemester();
                    }
                    //}
                    System.out.println("CGPA=" + cgpa[i]);
                    if (cgpa[i] < 2) {
                        comment[i] = "NP";
                    } else {
                        comment[i] = "P";
                    }
                    getCLGL(i, cgpa[i]);
                } else {
                    GPA[i] = -1;
                    resultset = statement.executeQuery("Select sum(CGPA) from result_set where ID='" + stu_roll[i] + "'");
                    if (resultset.next()) {
                        float sum = Float.parseFloat("" + resultset.getObject(1));
                        System.out.println("Pre CGPA = " + sum);
                        cgpa[i] = sum;
                    }
                    CLGA[i] = getCLGL(i, cgpa[i]);
                    if (CLGA[i] == "" || CLGA[i] == null) {
                        CLGA[i] = "F";
                    }
                    comment[i] = "TE";
                    continue;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error R:" + ex);
        }
    }

    public void insertSubjectWiseMarksInDatabase(int sem) {
        try {
            for (int i = 0; i < number_of_students; i++) {
                for (int j = 0; j < number_of_courses; j++) {
                    try {
                        System.out.println("insSD: " + LG[i][j] + " i " + i + " j " + j);
                        System.out.println("Insert into exam_mark values('" + stu_roll[i] + "','" + course_code[j] + "'," + FE[i][j] + "," + SE[i][j] + "," + difference[i][j] + "," + AV[i][j] + "," + TE[i][j] + "," + FinalMarks[i][j] + "," + TotalMarks[i][j] + "," + GP[i][j] + ",'" + LG[i][j] + "'," + continous_assessment[i][j] + "," + getSemester() + ")");

                        ips = connection.prepareStatement("Insert into exam_mark values('" + stu_roll[i] + "','" + course_code[j] + "'," + FE[i][j] + "," + SE[i][j] + "," + difference[i][j] + "," + AV[i][j] + "," + TE[i][j] + "," + FinalMarks[i][j] + "," + TotalMarks[i][j] + "," + GP[i][j] + ",'" + LG[i][j] + "'," + continous_assessment[i][j] + "," + getSemester() + ")");
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("IsWmID: " + ex);
                    }
                }
                ips = connection.prepareStatement("Insert into result_set values('" + stu_roll[i] + "','" + getBatch() + "'," + getSemester() + "," + sum_tgp[i] + "," + total_cradit + "," + GPA[i] + "," + cgpa[i] + ",'" + comment[i] + "', '" + CLGA[i] + "')");
                ips.executeUpdate();
                ips = connection.prepareStatement("Update student_information set CGPA=" + cgpa[i] + " where ID='" + stu_roll[i] + "'");
                ips.executeUpdate();

            }
        } catch (Exception ex) {
            System.out.println("Error isR: " + ex);
        }
    }

    public void checkExistenceOfaSemesterResult(String b, int s, ResProcFrame1 r) {
        try {
            resultset = statement.executeQuery("Select ID from result_set where batch='" + b + "' and semester=" + s);
            if (resultset.next()) {
                r.setChecker(0);
            } else {
                r.setChecker(1);
            }
        } catch (Exception ex) {
            System.out.println("Error CER: " + ex);
        }
    }

    public void showFinalResult(FinalResultShow frs) {
        try {
            for (int i = 0; i < number_of_students; i++) {
                if (frs.resultTable.getRowCount() <= i) {
                    DefaultTableModel model = (DefaultTableModel) frs.resultTable.getModel();
                    model.addRow(new Object[]{""});
                }
                frs.resultTable.setValueAt(stu_roll[i], i, 0);
                frs.resultTable.setValueAt(stu_name[i], i, 1);
                if (GPA[i] != -1) {
                    frs.resultTable.setValueAt(sum_tgp[i], i, 2);
                    frs.resultTable.setValueAt(total_cradit, i, 3);
                    frs.resultTable.setValueAt(GPA[i], i, 4);
                    frs.resultTable.setValueAt(cgpa[i], i, 5);
                    frs.resultTable.setValueAt(CLGA[i], i, 6);
                    frs.resultTable.setValueAt(comment[i], i, 7);
                } else {
                    frs.resultTable.setValueAt("---", i, 2);
                    frs.resultTable.setValueAt("---", i, 3);
                    frs.resultTable.setValueAt("---", i, 4);
                    frs.resultTable.setValueAt("---", i, 5);
                    frs.resultTable.setValueAt("---", i, 6);
                    frs.resultTable.setValueAt("3rd Examiner Marks need", i, 7);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }

    public String getCLGL(int cl, float gp) {
        try {
            resultset = statement.executeQuery("Select Letter_grade from grade where GRADE_POINT <=" + gp + " and Mark_ID=1 and Grade_Point > all( Select Grade_Point from grade where Grade_Point<=" + (gp - 0.25) + ")");
            if (resultset.next()) {
                CLGA[cl] = "" + resultset.getObject(1);
            }
        } catch (Exception ex) {
            System.out.println("Error3: " + ex);
        }
        return CLGA[cl];
    }

    public void finalResultShowStuSearch(int count, String content, FinalResultShow frS) {
        String s = null;
        DefaultTableModel model = (DefaultTableModel) frS.resultTable.getModel();

        for (int i = model.getRowCount() - 1; i >= 1; i--) {
            model.removeRow(i);
        }
        if (count == 0) {
            s = "STUDENT_NAME";
        } else if (count == 1) {
            s = "result_set.ID";
        }
        try {
            resultset = statement.executeQuery("Select result_set.ID, student_name, sum_tgp, total_cradit, gpa, result_set.cgpa, clga, comments from result_set, student_information where " + s + " Like '%" + content + "%' and result_set.batch = '" + getBatch() + "' and semester = " + getSemester() + " and result_set.ID=student_information.ID");
            metadata = resultset.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
            }
            //db.output.setText(db.output.getText() + "\n");
            int r = 0;
            while (resultset.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (frS.resultTable.getRowCount() <= r) {
                        model.addRow(new Object[]{""});
                    }
                    if (resultset.getObject(i) == "-1") {
                        frS.resultTable.setValueAt("---", r, i - 1);
                    } else if (resultset.getObject(i) == "TE") {
                        frS.resultTable.setValueAt("3rd Examiner Marks need", r, i - 1);
                    } else {
                        frS.resultTable.setValueAt(resultset.getObject(i), r, i - 1);
                    }
                }
                r++;
                //db.output.setText(db.output.getText() + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: 12" + ex);
        }
    }

    public void showReExamineList(ReExaminee reE) {
        DefaultTableModel model = (DefaultTableModel) reE.ViewTable.getModel();

        for (int i = model.getRowCount() - 1; i >= 1; i--) {
            model.removeRow(i);
        }

        for (int i = 0; i < number_of_courses; i++) {
            if (reE.ViewTable.getRowCount() <= i) {
                model.addRow(new Object[]{""});
            }
            reE.ViewTable.setValueAt(course_title[i], i, 0);
            reE.ViewTable.setValueAt(course_code[i], i, 1);
        }

        String s[] = new String[number_of_courses];
        for (int i = 0; i < s.length; i++) {
            s[i] = "";
        }
        for (int i = 0; i < number_of_students; i++) {
            for (int j = 0; j < number_of_courses; j++) {
                if (TotalMarks[i][j] == -1) {
                    s[j] += stu_roll[i] + ", ";
                    reE.ViewTable.setValueAt(s[j], j, 2);
                }
            }
        }
    }

    public int checkTEMarksInBatchSemester() {
        try {
            resultset = statement.executeQuery("Select ID from result_set where comments Like 'TE'");
            if (resultset.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            System.out.println("Error9: " + ex);
            return 0;
        }
    }

    public void showTEStuAndSub(String batch, String s, InsertTEMarks iTE) {
        int sem = Integer.parseInt(s);
        DefaultTableModel mod = (DefaultTableModel) iTE.markTable.getModel();
        try {
            resultset = statement.executeQuery("select count(e.ID) from exam_mark e, result_set r where e.id=r.id and r.batch='" + batch + "' and r.SEMESTER=" + sem + " and totalmark=-1");
            int size = 0;
            if (resultset.next()) {
                size = Integer.parseInt("" + resultset.getObject(1));
            }
            te_course_code = new String[size];
            te_course_title = new String[size];
            te_stu_id = new String[size];
            te_stu_name = new String[size];
            te_marks = new float[size];
            if (size == 0) {
                JOptionPane.showMessageDialog(null, "No more Re-Examine remains.");
                iTE.setVisible(false);
            }
        } catch (Exception ex) {
            System.out.println("Err: " + ex);
        }
        try {
            resultset = statement.executeQuery("select e.course_code, title, e.id, student_name from exam_mark e, student_information s, result_set r, courses c where r.id=e.id and c.course_code=e.course_code and e.id=s.ID and r.batch like '" + batch + "' and r.SEMESTER=" + sem + " and totalmark=-1.00");
            int k = 0;
            while (resultset.next()) {
                mod.addRow(new Object[]{""});
                for (int i = 1; i <= mod.getColumnCount() - 1; i++) {
                    if (i == 4) {
                        te_stu_name[k] = "" + resultset.getObject(i);
                        iTE.markTable.setValueAt(te_stu_name[k], k, i - 1);
                    } else if (i == 3) {
                        te_stu_id[k] = "" + resultset.getObject(i);
                        iTE.markTable.setValueAt(te_stu_id[k], k, i - 1);
                    } else if (i == 2) {
                        te_course_title[k] = "" + resultset.getObject(i);
                        iTE.markTable.setValueAt(te_course_title[k], k, i - 1);
                    } else if (i == 1) {
                        te_course_code[k] = "" + resultset.getObject(i);
                        iTE.markTable.setValueAt(te_course_code[k], k, i - 1);
                    }
                }
                k++;
            }
        } catch (Exception ex) {
            System.out.println("Error9: " + ex);
        }
    }

    public void saveTEMarksToDatabase(InsertTEMarks iTE, String batch, String sem) {
        DefaultTableModel mod = (DefaultTableModel) iTE.markTable.getModel();
        int pp = 1;
        for (int i = 0; i < mod.getRowCount(); i++) {
            String temp = "" + iTE.markTable.getValueAt(i, 4);
            try {
                te_marks[i] = Float.parseFloat("" + iTE.markTable.getValueAt(i, 4));
                iTE.info.setText("(" + pp + ") Result Calculated and updated that you have inserted!");
                pp++;
            } catch (Exception ex) {
                te_marks[i] = -1;
            }
        }

        for (int i = 0; i < te_marks.length; i++) {
            try {
                ips = connection.prepareStatement("Update  exam_mark set te=" + te_marks[i] + " where ID Like'" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("first: " + ex);
            }
            if (te_marks[i] != -1) {
                float cont_m = 0, fe = 0, se = 0, te = 0, gp = 0, fm = 0, tm = 0, avg = 0, sum_gp = 0, totCradit = 0, te_gpa = 0, te_cgpa = 0, te_sum_cgpa = 0, te_total_cradit = 0, te_real_cgpa = 0;
                String lg = "UK", te_clga = "UK", te_cmnt = "TE";
                try {
                    resultset = statement.executeQuery("Select cont_assment, FE, SE, TE from exam_mark where ID Like '" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                    if (resultset.next()) {
                        cont_m = Float.parseFloat("" + resultset.getObject(1));
                        System.out.println("cont_m: " + cont_m);
                        fe = Float.parseFloat("" + resultset.getObject(2));
                        System.out.println("fe: " + fe);
                        se = Float.parseFloat("" + resultset.getObject(3));
                        System.out.println("se: " + se);
                        te = Float.parseFloat("" + resultset.getObject(4));
                        System.out.println("te: " + te);
                    }
                } catch (Exception ex) {
                    System.out.println("1: " + ex);
                }
                float d1 = fe - te;
                float d2 = se - te;
                if (d1 < 0) {
                    d1 = (-1) * d1;
                }
                if (d2 < 0) {
                    d2 = (-1) * d2;
                }
                if (d1 < d2) {
                    fm = fe;
                } else {
                    fm = se;
                }
                avg = (fm + te) / 2;
                tm = avg + cont_m;

                try {
                    resultset = statement.executeQuery("Select Total_Mark from course_mark where course_code='" + te_course_code[i] + "'");
                    int v = 0;
                    if (resultset.next()) {
                        v = Integer.parseInt("" + resultset.getObject(1));
                    }
                    System.out.println("" + v);
                    resultset = statement.executeQuery("Select Letter_grade from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + tm + " and end_mark>=" + tm);
                    if (resultset.next()) {
                        lg = "" + resultset.getObject(1);
                    }

                    resultset = statement.executeQuery("Select Grade_point from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + tm + " and end_mark>=" + tm);
                    if (resultset.next()) {
                        gp = Float.parseFloat("" + resultset.getObject(1));
                    }
                    System.out.println("LG=" + lg + "\nGP=" + gp);
                } catch (Exception ex) {
                    System.out.println("Error3: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update  exam_mark set AVERAGE=" + avg + " where ID Like'" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("2: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update  exam_mark set FINALMARK=" + fm + " where ID Like'" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                    ips.executeUpdate();

                } catch (Exception ex) {
                    System.out.println("3: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update  exam_mark set TOTALMARK=" + tm + " where ID Like'" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                    ips.executeUpdate();

                } catch (Exception ex) {
                    System.out.println("4: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update  exam_mark set GP=" + gp + " where ID Like'" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("5: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update  exam_mark set LG='" + lg + "' where ID Like'" + te_stu_id[i] + "' and course_code Like '" + te_course_code[i] + "'");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("7: " + ex);
                }

                int semester = Integer.parseInt(sem);
                int value = -1;
                try {
                    resultset = statement.executeQuery("select count(totalmark) from exam_mark where id Like '" + te_stu_id[i] + "' and semester=" + semester + " and totalmark=-1");
                    if (resultset.next()) {
                        value = Integer.parseInt("" + resultset.getObject(1));
                    }
                } catch (Exception ex) {
                    System.out.println("ERR: " + ex);
                }
                System.out.println("Value");
                if (value == 0) {
                    try {
                        resultset = statement.executeQuery("select sum(gp*cradit) from exam_mark e, courses c where e.COURSE_CODE=c.course_code and id Like '" + te_stu_id[i] + "' and e.semester=" + semester + "");
                        if (resultset.next()) {
                            System.out.println("sum_gp= " + resultset.getObject(1));
                            sum_gp = Float.parseFloat("" + resultset.getObject(1));
                        }

                        resultset = statement.executeQuery("select total_cradit from result_set where id Like '" + te_stu_id[i] + "' and semester=" + semester + "");
                        if (resultset.next()) {
                            System.out.println("totCradit= " + resultset.getObject(1));
                            totCradit = Float.parseFloat("" + resultset.getObject(1));
                        }

                        /*resultset = statement.executeQuery("Select CRADIT from courses where course_code Like '" + te_course_code[i] + "'");
                         if (resultset.next()) {
                         gp = gp * Float.parseFloat("" + resultset.getObject(1));
                         }
                         sum_gp += gp;*/
                        te_gpa = sum_gp / totCradit;

                        resultset = statement.executeQuery("select sum(cgpa) from result_set where id='" + te_stu_id[i] + "' and semester<" + sem + "");
                        if (resultset.next()) {
                            System.out.println("te_sum_cgpa: " + resultset.getObject(1));
                            te_sum_cgpa = Float.parseFloat("" + resultset.getObject(1));
                        }
                    } catch (Exception ex) {
                        System.out.println("ERR--" + ex);
                    }

                    te_real_cgpa = te_sum_cgpa + te_gpa;
                    te_real_cgpa = te_real_cgpa / semester;


                    try {
                        resultset = statement.executeQuery("Select Letter_grade from grade where GRADE_POINT <=" + te_real_cgpa + " and Mark_ID=1 and Grade_Point > all( Select Grade_Point from grade where Grade_Point<=" + (te_real_cgpa - 0.25) + ")");
                        if (resultset.next()) {
                            te_clga = "" + resultset.getObject(1);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error3: " + ex);
                    }
                    try {
                        ips = connection.prepareStatement("Update  result_set set SUM_TGP=" + sum_gp + " where ID Like '" + te_stu_id[i] + "' and semester=" + semester);
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("8: " + ex);
                    }
                    try {
                        ips = connection.prepareStatement("Update  result_set set GPA=" + te_gpa + " where ID Like '" + te_stu_id[i] + "' and semester=" + semester);
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("9: " + ex);
                    }
                    try {
                        ips = connection.prepareStatement("Update  result_set set CGPA=" + te_real_cgpa + " where ID Like '" + te_stu_id[i] + "' and semester=" + semester);
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("10: " + ex);
                    }
                    if (te_real_cgpa < 2) {
                        te_cmnt = "NP";
                    } else {
                        te_cmnt = "P";
                    }
                    try {
                        ips = connection.prepareStatement("Update  result_set set COMMENTS = '" + te_cmnt + "' where ID Like '" + te_stu_id[i] + "' and semester=" + semester);
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("11: " + ex);
                    }
                    try {
                        ips = connection.prepareStatement("Update  result_set set CLGA = '" + te_clga + "' where ID Like '" + te_stu_id[i] + "' and semester=" + semester);
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("12: " + ex);
                    }

                    try {
                        ips = connection.prepareStatement("Update  student_information set CGPA = " + te_real_cgpa + " where ID Like '" + te_stu_id[i] + "'");
                        ips.executeUpdate();
                    } catch (Exception ex) {
                        System.out.println("12: " + ex);
                    }
                }
            }
        }

    }

    public void IMPMarkFirst(InsertImprovementMarks iim) {
        try {
            resultset = statement.executeQuery("Select distinct batch from result_set where semester > 0");
            while (resultset.next()) {
                iim.batchBox.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Err1: " + ex);
        }
    }

    public void IMPMarkBatch(InsertImprovementMarks iim, String batch) {
        try {
            resultset = statement.executeQuery("select ID from student_information where batch='" + batch + "'");
            while (resultset.next()) {
                iim.studentBox.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Err2: " + ex);
        }
    }

    public void IMPMarkStudent(InsertImprovementMarks iim, String batch) {
        try {
            resultset = statement.executeQuery("Select distinct semester from result_set where batch='" + batch + "' and semester>0");
            while (resultset.next()) {
                iim.semesterBox.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Err3: " + ex);
        }

    }

    public void IMPMarkSemester(InsertImprovementMarks iim, String sem) {
        try {
            int s = 0;
            try {
                s = Integer.parseInt(sem);
            } catch (Exception ex) {
                return;
            }
            resultset = statement.executeQuery("Select title from courses where semester=" + s);
            while (resultset.next()) {
                iim.subjectBox.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Err4: " + ex);
        }
    }

    public void checkInformationofIMPResults(String batch, String student, String sem, String sub, InsertImprovementMarks imp) {
        int iMPsemester = Integer.parseInt(sem);

        try {
            resultset = statement.executeQuery("select cont_assment, fe, se, te from exam_mark where id='" + student + "' and semester=" + iMPsemester + " and course_code = (select course_code from courses where title='" + sub + "')");

            if (resultset.next()) {
                if (!"-1.00".equals(resultset.getObject(1) + "")) {
                    imp.viewtable.setValueAt(resultset.getObject(1), 0, 0);
                    imp.infoLebel.setText("Marks : Cont. Assasment = Found, ");
                } else {
                    imp.infoLebel.setText("Marks : Cont. Assasment = Not Found, ");
                }
                if (!"-1.00".equals(resultset.getObject(2) + "")) {
                    imp.viewtable.setValueAt(resultset.getObject(2), 0, 1);
                    imp.infoLebel.setText(imp.infoLebel.getText() + "First Examine = Found, ");
                } else {
                    imp.infoLebel.setText(imp.infoLebel.getText() + "First Examine = Not Found, ");
                }
                if (!"-1.00".equals(resultset.getObject(3) + "")) {
                    imp.infoLebel.setText(imp.infoLebel.getText() + "Second Examine = Found, ");
                    imp.viewtable.setValueAt(resultset.getObject(3), 0, 2);
                } else {
                    imp.infoLebel.setText(imp.infoLebel.getText() + "Second Examine = Not Found, ");
                }
                if (!"-1.00".equals(resultset.getObject(4) + "")) {
                    imp.viewtable.setValueAt(resultset.getObject(4), 0, 3);
                    imp.infoLebel.setText(imp.infoLebel.getText() + "Third Examine = Found");
                } else {
                    imp.infoLebel.setText(imp.infoLebel.getText() + "Third Examine = Not Found");
                }

            }
        } catch (Exception ex) {
            System.out.println("Eror $: " + ex);
        }
    }

    public void calculateIMPResult(InsertImprovementMarks imp, String id, String s, String cours) {
        imp_stu_id = id;
        int sem = Integer.parseInt(s);
        imp_cont_a = Float.parseFloat("" + imp.viewtable.getValueAt(0, 0));
        try {
            imp_fe = Float.parseFloat("" + imp.viewtable.getValueAt(0, 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Correctly insert the First Examine number.");
            return;
        }
        try {
            imp_se = Float.parseFloat("" + imp.viewtable.getValueAt(0, 2));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Correctly insert the Second Examine number.");
            return;
        }
        try {
            imp_te = Float.parseFloat("" + imp.viewtable.getValueAt(0, 3));
        } catch (Exception ex) {
            imp_te = 0;
        }
        float dif = imp_fe - imp_se;
        if (dif < 0) {
            dif = (-1) * dif;
        }

        float imp_avg, imp_fm, imp_tm = 0, imp_tgpa, imp_cgpa, imp_gpa = 0, imp_sum_gp = 0, imp_tot_cradit = 0, imp_gp = 0, imp_sum_cgpa = 0, imp_real_cgpa = 0;
        String imp_clga = "UK", imp_lg = "UK";
        if (dif >= 12) {
            float dif1 = imp_te - imp_fe;
            float dif2 = imp_te - imp_se;
            if (dif1 < 0) {
                dif1 = (-1) * dif1;
            }
            if (dif2 < 0) {
                dif2 = (-1) * dif2;
            }

            if (dif1 < dif2) {
                imp_avg = (imp_te + imp_fe) / 2;
            } else {
                imp_avg = (imp_te + imp_se) / 2;
            }

            imp_fm = imp_avg;
            imp_tm = imp_cont_a + imp_avg;

            try {
                resultset = statement.executeQuery("Select Total_Mark from course_mark where course_code=(Select course_code from courses where title='" + cours + "')");
                int v = 0;
                if (resultset.next()) {
                    v = Integer.parseInt("" + resultset.getObject(1));
                }
                System.out.println("" + v);
                resultset = statement.executeQuery("Select Letter_grade from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + imp_tm + " and end_mark>=" + imp_tm);
                if (resultset.next()) {
                    imp_lg = "" + resultset.getObject(1);
                }

                resultset = statement.executeQuery("Select Grade_point from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + imp_tm + " and end_mark>=" + imp_tm);
                if (resultset.next()) {
                    imp_gp = Float.parseFloat("" + resultset.getObject(1));
                }
                System.out.println("LG=" + imp_lg + "\nGP=" + imp_gp);
            } catch (Exception ex) {
                System.out.println("Error3: " + ex);
            }


            float imp_sub_gp = 0, imp_sub_cradit = 0;
            try {
                resultset = statement.executeQuery("select sum_tgp from result_set where id='" + imp_stu_id + "' and semester=" + sem + "");
                if (resultset.next()) {
                    System.out.println("sum_gp= " + resultset.getObject(1));
                    imp_sum_gp = Float.parseFloat("" + resultset.getObject(1));
                }

                resultset = statement.executeQuery("select gp from exam_mark where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                if (resultset.next()) {
                    imp_sub_gp = Float.parseFloat("" + resultset.getObject(1));
                }

                resultset = statement.executeQuery("select cradit from courses where title='" + cours + "'");
                if (resultset.next()) {
                    imp_sub_cradit = Float.parseFloat("" + resultset.getObject(1));
                }

                imp_sum_gp = imp_sum_gp - imp_sub_gp * imp_sub_cradit;

                resultset = statement.executeQuery("select total_cradit from result_set where id Like '" + imp_stu_id + "' and semester=" + sem + "");
                if (resultset.next()) {
                    System.out.println("totCradit= " + resultset.getObject(1));
                    imp_tot_cradit = Float.parseFloat("" + resultset.getObject(1));
                }

                float value_gp = 0;
                resultset = statement.executeQuery("Select CRADIT from courses where title='" + cours + "'");
                if (resultset.next()) {
                    value_gp = imp_gp * Float.parseFloat("" + resultset.getObject(1));
                }
                imp_sum_gp += value_gp;
                imp_gpa = imp_sum_gp / imp_tot_cradit;

                resultset = statement.executeQuery("select sum(cgpa) from result_set where id='" + imp_stu_id + "' and semester != " + sem + "");
                if (resultset.next()) {
                    System.out.println("te_sum_cgpa: " + resultset.getObject(1));
                    imp_sum_cgpa = Float.parseFloat("" + resultset.getObject(1));
                }
            } catch (Exception ex) {
                System.out.println("ERR--" + ex);
            }

            imp_real_cgpa = imp_sum_cgpa + imp_gpa;
            imp_real_cgpa = imp_real_cgpa / sem;

            try {
                resultset = statement.executeQuery("Select Letter_grade from grade where GRADE_POINT <=" + imp_real_cgpa + " and Mark_ID=1 and Grade_Point > all( Select Grade_Point from grade where Grade_Point<=" + (imp_real_cgpa - 0.25) + ")");
                if (resultset.next()) {
                    imp_clga = "" + resultset.getObject(1);
                }
            } catch (Exception ex) {
                System.out.println("Error3: " + ex);
            }

            float rtm = 0;

            try {
                resultset = statement.executeQuery("select totalmark from exam_mark where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                if (resultset.next()) {
                    rtm = Float.parseFloat("" + resultset.getObject(1));
                }
            } catch (Exception ex) {
                System.out.println("Errrrr: " + ex);
            }

            if (rtm > imp_tm) {
                //No Update

                imp.impLebel.setText("ID : " + imp_stu_id + " result is not improved");
            } else {
                //Update
                try {
                    ips = connection.prepareStatement("Update exam_mark set FE=" + imp_fe + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTYFE: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set SE=" + imp_se + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTYSE: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set DIFF=" + dif + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTYDIFF: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set AVERAGE=" + imp_avg + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTY: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set TE=" + imp_te + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTYTE: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set FINALMARK=" + imp_fm + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTY: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set TOTALMARK=" + imp_tm + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTY: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set GP=" + imp_gp + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTY: " + ex);
                }

                try {
                    ips = connection.prepareStatement("Update exam_mark set LG=" + imp_lg + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("ErrrTY: " + ex);
                }


                try {
                    ips = connection.prepareStatement("Update  result_set set SUM_TGP=" + imp_sum_gp + " where ID Like '" + imp_stu_id + "' and semester=" + sem);
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("8: " + ex);
                }
                try {
                    ips = connection.prepareStatement("Update  result_set set GPA=" + imp_gpa + " where ID Like '" + imp_stu_id + "' and semester=" + sem);
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("9: " + ex);
                }
                try {
                    ips = connection.prepareStatement("Update  result_set set CGPA=" + imp_real_cgpa + " where ID Like '" + imp_stu_id + "' and semester=" + sem);
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("10: " + ex);
                }


                try {
                    ips = connection.prepareStatement("Update  student_information set CLGA = " + imp_clga + " where ID Like '" + imp_stu_id + "'");
                    ips.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("12: " + ex);
                }

                imp.impLebel.setText("ID : " + imp_stu_id + " result is improved");
            }

        } else {
            imp_avg = (imp_fe + imp_se) / 2;
            imp_fm = imp_avg;
            imp_tm = imp_cont_a + imp_avg;

            try {
                resultset = statement.executeQuery("Select Total_Mark from course_mark where course_code=(Select course_code from courses where title='" + cours + "')");
                int v = 0;
                if (resultset.next()) {
                    v = Integer.parseInt("" + resultset.getObject(1));
                }
                System.out.println("" + v);
                resultset = statement.executeQuery("Select Letter_grade from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + imp_tm + " and end_mark>=" + imp_tm);
                if (resultset.next()) {
                    imp_lg = "" + resultset.getObject(1);
                }

                resultset = statement.executeQuery("Select Grade_point from grade, grade_marks where grade.Mark_ID=grade_marks.Mark_ID and Total_Mark=" + v + " and start_mark<=" + imp_tm + " and end_mark>=" + imp_tm);
                if (resultset.next()) {
                    imp_gp = Float.parseFloat("" + resultset.getObject(1));
                }
                System.out.println("LG=" + imp_lg + "\nGP=" + imp_gp);
            } catch (Exception ex) {
                System.out.println("Error3: " + ex);
            }


            float imp_sub_gp = 0, imp_sub_cradit = 0;
            try {
                resultset = statement.executeQuery("select sum_tgp from result_set where id='" + imp_stu_id + "' and semester=" + sem + "");
                if (resultset.next()) {
                    System.out.println("sum_gp= " + resultset.getObject(1));
                    imp_sum_gp = Float.parseFloat("" + resultset.getObject(1));
                }

                resultset = statement.executeQuery("select gp from exam_mark where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                if (resultset.next()) {
                    imp_sub_gp = Float.parseFloat("" + resultset.getObject(1));
                }

                resultset = statement.executeQuery("select cradit from courses where title='" + cours + "'");
                if (resultset.next()) {
                    imp_sub_cradit = Float.parseFloat("" + resultset.getObject(1));
                }

                imp_sum_gp = imp_sum_gp - imp_sub_gp * imp_sub_cradit;

                resultset = statement.executeQuery("select total_cradit from result_set where id Like '" + imp_stu_id + "' and semester=" + sem + "");
                if (resultset.next()) {
                    System.out.println("totCradit= " + resultset.getObject(1));
                    imp_tot_cradit = Float.parseFloat("" + resultset.getObject(1));
                }

                float value_gp = 0;
                resultset = statement.executeQuery("Select CRADIT from courses where title='" + cours + "'");
                if (resultset.next()) {
                    value_gp = imp_gp * Float.parseFloat("" + resultset.getObject(1));
                }
                imp_sum_gp += value_gp;
                imp_gpa = imp_sum_gp / imp_tot_cradit;

                resultset = statement.executeQuery("select sum(cgpa) from result_set where id='" + imp_stu_id + "' and semester != " + sem + "");
                if (resultset.next()) {
                    System.out.println("te_sum_cgpa: " + resultset.getObject(1));
                    imp_sum_cgpa = Float.parseFloat("" + resultset.getObject(1));
                }
            } catch (Exception ex) {
                System.out.println("ERR--" + ex);
            }

            imp_real_cgpa = imp_sum_cgpa + imp_gpa;
            imp_real_cgpa = imp_real_cgpa / sem;

            try {
                resultset = statement.executeQuery("Select Letter_grade from grade where GRADE_POINT <=" + imp_real_cgpa + " and Mark_ID=1 and Grade_Point > all( Select Grade_Point from grade where Grade_Point<=" + (imp_real_cgpa - 0.25) + ")");
                if (resultset.next()) {
                    imp_clga = "" + resultset.getObject(1);
                }
            } catch (Exception ex) {
                System.out.println("Error3: " + ex);
            }



        }

        float rtm = 0;

        try {
            resultset = statement.executeQuery("select totalmark from exam_mark where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
            if (resultset.next()) {
                rtm = Float.parseFloat("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Errrrr: " + ex);
        }

        if (rtm > imp_tm) {
            //No Update
            imp.impLebel.setText("ID : " + imp_stu_id + " result is not improved");
        } else {
            //Update

            try {
                ips = connection.prepareStatement("Update exam_mark set FE=" + imp_fe + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTYFE: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set SE=" + imp_se + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTYSE: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set DIFF=" + dif + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTYDIFF: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set AVERAGE=" + imp_avg + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTYX: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set TE=" + imp_te + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTYTE: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set FINALMARK=" + imp_fm + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTY: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set TOTALMARK=" + imp_tm + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTY: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set GP=" + imp_gp + " where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTY: " + ex);
            }

            try {
                ips = connection.prepareStatement("Update exam_mark set LG='" + imp_lg + "' where id='" + imp_stu_id + "' and semester=" + sem + " and course_code=(select course_code from courses where title='" + cours + "')");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("ErrrTY: " + ex);
            }


            try {
                ips = connection.prepareStatement("Update  result_set set SUM_TGP=" + imp_sum_gp + " where ID Like '" + imp_stu_id + "' and semester=" + sem);
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("8: " + ex);
            }
            try {
                ips = connection.prepareStatement("Update  result_set set GPA=" + imp_gpa + " where ID Like '" + imp_stu_id + "' and semester=" + sem);
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("9: " + ex);
            }
            try {
                ips = connection.prepareStatement("Update  result_set set CGPA=" + imp_real_cgpa + " where ID Like '" + imp_stu_id + "' and semester=" + sem);
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("10: " + ex);
            }


            try {
                ips = connection.prepareStatement("Update  student_information set CGPA = " + imp_real_cgpa + " where ID Like '" + imp_stu_id + "'");
                ips.executeUpdate();
            } catch (Exception ex) {
                System.out.println("12: " + ex);
            }

            imp.impLebel.setText("ID : " + imp_stu_id + " result is improved");
        }
    }

    public void viewResultGetBatch(StartHere st) {
        try {
            resultset = statement.executeQuery("select distinct batch from result_set where semester>0");
            while (resultset.next()) {
                st.viewResultBatchBox.addItem("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("vEr: " + ex);
        }
    }

    public void viewResultGetSemester(StartHere st) {
        try {
            resultset = statement.executeQuery("select distinct semester from result_set where semester>0 and batch='" + st.viewResultBatchBox.getSelectedItem() + "'");

            while (resultset.next()) {
                st.viewResultSemesterBox.addItem("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("vEr: " + ex);
        }
    }

    public void viewResultinFrame(ViewResultFrame vrf, String batch, String s) {
        int sem = Integer.parseInt(s);
        try {
            DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
            resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + "");
            int k = 0;
            while (resultset.next()) {
                d.addRow(new Object[]{""});
                vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                if (!"-1.00".equals("" + resultset.getObject(5))) {
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                } else {
                    vrf.viewResultTable.setValueAt("---", k, 4);
                }
                if (!"-1.00".equals("" + resultset.getObject(6))) {
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                } else {
                    vrf.viewResultTable.setValueAt("---", k, 5);
                }
                vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                k++;
            }
        } catch (Exception ex) {
            System.out.println("Err");
        }

    }

    public void viewResultSearchByID(ViewResultFrame vrf, String batch, String s, String id) {
        int sem = Integer.parseInt(s);
        try {
            DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
            resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%'");
            int k = 0;
            while (resultset.next()) {
                d.addRow(new Object[]{""});
                vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                k++;
            }
        } catch (Exception ex) {
            System.out.println("Err");
        }
    }

    public void viewResultSearchByName(ViewResultFrame vrf, String batch, String s, String name) {
        int sem = Integer.parseInt(s);
        try {
            DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
            resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + name + "%'");
            int k = 0;
            while (resultset.next()) {
                d.addRow(new Object[]{""});
                vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                k++;
            }
        } catch (Exception ex) {
            System.out.println("Err");
        }
    }

    public void viewResultByASC(ViewResultFrame vrf, String batch, String s, String id, String name, int num) {
        int sem = Integer.parseInt(s);
        if (num == 0) {
            try {
                DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%' and student_name Like '%" + name + "%' order by id asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 1) {
            try {
                DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%' and student_name Like '%" + name + "%' order by student_name asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 2) {
            try {
                DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%' and student_name Like '%" + name + "%' order by r.cgpa asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        }
    }

    public void viewResultByDESC(ViewResultFrame vrf, String batch, String s, String id, String name, int num) {
        int sem = Integer.parseInt(s);
        if (num == 0) {
            try {
                DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%' and student_name Like '%" + name + "%' order by id desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 1) {
            try {
                DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%' and student_name Like '%" + name + "%' order by student_name desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 2) {
            try {
                DefaultTableModel d = (DefaultTableModel) vrf.viewResultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and r.id Like '%" + id + "%' and student_name Like '%" + name + "%' order by r.cgpa desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    vrf.viewResultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        }
    }

    public void finalResultByAsc(FinalResultShow frs, String batch, String s, int num, String value, String con) {
        int sem = Integer.parseInt(s);
        if (num == 0) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by id asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 1) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by student_information asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 2) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by gpa asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 3) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by r.cgpa asc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        }
    }

    public void finalResultByDesc(FinalResultShow frs, String batch, String s, int num, String value, String con) {
        int sem = Integer.parseInt(s);
        if (num == 0) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by id desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 1) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by student_information desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 2) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by gpa desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        } else if (num == 3) {
            try {
                DefaultTableModel d = (DefaultTableModel) frs.resultTable.getModel();
                resultset = statement.executeQuery("select s.id, s.student_name, sum_tgp, total_cradit, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and r.batch='" + batch + "' and semester=" + sem + " and " + value + " Like '%" + con + "%' order by r.cgpa desc");
                int k = 0;
                while (resultset.next()) {
                    d.addRow(new Object[]{""});
                    frs.resultTable.setValueAt("" + resultset.getObject(1), k, 0);
                    frs.resultTable.setValueAt("" + resultset.getObject(2), k, 1);
                    frs.resultTable.setValueAt("" + resultset.getObject(3), k, 2);
                    frs.resultTable.setValueAt("" + resultset.getObject(4), k, 3);
                    frs.resultTable.setValueAt("" + resultset.getObject(5), k, 4);
                    frs.resultTable.setValueAt("" + resultset.getObject(6), k, 5);
                    frs.resultTable.setValueAt("" + resultset.getObject(7), k, 6);
                    frs.resultTable.setValueAt("" + resultset.getObject(8), k, 7);
                    k++;
                }
            } catch (Exception ex) {
                System.out.println("Err");
            }
        }
    }

    public void viewResultRefreshTable(ViewResultFrame vrf) {
        DefaultTableModel dtm = (DefaultTableModel) vrf.viewResultTable.getModel();
        for (int i = 0; i <= dtm.getRowCount(); i++) {
            dtm.removeRow(0);
        }
    }

    public void finalResultRefreshTable(FinalResultShow frs) {
        DefaultTableModel dtm = (DefaultTableModel) frs.resultTable.getModel();
        for (int i = 0; i <= dtm.getRowCount(); i++) {
            dtm.removeRow(0);
        }
    }
    //Creating necessary files......
    public String ne_stu_id[];
    public String ne_stu_name[];
    public String ne_sum_tgp[];
    public float ne_gpa[];
    public String ne_lg[];
    public String ne_cgpa[];
    public String ne_clga[];
    public String ne_comments[];
    public String ne_course_title[];
    public String ne_course_code[];
    public String ne_course_cradit[];
    public String ne_sem;
    public String ne_batch;
    public int ne_count_course = 0;
    public int ne_num_stu = 0;

    public void resultSheet(String batch, String s) {
        ne_batch = batch;
        ne_sem = s;
        try {
            resultset = statement.executeQuery("Select count(course_code) from courses where semester=" + ne_sem);
            if (resultset.next()) {
                ne_count_course = Integer.parseInt("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("1 " + ex);
        }

        ne_course_code = new String[ne_count_course];
        ne_course_title = new String[ne_count_course];
        ne_course_cradit = new String[ne_count_course];


        try {
            resultset = statement.executeQuery("Select course_code, title, cradit from courses where semester=" + ne_sem);
            int k = 0;
            while (resultset.next()) {
                ne_course_code[k] = "" + resultset.getObject(1);
                ne_course_title[k] = "" + resultset.getObject(2);
                ne_course_cradit[k] = "" + resultset.getObject(3);
                k++;
            }
        } catch (Exception ex) {
            System.out.println("2 " + ex);
        }

        try {
            resultset = statement.executeQuery("Select count(id) from result_set where batch='" + ne_batch + "' and semester=" + ne_sem);
            if (resultset.next()) {
                ne_num_stu = Integer.parseInt("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("3 " + ex);
        }

        ne_stu_id = new String[ne_num_stu];
        ne_stu_name = new String[ne_num_stu];
        ne_sum_tgp = new String[ne_num_stu];
        ne_gpa = new float[ne_num_stu];
        ne_lg = new String[ne_num_stu];
        ne_cgpa = new String[ne_num_stu];
        ne_clga = new String[ne_num_stu];
        ne_comments = new String[ne_num_stu];


        System.out.println(ne_batch + " " + ne_sem);
        try {
            resultset = statement.executeQuery("Select r.id, s.student_name, sum_tgp, gpa, r.cgpa, clga, comments from result_set r, student_information s where r.id=s.id and semester=" + ne_sem + " and s.batch='" + ne_batch + "'");
            int j = 0;
            while (resultset.next()) {
                ne_stu_id[j] = "" + resultset.getObject(1);
                ne_stu_name[j] = "" + resultset.getObject(2);
                ne_sum_tgp[j] = "" + resultset.getObject(3);
                ne_gpa[j] = Float.parseFloat("" + resultset.getObject(4));
                ne_cgpa[j] = "" + resultset.getObject(5);
                ne_clga[j] = "" + resultset.getObject(6);
                ne_comments[j] = "" + resultset.getObject(7);
                System.out.println(ne_stu_id[j] + " " + ne_stu_name[j] + " " + ne_sum_tgp[j] + " " + ne_gpa[j] + " " + ne_cgpa[j] + " " + ne_clga[j] + " " + ne_comments[j] + " ");

                j++;
            }
        } catch (Exception ex) {
            System.out.println("4 " + ex);
        }

        try {
            WritableWorkbook wb = Workbook.createWorkbook(new File("D://Result Processing System/Batch " + batch + " semester " + ne_sem + " Result Sheet.xls"));
            WritableSheet sheet = wb.createSheet("Result SHEET", 0);

            sheet.mergeCells(0, 0, 1, 2);
            Label label = new Label(0, 0, "ID/Roll no.");
            sheet.addCell(label);

            sheet.mergeCells(2, 0, 3, 2);
            label = new Label(2, 0, "Name");
            sheet.addCell(label);


            for (int i = 3; i < ne_num_stu + 3; i++) {
                sheet.mergeCells(0, i, 1, i);
                label = new Label(0, i, "" + ne_stu_id[i - 3]);
                sheet.addCell(label);

                sheet.mergeCells(2, i, 3, i);
                label = new Label(2, i, "" + ne_stu_name[i - 3]);
                sheet.addCell(label);
            }
            int k = 0;
            for (int i = 0; i < ne_count_course; i++) {
                sheet.mergeCells(4 + k, 0, 5 + k, 0);
                label = new Label(4 + k, 0, "" + ne_course_title[i]);
                sheet.addCell(label);

                sheet.mergeCells(4 + k, 1, 5 + k, 1);
                label = new Label(4 + k, 1, "" + ne_course_code[i]);
                sheet.addCell(label);

                label = new Label(4 + k, 2, "GP");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + ne_course_code[i]);
                        resultset = statement.executeQuery("select gp from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + ne_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(4 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }


                label = new Label(5 + k, 2, "LG");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + ne_course_code[i]);
                        resultset = statement.executeQuery("select lg from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + ne_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(5 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }

                k = k + 2;
            }

            k = k - 1 + 5;

            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "Total Cradit");
            sheet.addCell(label);

            for (int p = 0; p < ne_num_stu; p++) {
                try {
                    resultset = statement.executeQuery("select distinct total_cradit from result_set where batch='" + ne_batch + "' and semester=" + ne_sem + "");
                    if (resultset.next()) {
                        label = new Label(k, p + 3, "" + resultset.getObject(1));
                        sheet.addCell(label);
                    }
                } catch (Exception ex) {
                    System.out.println("Er: 10" + ex);
                }
            }

            k++;
            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "Sum TGP");
            sheet.addCell(label);
            for (int q = 0; q < ne_num_stu; q++) {
                label = new Label(k, q + 3, "" + ne_sum_tgp[q]);
                sheet.addCell(label);
            }

            k++;
            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "GPA");
            sheet.addCell(label);
            for (int q = 0; q < ne_num_stu; q++) {
                label = new Label(k, q + 3, "" + ne_gpa[q]);
                sheet.addCell(label);
            }

            k++;
            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "Letter Grade(LG)");
            sheet.addCell(label);
            for (int q = 0; q < ne_num_stu; q++) {
                try {
                    resultset = statement.executeQuery("Select Letter_grade from grade where GRADE_POINT <=" + ne_gpa[q] + " and Mark_ID=1 and Grade_Point > all( Select Grade_Point from grade where Grade_Point<=" + (ne_gpa[q] - 0.25) + ")");
                    if (resultset.next()) {
                        label = new Label(k, q + 3, "" + resultset.getObject(1));
                        ne_lg[q] = "" + resultset.getObject(1);
                        sheet.addCell(label);
                    }
                } catch (Exception ex) {
                    System.out.println("Err " + ex);
                }
            }


            k++;
            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "CGPA");
            sheet.addCell(label);
            for (int q = 0; q < ne_num_stu; q++) {
                label = new Label(k, q + 3, "" + ne_cgpa[q]);
                sheet.addCell(label);
            }

            k++;
            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "CLGA");
            sheet.addCell(label);
            for (int q = 0; q < ne_num_stu; q++) {
                label = new Label(k, q + 3, "" + ne_clga[q]);
                sheet.addCell(label);
            }

            k++;
            sheet.mergeCells(k, 0, k, 2);
            label = new Label(k, 0, "Comments");
            sheet.addCell(label);
            for (int q = 0; q < ne_num_stu; q++) {
                label = new Label(k, q + 3, "" + ne_comments[q]);
                sheet.addCell(label);
            }

            wb.write();
            wb.close();
        } catch (Exception ex) {
            System.out.println("Exc: " + ex);
        }

    }
    public String avg_course_code[];
    public String avg_course_title[];
    public int avg_count_course = 0;

    public void averagerSheet(String batch, String sem) {
        try {
            WritableWorkbook wb = Workbook.createWorkbook(new File("D://Result Processing System/Batch " + batch + " semester " + ne_sem + " average sheet.xls"));
            WritableSheet sheet = wb.createSheet("Avg. SHEET", 0);

            sheet.mergeCells(0, 0, 1, 2);
            Label label = new Label(0, 0, "ID/Roll no.");
            sheet.addCell(label);

            sheet.mergeCells(2, 0, 3, 2);
            label = new Label(2, 0, "Name");
            sheet.addCell(label);

            for (int i = 3; i < ne_num_stu + 3; i++) {
                sheet.mergeCells(0, i, 1, i);
                label = new Label(0, i, "" + ne_stu_id[i - 3]);
                sheet.addCell(label);

                sheet.mergeCells(2, i, 3, i);
                label = new Label(2, i, "" + ne_stu_name[i - 3]);
                sheet.addCell(label);
            }

            try {
                resultset = statement.executeQuery("select count (course_code) from courses where marking_type='NORMAL' and semester=" + ne_sem + "");
                if (resultset.next()) {
                    avg_count_course = Integer.parseInt("" + resultset.getObject(1));
                }
            } catch (Exception ex) {
                System.out.println("Err1" + ex);
            }

            avg_course_code = new String[avg_count_course];
            avg_course_title = new String[avg_count_course];

            try {
                resultset = statement.executeQuery("select course_code, title from courses where marking_type='NORMAL' and semester=" + ne_sem + "");
                int j = 0;
                while (resultset.next()) {
                    avg_course_code[j] = "" + resultset.getObject(1);
                    avg_course_title[j] = "" + resultset.getObject(2);
                    j++;
                }
            } catch (Exception ex) {
                System.out.println("Er:2 " + ex);
            }

            int k = 0;
            for (int i = 0; i < avg_count_course; i++) {
                sheet.mergeCells(k + 4, 0, k + 8, 0);
                label = new Label(k + 4, 0, "" + avg_course_title[i]);
                sheet.addCell(label);

                sheet.mergeCells(k + 4, 1, k + 8, 1);
                label = new Label(k + 4, 1, "" + avg_course_code[i]);
                sheet.addCell(label);

                label = new Label(4 + k, 2, "1st Examiner");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + avg_course_code[i]);
                        resultset = statement.executeQuery("select fe from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + avg_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(4 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }


                label = new Label(5 + k, 2, "2nd Examiner");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + avg_course_code[i]);
                        resultset = statement.executeQuery("select se from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + avg_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(5 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }

                label = new Label(6 + k, 2, "3rd Examiner");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + avg_course_code[i]);
                        resultset = statement.executeQuery("select te from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + avg_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(6 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }

                label = new Label(7 + k, 2, "Difference");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + avg_course_code[i]);
                        resultset = statement.executeQuery("select diff from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + avg_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(7 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }

                label = new Label(8 + k, 2, "Average");
                sheet.addCell(label);
                for (int p = 0; p < ne_num_stu; p++) {
                    try {
                        System.out.println(ne_stu_id[p] + " " + avg_course_code[i]);
                        resultset = statement.executeQuery("select average from exam_mark where id='" + ne_stu_id[p] + "' and course_code='" + avg_course_code[i] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(8 + k, p + 3, "" + resultset.getObject(1));
                            System.out.print(" " + resultset.getObject(1) + "\n");
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("er: " + ex);
                    }
                }


                k = k + 5;
            }

            wb.write();
            wb.close();
        } catch (Exception ex) {
            System.out.println("ERRR: " + ex);
        }
    }

    public void personalResultSheet() {
        for (int i = 0; i < ne_num_stu; i++) {
            try {
                WritableWorkbook wb = Workbook.createWorkbook(new File("D://Result Processing System/ID " + ne_stu_id[i] + " semester " + ne_sem + " result sheet.xls"));
                WritableSheet sheet = wb.createSheet("" + ne_stu_id[i], 0);

                sheet.mergeCells(0, 0, 1, 0);
                Label label = new Label(0, 0, "ID/Roll no.");
                sheet.addCell(label);

                sheet.mergeCells(2, 0, 6, 0);
                label = new Label(2, 0, "" + ne_stu_id[i]);
                sheet.addCell(label);

                sheet.mergeCells(7, 0, 8, 0);
                label = new Label(7, 0, "Name");
                sheet.addCell(label);

                sheet.mergeCells(9, 0, 14, 0);
                label = new Label(9, 0, "" + ne_stu_name[i]);
                sheet.addCell(label);


                label = new Label(0, 1, "Serial");
                sheet.addCell(label);

                sheet.mergeCells(1, 1, 3, 1);
                label = new Label(1, 1, "Course Title");
                sheet.addCell(label);

                sheet.mergeCells(4, 1, 5, 1);
                label = new Label(4, 1, "Course Code");
                sheet.addCell(label);


                sheet.mergeCells(6, 1, 6, 1);
                label = new Label(6, 1, "Cradit");
                sheet.addCell(label);

                sheet.mergeCells(7, 1, 8, 1);
                label = new Label(7, 1, "Cont. Ass.");
                sheet.addCell(label);

                sheet.mergeCells(9, 1, 10, 1);
                label = new Label(9, 1, "Final");
                sheet.addCell(label);

                sheet.mergeCells(11, 1, 12, 1);
                label = new Label(11, 1, "Total Mark");
                sheet.addCell(label);

                sheet.mergeCells(13, 1, 13, 1);
                label = new Label(13, 1, "GP");
                sheet.addCell(label);

                sheet.mergeCells(14, 1, 14, 1);
                label = new Label(14, 1, "LG");
                sheet.addCell(label);


                for (int p = 0; p < ne_count_course; p++) {
                    label = new Label(0, p + 2, "" + (p + 1));
                    sheet.addCell(label);

                    sheet.mergeCells(1, p + 2, 3, p + 2);
                    label = new Label(1, p + 2, ne_course_title[p]);
                    sheet.addCell(label);

                    sheet.mergeCells(4, p + 2, 5, p + 2);
                    label = new Label(4, p + 2, ne_course_code[p]);
                    sheet.addCell(label);

                    sheet.mergeCells(6, p + 2, 6, p + 2);
                    label = new Label(6, p + 2, ne_course_cradit[p]);
                    sheet.addCell(label);

                    sheet.mergeCells(7, p + 2, 8, p + 2);
                    try {
                        resultset = statement.executeQuery("select cont_assment from exam_mark where id='" + ne_stu_id[i] + "' and course_code='" + ne_course_code[p] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(7, p + 2, "" + resultset.getObject(1));
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("Err::" + ex);
                    }


                    sheet.mergeCells(9, p + 2, 10, p + 2);
                    try {
                        resultset = statement.executeQuery("select FINALMARK from exam_mark where id='" + ne_stu_id[i] + "' and course_code='" + ne_course_code[p] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(9, p + 2, "" + resultset.getObject(1));
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("Err::" + ex);
                    }

                    sheet.mergeCells(11, p + 2, 12, p + 2);
                    try {
                        resultset = statement.executeQuery("select TOTALMARK from exam_mark where id='" + ne_stu_id[i] + "' and course_code='" + ne_course_code[p] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(11, p + 2, "" + resultset.getObject(1));
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("Err::" + ex);
                    }

                    sheet.mergeCells(13, p + 2, 13, p + 2);
                    try {
                        resultset = statement.executeQuery("select GP from exam_mark where id='" + ne_stu_id[i] + "' and course_code='" + ne_course_code[p] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(13, p + 2, "" + resultset.getObject(1));
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("Err::" + ex);
                    }

                    sheet.mergeCells(14, p + 2, 14, p + 2);
                    try {
                        resultset = statement.executeQuery("select LG from exam_mark where id='" + ne_stu_id[i] + "' and course_code='" + ne_course_code[p] + "' and semester=" + ne_sem + "");
                        if (resultset.next()) {
                            label = new Label(14, p + 2, "" + resultset.getObject(1));
                            sheet.addCell(label);
                        }
                    } catch (Exception ex) {
                        System.out.println("Err::" + ex);
                    }


                }

                sheet.mergeCells(0, ne_count_course + 3, 3, ne_count_course + 3);
                label = new Label(0, ne_count_course + 3, "Semester Grade Point (GPA)");
                sheet.addCell(label);

                sheet.mergeCells(4, ne_count_course + 3, 5, ne_count_course + 3);
                label = new Label(4, ne_count_course + 3, "" + ne_gpa[i]);
                sheet.addCell(label);

                sheet.mergeCells(6, ne_count_course + 3, 9, ne_count_course + 3);
                label = new Label(6, ne_count_course + 3, "Semester Letter Grade (LG)");
                sheet.addCell(label);


                sheet.mergeCells(10, ne_count_course + 3, 10, ne_count_course + 3);
                label = new Label(10, ne_count_course + 3, ne_lg[i]);
                sheet.addCell(label);



                sheet.mergeCells(11, ne_count_course + 3, 12, ne_count_course + 3);
                label = new Label(11, ne_count_course + 3, "Result");
                sheet.addCell(label);


                sheet.mergeCells(13, ne_count_course + 3, 14, ne_count_course + 3);
                if ("P".equals(ne_comments[i])) {
                    label = new Label(13, ne_count_course + 3, "PASSED");
                    sheet.addCell(label);
                } else {
                    label = new Label(13, ne_count_course + 3, "FAILED");
                    sheet.addCell(label);
                }



                wb.write();
                wb.close();

            } catch (Exception ex) {
            }
        }
    }

    public void preMark(MarksInserter mark, int c_count) {
        DefaultTableModel mod = (DefaultTableModel) mark.markTable.getModel();
        System.out.println(" mod.geRC " + mod.getRowCount());
        for (int i = 0; i < mod.getRowCount(); i++) {
            System.out.println("i=" + i + " mod.geRC " + mod.getRowCount());
            try {

                mod.setValueAt("" + continous_assessment[i][c_count], i, 2);
                System.out.println("--C_A " + continous_assessment[i][c_count]);

                mod.setValueAt("" + FE[i][c_count], i, 3);
                System.out.println("--FE " + FE[i][c_count]);

                //mod.setValueAt("55", i, 3);
                mod.setValueAt("" + SE[i][c_count], i, 4);
                System.out.println("--SE " + SE[i][c_count]);
                if (TE[i][c_count] == -1) {
                    mod.setValueAt("", i, 5);
                } else if (TE[i][c_count] == 0) {
                    mod.setValueAt("", i, 5);
                } else if ("" + TE[i][c_count] == null) {
                    mod.setValueAt("", i, 5);
                } else if ("".equals("" + TE[i][c_count])) {
                    mod.setValueAt("", i, 5);
                } else {
                    mod.setValueAt("" + TE[i][c_count], i, 5);
                }
            } catch (Exception ex) {
                System.out.println("Err" + ex);
            }
        }
    }

    public void preLab(MarksInserter mark, int c_count) {
        DefaultTableModel mod = (DefaultTableModel) mark.labTable.getModel();
        System.out.println(" mod.geRC " + mod.getRowCount());
        for (int i = 0; i < mod.getRowCount(); i++) {
            System.out.println("i=" + i + " mod.geRC " + mod.getRowCount());
            try {

                mod.setValueAt("" + continous_assessment[i][c_count], i, 2);

                mod.setValueAt("" + FE[i][c_count], i, 3);
            } catch (Exception ex) {
                System.out.println("Err" + ex);
            }
        }
    }

    public void preViva(MarksInserter mark, int c_count) {
        DefaultTableModel mod = (DefaultTableModel) mark.vivaTable.getModel();
        System.out.println(" mod.geRC " + mod.getRowCount());
        for (int i = 0; i < mod.getRowCount(); i++) {
            System.out.println("i=" + i + " mod.geRC " + mod.getRowCount());
            try {
                mod.setValueAt("" + FinalMarks[i][c_count], i, 2);
            } catch (Exception ex) {
                System.out.println("Err" + ex);
            }
        }
    }

    public void checkPassword(StartHere st) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                st.passChecker = true;
            } else {
                st.passChecker = false;
            }
        } catch (Exception ex) {
        }
    }

    public void matchPassword(StartHere st, String s) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                if (s.equals("" + resultset.getObject(1))) {
                    st.passwordField.setText("");
                    st.passInfoLabel.setVisible(true);
                    st.passInfoLabel.setText("");
                    st.passInfoLabel.setForeground(Color.BLACK);
                    st.goAdminPanel();
                } else {
                    st.passInfoLabel.setVisible(true);
                    st.passInfoLabel.setForeground(Color.RED);
                    st.passInfoLabel.setText("Incorrect Password");
                }
            }
        } catch (Exception ex) {
        }
    }

    public void checkPasswordInAdmin(StartHere st, passwordSettingsFrame p) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                p.oldPanel.setVisible(true);
                p.newPassPanel.setVisible(false);
                p.pass = "" + resultset.getObject(1);
            } else {
                p.newPassPanel.setVisible(true);
                p.oldPanel.setVisible(false);
                p.menu.setVisible(false);
            }
        } catch (Exception ex) {
        }
    }

    public void matchPasswordWithNewPassword(passwordSettingsFrame pw, String s) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                if (s.equals("" + resultset.getObject(1))) {
                    if (pw.oPNewField.getText().equals(pw.oPconfirmField.getText())) {
                        try {
                            ips = connection.prepareStatement("Update login set password = '" + pw.oPconfirmField.getText() + "' where password='" + s + "'");
                            ips.executeUpdate();
                            pw.opInfoLabel.setText("Password Changed");
                        } catch (Exception ex) {
                            System.out.println("PassErr: " + ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Confirm Password does not match!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Current Password does not match!");
                }
            }
        } catch (Exception ex) {
        }
    }

    public void matchPasswordWithFirstPassword(passwordSettingsFrame pw) {
        try {
            if (pw.nPnewField.getText().equals(pw.nPConfirmField.getText())) {
                try {
                    ips = connection.prepareStatement("Insert into login values('" + pw.nPnewField.getText() + "')");
                    ips.executeUpdate();
                    pw.setVisible(false);
                } catch (Exception ex) {
                    System.out.println("PassErr: " + ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Confirm Password does not match!");
            }

        } catch (Exception ex) {
        }
    }

    public void deletePassword(passwordSettingsFrame pw) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                try {
                    ips = connection.prepareStatement("Delete from login where password='" + resultset.getObject(1) + "'");
                    ips.executeUpdate();
                    pw.setVisible(false);
                } catch (Exception ex) {
                    System.out.println("PassErr: " + ex);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void getBatchInStuInfoSett(StudentInformationSettings sis) {
        try {
            resultset = statement.executeQuery("Select distinct batch from student_information");
            while (resultset.next()) {
                sis.batchBox.addItem(resultset.getObject(1));
                sis.batchBox2.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
        }
    }

    public void getStuIDInStuInfoSettForBatchBox(StudentInformationSettings sis, String s) {
        try {
            sis.idBox.removeAllItems();
            sis.idBox.addItem("None");
            resultset = statement.executeQuery("Select id from student_information where batch = '" + s + "'");
            while (resultset.next()) {
                sis.idBox.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
        }
    }

    public void setStuIDInStuInfoSett(StudentInformationSettings sis, String b, String i) {
        try {
            resultset = statement.executeQuery("Select student_name, e_mail_phone from student_information where batch = '" + b + "' and id='" + i + "'");
            while (resultset.next()) {
                sis.nameField.setText("" + resultset.getObject(1));
                sis.emailFeild.setText("" + resultset.getObject(2));
            }
        } catch (Exception ex) {
            System.out.println("Err::::" + ex);
        }
    }

    public void updateStuInfoSett(StudentInformationSettings sis, String b, String oi, String n, String e) {
        try {
            ips = connection.prepareStatement("Update student_information set student_name='" + n + "', e_mail_phone='" + e + "' where batch='" + b + "' and id='" + oi + "'");
            ips.executeUpdate();
            sis.infoLabel.setText("Updated");
        } catch (Exception ex) {
            System.out.println("Err::::" + ex);
        }
    }

    public void getBatch1InStuInfoSett(StudentInformationSettings sis) {
        try {
            resultset = statement.executeQuery("select distinct batch from result_set where batch not in(select batch from result_set where semester>0)");
            while (resultset.next()) {
                sis.batchBox1.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("ER: " + ex);
        }
    }

    public void addStuInStuInfoSett(StudentInformationSettings sis, String b, String i, String n, String e) {
        try {
            ips = connection.prepareStatement("Insert into student_information values('" + i + "','" + n + "','" + e + "','" + b + "',('select distinct session from student_information where batch='" + b + "')'," + 0 + ")");
            ips.executeUpdate();
            ips = connection.prepareStatement("Insert into result_set values('" + i + "','" + b + "',0,0.00,0.00,0.00,0.00,'None','x')");
            ips.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Something wrong:\n" + ex);
        }
    }

    public void setStuIDforDeleteID(StudentInformationSettings sis, String b) {
        try {
            sis.idBox2.removeAllItems();
            sis.idBox2.addItem("None");
            resultset = statement.executeQuery("Select id from student_information where batch = '" + b + "'");
            while (resultset.next()) {
                sis.idBox2.addItem(resultset.getObject(1));
            }
        } catch (Exception ex) {
        }
    }

    public void getInfoForDelete(StudentInformationSettings sis, String b, String i) {
        try {
            resultset = statement.executeQuery("Select student_name, cgpa from student_information where batch = '" + b + "' and id='" + i + "'");
            if (resultset.next()) {
                sis.nameLabel2.setText("" + resultset.getObject(1) + "\tCGPA = " + resultset.getObject(2));
                sis.infoLabel2.setText("All The result and information will be deleted of this Student");
            }
        } catch (Exception ex) {
            System.out.println("Er: " + ex);
        }
    }

    public void getPasswordToDeleteStu(StudentInformationSettings sis) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                sis.pass = "" + resultset.getObject(1);
            }
        } catch (Exception ex) {
            System.out.println("Er: " + ex);
        }
    }

    public void deleteAllInfoOfAID(String b, String i) {
        try {
            ips = connection.prepareStatement("Delete from result_set where id='" + i + "' and batch='" + b + "'");
            ips.executeUpdate();


            ips = connection.prepareStatement("Delete from exam_mark where id='" + i + "'");
            ips.executeUpdate();


            ips = connection.prepareStatement("Delete from student_information where id='" + i + "' and batch='" + b + "'");
            ips.executeUpdate();

        } catch (Exception ex) {
            System.out.println("DelIDErr: " + ex);
        }
    }

    public void setPassSubSett(SubjectSettingsFrame ssf) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                ssf.pass = "" + resultset.getObject(1);
            }
        } catch (Exception ex) {
            System.out.println("PassSubErr: " + ex);
        }
    }

    public void setInitialSubSett(SubjectSettingsFrame ssf) {
        try {
            ssf.totalMarkBox.addItem("None");
            resultset = statement.executeQuery("select total_mark from grade_marks");
            while (resultset.next()) {
                ssf.totalMarkBox.addItem("" + resultset.getObject(1));
            }

        } catch (Exception ex) {
            System.out.println("SubInErr: " + ex);
        }
    }

    public void setInitial1SubSett(SubjectSettingsFrame ssf) {
        try {
            ssf.codeBox1.addItem("None");
            resultset = statement.executeQuery("select course_code from courses where course_code not in (select distinct course_code from exam_mark where totalmark=-1)");
            while (resultset.next()) {
                ssf.codeBox1.addItem("" + resultset.getObject(1));
            }

        } catch (Exception ex) {
            System.out.println("SubIn1Err: " + ex);
        }
    }

    public void setInitial2SubSett(SubjectSettingsFrame ssf) {
        try {
            ssf.semBox2.addItem("None");
            resultset = statement.executeQuery("select distinct semester from courses where semester not in (select distinct semester from exam_mark where totalmark=-1)");
            while (resultset.next()) {
                ssf.semBox2.addItem("" + resultset.getObject(1));
            }

        } catch (Exception ex) {
            System.out.println("SubIn2Err: " + ex);
        }
    }

    public void checkSemesterInSubSett(SubjectSettingsFrame ssf, String s) {
        try {
            resultset = statement.executeQuery("select id from exam_mark where semester=" + s + " and totalmark=-1");
            if (resultset.next()) {
                ssf.semCheck = 1;
            }

        } catch (Exception ex) {
            System.out.println("SubIn2Err: " + ex);
        }
    }

    public void insertNewSubject(SubjectSettingsFrame ssf) {
        try {
            ips = connection.prepareStatement("Insert into courses values('" + ssf.code.getText() + "', '" + ssf.title.getText() + "', " + ssf.sem.getText() + ", " + ssf.cradit.getText() + ", '" + ssf.markingTypeBox.getSelectedItem() + "')");
            ips.executeUpdate();


            ips = connection.prepareStatement("Insert into course_mark values('" + ssf.code.getText() + "', " + ssf.totalMarkBox.getSelectedItem() + ")");
            ips.executeUpdate();
            JOptionPane.showMessageDialog(null, "Subject is added to Database.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Subject Code Already Have!");
        }
    }

    public void viewSubSett1(SubjectSettingsFrame ssf) {
        try {
            resultset = statement.executeQuery("select title, semester, cradit from courses where course_code='" + ssf.codeBox1.getSelectedItem() + "'");
            if (resultset.next()) {
                ssf.title1.setText("" + resultset.getObject(1));
                ssf.sem1.setText("" + resultset.getObject(2));
                ssf.cradit1.setText("" + resultset.getObject(3));
            }

            ssf.totalMarkBox1.removeAllItems();
            ssf.totalMarkBox1.addItem("None");
            resultset = statement.executeQuery("select distinct total_mark from course_mark");
            while (resultset.next()) {
                ssf.totalMarkBox1.addItem("" + resultset.getObject(1));
            }


            resultset = statement.executeQuery("select total_mark from course_mark where course_code='" + ssf.codeBox1.getSelectedItem() + "'");
            if (resultset.next()) {
                ssf.totalMarkBox1.setSelectedItem("" + resultset.getObject(1));
            }

        } catch (Exception ex) {
            System.out.println("SubIn2Err: " + ex);
        }
    }

    public void semCheck1SubSett(SubjectSettingsFrame ssf, String s) {
        try {
            resultset = statement.executeQuery("select id from exam_mark where semester=" + s + " and totalmark=-1");
            if (resultset.next()) {
                ssf.SemCheck1 = 1;
            }
        } catch (Exception ex) {
        }
    }

    public void insertEditedSubSett(SubjectSettingsFrame ssf) {
        try {
            ips = connection.prepareStatement("Update courses set title='" + ssf.title1.getText() + "', semester=" + ssf.sem1.getText() + ", cradit=" + ssf.cradit1.getText() + " where course_code='" + ssf.codeBox1.getSelectedItem() + "'");
            ips.executeUpdate();

            ips = connection.prepareStatement("Update course_mark set total_mark=" + ssf.totalMarkBox1.getSelectedItem() + " where course_code='" + ssf.codeBox1.getSelectedItem() + "'");
            ips.executeUpdate();

            ssf.infoLabel1.setText("Subject Updated.");
        } catch (Exception ex) {
            System.out.println("Errrr: " + ex);
        }
    }

    public void deleteCourse2SubSett(SubjectSettingsFrame ssf) {
        try {
            ips = connection.prepareStatement("Delete from course_mark where course_code='" + ssf.codeBox2.getSelectedItem() + "'");
            ips.executeUpdate();


            ips = connection.prepareStatement("Delete from courses where course_code='" + ssf.codeBox2.getSelectedItem() + "'");
            ips.executeUpdate();

            ssf.info2.setText("Subject Deleted");
        } catch (Exception ex) {
            System.out.println("Err " + ex);
        }
    }

    public void setCourseCode2SubSett(SubjectSettingsFrame ssf, String s) {
        try {
            ssf.codeBox2.removeAllItems();
            ssf.codeBox2.addItem("None");
            resultset = statement.executeQuery("select course_code from courses where semester=" + s + "");

            while (resultset.next()) {
                ssf.codeBox2.addItem("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Err: " + ex);
        }
    }

    public void setSubject2SubSett(SubjectSettingsFrame ssf) {
        try {
            resultset = statement.executeQuery("select title from courses where course_code='" + ssf.codeBox2.getSelectedItem() + "'");

            if (resultset.next()) {
                ssf.info2.setText("" + resultset.getObject(1));
            }
        } catch (Exception ex) {
            System.out.println("Err: " + ex);
        }
    }

    public void viewGradePointList(GradePointView gpv) {
        try {
            resultset = statement.executeQuery("select * from grade");

            int r = 0;
            while (resultset.next()) {

                DefaultTableModel model = (DefaultTableModel) gpv.table.getModel();
                model.addRow(new Object[]{""});
                gpv.table.setValueAt(resultset.getObject(1), r, 0);
                gpv.table.setValueAt(resultset.getObject(2), r, 1);
                gpv.table.setValueAt(resultset.getObject(3), r, 2);
                gpv.table.setValueAt(resultset.getObject(4), r, 3);
                gpv.table.setValueAt(resultset.getObject(5), r, 4);

                r++;
            }
        } catch (Exception ex) {
            System.out.println("Err: " + ex);
        }


    }

    public void runSimpleQuery1(RunFreeQuery rfq) {
        try {
            resultset = statement.executeQuery(rfq.query.getText());
            rfq.tablePanel.setVisible(true);
            rfq.errorPanel.setVisible(false);
            ResultSetMetaData metaData = resultset.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            DefaultTableModel mod = null;
            mod = (DefaultTableModel) rfq.table.getModel();

            for (int i = mod.getRowCount() - 1; i >= 0; i--) {
                mod.removeRow(i);
            }

            mod.addRow(new Object[]{""});
            mod.addColumn("");
            DefaultTableColumnModel modT = (DefaultTableColumnModel) rfq.table.getColumnModel();
            modT.removeColumn(modT.getColumn(0));

            for (int i = 1; i <= rfq.totalColumn - numberOfColumns; i++) {
                modT.removeColumn(modT.getColumn(0));
            }
            for (int i = 1; i <= numberOfColumns; i++) {
                //form.format("%s\t", metaData.getColumnName(i));
                rfq.table.setValueAt(metaData.getColumnName(i), 0, i - 1);
            }
            int r = 1;
            while (resultset.next()) {
                mod.addRow(new Object[]{""});
                for (int i = 1; i <= numberOfColumns; i++) {
                    //form.format("%s\t\t", resultset.getObject(i));
                    rfq.table.setValueAt(resultset.getObject(i), r, i - 1);
                }
                r++;
            }

            rfq.totalColumn++;
            rfq.tableView = true;
            rfq.errorView = false;

        } catch (Exception ex) {
            rfq.tablePanel.setVisible(false);
            rfq.errorPanel.setVisible(true);
            rfq.errorArea.setText("ERROR: " + ex);
            rfq.tableView = false;
            rfq.errorView = true;
        }
    }

    public void runModificationQuery(RunFreeQuery rfq) {
        try {

            ips = connection.prepareStatement(rfq.query.getText());
            ips.executeUpdate();

            rfq.tablePanel.setVisible(false);
            rfq.errorPanel.setVisible(true);
            rfq.errorArea.setText("Query executed Successfully");

            rfq.tableView = false;
            rfq.errorView = true;

        } catch (Exception ex) {
            rfq.tablePanel.setVisible(false);
            rfq.errorPanel.setVisible(true);
            rfq.errorArea.setText("ERROR: " + ex);
            rfq.tableView = false;
            rfq.errorView = true;
        }
    }

    public void saveToHistory(RunFreeQuery rfq) {
        try {

            ips = connection.prepareStatement("Insert into history values('" + new Date() + "','" + rfq.query.getText() + "')");
            ips.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ErrH: " + ex);
        }
    }

    public void viewHistory(RunFreeQuery rfq) {
        try {
            resultset = statement.executeQuery("select * from history");
            while (resultset.next()) {
                rfq.HistoryArea.setText("------------------------------------------------------------------------------------------------------------------------------------------" + "\n" + rfq.HistoryArea.getText());
                rfq.HistoryArea.setText(resultset.getObject(1) + "\n" + resultset.getObject(2) + "\n" + rfq.HistoryArea.getText());
            }
        } catch (Exception ex) {
            System.out.println("Err: " + ex);
        }
    }

    public void clearAllHistory(RunFreeQuery rfq) {
        try {

            ips = connection.prepareStatement("Delete from history");
            ips.executeUpdate();

            rfq.HistoryArea.setText("");

        } catch (Exception ex) {
            System.out.println("ErrH: " + ex);
        }
    }

    public void viewTableValues(RunFreeQuery rfq, String s) {
        try {
            resultset = statement.executeQuery(s);
            rfq.tablePanel.setVisible(true);
            rfq.errorPanel.setVisible(false);
            ResultSetMetaData metaData = resultset.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            DefaultTableModel mod = null;
            mod = (DefaultTableModel) rfq.table.getModel();

            for (int i = mod.getRowCount() - 1; i >= 0; i--) {
                mod.removeRow(i);
            }

            mod.addRow(new Object[]{""});
            mod.addColumn("");
            DefaultTableColumnModel modT = (DefaultTableColumnModel) rfq.table.getColumnModel();
            modT.removeColumn(modT.getColumn(0));

            for (int i = 1; i <= rfq.totalColumn - numberOfColumns; i++) {
                modT.removeColumn(modT.getColumn(0));
            }
            for (int i = 1; i <= numberOfColumns; i++) {
                //form.format("%s\t", metaData.getColumnName(i));
                rfq.table.setValueAt(metaData.getColumnName(i), 0, i - 1);
            }
            int r = 1;
            while (resultset.next()) {
                mod.addRow(new Object[]{""});
                for (int i = 1; i <= numberOfColumns; i++) {
                    //form.format("%s\t\t", resultset.getObject(i));
                    rfq.table.setValueAt(resultset.getObject(i), r, i - 1);
                }
                r++;
            }

            rfq.totalColumn++;
            rfq.tableView = true;
            rfq.errorView = false;

        } catch (Exception ex) {
            rfq.tablePanel.setVisible(false);
            rfq.errorPanel.setVisible(true);
            rfq.errorArea.setText("ERROR: " + ex);
            rfq.tableView = false;
            rfq.errorView = true;
        }
    }

    public void getPassword(StartHere s) {
        try {
            resultset = statement.executeQuery("Select password from login");
            if (resultset.next()) {
                s.pass = "" + resultset.getObject(1);
            }
        } catch (Exception ex) {
            System.out.println("Er: " + ex);
        }
    }
}
