import { Box, Drawer } from '@material-ui/core';
import MSideNavBar from '../../component/sidebar/MSideNavBar';
import { makeStyles } from '@material-ui/styles';
import { Route, Switch } from 'react-router-dom';
import { useState } from 'react';
import DashboardMain from './DashboardMain';
import DashboardDetail from './DashboardDetail';
import { connect } from 'react-redux';
import { getUserAction } from '../../redux/action/UserAction';
import React from 'react';
const useStyles = makeStyles((theme) => ({
	shrink: {
		width: 66,
		overflowX: 'hidden',

		transition: 'width .25s linear 100ms',
		'-moz-transition': 'width .25s linear 100ms' /* Firefox 4 */,
		'-webkit-transition': 'width .25s linear 100ms' /* Safari 和 Chrome */,
		'-o-transition': 'width .25s linear 100ms' /* Opera */
	}
}));

function DashboardLayout(props) {
	const classes = useStyles();
	const [shrink, setShrink] = useState(false);
	const { getUserInfo } = props;

	const openShrink = () => {
		setShrink(!shrink);
	};

	React.useEffect(() => {
		getUserInfo();
	}, []);

	return (
		<Box style={{ display: 'flex' }}>
			<div>
				<Drawer
					variant="permanent"
					anchor="left"
					classes={{
						paper: shrink ? classes.shrink : null,
						docked: shrink ? classes.shrink : null
					}}
				>
					<MSideNavBar shrink={shrink} openShrink={openShrink} />
				</Drawer>
			</div>
			<Switch>
				<Route path="/affair/:id" component={DashboardDetail} />
				<Route path="/" component={DashboardMain} />
			</Switch>
		</Box>
	);
}

export default connect(
	(state) => {
		return {};
	},
	(dispatch) => {
		return {
			getUserInfo: () => dispatch(getUserAction())
		};
	}
)(DashboardLayout);
