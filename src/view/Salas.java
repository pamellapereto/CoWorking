package view;

import javax.swing.JDialog;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Salas extends JDialog {

	public Salas() {
		setTitle("Salas");
		setResizable(false);
		setBounds(new Rectangle(300, 100, 613, 362));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);

	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Salas dialog = new Salas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
