package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

/**
 * 
 * this class is used to instantiate objects of type customer and retrieve
 * parameters associated with said objects
 * 
 * @param customerID
 *            string used to uniquely identify customer objects.
 * @param firstName
 *            String for customer's first name.
 * @param lastName
 *            String for customer's last name.
 * @param ssn
 *            string for social security number of customer.
 * @param streetAddress
 *            string for street address of customer's place of residence.
 * @param city
 *            string for city that customer lives in.
 * @param state
 *            string for state that customer lives in.
 * @param zip
 *            string that holds zip code of where customer lives
 * @param loginName
 *            a string which holds the username of the customers account
 * @param pin
 *            a String which holds the pin for the customer's account
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
	private ArrayList<Account> accountsOwned = new ArrayList<Account>();

	public ArrayList<Account> getAccountsOwned() {
		return accountsOwned;
	}
/**
 * 
 * @param customerID used to create a customer object by finding it in the database
 */
	public Customer(String customerID) {
		String sql = "SELECT * FROM srp63_bank1017.customer ";
		sql += "WHERE customerID = '" + customerID + "';";
		
		DbUtilities db = new MySqlUtilities();
		try {
			if (customerID != null) {
				ResultSet rs = db.getResultSet(sql);
				while (rs.next()) {
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
				db.closeDbConnection();
			} else {
				db.closeDbConnection();
			}
		} catch (SQLException e) {

			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
		}
	}
/**
 * used to create a new customer object
 * @param lastName string last name of customer
 * @param firstName string first name of customer
 * @param ssn string social of customer0
 * @param loginName string login name of cusotmer
 * @param pin integer pin of customer
 * @param streetAddress string address of customer
 * @param city string city of customer
 * @param state string state of customer
 * @param zip integer zip of customer
 */
	public Customer(String lastName, String firstName, String ssn,
			String loginName, int pin, String streetAddress, String city,
			String state, int zip) {
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

		String sql = "INSERT INTO srp63_bank1017.transaction ";
		sql += "(customerID, lastName, firstName, ssn, loginName, pin) ";
		sql += " VALUES ";
		sql += "('" + this.customerID + "', ";
		sql += "'" + this.getLastName() + "', ";
		sql += "'" + this.getFirstName() + "', ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.getLoginName() + "', ";
		sql += "'" + this.pin + "', ";
		sql += "'" + this.getStreetAddress() + "', ";
		sql += "'" + this.getCity() + "', ";
		sql += "'" + this.getState() + "', ";
		sql += "'" + this.getZip() + "');";

		// System.out.println(sql);

		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		db.closeDbConnection();

	}
/** 
 * 
 * @return returns accounts owned by a customer
 */
	public ArrayList<Account> retrieveAccountsOwned() {
		
		String sql = "SELECT fk_accountID FROM srp63_bank1017.customer_account ";
		sql += "WHERE fk_customerID = '" + this.customerID + "';";

		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				Account foundAccount = new Account(rs.getString("fk_accountID"));
				
				this.accountsOwned.add(foundAccount);
				;
			}
			db.closeDbConnection();
		} catch (SQLException e) {

			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
		}

		return null;

	}
/**
 * 
 * @return returns the customer's ID
 */
	public String getCustomerID() {
		return this.customerID;
	}

	/**
	 * @return returns the first name of the customer
	 */
	public String getFirstName() {
		return firstName;
	}
/**
 * 
 * @param firstName sets the first name of the customer
 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
/**
 * 
 * @return returns the last name of the customer
 */
	public String getLastName() {
		return lastName;
	}
/**
 * 
 * @param lastName sets the last name of the customer
 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
/**
 * 
 * @return returns the street address of the customer
 */
	public String getStreetAddress() {
		return streetAddress;
	}
/**
 * 
 * @param streetAddress sets the street address of the customer
 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
/**
 * 
 * @return returns the city of the customer
 */
	public String getCity() {
		return city;
	}
/**
 * 
 * @param city sets the city of the customer
 */
	public void setCity(String city) {
		this.city = city;
	}
/**
 * 
 * @return returns the state of the customer
 */
	public String getState() {
		return state;
	}
/**
 * 
 * @param state sets the state of the customer
 */
	public void setState(String state) {
		this.state = state;
	}
/**
 * 
 * @return returns the zip of the customer
 */
	public String getZip() {
		return zip;
	}
/**
 * 
 * @param zip sets the zip of the customer
 */
	public void setZip(String zip) {
		this.zip = zip;
	}
/**
 * 
 * @return ruturns the login name of the customer
 */
	public String getLoginName() {
		return loginName;
	}
/**
 * 
 * @param loginName sets the login name of the cusotmer
 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
