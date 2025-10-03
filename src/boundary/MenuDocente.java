package boundary;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuDocente {
	static JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDocente window = new MenuDocente();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuDocente() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblInterfacciaDocente = new JLabel("Menu");
		lblInterfacciaDocente.setForeground(Color.RED);
		lblInterfacciaDocente.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblInterfacciaDocente.setBounds(138, 6, 183, 64);
		frame.getContentPane().add(lblInterfacciaDocente);
		
		JButton btnAperturaVerbale = new JButton("Apertura Verbale");
        btnAperturaVerbale.setBounds(90, 100, 270, 40);
        frame.getContentPane().add(btnAperturaVerbale);
		
		JButton btnChiusuraVerbale = new JButton("Chiusura Verbale");
        btnChiusuraVerbale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GuiChiusura window = new GuiChiusura();
                    window.frame.setVisible(true);
                    frame.setVisible(false);
                } catch (Exception exc) {
                    System.out.println("Errore nella creazione della finestra");
                }
            }
        });
        btnChiusuraVerbale.setBounds(90, 150, 270, 40);
        frame.getContentPane().add(btnChiusuraVerbale);
	}
}
