package view;

import javax.swing.JDialog;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Cursor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

public class Salas extends JDialog {
	private JTextField inputOcup;

	public JButton imgCreate;
	public JButton imgUpdate;
	public JButton imgDelete;

	public Salas() {
		setTitle("Salas");
		setResizable(false);
		setBounds(new Rectangle(300, 100, 724, 446));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);

		JLabel tipoSala = new JLabel("Categoria:");
		tipoSala.setBounds(24, 58, 74, 14);
		getContentPane().add(tipoSala);

		JLabel codSala = new JLabel("Código:");
		codSala.setBounds(24, 249, 46, 14);
		getContentPane().add(codSala);

		JLabel andarSala = new JLabel("Andar:");
		andarSala.setBounds(298, 249, 46, 14);
		getContentPane().add(andarSala);

		JLabel ocupSala = new JLabel("Ocupação máxima:");
		ocupSala.setBounds(298, 312, 98, 14);
		getContentPane().add(ocupSala);

		JLabel numSala = new JLabel("Número:");
		numSala.setBounds(24, 314, 46, 14);
		getContentPane().add(numSala);

		inputOcup = new JTextField();
		inputOcup.setColumns(10);
		inputOcup.setBounds(392, 309, 86, 20);
		getContentPane().add(inputOcup);

		imgCreate = new JButton("");
		imgCreate.setBackground(new Color(240, 240, 240));
		imgCreate.setBorderPainted(false);
		imgCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgCreate.setIcon(new ImageIcon(Salas.class.getResource("/img/create.png")));
		imgCreate.setBounds(392, 342, 65, 54);
		getContentPane().add(imgCreate);

		imgCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// adicionarFuncionario();
			}
		});

		imgUpdate = new JButton("");
		imgUpdate.setBackground(new Color(240, 240, 240));
		imgUpdate.setBorderPainted(false);
		imgUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgUpdate.setIcon(new ImageIcon(Salas.class.getResource("/img/update.png")));
		imgUpdate.setBounds(488, 342, 65, 54);
		getContentPane().add(imgUpdate);

		imgUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atualizarFuncionario();
			}
		});

		imgDelete = new JButton("");
		imgDelete.setBackground(new Color(240, 240, 240));
		imgDelete.setBorderPainted(false);
		imgDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgDelete.setIcon(new ImageIcon(Salas.class.getResource("/img/delete.png")));
		imgDelete.setBounds(581, 342, 65, 54);
		getContentPane().add(imgDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(95, 83, 516, 90);
		getContentPane().add(scrollPane);

		tblSalas = new JTable();
		scrollPane.setViewportView(tblSalas);

		JButton btnPesquisar = new JButton("");
		btnPesquisar.setBackground(new Color(240, 240, 240));
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setIcon(new ImageIcon(Salas.class.getResource("/img/search.png")));
		btnPesquisar.setBounds(284, 184, 46, 33);
		getContentPane().add(btnPesquisar);

		JLabel IDSala = new JLabel("ID:");
		IDSala.setBounds(24, 192, 46, 14);
		getContentPane().add(IDSala);

		inputID = new JTextField();
		inputID.setEnabled(false);
		inputID.setBounds(74, 189, 200, 20);
		getContentPane().add(inputID);
		inputID.setColumns(10);

		inputCategoria = new JComboBox();
		inputCategoria.setModel(new DefaultComboBoxModel(new String[] { "", "Sala de reunião", "Sala de conferência",
				"Espaço de eventos", "Escritório privado" }));
		inputCategoria.setBounds(95, 54, 443, 22);
		getContentPane().add(inputCategoria);

		inputCod = new JComboBox();
		inputCod.setModel(new DefaultComboBoxModel(new String[] { "", "REU", "CONF", "EVENT", "PRIV" }));
		inputCod.setBounds(74, 245, 147, 22);
		getContentPane().add(inputCod);

		inputAndar = new JComboBox();
		inputAndar.setModel(
				new DefaultComboBoxModel(new String[] { "", "Subsolo", "Térreo", "1º andar", "2º andar", "3º andar" }));
		inputAndar.setBounds(338, 245, 157, 22);
		getContentPane().add(inputAndar);

		inputNum = new JTextField();
		inputNum.setBounds(74, 309, 86, 20);
		getContentPane().add(inputNum);
		inputNum.setColumns(10);

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// btnBuscarFuncionario();
			}
		});

		tblSalas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// setarCaixasTexto();
			}
		});

	} // Fim do construtor

	// Criar um objeto da classe DAO para estabelecer conexão com banco
	DAO dao = new DAO();
	private JTable tblSalas;
	private JTextField inputID;
	private JComboBox inputCategoria;
	private JComboBox inputCod;
	private JComboBox inputAndar;
	private JTextField inputNum;

	private void adicionarFuncionario() {
		String create = "insert into funcionario (nomeFunc, login, senha, email, perfil) values (?, ?, md5(?), ?, ?);";

		// Validação do nome do funcionário
		if (inputNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nome do funcionário obrigatório!");
			inputNome.requestFocus();
		}

		// Validação do login do funcionário
		else if (inputLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Login do funcionário obrigatório!");
			inputLogin.requestFocus();
		}

		// Validação da senha do funcionário
		else if (inputSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Senha do funcionário obrigatória!");
			inputSenha.requestFocus();
		}

		// Validação do email do funcionário
		else if (inputOcup.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "E-mail do funcionário obrigatório!");
			inputOcup.requestFocus();
		}

		else {

			try {
				// Estabelecer a conexão
				Connection conexaoBanco = dao.conectar();

				// Preparar a execusão do script SQL
				PreparedStatement executarSQL = conexaoBanco.prepareStatement(create);

				// Substituir os pontos de interrogação pelo conteúdo das caixas de texto
				// (inputs)
				executarSQL.setString(1, inputNome.getText());
				executarSQL.setString(2, inputLogin.getText());
				executarSQL.setString(3, inputSenha.getText());
				executarSQL.setString(4, inputOcup.getText());
				executarSQL.setString(5, inputPerfil.getSelectedItem().toString());

				// Executar os comandos SQL e inserir o funcionário no banco de dados
				executarSQL.executeUpdate();

				JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
				limparCampos();

				conexaoBanco.close();
			}

			catch (SQLIntegrityConstraintViolationException error) {
				JOptionPane.showMessageDialog(null, "Login em uso. \nEscolha outro nome de usuário.");
			}

			catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscarFuncionarioNaTabela() {
		String readTabela = "select idFuncionario as ID, nomeFunc as Nome, email as Email from funcionario"
				+ " where nomeFunc like ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução dos comandos SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readTabela);

			// Substituir o ? pelo conteúdo da caixa de texto
			executarSQL.setString(1, inputNome.getText() + "%");

			// Executar o comando SQL
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			// Exibir o resultado na tabela, utilização da biblioteca rs2xml para "popular"
			// a tabela
			tblFuncionarios.setModel(DbUtils.resultSetToTableModel(resultadoExecucao));

			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarCaixasTexto() {

		// Criar uma variável para receber a linha da tabela
		int setarLinha = tblFuncionarios.getSelectedRow();

		inputNome.setText(tblFuncionarios.getModel().getValueAt(setarLinha, 1).toString());
		inputID.setText(tblFuncionarios.getModel().getValueAt(setarLinha, 0).toString());

	}

	// Criar método para buscar funcionário pelo botão Pesquisar
	private void btnBuscarFuncionario() {
		String readBtn = "select * from funcionario where idFuncionario = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução do comando SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readBtn);

			// Substituir o ponto de interrogação pelo conteúdo da caixa de texto (nome)
			executarSQL.setString(1, inputID.getText());

			// Executar o comando SQL e exibir o resultado no formulário funcionário (todos
			// os seus dados)
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			if (resultadoExecucao.next()) {
				// Preencher os campos do formulário
				inputLogin.setText(resultadoExecucao.getString(3));
				inputSenha.setText(resultadoExecucao.getString(4));
				inputPerfil.setSelectedItem(resultadoExecucao.getString(5));
				inputOcup.setText(resultadoExecucao.getString(6));

			}

			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	private void atualizarFuncionario() {
		String update = "update funcionario set nomeFunc = ?, login = ?, senha = md5(?), email = ?,"
				+ " perfil = ? where idFuncionario = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execusão do script SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(update);

			// Substituir os pontos de interrogação pelo conteúdo das caixas de texto
			// (inputs)
			executarSQL.setString(1, inputNome.getText());
			executarSQL.setString(2, inputLogin.getText());
			executarSQL.setString(3, inputSenha.getText());
			executarSQL.setString(4, inputOcup.getText());
			executarSQL.setString(5, inputPerfil.getSelectedItem().toString());
			executarSQL.setString(6, inputID.getText());

			// Executar os comandos SQL e atualizar o funcionário no banco de dados
			executarSQL.executeUpdate();

			JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
			limparCampos();

			conexaoBanco.close();
		}

		catch (SQLIntegrityConstraintViolationException error) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro. \nO login e/ou e-mail já estão sendo usados");
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	
	private void limparCampos() {
		inputNome.setText(null);
		inputLogin.setText(null);
		inputSenha.setText(null);
		inputOcup.setText(null);
		// Para limpar componente JComboBox
		inputPerfil.setSelectedIndex(-1);

		// Posicionar o cursor de volta no campo Nome
		inputNome.requestFocus();
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
