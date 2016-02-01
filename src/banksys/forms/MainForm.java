package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends JFrame {

	private static final String CREATE_ACCOUNT = "CADASTRAR CONTA";
	private static final String DEPOSIT = "FAZER DEPOSITO";
	private static final String WITHDRAW = "REALIZAR SAQUE";
	private static final String TRANSFER = "TRANSFERENCIA";
	private static final String SHOW_BALANCE = "VISUALIZAR SALDO";
	private static final String REMOVE_ACCOUNT = "REMOVER CONTA";
	private static final String EARN_ITEREST = "RENDER JUROS";
	private static final String EARN_BONUS = "RENDER BONUS";
	private static final String EXIT = "SAIR";
	private static final String BANK_SYSTEM = "SISTEMA BANCARIO";
	private static final String HISTORIC = "HISTORICO";
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public MainForm() {
		setType(Type.UTILITY);
		setTitle(BANK_SYSTEM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 377, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnCadastrarConta = new JButton(CREATE_ACCOUNT);
		btnCadastrarConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormCadastro cadastro = new FormCadastro();
				cadastro.setVisible(true);
			}
		});
		btnCadastrarConta.setBounds(10, 62, 161, 23);
		panel.add(btnCadastrarConta);
		
		JButton btnFazerDeposito = new JButton(DEPOSIT);
		btnFazerDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormDeposito deposito = new FormDeposito();
				deposito.setVisible(true);
			}
		});
		btnFazerDeposito.setBounds(10, 96, 161, 23);
		panel.add(btnFazerDeposito);
		
		JButton btnRealizarSaque = new JButton(WITHDRAW);
		btnRealizarSaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormSaque saque = new FormSaque();
				saque.setVisible(true);
			}
		});
		btnRealizarSaque.setBounds(10, 130, 161, 23);
		panel.add(btnRealizarSaque);
		
		JButton btnTransferencia = new JButton(TRANSFER);
		btnTransferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormTransferencia transferencia = new FormTransferencia();
				transferencia.setVisible(true);
			}
		});
		btnTransferencia.setBounds(10, 164, 161, 23);
		panel.add(btnTransferencia);
		
		JButton btnVisualizarSaldo = new JButton(SHOW_BALANCE);
		btnVisualizarSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormSaldo saldo = new FormSaldo();
				saldo.setVisible(true);
			}
		});
		btnVisualizarSaldo.setBounds(181, 62, 161, 23);
		panel.add(btnVisualizarSaldo);
		
		JButton btnRemoverConta = new JButton(REMOVE_ACCOUNT);
		btnRemoverConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormRemoverConta remover = new FormRemoverConta();
				remover.setVisible(true);
			}
		});
		btnRemoverConta.setBounds(181, 96, 161, 23);
		panel.add(btnRemoverConta);
		
		JButton btnRenderJuros = new JButton(EARN_ITEREST);
		btnRenderJuros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormRenderJuros renderJuros = new FormRenderJuros();
				renderJuros.setVisible(true);
			}
		});
		btnRenderJuros.setBounds(181, 130, 161, 23);
		panel.add(btnRenderJuros);
		
		JButton btnRenderBonus = new JButton(EARN_BONUS);
		btnRenderBonus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormRenderBonus renderBonus = new FormRenderBonus();
				renderBonus.setVisible(true);
			}
		});
		btnRenderBonus.setBounds(181, 164, 161, 23);
		panel.add(btnRenderBonus);
		
		JButton btnSair = new JButton(EXIT);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(181, 198, 161, 23);
		panel.add(btnSair);
		
		JLabel lblSistemaBancrio = new JLabel(BANK_SYSTEM);
		lblSistemaBancrio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSistemaBancrio.setBounds(73, 11, 208, 23);
		panel.add(lblSistemaBancrio);
		
		JButton btnHistorico = new JButton(HISTORIC);
		btnHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormHistorico historico = new FormHistorico();
				historico.setVisible(true);
			}
		});
		btnHistorico.setBounds(10, 198, 161, 23);
		panel.add(btnHistorico);
	}
}
