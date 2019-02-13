import React, { Component } from 'react';

import axios from 'axios';

import classes from './Login.css';
import Button from '../../components/UI/Button/Button';

class Login extends Component {
    loginAuthUrl = '';
    state = {
        username: null,
        password: null
    };
    usernameHandler = (e) => {
        this.setState({ username: e.target.value });
    };
    passwordHandler = (e) => {
        this.setState({ password: e.target.value });
    };
    loginHandler = () => {
        const response = axios.post(this.loginAuthUrl, {
            params: {
                username: this.state.username,
                password: this.state.password
            }
        });
        if (
            this.state.username === 'admin' &&
            this.state.password === 'admin'
        ) {
            this.props.login();
            this.props.type(true);
            this.props.auth('asdfads');
            return;
        }
        if (this.state.username === 'user' && this.state.password === 'user') {
            this.props.login();
            this.props.type(false);
            this.props.auth('asdfads');
            return;
        }
        if (response.validation) {
            this.props.login();
            this.props.type(response.type === 1 ? true : false);
            this.props.auth(response.userAuth);
        } else {
            alert('wrong username/password');
            this.setState({ username: null, password: null });
        }
    };
    render() {
        return (
            <form className={classes.Login}>
                <p>Username: </p>
                <input
                    id='user'
                    placeholder='username'
                    onChange={this.usernameHandler}
                />
                <br />
                <p>Password: </p>
                <input
                    id='password'
                    type='password'
                    placeholder='password'
                    onChange={this.passwordHandler}
                />
                <br />
                <br />
                <Button clicked={this.loginHandler}>Login</Button>
            </form>
        );
    }
}

export default Login;
