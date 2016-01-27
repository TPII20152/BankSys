package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumConta;
	//private SQLiteContas database;

	/**
	 * Create the frame.
	 */
	public FormCadastro() {
		//database = new SQLiteContas();
		setType(Type.UTILITY);
		setTitle("CADASTRO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 236, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("TIPO DE CONTA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		final JRadioButton rdbtnEspecial = new JRadioButton("ESPECIAL");
		
		final JRadioButton rdbtnImposto = new JRadioButton("IMPOSTO");
		
		final JRadioButton rdbtnComum = new JRadioButton("COMUM");
		rdbtnComum.setSelected(true);
		
		final JRadioButton rdbtnPoupanca = new JRadioButton("POUPAN\u00C7A");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(40)
					.addComponent(lblNewLabel))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addComponent(rdbtnComum)
					.addGap(12)
					.addComponent(rdbtnPoupanca))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addComponent(rdbtnEspecial)
					.addGap(2)
					.addComponent(rdbtnImposto))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblNewLabel)
					.addGap(2)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnComum)
						.addComponent(rdbtnPoupanca))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnEspecial)
						.addComponent(rdbtnImposto)))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JLabel lblNmeroDaConta = new JLabel("N\u00DAMERO DA CONTA");
		lblNmeroDaConta.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtNumConta = new JTextField();
		txtNumConta.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnCadastrar = new JButton("Cadastrar");
		/*btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					ContaAbstrata conta;
					if(rdbtnComum.isSelected()){
						conta = new Conta(txtNumConta.getText());
					}
					else if(rdbtnEspecial.isSelected()){
						conta = new ContaEspecial(txtNumConta.getText());
					}
					else if(rdbtnImposto.isSelected()){
						conta = new ContaImposto(txtNumConta.getText());
					}
					else{
						conta = new ContaPoupanca(txtNumConta.getText());
					}
					database.inserir(conta);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});*/
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNumConta, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
						.addComponent(lblNmeroDaConta)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(btnCancelar)
							.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
							.addComponent(btnCadastrar)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNmeroDaConta)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNumConta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancelar)
						.addComponent(btnCadastrar))
					.addContainerGap(105, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnComum);
		bg.add(rdbtnEspecial);
		bg.add(rdbtnImposto);
		bg.add(rdbtnPoupanca);
		
	}

}
