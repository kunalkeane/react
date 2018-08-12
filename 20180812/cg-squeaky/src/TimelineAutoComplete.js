import React from 'react';
import ReactAutocomplete from 'react-autocomplete';
 
export default class TimelineAutoComplete extends React.Component {
		  constructor (props) {
		    super(props)
		    this.users = [];
		    var getUsersUrl = require('./api_url.js').getUsersURL;
		    var that = this;
		    require('./CommentFunctions.js').getAllUsers(getUsersUrl, this, function() {
		    	that.setState({dummy : ""})
		    });
		    this.selectedUserId = "";
		    var isUserSelected = false;
		    this.state = {
		      value: '',
		    }
		  }

		  fetchUserComments = () => {
			  this.props.fetchUserComments(this.selectedUserId);
			  this.isUserSelected = false;
		  };
		  
		  ifUserExists = () =>{
			 var userName = this.state.value;
			 var found = false;
			 var that = this;
			 this.users.map(function(user){
				 if(user.userName === userName){
					 found = true;
					 that.selectedUserId = user.userId;
					 return true;
				 }
			 })
			 return found;
		  }
		  
		  render() {
		    return (
		    		<div>
		    		<h4 class="text-success">Timelines <span class="glyphicon glyphicon-hourglass"></span> &nbsp;&nbsp;<span class="glyphicon glyphicon-search"></span></h4>
		      <ReactAutocomplete
		        items={this.users}
		    		shouldItemRender={(item, value) => item.userName.toLowerCase().indexOf(value.toLowerCase()) > -1}
			        getItemValue={item => item.userName}
			        renderItem={(item, highlighted) =>
			          <div
			          	key={item.userName}
			            style={{ backgroundColor: highlighted ? '#eee' : 'transparent'}}
			          >
			            {item.userName}
			          </div>
			        }
			        value={this.state.value}
			        onChange={e => this.setState({ value: e.target.value })}
		      	    onSelect={value => {
		      	    					this.setState({ value });
		      	    					this.isUserSelected = true;
		      	    				}
		      	    			}
			      />
			    {(this.state.value !== "") && (this.isUserSelected) && (this.ifUserExists()) &&
		      		this.fetchUserComments()
			    }
		      </div>
		    )
		    
		  }
		
}