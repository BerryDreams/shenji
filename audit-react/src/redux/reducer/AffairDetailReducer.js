import * as actionType from '../action/ActionType';

const initialState = {
	loading: true,

	//报销审计全部
	baseInfo: {},
	approvalLog: [],
	sourceFileList: [],

	//全程跟踪节点
	materialLog: []
};

export const affairDetailReducer = (state = initialState, action) => {
	switch (action.type) {
		case actionType.GET_AFFAIR_DETAIL_LOADING:
			return {
				...state,
				loading: action.inState
			};
		case actionType.GET_AFFAIR_BASE_INFO:
			return {
				...state,
				baseInfo: action.newBaseInfo
			};
		case actionType.GET_AFFAIR_APPROVAL_LOG:
			return {
				...state,
				approvalLog: action.newApprovalLog
			};
		case actionType.GET_AFFAIR_SOURCE_FILE_LIST:
			return {
				...state,
				sourceFileList: action.newSourceFileList
			};
		case actionType.GET_AFFAIR_MATERIAL_LOG:
			return {
				...state,
				materialLog: action.newMaterialLog
			};

		default:
			return state;
	}
};
