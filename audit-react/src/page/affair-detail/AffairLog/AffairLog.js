import { Card, CardContent, CardHeader, Grid } from '@material-ui/core';
import TimeTree from '../../component/TimeTree';
import { connect } from 'react-redux';
import { getAffairDetail } from '../../redux/action/AffairDetailAction';

function AffairLog(props) {
	const { approvalLog } = props;

	return (
		<Grid container spacing={2} style={{ marginTop: 30 }}>
			<Grid item lg={6} md={12}>
				<Card>
					<CardHeader title="跟踪提交节点" />
					<CardContent></CardContent>
				</Card>
			</Grid>
			<Grid item lg={6} md={12}>
				<Card>
					<CardHeader title="审批记录" />
					<CardContent>
						<TimeTree approvalLog={approvalLog} />
					</CardContent>
				</Card>
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
			materialLog: state.affairDetailReducer.materialLog
		};
	},
	(dispatch) => {
		return {};
	}
)(AffairLog);
