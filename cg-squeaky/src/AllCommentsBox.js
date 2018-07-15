import React from 'react';
import './index.css';
import EditComment from './EditComment.js';

const divStyle = {
		   display: 'inline-block',
};
export default class AllCommentsBox extends React.Component {

	constructor(props) {
		super(props);
		this.comments = [];
		this.commentTexts = [];
		this.endCommentId=-1;
		this.startCommentId=-1;
	}

	
	getDate(date){
		return date.getDate() +"/"+ (date.getMonth() +1)+ "/"+ date.getFullYear() +
		"  " + date.getHours() +":"+ date.getMinutes();
	}
	

		handleChange = (commentArray, startComment, endComment) =>{
			this.commentTexts = []
			if(commentArray[0] !== undefined){
				this.comments = Array.from(commentArray);
				this.startCommentId = startComment
				this.endCommentId = endComment
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
		  	<a href="#" onClick={this.next.bind(this)}><span class="glyphicon glyphicon-step-backward"></span></a>&nbsp;&nbsp;
		  	<a href="#" onClick={this.getFirstComments.bind(this)}><span class="glyphicon glyphicon-fast-backward"></span></a>&nbsp;&nbsp;
		  	<a href="#" onClick={this.getLastComments.bind(this)}><span class="glyphicon glyphicon-fast-forward"></span></a>&nbsp;&nbsp;
		  	<a href="#" onClick={this.prev.bind(this)}><span class="glyphicon glyphicon-step-forward"></span></a>
		  </div>
				<div>{this.comments.map(function(comment){
					var date = new Date(comment.timestamp);
					return <div class="well">
					  <div>
					    <div>{comment.text}</div>
					    <div>&nbsp;</div>
					    <div class="right">
					    	<div style={divStyle}><small>By: {comment.userName}&nbsp; &nbsp;at&nbsp; {that.getDate(date)}</small></div>
					    	{(require('./CommentFunctions.js').getLoggedInUser() === comment.user) &&
								<div  style={divStyle}>&nbsp;<EditComment saveCommentCallBack={that.saveComment} comment={comment}/></div>
							}
					</div>
					</div>
					</div>
				})}</div>
				<div align="right">
			  	<a href="#" onClick={this.next.bind(this)}><span class="glyphicon glyphicon-step-backward"></span></a>&nbsp;&nbsp;
			  	<a href="#" onClick={this.getFirstComments.bind(this)}><span class="glyphicon glyphicon-fast-backward"></span></a>&nbsp;&nbsp;
			  	<a href="#" onClick={this.getLastComments.bind(this)}><span class="glyphicon glyphicon-fast-forward"></span></a>&nbsp;&nbsp;
			  	<a href="#" onClick={this.prev.bind(this)}><span class="glyphicon glyphicon-step-forward"></span></a>
			  </div>		
				
			</div>
	    );
	  }
	
	}