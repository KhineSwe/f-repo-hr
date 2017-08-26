import React from 'react';
import { render } from 'react-dom';
import $ from 'jquery';

class Events extends React.Component {

    constructor( props ) {

        super( props );
        this.state = {
            calendarId: calendarId,
            eventsTitles: [],
            year: 2017,
            currentyear: currentyear
        }
        this.handleForward = this.handleForward.bind( this );
        this.handleBackward = this.handleBackward.bind( this );
    };

    componentWillMount() {
        console.log( "componentWillMount" );
        var url = '/admin/calendar_template/' + this.state.calendarId + '/year/' + this.state.currentyear + '/events';
        $.get( url ).done(( eventTitle ) => {
            this.setState( { eventsTitles: eventTitle } );
            console.log( JSON.stringify( eventTitle ) );
        } );
    };

    handleForward( event ) {
        console.log( "Enter handle forward method:" );
        event.preventDefault();
        var forwardyear = this.state.year + 1;
        this.state.year = forwardyear;
        $( "#labelid" ).html( forwardyear );
        var url = '/admin/calendar_template/' + this.state.calendarId + '/forwardyear/' + forwardyear + '/events';
        $.get( url ).done(( eventTitle ) => {
            this.setState( { eventsTitles: eventTitle } );
            console.log( JSON.stringify( eventTitle ) );
        } );
    }

    handleBackward( event ) {
        console.log( "Enter handle backward method:" );
        event.preventDefault();
        var backwardyear = this.state.year - 1;
        this.state.year = backwardyear;
        $( "#labelid" ).html( backwardyear );
        var url = '/admin/calendar_template/' + this.state.calendarId + '/backwardyear/' + backwardyear + '/events';
        $.get( url ).done(( eventTitle ) => {
            this.setState( { eventsTitles: eventTitle } );
            console.log( JSON.stringify( eventTitle ) );
        } );
    }

    componentDidMount() {
        var self = this;
        $( document ).ready( function() {
            $( "#event-form-button" ).click( function( event ) {
                var token = $( "meta[name='_csrf']" ).attr( "content" );
                var header = $( "meta[name='_csrf_header']" ).attr( "content" );
                var id = $( '#id' ).val();
                var date = $( '#date' ).val();
                console.log("Date in event form button:::"+date);
                var year = $( '#year' ).val();
                console.log("Year in event form button:::"+year);
                var eventTitle = $( '#title' ).val();
                console.log("Event title in event form button:::"+eventTitle);
                var calendarId = $( '#calendarId' ).val();
                console.log("Calendar id in event form button:::"+calendarId);
                var url = '/admin/calendar_template/edit';
                console.log( "Onclick Edit Function" );
                event.preventDefault();
                $.ajax( {
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify( { id: id, date: date, eventTitle: eventTitle, year: year, calendarId: calendarId } ),
                    dataType: 'json',
                    url: url,
                    beforeSend: function( xhr ) {
                        xhr.setRequestHeader( header, token );
                    },
                    success: function( eventUpdate ) {
                        self.setState( { eventsTitles: eventUpdate } );
                        self.forceUpdate();
                    },
                    error: function( e ) {
                        console.log( JSON.stringify( e ) );
                    }
                } );

            } );

            $( "#event-delete-button" ).click( function( event ) {
                var token = $( "meta[name='_csrf']" ).attr( "content" );
                var header = $( "meta[name='_csrf_header']" ).attr( "content" );
                var id = $( '#id' ).val();
                var date = $( '#date' ).val();
                var eventTitle = $( '#title' ).val();
                var year = $( '#year' ).val();
                var calendarId = $( '#calendarId' ).val();
                var url = '/admin/calendar_template/delete';
                event.preventDefault();
                $.ajax( {
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify( { id: id, date: date, eventTitle: eventTitle, year: year, calendarId: calendarId } ),
                    dataType: 'json',
                    url: url,
                    beforeSend: function( xhr ) {
                        xhr.setRequestHeader( header, token );
                    },
                    success: function( eventDelete ) {
                        console.log("After deleting");
                        console.log(JSON.stringify(eventDelete));
                        self.setState( { eventsTitles: eventDelete } );
                        self.forceUpdate();
                    },
                    error: function( e ) {
                        console.log( JSON.stringify( e ) );
                    }
                } );

            } );

        } );
    }

    render() {
        var self = this;
        console.log( "Events render" );
        return (
            <div class="col-sm-8">
                <YearEventLists year={this.state.year} handleForward={this.handleForward} handleBackward={this.handleBackward} addEvent={this.addEvent} calendarId={this.state.calendarId} />
                <EventsList events={this.state.eventsTitles} year={this.state.year} calendarId={this.state.calendarId} />
            </div>
        );
    }
}


class EventsList extends React.Component {
    constructor( props ) {
        super( props );
    };
    
    render() {
        var self = this;
        var eventMap = new Map();
        this.props.events.forEach( function( event ) {
            var eventArray = eventMap.get( event.date );
            if ( !eventArray ) {
                eventArray = [];
            }
            eventArray.push( event );
            eventMap.set( event.date, eventArray );
        } );
        
        var eventRows = [];
        for ( var [eventDate, eventArray] of eventMap.entries() ) {
            console.log("Enter event rows push");
            var firstEvent = eventArray.pop();
            console.log( "Pop value is::::" + firstEvent.eventTitle );
            console.log( "Event Array Size is::::" + eventArray.length);
            eventRows.push(
                  <EventDeleteRow rowSpan={eventArray.length + 1} eventDate={eventDate} events = {firstEvent} year={self.props.year} calendarId={self.props.calendarId} />  
            );
            eventArray.forEach( function( event ) {
                console.log("Enter event array");
                eventRows.push( 
                <EventRow events = {event} year={self.props.year} calendarId={self.props.calendarId} />        
                );
            } );
        }

        return ( <div className="form-group">
            <div className="col-sm-12">
                <div className="row">
                    <table className="table table-bordered" style={{border:"1px solid #3CABDB"}}>
                        <thead style={{ backgroundColor: "#3CABDB" }}>
                            <tr>
                                <th>Event Date</th>
                                <th>Event Title</th>
                                <th className="col-sm-2"/>
                            </tr>
                        </thead>
                        <tbody>
                            {eventRows}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        );
    }
}

