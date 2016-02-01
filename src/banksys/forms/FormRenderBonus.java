package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.control.exception.IncompatibleAccountException;
import banksys.persistence.SQLiteAccounts;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormRenderBonus extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumConta;
	private BankController bank;

	/**
	 * Create the frame.
	 */
	public FormRenderBonus() {
		bank = new BankController(new SQLiteAccounts());
		setTitle("RENDER B\u00D4NUS");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 261, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel("N\u00DAMERO DA CONTA:");
		label.setBounds(10, 11, 208, 14);
		panel.add(label);
		
		txtNumConta = new JTextField();
		txtNumConta.setColumns(10);
		txtNumConta.setBounds(10, 36, 215, 20);
		panel.add(txtNumConta);
		
		JButton btnVoltar = new JButton("Cancelar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setBounds(10, 67, 89, 23);
		panel.add(btnVoltar);
		
		JButton btnRenderBonus = new JButton("Render B\u00F4nus");
		btnRenderBonus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					bank.doEarnBonus(txtNumConta.getText().toString());
				} catch (IncompatibleAccountException e1) {
					e1.printStackTrace();
				} catch (BankTransactionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRenderBonus.setBounds(109, 67, 116, 23);
		panel.add(btnRenderBonus);
	}

}
