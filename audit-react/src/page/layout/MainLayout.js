import React from 'react';
import { Route, Switch } from 'react-router';
import DashboardLogin01 from '../auth/Login';

function MainLayout(props) {
  return (
    <Switch>
      <Route path="/auth/login" component={DashboardLogin01}></Route>
    </Switch>
  );
}

export default MainLayout;
