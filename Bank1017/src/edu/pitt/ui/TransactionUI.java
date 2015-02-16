package edu.pitt.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import edu.pitt.bank.Account;
import edu.pitt.bank.Transaction;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class TransactionUI/* extends JFrame*/ {
	private JFrame  frame;
	private JScrollPane transactionPane;// used to display the selected account info
	private JTable tblTransactions;
	
	public TransactionUI(Object object) {
		
		/*userAccount.getTransactionList();
		JTextArea textArea = new JTextArea(""
				+ "Type"
				+ "\t" 
				+ "Date"
				+ "\t" 
				+ "Amount"
				+ "\n");
		for (Transaction t : userAccount.getTransactionList()) {
			textArea.setText(textArea.getText() 
					+ t.getType() 
					+ "\t" 
					+ t.getTransactionDate()
					+ "\t" 
					+ t.getAmount()
					+ "\n");
	       }
		
		textArea.setBounds(23, 27, 389, 174);
		getContentPane().add(textArea);
*/
		frame = new JFrame();
		frame.setTitle("Account Transactions");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		transactionPane = new JScrollPane();
		frame.getContentPane().add(transactionPane);
		//transactionPane.getViewport().setView(tblTransactions);
		DbUtilities db = new MySqlUtilities();
		String [] cols = {"Type", "Amount", "Date"};
		String sql = "SELECT type, amount, transactionDate FROM srp63_bank1017.transaction;";

		try {
			//System.out.println("use getDataTable()");
			DefaultTableModel transactionList = db.getDataTable(sql, cols);
			//System.out.println("number of columns: " + transactionList.getColumnCount());
			//System.out.println("getDataTable() used");
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.BLACK);
			//transactionPane.getViewport().add(tblTransactions);
			transactionPane.setViewportView(tblTransactions);
			db.closeDbConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
	}
		
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Close");
        buttons.add(btnClose);
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);

		btnClose.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				frame.setVisible(false);
			}		
		});
	}
	
	public JFrame getFrame() {
		return frame;
	} 
}
