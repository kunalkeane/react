package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CommentDAO;

@RestController
public class TimelineController {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@RequestMapping(value = "/getTimelines/{user}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getCommentsByUser(@PathVariable String user) {
		return commentDAO.getCommentsByUser(user);
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  List<User> getAllUser() {
		return commentDAO.getUsers();
	}
	
	@RequestMapping(value = "/getLastTimelines/{userId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getLastComments(@PathVariable String userId) {
		return commentDAO.getLastCommentsByUser(userId);
	}
	
	@RequestMapping(value = "/getNextTimelines/{commentId}/{userId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getNextComments(@PathVariable long commentId, @PathVariable String userId) {
		return commentDAO.getNextCommentsByUser(commentId, userId);
	}
	
	@RequestMapping(value = "/getPrevTimelines/{commentId}/{userId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getPrevComments(@PathVariable long commentId, @PathVariable String userId) {
		return commentDAO.getPrevCommentsByUser(commentId, userId);
	}

	
}	