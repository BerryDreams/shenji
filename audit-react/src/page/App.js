import {
    AppBar,
    Box,
    Container,
    Drawer,
    Grid, IconButton,
    List,
    ListItem, ListItemIcon,
    Toolbar,
    Typography
} from "@material-ui/core";
import ScrollBar from "react-perfect-scrollbar";
import ColorPicker from "../../util/ColorPicker";
import MTable from "../../component/MTable";
import {Dashboard, Menu} from "@material-ui/icons";
import FormData from "../../__mocks__/FormData";

export default function App() {

    return (
        <Box style={{display: "flex"}}>
            <div>
                <Drawer
                    variant="permanent"
                    anchor="left"
                    classes={{
                        root: "css003",
                        paper: "css002"
                    }}
                    >
                    <ScrollBar>
                        <Box className="css004">
                            <Typography variant="h2" className="css001">审计管理系统</Typography>
                            <IconButton>
                                <Menu/>
                            </IconButton>
                        </Box>
                        <List>
                            <ListItem button>
                                <ListItemIcon>
                                    <Dashboard style={{color: ColorPicker("primary")}}/>
                                </ListItemIcon>
                                总览
                            </ListItem>
                            <ListItem button>
                                <ListItemIcon>
                                    <Dashboard style={{color: ColorPicker("primary")}}/>
                                </ListItemIcon>
                                我的审计
                            </ListItem>
                            <ListItem button>
                                <ListItemIcon>
                                    <Dashboard style={{color: ColorPicker("primary")}}/>
                                </ListItemIcon>
                                我的审批
                            </ListItem>
                            <ListItem button>
                                <ListItemIcon>
                                    <Dashboard style={{color: ColorPicker("primary")}}/>
                                </ListItemIcon>
                                总览
                            </ListItem>
                        </List>
                    </ScrollBar>
                </Drawer>
            </div>
            <Box className="css005">
                <AppBar position="relative" className='css100' elevation={0}>
                    <Toolbar>

                    </Toolbar>
                </AppBar>
                <Container classes={{root: "css007"}}>
                    <Grid container style={{paddingTop: '1.5rem', paddingBottom: '1.5rem'}}>
                        <Grid item xs={7} lg={6}>
                            <Typography variant="h2" style={{color: '#fff'}}>我的审计</Typography>
                        </Grid>
                        <Grid item xs={5} lg={6}>

                        </Grid>
                    </Grid>
                </Container>
                <Container style={{marginTop: '-4.5rem'}}>
                    <MTable data={FormData}/>
                </Container>
            </Box>
        </Box>
    );
}