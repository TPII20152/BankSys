package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.persistence.SQLiteAccounts;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FormSaque extends JFrame {

	private static final String INVALID_VALUE = "Valor invAlido!";
	private JPanel contentPane;
	private JTextField txtNumConta;
	private JTextField txtSaque;
	private BankController bank;
	
	/**
	 * Create the frame.
	 */
	public FormSaque() {
		bank = new BankController(new SQLiteAccounts());
		setType(Type.UTILITY);
		setTitle("SAQUE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 272, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("N\u00DAMERO DA CONTA");
		
		txtNumConta = new JTextField();
		txtNumConta.setColumns(10);
		
		JLabel lblValorDoSaque = new JLabel("VALOR DO SAQUE");
		
		txtSaque = new JTextField();
		txtSaque.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JButton btnDepositar = new JButton("Sacar");
		btnDepositar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double valor;
				try{
					valor = Double.parseDouble(txtSaque.getText().toString());
				}catch(Exception ex){
					Object[] options = {"OK"};
					JOptionPane.showOptionDialog(null, INVALID_VALUE, "Erro", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					return;
				}
				try {
					bank.doDebit(txtNumConta.getText().toString(),valor);
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
						.addComponent(txtSaque, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(txtNumConta, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDepositar, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(lblValorDoSaque, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNumConta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblValorDoSaque)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSaque, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDepositar)
						.addComponent(btnCancelar))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

}
