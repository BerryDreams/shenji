import MAppBar from "../../component/MAppBar";
import {Box, Container} from "@material-ui/core";
import MBreadCrumbs from "../../component/MBreadCrumbs";
import {Redirect, Route, Switch} from "react-router-dom";
import Home from "../home/Home";
import CreateFollow from "../create/CreateFollow";
import CreateReim from "../create/CreateReim";
import MyAffair from "../running/MyAffair";
import ToApprove from "../running/ToApprove";
import History from "../history/History";
import {makeStyles} from "@material-ui/styles";
import DashboardDetail from "./DashboardDetail";

const useStyles = makeStyles((theme) => ({
    mainContent: {
        maxWidth: '100%',
        flex: '1 1 0%',
        position: 'relative'
    },



    breadcrumb: {
        paddingBottom: '4.5rem',
        position: 'relative',
        backgroundColor: theme.palette._info
    }
}));

export default function DashboardMain(props) {

    const classes = useStyles();
    const { history } = props;

    return (<Box className={classes.mainContent}>
        <MAppBar />
        <div className={classes.breadcrumb}>
            <Container>
                <div style={{ paddingTop: '1.5rem', paddingBottom: '1.5rem' }}>
                    <MBreadCrumbs path={history.location.pathname} />
                </div>
            </Container>
        </div>
        <Container style={{ marginTop: '-4.5rem' }}>
            <Switch>
                <Route exact path="/" component={Home}>
                    <Redirect to="/home"/>
                </Route>
                <Route path="/home" component={Home}/>
                <Route path="/create">
                    <Route path="/create/follow" component={CreateFollow}/>
                    <Route
                        path="/create/reimbursement"
                        component={CreateReim}
                    />
                </Route>
                <Route path="/running">
                    <Route path="/running/my_affair" component={MyAffair}/>
                    <Route path="/running/my_approval" component={ToApprove}/>
                </Route>
                <Route path="/history" component={History}/>
                <Route path="/user"/>

                <Route path="/affair/:id" component={DashboardDetail}/>
            </Switch>
        </Container>
    </Box>);
}