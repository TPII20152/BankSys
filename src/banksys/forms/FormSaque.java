package src.banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;

public class FormSaque extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumConta;
	private JTextField txtNumDeposito;

	/**
	 * Create the frame.
	 */
	public FormSaque() {
		setType(Type.UTILITY);
		setTitle("SAQUE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 232, 189);
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
		
		JLabel label_1 = new JLabel("DEP\u00D3SITO");
		
		txtNumDeposito = new JTextField();
		txtNumDeposito.setColumns(10);
		
		JButton btnCancelar = new JButton("CANCELAR");
		
		JButton btnDepositar = new JButton("DEPOSITAR");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumConta, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumDeposito, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnDepositar, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(232, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addGap(6)
					.addComponent(txtNumConta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(label_1)
					.addGap(6)
					.addComponent(txtNumDeposito, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancelar)
						.addComponent(btnDepositar))
					.addContainerGap(125, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

}
