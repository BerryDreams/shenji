import { Box, ListItem } from '@material-ui/core';
import propTypes from 'prop-types';
import React from 'react';
import { makeStyles } from '@material-ui/styles';
import { useHistory } from 'react-router';

const useStyles = makeStyles((theme) => ({
  navListItemFather: {
    color: 'rgba(0, 0, 0, 0.5)',
    width: 'auto',
    display: 'flex',
    padding: '.8125rem 1rem !important',
    fontSize: '.9rem',
    fontWeight: 600,
    fontFamily: 'MSYaHei',
    marginLeft: '.5rem',
    marginRight: '.5rem',
    borderRadius: '.375rem'
  },

  navListItemFatherIconBox: {
    display: 'flex',
    minWidth: '2.25rem',
    alignItems: 'center'
  }
}));

export default function MSideNavBarItemOnly(props) {
  const classes = useStyles();
  const { title, url, icon, shrink } = props;
  const history = useHistory();

  let name = null;
  if (!shrink) {
    name = title;
  }

  const goto = () => {
    history.push(url);
  };

  return (
    <ListItem
      button
      component="a"
      onClick={goto}
      className={classes.navListItemFather}
    >
      <Box className={classes.navListItemFatherIconBox}>{icon}</Box>
      {name}
    </ListItem>
  );
}
MSideNavBarItemOnly.propTypes = {
  shrink: propTypes.bool,
  title: propTypes.string,
  url: propTypes.string,
  icon: propTypes.node
};
