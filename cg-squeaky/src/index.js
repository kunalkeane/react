import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import CommentBox from './CommentBox.js';
import TagsBox from './TagsBox.js';
import TopTrends from './TopTrends.js';
import AllCommentsBox from './AllCommentsBox.js';
import Welcome from './Welcome.js';


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

	require('./CommentFunctions.js').getCurrentlyLoggedInUser();
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

fetchAllTagComments = () => {
	var that = this;
	this.fetchDataCallBack();
};
    
fetchDataCallBack = () => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getCommentsURL, this, function(){that.setState({dummy:""})});
};    

getNextComment = (commentId) => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getNextCommentsURL+commentId, this, function(){that.setState({dummy:""})});
};

getPrevComment = (commentId) => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getPrevCommentsURL+commentId, this, function(){that.setState({dummy:""})});
};

getLastComments = () => {
	var that = this;
	var fetchData = require('./CommentFunctions.js').fetchData;
	fetchData(require('./api_url.js').getLastCommentsURL, this, function(){that.setState({dummy:""})});
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
		    	 		<div  style={divStyle}><TagsBox listNameFromParent={this.hashTags} fetchAllTagComments={this.fetchAllTagComments} fetchTagComments={this.fetchTagComments}/></div>
			    	</div>
			    	<div class="col-sm-7" >
			    		<div class="text-center"><CommentBox callbackFromParent={this.myCallback}/></div>
			      		<div  style={divStyle}><AllCommentsBox listNameFromParent={this.comments} getLastComments={this.getLastComments} getFirstComments={this.fetchDataCallBack} getNextComment={this.getNextComment} getPrevComment={this.getPrevComment} startCommentId={this.startCommentId} endCommentId={this.endCommentId} editCommentCallBack={this.editComment}/></div>
			      	</div>
			      	<div class="col-sm-2">
			      		<div  style={divStyle}><TopTrends listNameFromParent={this.topTrends} fetchTagComments={this.fetchTagComments}/></div>
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
	    this.state = {date: new Date()};
	  }

	  componentDidMount() {
		  alert("clock.componentDidMount");
	    this.timerID = setInterval(
	      () => this.tick(),
	      3000
	    );
	  }
	  
	  /*componentDidUpdate() {
		  alert("clock.componentDidUpdate");
	    if(this.state.date.toLocaleTimeString()%2===0){
	    	alert("clock.componentDidUpdate even");
	    }else{
	    	alert("clock.componentDidUpdate odd");
	    }
	  }*/

	  componentWillUnmount() {
	    clearInterval(this.timerID);
	  }

	  tick() {
		  alert(1);
		this.setState({
	      date: new Date()
	    });
	  }

	  render() {
	    return (
	      <div>
	        <h1>Hello, world!</h1>
	        <h2>It is {this.state.date.toLocaleTimeString()}.</h2>
	      </div>
	    );
	  }
	}

ReactDOM.render(<MainApp />, document.getElementById('root'));