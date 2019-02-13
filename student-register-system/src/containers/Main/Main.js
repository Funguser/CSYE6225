import React, { Component } from 'react';
import axios from 'axios';
import MainEvenetDisplay from '../../components/MainEventDisplay/MainEventDisplay';

import Modal from '../../components/UI/Modal/Modal';
import Button from '../../components/UI/Button/Button';

class Main extends Component {
    getEventListUrl = '';
    state = {
        events: [123, 124, 125, 126],
        createNew: false
    };

    componentDidMount() {
        const eventlist = axios
            .get(this.getEventListUrl, {
                params: {
                    userAuth: this.props.auth
                }
            })
            .then((response) => {
                this.setState({
                    events: eventlist
                });
            });
    }

    createNewEvent = () => {
        this.setState({
            createNew: true
        });
    };
    createCancel = () => {
        this.setState({ createNew: false });
    };

    render() {
        const events = this.state.events.map((event) => {
            return (
                <MainEvenetDisplay
                    id={event.id}
                    auth={this.props.auth}
                    type={this.props.isAdmin}
                />
            );
        });
        const adminButton = this.props.isAdmin ? (
            <Button value='Create' clicked={this.createNewEvent}>
                Create
            </Button>
        ) : null;
        return (
            <div>
                <Modal
                    show={this.state.createNew}
                    modalClosed={this.createCancel}>
                    <MainEvenetDisplay
                        auth={this.props.auth}
                        type={this.props.isAdmin}
                        editable={true}
                        modelClosed={this.createCancel}
                        value={this.props.isAdmin ? 'create' : null}
                    />
                </Modal>
                {adminButton}
                <br />
                {events}
            </div>
        );
    }
}

export default Main;
