package boundary;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuSegreteriaStudenti {
    static JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuSegreteriaStudenti window = new MenuSegreteriaStudenti();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MenuSegreteriaStudenti() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblInterfacciaSegreteria = new JLabel("Menu Segreteria");
        lblInterfacciaSegreteria.setForeground(Color.RED);
        lblInterfacciaSegreteria.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        lblInterfacciaSegreteria.setBounds(90, 6, 270, 64);
        frame.getContentPane().add(lblInterfacciaSegreteria);

        JButton btnAggiungiDocente = new JButton("Aggiungi Docente");
        btnAggiungiDocente.setBounds(90, 100, 270, 40);
        frame.getContentPane().add(btnAggiungiDocente);

        JButton btnAggiungiCorso = new JButton("Aggiungi Corso");
        btnAggiungiCorso.setBounds(90, 150, 270, 40);
        frame.getContentPane().add(btnAggiungiCorso);

        JButton btnAssociazioneDocenteCorso = new JButton("Associa Docente a Corso");
        btnAssociazioneDocenteCorso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GuiAssociazione window = new GuiAssociazione();
                    window.frame.setVisible(true);
                    frame.setVisible(false);
                } catch (Exception exc) {
                    System.out.println("Errore nella creazione della finestra");
                }
            }
        });
        btnAssociazioneDocenteCorso.setBounds(90, 200, 270, 40);
        frame.getContentPane().add(btnAssociazioneDocenteCorso);
    }
}

