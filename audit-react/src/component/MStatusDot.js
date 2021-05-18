import { Box, makeStyles } from '@material-ui/core';
import { useTheme } from '@material-ui/styles';
import propTypes from 'prop-types';
import React from 'react';

const useStyles = makeStyles((theme) => ({
  statusDot: {
    verticalAlign: 'middle',
    width: '0.375rem',
    height: '0.375rem',
    display: 'inline-block',
    marginRight: 10,
    borderRadius: '50%'
  }
}));

function getColor(name) {
  switch (name) {
    case '审批结束':
      return '_success';
    case '审批进行中':
      return '_warning';
    case '资料上传中':
      return 'indigo';
    case '审批失败':
      return '_danger';
    default:
      return '_primary';
  }
}

function MStatusDot(props) {
  const { color, name } = props;

  const { statusDot } = useStyles();
  const theme = useTheme();

  return (
    <Box
      m={0}
      style={{
        paddingTop: '0.35rem',
        paddingBottom: '0.35rem'
      }}
    >
      <i
        className={statusDot}
        style={{ backgroundColor: theme.palette[getColor(props.name)] }}
      />
      {props.name}
    </Box>
  );
}

MStatusDot.propTypes = {
  color: propTypes.oneOf([]),
  name: propTypes.string
};

export default MStatusDot;
