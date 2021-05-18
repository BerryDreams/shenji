import {
	Avatar,
	Box,
	Card,
	CardActions,
	CardHeader,
	IconButton,
	LinearProgress,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TableRow
} from '@material-ui/core';
import propTypes from 'prop-types';
import { Pagination } from '@material-ui/lab';
import { Assignment, Visibility } from '@material-ui/icons';
import { makeStyles } from '@material-ui/styles';
import React from 'react';
import { number, string } from 'prop-types';
import { affairCodeToString as convert } from '../util/CodeToString';
import MStatusDot from './MStatusDot';
import { getDate } from '../util/TimeFormat';
import { useHistory } from 'react-router';

const useStyles = makeStyles((theme) => ({
	cardTitle: {
		fontWeight: 600,
		fontFamily: 'MSYaHei'
	},

	progress: {
		width: '100%'
	},

	progresscolorPrimary: {
		backgroundColor: theme.palette.pink
	},

	progressBarColorPrimary: {
		backgroundColor: theme.palette._info
	},

	titleBoxIcon: {
		backgroundColor: '#fff',
		marginRight: '1rem'
	},

	iconSize: {
		width: '1.25rem !important',
		height: '1.25rem !important'
	},

	cardAction: {
		justifyContent: 'flex-end',
		borderTop: '0!important',
		paddingTop: '1.5rem!important',
		paddingBottom: '1.5rem!important'
	}
}));

function TitleBox(props) {
	const { titleBoxIcon } = useStyles();

	return (
		<Box
			m={0}
			style={{
				display: 'flex',
				alignItems: 'center'
			}}
		>
			<Avatar variant="circular" className={titleBoxIcon}>
				{props.icon}
			</Avatar>
			<Box component="span">{props.title}</Box>
		</Box>
	);
}

export default function MTable(props) {
	const {
		cardTitle,
		cardAction,
		iconSize,
		progress,
		progresscolorPrimary,
		progressBarColorPrimary
	} = useStyles();
	const history = useHistory();

	const { loading, title, data, pageSize } = props;

	const handleClick = (id) => {
		history.push('/affair/' + id);
	};

	let tmp;

	if (!data) {
		tmp = [];
	} else {
		tmp = data;
	}

	const [pageIndex, setPageIndex] = React.useState(1);

	const length = tmp.length;
	const pageNumber = Math.ceil(length / pageSize);

	const indexStart = (pageIndex - 1) * pageSize;
	const indexEnd = pageIndex * pageSize;

	const rows = tmp.slice(indexStart, indexEnd).map((row, index) => (
		<TableRow key={index}>
			<TableCell
				variant="head"
				children={
					<TitleBox
						title={row.name}
						icon={<Assignment color="primary" className={iconSize} />}
					/>
				}
			/>
			<TableCell>{row.promoterId}</TableCell>
			<TableCell>{convert(row.kind)}</TableCell>
			<TableCell>
				<MStatusDot name={convert(row.state)} />
			</TableCell>
			<TableCell>{getDate(row.startTime)}</TableCell>
			<TableCell>{row.remark}</TableCell>
			<TableCell>
				<IconButton
					aria-label="more"
					aria-controls="long-menu"
					aria-haspopup="true"
					onClick={() => handleClick(row.id)}
				>
					<Visibility />
				</IconButton>
			</TableCell>
		</TableRow>
	));

	const pageChange = (event, page) => {
		setPageIndex(page);
	};

	return (
		<Card>
			<CardHeader title={title} className={cardTitle} />
			<TableContainer>
				{loading ? (
					<LinearProgress
						className={progress}
						classes={{
							colorPrimary: progresscolorPrimary,
							barColorPrimary: progressBarColorPrimary
						}}
					/>
				) : null}
				<Table>
					<TableHead>
						<TableRow>
							<TableCell>名称</TableCell>
							<TableCell>作者</TableCell>
							<TableCell>类型</TableCell>
							<TableCell>状态</TableCell>
							<TableCell>创建时间</TableCell>
							<TableCell>详情</TableCell>
							<TableCell>操作</TableCell>
						</TableRow>
					</TableHead>

					<TableBody>{rows}</TableBody>
				</Table>
			</TableContainer>
			<CardActions className={cardAction}>
				<Pagination
					count={pageNumber}
					page={pageIndex}
					onChange={pageChange}
					variant="outlined"
					color="primary"
				/>
			</CardActions>
		</Card>
	);
}

MTable.propTypes = {
	data: propTypes.arrayOf(
		propTypes.shape({
			id: string,
			name: string,
			remark: string,
			kind: number,
			state: number,
			startTime: string,
			endTime: string,
			promoterId: string,
			approverPost: number
		})
	),
	pageSize: propTypes.number
};
