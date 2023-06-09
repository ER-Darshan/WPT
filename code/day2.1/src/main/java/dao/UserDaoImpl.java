package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static utils.DBUtils.*;

import pojos.User;

public class UserDaoImpl implements UserDao {
	// fields
	private Connection cn;
	private PreparedStatement pst1;
	private PreparedStatement pst2;


	public UserDaoImpl() throws SQLException {
		// open conn
		cn = openConnection();
		pst1 = cn.prepareStatement("select * from users where email=? and password=?");
		System.out.println("user dao created!");
		//cn=openConnection();
		pst2=cn.prepareStatement("insert into users values (default,?,?,?,?,?,?,?)");
		
	//caller servlet(login)
	}
	//add method add user
/*register new voter throws sql exception 
 * 
 * 
 * */
@Override
	public String registerNewUser(String fName,String lName,String dob,String email, String password) throws SQLException{
		Date d=Date.valueOf(dob);
		if(Period.between(LocalDate.parse(dob),LocalDate.now()).getYears()>21) {
		pst2.setString(1,fName);
		pst2.setString(2,lName);
		pst2.setString(3,email);
		pst2.setString(4,password);
		pst2.setDate(5,d);
		pst2.setInt(6, 0);
		pst2.setString(7, "voter");
		}else {
			return "Faliure";
		}
		int rst2=pst2.executeUpdate();
			if(rst2>0) 
			{		
				return "success";
			}	/*
				 * int id, String firstName, String lastName, String email, String password, Date dob,
			boolean votingStatus, String role
				 */
//				return new User(rst2.getInt(1),fName, lName, email, password, 
//						d, rst2.getBoolean(7), rst2.getString(8));//oRM-obj. realtional mapping
		
		return "Not success";

}
	@Override
	public User authenticateUser(String email, String password) throws SQLException {
		// set IN params
		pst1.setString(1, email);		
		pst1.setString(2, password);
		try(ResultSet rst=pst1.executeQuery()) {
			if(rst.next()) //=> success
				/*
				 * int id, String firstName, String lastName, String email, String password, Date dob,
			boolean votingStatus, String role
				 */
				return new User(rst.getInt(1), rst.getString(2), rst.getString(3), email, password, 
						rst.getDate(6), rst.getBoolean(7), rst.getString(8));//oRM-obj. realtional mapping
		}
		return null;
	}

	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		closeConnection();
		System.out.println("user dao cleaned up!");
	}

}
