import React, { Component } from 'react';

import Aux from '../../hoc/Aux';
import axios from 'axios';
import Button from '../UI/Button/Button';
import classes from './MainEventDisplay.css';

class MainEvenetDisplay extends Component {
    getEventDetialUrl = '';
    postEventUrl = '';
    updateEventUrl = '';
    deleteEventUrl = '';
    registerEventUrl = '';
    state = {
        id: null,
        name: null,
        pic: null,
        detail: null
    };
    componentDidMount() {
        const userAuth = this.props.userAuth;
        const eventId = this.props.id;
        const response = axios.get(this.getEventDetialUrl, {
            params: {
                user: userAuth,
                id: eventId
            }
        });
        this.setState({
            name: response.name,
            pic: response.pic,
            detail: response.detail
        });
    }

    updateEventHandler = () => {
        let response = null;
        if (this.props.value === 'create')
            response = axios.post(this.postEventUrl, {
                eventName: this.state.name,
                eventDetail: this.state.detail,
                image: this.state.pic
            });
        else {
            response = axios.update(this.updateEventUrl, {
                id: this.state.id,
                eventName: this.state.name,
                eventDetail: this.state.detail,
                image: this.state.pic
            });
        }
        if (response.result === true) {
            this.setState({
                id: response.id,
                name: response.eventName,
                pic: response.image,
                detail: response.eventDetail
            });
            alert('Success');
        } else {
            alert('Failed');
        }
        this.props.modelClosed();
    };

    deleteEventHandler = () => {
        if (this.props.value !== 'create') {
            const response = axios.delete(this.deleteEventUrl, {
                id: this.state.id
            });
            if (response.result === true) {
                alert('Delete Success');
            } else {
                alert('Delete Failed');
            }
        }
        this.props.modelClosed();
    };

    registerEvenetHandler = () => {
        const response = axios.post(this.registerEventUrl, {
            id: this.state.id,
            userAuth: this.props.userAuth
        });
        if (response.result === true) {
            alert('Register Success');
        } else {
            alert('Register Failed');
        }
    };

    render() {
        const modButton = this.props.type ? (
            <div>
                <Button value='Edit' clicked={this.updateEventHandler}>
                    Edit
                </Button>
                <br />
                <Button value='Delete' clicked={this.deleteEventHandler}>
                    Delete
                </Button>
            </div>
        ) : (
            <div>
                <Button value='Join' clicked={this.registerEvenetHandler}>
                    Join
                </Button>
            </div>
        );
        return (
            <div className={classes.Display}>
                <img src={this.state.img} alt='default' />
                <p>Event Name: {this.state.name}</p>
                {this.props.type ? <input /> : <br />}
                <p>Event Detail: {this.state.detail}</p>
                {this.props.type ? <input /> : <br />}
                {modButton}
            </div>
        );
    }
}

export default MainEvenetDisplay;
