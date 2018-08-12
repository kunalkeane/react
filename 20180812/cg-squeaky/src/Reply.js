import React from 'react';
import Modal from 'react-responsive-modal';
import './index.css';
import LikeComment from './LikeComment.js';

const divStyle = {
		   display: 'inline-block',
};

const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
	  "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"
	];


export default class Centered extends React.Component {
	constructor(props) {
	    super(props);
	    this.state = {open: false};
	    this.comments=[];
	    this.onload = true;
		}

	getDate(date){
		return monthNames[(date.getMonth())]+ " "+ date.getDate() +", "+ date.getFullYear() +
		"  " + date.getHours() +":"+ date.getMinutes();
	}
	
	getShortDate(date){
		return monthNames[(date.getMonth())]+ " "+ date.getDate();
	}
	
	
  onOpenModal = () => {
	  this.fetchCommentsReply();
  };

  onCloseModal = () => {
	this.onload = true;
    this.setState({ open: false });
  };

  fetchCommentsReply = () => {
		var that = this;
		var fetchReplies = require('./CommentFunctions.js').fetchReplies;
		fetchReplies(require('./api_url.js').getRepliesForComment+this.props.comment.id, this, function(){that.setState({ open: true})
		});
	};
	
	fetchCommentsReplyOnLoad = () => {
		this.onload = false;
		var that = this;
		this.comments = [];
		var fetchReplies = require('./CommentFunctions.js').fetchReplies;
		fetchReplies(require('./api_url.js').getRepliesForComment+this.props.comment.id, this, 
				function(){that.setState({ dummy: ""}, function(){
					that.onload = true;
				})
		});
	};

 deleteReply = (replyId) =>{
	 	var deleteReply = require('./CommentFunctions.js').deleteReply;
		deleteReply(replyId, this.fetchCommentsReply);
		document.getElementById("reply").value="";
		
		}

	
  
  postReply = (e) =>{
	  	this.onload = false;
		var reply = document.getElementById("reply").value;
		if(reply === null || reply.trim().length <1){
			return;
		}
		var replyTo = document.getElementById("replyTo").value;
		var replyComment = require('./CommentFunctions.js').replyComment;
		replyComment(reply, replyTo, this.fetchCommentsReply);
		document.getElementById("reply").value="";
		
		}

  render() {
	  if(this.onload === true){
		  this.fetchCommentsReplyOnLoad();
	  }
	  
    const { open } = this.state;
    var that = this;
    return (
    		
      <div style={divStyle}>
      
      <a href="#" onClick={this.onOpenModal}>
	  
      {(this.comments !== null && this.comments.length > 0 ) ?
      	  <font color="#acf441"><span class="glyphicon glyphicon-comment" title={this.comments.length +" Replies"}></span></font>
      :
    	  <font color="#DAF7A6"><span class="glyphicon glyphicon-comment" title={"Be the 1st one to reply " + this.props.comment.userName}></span></font>
        }  
      </a>
        
        <Modal open={open} onClose={this.onCloseModal}  reply>
	        <div>
	        <div>&nbsp; </div><div>&nbsp; </div>
	        <div class="bg-primary"><span>Replying to "{this.props.comment.text}" : By {this.props.comment.userName}</span></div> 
	        <table class="table table-striped">
	        <tbody>
		        {this.comments.map(function(comment){
		        	
		        	var date = new Date(comment.timestamp);
					var dateTitle = that.getDate(date)
					
		        	return (<tr>
		            			<td><div>{comment.text}</div>
		            			<div>&nbsp;</div>
				            <div>
				            	<table><tr>
				            		<td width="50%"><LikeComment comment={comment} likes={comment.likes}/></td>
				            		<td width="48%">
				            			<div class="text-center" title={dateTitle}><small>Replied by&nbsp; 
						            		{(require('./CommentFunctions.js').getLoggedInUser() === comment.user) ?
						            				(<font color="blue">You</font>)
						            				:(<font>{comment.userName}</font>)
						            		}
						            		&nbsp; on {that.getShortDate(date)}</small>
						            	</div>
						            </td>
						            <td width="2%">
							            {(require('./CommentFunctions.js').getLoggedInUser() === comment.user) &&
											<div class="text-right">&nbsp;<a href="#" onClick={() => that.deleteReply(comment.id)}>
									          <span class="glyphicon glyphicon-trash"></span>
									          </a></div>
										}
						            </td>
						            </tr></table>	
					    	</div>	
					    </td>
			    </tr>);
		        })}
	        </tbody>
	      </table>
		</div>
          <div>
	          <table class="table">
	          <tbody>
	            <tr>
	              <td width="40%"><textarea  id="reply" rows="3" cols= "40" maxlength="100"></textarea></td>
	              <td width="40%" ><div class="text-left"><button  class="btn btn-default" onClick={this.postReply}>Post</button></div></td>
	              <td width="20%"><input type="hidden" defaultValue={this.props.comment.id} id="replyTo"/></td>
	            </tr>
	            
	          </tbody>
	        </table>
		</div>
        </Modal>
      </div>
    );
  }
}