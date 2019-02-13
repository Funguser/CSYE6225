import React from 'react';

import classes from './Logo.css';
import Logo from '../../assets/images/logo.png';

const logo = (props) => (
    <div className={classes.Logo} style={{ height: props.height }}>
        <img src={Logo} alt='logo' />
    </div>
);

export default logo;
