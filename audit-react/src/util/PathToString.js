function pathToString(path) {
  switch (path) {
    case 'home':
      return '主页';
    case 'create':
      return '创建审计项目';
    case 'running':
      return '进行中的项目';
    case 'history':
      return '历史记录';
    case 'user':
      return '个人中心';
    case 'my_affair':
      return '我的审批';
    case 'my_approval':
      return '我的审计';
    case 'affair':
      return '事务详情';
    case 'reimbursement':
      return '报销审计';
    case 'follow':
      return '全程跟踪审计';
    default:
      return path;
  }
}

export default pathToString;
