package edu.pitt.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Account;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;


public class AccountDetailsUI extends JFrame {
	Account userAccount = new Account(null);
	public AccountDetailsUI(final Customer c) {
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(c.getFirstName() + " " + c.getLastName() + " , welcome to 1017 bank. You have the following permissions in");
		lblNewLabel.setBounds(30, 11, 382, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblThisSystemAdministrator = new JLabel("this system; Administrator, Branch Manager, Customer");
		lblThisSystemAdministrator.setBounds(30, 27, 327, 14);
		getContentPane().add(lblThisSystemAdministrator);
		
		userAccount = new Account(c.getCustomerID());
		JComboBox<String> comboBox = new JComboBox<String>();// may need to be of type customer or changed to string...
		comboBox.setBounds(118, 63, 239, 20);
		getContentPane().add(comboBox);
		for(int i = 0; i < userAccount.getAccountOwners().size(); i++)
		{
		 comboBox.addItem(userAccount.getAccountOwners().get(i).toString());
		}
		
		JLabel lblYourAccounts = new JLabel("Your accounts:");
		lblYourAccounts.setBounds(30, 66, 78, 14);
		getContentPane().add(lblYourAccounts);
		
		JLabel lblAccountType = new JLabel("Account type: " + userAccount.getType());
		lblAccountType.setBounds(30, 118, 144, 14);
		getContentPane().add(lblAccountType);
		
		JLabel lblBalance = new JLabel("Balance: " + userAccount.getBalance());
		lblBalance.setBounds(30, 134, 106, 14);
		getContentPane().add(lblBalance);
		
		JLabel lblInterestRate = new JLabel("Interest rate: " + userAccount.getInterestRate());
		lblInterestRate.setBounds(30, 149, 106, 14);
		getContentPane().add(lblInterestRate);
		
		JLabel label = new JLabel("Penalty: " + userAccount.getPenalty());
		label.setBounds(30, 159, 127, 20);
		getContentPane().add(label);
		
		JLabel lblAmount = new JLabel("Amount: ");
		lblAmount.setBounds(146, 132, 55, 19);
		getContentPane().add(lblAmount);
		
		textField = new JTextField();
		textField.setBounds(193, 131, 164, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(169, 158, 89, 23);
		getContentPane().add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(268, 158, 89, 23);
		getContentPane().add(btnWithdraw);
		
		JButton btnShowTransaction = new JButton("Show Transactions");
		btnShowTransaction.setBounds(130, 214, 144, 23);
		
		btnShowTransaction.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				Account account1 = new Account(c.getCustomerID());
					TransactionUI tUI = new TransactionUI(account1);
					tUI.setVisible(true);
			}		
		});
		getContentPane().add(btnShowTransaction);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(284, 214, 73, 23);
		
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				System.exit(0);
			}		
		});
		
		getContentPane().add(btnExit);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8757383188245064161L;
	private JTextField textField;
}
