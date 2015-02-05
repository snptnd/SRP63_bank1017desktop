package edu.pitt.bank;
import edu.pitt.utilities.ErrorLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.swing.JOptionPane;

import edu.pitt.utilities.MySqlUtilities;


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
		String sql = "SELECT * FROM srp63_bank1017.account "; 
		sql += "WHERE accountID = '" + accountID + "'";
		MySqlUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.accountID = rs.getString("accountID");
				this.setType(rs.getString("type")); 
				this.setBalance(rs.getDouble("balance"));
				this.setInterestRate(rs.getDouble("interestRate"));
				this.setPenalty(rs.getDouble("penalty"));
				this.setStatus(rs.getString("status"));
				this.setDateOpen(new Date());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql2 = "SELECT * FROM srp63_bank1017.transaction ";
		sql2 += "WHERE accountID = '" + accountID + "';";
		System.out.println(sql2);
		try {
			ResultSet rs = db.getResultSet(sql2);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				createTransaction(transactionID);
			}
		} catch (SQLException e) {
			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
			//JOptionPane.showMessageDialog(null, "invalid SQL query");
		}
		
	}
	/*
	 * Overloads the above method and creates an object of type account which is inserted into the database.
	 * 
	 */
	public Account(String accountType, double initialBalance){
		this.accountID = UUID.randomUUID().toString();
		this.setType(accountType);
		this.setBalance(initialBalance);
		this.setInterestRate(0);
		this.setPenalty(0);
		this.setStatus("active");
		this.setDateOpen(new Date());
		
		String sql = "INSERT INTO srp63_bank1017.account ";
		sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
		sql += " VALUES ";
		sql += "('" + this.accountID + "', ";
		sql += "'" + this.getType() + "', ";
		sql += this.getBalance() + ", ";
		sql += this.getInterestRate() + ", ";
		sql += this.getPenalty() + ", ";
		sql += "'" + this.getStatus() + "', ";
		sql += "CURDATE());";
		
		MySqlUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
	
	void addAccountOwner(Customer accountOwner){
		this.getAccountOwners().add(accountOwner);
	}
	
	
	public void withdraw(double amount){
		this.setBalance(this.getBalance() - amount);
		createTransaction(this.accountID, this.getType(), amount, this.getBalance());
		updateDatabaseAccountBalance();
	}
	
	
	public void deposit(double amount){
		this.setBalance(this.getBalance() + amount);
		createTransaction(this.accountID, this.getType(), amount, this.getBalance());
		updateDatabaseAccountBalance();
	}
	
	private void updateDatabaseAccountBalance(){
		String sql = "UPDATE srp63_bank1017.account SET balance = " + this.getBalance() + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";
		
		MySqlUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
	
	private Transaction createTransaction(String transactionID){
		Transaction t = new Transaction(transactionID);
		getTransactionList().add(t);
		return t;
	}
	
	private Transaction createTransaction(String accountID, String type, double amount, double balance){
		Transaction t = new Transaction(accountID, type, amount, balance);
		getTransactionList().add(t);
		return t;
	}
	
	public String getAccountID(){
		return this.accountID;
	}
	
	public double getBalance(){
		return this.balance;
	}
	public ArrayList<Customer> getAccountOwners() {
		return accountOwners;
	}
	public void setAccountOwners(ArrayList<Customer> accountOwners) {
		this.accountOwners = accountOwners;
	}
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateOpen() {
		return dateOpen;
	}
	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}
	
}
