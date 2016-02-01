package banksys.forms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.persistence.SQLiteAccounts;

public class FormDeposito extends JFrame {

	private static final String INVALID_VALUE = "Valor invAlido!";
	private JPanel contentPane;
	private JTextField txtNumConta;
	private JTextField txtDeposito;
	private BankController bank;
	/**
	 * Create the frame.
	 */
	public FormDeposito() {
		bank = new BankController(new SQLiteAccounts());
		setType(Type.UTILITY);
		setTitle("DEP\u00D3SITO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 236, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNmeroDaConta = new JLabel("N\u00DAMERO DA CONTA");
		
		txtNumConta = new JTextField();
		txtNumConta.setColumns(10);
		
		JLabel lblDepsito = new JLabel("DEP\u00D3SITO");
		
		txtDeposito = new JTextField();
		txtDeposito.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnDepositar = new JButton("Depositar");
		btnDepositar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double valor;
				try{
					valor = Double.parseDouble(txtDeposito.getText().toString());
				}catch(Exception ex){
					Object[] options = {"OK"};
					JOptionPane.showOptionDialog(null, INVALID_VALUE, "Erro", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					return;
				}
				try{
					bank.doCredit(txtNumConta.getText().toString(), valor);
				}
				catch(BankTransactionException ex){
					
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtDeposito, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblNmeroDaConta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblDepsito))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnCancelar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDepositar, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
						.addComponent(txtNumConta, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNmeroDaConta)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNumConta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDepsito)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtDeposito, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDepositar)
						.addComponent(btnCancelar))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

}
