import * as actionType from './ActionType';

//affairReducer
export const _getMyAffairAction = (newAffairList) => {
	return { type: actionType.GET_MY_AFFAIR, newAffairList };
};

export const _getToApproveAction = (newAffairList) => {
	return { type: actionType.GET_TO_APPROVE, newAffairList };
};

export const _getHistoryLoadingAction = () => {
	return { type: actionType.GET_HISTORY_LOADING };
};

export const _getHistoryAction = (newAffairList) => {
	return { type: actionType.GET_HISTORY, newAffairList };
};

//affairDetailReducer
export const _getAffairDetailLoadingAction = (inState) => {
	return { type: actionType.GET_AFFAIR_DETAIL_LOADING, inState };
};

export const _getAffairBaseInfoAction = (newBaseInfo) => {
	return { type: actionType.GET_AFFAIR_BASE_INFO, newBaseInfo };
};

export const _getAffairApprovalLogAction = (newApprovalLog) => {
	return { type: actionType.GET_AFFAIR_APPROVAL_LOG, newApprovalLog };
};

export const _getAffairSourceFileListAction = (newSourceFileList) => {
	return { type: actionType.GET_AFFAIR_SOURCE_FILE_LIST, newSourceFileList };
};

export const _getAffairMaterialLogAction = (newMaterialLog) => {
	return { type: actionType.GET_AFFAIR_MATERIAL_LOG, newMaterialLog };
};

//userReducer
export const _getUserInfo = (newUserInfo) => {
	return { type: actionType.GET_USER_INFO, newUserInfo };
};
