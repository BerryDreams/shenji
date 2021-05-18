import FileUpload from '../../../component/FileUpload';
import { Button, Switch, TextField } from '@material-ui/core';
import { approve, postMaterial } from '../../../redux/action/AffairAction';
import React from 'react';
import { makeStyles } from '@material-ui/styles';
const useStyles = makeStyles((theme) => ({
	modalPaper: {
		backgroundColor: theme.palette.background.paper,
		boxShadow: theme.shadows[5],
		padding: theme.spacing(2, 4, 3),
		width: 550,
		borderRadius: 4,

		display: 'flex',
		flexDirection: 'column',
		alignItems: 'center'
	},
	formItem: {
		width: '100%',
		marginBottom: 20,
		fontWeight: 500
	}
}));
export default function ApproveBlock(props) {
	const classes = useStyles();
	const { affairId } = props;
	const [pass, setPass] = React.useState(false);
	const [msg, setMsg] = React.useState('');
	const [fileList, setFileList] = React.useState([]);

	const onPassChange = (e) => {
		setPass(e.target.checked);
	};

	const onMsgChange = (e) => {
		setMsg(e.target.value);
	};

	const onFileListChange = (e) => {
		setFileList(e);
	};
	return (
		<div className={classes.modalPaper}>
			<span className={classes.formItem}>是否通过</span>
			<Switch value={pass} onChange={onPassChange} variant="outlined" />
			<span className={classes.formItem}>建议</span>
			<TextField
				rows={5}
				multiline
				className={classes.formItem}
				value={msg}
				variant="outlined"
				onChange={onMsgChange}
			/>
			<span className={classes.formItem}>上传文件</span>
			<FileUpload className={classes.formItem} onChange={onFileListChange} />
			<Button
				variant="contained"
				color="secondary"
				onClick={() => approve(affairId, pass, msg, fileList)}
				className={classes.button}
			>
				审批
			</Button>
		</div>
	);
}
