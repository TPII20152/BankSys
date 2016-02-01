package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.persistence.SQLiteAccounts;

import java.awt.Window.Type;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormTransferencia extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumContaOrigem;
	private JTextField txtNumContaDestino;
	private JTextField txtDeposito;
	private BankController bank;

	/**
	 * Create the frame.
	 */
	public FormTransferencia() {
		bank = new BankController(new SQLiteAccounts());
		setType(Type.UTILITY);
		setTitle("TRANSFER\u00CANCIA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 233, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNmeroContaOrigem = new JLabel("N\u00DAMERO CONTA ORIGEM");
		
		txtNumContaOrigem = new JTextField();
		txtNumContaOrigem.setColumns(10);
		
		JLabel lblNmeroContaDestino = new JLabel("N\u00DAMERO CONTA DESTINO");
		
		txtNumContaDestino = new JTextField();
		txtNumContaDestino.setColumns(10);
		
		JLabel lblValorADepositar = new JLabel("VALOR A DEPOSITAR");
		
		txtDeposito = new JTextField();
		txtDeposito.setColumns(10);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnTransferir = new JButton("Transferir");
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double valor;
				try{
					valor = Double.parseDouble(txtDeposito.getText().toString());
				}catch(Exception ex){
					Object[] options = {"OK"};
					JOptionPane.showOptionDialog(null, "Valor inválido!", "Erro", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					return;
				}
				try {
					bank.doTransfer(txtNumContaOrigem.getText().toString(), txtNumContaDestino.getText().toString(),valor);
				} catch (BankTransactionException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtDeposito, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
						.addComponent(lblNmeroContaOrigem)
						.addComponent(lblValorADepositar, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTransferir, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
						.addComponent(txtNumContaOrigem, 187, 187, Short.MAX_VALUE)
						.addComponent(txtNumContaDestino, 187, 187, Short.MAX_VALUE)
						.addComponent(lblNmeroContaDestino, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNmeroContaOrigem)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNumContaOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNmeroContaDestino)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNumContaDestino, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblValorADepositar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtDeposito, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnTransferir))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

}
