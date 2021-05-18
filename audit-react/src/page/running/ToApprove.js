import MTable from '../../component/MTable';
import React from 'react';
import { getToApproveAction } from '../../redux/action/AffairAction';
import { connect } from 'react-redux';

function ToApprove(props) {
  const { affairList, getAffairList } = props;

  React.useEffect(() => {
    getAffairList();
  }, []);

  return <MTable title="正在审批的事务" data={affairList} pageSize={5} />;
}

export default connect(
  (state) => {
    return {
      affairList: state.toApproveReducer.affairList
    };
  },
  (dispatch) => {
    return {
      getAffairList: () => dispatch(getToApproveAction())
    };
  }
)(ToApprove);
