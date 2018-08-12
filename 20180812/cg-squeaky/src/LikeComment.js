import React from 'react';
import ReactTooltip from 'react-tooltip'
import './index.css';

const divStyle = {
		   display: 'inline-block',
};

export default class Centered extends React.Component {
	constructor(props) {
	    super(props);
	    var count =0;
	    var commentId =-1;
	    var userNames=[]
	    var isLiked = false;
	    var isLikedByUser = false;
	    }

	allCommentOnLoad = () => {
		this.count = 0;
		this.commentId = -1
		this.userNames = [];
		this.isLikedByUser = false;
		var likeCommentJSON = this.props.comment.likes;
		if(likeCommentJSON !== null){
			this.count = likeCommentJSON.count;
			this.commentId = likeCommentJSON.commentId;
			this.userNames = Array.from(likeCommentJSON.userName);
			var loggedInUser = require('./CommentFunctions.js').getLoggedInUser();
			if(likeCommentJSON.userId !== null && likeCommentJSON.userId.indexOf(loggedInUser) > -1){
				this.isLikedByUser = true;
			}
		}
	};    

	handleLikeComment = () => {
		var that = this;
		this.isLiked = true;
		this.isLikedByUser = true;
		var likeComment = require('./CommentFunctions.js').likeComment;
		likeComment(this.props.comment.id, true,
				function(likeCommentJSON){
										that.count = likeCommentJSON.count;
										that.userNames = Array.from(likeCommentJSON.userName);
										that.setState({dummy:""})});
	};
	
	handleUnlikeComment = () => {
		var that = this;
		this.isLiked = true;
		this.isLikedByUser = false;
		var likeComment = require('./CommentFunctions.js').likeComment;
		likeComment(this.props.comment.id, false,
				function(likeCommentJSON){
										that.count = likeCommentJSON.count;
										that.userNames = Array.from(likeCommentJSON.userName);
										that.setState({dummy:""})});
	};
	
  render() {
	  if(this.isLiked !== true){
		  this.allCommentOnLoad();
	  }
	  this.isLiked = false;
    return (
      <div class="d-inline-block">
      &nbsp;&nbsp;    
      {(this.isLikedByUser === true) ? 
    		  (<a href="#" onClick={this.handleUnlikeComment}><img src= "like.png" title="Unlike" width="20" height="20"></img></a>)
	    : (<a href="#" onClick={this.handleLikeComment}><img src="unlike.png" title="Like" width="20" height="20"></img></a>)}
	   	
	      {(this.count > 0 ) &&
	    	  <font color="#EF298F">&nbsp; <span title={this.userNames}>{this.count} Likes </span> </font>
	      }
      </div>
    );
  }
}