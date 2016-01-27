package src.banksys.forms;

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

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setType(Type.UTILITY);
		setTitle("SISTEMA BANCARIO");
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
		
		JButton btnCadastrarConta = new JButton("CADASTRAR CONTA");
		btnCadastrarConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormCadastro cadastro = new FormCadastro();
				cadastro.setVisible(true);
			}
		});
		btnCadastrarConta.setBounds(10, 62, 161, 23);
		panel.add(btnCadastrarConta);
		
		JButton btnFazerDeposito = new JButton("FAZER DEPÓSITO");
		btnFazerDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormDeposito deposito = new FormDeposito();
				deposito.setVisible(true);
			}
		});
		btnFazerDeposito.setBounds(10, 96, 161, 23);
		panel.add(btnFazerDeposito);
		
		JButton btnRealizarSaque = new JButton("REALIZAR SAQUE");
		btnRealizarSaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormSaque saque = new FormSaque();
				saque.setVisible(true);
			}
		});
		btnRealizarSaque.setBounds(10, 130, 161, 23);
		panel.add(btnRealizarSaque);
		
		JButton btnTransferencia = new JButton("TRANSFERÊNCIA");
		btnTransferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormTransferencia transferencia = new FormTransferencia();
				transferencia.setVisible(true);
			}
		});
		btnTransferencia.setBounds(10, 164, 161, 23);
		panel.add(btnTransferencia);
		
		JButton btnVisualizarSaldo = new JButton("VISUALIZAR SALDO");
		btnVisualizarSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormSaldo saldo = new FormSaldo();
				saldo.setVisible(true);
			}
		});
		btnVisualizarSaldo.setBounds(181, 62, 161, 23);
		panel.add(btnVisualizarSaldo);
		
		JButton btnRemoverConta = new JButton("REMOVER CONTA");
		btnRemoverConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormRemoverConta remover = new FormRemoverConta();
				remover.setVisible(true);
			}
		});
		btnRemoverConta.setBounds(181, 96, 161, 23);
		panel.add(btnRemoverConta);
		
		JButton btnRenderJuros = new JButton("RENDER JUROS");
		btnRenderJuros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormRenderJuros renderJuros = new FormRenderJuros();
				renderJuros.setVisible(true);
			}
		});
		btnRenderJuros.setBounds(181, 130, 161, 23);
		panel.add(btnRenderJuros);
		
		JButton btnRenderBonus = new JButton("RENDER BÔNUS");
		btnRenderBonus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormRenderBonus renderBonus = new FormRenderBonus();
			}
		});
		btnRenderBonus.setBounds(181, 164, 161, 23);
		panel.add(btnRenderBonus);
		
		JButton btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(96, 198, 161, 23);
		panel.add(btnSair);
		
		JLabel lblSistemaBancrio = new JLabel("SISTEMA BANC\u00C1RIO");
		lblSistemaBancrio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSistemaBancrio.setBounds(73, 11, 208, 23);
		panel.add(lblSistemaBancrio);
	}
}
