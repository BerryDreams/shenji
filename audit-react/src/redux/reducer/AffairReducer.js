import * as actionType from '../action/ActionType';

const initialState = {
  loading: false,
  affairList: []
};

const myAffairReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionType.GET_MY_AFFAIR:
      return {
        ...state,
        affairList: action.newAffairList
      };
    default:
      return state;
  }
};

const toApproveReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionType.GET_TO_APPROVE:
      return {
        ...state,
        affairList: action.newAffairList
      };
    default:
      return state;
  }
};

const historyReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionType.GET_HISTORY_LOADING:
      return {
        ...state,
        loading: true
      };
    case actionType.GET_HISTORY:
      return {
        ...state,
        loading: false,
        affairList: action.newAffairList
      };
    default:
      return state;
  }
};

export { myAffairReducer, historyReducer, toApproveReducer };
