package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.Affair;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.type.AffairType;
import com.shenji.audit.type.RespType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 事务接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 13:20
 */
@RestController
@RequestMapping("/api")
@Api("审计事务接口")
public class AffairController {

    @Autowired
    private AffairService affairService;

    @PostMapping("/follow_up_audit/init")
    @ApiOperation("创建跟踪审计")
    public RespBean startFollowUpAudit(@RequestBody Affair affair) {
        affairService.startAffair(1L, affair.getName(), affair.getRemark(), AffairType.KIND_FOLLOW_UP_AUDIT);
        return RespBean.build(RespType.OK);
    }

    @PostMapping("/reimbursement_audit/init")
    @ApiOperation("创建报销审计")
    public RespBean startReimbursementAudit(@RequestBody Affair affair) {
        affairService.startAffair(1L, affair.getName(), affair.getRemark(), AffairType.KIND_REIMBURSEMENT_AUDIT);
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/affair")
    @ApiOperation("获取所有的事务")
    public RespBean getAllAffair() {
        return RespBean.build(affairService.getAllAffair(1L), RespType.OK);
    }

    @GetMapping("/my_affair")
    @ApiOperation("获取我发起的事务")
    public RespBean getMyAffair() {
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/my_approval")
    @ApiOperation("获取待我审批的事务")
    public RespBean getMyApproval() {
        return RespBean.build(RespType.OK);
    }
}
