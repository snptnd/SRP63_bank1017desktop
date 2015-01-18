package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;

public class Account {
	private String accountID;
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
	}
	
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
