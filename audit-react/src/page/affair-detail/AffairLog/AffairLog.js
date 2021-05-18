import {
	Button,
	Card,
	CardContent,
	CardHeader,
	Fade,
	Grid,
	Modal
} from '@material-ui/core';
import ApprovalLogTree from './ApprovalLogTree';
import { connect } from 'react-redux';
import Backdrop from '@material-ui/core/Backdrop';
import MaterialLogTree from './MaterialLogTree';
import { makeStyles } from '@material-ui/styles';
import React from 'react';
import FileUpload from '../../../component/FileUpload';
import { postMaterial } from '../../../redux/action/AffairAction';
import PostMaterialBlock from './PostMaterialBlock';
import App from '../../App';
import ApproveBlock from './ApproveBlock';

const useStyles = makeStyles((theme) => ({
	button: {
		marginTop: 3,
		backgroundColor: theme.palette._info
	},
	modal: {
		display: 'flex',
		alignItems: 'center',
		justifyContent: 'center'
	}
}));

function AffairLog(props) {
	const { userInfo, baseInfo, approvalLog, materialLog } = props;
	const classes = useStyles();

	const [openA, setOpenA] = React.useState(false);
	const [openB, setOpenB] = React.useState(false);

	const handleOpenA = () => {
		setOpenA(true);
	};

	const handleCloseA = () => {
		setOpenA(false);
	};

	const handleOpenB = () => {
		setOpenB(true);
	};

	const handleCloseB = () => {
		setOpenB(false);
	};

	return (
		<Grid container spacing={2} style={{ marginTop: 30 }}>
			<Grid item lg={7} md={12}>
				<Card>
					<CardHeader
						title="跟踪审计资料节点"
						action={
							<Button
								variant="contained"
								color="secondary"
								onClick={handleOpenA}
								disabled={
									baseInfo.state !== 10 || userInfo.id !== baseInfo.promoterId
								}
								className={classes.button}
							>
								提交节点
							</Button>
						}
					/>
					<CardContent>
						<MaterialLogTree materialLog={materialLog} />
					</CardContent>
				</Card>
				<Modal
					aria-labelledby="transition-modal-title"
					aria-describedby="transition-modal-description"
					className={classes.modal}
					open={openA}
					onClose={handleCloseA}
					closeAfterTransition
					BackdropComponent={Backdrop}
					BackdropProps={{
						timeout: 500
					}}
				>
					<Fade in={openA}>
						<PostMaterialBlock affairId={baseInfo.id} />
					</Fade>
				</Modal>
			</Grid>
			<Grid item lg={5} md={12}>
				<Card>
					<CardHeader
						title="审批记录"
						action={
							<Button
								variant="contained"
								color="secondary"
								disabled={
									baseInfo.state !== 11 ||
									userInfo.post !== baseInfo.approverPost
								}
								onClick={handleOpenB}
								className={classes.button}
							>
								审批
							</Button>
						}
					/>
					<CardContent>
						<ApprovalLogTree approvalLog={approvalLog} />
					</CardContent>
				</Card>
				<Modal
					aria-labelledby="transition-modal-title"
					aria-describedby="transition-modal-description"
					className={classes.modal}
					open={openB}
					onClose={handleCloseB}
					closeAfterTransition
					BackdropComponent={Backdrop}
					BackdropProps={{
						timeout: 500
					}}
				>
					<Fade in={openB}>
						<ApproveBlock affairId={baseInfo.id} />
					</Fade>
				</Modal>
			</Grid>
		</Grid>
	);
}

export default connect(
	(state) => {
		return {
			baseInfo: state.affairDetailReducer.baseInfo,
			approvalLog: state.affairDetailReducer.approvalLog,
			//全程跟踪节点
			materialLog: state.affairDetailReducer.materialLog,

			userInfo: state.userInfoReducer.userInfo
		};
	},
	(dispatch) => {
		return {};
	}
)(AffairLog);
