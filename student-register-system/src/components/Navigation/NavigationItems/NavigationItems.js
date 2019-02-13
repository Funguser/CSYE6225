import React from 'react';

import classes from './NavigationItems.css';
import NavigationItem from './NavigationItem/NavigationItem';

const navigationItems = (props) => (
    <ul className={classes.NavigationItems}>
        <NavigationItem link='/' func={props.func}>
            logout
        </NavigationItem>
        <NavigationItem>My Info</NavigationItem>
    </ul>
);

export default navigationItems;
