package boundary;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import control.GestoreCorsiDiStudioConservatorio;
import exception.OperationException;
import java.util.List;

public class GuiAssociazione {

    JFrame frame;
    private JTextField courseCodeField;
    private JTextField teacherIDField;
    private String courseCode;
    private String teacherID;

   
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GuiAssociazione window = new GuiAssociazione();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

   
    public GuiAssociazione() {
        initialize();
    }

   
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblAssociationTeacherCourse = new JLabel("Association Teacher Course");
        lblAssociationTeacherCourse.setForeground(Color.RED);
        lblAssociationTeacherCourse.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblAssociationTeacherCourse.setBounds(100, 6, 250, 32);
        frame.getContentPane().add(lblAssociationTeacherCourse);

        JLabel lblcourseCode = new JLabel("Course Code");
        lblcourseCode.setBounds(25, 50, 100, 16);
        frame.getContentPane().add(lblcourseCode);

        courseCodeField = new JTextField();
        courseCodeField.setBounds(25, 70, 168, 26);
        frame.getContentPane().add(courseCodeField);
        courseCodeField.setColumns(10);

        JLabel lblteacherID = new JLabel("Teacher id");
        lblteacherID.setBounds(25, 110, 130, 16);
        frame.getContentPane().add(lblteacherID);

        teacherIDField = new JTextField();
        teacherIDField.setBounds(25, 130, 168, 26);
        frame.getContentPane().add(teacherIDField);
        teacherIDField.setColumns(10);

        JButton btnAssociate = new JButton("Associate");
        btnAssociate.setBounds(268, 100, 155, 26);
        frame.getContentPane().add(btnAssociate);

        btnAssociate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                courseCode = courseCodeField.getText();
                teacherID = teacherIDField.getText();
                GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();

                try {
                    if (courseCode.length() != 5 || !courseCode.matches("[a-zA-Z0-9]+")) {
                        throw new OperationException("The Course Code must be 5 alphanumeric characters long.");
                    }
                    if (teacherID.length() != 7 || !teacherID.matches("[a-zA-Z0-9]+")) {
                        throw new OperationException("The Teacher id must be 7 alphanumeric characters long.");
                    }
                    
                    gestore.AssociationTeacherCourse(courseCode, teacherID);
                    JOptionPane.showMessageDialog(frame, "Successfully taught course associate professor!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (OperationException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Operation error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                MenuDocente.frame.setVisible(true);
            }
        });
        btnReturn.setBounds(306, 223, 117, 29);
        frame.getContentPane().add(btnReturn);
    }
}
