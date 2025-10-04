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
import exception.PropedeuticitaException;
import exception.DAOException;
import exception.DBConnectionException;
import java.util.List;

public class GuiChiusura {

    JFrame frame;
    private JTextField reportCodeField;
    private JTextField pinField;
    private String reportCode;
    private List<String> usernames;
    private int currentUserIndex;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GuiChiusura window = new GuiChiusura();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GuiChiusura() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblClosingReport = new JLabel("Closing Report");
        lblClosingReport.setForeground(Color.RED);
        lblClosingReport.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
        lblClosingReport.setBounds(138, 6, 200, 32);
        frame.getContentPane().add(lblClosingReport);

        JLabel lblreportCode = new JLabel("Report Code");
        lblreportCode.setBounds(25, 50, 100, 16);
        frame.getContentPane().add(lblreportCode);

        reportCodeField = new JTextField();
        reportCodeField.setBounds(25, 70, 168, 26);
        frame.getContentPane().add(reportCodeField);
        reportCodeField.setColumns(10);

        JLabel lblPin = new JLabel("Pin");
        lblPin.setBounds(25, 110, 100, 16);
        frame.getContentPane().add(lblPin);

        pinField = new JTextField();
        pinField.setBounds(25, 130, 168, 26);
        frame.getContentPane().add(pinField);
        pinField.setColumns(10);

        JButton btnCheck = new JButton("Check Report");
        btnCheck.setBounds(268, 70, 155, 26);
        frame.getContentPane().add(btnCheck);

        JButton btnInsertPin = new JButton("Insert PIN");
        btnInsertPin.setBounds(268, 130, 155, 26);
        frame.getContentPane().add(btnInsertPin);
        btnInsertPin.setEnabled(false);

        JButton btnConfirm = new JButton("Conferma");
        btnConfirm.setBounds(268, 190, 155, 49);
        frame.getContentPane().add(btnConfirm);
        btnConfirm.setEnabled(false);

        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reportCode = reportCodeField.getText();
                GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();
                try {
                    gestore.checkReport(reportCode);
                    usernames = gestore.getUsernamesByReport(reportCode);
                    currentUserIndex = 0;
                    if (usernames.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "No students associated with the report.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        btnInsertPin.setEnabled(true);
                    }
                } catch (OperationException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Operation error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnInsertPin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pinStr = pinField.getText();
                try {
                    int pin = Integer.parseInt(pinStr);
                    int length = String.valueOf(pin).length();
                    if (length != 7) {
                        throw new NumberFormatException();
                    }
                    GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();
                    String username = usernames.get(currentUserIndex);
                    gestore.checkPIN(pin, reportCode, username);
                    gestore.ClosingReport1(reportCode, username);
                    currentUserIndex++;
                    pinField.setText("");
                    if (currentUserIndex < usernames.size()) {
                        JOptionPane.showMessageDialog(frame, "PIN accepted for " + username + ". Insert pin for next student.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        btnConfirm.setEnabled(true);
                        btnInsertPin.setEnabled(false);
                        JOptionPane.showMessageDialog(frame, "All PINs have been accepted. Confirm to close the report.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "The PIN must be a 7-digit number. Try again.", "Format Error", JOptionPane.ERROR_MESSAGE);
                } catch (OperationException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Operation error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();
                    gestore.ClosingReport2(reportCode);
                    JOptionPane.showMessageDialog(frame, "Report closed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
