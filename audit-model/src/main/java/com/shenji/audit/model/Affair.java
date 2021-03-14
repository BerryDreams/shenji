package com.shenji.audit.model;

import java.util.Date;
import java.util.List;

/**
 * 事务实体类
 *
 * @author LWT
 * @date 2021/3/14
 */

//@Data
public class Affair {
    private Integer id;                 //事务id
    private String remark;              //备注
    private List<User> promoters;       //事务发起人
    private List<User> approvers;       //事务审批人
    private List<Date> schedule;        //进度，有效期（开始时间、终止时间），每过一个里程碑记录一个时间进度？？？
//    private List<String> schedule;       或者分开
//    private List<Date> term_of_validity;
}