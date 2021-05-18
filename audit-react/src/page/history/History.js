import MTable from '../../component/MTable';
import React from 'react';
import { getHistoryAction } from '../../redux/action/AffairAction';
import { connect } from 'react-redux';

function History(props) {
  const { loading, affairList, getAffairList } = props;

  React.useEffect(() => {
    getAffairList();
  }, []);

  return (
    <MTable
      title="结束的事务"
      loading={loading}
      data={affairList}
      pageSize={5}
    />
  );
}

export default connect(
  (state) => {
    return {
      loading: state.historyReducer.loading,
      affairList: state.historyReducer.affairList
    };
  },
  (dispatch) => {
    return {
      getAffairList: () => dispatch(getHistoryAction())
    };
  }
)(History);
