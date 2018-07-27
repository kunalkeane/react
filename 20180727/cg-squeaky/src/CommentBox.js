import React from 'react';
import './index.css';

const divStyle = {
		display: 'flex',
};

export default class CommentBox extends React.Component {
	constructor(props) {
    super(props);
		this.state = {values : []};
		this.state = {comment : ""};
	}
	
	handleCommentChange = (e) =>{
	var comment1 = document.getElementById("comment").value;
	if(comment1 === null || comment1.trim().length <1){
		return;
	}
	document.getElementById("comment").value = "";
	this.props.callbackFromParent(comment1);	
	}
		
	render() {
		return (
			<div class="container-fluid">	
			<div class="row">
				<div class="col-md-8 text-right">
		      		<textarea  id="comment" class="form-control" rows="3" cols= "40" maxlength="100" placeholder="What's on your mind.."></textarea>
		      	</div>
		      	<div  class="col-md-4 text-left"><br/><br/>
		      	<button class="btn btn-sm" onClick={this.handleCommentChange}>POST</button>
		      	</div>
		     </div>
		</div>
		
     	);
	}
		
}
	

