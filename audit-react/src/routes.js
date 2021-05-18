import { BrowserRouter, Route, Switch } from 'react-router-dom';
import DashboardLayout from '../src/page/layout/DashboardLayout';
import MainLayout from '../src/page/layout/MainLayout';
import ToApprove from './page/running/ToApprove';

const routes = (
	<BrowserRouter>
		<Switch>
			<Route path="/auth" component={MainLayout} />
			<Route path="/" component={DashboardLayout} />
		</Switch>
	</BrowserRouter>
);

export default routes;
