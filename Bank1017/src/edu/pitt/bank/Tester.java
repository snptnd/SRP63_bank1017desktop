package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import edu.pitt.ui.LoginUI;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.DbUtilities;



public class Tester {

	public static void main(String[] args) {
		/*DbUtilities db = new MySqlUtilities();
		ArrayList<Account> accountList = new ArrayList<Account>();
		Map<String, Account> betterAccountList = new Hashtable<String, Account>();// stores keys of type strings and object of type account
		
		String sql ="SELET * FROM account";
		ResultSet rs;
		try {
			rs = db.getResultSet(sql);
			while(rs.next()){
				Account a = new Account(rs.getString("accountID"));
				accountList.add(a);
				betterAccountList.put(rs.getString("accountID"), a);
				System.out.println(rs.getString("accountID"));
				
			}
			db.closeDbConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(Account acct : accountList){
			if(acct.getAccountID())
			System.out.println(acct);
		}
		*/
		LoginUI lUI = new LoginUI();
		lUI.setVisible(true);
		
	}

}
