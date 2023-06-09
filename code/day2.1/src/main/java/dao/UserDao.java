package dao;

import java.sql.SQLException;

import pojos.User;

public interface UserDao {
//add a method for user authentication(login)
	User authenticateUser(String email, String password) throws SQLException;

	String registerNewUser(String fName,String lName,String dob,String email, String password)throws SQLException;
}
