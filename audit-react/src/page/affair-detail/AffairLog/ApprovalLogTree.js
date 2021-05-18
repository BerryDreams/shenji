import {
	Timeline,
	TimelineConnector,
	TimelineContent,
	TimelineDot,
	TimelineItem,
	TimelineOppositeContent,
	TimelineSeparator
} from '@material-ui/lab';
import { Paper, Typography } from '@material-ui/core';
import { Check, DoneOutline, Fastfood } from '@material-ui/icons';
import { makeStyles } from '@material-ui/styles';
import { getExactTime } from '../util/TimeFormat';
import classnames from 'classnames';
import React from 'react';

const useStyles = makeStyles((theme) => ({
	opposite: {
		flex: 0.3
	},
	titles: {
		display: 'flex',
		flexFlow: 'row nowrap',
		alignItems: 'center'
	},
	title: {
		marginRight: 10
	},
	dotSuccess: {
		backgroundColor: theme.palette.green
	},
	dotFailure: {
		backgroundColor: theme.palette.red
	}
}));

export default function TimeTree(props) {
	const classes = useStyles();

	const { approvalLog } = props;

	React.useEffect(() => {}, []);

	let child = approvalLog.map((item, index) => (
		<TimelineItem key={index}>
			<TimelineOppositeContent classes={{ root: classes.opposite }}>
				<Typography variant="body2" color="textSecondary">
					{getExactTime(item.createTime)}
				</Typography>
			</TimelineOppositeContent>
			<TimelineSeparator>
				<TimelineDot
					className={classnames({
						[classes.dotSuccess]: item.isPass,
						[classes.dotFailure]: !item.isPass
					})}
				>
					<DoneOutline />
				</TimelineDot>
				<TimelineConnector />
			</TimelineSeparator>
			<TimelineContent>
				<div className={classes.titles}>
					<Typography variant="h6" component="h1" className={classes.title}>
						{item.isPass ? '通过' : '未通过'}
					</Typography>
					<Typography variant="h7">{item.authorId}</Typography>
				</div>
				<Typography variant="p">{item.msg}</Typography>
			</TimelineContent>
		</TimelineItem>
	));

	return <Timeline align="left">{child}</Timeline>;
}
