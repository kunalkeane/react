import React from 'react';
import ReactAutocomplete from 'react-autocomplete';
 
export default class TagAutoComplete extends React.Component {
		  constructor (props) {
		    super(props)
		    this.state = {
		      value: '',
		    }
		    var isTagSelected = false;
		  }

		  getComments = () =>{
			  	this.props.fetchTagComments(this.state.value);
			  	this.isTagSelected = false;
			}
		  
		  render() {
		    return (
		    		<div>
		    		<h4 class="text-danger">Trends <span class="glyphicon glyphicon-tag"></span> &nbsp;&nbsp;<span class="glyphicon glyphicon-search"></span></h4>
		      <ReactAutocomplete
		        items={this.props.listNameFromParent}
		    		shouldItemRender={(item, value) => item.toLowerCase().indexOf(value.toLowerCase()) > -1}
			        getItemValue={item => item}
			        renderItem={(item, highlighted) =>
			          <div
			            key={item}
			            style={{ backgroundColor: highlighted ? '#eee' : 'transparent'}}
			          >
			            {item}
			          </div>
			        }
			        value={this.state.value}
			        onChange={e => this.setState({ value: e.target.value })}
		      		//onChange={e => value =  e.target.value}	
			        onSelect={value => {
			        						this.setState({ value });
			        						this.isTagSelected = true;
			        					}
			        			}
			      />
			      {(this.state.value !== "") && (this.isTagSelected) && (this.props.listNameFromParent.indexOf(this.state.value) > -1) &&
		      		this.getComments()
		      	  }
		      </div>
		    )
		    
		  }
		
}