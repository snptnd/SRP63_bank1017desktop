package edu.pitt.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;

import edu.pitt.bank.Account;
import edu.pitt.bank.Transaction;

public class TransactionUI extends JFrame {
	public TransactionUI(Account userAccount) {
		getContentPane().setLayout(null);
		this.setSize(500, 300);
		this.setLocation(210, 210);
		
		userAccount.getTransactionList();
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
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(323, 212, 89, 23);
		
		btnClose.setBounds(284, 214, 73, 23);
		
		btnClose.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				System.exit(0);
			}		
		});
		getContentPane().add(btnClose);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3177606670122428163L;
}
