package com.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Comments;
import com.dao.CommentDAO;

/**
 * @author kuaggarw
 *
 */
@Component
public class StatsCache {
	
	@Autowired
	CommentDAO commentDAO;
	
	private long lastUpdateTime = new Date().getTime();
	/*private Comments comments;*/
	
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/*public Comments getComments() {
			if(this.comments == null){
				setComments(commentDAO.getAllComments());
			}
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}*/

	public void rebuildCache(boolean editOrUpdate){
		if(editOrUpdate){
			this.lastUpdateTime = new Date().getTime();
		}
	/*	this.comments = commentDAO.getAllComments();*/
	}
	
}
