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
    private JTextField codiceCorsoField;
    private JTextField matricolaDocenteField;
    private String codiceCorso;
    private String matricolaDocente;

   
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

        JLabel lblAssociazioneDocenteCorso = new JLabel("Associazione Docente Corso");
        lblAssociazioneDocenteCorso.setForeground(Color.RED);
        lblAssociazioneDocenteCorso.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblAssociazioneDocenteCorso.setBounds(100, 6, 250, 32);
        frame.getContentPane().add(lblAssociazioneDocenteCorso);

        JLabel lblCodiceCorso = new JLabel("Codice Corso");
        lblCodiceCorso.setBounds(25, 50, 100, 16);
        frame.getContentPane().add(lblCodiceCorso);

        codiceCorsoField = new JTextField();
        codiceCorsoField.setBounds(25, 70, 168, 26);
        frame.getContentPane().add(codiceCorsoField);
        codiceCorsoField.setColumns(10);

        JLabel lblMatricolaDocente = new JLabel("Matricola Docente");
        lblMatricolaDocente.setBounds(25, 110, 130, 16);
        frame.getContentPane().add(lblMatricolaDocente);

        matricolaDocenteField = new JTextField();
        matricolaDocenteField.setBounds(25, 130, 168, 26);
        frame.getContentPane().add(matricolaDocenteField);
        matricolaDocenteField.setColumns(10);

        JButton btnAssocia = new JButton("Associa");
        btnAssocia.setBounds(268, 100, 155, 26);
        frame.getContentPane().add(btnAssocia);

        btnAssocia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codiceCorso = codiceCorsoField.getText();
                matricolaDocente = matricolaDocenteField.getText();
                GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();

                try {
                    if (codiceCorso.length() != 5 || !codiceCorso.matches("[a-zA-Z0-9]+")) {
                        throw new OperationException("Il Codice Corso deve essere di 5 caratteri alfanumerici.");
                    }
                    if (matricolaDocente.length() != 7 || !matricolaDocente.matches("[a-zA-Z0-9]+")) {
                        throw new OperationException("La Matricola Docente deve essere di 7 caratteri alfanumerici.");
                    }
                    
                    gestore.AssociazioneDocenteCorso(codiceCorso, matricolaDocente);
                    JOptionPane.showMessageDialog(frame, "Docente associato con successo al corso!", "Successo", JOptionPane.INFORMATION_MESSAGE);
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
