function createData(title, author, statusA, time, remark) {
    return {title, author, status: {name: statusA.name, color: statusA.color}, time, remark};
}

const formData =  [
    createData("大门修饰项目", "李文唐", {name: "审批中", color: "blue"}, "2021-4-25", "这次项目的问题很大，可以分期"),
    createData("大门修饰项目", "李文唐", {name: "审批失败", color: "red"}, "2021-4-25", "这次项目的问题很大，可以分期"),
    createData("大门修饰项目", "李文唐", {name: "审批成功", color: "green"}, "2021-4-25", "这次项目的问题很大，可以分期"),
    createData("大门修饰项目", "李文唐", {name: "提交资料", color: "pink"}, "2021-4-25", "这次项目的问题很大，可以分期"),
    createData("大门修饰项目", "李文唐", {name: "审批中", color: "blue"}, "2021-4-25", "这次项目的问题很大，可以分期"),
    createData("大门修饰项目", "李文唐", {name: "审批成功", color: "green"}, "2021-4-25", "这次项目的问题很大，可以分期"),
];

export default formData;