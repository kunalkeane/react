package com;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CommentDAO;
import com.util.CommentUtil;
import com.util.StatsCache;

@RestController
public class CommentController {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private StatsCache statsCache;
	
	@RequestMapping(value = "/getComments", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getAllComments() {
		return commentDAO.getAllComments();
	}
	
	@RequestMapping(value = "/getLastComments", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getLastComments() {
		return commentDAO.getLastComments();
	}
	
	@RequestMapping(value = "/getNextComments/{commentId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getNextComments(@PathVariable long commentId) {
		return commentDAO.getNextComments(commentId);
	}
	
	@RequestMapping(value = "/getPrevComments/{commentId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getPrevComments(@PathVariable long commentId) {
		return commentDAO.getPrevComments(commentId);
	}
	
	@RequestMapping(value = "/getComments/tags/{tag}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getAllComments(@PathVariable String tag) {
		return commentDAO.getCommentsByTag(tag);
	}
	
	@RequestMapping(value = "/postComment", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comment postComment(@RequestBody CommentForm commentForm, HttpServletRequest request) {
		commentForm.setUser(request.getUserPrincipal().getName());
		return commentDAO.postComment(commentForm);
	}
	
	@RequestMapping(value = "/editComment", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comment editComment(@RequestBody CommentForm commentForm, HttpServletRequest request) {
		commentForm.setUser(request.getUserPrincipal().getName());
		return commentDAO.editComment(commentForm);
	}
	
	@RequestMapping(value = "/getComment/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comment getComment(@PathVariable int id) {
		return commentDAO.getComment(id);
	}
	
	@RequestMapping(value = "/checkNewComments/{lastTimestamp}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  NewComments checkNewComments(@PathVariable long lastTimestamp) {
		if(lastTimestamp > statsCache.getLastUpdateTime())
			return new NewComments();
		return commentDAO.getNewComments(lastTimestamp);
	}
	
}	