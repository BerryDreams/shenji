import MTable from '../../component/MTable';
import React from 'react';
import { getMyAffairAction } from '../../redux/action/AffairAction';
import { connect } from 'react-redux';

function MyAffair(props) {
  const { loading, affairList, getAffairList } = props;

  React.useEffect(() => {
    console.log(affairList);
    getAffairList();
  }, []);

  return <MTable title="正在审计的事务" data={affairList} pageSize={5} />;
}

export default connect(
  (state) => {
    return {
      loading: state.myAffairReducer.loading,
      affairList: state.myAffairReducer.affairList
    };
  },
  (dispatch) => {
    return {
      getAffairList: () => dispatch(getMyAffairAction())
    };
  }
)(MyAffair);
