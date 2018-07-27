import React from 'react';
import Modal from 'react-responsive-modal';
import './index.css';

const divStyle = {
		   display: 'inline-block',
};

export default class Centered extends React.Component {
	constructor(props) {
	    super(props);
	    this.state = {open: false};
		}
	
  onOpenModal = () => {
    this.setState({ open: true });
  };

  onCloseModal = () => {
    this.setState({ open: false });
  };
  
  handleCommentChange = (e) =>{
		var edittedComment = document.getElementById("edittedComment").value;
		if(edittedComment === null || edittedComment.trim().length <1){
			return;
		}
		var edittedCommentId = document.getElementById("edittedCommentId").value;
		this.props.saveCommentCallBack(edittedComment, edittedCommentId);
		this.onCloseModal();
		}

  render() {
    const { open } = this.state;
    return (
      <div style={divStyle}>
        <a href="#" onClick={this.onOpenModal}><img src="edit.gif" width="20" height="20" title="Edit comment"></img></a>
        
        <Modal open={open} onClose={this.onCloseModal}  editcomment>
          <h2>Edit Comment</h2>
          <div>
			<textarea  id="edittedComment" rows="3" cols= "40" maxlength="100">{this.props.comment.text}</textarea>
			<input type="hidden" defaultValue={this.props.comment.id} id="edittedCommentId"/>
			<button  class="btn btn-default" onClick={this.handleCommentChange}>Save</button>
		</div>
        </Modal>
      </div>
    );
  }
}