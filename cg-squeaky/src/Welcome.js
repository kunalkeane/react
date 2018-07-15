import React from 'react';

const divStyle = {
		   display: 'inline-block',
};

export default class CommentBox extends React.Component {
	
	constructor(props) {
	    super(props);
	    var notification = "";
	    var commentCount = 0;
	    var refreshInterval = 5000;
	    var interval = 1;
			
		}
		
		setNotification  = (count) =>{
			this.commentCount = count;
			this.setState({"abc" : ""});
		} 
		
		componentDidMount() {
			var that = this;
			var checkNewComment = require('./CommentFunctions.js').checkNewComment;
			this.interval = setInterval(function(){
				  checkNewComment(that.props.lastAccessTime, that)
				  }, 10000);
		}
		
		componentWillUnmount() {
			clearInterval(this.interval);
		}
		
		getAllComments = () =>{
			this.commentCount = 0;
			this.props.callbackFromParent();
		}
		
	render() {
		return (
				<div class="container">
		    	 	<div class="row alert alert-info">
			    	 	<div class="col-sm-6">
			    	 		 Hi {require('./CommentFunctions.js').getLoggedInUserName()} ... <strong> Welcome to Squeaky! </strong> 
				      	</div>
				      	<div class="col-sm-6">
				      		{(this.commentCount > 0) && 
								<font color="red">Voila ! <a href="#" onClick={this.getAllComments}>{this.commentCount}</a> new message(s)</font>
						  }
				      	</div>
				    </div>
				    
	      	</div>
		);
	}
}
	

