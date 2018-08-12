import React from 'react';
import './index.css';
import EditComment from './EditComment.js';
import Reply from './Reply.js';
import LikeComment from './LikeComment.js';

const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
	  "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"
	];

export default class AllCommentsBox extends React.Component {

	constructor(props) {
		super(props);
		this.comments = [];
		this.commentTexts = [];
		this.endCommentId=-1;
		this.startCommentId=-1;
	}

	
	getDate(date){
		return monthNames[(date.getMonth())]+ " "+ date.getDate() +", "+ date.getFullYear() +
		"  " + date.getHours() +":"+ date.getMinutes();
	}
	
	getShortDate(date){
		return monthNames[(date.getMonth())]+ " "+ date.getDate();
	}
	

		handleChange = (commentArray, startComment, endComment) =>{
			this.commentTexts = []
			if(commentArray[0] !== undefined){
				this.comments = Array.from(commentArray);
				this.startCommentId = startComment;
				this.endCommentId = endComment;
			}
		}

		saveComment = (edittedComment,edittedCommentId) =>{
			this.props.editCommentCallBack(edittedComment, edittedCommentId);
		}
		
		next = () =>{
			this.props.getNextComment(this.startCommentId);
		}
		
		prev = () =>{
			this.props.getPrevComment(this.endCommentId);
		}
		
		getFirstComments = () =>{
			this.props.getFirstComments();
		}
		
		getLastComments = () =>{
			this.props.getLastComments();
		}
		
	    render() {
				this.handleChange(this.props.listNameFromParent, this.props.startCommentId, this.props.endCommentId);
				var that = this;
		return (
		  <div>
		  <div align="right">
		  	<a href="#" title="Newer" onClick={this.next.bind(this)}><span class="glyphicon glyphicon-step-backward"></span></a>&nbsp;&nbsp;
		  	<a href="#" title="Latest" onClick={this.getFirstComments.bind(this)}><span class="glyphicon glyphicon-fast-backward"></span></a>&nbsp;&nbsp;
		  	<a href="#" title="Oldest" onClick={this.getLastComments.bind(this)}><span class="glyphicon glyphicon-fast-forward"></span></a>&nbsp;&nbsp;
		  	<a href="#" title="Older"onClick={this.prev.bind(this)}><span class="glyphicon glyphicon-step-forward"></span></a>
		  </div>
				<div>{this.comments.map(function(comment){
					var date = new Date(comment.timestamp);
					var dateTitle = that.getDate(date)
					return <div class="well well-sm">
					  <div>
					    <div class="container-fluid">
					    <div class="row">
					    	<div class="col-md-12">{comment.text}</div>
					    </div>
					    <div class="row">
				    		<div class="col-md-12">&nbsp;</div>
				    	</div>
					    <div class="row">
					    	<div class="col-md-4 text-left">
					    		<LikeComment comment={comment} likes={comment.likes}/>
					    	</div>
					    		
					    	<div class="col-md-3 text-left">
					    		<Reply comment={comment}/>	
					    	</div>	
					    		
					    	<div class="col-md-4">
						    	<div class="text-right" title={dateTitle}><small>
						    	{(comment.edited === "Y") ? 
						    		<span>Edited</span> 
						    	: 
						    		<span>Posted</span> 
						    	} by&nbsp; 
						    	{(require('./CommentFunctions.js').getLoggedInUser() === comment.user) ?
									(<font color="blue">You</font>)
									:(<font>{comment.userName}</font>)
								}
						    		
						    	
						    	&nbsp; on {that.getShortDate(date)}</small></div>
						    </div>
						    <div class="col-md-1 text-left">
						    	{(require('./CommentFunctions.js').getLoggedInUser() === comment.user) &&
									<div>&nbsp;<EditComment saveCommentCallBack={that.saveComment} comment={comment}/></div>
								}
						    </div>
					    </div>
					    
					    </div>
					</div>
					</div>
				})}</div>
				<div align="right">
			  	<a href="#" title="Newer" onClick={this.next.bind(this)}><span class="glyphicon glyphicon-step-backward"></span></a>&nbsp;&nbsp;
			  	<a href="#" title="Latest" onClick={this.getFirstComments.bind(this)}><span class="glyphicon glyphicon-fast-backward"></span></a>&nbsp;&nbsp;
			  	<a href="#" title="Oldest" onClick={this.getLastComments.bind(this)}><span class="glyphicon glyphicon-fast-forward"></span></a>&nbsp;&nbsp;
			  	<a href="#" title="Older"onClick={this.prev.bind(this)}><span class="glyphicon glyphicon-step-forward"></span></a>
			  </div>		
				
			</div>
	    );
	  }
	
	}