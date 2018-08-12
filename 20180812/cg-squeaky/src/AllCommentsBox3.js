import React from 'react';
import './index.css';
import EditComment from './EditComment.js';
import Reply from './Reply.js';
import LikeComment from './LikeComment.js';

const monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun",
	"Jul", "Aug", "Sept", "Oct", "Nov", "Dec"
];

export default class AllCommentsBox extends React.Component {

	constructor(props) {
		super(props);
		this.comments = [];
		this.commentTexts = [];
		this.endCommentId = -1;
		this.startCommentId = -1;
	}


	getDate(date) {
		return monthNames[(date.getMonth())] + " " + date.getDate() + ", " + date.getFullYear() +
			"  " + date.getHours() + ":" + date.getMinutes();
	}

	getShortDate(date) {
		return monthNames[(date.getMonth())] + " " + date.getDate();
	}


	handleChange = (commentArray, startComment, endComment) => {
		this.commentTexts = []
		if (commentArray[0] !== undefined) {
			this.comments = Array.from(commentArray);
			this.startCommentId = startComment;
			this.endCommentId = endComment;
		}
	}

	saveComment = (edittedComment, edittedCommentId) => {
		this.props.editCommentCallBack(edittedComment, edittedCommentId);
	}

	next = () => {
		this.props.getNextComment(this.startCommentId);
	}

	prev = () => {
		this.props.getPrevComment(this.endCommentId);
	}

	getFirstComments = () => {
		this.props.getFirstComments();
	}

	getLastComments = () => {
		this.props.getLastComments();
	}

	render() {
		this.handleChange(this.props.listNameFromParent, this.props.startCommentId, this.props.endCommentId);
		var that = this;
		return (
			<div>
		  <div align="right">
		  	<a href="#" title="Newer" onClick={this.next.bind(this)}><span class="glyphicon glyphicon-step-backward"></span></a>  
		  	<a href="#" title="Latest" onClick={this.getFirstComments.bind(this)}><span class="glyphicon glyphicon-fast-backward"></span></a>  
		  	<a href="#" title="Oldest" onClick={this.getLastComments.bind(this)}><span class="glyphicon glyphicon-fast-forward"></span></a>  
		  	<a href="#" title="Older"onClick={this.prev.bind(this)}><span class="glyphicon glyphicon-step-forward"></span></a>
		  </div>
				<table class="table table-striped" width="100%"><tbody>
				{this.comments.map(function(comment) {
				var date = new Date(comment.timestamp);
				var dateTitle = that.getDate(date)
				return <tr><td><table width="100%"><tr>
					    	<td width="100%" colspan="4">{comment.text}</td>
					    </tr>
					    <tr>
					    	<td width="25%">
					    		<LikeComment comment={comment} likes={comment.likes}/>
					    	</td>
					    		
					    		<td width="25%">
					    		<Reply comment={comment}/>	
					    	</td>	
					    		
					    		<td width="25%">
						    	<div class="text-right" title={dateTitle}><small>Posted by  
						    	{(require('./CommentFunctions.js').getLoggedInUser() === comment.user) ?
						(<font color="blue">You</font>)
						: (<font>{comment.userName}</font>)
					}
						    		
						    	
						    	  on {that.getShortDate(date)}</small></div>
						    </td>
						    <td width="25%">
						    	{(require('./CommentFunctions.js').getLoggedInUser() === comment.user) &&
					<div> <EditComment saveCommentCallBack={that.saveComment} comment={comment}/></div>
					}
						    </td>
					    
					    
					</tr></table></td></tr>
			})}</tbody></table>
				<div align="right">
			  	<a href="#" title="Newer" onClick={this.next.bind(this)}><span class="glyphicon glyphicon-step-backward"></span></a>  
			  	<a href="#" title="Latest" onClick={this.getFirstComments.bind(this)}><span class="glyphicon glyphicon-fast-backward"></span></a>  
			  	<a href="#" title="Oldest" onClick={this.getLastComments.bind(this)}><span class="glyphicon glyphicon-fast-forward"></span></a>  
			  	<a href="#" title="Older"onClick={this.prev.bind(this)}><span class="glyphicon glyphicon-step-forward"></span></a>
			  </div>		
				
			</div>
			);
	}

}