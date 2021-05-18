import { string } from 'prop-types';
import axios from 'axios';
import { _getUserInfo } from './ActionCreater';

/**
 * 登录接口
 * @param  props
 * @returns
 */
function login(props) {
	const { username, password } = props;

	return axios.post('/api/login', {
		username,
		password
	});
}

login.prototype = {
	username: string.isRequired,
	password: string.isRequired
};

/**
 * 获取用户信息
 * @returns
 */
const getUserAction = () => {
	return (dispatch, getState) => {
		axios.get('/api/user').then(({ data }) => {
			dispatch(_getUserInfo(data.content));
		});
	};
};

export { login, getUserAction };
