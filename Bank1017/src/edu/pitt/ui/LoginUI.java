package edu.pitt.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.ui.AccountDetailsUI;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;

public class LoginUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2720468151261938056L;
	private JTextField txtUserName;
	private JTextField txtPass;
	public LoginUI() {
		getContentPane().setLayout(null);
		this.setSize(500, 300);
		this.setLocation(200, 200);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(209, 210, 89, 23);
		getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(308, 210, 69, 23);
		getContentPane().add(btnExit);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(142, 60, 255, 20);
		getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPass = new JTextField();
		txtPass.setBounds(142, 103, 255, 20);
		getContentPane().add(txtPass);
		txtPass.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Login Name:");
		lblNewLabel.setBounds(46, 63, 86, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(46, 106, 86, 14);
		getContentPane().add(lblPassword);
		
		
		btnLogin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Security sec = new Security();// create new security object so that we can use the validateLogin method
				// create new customer object and find the object using the values provided to validateLogin method
				Customer c = new Customer(sec.validateLogin(txtUserName.getText(), Integer.parseInt(txtPass.getText())).getCustomerID());
				//sec.validateLogin(txtUserName.getText(), Integer.parseInt(txtPass.getText()));
				if(c.getCustomerID() != null){
					AccountDetailsUI adUI = new AccountDetailsUI(c.getCustomerID());
					setVisible(false);
					adUI.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "invalid account credintials");
				}
			}		
		}); 
		
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				System.exit(0);
			}		
		});
		 
		
	}
}
