package edu.pitt.bank;

//import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.ui.AccountDetailsUI;
import edu.pitt.ui.LoginUI;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

public class Test {

	public static void main(String[] args) {
		/*MySqlUtilities db = new MySqlUtilities();
		String sql = "SELECT * FROM srp63_bank1017.account;";
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				System.out.println(rs.getString("accountID"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
*/
		
		LoginUI lUI = new LoginUI();
		lUI.setVisible(true);
		
		//Security s = new Security();
		//s.validateLogin("nmarcus", 8125);
		//Customer c = s.validateLogin("nmarcus", 8125);
		//System.out.println(c);
	}

}
