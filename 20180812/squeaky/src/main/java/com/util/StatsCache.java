package com.util;

import java.util.Date;

import javax.annotation.PostConstruct;

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
	
	private long lastUpdateTime;
	private Comments allComments;
	private boolean isCommentEdited;
	
	
	@PostConstruct
    public void init() {
		this.lastUpdateTime = new Date().getTime();
		this.allComments = commentDAO.getAllComments();
    }
	
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public Comments getAllComments() {
		/*synchronized(this){*/
			if(isCommentEdited){
				this.allComments = commentDAO.getAllComments();//refresh cache
				isCommentEdited = false;
			}
		/*}*/
		return allComments;
	}

	public /*synchronized*/ void rebuildCache(Long lastUpdateTime){
		if(lastUpdateTime != null){
			this.lastUpdateTime = lastUpdateTime;
		}
		this.isCommentEdited = true;	
	}
}
