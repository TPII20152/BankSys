package banksys.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import banksys.persistence.SQLiteAccounts;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FormHistorico extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumConta;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public FormHistorico() {
		setTitle("HISTORICO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNumeroDaConta = new JLabel("NUMERO DA CONTA");
		lblNumeroDaConta.setBounds(10, 11, 404, 14);
		panel.add(lblNumeroDaConta);
		
		txtNumConta = new JTextField();
		txtNumConta.setBounds(10, 36, 289, 20);
		panel.add(txtNumConta);
		txtNumConta.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 67, 404, 173);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"CONTA", "MENSAGEM"
			}
		));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(175);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQLiteAccounts sql = new SQLiteAccounts();
				Object[] linha;
				DefaultTableModel model = new DefaultTableModel(null,new String[]{"CONTA","MENSAGEM"});
				try{
					ArrayList<String[]> lista = sql.searchLog(txtNumConta.getText());
					if(lista != null){
						for(int i=0;i<lista.size();i++){
							linha = new Object[2];
							linha[0] = lista.get(i)[0];
							linha[1] = lista.get(i)[1];
							model.addRow(linha);
						}
						table.setModel(model);
					}
				}
				catch(Exception ex){
					
				}
			}
		});
		btnPesquisar.setBounds(309, 35, 105, 23);
		panel.add(btnPesquisar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(10, 251, 89, 23);
		panel.add(btnCancelar);
	}
}
