import React from 'react';

import classes from './NavigationItem.css';

const navigationItem = (props) => (
    <li className={classes.NavigationItem}>
        <a
            href={props.link}
            onClick={props.func}
            className={props.active ? classes.active : null}>
            {props.children}
        </a>
    </li>
);
export default navigationItem;
