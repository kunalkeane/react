import React from 'react';
import ReactAutocomplete from 'react-autocomplete';
 
export default class TagAutoComplete extends React.Component {
		  constructor (props) {
		    super(props)
		    this.state = {
		      value: '',
		    }
		  }

		  getComments = (e) =>{
			  	this.props.fetchTagComments(e.target.innerText);
			}
		  
		  render() {
		    return (
		    		<div>
		    		<h4>Search All Trends &nbsp;</h4>
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
			        onSelect={value => this.setState({ value })}
			      />
			      {(this.state.value !== "") && (this.props.listNameFromParent.indexOf(this.state.value) > -1) &&
		      		<div><br/><mark>See what people are talking about <a href="#" onClick={this.getComments.bind(this)}>{this.state.value}</a></mark></div>
			      }
		      </div>
		    )
		    
		  }
		
}