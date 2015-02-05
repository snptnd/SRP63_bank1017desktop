package edu.pitt.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import edu.pitt.bank.Security;
import edu.pitt.ui.AccountDetailsUI;

public class LoginUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2720468151261938056L;
	private JTextField txtUserName;
	private JTextField txtPass;
	public LoginUI() {
		getContentPane().setLayout(null);
		
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
		lblNewLabel.setBounds(46, 63, 69, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(46, 106, 69, 14);
		getContentPane().add(lblPassword);
		
		btnLogin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Security sec = new Security();
				sec.validateLogin(txtUserName.getText(), Integer.parseInt(txtPass.getText()));
				if(sec.validateLogin(txtUserName.getText(), Integer.parseInt(txtPass.getText())) != null){
					AccountDetailsUI adUI = new AccountDetailsUI(sec.validateLogin(txtUserName.getText(), Integer.parseInt(txtPass.getText())));
					adUI.setVisible(true);
					getContentPane().setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "invalid account credintials");
				}
			}		
		}); 
		 
		
	}
}
