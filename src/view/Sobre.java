package view;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.EventQueue;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Sobre extends JDialog {

	public Sobre() {
		setTitle("Sobre");
		setBounds(new Rectangle(100, 100, 579, 308));
		setResizable(false);
	
		//Será explicado ainda hoje, após criarmos outra janela
		setModal(true);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);
		
		JLabel titulo = new JLabel("Sobre o software");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		titulo.setBounds(0, 35, 579, 25);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(titulo);
		
		JLabel descricao1 = new JLabel("O software CoWorking trata-se de um protótipo cujo objetivo é");
		descricao1.setHorizontalAlignment(SwingConstants.CENTER);
		descricao1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		descricao1.setBounds(0, 81, 579, 25);
		getContentPane().add(descricao1);
		
		JLabel descricao2 = new JLabel("possibilitar o gerenciamento de reserva de salas em um espaço colaborativo.");
		descricao2.setHorizontalAlignment(SwingConstants.CENTER);
		descricao2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		descricao2.setBounds(0, 117, 579, 25);
		getContentPane().add(descricao2);
		
		JLabel versao = new JLabel("Versão: 1.0.0");
		versao.setHorizontalAlignment(SwingConstants.CENTER);
		versao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		versao.setBounds(0, 173, 579, 25);
		getContentPane().add(versao);
		
		JLabel atualizacao = new JLabel("Última atualização: 31/01/2024");
		atualizacao.setHorizontalAlignment(SwingConstants.CENTER);
		atualizacao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		atualizacao.setBounds(0, 205, 579, 25);
		getContentPane().add(atualizacao);
		
		JLabel imgMIT = new JLabel("");
		imgMIT.setIcon(new ImageIcon(Sobre.class.getResource("/img/mitLicense.png")));
		imgMIT.setBounds(493, 208, 48, 48);
		getContentPane().add(imgMIT);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
