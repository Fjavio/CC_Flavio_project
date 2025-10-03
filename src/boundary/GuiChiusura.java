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
    private JTextField codiceVerbaleField;
    private JTextField pinField;
    private String codiceVerbale;
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

        JLabel lblChiusuraVerbale = new JLabel("Chiusura Verbale");
        lblChiusuraVerbale.setForeground(Color.RED);
        lblChiusuraVerbale.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
        lblChiusuraVerbale.setBounds(138, 6, 200, 32);
        frame.getContentPane().add(lblChiusuraVerbale);

        JLabel lblCodiceVerbale = new JLabel("Codice Verbale");
        lblCodiceVerbale.setBounds(25, 50, 100, 16);
        frame.getContentPane().add(lblCodiceVerbale);

        codiceVerbaleField = new JTextField();
        codiceVerbaleField.setBounds(25, 70, 168, 26);
        frame.getContentPane().add(codiceVerbaleField);
        codiceVerbaleField.setColumns(10);

        JLabel lblPin = new JLabel("Pin");
        lblPin.setBounds(25, 110, 100, 16);
        frame.getContentPane().add(lblPin);

        pinField = new JTextField();
        pinField.setBounds(25, 130, 168, 26);
        frame.getContentPane().add(pinField);
        pinField.setColumns(10);

        JButton btnVerifica = new JButton("Verifica Verbale");
        btnVerifica.setBounds(268, 70, 155, 26);
        frame.getContentPane().add(btnVerifica);

        JButton btnInserisciPin = new JButton("Inserisci PIN");
        btnInserisciPin.setBounds(268, 130, 155, 26);
        frame.getContentPane().add(btnInserisciPin);
        btnInserisciPin.setEnabled(false);

        JButton btnConferma = new JButton("Conferma");
        btnConferma.setBounds(268, 190, 155, 49);
        frame.getContentPane().add(btnConferma);
        btnConferma.setEnabled(false);

        btnVerifica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codiceVerbale = codiceVerbaleField.getText();
                GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();
                try {
                    gestore.verificaVerbale(codiceVerbale);
                    usernames = gestore.getUsernamesByVerbale(codiceVerbale);
                    currentUserIndex = 0;
                    if (usernames.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Nessuno studente associato al verbale.", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        btnInserisciPin.setEnabled(true);
                    }
                } catch (OperationException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore di Operazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnInserisciPin.addActionListener(new ActionListener() {
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
                    gestore.controlloPIN(pin, codiceVerbale, username);
                    gestore.ChiusuraVerbale1(codiceVerbale, username);
                    currentUserIndex++;
                    pinField.setText("");
                    if (currentUserIndex < usernames.size()) {
                        JOptionPane.showMessageDialog(frame, "PIN accettato per " + username + ". Inserisci il PIN per il prossimo studente.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        btnConferma.setEnabled(true);
                        btnInserisciPin.setEnabled(false);
                        JOptionPane.showMessageDialog(frame, "Tutti i PIN sono stati accettati. Conferma per chiudere il verbale.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Il PIN deve essere un numero di 7 cifre. Riprovare.", "Errore di Formato", JOptionPane.ERROR_MESSAGE);
                } catch (OperationException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore di Operazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnConferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();
                    gestore.ChiusuraVerbale2(codiceVerbale);
                    JOptionPane.showMessageDialog(frame, "Verbale chiuso con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } catch (OperationException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore di Operazione", JOptionPane.ERROR_MESSAGE);
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
