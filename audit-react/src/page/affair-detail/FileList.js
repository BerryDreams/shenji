import {
	Button,
	Card,
	CardContent,
	CardHeader,
	Fade,
	Grid,
	List,
	ListItem,
	ListItemIcon,
	ListItemText,
	Modal,
	SvgIcon,
	Switch,
	TextField
} from '@material-ui/core';
import { FileCopy } from '@material-ui/icons';
import MFileList from '../../component/MFileList';
import { makeStyles } from '@material-ui/styles';
import { connect } from 'react-redux';
import React from 'react';
import Backdrop from '@material-ui/core/Backdrop';
import FileUpload from '../../component/FileUpload';
import { approve, postReport } from '../../redux/action/AffairAction';

const useStyles = makeStyles((theme) => ({
	modalPaper: {
		backgroundColor: theme.palette.background.paper,
		boxShadow: theme.shadows[5],
		padding: theme.spacing(2, 4, 3),
		width: 550,
		borderRadius: 4,

		display: 'flex',
		flexDirection: 'column',
		alignItems: 'center'
	},
	button: {
		marginTop: 3,
		backgroundColor: theme.palette._info
	},
	avatar: {
		marginTop: 4
	},
	modal: {
		display: 'flex',
		alignItems: 'center',
		justifyContent: 'center'
	}
}));

function PostReportBlock(props) {
	const classes = useStyles();

	const { affairId } = props;
	const [fileList, setFileList] = React.useState([]);

	const onFileListChange = (e) => {
		setFileList(e);
	};

	return (
		<div className={classes.modalPaper}>
			<FileUpload className={classes.formItem} onChange={onFileListChange} />
			<Button
				variant="contained"
				color="secondary"
				onClick={() => postReport(affairId, fileList)}
				className={classes.button}
			>
				上传草稿
			</Button>
		</div>
	);
}

function FileList(props) {
	const { userInfo, baseInfo, fileList } = props;

	const classes = useStyles();

	const [open, setOpen] = React.useState(false);

	const handleClose = () => {
		setOpen(false);
	};

	const handleOpen = () => {
		setOpen(true);
	};

	return (
		<Card style={{ marginTop: 30 }}>
			<CardHeader
				classes={{
					avatar: classes.avatar
				}}
				action={
					<Button
						variant="contained"
						color="secondary"
						onClick={handleOpen}
						disabled={
							(baseInfo.state !== 10 && baseInfo.state !== 12) ||
							userInfo.id !== baseInfo.promoterId
						}
						className={classes.button}
					>
						提交草稿
					</Button>
				}
				avatar={
					<SvgIcon style={{ height: 24, width: 24 }}>
						<path d="M3,3H9V7H3V3M15,10H21V14H15V10M15,17H21V21H15V17M13,13H7V18H13V20H7L5,20V9H7V11H13V13Z" />
					</SvgIcon>
				}
				title="报告草稿文件"
			/>
			<CardContent>
				<MFileList
					baseUrl={'http://10.2.9.173/api/report/source/' + baseInfo.id + '/'}
					fileList={fileList}
				/>
			</CardContent>
			<Modal
				aria-labelledby="transition-modal-title"
				aria-describedby="transition-modal-description"
				className={classes.modal}
				open={open}
				onClose={handleClose}
				closeAfterTransition
				BackdropComponent={Backdrop}
				BackdropProps={{
					timeout: 500
				}}
			>
				<Fade in={open}>
					<PostReportBlock affairId={baseInfo.id} />
				</Fade>
			</Modal>
		</Card>
	);
}

export default connect((state) => {
	return {
		baseInfo: state.affairDetailReducer.baseInfo,
		fileList: state.affairDetailReducer.sourceFileList,

		userInfo: state.userInfoReducer.userInfo
	};
})(FileList);
