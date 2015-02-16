package edu.pitt.bank;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.mysql.jdbc.Statement;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

/**
 * 
 * this class is used to create objects of type transaction. New transactions can be inserted 
 * into the database.
 * 
 * @param transactionID string for unique identifier for transaction object
 * @param accountID String for ID of account the transaction is affecting
 * @param type String type of transaction (withdrawl/deposit).
 * @param amount String for amount withdrawn or deposited.
 * @param balance String for the balance at the time of the transaction.
 * @param transactionDate Date which holds the date that the transaction object was instantiated
 * @param state state that customer lives in
 * @param zip string that holds zip code of where customer lives
 * @param loginName a string which holds the username of the customers account
 * @param pin a String which holds the pin for the customer's account
 */

public class Transaction {
	private String transactionID;
	private String accountID;
	public String getTransactionID() {
		return transactionID;
	}
/**
 * 
 * @param transactionID string used to set transaction ID
 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * 
	 * @return returns the balance of a transaction
	 */
	public double getBalance() {
		return this.balance;
	}
/**
 * 
 * @param balance used to set the balance
 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
/**
 * 
 * @return returns the account ID of this transaction object
 */
	public String getAccountID() {
		return this.accountID;
	}

	
	/**
	 *  this class is used to find transactions in the database using a transaction ID
	 *  each parameter corresponds to a column in the database
	 */
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate; 
	
	public Transaction(String transactionID){
		String sql = "SELECT * FROM srp63_bank1017.transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");
				this.transactionDate = new Date();
			}
			db.closeDbConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
		} 

		
	}
	/** 
	 * used to create a record in the database
	 * @param accountID needed in the SQL INSERT statement
	 * @param type needed in the SQL INSERT statement
	 * @param amount needed in the SQL INSERT statement
	 * @param balance needed in the SQL INSERT statement
	 */
	public Transaction(String accountID, String type, double amount, double balance){
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;
		
		String sql = "INSERT INTO srp63_bank1017.transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE(), ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";  
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		db.closeDbConnection();
	}
/**
 * 
 * @return returns the transaction type
 */
	public String getType() {
		return type;
	}
/**
 * @param type used to set the transaction type
 */
	public void setType(String type) {
		this.type = type;
	}
/**
 * 
 * @return used to return the transaction amount
 */
	public double getAmount() {
		return amount;
	}
/**
 * 
 * @param amount used to set the transaction amount
 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
/**
 * 
 * @return used to get the transaction date
 */
	public Date getTransactionDate() {
		return transactionDate;
	}
/**
 * 
 * @param transactionDate used to set the transaction date
 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
}
