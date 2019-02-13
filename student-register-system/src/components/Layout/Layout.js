import React, { Component } from 'react';

import Aux from '../../hoc/Aux';
import classes from './Layout.css';
import Toolbar from '../Navigation/Toolbar/Toolbar';
import SideDrawer from '../Navigation/SideDrawer/SideDrawer';

import Main from '../../containers/Main/Main';
import Login from '../../containers/Login/Login';

class Layout extends Component {
    state = {
        showSideDrawer: false,
        isAdmin: false,
        isLoggedin: false,
        userAuth: null
    };

    sideDrawerClosedHandler = () => {
        this.setState({ showSideDrawer: false });
    };

    sideDrawerOpenHandler = () => {
        this.setState((prevState) => {
            return {
                showSideDrawer: !prevState.showSideDrawer
            };
        });
    };

    switchLoggedinHandler = () => {
        this.setState((prevState) => {
            return {
                isLoggedin: !prevState.isLoggedin
            };
        });
    };

    switchAdminHandler = (loginType) => {
        this.setState({
            isAdmin: loginType
        });
    };

    setUserAuth = (auth) => {
        this.setState({
            userAuth: auth
        });
    };
    render() {
        const mainPage = this.state.isLoggedin ? (
            <Main
                login={this.switchLoggedinHandler}
                type={this.switchAdminHandler}
                isAdmin={this.state.isAdmin}
                auth={this.state.userAuth}
            />
        ) : (
            <Login
                login={this.switchLoggedinHandler}
                type={this.switchAdminHandler}
                auth={this.setUserAuth}
            />
        );
        return (
            <Aux>
                <Toolbar
                    drawerToggleClicked={this.sideDrawerOpenHandler}
                    func={this.switchLoggedinHandler}
                />
                <SideDrawer
                    open={this.state.showSideDrawer}
                    closed={this.sideDrawerClosedHandler}
                />
                <main className={classes.Content}>{mainPage}</main>
            </Aux>
        );
    }
}

export default Layout;
