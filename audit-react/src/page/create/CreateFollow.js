import {
	Button,
	Card,
	CardContent,
	CardHeader,
	Container,
	Grid,
	TextField
} from '@material-ui/core';
import propTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import React from 'react';
import { createFollowAffairAction } from '../../redux/action/AffairAction';

const useStyles = makeStyles((theme) => ({
	card: {},
	cardTitle: {
		fontWeight: 600,
		fontFamily: 'MSYaHei'
	},

	form: {
		display: 'flex',
		flexDirection: 'column',
		alignItems: 'center'
	},

	formItem: {
		width: '100%',
		marginBottom: 20,
		fontWeight: 500
	},

	actionClass: {
		width: '100%',
		display: 'flex',
		justifyContent: 'flex-end'
	}
}));

export default function CreateFollow(props) {
	const { card, cardTitle, form, formItem, actionClass } = useStyles();
	const [name, setName] = React.useState('');
	const [remark, setRemark] = React.useState('');

	const create = () => {
		createFollowAffairAction(name, remark);
	};

	const onNameChange = (e) => {
		setName(e.target.value);
	};

	const onRemarkChange = (e) => {
		setRemark(e.target.value);
	};

	return (
		<Grid container justify="center">
			<Grid item lg={7} xs={12}>
				<Card className={card}>
					<CardHeader
						title="创建全程跟踪审计"
						className={cardTitle}
					></CardHeader>
					<CardContent>
						<Container className={form}>
							<span className={formItem}>名称</span>
							<TextField
								className={formItem}
								value={name}
								onChange={onNameChange}
								variant="outlined"
							/>
							<span className={formItem}>详情</span>
							<TextField
								className={formItem}
								rows={5}
								multiline
								value={remark}
								variant="outlined"
								onChange={onRemarkChange}
							/>
							<div className={actionClass}>
								<Button color="primary" onClick={create}>
									创建
								</Button>
							</div>
						</Container>
					</CardContent>
				</Card>
			</Grid>
		</Grid>
	);
}

CreateFollow.propTypes = {
	title: propTypes.string
};
