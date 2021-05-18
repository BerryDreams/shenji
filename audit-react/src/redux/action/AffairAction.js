import axios from 'axios';
import * as _creater from './ActionCreater';

export const createFollowAffairAction = (name, remark) => {
	let formData = new FormData();
	formData.append('name', name);
	formData.append('remark', remark);
	axios.post('/api/follow_up_audit/init', formData).then(({ data }) => {
		console.log(data.content);
	});
};

export const createReimAffairAction = (name, remark, fileList) => {
	let formData = new FormData();
	formData.append('name', name);
	formData.append('remark', remark);
	fileList.forEach((file) => {
		formData.append('files', file);
	});
	axios.post('/api/reimbursement_audit/init', formData).then(({ data }) => {
		console.log(data.content);
	});
};

export const postReport = (affairId, files) => {
	let formData = new FormData();
	formData.append('affair_id', affairId);
	files.forEach((file) => {
		formData.append('files', file);
	});
	axios.post('/api/report/source', formData).then(({ data }) => {
		if (data.status === '00200') console.log('提交报告成功');
	});
};

export const postMaterial = (affairId, name, remark, files) => {
	let formData = new FormData();
	formData.append('affair_id', affairId);
	formData.append('name', name);
	formData.append('remark', remark);
	files.forEach((file) => {
		formData.append('files', file);
	});
	axios.post('/api/material', formData).then(({ data }) => {
		if (data.status === '00200') console.log('提交材料成功');
	});
};

export const approve = (affairId, isPass, msg, files) => {
	let formData = new FormData();
	formData.append('affair_id', affairId);
	formData.append('is_pass', isPass);
	formData.append('msg', msg);
	files.forEach((file) => {
		formData.append('files', file);
	});
	axios.post('/api/approve', formData).then(({ data }) => {
		if (data.status === '00200') console.log('审批成功');
	});
};

export const getMyAffairAction = () => {
	return (dispatch, getState) => {
		axios.get('/api/my_affair').then(({ data }) => {
			dispatch(_creater._getMyAffairAction(data.content));
		});
	};
};

export const getToApproveAction = () => {
	return (dispatch, getState) => {
		axios.get('/api/to_approve').then(({ data }) => {
			dispatch(_creater._getToApproveAction(data.content));
		});
	};
};

export const getHistoryAction = () => {
	return (dispatch, getState) => {
		dispatch(_creater._getHistoryLoadingAction());
		axios.get('/api/history').then(({ data }) => {
			dispatch(_creater._getHistoryAction(data.content));
		});
	};
};
