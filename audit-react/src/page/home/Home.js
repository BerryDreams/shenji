import { Button, Card, TextField } from '@material-ui/core';
import React from 'react';
import { login } from '../../redux/action/UserAction';
export default function Home(props) {
	// const [username, setUsername] = React.useState('');
	// const [password, setPassword] = React.useState('');

	// const onUsernameChange = (e) => {
	// 	setUsername(e.target.value);
	// };

	// const onPasswordChange = (e) => {
	// 	setPassword(e.target.value);
	// };

	// const login = () => {
	// 	login({ username, password }).then(({ data }) => {
	// 		if (data && data.status === '00200') {
	// 			localStorage.setItem('token', data.content);
	// 		}
	// 	});
	// };
	return (
		<Card>
			{/* <TextField value={username} onChange={onUsernameChange} />
			<TextField value={password} onChange={onPasswordChange} />
			<Button variant="contained" onClick={login}>
				login
			</Button> */}
			hello
		</Card>
	);
}