class EventRow extends React.Component {
    constructor( props ) {
        super( props );
        this.handleClick = this.handleClick.bind( this );
        this.handleDelete = this.handleDelete.bind( this );
    }
    render() {
        console.log( "event row render" );
        return (
                <tr>
                <td>
                  <div>
                    {this.props.events.eventTitle}
                  </div>
                </td>
                <td>
                  <div>
                    <input type="submit" value="Edit" className="btn btn-warning" data-toggle="modal" data-target="#eventFormModal"  onClick={this.handleClick} />
                    <span style={{padding: '10px'}}></span>
                    <input type="submit" value="Delete" className="btn btn-danger" data-toggle="modal" data-target="#eventDeleteModal" onClick={this.handleDelete} />
                  </div>
                </td>
              </tr>
        );
    }

    handleClick( event ) {
        event.preventDefault();
        $( "#title" ).val( this.props.events.eventTitle );
        $( "#date" ).val( this.props.events.date );
        console.log("Date in event row::::"+this.props.events.date);
        $( "#id" ).val( this.props.events.id );
        $( "#year" ).val( this.props.years );
        $( "#calendarId" ).val( this.props.calendarId );
        $( "#event-form-button" ).val( "Save" );
        $( '#eventFormModal' ).modal( 'show' );
    }

    handleDelete( event ) {
        event.preventDefault();
        $( "#id" ).val( this.props.events.id );
        $( "#year" ).val( this.props.years );
        $( "#calendarId" ).val( this.props.calendarId );
        var img = new Image();
        img.src = "/resources/images/warning.jpg";
        $( "#modalBody" ).html( '<img src="' + img.src + '" width="60" height="60"/>' + '  You are about to delete <b>' + this.props.events.eventTitle + '</b>.Are you sure?' );
        $( '#eventDeleteModal' ).modal( 'show' );
    }
}

class EventDeleteRow extends React.Component {
    constructor( props ) {
        super( props );
        this.handleClick = this.handleClick.bind( this );
        this.handleDelete = this.handleDelete.bind( this );
    }

    render() {
        console.log( "event delete row render" );
        return (
                <tr>
                <td rowSpan={this.props.rowSpan}>
                  <div>
                    {this.props.eventDate}
                  </div>
                </td>
                <td>
                  <div>
                    {this.props.events.eventTitle}
                  </div>
                </td>
                <td>
                  <div>
                    <input type="submit" value="Edit" className="btn btn-warning" data-toggle="modal" data-target="#eventFormModal" onClick={this.handleClick} />
                    <span style={{padding: '10px'}}></span>
                   <input type="submit" value="Delete" className="btn btn-danger" data-toggle="modal" data-target="#eventDeleteModal"onClick={this.handleDelete} />
                  </div>
                </td>
              </tr>
        );
    }
    handleClick( event ) {
        event.preventDefault();
        $( "#title" ).val( this.props.events.eventTitle);
        $( "#date" ).val( this.props.eventDate );
        $( "#id" ).val( this.props.events.id );
        console.log("Id is::::"+this.props.events.id);
        $( "#year" ).val( this.props.year);
        $( "#calendarId" ).val( this.props.calendarId);
        $( "#event-form-button" ).val( "Save" );
        $( '#eventFormModal' ).modal( 'show' );
    }

    handleDelete( event ) {
        event.preventDefault();
        $( "#id" ).val( this.props.events.id);
        $( "#year" ).val( this.props.year);
        $( "#calendarId" ).val( this.props.calendarId);
        var img = new Image();
        img.src = "/resources/images/warning.jpg";
        $( "#modalBody" ).html( '<img src="' + img.src + '" width="60" height="60"/>' + '  You are about to delete <b>' + this.props.events.eventTitle + '</b>.Are you sure?' );
        $( '#eventDeleteModal' ).modal( 'show' );
    }
}

class YearEventLists extends React.Component {
    constructor( props ) {
        super( props );
    }

    render() {
        var url = "/admin/calendar_template/new_event/" + this.props.calendarId;
        return (
            <div className="form-group">
                <div className="col-sm-12">
                    <div className="row">
                        <div className="col-sm-1"> <button type="button" style={{ borderRadius: "50%", backgroundColor: "black", color: "white", height: "30px" }} className="glyphicon glyphicon-menu-left" id="backwardButton" onClick={this.props.handleBackward} /> </div>
                        <div className="col-sm-1"><h5> <div id="labelid"><label>{this.props.year}</label></div></h5> </div>
                        <div className="col-sm-2"> <button type="button" style={{ borderRadius: "50%", backgroundColor: "black", color: "white", height: "30px" }} className="glyphicon glyphicon-menu-right" id="forwardButton" onClick={this.props.handleForward} /> </div>
                        <div className="col-sm-offset-6 col-sm-2"><h5><a href={url} className="btn btn-warning">Add Event</a></h5> </div>
                    </div>
                </div>
            </div>
        );
    }
}


render( <Events />, document.getElementById( 'event' ) );