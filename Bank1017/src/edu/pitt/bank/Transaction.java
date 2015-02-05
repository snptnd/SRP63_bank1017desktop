package edu.pitt.bank;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.mysql.jdbc.Statement;

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
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate; 
	
	public Transaction(String transactionID){
		String sql = "SELECT * FROM bank1017.transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'";
		System.out.println(sql);
		MySqlUtilities db = new MySqlUtilities();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	}
	
	public Transaction(String accountID, String type, double amount, double balance){
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;
		
		String sql = "INSERT INTO bank1017.transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE(), ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";
		
		//System.out.println(sql);
		
		MySqlUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
}
