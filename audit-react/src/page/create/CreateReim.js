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
import {createFollowAffairAction, createReimAffairAction} from '../../redux/action/AffairAction';
import FileUpload from '../../component/FileUpload';

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
    display: 'flex',
    alignItems: 'center',
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

export default function CreateReim(props) {
  const { card, cardTitle, form, formItem, actionClass } = useStyles();

  const [name, setName] = React.useState('');
  const [remark, setRemark] = React.useState('');
  const [fileList, setFileList] = React.useState([]);

  const create = () => {
    createReimAffairAction(name, remark, fileList);
  };

  const onNameChange = (e) => {
    setName(e.target.value);
  };

  const onRemarkChange = (e) => {
    setRemark(e.target.value);
  };

  const onFileListChange = (e) => {
    console.log(e);
    setFileList(e);
  }

  return (
    <Grid container justify="center">
      <Grid item lg={7} xs={12}>
        <Card className={card}>
          <CardHeader title="创建报销审计" className={cardTitle}/>
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
              <span className={formItem}>报告上传</span>
              <FileUpload onChange={onFileListChange}/>
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

CreateReim.propTypes = {
  title: propTypes.string
};
