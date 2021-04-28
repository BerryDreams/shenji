import {Avatar, Box, Collapse, IconButton, List, ListItem, Typography} from "@material-ui/core";
import {
    AssignmentTurnedIn,
    ChevronRight,
    Home,
    HourglassFull,
    KeyboardArrowDown,
    Menu,
    PostAdd
} from "@material-ui/icons";
import ScrollBar from "react-perfect-scrollbar";
import {makeStyles, useTheme} from "@material-ui/styles";
import {useState} from "react";
import propTypes from "prop-types";
import React from "react";
import classnames from 'classnames'

const useStyles = makeStyles((theme) => ({
    titleBoxNotShrink: {
        display: 'flex',
        padding: '0px 1rem 1rem 1.5rem',
        alignItems: 'center',
        justifyContent: 'space-between',
    },

    titleBoxShrink: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },

    navList: {
        marginTop: '2rem',
    },

    navListNotShrink: {
        height: '100%',
    },

    title: {
        fontWeight: "bolder",
        color: theme.palette._info,
        marginBottom: 0
    },

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

    navListItemChild: {
        color: 'rgba(0, 0, 0, 0.5)',
        width: 'auto',
        display: 'flex',
        padding: '.8125rem 1rem !important',
        marginLeft: '.5rem',
        marginRight: '.5rem',
        borderRadius: '.375rem',
    },

    navListItemTextChildNotShrink: {
        marginLeft: 36
    },

    navListItemTextChildShrink: {
        marginLeft: 2
    },

    navListItemFatherIconBox: {
        display: 'flex',
        minWidth: '2.25rem',
        alignItems: 'center',
    },
    navIcon: {
        width: '1.25rem !important',
        height: '1.25rem !important',
    },

    navListItemFatherArrow: {
        marginLeft: 'auto',
        width: '1rem !important',
        height: '1rem !important',
        transition: 'all .15s ease',
    },
}));

function ArrowElement(props) {

    const {navListItemFatherArrow} = useStyles();

    const {isExpand} = props;

    return (
        <React.Fragment>
            {
                isExpand ?
                <KeyboardArrowDown className={navListItemFatherArrow}/>
                :<ChevronRight className={navListItemFatherArrow}/>
            }
        </React.Fragment>
    );
}

ArrowElement.propTypes = {
    isExpand: propTypes.bool
}

function MSideNavBarItem(props) {

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

function MSideNavBarItemOnly(props) {

    const classes = useStyles();
    const {title, icon, shrink} = props;

    let name = null;
    if(!shrink) {
        name = title
    }

    return (
        <ListItem button component="a" className={classes.navListItemFather}>
            <Box className={classes.navListItemFatherIconBox}>
                {icon}
            </Box>
            {name}
        </ListItem>
    );
}
MSideNavBarItemOnly.propTypes = {
    shrink: propTypes.bool,
    title: propTypes.string,
    icon: propTypes.node,
}


export default function MSideNavBar(props) {

    const theme = useTheme();
    const classes = useStyles();

    const {shrink, openShrink} = props;

    const [openItem, setOpenItem] = useState("");

    const expandClick = (event) => {
        const name = event.target.name;

        if(name === null || name === undefined || name === "") {
            return;
        }
        console.log("====" + name + "  " + openItem + "====");

        if(openItem === "") {
          setOpenItem(name);
        }else if(openItem === name){
          setOpenItem("");
        }else {
          setOpenItem(name);
        }


    };

    const menuClick = () => {
        openShrink();
    }

    let totalName = null;

    if(!shrink) {
        totalName = <Typography variant="h2" className={classes.title}>审计管理系统</Typography>
    }

    return (
        <ScrollBar>
            <Box className={classnames({
                [classes.titleBoxNotShrink]: !shrink,
                [classes.titleBoxShrink]: shrink
            })}>
                {totalName}
                <IconButton onClick={menuClick}>
                    <Menu className={classes.navIcon}/>
                </IconButton>
            </Box>
            <List
                component="ul"
                className={classnames({
                    [classes.navList]: true,
                    [classes.navListNotShrink]: !shrink
                })}
            >

                <MSideNavBarItemOnly
                    title="主页"
                    icon={
                        <Home className={classes.navIcon} style={{color: theme.palette.blue}}/>
                    }
                    shrink={shrink}
                />

                <MSideNavBarItem
                    name="create"
                    expand={openItem === "create"}
                    title="创建项目"
                    shrink={shrink}
                    icon={
                        <PostAdd className={classes.navIcon} style={{color: theme.palette._default}}/>
                    }
                    itemClick={expandClick}
                    list={
                        [
                            {
                                title: "报销审计",
                                url: "approve"
                            },
                            {
                                title: "全程跟踪审计",
                                url: "approve"
                            },
                        ]
                    }
                />

                <MSideNavBarItem
                    name="running"
                    expand={openItem === "running"}
                    title="正在进行"
                    shrink={shrink}
                    icon={
                        <HourglassFull className={classes.navIcon} style={{color: theme.palette.red}}/>
                    }
                    itemClick={expandClick}
                    list={
                        [
                            {
                                title: "我的审批",
                                url: "approve"
                            },
                            {
                                title: "我的审计",
                                url: "approve"
                            },
                        ]
                    }
                />

                <MSideNavBarItem
                    name="history"
                    expand={openItem === "history"}
                    title="历史记录"
                    shrink={shrink}
                    icon={
                        <AssignmentTurnedIn className={classes.navIcon} style={{color: theme.palette._success}}/>
                    }
                    itemClick={expandClick}
                    list={
                        [
                            {
                                title: "我的审批",
                                url: "approve"
                            }
                        ]
                    }
                />
            </List>
        </ScrollBar>
    );
}

MSideNavBar.propTypes = {
    shrink: propTypes.bool
}