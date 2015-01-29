package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.pitt.utilities.DbUtilities;

public class Security {

	
	public Customer validateLogin(String loginName, int pin){
		String sql = "SELECT * FROM bank1017.customer ";
		sql += "WHERE loginName = '" + loginName + "' ";
		sql += "and pin = " + pin + ";";
		Customer foundCustomer = new Customer(null);
		@SuppressWarnings("unused")
		boolean foundMatch = false;
		
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if(rs != null){
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
