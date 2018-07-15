import React from 'react';

export default class CommentBox extends React.Component {
	constructor(props) {
    super(props);
		this.state = {values : []};
		this.state = {comment : ""};
	}
	
	handleCommentChange = (e) =>{
	var comment1 = document.getElementById("comment").value;    
	document.getElementById("comment").value = "";
	this.props.callbackFromParent(comment1);	
	}
		
	render() {
		return (
		<div>	
			<div align="center">
		      <textarea  id="comment" rows="3" cols= "40" maxlength="100" placeholder="Enter Comment Here"></textarea>
		      </div>  
		      <div align="center"><button class="btn btn-default" onClick={this.handleCommentChange}>POST</button></div>
		</div>
     	);
	}
		
}
	

