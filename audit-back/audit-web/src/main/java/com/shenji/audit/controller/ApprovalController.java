package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.util.IpUtil;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 审批接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/28 12:00
 */
@RestController
@RequestMapping("/api")
@Api(tags = "审批接口")
public class ApprovalController {

    @Resource
    private AffairService affairService;

    @PostMapping("/report/source")
    @ApiOperation("上传报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "待上传文件", paramType = "formData", allowMultiple = true, required = true, dataType = "file")
    })
    public RespBean postReport(@RequestParam("affair_id") Long affairId,
                               @RequestParam("files") MultipartFile[] files,
                               @RequestHeader("token")String token) {
        affairService.postSource(JwtUtil.decodeToken(token), affairId, files);
        return RespBean.ok();
    }


    @GetMapping("/report/source/{affair_id}")
    @ApiOperation("获取源文件列表")
    public RespBean getSourceReportList(@PathVariable("affair_id") Long affairId,
                                        @RequestHeader("token")String token) {
        return RespBean.ok(affairService.getSourceList(JwtUtil.decodeToken(token), affairId));
    }


    @GetMapping("/approval/{affair_id}")
    @ApiOperation("获取审批记录")
    public RespBean getApproval(@PathVariable("affair_id")Long affairId,
                                @RequestHeader("token")String token) {
        return RespBean.ok(affairService.getApproval(JwtUtil.decodeToken(token), affairId));
    }

    @GetMapping("/pdf/{affair_id}")
    @ApiOperation("获取报告结果文件信息")
    public RespBean getPdf(@PathVariable("affair_id")Long affairId,
                                @RequestHeader("token")String token) {
        return RespBean.ok(affairService.getPdf(JwtUtil.decodeToken(token), affairId));
    }

    @PostMapping("/approve")
    @ApiOperation("审批事务")
    public RespBean approve(@RequestParam("affair_id")Long affairId,
                            @RequestParam("is_pass")Boolean isPass,
                            @RequestParam("msg")String msg,
                            @RequestParam(value = "files", required = false)MultipartFile[] files,
                            HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        String token = request.getHeader("token");
        affairService.approve(JwtUtil.decodeToken(token), affairId, isPass, msg, ip, files);
        return RespBean.ok();
    }


}
