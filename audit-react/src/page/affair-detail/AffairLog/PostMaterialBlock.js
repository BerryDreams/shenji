import FileUpload from '../../../component/FileUpload';
import { Button, Container, TextField } from '@material-ui/core';
import {
	createReimAffairAction,
	postMaterial
} from '../../../redux/action/AffairAction';
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
export default function PostMaterialBlock(props) {
	const classes = useStyles();
	const { affairId } = props;
	const [name, setName] = React.useState('');
	const [remark, setRemark] = React.useState('');
	const [fileList, setFileList] = React.useState([]);

	const onNameChange = (e) => {
		setName(e.target.value);
	};

	const onRemarkChange = (e) => {
		setRemark(e.target.value);
	};
	const onFileListChange = (e) => {
		setFileList(e);
	};
	return (
		<div className={classes.modalPaper}>
			<span className={classes.formItem}>名称</span>
			<TextField
				value={name}
				className={classes.formItem}
				onChange={onNameChange}
				variant="outlined"
			/>
			<span className={classes.formItem}>详情</span>
			<TextField
				rows={5}
				multiline
				className={classes.formItem}
				value={remark}
				variant="outlined"
				onChange={onRemarkChange}
			/>
			<span className={classes.formItem}>上传文件</span>
			<FileUpload className={classes.formItem} onChange={onFileListChange} />
			<Button
				variant="contained"
				color="secondary"
				onClick={() => postMaterial(affairId, name, remark, fileList)}
				className={classes.button}
			>
				提交节点
			</Button>
		</div>
	);
}
