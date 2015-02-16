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
import edu.pitt.utilities.ErrorLogger;

/**
 * 
 * @author Sean Patnode
 * used to display Account details
 * and control changes to account
 *
 */
public class AccountDetailsUI extends JFrame {
	Customer user = new Customer(null);
	Account userAccount = new Account(null);
	JComboBox<String> comboBox = new JComboBox<String>();
	private JLabel lblAccountType = new JLabel("");
	private JLabel lblBalance = new JLabel("");
	private JLabel lblInterestRate = new JLabel("");
	private JLabel lblPenalty = new JLabel("");
	private JLabel lblAmount = new JLabel("Amount: ");
	private JTextField txtAmount = new JTextField();
	
	/**
	 * sets all the fields ie jLabels and buttons
	 * @param cID used to instantiate a customer object using its ID
	 */
	public AccountDetailsUI(String cID) {
		getContentPane().setLayout(null);
		this.setSize(500, 300);
		this.setLocation(200, 200);
		user = new Customer(cID);// use this to retrieve account info for specific customer
		user.retrieveAccountsOwned();// use this to check database for accounts owned by this customer
		userAccount = new Account(user.getAccountsOwned().get(0).getAccountID());
		JLabel lblNewLabel = new JLabel(user.getFirstName() + " " + user.getLastName() + " , welcome to 1017 bank. You have the following permissions in");
		lblNewLabel.setBounds(30, 11, 444, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblThisSystemAdministrator = new JLabel("this system; Administrator, Branch Manager, Customer");
		lblThisSystemAdministrator.setBounds(30, 27, 444, 14);
		getContentPane().add(lblThisSystemAdministrator);
		comboBox.setBounds(118, 63, 239, 20);
		getContentPane().add(comboBox);
		for(int i = 0; i < user.getAccountsOwned().size(); i++)
		{
		 comboBox.addItem(user.getAccountsOwned().get(i).getAccountID());
		}

		JLabel lblYourAccounts = new JLabel("Your accounts:");
		lblYourAccounts.setBounds(30, 66, 127, 14);
		getContentPane().add(lblYourAccounts);
		
		lblAccountType = new JLabel("Account type: " + userAccount.getType());
		lblAccountType.setBounds(30, 118, 144, 14);
		getContentPane().add(lblAccountType);
		
		lblBalance = new JLabel("Balance: " + userAccount.getBalance());
		lblBalance.setBounds(30, 134, 184, 14);
		getContentPane().add(lblBalance);
		
		lblInterestRate = new JLabel("Interest rate: " + userAccount.getInterestRate());
		lblInterestRate.setBounds(30, 149, 144, 14);
		getContentPane().add(lblInterestRate);
		
		lblPenalty = new JLabel("Penalty: " + userAccount.getPenalty());
		lblPenalty.setBounds(30, 159, 144, 20);
		getContentPane().add(lblPenalty);
		
		lblAmount = new JLabel("Amount: ");
		lblAmount.setBounds(224, 132, 55, 19);
		getContentPane().add(lblAmount);
		
		txtAmount.setBounds(277, 131, 164, 20);
		getContentPane().add(txtAmount);
		txtAmount.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(247, 158, 89, 23);
		getContentPane().add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(352, 158, 89, 23);
		getContentPane().add(btnWithdraw);
		
		JButton btnShowTransaction = new JButton("Show Transactions");
		btnShowTransaction.setBounds(247, 214, 144, 23);
		
		btnShowTransaction.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				//Account account1 = new Account(userAccount.getAccountID());
				if(userAccount.getAccountID() != null){
					TransactionUI tUI = new TransactionUI(comboBox.getSelectedItem()); // use combo box value here
					JFrame frame = tUI.getFrame();
					tUI.getFrame().setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
				} else {
					ErrorLogger.log("AccountDetailsUI.Java: actionPerformed - account object cannot be null");
				}
			}		
		});
		getContentPane().add(btnShowTransaction);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(401, 214, 73, 23);
		
		comboBox.addActionListener(new ActionListener(){
			Account tempAccount = new Account(comboBox.getSelectedItem().toString());
			public void actionPerformed(ActionEvent arg1){
				lblPenalty.setText("Penalty: " + tempAccount.getPenalty());
				lblBalance.setText("Balance: " + tempAccount.getBalance());
				lblInterestRate.setText("Interest rate: " + tempAccount.getInterestRate());
				lblAccountType.setText("Account type: " + tempAccount.getType());
				// update labels here!!!
			}
		});
		
		btnWithdraw.addActionListener(new ActionListener(){
			@SuppressWarnings("unused")
			String holder;
			Account tempAccount = new Account(comboBox.getSelectedItem().toString());
			@Override
			public void actionPerformed(ActionEvent arg0){
				holder = txtAmount.getText().replace(".", "");
				if(txtAmount.getText().replace(".", "").matches("[0-9]+")){
					userAccount.withdraw(Double.parseDouble(txtAmount.getText()));
					lblBalance.setText("Balance: " + tempAccount.getBalance());
					lblBalance.paintImmediately(lblBalance.getVisibleRect());
					// this wont update!!! why???
				}else{
					JOptionPane.showMessageDialog(null, "Amount must be numerical");
				}

			}		
		});
		
		btnDeposit.addActionListener(new ActionListener(){
			@SuppressWarnings("unused")
			String holder;
			Account tempAccount = new Account(comboBox.getSelectedItem().toString());
			@Override
			public void actionPerformed(ActionEvent arg0){	
				holder = txtAmount.getText().replace(".", "");
				if(txtAmount.getText().replace(".", "").matches("[0-9]+")){
					userAccount.deposit(Double.parseDouble(txtAmount.getText()));
					lblBalance.setText("Balance: " + tempAccount.getBalance());
					lblBalance.paintImmediately(lblBalance.getVisibleRect());
					// this wont update!!! why???
				}else{
					JOptionPane.showMessageDialog(null, "Amount must be numerical");
				}
			}		
		});
		
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){	
				System.exit(0);
			}		
		});
		
		getContentPane().add(btnExit);
	}

}
