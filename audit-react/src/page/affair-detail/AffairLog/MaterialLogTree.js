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
import { getExactTime } from '../../../util/TimeFormat';
import classnames from 'classnames';
import React from 'react';
import MFileList from '../../../component/MFileList';

const useStyles = makeStyles((theme) => ({
	opposite: {
		flex: 0.3
	},

	dot: {
		backgroundColor: theme.palette._info
	},

	content: {
		marginTop: '-.5rem'
	},

	title: {
		marginBottom: '1rem'
	},

	remark: {}
}));

export default function MaterialLogTree(props) {
	const classes = useStyles();

	const { materialLog } = props;

	React.useEffect(() => {}, []);

	let child = [...materialLog].reverse().map((item, index) => (
		<TimelineItem key={index}>
			<TimelineOppositeContent classes={{ root: classes.opposite }}>
				<Typography variant="body2" color="textSecondary">
					{getExactTime(item.createTime)}
				</Typography>
			</TimelineOppositeContent>
			<TimelineSeparator>
				<TimelineDot className={classes.dot} />
				<TimelineConnector />
			</TimelineSeparator>
			<TimelineContent className={classes.content}>
				<Typography variant="h6" component="h1" className={classes.title}>
					{item.name}
				</Typography>

				<div>
					<div className={classes.remark}>{item.remark}</div>
					<MFileList
						baseUrl={'http://10.2.9.173/api/material/source/' + item.id + '/'}
						fileList={item.fileList}
					/>
				</div>
			</TimelineContent>
		</TimelineItem>
	));

	return <Timeline align="left">{child}</Timeline>;
}
