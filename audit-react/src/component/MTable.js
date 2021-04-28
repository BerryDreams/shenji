import {
    Avatar,
    Box,
    Card, CardActions,
    CardHeader,
    Table, TableBody,
    TableCell, TableContainer,
    TableHead,
    TableRow,
} from "@material-ui/core";
import propTypes from 'prop-types';
import {Pagination} from "@material-ui/lab";
import {Assignment, AssignmentTurnedIn} from "@material-ui/icons";
import theme from "../theme";
import {makeStyles} from "@material-ui/styles";
import React from "react";

const useStyles = makeStyles((theme) => ({

    cardTitle: {
        fontWeight: 600,
        fontFamily: 'MSYaHei'
    },

    titleBoxIcon: {
        backgroundColor: '#fff',
        marginRight: '1rem',
    },

    iconSize: {
        width: '1.25rem !important',
        height: '1.25rem !important'
    },

    statusDot: {
        verticalAlign: 'middle',
        width: '0.375rem',
        height: '0.375rem',
        display: 'inline-block',
        marginRight: 10,
        borderRadius: '50%',
    },

    cardAction: {
        justifyContent: 'flex-end',
        borderTop: '0!important',
        paddingTop: '1.5rem!important',
        paddingBottom: '1.5rem!important',
    },
}));

function TitleBox(props) {

    const {titleBoxIcon} = useStyles();

    return (
        <Box m={0} style={{
            display: 'flex',
            alignItems: 'center',
        }}>
            <Avatar variant="circular" className={titleBoxIcon}>
                {props.icon}
            </Avatar>
            <Box component="span">
                {props.title}
            </Box>
        </Box>
    );
}

function StatusBox(props) {

    const { color } = props;
    const { statusDot } = useStyles();

    return (
        <Box m={0} style={{
            paddingTop: '0.35rem',
            paddingBottom: '0.35rem',
        }}>
            <i className={statusDot} style={{backgroundColor: theme.palette[color]}}/>
            {props.name}
        </Box>
    );
}

StatusBox.propTypes = {
    color: propTypes.oneOf([
        "_primary",
        "_info",
        "_secondary",
        "pink",
        "green",
        "red",
        "blue"
    ])
}



export default function MTable(props) {

    const { cardTitle, cardAction, iconSize } = useStyles();

    const { title, data, pageSize } = props;

    const [pageIndex, setPageIndex] = React.useState(1);

    const length = data.length;
    const pageNumber = Math.ceil(length / pageSize);

    const indexStart = (pageIndex - 1) * pageSize;
    const indexEnd = pageIndex * pageSize;

    const rows = data.slice(indexStart, indexEnd).map((row, index) => (
        <TableRow key={index}>
            <TableCell variant="head" children={
                <TitleBox title={row.title}
                          icon={row.status.name === "审批成功"
                              ?
                              <AssignmentTurnedIn
                                  style={{ color: theme.palette["green"] }}
                                  className={iconSize}/>
                              :
                              <Assignment
                                  color="primary"
                                  className={iconSize}/>
                          }/>
            }/>
            <TableCell>{row.author}</TableCell>
            <TableCell>
                <StatusBox name={row.status.name} color={row.status.color}/>
            </TableCell>
            <TableCell>{row.time}</TableCell>
            <TableCell>{row.remark}</TableCell>
        </TableRow>
    ));

    const pageChange = (event, page) => {
        setPageIndex(page);
    }

    return (
        <Card>
            <CardHeader title={title} className={cardTitle}>
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
            <CardActions className={cardAction}>
                <Pagination count={pageNumber} page={pageIndex} onChange={pageChange} variant="outlined" color="primary"/>
            </CardActions>
        </Card>
    );
}

MTable.propTypes = {
    data: propTypes.array,
    pageSize: propTypes.number
}