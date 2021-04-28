import {
    Avatar,
    Box,
    Card, CardActions,
    CardHeader,
    makeStyles,
    Table, TableBody,
    TableCell, TableContainer,
    TableHead,
    TableRow,
} from "@material-ui/core";
import {Pagination} from "@material-ui/lab";
import './MTable.scss';
import {Assignment, AssignmentTurnedIn} from "@material-ui/icons";
import ColorPicker from "../../util/ColorPicker";

function TitleBox(props) {
    return (
        <Box m={0} style={{
            display: 'flex',
            alignItems: 'center',
        }}>
            <Avatar variant={"circular"} style={{backgroundColor: '#fff', marginRight: '1rem'}}>
                {props.icon}
            </Avatar>
            <Box component={"span"}>
                {props.title}
            </Box>
        </Box>
    );
}

function StatusBox(props) {

    const dotClasses = {
        backgroundColor: ColorPicker(props.color),
        verticalAlign: 'middle',
        width: '0.375rem',
        height: '0.375rem',
        display: 'inline-block',
        marginRight: 10,
        borderRadius: '50%',
    }

    return (
        <Box m={0} style={{
            paddingTop: '0.35rem',
            paddingBottom: '0.35rem',
        }}>
            <i style={dotClasses}/>
            {props.name}
        </Box>
    );
}


const cardActions = makeStyles((theme) => ({
    root: {
        justifyContent: 'flex-end',
        borderTop: '0!important',
        paddingTop: '1.5rem!important',
        paddingBottom: '1.5rem!important',
    }
}));

export default function MTable(props) {

    const cardActionsClasses = cardActions();

    // header, data, size,
    const rows = props.data.map(row => (
        <TableRow>
            <TableCell variant="head" children={
                <TitleBox title={row.title} icon={row.status.name === "审批成功"?<AssignmentTurnedIn style={{ color: ColorPicker("green") }}/>:<Assignment color="primary"/>}/>
            }/>
            <TableCell>{row.author}</TableCell>
            <TableCell>
                <StatusBox name={row.status.name} color={row.status.color}/>
            </TableCell>
            <TableCell>{row.time}</TableCell>
            <TableCell>{row.remark}</TableCell>
        </TableRow>
    ));

    return (
        <Card>
            <CardHeader title="我的审计">
            </CardHeader>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>名称</TableCell>
                            <TableCell>作者</TableCell>
                            <TableCell>状态</TableCell>
                            <TableCell>时间</TableCell>
                            <TableCell>详情</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows}
                    </TableBody>
                </Table>
            </TableContainer>
            <CardActions classes={{root: cardActionsClasses.root}}>
                <Pagination count={10} variant="outlined" color="primary"/>
            </CardActions>
        </Card>
    );
}