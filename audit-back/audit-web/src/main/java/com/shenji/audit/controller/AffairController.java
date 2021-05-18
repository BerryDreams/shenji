package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.service.MaterialService;
import com.shenji.audit.common.AffairType;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private MaterialService materialService;

    @PostMapping("/follow_up_audit/init")
    @ApiOperation("创建跟踪审计")
    public RespBean startFollowUpAudit(@RequestParam("name") String name,
                                       @RequestParam("remark") String remark,
                                       @RequestHeader("token")String token) {
        Long affairId = affairService.startAffair(JwtUtil.decodeToken(token), name, remark, AffairType.KIND_FOLLOW_UP_AUDIT);
        return RespBean.ok(affairId.toString());
    }

    @PostMapping("/reimbursement_audit/init")
    @ApiOperation("创建报销审计")
    public RespBean startReimbursementAudit(@RequestParam("name") String name,
                                            @RequestParam("remark") String remark,
                                            @RequestParam("files") MultipartFile[] files,
                                            @RequestHeader("token")String token) {
        Long userId = JwtUtil.decodeToken(token);
        Long affairId = affairService.startAffair(userId, name, remark, AffairType.KIND_REIMBURSEMENT_AUDIT);
        affairService.postSource(userId, affairId, files);
        return RespBean.ok(affairId.toString());
    }

    @GetMapping("/affair/{id}")
    @ApiOperation("获取某个事务")
    public RespBean getAffair(@PathVariable("id")Long id,
                              @RequestHeader("token") String token) {
        return RespBean.ok(affairService.getAffair(JwtUtil.decodeToken(token), id));
    }

    @GetMapping("/history")
    @ApiOperation("获取历史记录")
    public RespBean getHistory(@RequestHeader("token")String token) {
        return RespBean.ok(affairService.getHistory(JwtUtil.decodeToken(token)));
    }

    @GetMapping("/affair")
    @ApiOperation("获取所有的事务")
    public RespBean getAllAffair(@RequestHeader("token")String token) {
        return RespBean.ok(affairService.getAllAffair(JwtUtil.decodeToken(token)));
    }

    @GetMapping("/my_affair")
    @ApiOperation("获取我发起的事务")
    public RespBean getMyAffair(@RequestHeader("token")String token) {
        return RespBean.ok(affairService.getMyAffair(JwtUtil.decodeToken(token)));
    }

    @GetMapping("/to_approve")
    @ApiOperation("获取待我审批的事务")
    public RespBean getToApprove(@RequestHeader("token")String token) {
        return RespBean.ok(affairService.getToApprove(JwtUtil.decodeToken(token)));
    }

    @GetMapping("/my_approval")
    @ApiOperation("获取我审批过的事务")
    public RespBean getMyApproval(@RequestHeader("token")String token) {
        return RespBean.ok(affairService.getMyApproval(JwtUtil.decodeToken(token)));
    }
}
