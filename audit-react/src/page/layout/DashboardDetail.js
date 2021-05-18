import { makeStyles } from '@material-ui/styles';
import {
	AppBar,
	Avatar,
	Box,
	Container,
	Tabs,
	Tab,
	Toolbar,
	Tooltip,
	IconButton,
	Typography,
	SvgIcon,
	Icon,
	LinearProgress
} from '@material-ui/core';
import React, { useState } from 'react';
import { ArrowBack } from '@material-ui/icons';
import FileList from '../affair-detail/FileList';
import AffairLog from '../affair-detail/AffairLog/AffairLog';
import Overview from '../affair-detail/Overview';
import Report from '../affair-detail/Report';
import { connect } from 'react-redux';
import { useHistory } from 'react-router';
import { getAffairDetail } from '../../redux/action/AffairDetailAction';
import classnames from 'classnames';

const useStyles = makeStyles((theme) => ({
	hidden: {
		display: 'none'
	},
	mainContent: {
		maxWidth: '100%',
		flex: '1 1 0%',
		position: 'relative'
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
		justifyContent: 'center'
	},

	userBlock: {
		marginLeft: 'auto'
	},

	avatar: {
		backgroundColor: theme.palette._primary
	},

	middle: {
		paddingBottom: '2rem',
		position: 'relative',
		backgroundColor: theme.palette._info
	},

	title: {
		color: '#fff',
		display: 'flex',
		fontSize: 25,
		fontWeight: 500,
		padding: '1rem 0 0 3rem',
		alignItems: 'center'
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

	tabsRoot: {
		marginLeft: theme.spacing(1)
	},
	tabsIndicator: {
		height: 3,
		borderTopLeftRadius: 3,
		borderTopRightRadius: 3,
		backgroundColor: theme.palette.common.white
	},

	tabRoot: {
		textTransform: 'initial',
		margin: `0 ${theme.spacing(2)}px`,
		minWidth: 0,
		[theme.breakpoints.up('md')]: {
			minWidth: 0
		}
	},
	tabLabel: {
		fontWeight: 'normal',
		letterSpacing: 0.5
	},
	tabLabelContainer: {
		padding: 0,
		[theme.breakpoints.up('md')]: {
			padding: 0,
			paddingLeft: 0,
			paddingRight: 0
		}
	}
}));

function DashboardDetail(props) {
	const classes = useStyles();
	const { match, init, baseInfo, loading } = props;

	const [tab, setTab] = useState('#overview');
	const [content, setContent] = useState(<Overview />);

	const onHashChange = () => {
		const tmp = window.location.hash;
		if (hash.indexOf(tmp) > -1) {
			setTab(window.location.hash);
			if (tmp === '#overview') {
				setContent(<Overview />);
			} else if (tmp === '#file') {
				setContent(<FileList />);
			} else if (tmp === '#log') {
				setContent(<AffairLog />);
			} else if (tmp === '#report') {
				setContent(<Report />);
			} else {
				setContent(<div>loading...</div>);
			}
		}
	};

	React.useEffect(() => {
		window.addEventListener('hashchange', onHashChange, false);
		console.log(match.params.id);
		if (match.params.id) {
			init(match.params.id);
		}
		return () => {
			window.removeEventListener('hashchange', onHashChange);
		};
	}, []);

	const list = ['概览', '日志', '文件', '报告'];

	const hash = ['#overview', '#log', '#file', '#report'];

	return (
		<Box className={classes.mainContent}>
			<AppBar
				position="relative"
				className={classnames({
					[classes.appBarStyle]: true,
					[classes.hidden]: tab === '#report'
				})}
				elevation={0}
			>
				<Toolbar>
					<Box className={classes.barWrapper}>
						<Box>
							<IconButton>
								<SvgIcon style={{ width: 20, height: 20 }}>
									<path
										d="M5,13L9,17L7.6,18.42L1.18,12L7.6,5.58L9,7L5,11H21V13H5M21,6V8H11V6H21M21,16V18H11V16H21Z"
										fill="#fff"
									/>
								</SvgIcon>
							</IconButton>
						</Box>
						<Tooltip
							title="陈振国"
							aria-label="用户名"
							className={classes.userBlock}
						>
							<Avatar className={classes.avatar}>陈</Avatar>
						</Tooltip>
					</Box>
				</Toolbar>
			</AppBar>
			<div
				className={classnames({
					[classes.middle]: true,
					[classes.hidden]: tab === '#report'
				})}
			>
				<Typography className={classes.title}>{baseInfo.name}</Typography>
			</div>
			{loading ? (
				<LinearProgress
					className={classes.progress}
					classes={{
						colorPrimary: classes.progresscolorPrimary,
						barColorPrimary: classes.progressBarColorPrimary
					}}
				/>
			) : null}
			<AppBar position="static" className={classes.appBarStyle}>
				<Tabs
					value={hash.indexOf(tab)}
					classes={{
						root: classes.tabsRoot,
						indicator: classes.tabsIndicator
					}}
				>
					{list.map((name, index) => (
						<Tab
							label={name}
							key={index}
							onClick={() => {
								setTab(hash[index]);
								window.location['hash'] = hash[index];
							}}
							classes={{
								root: classes.tabRoot
								// label: classes.tabLabel,
								// labelContainer: classes.tabLabelContainer
							}}
							disableRipple
						/>
					))}
				</Tabs>
			</AppBar>

			<Container className={classnames({ [classes.hidden]: loading })}>
				{content}
			</Container>
		</Box>
	);
}

export default connect(
	(state) => {
		return {
			loading: state.affairDetailReducer.loading,
			baseInfo: state.affairDetailReducer.baseInfo,
			approvalLog: state.affairDetailReducer.approvalLog,
			sourceFileList: state.affairDetailReducer.sourceFileList,

			//全程跟踪节点
			materialLog: state.affairDetailReducer.materialLog
		};
	},
	(dispatch) => {
		return {
			init: (affairId) => dispatch(getAffairDetail(affairId))
		};
	}
)(DashboardDetail);
