//var apiHost="http://localhost:9080/squeaky/api/";
//var apiHost="http://LIN59000544:9080/Rest/api/";
var apiHost="http://tag-pune/squeaky/api/";

var loggedInUserUrl = apiHost+"loggedInUser";
var postCommentURL = apiHost+"postComment";
var checkNewCommentsURL = apiHost+"checkNewComments/";
var getCommentsByTagsURL = apiHost+"getComments/tags/";
var getCommentsURL = apiHost+"getComments";
var editCommentURL = apiHost+"editComment";
var likeCommentURL = apiHost+"likeComment";
var getNextComments = apiHost+"getNextComments/";
var getPrevComments = apiHost+"getPrevComments/";
var getLastCommentsURL = apiHost+"getLastComments";



exports.loggedInUserUrl = loggedInUserUrl;
exports.postCommentURL = postCommentURL;
exports.checkNewCommentURL = checkNewCommentsURL;
exports.getCommentsByTagsURL = getCommentsByTagsURL;
exports.getCommentsURL = getCommentsURL;
exports.editCommentURL = editCommentURL;
exports.likeCommentURL = likeCommentURL;
exports.getNextCommentsURL = getNextComments;
exports.getPrevCommentsURL = getPrevComments;
exports.getLastCommentsURL = getLastCommentsURL;