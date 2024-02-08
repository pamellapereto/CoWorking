package view;

import javax.swing.JDialog;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Reservas extends JDialog {

	public Reservas() {
		setTitle("Reservas");
		setResizable(false);
		setBounds(new Rectangle(300, 100, 613, 362));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);

	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas dialog = new Reservas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
