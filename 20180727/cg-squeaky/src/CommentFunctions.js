var userName;
var userId;
var selectedTag;
var userFirstName;

function getCurrentlyLoggedInUser(){
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
					userName = userJSON.username;
					userFirstName =  userJSON.firstName;
				}
				userId = userJSON.username;
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
			that.lastAccessTime = commentsJSON.accessTime;
			that.startCommentId = commentsJSON.startCommentId;
			that.endCommentId = commentsJSON.endCommentId;
			selectedTag = commentsJSON.selectedTag;
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
exports.editComment = editComment;
exports.likeComment = likeComment;
exports.checkNewComment = checkNewComment;
exports.fetchData = fetchData;
exports.getLoggedInUser = getLoggedInUser;
exports.getCurrentlyLoggedInUser=getCurrentlyLoggedInUser;
exports.getLoggedInUserName=getLoggedInUserName;
exports.getSelectedTag=getSelectedTag;
exports.getLoggedInUserFirstName=getLoggedInUserFirstName