package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Window.Type;
import javax.swing.JButton;

public class FormSaldo extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public FormSaldo() {
		setType(Type.UTILITY);
		setTitle("SALDO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 231, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNmeroDaConta = new JLabel("N\u00DAMERO DA CONTA:");
		lblNmeroDaConta.setBounds(10, 11, 109, 14);
		panel.add(lblNmeroDaConta);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 185, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSaldo = new JLabel("SALDO:");
		lblSaldo.setBounds(10, 67, 46, 14);
		panel.add(lblSaldo);
		
		JLabel lblTotal = new JLabel("total");
		lblTotal.setBounds(52, 67, 46, 14);
		lblTotal.setVisible(false);
		panel.add(lblTotal);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(10, 104, 89, 23);
		panel.add(btnVoltar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(109, 104, 89, 23);
		panel.add(btnConsultar);
	}
}
