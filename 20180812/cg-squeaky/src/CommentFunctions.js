var userName;
var userId;
var selectedTag;
var timelineUser;
var userFirstName;

function getCurrentlyLoggedInUser(callBack){
	fetch(require('./api_url.js').loggedInUserUrl,{
		headers: {
			'accept': 'application/json'
			},
			credentials: "same-origin" }).then(function(result){
				return result.json();
			}).then(function(data){
				var userJSON = JSON.parse(JSON.stringify(data));
				if(userJSON.firstName != null){
					userName = userJSON.firstName;
					userFirstName =  userJSON.firstName;
					
					if(userJSON.lastName != null){
						userName = userName + " "+ userJSON.lastName;
					}
				} else{
					userName = userJSON.userName;
					userFirstName =  userJSON.firstName;
					if(userFirstName === null){
						userFirstName = userName;
					}
				}
				userId = userJSON.userName;
				callBack();
			});
}

function getLoggedInUserName(){
	return userName;
}

function getLoggedInUserFirstName(){
	return userFirstName;
}

function getLoggedInUser(){
	return userId;
}


function getSelectedTag(){
	return selectedTag;
}

function getTimelineUser(){
	return timelineUser;
}


function fetchData(url, that, postFetchFuntion){
	fetch(url,{
	headers: {
		'accept': 'application/json'
		},
		credentials: "same-origin" }).then(function(result){
			return result.json();
		}).then(function(data){
			var commentsJSON = JSON.parse(JSON.stringify(data));
			that.comments = Array.from(commentsJSON.comments);
			that.hashTags = Array.from(commentsJSON.hashTags);
			that.topTrends = Array.from(commentsJSON.topTrends);
			that.startCommentId = commentsJSON.startCommentId;
			that.endCommentId = commentsJSON.endCommentId;
			selectedTag = commentsJSON.selectedTag;
			timelineUser = commentsJSON.timelineUser;
			that.lastAccessTime = new Date().getTime();
			if(timelineUser!= null && (that.comments === null || that.comments.length < 1)){
				alert("There are no comments for the selected User");
			}
			postFetchFuntion();
		});
}

function fetchReplies(url, that, postFetchFuntion){
	fetch(url,{
	headers: {
		'accept': 'application/json'
		},
		credentials: "same-origin" }).then(function(result){
			return result.json();
		}).then(function(data){
			var commentsJSON = JSON.parse(JSON.stringify(data));
			that.comments = Array.from(commentsJSON.comments);
			that.lastAccessTime = new Date().getTime();
			postFetchFuntion();
		});
}

function getAllUsers(url, that, postFetchFuntion){
	fetch(url,{
	headers: {
		'accept': 'application/json'
		},
		credentials: "same-origin" }).then(function(result){
			return result.json();
		}).then(function(data){
			that.users = Array.from(data);
			postFetchFuntion();
		});
}

function postComment(comment, postSubmitCallback){
	var payload = new Object();
	payload.text= comment;
	
	fetch(require('./api_url.js').postCommentURL,{
	headers: {
		'accept': 'application/json',
		'Content-Type': 'application/json'
		},
		method: "POST",
    	body: JSON.stringify( payload ),
    	credentials: "same-origin"
	}).then(function(result){
			postSubmitCallback();
		});
}

function replyComment(reply, replyTo, postSubmitCallback){
	var payload = new Object();
	payload.text= reply;
	payload.replyTo= replyTo;
	
	fetch(require('./api_url.js').postReplyURL,{
	headers: {
		'accept': 'application/json',
		'Content-Type': 'application/json'
		},
		method: "POST",
    	body: JSON.stringify( payload ),
    	credentials: "same-origin"
	}).then(function(result){
			postSubmitCallback();
		});
}

function deleteReply(replyId, postSubmitCallback){
	fetch(require('./api_url.js').deleteReplyURL+replyId,{
	headers: {
		'accept': 'application/json',
		'Content-Type': 'application/json'
		},
		method: "DELETE",
    	credentials: "same-origin"
	}).then(function(result){
			postSubmitCallback();
		});
}

function editComment(comment, commentId, postSubmitCallback){
	var payload = new Object();
	payload.text= comment;
	payload.commentId= commentId;
	
	
	fetch(require('./api_url.js').editCommentURL,{
	headers: {
		'accept': 'application/json',
		'Content-Type': 'application/json'
		},
		method: "POST",
    	body: JSON.stringify( payload ),
    	credentials: "same-origin"
	}).then(function(result){
			postSubmitCallback();
		});
}

function likeComment(commentId, isLike, postSubmitCallback){
	var payload = new Object();
	payload.commentId= commentId;
	payload.like= isLike;
	
	fetch(require('./api_url.js').likeCommentURL,{
	headers: {
		'accept': 'application/json',
		'Content-Type': 'application/json'
		},
		method: "POST",
    	body: JSON.stringify( payload ),
    	credentials: "same-origin"
	}).then(function(data){
		return data.json();
	}).then(function(data){
		var likeCommentJSON = JSON.parse(JSON.stringify(data));
		postSubmitCallback(likeCommentJSON);
	});
}

function checkNewComment(lastTimeStamp, that){
	fetch(require('./api_url.js').checkNewCommentURL+lastTimeStamp,{
	headers: {
		'accept': 'application/json'
		},
		credentials: "same-origin"}).then(function(result){
			return result.json();
		}).then(function(data){
			var commentsJSON = JSON.parse(JSON.stringify(data));
			var timestamp = commentsJSON.timestamp;
			var count = commentsJSON.totalNewMessages;
			that.setNotification(count);
			return count;
		});
}



exports.postComment = postComment;
exports.replyComment = replyComment;
exports.editComment = editComment;
exports.likeComment = likeComment;
exports.checkNewComment = checkNewComment;
exports.fetchData = fetchData;
exports.fetchReplies = fetchReplies;
exports.getLoggedInUser = getLoggedInUser;
exports.getCurrentlyLoggedInUser=getCurrentlyLoggedInUser;
exports.getLoggedInUserName=getLoggedInUserName;
exports.getSelectedTag=getSelectedTag;
exports.getTimelineUser=getTimelineUser;
exports.getLoggedInUserFirstName=getLoggedInUserFirstName
exports.getAllUsers=getAllUsers
exports.deleteReply=deleteReply