import {AppBar, Avatar, Box, InputBase, SvgIcon, Toolbar, Tooltip} from "@material-ui/core";
import {makeStyles} from "@material-ui/styles";


const useStyles = makeStyles((theme) => ({
    searchIcon: {
        color: '#32325d',
        marginLeft: '1rem',
        marginRight: '0.5rem',
    },

    inputBase: {
        marginRight: 20
    },

    appBarStyle: {
        borderBottom: '1px solid rgba(255, 255, 255, 0.08)',
        backgroundColor: theme.palette._info
    },
    barWrapper: {
        width: '100%',
        display: 'flex',
        marginTop: '1rem',
        alignItems: 'center',
        marginBottom: '1rem',
        justifyContent: 'center',
    },
    searchField: {
        border: '2px solid',
        boxShadow: '0 1px 3px rgb(50 50 93 / 15%), 0 1px 0 rgb(0 0 0 / 2%)',
        transition: 'box-shadow .15s ease',
        borderColor: 'rgba(255, 255, 255, 0.6)',
        borderRadius: '2rem',
        backgroundColor: 'rgba(255, 255, 255,0.9)',

        width: 'auto',
        display: 'flex',
        alignItems: 'center',
        marginRight: '1rem',
    },
    userBlock: {
        marginLeft: 'auto',
    },

    avatar: {
        backgroundColor: theme.palette._primary,
    }
}));

export default function MAppBar() {

    const classes = useStyles();

    return (
        <AppBar position="relative" className={classes.appBarStyle} elevation={0}>
            <Toolbar>
                <Box className={classes.barWrapper}>
                    <Box className={classes.searchField}>
                        <SvgIcon className={classes.searchIcon}>
                            <path  d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
                        </SvgIcon>
                        <InputBase classes={{root: classes.inputBase}}/>
                    </Box>
                    <Tooltip title="陈振国" aria-label="用户名" className={classes.userBlock}>
                        <Avatar className={classes.avatar}>陈</Avatar>
                    </Tooltip>
                </Box>
            </Toolbar>
        </AppBar>
    )
}