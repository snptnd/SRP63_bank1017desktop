package edu.pitt.bank;
import edu.pitt.utilities.ErrorLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.swing.JOptionPane;

import edu.pitt.utilities.MySqlUtilities;
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
		String sql = "SELECT * FROM srp63_bank1017.account "; 
		sql += "WHERE accountID = '" + accountID + "'";
		DbUtilities db = new MySqlUtilities();
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
			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
		}
		
		String sql2 = "SELECT * FROM srp63_bank1017.transaction ";
		sql2 += "WHERE accountID = '" + accountID + "';";
		try {
			ResultSet rs = db.getResultSet(sql2);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				createTransaction(transactionID);
			}
			db.closeDbConnection();
		} catch (SQLException e) {
			ErrorLogger.log("SQL error");
			ErrorLogger.log(e.getMessage());
			//JOptionPane.showMessageDialog(null, "invalid SQL query");
		}
		
	}
	/**
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
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		db.closeDbConnection();
	}
	/**
	 * 
	 * @param accountOwner used to add account owners to the arraylist
	 */
	void addAccountOwner(Customer accountOwner){
		this.getAccountOwners().add(accountOwner);
	}
	
	/**
	 * 
	 * @param amount used to withdraw funds from an account
	 * withdraws funds and creates a transaction in the process
	 */
	public void withdraw(double amount){
		this.setBalance(this.getBalance() - amount);
		createTransaction(this.accountID, "Withdrawal", amount, this.getBalance());
		updateDatabaseAccountBalance();
	}
	
	/**
	 * 
	 * @param amount used to deposit funds from an account
	 * deposits funds and creates a transaction in the process
	 */
	public void deposit(double amount){
		this.setBalance(this.getBalance() + amount);
		createTransaction(this.accountID, "Deposit", amount, this.getBalance());
		updateDatabaseAccountBalance();
	}
	/**
	 * used to update the account balance after withdrawals and deposits are made
	 */
	private void updateDatabaseAccountBalance(){
		String sql = "UPDATE srp63_bank1017.account SET balance = " + this.getBalance() + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		db.closeDbConnection();
	}
	/**
	 * 
	 * @param transactionID needed to create a new transaction
	 * @return returns the new transaction object
	 */
	private Transaction createTransaction(String transactionID){
		Transaction t = new Transaction(transactionID);
		getTransactionList().add(t);
		return t;
	}
	/**
	 * creates a new transaction using the below parameters
	 * @param accountID 
	 * @param type
	 * @param amount
	 * @param balance
	 * @return returns the new transaction object
	 */
	private Transaction createTransaction(String accountID, String type, double amount, double balance){
		Transaction t = new Transaction(accountID, type, amount, balance);
		getTransactionList().add(t);
		return t;
	}
	/**
	 * 
	 * @return returns the account ID associated with this Account
	 */
	public String getAccountID(){
		return this.accountID;
	}
	/**
	 * 
	 * @return returns the balance associated with this Account
	 */
	public double getBalance(){
		return this.balance;
	}
	/**
	 * 
	 * @return returns the customers associated with this Account
	 */
	public ArrayList<Customer> getAccountOwners() {
		return accountOwners;
	}
	/**
	 * 
	 * @param accountOwners sets the customers that own this account
	 */
	public void setAccountOwners(ArrayList<Customer> accountOwners) {
		this.accountOwners = accountOwners;
	}
	/**
	 * 
	 * @return returns the transactions associated with this Account
	 */
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}
	/**
	 * 
	 * @param transactionList sets the transactions associated with this account
	 */
	public void setTransactionList(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	/**
	 * 
	 * @return returns the type of this Account
	 */
	public String getType() {
		return type;
	}
	/**
	 * 
	 * @param type sets the type of this account
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * sets the balance of this account
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	/**
	 * 
	 * @return returns the interest rate associated with this Account
	 */
	public double getInterestRate() {
		return interestRate;
	}
	/**
	 * 
	 * @param interestRate sets the interest rate of this account
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	/**
	 * 
	 * @return returns the penalty associated with this Account
	 */
	public double getPenalty() {
		return penalty;
	}
	/**
	 * 
	 * @param penalty sets the penalty of this account
	 */
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	/**
	 * 
	 * @return returns the status associated with this Account
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status sets the status of the account
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return returns the date this account was opened
	 */
	public Date getDateOpen() {
		return dateOpen;
	}
	/**
	 * 
	 * @param dateOpen sets the open date of an account
	 */
	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}
	
}
