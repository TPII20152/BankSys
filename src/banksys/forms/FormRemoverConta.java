package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.persistence.SQLiteAccounts;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormRemoverConta extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumConta;
	private BankController bank;

	/**
	 * Create the frame.
	 */
	public FormRemoverConta() {
		bank = new BankController(new SQLiteAccounts());
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
		label.setBounds(10, 11, 193, 14);
		panel.add(label);
		
		txtNumConta = new JTextField();
		txtNumConta.setColumns(10);
		txtNumConta.setBounds(10, 36, 193, 20);
		panel.add(txtNumConta);
		
		JButton btnVoltar = new JButton("Cancelar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setBounds(10, 67, 89, 23);
		panel.add(btnVoltar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					bank.removeAccount(txtNumConta.getText().toString());
				} catch (BankTransactionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemover.setBounds(109, 67, 94, 23);
		panel.add(btnRemover);
	}

}
