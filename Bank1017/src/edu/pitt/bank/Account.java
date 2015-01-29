package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;


/**
 * 
 * @author Sean Patnode
 * @version 0.02
 * Creates multiple objects of type Account that are read from the result set.
 * @param accountID a string which holds the ID number to uniquely identify this object.
 * @param type distinguishes the account type between checking and savings.
 * @param balace a double which reflects the database value of the amount that this record contains.
 * @param interestRate a double which holds the interest rate for this account, assuming it is a savings account. 
 * @param penalty a double which holds the amount charged for overdrafts.
 * @param status a String which delineates whether the account is open.
 * @param dateOpen a Date which holds the date that the account was created.
 * @param transactionList an ArrayList which holds objects of type Transaction for later use
 * @param AccountOwners an ArrayList which holds objects of type Customer for later use
 */
public class Account {
	private String accountID;
	private String transactionID;
	private String type;
	private double balance;
	private double interestRate;
	private double penalty;
	private String status;
	private Date dateOpen;
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private ArrayList<Customer> accountOwners = new ArrayList<Customer>();
	
	public Account(String accountID){
		String sql = "SELECT * FROM bank1017.account "; 
		sql += "WHERE accountID = '" + accountID + "'";
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.balance = rs.getDouble("balance");
				this.interestRate = rs.getDouble("interestRate");
				this.penalty = rs.getDouble("penalty");
				this.status = rs.getString("status");
				this.dateOpen = new Date();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql2 = "SELECT * FROM bank1017.transaction ";
		sql2 += "WHERE accountID = '" + accountID + "'";
		System.out.println(sql2);
		try {
			ResultSet rs = db.getResultSet(sql2);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				createTransaction(transactionID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * Overloads the above method and creates an object of type account which is inserted into the database.
	 * 
	 */
	public Account(String accountType, double initialBalance){
		this.accountID = UUID.randomUUID().toString();
		this.type = accountType;
		this.balance = initialBalance;
		this.interestRate = 0;
		this.penalty = 0;
		this.status = "active";
		this.dateOpen = new Date();
		
		String sql = "INSERT INTO bank1017.account ";
		sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
		sql += " VALUES ";
		sql += "('" + this.accountID + "', ";
		sql += "'" + this.type + "', ";
		sql += this.balance + ", ";
		sql += this.interestRate + ", ";
		sql += this.penalty + ", ";
		sql += "'" + this.status + "', ";
		sql += "CURDATE());";
		
		DbUtilities db = new DbUtilities();
		db.executeQuery(sql);
	}
	
	void addAccountOwner(Customer accountOwner){
		accountOwners.add(accountOwner);
	}
	
	
	public void withdraw(double amount){
		this.balance -= amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}
	
	
	public void deposit(double amount){
		this.balance += amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}
	
	private void updateDatabaseAccountBalance(){
		String sql = "UPDATE bank1017.account SET balance = " + this.balance + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";
		
		DbUtilities db = new DbUtilities();
		db.executeQuery(sql);
	}
	
	private Transaction createTransaction(String transactionID){
		Transaction t = new Transaction(transactionID);
		transactionList.add(t);
		return t;
	}
	
	private Transaction createTransaction(String accountID, String type, double amount, double balance){
		Transaction t = new Transaction(accountID, type, amount, balance);
		transactionList.add(t);
		return t;
	}
	
	public String getAccountID(){
		return this.accountID;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
}
