package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.ApprovalLog;
import com.shenji.audit.type.RespType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 审批接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/28 12:00
 */
@RestController
@RequestMapping("/api")
@Api("审批接口")
public class ApprovalController {

    @PostMapping("/report/source")
    @ApiOperation("上传报告")
    public RespBean postReport(@RequestParam("affair_id") Long affairId,
                               @RequestParam("files") MultipartFile files) {
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/report/source")
    @ApiOperation("获取源文件列表")
    public RespBean getSourceReportList() {
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/report/pdf")
    @ApiOperation("获取pdf文件")
    public RespBean getPdfReport() {
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/report/source/{reportId}/{filename}")
    @ApiOperation("获取源文件")
    public ResponseEntity<byte[]> getSourceReportFile(@PathVariable("reportId") Long reportId,
                                                      @PathVariable("filename") String filename) {
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/approve")
    @ApiOperation("审批事务")
    public RespBean approve(@RequestBody ApprovalLog approvalLog) {
        return RespBean.build(RespType.OK);
    }


}
