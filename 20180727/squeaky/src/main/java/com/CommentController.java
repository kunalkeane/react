package com;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CommentDAO;
import com.notification.service.EmailService;
import com.util.CommentUtil;
import com.util.StatsCache;

@RestController
public class CommentController {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private StatsCache statsCache;
	
	@Autowired
	private EmailService emailService;
	
	
	@RequestMapping(value = "/getComments", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getAllComments() {
		return commentDAO.getAllComments();
	}
	
	@RequestMapping(value = "/getLastComments/{tag:.+}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getLastComments(@PathVariable String tag) {
		return commentDAO.getLastComments(tag);
	}
	
	@RequestMapping(value = "/getNextComments/{commentId}/{tag:.+}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getNextComments(@PathVariable long commentId, @PathVariable String tag) {
		return commentDAO.getNextComments(commentId, tag);
	}
	
	@RequestMapping(value = "/getPrevComments/{commentId}/{tag:.+}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getPrevComments(@PathVariable long commentId, @PathVariable String tag) {
		return commentDAO.getPrevComments(commentId, tag);
	}
	
	@RequestMapping(value = "/getComments/tags/{tag:.+}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comments getCommentsByTag(@PathVariable String tag) {
		return commentDAO.getCommentsByTag(tag);
	}
	
	@RequestMapping(value = "/getComments/user}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  List<Comment> getCommentsByUser(@PathVariable String user) {
		return commentDAO.getCommentsByUser(user);
	}
	
	@RequestMapping(value = "/postComment", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comment postComment(@RequestBody CommentForm commentForm, HttpServletRequest request) {
		commentForm.setUser(CommentUtil.getUserIdFromRequest(request));
		Comment comment = commentDAO.postComment(commentForm); 
		statsCache.rebuildCache(true);
		return comment;
	}
	
	@RequestMapping(value = "/editComment", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Comment editComment(@RequestBody CommentForm commentForm, HttpServletRequest request) {
		commentForm.setUser(CommentUtil.getUserIdFromRequest(request));
		Comment comment = commentDAO.editComment(commentForm); 
		statsCache.rebuildCache(true);
		return comment;
	}
	
	@RequestMapping(value = "/likeComment", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public  Likes likeComment(@RequestBody CommentForm commentForm, HttpServletRequest request) {
		commentDAO.likeComment(commentForm.getCommentId(), commentForm.isLike(), CommentUtil.getUserIdFromRequest(request));
		return commentDAO.getLikesByCommentId(commentForm.getCommentId());
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
	
	@RequestMapping(value = "/getTags/{tag:.+}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  Set<String> getTags(@PathVariable String tag) {
		return commentDAO.getHashTags(tag);
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public  String sendEmail() {
		emailService.sendEmail();
		return "test";
	}
	
}	