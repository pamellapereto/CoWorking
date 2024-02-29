package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//Importação da biblioteca responsável pelo calendário
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;

import model.DAO;
import net.proteanit.sql.DbUtils;

public class Reservas extends JDialog {

	public JButton imgCreate;
	public JButton imgUpdate;
	public JButton imgDelete;

	public Reservas() {
		setTitle("Reservas");
		setResizable(false);
		setBounds(new Rectangle(300, 100, 860, 447));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);

		JLabel tipoSala = new JLabel("Categoria:");
		tipoSala.setBounds(24, 29, 74, 14);
		getContentPane().add(tipoSala);

		JLabel andarSala = new JLabel("Andar:");
		andarSala.setBounds(24, 251, 57, 14);
		getContentPane().add(andarSala);

		JLabel numSala = new JLabel("Número:");
		numSala.setBounds(24, 203, 74, 14);
		getContentPane().add(numSala);

		imgCreate = new JButton("");
		imgCreate.setEnabled(false);
		imgCreate.setBackground(new Color(240, 240, 240));
		imgCreate.setBorderPainted(false);
		imgCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgCreate.setIcon(new ImageIcon(Reservas.class.getResource("/img/create.png")));
		imgCreate.setBounds(392, 342, 65, 54);
		getContentPane().add(imgCreate);

		imgCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarReserva();
			}
		});

		imgUpdate = new JButton("");
		imgUpdate.setEnabled(false);
		imgUpdate.setBackground(new Color(240, 240, 240));
		imgUpdate.setBorderPainted(false);
		imgUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgUpdate.setIcon(new ImageIcon(Reservas.class.getResource("/img/update.png")));
		imgUpdate.setBounds(488, 342, 65, 54);
		getContentPane().add(imgUpdate);

		imgUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atualizarReserva();
			}
		});

		imgDelete = new JButton("");
		imgDelete.setEnabled(false);
		imgDelete.setBackground(new Color(240, 240, 240));
		imgDelete.setBorderPainted(false);
		imgDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgDelete.setIcon(new ImageIcon(Reservas.class.getResource("/img/delete.png")));
		imgDelete.setBounds(581, 342, 65, 54);
		getContentPane().add(imgDelete);

		imgDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// deletarReserva();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(95, 54, 738, 90);
		getContentPane().add(scrollPane);

		tblSalas = new JTable();
		scrollPane.setViewportView(tblSalas);

		JButton btnPesquisar = new JButton("");
		btnPesquisar.setEnabled(false);
		btnPesquisar.setBackground(new Color(240, 240, 240));
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setIcon(new ImageIcon(Reservas.class.getResource("/img/search.png")));
		btnPesquisar.setBounds(261, 215, 43, 33);
		getContentPane().add(btnPesquisar);

		inputID = new JTextField();
		inputID.setEnabled(false);
		inputID.setBounds(24, 160, 40, 20);
		getContentPane().add(inputID);
		inputID.setColumns(10);

		// Deixar o campo ID invisível
		inputID.setVisible(true);

		inputCategoria = new JComboBox();
		inputCategoria.setToolTipText("");
		inputCategoria.setModel(new DefaultComboBoxModel(new String[] { "", "Sala de reunião", "Sala de conferência",
				"Espaço de eventos", "Escritório privado" }));
		inputCategoria.setBounds(95, 25, 738, 22);
		getContentPane().add(inputCategoria);

		inputCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarSalaNaTabela();
			}
		});

		inputAndar = new JComboBox();
		inputAndar.setModel(
				new DefaultComboBoxModel(new String[] { "", "Subsolo", "Térreo", "1º andar", "2º andar", "3º andar" }));
		inputAndar.setBounds(95, 247, 160, 22);
		getContentPane().add(inputAndar);
		
		inputAndar.setEnabled(false);

		inputNum = new JTextField();
		inputNum.setBounds(95, 200, 160, 20);
		getContentPane().add(inputNum);
		inputNum.setColumns(10);
		
		inputNum.setEditable(false);

		responsavelReserva = new JLabel("Responsável:");
		responsavelReserva.setBounds(356, 203, 86, 14);
		getContentPane().add(responsavelReserva);

		inputResponsavel = new JTextField();
		inputResponsavel.setEditable(false);
		inputResponsavel.setBounds(456, 200, 155, 20);
		getContentPane().add(inputResponsavel);
		inputResponsavel.setColumns(10);

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBuscarSala();
			}
		});

		tblSalas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setarCaixasTexto();
				btnPesquisar.setEnabled(true);
				imgCreate.setEnabled(false);
				imgDelete.setEnabled(true);
			}
		});

		inputInicioReserva = new JDateChooser(); // Objeto da classe de calendário para a pessoa clicar
		inputInicioReserva.setEnabled(false);
		inputInicioReserva.setBounds(456, 247, 155, 20); // Posicionado o calendário na janela e o tamanho dele
		getContentPane().add(inputInicioReserva); // Adicionando o calendário na janela

		inputFimReserva = new JDateChooser();
		inputFimReserva.setEnabled(false);
		inputFimReserva.setBounds(456, 294, 155, 20);
		getContentPane().add(inputFimReserva);

		inicioReserva = new JLabel("Início da reserva:");
		inicioReserva.setBounds(356, 251, 101, 14);
		getContentPane().add(inicioReserva);

		fimReserva = new JLabel("Fim da reserva:");
		fimReserva.setBounds(356, 298, 101, 14);
		getContentPane().add(fimReserva);
		
		inputEmReforma = new JTextField();
		inputEmReforma.setEnabled(false);
		inputEmReforma.setText("Não");
		inputEmReforma.setBounds(95, 160, 86, 20);
		getContentPane().add(inputEmReforma);
		inputEmReforma.setColumns(10);

	} // Fim do construtor

	// Criar um objeto da classe DAO para estabelecer conexão com banco
	DAO dao = new DAO();
	private JTable tblSalas;
	private JTextField inputID;
	private JComboBox inputCategoria;
	private JComboBox inputAndar;
	//private JTextField inputEmReforma;
	private JTextField inputNum;
	private JLabel responsavelReserva;
	private JTextField inputResponsavel;
	private JDateChooser inputInicioReserva; // Declarando como privado o objeto calendário
	private JDateChooser inputFimReserva;
	private JLabel inicioReserva;
	private JLabel fimReserva;
	private JTextField inputEmReforma;

	private void adicionarReserva() {
		String create = "insert into reservas (idSala, responsavelReserva, inicioReserva, fimReserva)"
				+ " values (?, ?, ?, ?);";

		// Validação do responsável pela reserva
		if (inputResponsavel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Responsável pela reserva é obrigatório!");
			inputResponsavel.requestFocus();
		}

		// Validação do início da reserva
		/*
		 * else if (inputCod.getSelectedItem().equals("")) {
		 * JOptionPane.showMessageDialog(null, "Código da sala obrigatório!");
		 * inputCod.requestFocus(); }
		 * 
		 * // Validação do fim da reserva else if
		 * (inputAndar.getSelectedItem().equals("")) {
		 * JOptionPane.showMessageDialog(null, "Andar da sala obrigatório!");
		 * inputAndar.requestFocus(); }
		 */

		else {

			try {
				// Estabelecer a conexão
				Connection conexaoBanco = dao.conectar();

				// Preparar a execusão do script SQL
				PreparedStatement executarSQL = conexaoBanco.prepareStatement(create);

				// Substituir os pontos de interrogação pelo conteúdo das caixas de texto
				// (inputs)
				executarSQL.setString(1, inputID.getText());
				executarSQL.setString(2, inputResponsavel.getText());

				// Formatar a data da reserva utilizando a biblioteca JCalendar para inserção
				// CORRETA NO BANCO
				SimpleDateFormat formatadorReserva = new SimpleDateFormat("yyyyMMdd");
				String dataInicioReservaFormatada = formatadorReserva.format(inputInicioReserva.getDate());
				executarSQL.setString(3, dataInicioReservaFormatada);

				SimpleDateFormat formatadorReserva2 = new SimpleDateFormat("yyyyMMdd");
				String dataFimReservaFormatada = formatadorReserva2.format(inputFimReserva.getDate());
				executarSQL.setString(4, dataFimReservaFormatada);

				// Executar os comandos SQL e inserir a reserva no banco de dados
				executarSQL.executeUpdate();

				JOptionPane.showMessageDialog(null, "Sala reservada com sucesso!");
				// limparCampos();

				conexaoBanco.close();
			}

			catch (SQLIntegrityConstraintViolationException error) {
				JOptionPane.showMessageDialog(null, "Sala já reservada");
			}

			catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscarSalaNaTabela() {
		String readTabela = "select tipoSala as Categoria, andarSala as Andar, numeroSala as Número from salas"
				+ " where emReforma = ? and tipoSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução dos comandos SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readTabela);

			// Substituir o ? pelo conteúdo da caixa de texto
			executarSQL.setString(1, inputEmReforma.getText());
			executarSQL.setString(2, inputCategoria.getSelectedItem().toString());

			// Executar o comando SQL
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			// Exibir o resultado na tabela, utilização da biblioteca rs2xml para "popular"
			// a tabela
			tblSalas.setModel(DbUtils.resultSetToTableModel(resultadoExecucao));

			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarCaixasTexto() {

		// Criar uma variável para receber a linha da tabela
		int setarLinha = tblSalas.getSelectedRow();

		inputCategoria.setSelectedItem(tblSalas.getModel().getValueAt(setarLinha, 0).toString());

		// Setar o andar e o número da sala selecionada na linha específica da tabela
		// que o usuário clicou
		inputAndar.setSelectedItem(tblSalas.getModel().getValueAt(setarLinha, 1).toString());

		inputNum.setText(tblSalas.getModel().getValueAt(setarLinha, 2).toString());

	}

	// Criar método para buscar sala pelo botão Pefsquisar
	private void btnBuscarSala() {
		String readBtn = "select * from salas where numeroSala = ? and andarSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução do comando SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readBtn);

			// Substituir o ponto de interrogação pelo conteúdo da caixa de texto (número da
			// sala)
			executarSQL.setString(1, inputNum.getText());
			executarSQL.setString(2, inputAndar.getSelectedItem().toString());

			// Executar o comando SQL e exibir o resultado no formulário salas (todos
			// os seus dados)
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			if (resultadoExecucao.next()) {
				// Preencher os campos do formulário
				inputID.setText(resultadoExecucao.getString(1));

				imgUpdate.setEnabled(true);
				imgDelete.setEnabled(true);
				imgCreate.setEnabled(false);
				inputAndar.setEnabled(true);
				inputNum.setEditable(true);
			}

			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
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
