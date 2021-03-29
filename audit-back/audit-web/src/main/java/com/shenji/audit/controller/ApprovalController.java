package com.shenji.audit.controller;

import com.shenji.audit.common.FileData;
import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.ApprovalLog;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.service.ReportService;
import com.shenji.audit.type.RespType;
import com.shenji.audit.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
@Api(tags = "审批接口")
public class ApprovalController {

    @Resource
    private AffairService affairService;

    @Resource
    private ReportService reportService;

    @PostMapping("/report/source")
    @ApiOperation("上传报告")
    public RespBean postReport(@RequestParam("affair_id") Long affairId,
                               @RequestParam("files") MultipartFile[] files) {
        List<FileData> fileDataList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                FileData fileData = new FileData();
                fileData.setName(file.getOriginalFilename());
                fileData.setData(file.getBytes());
                fileDataList.add(fileData);
            }
        }catch (Exception e) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        reportService.postSource(1L, affairId, fileDataList);
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/report/source/{affair_id}")
    @ApiOperation("获取源文件列表")
    public RespBean getSourceReportList(@PathVariable("affair_id") Long affairId) {
        return RespBean.build(reportService.getSourceList(1L, affairId), RespType.OK);
    }

    @GetMapping("/report/source/{affair_id}/{filename}")
    @ApiOperation("获取源文件")
    public ResponseEntity<byte[]> getSourceReportFile(@PathVariable("affair_id") Long affairId,
                                                      @PathVariable("filename") String filename) {
        FileData fileData = reportService.getSource(1L, affairId, filename);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.add("Content-Disposition", "attachment; filename=" + new String(fileData.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        return ResponseEntity.ok().headers(httpHeaders).body(fileData.getData());
    }



    @GetMapping("/report/pdf/{affair_id}")
    @ApiOperation("获取pdf文件")
    public ResponseEntity<byte[]> getPdfReport(@PathVariable("affair_id")Long affairId) {
        FileData fileData = reportService.getPDF(1L, affairId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.add("Content-Disposition",
                "attachment; filename=" +
                        new String(fileData.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        return ResponseEntity.ok().headers(httpHeaders).body(fileData.getData());
    }



    @PostMapping("/approve")
    @ApiOperation("审批事务")
    public RespBean approve(@RequestParam("affair_id")Long affairId,
                            @RequestParam("is_pass")Boolean isPass,
                            @RequestParam("msg")String msg,
                            @RequestParam("files")MultipartFile[] files,
                            HttpServletRequest request) {
        List<FileData> fileDataList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                FileData fileData = new FileData();
                fileData.setName(file.getOriginalFilename());
                fileData.setData(file.getBytes());
                fileDataList.add(fileData);
            }
        }catch (Exception e) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        String ip = IpUtil.getIpAddr(request);
        affairService.approve(1L, affairId, isPass, msg, ip, fileDataList);
        return RespBean.build(RespType.OK);
    }


}
