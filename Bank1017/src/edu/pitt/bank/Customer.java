package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.utilities.DbUtilities;

public class Customer {
	private String customerID;
	private String firstName;
	private String lastName;
	private String ssn;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String loginName;
	private String pin;
	
	public Customer(String customerID){
		String sql = "SELECT * FROM bank1017.customer "; 
		sql += "WHERE customerID = '" + customerID + "'";
		System.out.println(sql);
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.customerID = rs.getString("customerID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.city = rs.getString("city");
				this.state = rs.getString("state");
				this.zip = rs.getString("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getString("pin");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public Customer(String lastName, String firstName, String ssn, String loginName, int pin, String streetAddress, String city, String state, int zip){
		this.customerID = java.util.UUID.randomUUID().toString();
		this.lastName = lastName;
		this.firstName = firstName;
		this.ssn = ssn;
		this.loginName = loginName;
		this.pin = Integer.toString(pin);
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = Integer.toString(zip);
		
		String sql = "INSERT INTO bank1017.transaction ";
		sql += "(customerID, lastName, firstName, ssn, loginName, pin) ";
		sql += " VALUES ";
		sql += "('" + this.customerID + "', ";
		sql += "'" + this.lastName + "', ";
		sql += "'" + this.firstName + "', ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.loginName + "', ";
		sql += "'" + this.pin + "', ";
		sql += "'" + this.streetAddress +"', ";
		sql += "'" + this.city + "', ";
		sql += "'" + this.state + "', ";
		sql += "'" + this.zip + "');";
		
		
		//System.out.println(sql);
		
		DbUtilities db = new DbUtilities();
		db.executeQuery(sql);
		
	}
	
	public String getCustomerID(){
		return this.customerID;
	}
	
}
