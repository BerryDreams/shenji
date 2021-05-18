import * as actionType from '../action/ActionType';

const initialState = {
  userInfo: {
    id: '',
    username: '',
    name: '',
    post: 1
  }
};

const userInfoReducer = (state = initialState, action) => {
  switch (action.type) {
    case actionType.GET_USER_INFO:
      return {
        ...state,
        userInfo: action.newUserInfo
      };
    default:
      return state;
  }
};

export { userInfoReducer };
