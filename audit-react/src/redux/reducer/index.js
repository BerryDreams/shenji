import { combineReducers } from 'redux';
import { userInfoReducer } from './UserReducer';
import {
  myAffairReducer,
  toApproveReducer,
  historyReducer
} from './AffairReducer';

import {affairDetailReducer} from "./AffairDetailReducer";

const appReducer = combineReducers({
  myAffairReducer,
  toApproveReducer,
  historyReducer,
  userInfoReducer,
  affairDetailReducer
});
export default appReducer;
