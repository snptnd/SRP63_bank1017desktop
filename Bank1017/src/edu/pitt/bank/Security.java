package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.pitt.utilities.MySqlUtilities;

public class Security {

	/**
	 * 
	 * this class is used to check if the entered username and pin
	 * match a record in the database. If a record is found and the pin 
	 * matches then a value of true is returned.
	 * 
	 */
	
	public Customer validateLogin(String loginName, int pin){
		String sql = "SELECT * FROM srp63_bank1017.customer ";
		sql += "WHERE loginName = '" + loginName + "' ";
		sql += "and pin = " + pin + ";";
		Customer foundCustomer = new Customer(null);
		@SuppressWarnings("unused")
		boolean foundMatch = false;
		System.out.println(sql);
		
		MySqlUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if(rs.next() && rs != null){
				foundCustomer = new Customer(rs.getString("customerID"));
				foundMatch = true;
			}else {
				foundMatch = false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if(foundMatch = true){
		return foundCustomer; 
		} else {
			return null;
		}
	}
	
	public ArrayList<String> listUserGroups(String userID){
		return null;
		
	}
}
