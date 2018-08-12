//var apiHost="http://localhost:9080/squeaky/api/";
//var apiHost="http://LIN59000544:9080/Rest/api/";
var apiHost="http://tag-pune/squeaky/api/";

var loggedInUserUrl = apiHost+"loggedInUser";
var postCommentURL = apiHost+"postComment";
var postReplyURL = apiHost+"postReply";
var checkNewCommentsURL = apiHost+"checkNewComments/";
var getCommentsByTagsURL = apiHost+"getComments/tags/";
var getRepliesForComment = apiHost+"getReplies/comment/";
var deleteReply = apiHost+"deleteReply/comment/";
var getTimelinesByUserURL = apiHost+"getTimelines/";
var getCommentsURL = apiHost+"getComments";
var editCommentURL = apiHost+"editComment";
var likeCommentURL = apiHost+"likeComment";
var getNextComments = apiHost+"getNextComments/";
var getPrevComments = apiHost+"getPrevComments/";
var getLastCommentsURL = apiHost+"getLastComments";
var getUsersURL = apiHost+"getUsers";
var getNextTimelines = apiHost+"getNextTimelines/";
var getPrevTimelines = apiHost+"getPrevTimelines/";
var getLastTimelinesURL = apiHost+"getLastTimelines";

exports.loggedInUserUrl = loggedInUserUrl;
exports.postCommentURL = postCommentURL;
exports.postReplyURL = postReplyURL;
exports.checkNewCommentURL = checkNewCommentsURL;
exports.getCommentsByTagsURL = getCommentsByTagsURL;
exports.getRepliesForComment = getRepliesForComment;
exports.deleteReplyURL= deleteReply;
exports.getTimelinesByUserURL = getTimelinesByUserURL;
exports.getCommentsURL = getCommentsURL;
exports.editCommentURL = editCommentURL;
exports.likeCommentURL = likeCommentURL;
exports.getNextCommentsURL = getNextComments;
exports.getPrevCommentsURL = getPrevComments;
exports.getLastCommentsURL = getLastCommentsURL;
exports.getUsersURL = getUsersURL;
exports.getNextTimelinesURL = getNextTimelines;
exports.getPrevTimelinesURL = getPrevTimelines;
exports.getLastTimelinesURL = getLastTimelinesURL;