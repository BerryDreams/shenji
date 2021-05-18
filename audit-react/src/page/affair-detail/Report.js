import { Paper } from '@material-ui/core';
import { connect } from 'react-redux';

import React from 'react';
import { makeStyles } from '@material-ui/styles';
import clsx from 'clsx';

function SvgButton() {
	return (
		<svg
			xmlns="http://www.w3.org/2000/svg"
			width="54"
			height="14"
			viewBox="0 0 54 14"
		>
			<g fill="none" fill-rule="evenodd" transform="translate(1 1)">
				<circle
					cx="6"
					cy="6"
					r="6"
					fill="#FF5F56"
					stroke="#E0443E"
					stroke-width=".5"
				></circle>
				<circle
					cx="26"
					cy="6"
					r="6"
					fill="#FFBD2E"
					stroke="#DEA123"
					stroke-width=".5"
				></circle>
				<circle
					cx="46"
					cy="6"
					r="6"
					fill="#27C93F"
					stroke="#1AAB29"
					stroke-width=".5"
				></circle>
			</g>
		</svg>
	);
}

const useStyles = makeStyles((theme) => ({
	paper: {
		marginTop: 13,
		height: '666px',
		borderRadius: 7
	},
	bar: {
		paddingTop: 11,
		paddingLeft: 14,
		paddingBottom: 7,
		borderBottom: '1px solid #e9ecef',
		borderRadius: '7px 7px 0 0'
	},
	iframe: {
		/* 设置滚动条的样式 */
		'& ::-webkit-scrollbar': {
			width: 3
		},
		/*滚动槽*/
		'& ::-webkit-scrollbar-track': {
			boxShadow: 'inset 0 0 6px rgba(0,0,0,0.1)',
			borderRadius: 10
		},
		/* 滚动条滑块 */
		'& ::-webkit-scrollbar-thumb': {
			borderRadius: 10,
			background: 'rgba(0, 0, 0, 0.1)',
			boxShadow: 'inset 0 0 6px rgba(0, 0, 0, 0.15)'
		}
	}
}));

function Report(props) {
	const { baseInfo } = props;
	const classes = useStyles();

	const [fullScreen, setFullScreen] = React.useState(true);

	return (
		<Paper elevation={2} className={classes.paper}>
			<div className={classes.bar}>
				<SvgButton />
			</div>
			<iframe
				frameBorder="0"
				className={classes.iframe}
				src={'http://10.2.9.173/api/report/pdf/' + baseInfo.id + '/view'}
				style={{ width: '100%', height: '100%' }}
			/>
		</Paper>
	);
}

export default connect((state) => {
	return {
		baseInfo: state.affairDetailReducer.baseInfo
	};
})(Report);
