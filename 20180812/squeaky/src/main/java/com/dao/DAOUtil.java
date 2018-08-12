package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Comment;
import com.service.UserDataAccess;

@Component
public class DAOUtil {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;

	public List<Comment> getComments(ResultSet rs) throws SQLException{
		List<Comment> comments = new ArrayList<Comment>();
		
		while (rs.next()){
			Comment comment = new Comment();
			int commentId = rs.getInt("id");
			comment.setId(commentId);
			comment.setDisplayId(rs.getInt("displayid"));
			comment.setUser(rs.getString("user"));
			comment.setText(rs.getString("text"));
			Timestamp timestamp = rs.getTimestamp("updatetime");
			comment.setTimestamp(timestamp.getTime());
			comment.setUserName(ldapDataAccessObject.getUserNameByUserId(comment.getUser()));
			comment.setEdited(rs.getString("edited"));
			comment.setReplyTo(rs.getInt("replyTo"));
			comments.add(comment);
		}
		return comments;
	}
	
}
