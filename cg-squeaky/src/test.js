var myjson = {"comments":[{"id":1,"text":"#infoquest is great program","hashTag":"#infoquest","user":null}, {"id":1,"text":"#techfiesta was good too","hashTag":"#techfiesta","user":null}]};
var commentsJSON = JSON.parse(JSON.stringify(myjson));
var comments = commentsJSON.comments;
console.log(comments);

comments.map(function(element){
    console.log(element.text);
})
