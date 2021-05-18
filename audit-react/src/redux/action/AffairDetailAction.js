import * as _creater from './ActionCreater';
import axios from 'axios';

export const getAffairDetail = (id) => {
	const mainFun = async (affairId, dispatch) => {
		let baseInfoRes = await axios.get('/api/affair/' + affairId);
		let approvalLogRes = await axios.get('/api/approval/' + affairId);
		let materialLogRes = await axios.get('/api/material/' + affairId);
		let sourceFileListRes = await axios.get('/api/report/source/' + affairId);
		let materialLog;
		if (materialLogRes.data.status === '00200') {
			materialLog = materialLogRes.data.content;
			for (let i = 0; i < materialLog.length; i++) {
				const materialFileRes = await axios.get(
					'/api/material/source/' + materialLog[i].id
				);
				if (materialFileRes.data.status === '00200') {
					materialLog[i]['fileList'] = materialFileRes.data.content;
				}
			}
		}

		if (
			baseInfoRes.data.status === '00200' &&
			approvalLogRes.data.status === '00200' &&
			sourceFileListRes.data.status === '00200'
		) {
			dispatch(_creater._getAffairBaseInfoAction(baseInfoRes.data.content));
			dispatch(
				_creater._getAffairApprovalLogAction(approvalLogRes.data.content)
			);
			dispatch(_creater._getAffairMaterialLogAction(materialLog));
			dispatch(
				_creater._getAffairSourceFileListAction(sourceFileListRes.data.content)
			);
			dispatch(_creater._getAffairDetailLoadingAction(false));
		}
		return 'ok';
	};
	return (dispatch, getState) => {
		dispatch(_creater._getAffairDetailLoadingAction(true));
		mainFun(id, dispatch).then((res) => {});
	};
};
