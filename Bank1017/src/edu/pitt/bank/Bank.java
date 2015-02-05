package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import edu.pitt.utilities.MySqlUtilities;

/**
 * 
 * this class loads info for account objects and customer objects to be held in array Lists
 * 
 * @param accountList an Array List which holds objects of type account. used in loadAccounts()
 * @param customerList an Array List which holds objects of type customer. used in setAccountOwners()
 */

public class Bank {

	private ArrayList<Account> accountList = new ArrayList<Account>();
	private ArrayList<Customer> customerList = new ArrayList<Customer>();

	public Bank(){ // this is the default constructor that loads at runtime. it fills the account info arraylists.
		loadAccounts();
		setAccountOwners();
	}
	
	void loadAccounts() {
		String sql = "SELECT accountID FROM bank1017.account";
		MySqlUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				// this loop takes each accountID from the result set and uses
				// it to create and add an account object to the accountList
				// ArrayList
				Account newAccount = new Account(rs.getString("accountID"));
				accountList.add(newAccount);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Account findAccount(String accountID) {
		Account tempAccount = new Account(null);
		/*
		for(Account a : accountList){
			// THIS needs to get accountID and compare it to accountID above. Requires an extra call to the database. Too much resource usage.
	        if(a.getAccountID() != null && a.getAccountID().contains(accountID)){
	        	tempAccount = new Account(a.getAccountID());
	        }
		}
		*/
		// iterates through accountList until a match is found with accountID and that object is returned
		Iterator<Account> it = accountList.iterator();
        while(it.hasNext()){
        	if(((Account) it).getAccountID() != null && ((Account) it).getAccountID().contains(accountID)){
        		tempAccount = (Account) it;
        		}
        	}
        
        return tempAccount;
	    }


	public Customer findCustomer(String customerID) {
		Customer tempCustomer = new Customer(null);

		// iterates through accountList until a match is found with accountID and that object is returned
		Iterator<Customer> it = customerList.iterator();
        while(it.hasNext()){
        	if(((Customer) it).getCustomerID() != null && ((Customer) it).getCustomerID().contains(customerID)){
        		tempCustomer = (Customer) it;
        		}
        	}
        
        return tempCustomer;
	}

	void setAccountOwners() {
		String sql = "SELECT cutomerID FROM bank1017.customer;";
		MySqlUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				// this loop takes each accountID from the result set and uses
				// it to create and add an account object to the accountList
				// ArrayList
				Customer newCustomer = new Customer(rs.getString("customerID"));
				customerList.add(newCustomer);
				Account instanceOfAccount = new Account(null);
			    instanceOfAccount.addAccountOwner(newCustomer);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
