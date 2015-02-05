package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.utilities.MySqlUtilities;

/**
 * 
 * this class is used to instantiate objects of type customer and 
 * retrieve parameters associated with said objects
 * 
 * @param customerID string used to uniquely identify customer objects.
 * @param firstName String for customer's first name.
 * @param lastName String for customer's last name.
 * @param ssn  string for social security number of customer.
 * @param streetAddress string for street address of customer's place of residence.
 * @param city string for city that customer lives in.
 * @param state string for state that customer lives in.
 * @param zip string that holds zip code of where customer lives
 * @param loginName a string which holds the username of the customers account
 * @param pin a String which holds the pin for the customer's account
 */

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
		MySqlUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.customerID = rs.getString("customerID");
				this.setFirstName(rs.getString("firstName"));
				this.setLastName(rs.getString("lastName"));
				this.ssn = rs.getString("ssn");
				this.setStreetAddress(rs.getString("streetAddress"));
				this.setCity(rs.getString("city"));
				this.setState(rs.getString("state"));
				this.setZip(rs.getString("zip"));
				this.setLoginName(rs.getString("loginName"));
				this.pin = rs.getString("pin");
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
	}
	
	public Customer(String lastName, String firstName, String ssn, String loginName, int pin, String streetAddress, String city, String state, int zip){
		this.customerID = java.util.UUID.randomUUID().toString();
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.ssn = ssn;
		this.setLoginName(loginName);
		this.pin = Integer.toString(pin);
		this.setStreetAddress(streetAddress);
		this.setCity(city);
		this.setState(state);
		this.setZip(Integer.toString(zip));
		
		String sql = "INSERT INTO bank1017.transaction ";
		sql += "(customerID, lastName, firstName, ssn, loginName, pin) ";
		sql += " VALUES ";
		sql += "('" + this.customerID + "', ";
		sql += "'" + this.getLastName() + "', ";
		sql += "'" + this.getFirstName() + "', ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.getLoginName() + "', ";
		sql += "'" + this.pin + "', ";
		sql += "'" + this.getStreetAddress() +"', ";
		sql += "'" + this.getCity() + "', ";
		sql += "'" + this.getState() + "', ";
		sql += "'" + this.getZip() + "');";
		
		
		//System.out.println(sql);
		
		MySqlUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		
	}
	
	public String getCustomerID(){
		return this.customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
