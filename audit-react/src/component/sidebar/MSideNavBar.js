import { Box, IconButton, List, Typography } from '@material-ui/core';
import {
  AccountCircle,
  AssignmentTurnedIn,
  Home,
  HourglassFull,
  Menu,
  PostAdd
} from '@material-ui/icons';
import ScrollBar from 'react-perfect-scrollbar';
import { makeStyles, useTheme } from '@material-ui/styles';
import { useState } from 'react';
import propTypes from 'prop-types';
import React from 'react';
import classnames from 'classnames';
import MSideNavBarItemOnly from './MSideNavBarItemOnly';
import MSideNavBarItem from './MSideNavBarItem';

const useStyles = makeStyles((theme) => ({
  titleBoxNotShrink: {
    display: 'flex',
    padding: '0px 1rem 1rem 1.5rem',
    alignItems: 'center',
    justifyContent: 'space-between'
  },

  titleBoxShrink: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  },

  navList: {
    marginTop: '2rem'
  },

  navListNotShrink: {
    height: '100%'
  },

  title: {
    fontWeight: 'bolder',
    color: theme.palette._info,
    whiteSpace: 'nowrap',
    marginBottom: 0
  },

  navIcon: {
    width: '1.25rem !important',
    height: '1.25rem !important'
  },

  navListItemChild: {
    color: 'rgba(0, 0, 0, 0.5)',
    width: 'auto',
    display: 'flex',
    padding: '.8125rem 1rem !important',
    marginLeft: '.5rem',
    marginRight: '.5rem',
    borderRadius: '.375rem'
  }
}));

export default function MSideNavBar(props) {
  const theme = useTheme();
  const classes = useStyles();

  const { shrink, openShrink } = props;

  const [openItem, setOpenItem] = useState('');

  const expandClick = (event) => {
    const name = event.target.name;

    if (name === null || name === undefined || name === '') {
      return;
    }
    console.log('====' + name + '  ' + openItem + '====');

    if (openItem === '') {
      setOpenItem(name);
    } else if (openItem === name) {
      setOpenItem('');
    } else {
      setOpenItem(name);
    }
  };

  const menuClick = () => {
    openShrink();
  };

  let totalName = null;

  if (!shrink) {
    totalName = (
      <Typography variant="h2" className={classes.title}>
        审计管理系统
      </Typography>
    );
  }

  return (
    <ScrollBar>
      <Box
        className={classnames({
          [classes.titleBoxNotShrink]: !shrink,
          [classes.titleBoxShrink]: shrink
        })}
      >
        {totalName}
        <IconButton onClick={menuClick}>
          <Menu className={classes.navIcon} />
        </IconButton>
      </Box>
      <List
        component="ul"
        className={classnames({
          [classes.navList]: true,
          [classes.navListNotShrink]: !shrink
        })}
      >
        <MSideNavBarItemOnly
          title="主页"
          url="/"
          icon={
            <Home
              className={classes.navIcon}
              style={{ color: theme.palette.blue }}
            />
          }
          shrink={shrink}
        />

        <MSideNavBarItem
          name="create"
          expand={openItem === 'create'}
          title="创建项目"
          shrink={shrink}
          icon={
            <PostAdd
              className={classes.navIcon}
              style={{ color: theme.palette._default }}
            />
          }
          itemClick={expandClick}
          list={[
            {
              title: '报销审计',
              url: '/create/reimbursement'
            },
            {
              title: '全程跟踪审计',
              url: '/create/follow'
            }
          ]}
        />

        <MSideNavBarItem
          name="running"
          expand={openItem === 'running'}
          title="正在进行"
          shrink={shrink}
          icon={
            <HourglassFull
              className={classes.navIcon}
              style={{ color: theme.palette.red }}
            />
          }
          itemClick={expandClick}
          list={[
            {
              title: '我的审批',
              url: '/running/my_approval'
            },
            {
              title: '我的审计',
              url: '/running/my_affair'
            }
          ]}
        />

        <MSideNavBarItemOnly
          title="历史记录"
          url="/history"
          icon={
            <AssignmentTurnedIn
              className={classes.navIcon}
              style={{ color: theme.palette._success }}
            />
          }
          shrink={shrink}
        />

        <MSideNavBarItemOnly
          title="个人中心"
          url="/user"
          icon={
            <AccountCircle
              className={classes.navIcon}
              style={{ color: theme.palette.purple }}
            />
          }
          shrink={shrink}
        />
      </List>
    </ScrollBar>
  );
}

MSideNavBar.propTypes = {
  shrink: propTypes.bool
};
