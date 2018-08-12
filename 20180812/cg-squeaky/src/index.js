import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import CommentBox from './CommentBox.js';
import TagAutoComplete from './TagAutoComplete.js';
import TimelineAutoComplete from './TimelineAutoComplete.js';
import TopTrends from './TopTrends.js';
import AllCommentsBox from './AllCommentsBox.js';
import Welcome from './Welcome.js';
import ReactAutocomplete from 'react-autocomplete';
import Modal from 'react-responsive-modal';

const divStyle = {
   display: 'inline-block'
};

class MainApp extends React.Component {
constructor(props) {
    super(props);
	this.state = {listName: []};
	this.lastAccessTime = new Date().getTime();
	this.comments = [];
	this.hashTags = [];
	this.topTrends = [];
	this.endCommentId=-1;
	this.startCommentId=-1;
	var that = this;
	require('./CommentFunctions.js').getCurrentlyLoggedInUser(function(){
    	that.setState({"dummy" : ""});
    });	

	}


myCallback = (comment) => {
	var that = this;
	var postComment = require('./CommentFunctions.js').postComment;
		postComment(comment, this.fetchDataCallBack);
		
    };
    
editComment = (edittedComment, edittedCommentId) => {
	var that = this;
	var editComment = require('./CommentFunctions.js').editComment;
		editComment(edittedComment, edittedCommentId, this.fetchDataCallBack);
		
};    
    
fetchTagComments = (tag) => {
	var that = this;
	
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getCommentsByTagsURL+tag, this, function(){that.setState({dummy:""})});
};

fetchUserComments = (selectedUserId) => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getTimelinesByUserURL+selectedUserId, this, function(){that.setState({dummy:""})});
};

fetchAllTagComments = () => {
	var that = this;
	this.fetchDataCallBack();
};
    
fetchDataCallBack = () => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getCommentsURL, this, function(){that.setState({dummy:""})});
};    

appendSelectTag = (commentId) =>{
	if(commentId == null){
		return "/"+require('./CommentFunctions.js').getSelectedTag();
	}
	return commentId +"/"+require('./CommentFunctions.js').getSelectedTag();
}

appendSelectedUser = (commentId) =>{
	if(commentId == null){
		return "/"+require('./CommentFunctions.js').getTimelineUser();
	}
	return commentId +"/"+require('./CommentFunctions.js').getTimelineUser();
}

getNextComment = (commentId) => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	var url = null;
	if(require('./CommentFunctions.js').getTimelineUser() !== null){
		url = require('./api_url.js').getNextTimelinesURL+this.appendSelectedUser(commentId)
	}else{
		url = require('./api_url.js').getNextCommentsURL+this.appendSelectTag(commentId)	
	}
	fetchData(url, this, function(){that.setState({dummy:""})});
};

getPrevComment = (commentId) => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	var url = null;
	if(require('./CommentFunctions.js').getTimelineUser() !== null){
		url = require('./api_url.js').getPrevTimelinesURL+this.appendSelectedUser(commentId)
	}else{
		url = require('./api_url.js').getPrevCommentsURL+this.appendSelectTag(commentId)	
	}
	fetchData(url, this, function(){that.setState({dummy:""})});
};

getLastComments = () => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	var url = null;
	if(require('./CommentFunctions.js').getTimelineUser() !== null){
		url = require('./api_url.js').getLastTimelinesURL+this.appendSelectedUser()
	}else{
		url = require('./api_url.js').getLastCommentsURL+this.appendSelectTag()	
	}
	fetchData(url, this, function(){that.setState({dummy:""})});
};

getFirstComments = () => {
	var that = this;
	if(require('./CommentFunctions.js').getTimelineUser() !== null){
		this.fetchUserComments(require('./CommentFunctions.js').getTimelineUser())
	}else{
		var selectedTag = require('./CommentFunctions.js').getSelectedTag(); 
		if(selectedTag ==="ALL"){
			var that = this;
			this.fetchDataCallBack();
		}else{
			that.fetchTagComments(selectedTag);
		}
	}
};

componentDidMount() {
	this.fetchDataCallBack();
}
    
    render() {
    	return (
	    <div class="main jumbotron text-left" >
	    <div><Welcome  callbackFromParent={this.fetchDataCallBack} lastAccessTime={this.lastAccessTime}/></div>	
	    <div class="container">
	    	 	<div class="row">
		    	 	<div class="col-sm-3">
		    	 		<div  style={divStyle}><TopTrends listNameFromParent={this.topTrends} fetchTagComments={this.fetchTagComments} fetchAllTagComments={this.fetchAllTagComments}/></div>	
		    	 	</div>
			    	<div class="col-sm-7" >
			    		<div class="text-center"><CommentBox callbackFromParent={this.myCallback}/></div>
			      		<div  style={divStyle}><AllCommentsBox listNameFromParent={this.comments} getLastComments={this.getLastComments} getFirstComments={this.getFirstComments} getNextComment={this.getNextComment} getPrevComment={this.getPrevComment} startCommentId={this.startCommentId} endCommentId={this.endCommentId} editCommentCallBack={this.editComment}/></div>
			      	</div>
			      	<div class="col-sm-2">
			      		<div  style={divStyle}><TagAutoComplete listNameFromParent={this.hashTags} fetchAllTagComments={this.fetchAllTagComments} fetchTagComments={this.fetchTagComments}/></div>
			      		<div>&nbsp;<br/><br/></div>
			      		<div  style={divStyle}><TimelineAutoComplete fetchUserComments={this.fetchUserComments}/></div>
			      	</div>	
		      	</div>
	      	</div>
	      </div>
	    );
	  }
	}


class Clock extends React.Component {
	constructor(props) {
	    super(props);
	    this.state = {open: false};
		}

	onOpenModal = () => {
	    this.setState({ open: true });
	  };

	  onCloseModal = () => {
	    this.setState({ open: false });
	  };

	  on = () => {
		    document.getElementById("overlay").style.display = "block";
	    }

	  off = () => {
	        document.getElementById("overlay").style.display = "none";
	    }		      


	
		  render() {
			  const { open } = this.state;  
		    return (
		    <div title="Kunal Aggarwal">		
		      Selected Value is ABC
		    
		    
		    		      
		      </div>
		    )
		  }
	}

ReactDOM.render(<MainApp />, document.getElementById('root'));