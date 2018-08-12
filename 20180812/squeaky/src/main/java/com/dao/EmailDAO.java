package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Comment;
import com.DBConnection;
import com.service.UserDataAccess;


@Component
public class EmailDAO {
	
	@Autowired
	private UserDataAccess ldapDataAccessObject;
	@Autowired
	DAOUtil daoUtil;
	private final static String SPACE  = " ";
	
		public  String getCommentsInXHours(int hours) {
			long startTime = new Date().getTime() - hours*60*60*1000;   //convert in miliseconds
			
			try {
				String sql = "select * from comment where updatetime > ?  order by updatetime desc limit 20";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(startTime));
				ResultSet rs = ps.executeQuery();
				List<Comment> commentsList = daoUtil.getComments(rs);
				con.close();
				
				Map<Long, String> activites = new TreeMap<Long, String>();
				
				for(Comment comment : commentsList){
					StringBuilder commentBuilder = new StringBuilder();
					commentBuilder.append("<p><i><font color=blue>").append(comment.getUserName()).append("</font>").append(SPACE);
					if(comment.getReplyTo() > 0){
						commentBuilder.append("replied ");
					}else{
						commentBuilder.append("wrote ");
					}
					commentBuilder.append("</i> - ").append(SPACE).append(comment.getText()).append("</p>");
					activites.put(comment.getTimestamp(), commentBuilder.toString());
				}
				activites.putAll(getLikesInXHours(hours));
				if(activites.size() == 0){
					return null;
				}
				
				StringBuilder activityBuilder = new StringBuilder(); 
				activityBuilder.append("<html><body><br/>");
				for(String activity : activites.values()){
					activityBuilder.append(activity);
				}	
				activityBuilder.append("</body></html>");
				return activityBuilder.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  Map<Long, String> getLikesInXHours(int hours) {
			long startTime = new Date().getTime() - hours*60*60*1000;   //convert in miliseconds
			Map<Long, String> likes = new TreeMap<Long, String>();
			
			
			try {
				String sql = "select * from likes a, comment b where a.updatetime > ? and (a.comment_id=b.id) order by a.updatetime desc limit 20";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(startTime));
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					StringBuilder sBuilder = new StringBuilder();
					sBuilder.append("<p><font color=green><i>").append(ldapDataAccessObject.getUserNameByUserId(rs.getString("a.user"))).append("</i></font>").append(SPACE).append("likes").
					append(SPACE).append("<font color=brown><i>").append(ldapDataAccessObject.getUserNameByUserId(rs.getString("b.user"))).
					append("'s").append("</font></i>").append(SPACE).append("comment").append("</p>");
					
					if(rs.getTimestamp("a.updatetime") != null){
						likes.put(rs.getTimestamp("a.updatetime").getTime(), sBuilder.toString());
					}
				}
				con.close();
				return likes;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public  String[] getEmails() {
			
			List<String> emails =new ArrayList<String>();
			try {
				String sql = "select distinct email from users";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					emails.add(rs.getString("email"));
				}
				con.close();
				
				if(emails.size() > 0){
					return emails.toArray(new String[emails.size()]);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return null;
		}
		
		public static void main(String args[]){
			String emails = "kunal.aggarwal@capgemini.com";
			int start = emails.indexOf("<");
			int end = emails.indexOf(">");
			while(start != -1){
				System.out.println(emails.substring(start+1, end));
				emails = emails.replaceFirst("<", "-");
				emails = emails.replaceFirst(">", "-");
				start = emails.indexOf("<");
				end = emails.indexOf(">");
				
			}
			
			
		}
		
		
}	

