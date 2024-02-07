package view;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Cursor;

public class Home extends JDialog {
	//Construtor
	public Home() {
		setTitle("Home");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/img/logo.png")));
		setBounds(new Rectangle(300, 100, 613, 362));
		getContentPane().setLayout(null);
		
		JButton btnUser = new JButton("");
		btnUser.setBorderPainted(false);
		btnUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUser.setIcon(new ImageIcon(Home.class.getResource("/img/user.png")));
		btnUser.setBounds(71, 101, 96, 96);
		getContentPane().add(btnUser);
		
		JButton btnRoom = new JButton("");
		btnRoom.setBorderPainted(false);
		btnRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRoom.setIcon(new ImageIcon(Home.class.getResource("/img/room.png")));
		btnRoom.setBounds(237, 101, 96, 96);
		getContentPane().add(btnRoom);
		
		JButton btnReserve = new JButton("");
		btnReserve.setBorderPainted(false);
		btnReserve.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReserve.setIcon(new ImageIcon(Home.class.getResource("/img/reserve.png")));
		btnReserve.setBounds(387, 101, 96, 96);
		getContentPane().add(btnReserve);
	}
	
	//Implementação
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home dialog = new Home();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
}
