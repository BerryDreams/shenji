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
import { Delete, Folder } from '@material-ui/icons';
import React from 'react';
const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%'
  },

  fileList: {
    color: 'rgba(0, 0, 0, 0.5)',
    width: '100%',
    display: 'flex',
    justifyContent: 'flex-start',
    padding: '.8125rem 1rem !important',
    fontSize: '.9rem',
    fontWeight: 600,
    fontFamily: 'MSYaHei',
    marginLeft: '.5rem',
    marginRight: '.5rem',
    borderRadius: '.375rem',
    borderBottom: '1px solid #e9ecef'
  },

  listItemIcon: {
    minWidth: 30
  }
}));

function FileUpload(props) {
  const classes = useStyles();

  const [fileList, setFileList] = React.useState([]);

  const {onChange} = props;

  const removeFile = (filename) => {
    const newFileList = fileList.filter(item => item.name !== filename);
    onChange(newFileList);
    setFileList(newFileList);
  }

  const submit = (e) => {
    const newFile = e.target.files[0];
    let tmp = 0;
    fileList.map(file => {
      if(file.name === newFile.name) {
        tmp = 1;
      }
    });
    if(tmp) {
      return;
    }
    const newFileList = fileList.concat(e.target.files[0]);
    onChange(newFileList);
    setFileList(newFileList);
  };


  const fileElement = fileList.map((item, index) => {
    return (
      <ListItem key={index} component={'a'} className={classes.fileList}>
        <ListItemIcon classes={{root: classes.listItemIcon}}>
          <Folder color="primary"/>
        </ListItemIcon>
        <ListItemText>
        {item.name}
        </ListItemText>
        <ListItemSecondaryAction>
          <IconButton edge="end" aria-label="delete" onClick={() => removeFile(item.name)}>
            <Delete color="secondary"/>
          </IconButton>
        </ListItemSecondaryAction>
      </ListItem>
    );
  });

  return (
    <div className={classes.root}>
      <input
          id="contained-button-file"
          style={{display: 'none'}}
          multiple
          type="file"
          onChange={submit}
      />
      <label htmlFor="contained-button-file">
        <Button variant="contained" color="primary" component="span">
          Upload
        </Button>
      </label>
      <List dense>{fileElement}</List>
    </div>
  );
}

export default FileUpload;
