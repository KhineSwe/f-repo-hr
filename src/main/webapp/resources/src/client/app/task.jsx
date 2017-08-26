import React from 'react';
import { render } from 'react-dom';

class Task extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            organizationName: organizationName,
            projectName: projectName,
            taskNo: taskNo,
            taskNotes: []
        };
        this.addComment = this.addComment.bind( this );
    };

    componentWillMount() {
        var url = '/u/' + this.state.organizationName + '/projects/' + this.state.projectName + '/tasks/' + this.state.taskNo + '/comments';
        $.get( url ).done(( taskNotes ) => {
            this.setState( { taskNotes: taskNotes } );
        } );
    };

    addComment( comment ) {
        var self = this;
        var token = $( "meta[name='_csrf']" ).attr( "content" );
        var header = $( "meta[name='_csrf_header']" ).attr( "content" );
        var url = "/u/" + this.state.organizationName + "/projects/" + this.state.projectName + "/tasks/" + this.state.taskNo + "/comments/new";
        $.ajax( {
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify( comment ),
            dataType: 'json',
            url: url,
            beforeSend: function( xhr ) {
                xhr.setRequestHeader( header, token );
            },
            success: function( taskNote ) {
                var dumpTaskNotes = self.state.taskNotes.slice( 0 );
                dumpTaskNotes.push( taskNote );
                self.setState( { taskNotes: dumpTaskNotes } );
            }
        } );
    }

    render() {
        return (
            <div className="panel-group">
                <CommentList comments={this.state.taskNotes}></CommentList>
                <CommentForm addComment={this.addComment}></CommentForm>
            </div>
        );
    }
}

class CommentList extends React.Component {
    render() {
        return (
            <div>
                <ul style={{ listStyleType: 'none', paddingLeft: 'inherit' }}>
                    {this.props.comments.map(( data, index ) =>
                        <li key={index}>
                            <div className="panel panel-default">
                                <div className="panel-heading">aungmyohtet commented 5 days ago.</div>
                                <div className="panel-body">{data.comment}</div>
                            </div>
                            <br />
                        </li>
                    )}
                </ul>
            </div>
        );
    }
}

class CommentForm extends React.Component {
    constructor( props ) {
        super( props );
        this.handleSubmit = this.handleSubmit.bind( this );
    }

    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <label>Your Comment:</label>
                    <textarea className="form-control" rows="5" id="comment" ref="comment" />
                    <br />
                    <input type="submit" value="Comment" className="btn btn-primary pull-right" />
                </form>
            </div>
        );
    }

    handleSubmit( event ) {
        event.preventDefault();
        var data = {
            comment: this.refs.comment.value
        }
        this.props.addComment( data );
        this.refs.comment.value = "";
    }
}

render( <Task />, document.getElementById( 'task' ) );
