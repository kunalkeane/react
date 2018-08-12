package com;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ReplyDAO;
import com.util.CommentUtil;
import com.util.StatsCache;

@RestController
public class ReplyController {
	
	@Autowired
	private ReplyDAO replyDAO;
	
	@Autowired
	private StatsCache statsCache;
	
	@RequestMapping(value = "/postReply", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comment postComment(@RequestBody CommentForm commentForm, HttpServletRequest request) {
		commentForm.setUser(CommentUtil.getUserIdFromRequest(request));
		Comment comment = replyDAO.replyComment(commentForm);
		statsCache.rebuildCache(new Date().getTime());
		return comment;
	}
	
	@RequestMapping(value = "/getReplies/comment/{commentId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getCommentsByTag(@PathVariable long commentId) {
		return replyDAO.getRepliesForComments(commentId);
	}

	@RequestMapping(value = "/deleteReply/comment/{commentId}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public  void deleteReply(@PathVariable long commentId) {
		replyDAO.deleteReply(commentId);
	}
	
	
}	