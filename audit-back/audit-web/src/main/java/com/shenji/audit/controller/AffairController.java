package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.Affair;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.type.AffairType;
import com.shenji.audit.type.RespType;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 事务接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 13:20
 */
@RestController
@RequestMapping("/api")
@Api(tags = "事务接口")
public class AffairController {

    @Autowired
    private AffairService affairService;

    @PostMapping("/follow_up_audit/init")
    @ApiOperation("创建跟踪审计")
    public RespBean startFollowUpAudit(@RequestBody Map<String, String> map, @RequestHeader("token")String token) {
        String name = map.get("name");
        String remark = map.get("remark");
        Long affairId = affairService.startAffair(JwtUtil.decodeToken(token), name, remark, AffairType.KIND_FOLLOW_UP_AUDIT);
        return RespBean.build(affairId.toString(), RespType.OK);
    }

    @PostMapping("/reimbursement_audit/init")
    @ApiOperation("创建报销审计")
    public RespBean startReimbursementAudit(@RequestBody Map<String, String> map, @RequestHeader("token")String token) {
        String name = map.get("name");
        String remark = map.get("remark");
        Long affairId = affairService.startAffair(JwtUtil.decodeToken(token), name, remark, AffairType.KIND_REIMBURSEMENT_AUDIT);
        return RespBean.build(affairId.toString(), RespType.OK);
    }

    @GetMapping("/affair")
    @ApiOperation("获取所有的事务")
    public RespBean getAllAffair(@RequestHeader("token")String token) {
        return RespBean.build(affairService.getAllAffair(JwtUtil.decodeToken(token)), RespType.OK);
    }

    @GetMapping("/my_affair")
    @ApiOperation("获取我发起的事务")
    public RespBean getMyAffair(@RequestHeader("token")String token) {
        return RespBean.build(affairService.getMyAffair(JwtUtil.decodeToken(token)), RespType.OK);
    }

    @GetMapping("/to_approval")
    @ApiOperation("获取待我审批的事务")
    public RespBean getToApproval(@RequestHeader("token")String token) {
        return RespBean.build(affairService.toApprove(JwtUtil.decodeToken(token)), RespType.OK);
    }

    @GetMapping("/my_approval")
    @ApiOperation("获取我审批过的事务")
    public RespBean getMyApproval(@RequestHeader("token")String token) {
        return RespBean.build(affairService.getMyApproval(JwtUtil.decodeToken(token)), RespType.OK);
    }
}
