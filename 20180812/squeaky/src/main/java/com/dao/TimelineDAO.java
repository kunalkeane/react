package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DBConnection;
import com.User;
import com.service.UserDataAccess;


@Component
public class TimelineDAO {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;
	
	@Autowired
	DAOUtil daoUtil;
	
	public  User AllUsers(long commentId) {
			try {
				Connection con = DBConnection.getConnection();
				
				String sql = "select * from users";
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				User user = new User();
				if (rs.next()){
					user.setUserId(rs.getString("userid"));
					user.setEmailAddress(rs.getString("email"));
					user.setFirstName(rs.getString("fname"));
					user.setLastName(rs.getString("lname"));
					user.setUserName(ldapDataAccessObject.getUserNameByUserId(user.getUserId()));	
				}
				
				//comment.setHashTag(getHashTags(commentId));
				con.close();
				return user;
			} catch (Exception e) {
				new RuntimeException(e);
			}
			return null;
	    }
		
		}	

