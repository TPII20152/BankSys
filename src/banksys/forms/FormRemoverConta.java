package src.banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;

public class FormRemoverConta extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumConta;

	/**
	 * Create the frame.
	 */
	public FormRemoverConta() {
		setTitle("REMOVER CONTA");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 239, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel("N\u00DAMERO DA CONTA:");
		label.setBounds(10, 11, 109, 14);
		panel.add(label);
		
		txtNumConta = new JTextField();
		txtNumConta.setColumns(10);
		txtNumConta.setBounds(10, 36, 185, 20);
		panel.add(txtNumConta);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(10, 67, 89, 23);
		panel.add(btnVoltar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(109, 67, 89, 23);
		panel.add(btnRemover);
	}

}
