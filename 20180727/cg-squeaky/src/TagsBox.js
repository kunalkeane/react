import React from 'react';
import './index.css';

export default class Tags extends React.Component {

	constructor(props) {
		super(props);
		this.hashTags = [];
	}

	
	getComments = (e) =>{
		this.props.fetchTagComments(e.target.innerText);
	}
	
	getAllComments = (e) =>{
		this.props.fetchAllTagComments();
	}

		handleChange = (hashArray) =>{
			this.hashTags = [];
			if(hashArray !== undefined && hashArray[0] !== undefined){
				this.hashTags = Array.from(hashArray);
			}
		}

	    render() {
				this.handleChange(this.props.listNameFromParent);
				var that = this;
		return (
	      <div>
					<h3>Trends &nbsp;
					<a href="#" title="All Trends" onClick={that.getAllComments.bind(this)}><span class="glyphicon glyphicon-home"></span></a>
			          </h3>
				<div class="left">{this.hashTags.map(function(element){
					return <div class="tagtext">
					{(element.toLowerCase() === require('./CommentFunctions.js').getSelectedTag().toLowerCase()) ?
						(<mark><a href="#" onClick={that.getComments.bind(this)}>{element}</a></mark>)
					: (<a href="#" onClick={that.getComments.bind(this)}>{element}</a>)
					}
					</div>
				})}</div>
					
				
			</div>
	    );
	  }
	
	}
	

