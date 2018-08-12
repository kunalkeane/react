import React from 'react';

export default class SuperComment extends React.Component {
	constructor() {
    	super();
		this.state = {comment : ""};
	}
	
	handleCommentChange = (comment2) =>{
		this.setState({comment:comment2});
	}
}
	

