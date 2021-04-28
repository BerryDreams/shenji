import React from "react";
import classnames from "classnames";
import {Box, Collapse, List, ListItem} from "@material-ui/core";
import propTypes from "prop-types";
import {makeStyles} from "@material-ui/styles";
import ArrowElement from "./ArrowElement";

const useStyles = makeStyles((theme) => ({
    navListItemFather: {
        color: 'rgba(0, 0, 0, 0.5)',
        width: 'auto',
        display: 'flex',
        padding: '.8125rem 1rem !important',
        fontSize: '.9rem',
        fontWeight: 600,
        fontFamily: "MSYaHei",
        marginLeft: '.5rem',
        marginRight: '.5rem',
        borderRadius: '.375rem',
    },

    navListItemFatherIconBox: {
        display: 'flex',
        minWidth: '2.25rem',
        alignItems: 'center',
    },

    navListItemTextChildNotShrink: {
        marginLeft: 36
    },

    navListItemTextChildShrink: {
        marginLeft: 2
    },
}));


export default function MSideNavBarItem(props) {

    const classes = useStyles();

    const {shrink, expand, name, title, icon, itemClick, list} = props;

    let shrinkBlockF = null;

    if(!shrink) {
        shrinkBlockF = (
            <React.Fragment>
                {title}
                <ArrowElement expand={expand}/>
            </React.Fragment>
        )
    }

    const child = list.map(item => {

        let inner = null;

        const itemTextClasses = classnames({
            [classes.navListItemTextChildShrink]: shrink,
            [classes.navListItemTextChildNotShrink]: !shrink,
        })

        if(!shrink) {
            inner = <span className={itemTextClasses}>{item.title}</span>;
        }else {
            inner = <span className={itemTextClasses}>{item.title.charAt(0)}</span>;
        }

        return (
            <ListItem
                button
                component="a"
            >
                {inner}
            </ListItem>
        );
    });

    return (
        <React.Fragment>
            <ListItem
                button
                component="a"
                name={name}
                className={classes.navListItemFather}
                onClick={itemClick}
            >
                <Box className={classes.navListItemFatherIconBox}>
                    {icon}
                </Box>
                {shrinkBlockF}
            </ListItem>
            <Collapse in={expand} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                    {child}
                </List>
            </Collapse>
        </React.Fragment>
    );
}

MSideNavBarItem.propTypes = {
    shrink: propTypes.bool,
    expand: propTypes.bool,
    name: propTypes.string,
    title: propTypes.string,
    icon: propTypes.node,
    itemClick: propTypes.func,
    list: propTypes.arrayOf(propTypes.shape({
        title: propTypes.string,
        url: propTypes.string
    }))
}