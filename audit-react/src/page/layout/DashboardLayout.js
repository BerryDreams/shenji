import {
    Box,
    Container,
    Drawer,
} from "@material-ui/core";
import MTable from "../../component/MTable";
import FormData from "../../__mocks__/FormData";
import MBreadCrumbs from "../../component/MBreadCrumbs";
import MAppBar from "../../component/MAppBar";
import MSideNavBar from "../../component/sidebar/MSideNavBar";
import {makeStyles} from "@material-ui/styles";
import {useState} from "react";

const useStyles = makeStyles((theme) => ({
    mainContent: {
        maxWidth: "100%",
        flex: "1 1 0%",
        position: "relative"
    },

    shrink: {
        width: 66,
        overflowX: 'hidden',
        transition: 'width .5s ease-in-out 200ms',
        '-moz-transition': 'width .5s ease-in-out 200ms', /* Firefox 4 */
        '-webkit-transition': 'width .5s ease-in-out 200ms', /* Safari 和 Chrome */
        '-o-transition': 'width .5s ease-in-out 200ms', /* Opera */
    },


    breadcrumb: {
        paddingBottom: "4.5rem",
        position: "relative",
        backgroundColor: '#11cdef'
    },
}));

export default function DashboardLayout() {

    const classes = useStyles();
    const [shrink, setShrink] = useState(false);

    const openShrink = () => {
        setShrink(!shrink);
    }

    return (
        <Box style={{display: "flex"}}>
            <div>
                <Drawer
                    variant="permanent"
                    anchor="left"
                    classes={{
                        paper: shrink ? classes.shrink : null,
                        docked: shrink ? classes.shrink : null
                    }}
                >
                    <MSideNavBar shrink={shrink} openShrink={openShrink}/>
                </Drawer>
            </div>
            <Box className={classes.mainContent}>
                <MAppBar/>
                <div className={classes.breadcrumb}>
                    <Container>
                        <div style={{paddingTop: '1.5rem', paddingBottom: '1.5rem'}}>
                            <MBreadCrumbs path="/audit/approve"/>
                        </div>
                    </Container>
                </div>
                <Container style={{marginTop: '-4.5rem'}}>
                    <MTable title="我的历史" data={FormData} pageSize={5}/>
                </Container>
            </Box>
        </Box>
    );
}