import propTypes from 'prop-types';
import {
	Button,
	IconButton,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListItemText,
	makeStyles
} from '@material-ui/core';
import { Delete, Folder, GetApp } from '@material-ui/icons';
import React from 'react';
import PdfIcon from './icon/PdfIcon';
import FileQuestionIcon from './icon/FileQuestionIcon';
import WordIcon from './icon/WordIcon';
import ExcelIcon from './icon/ExcelIcon';
import PhotoIcon from './icon/PhotoIcon';
import ZipIcon from './icon/ZipIcon';
import MusicIcon from './icon/MusicIcon';
import PowerpointIcon from './icon/PowerpointIcon';
import VideoIcon from './icon/VideoIcon';
import axios from 'axios';

const useStyles = makeStyles((theme) => ({
	listItem: {
		borderBottom: '1px solid #e9ecef',
		borderRadius: 0
	},
	listItemIcon: {
		width: '1.25rem !important',
		height: '1.25rem !important',
		color: theme.palette._info
	},
	listItemButton: {
		color: theme.palette._info
	}
}));

function IconWrapper(props) {
	const { type } = props;

	let icon = <FileQuestionIcon {...props} />;
	if (type === 'pdf') {
		icon = <PdfIcon {...props} />;
	} else if (type === 'docx' || type === 'doc') {
		icon = <WordIcon {...props} />;
	} else if (type === 'xls') {
		icon = <ExcelIcon {...props} />;
	} else if (
		type === 'png' ||
		type === 'jpg' ||
		type === 'jpeg' ||
		type === 'bmp' ||
		type === 'gif'
	) {
		icon = <PhotoIcon {...props} />;
	} else if (type === 'zip' || type === 'rar') {
		icon = <ZipIcon {...props} />;
	} else if (type === 'mp3' || type === 'wav' || type === 'm4a') {
		icon = <MusicIcon {...props} />;
	} else if (
		type === 'mp4' ||
		type === 'm4v' ||
		type === '3pg' ||
		type === 'mpg' ||
		type === 'flv' ||
		type === 'avi' ||
		type === 'rmvb' ||
		type === 'wmv' ||
		type === 'mkv'
	) {
		icon = <VideoIcon {...props} />;
	} else if (type === 'ppt' || type === 'pptx') {
		icon = <PowerpointIcon {...props} />;
	}
	return <React.Fragment>{icon}</React.Fragment>;
}

export default function MFileList(props) {
	const { baseUrl, fileList } = props;

	const download = (filename) => {
		axios.get(baseUrl + filename).then((res) => {
			let url = window.URL.createObjectURL(new Blob([res.data]));
			// 生成一个a标签
			let link = document.createElement('a');
			link.style.display = 'none';
			link.href = url;
			link.download = filename;
			document.body.appendChild(link);
			link.click();
		});
	};

	const classes = useStyles();
	return (
		<List dense>
			{fileList.map((item, index) => {
				const type = item.substr(item.lastIndexOf('.') + 1);

				return (
					<ListItem key={index} component={'a'} className={classes.listItem}>
						<ListItemIcon>
							<IconWrapper
								type={type}
								color="primary"
								className={classes.listItemIcon}
							/>
						</ListItemIcon>
						<ListItemText>{item}</ListItemText>
						<ListItemSecondaryAction>
							<IconButton
								onClick={() => download(item)}
								className={classes.listItemButton}
							>
								<GetApp />
							</IconButton>
						</ListItemSecondaryAction>
					</ListItem>
				);
			})}
		</List>
	);
}
