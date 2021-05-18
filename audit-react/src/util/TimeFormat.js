import moment from 'moment';
export function getDate(val) {
	let d = moment(val);
	return d.format('YYYY-M-D');
}

export function getExactTime(val) {
	let d = moment(val);
	return d.format('YY年M月D日 HH:mm');
}
