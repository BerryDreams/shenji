import { number } from 'prop-types';

function affairCodeToString(code) {
  switch (code) {
    case 10:
      return '资料上传中';
    case 11:
      return '审批中';
    case 12:
      return '审批失败';
    case 13:
      return '审批结束';
    case 20:
      return '报销审计';
    case 21:
      return '报销审计';
    default:
      return '转换失败';
  }
}

affairCodeToString.proptype = number.isRequired;

export { affairCodeToString };
