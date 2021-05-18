import { Card, Grid, Paper } from '@material-ui/core';

export default function Overview(props) {
	return (
		<Grid container>
			<Grid item lg={7} md={12}>
				<Card>hello</Card>
			</Grid>
			<Grid item lg={5} md={12}>
				<Card>hello</Card>
			</Grid>
		</Grid>
	);
}
