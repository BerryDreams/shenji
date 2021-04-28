package com.shenji.audit.controller;

import com.shenji.audit.common.FileData;
import com.shenji.audit.common.RespBean;
import com.shenji.audit.service.AffairService;
import com.shenji.audit.service.ReportService;
import com.shenji.audit.type.RespType;
import com.shenji.audit.util.IpUtil;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="query", dataType = "Long",dataTypeClass = Long.class)
    @PostMapping("/report/source")
    @ApiOperation("上传报告")
    public RespBean postReport(@RequestParam("affair_id") Long affairId,
                               @RequestParam("files") MultipartFile[] files,
                               @RequestHeader("token")String token) {
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
        reportService.postSource(JwtUtil.decodeToken(token), affairId, fileDataList);
        return RespBean.build(RespType.OK);
    }


    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="path", dataType = "Long",dataTypeClass = Long.class)
    @GetMapping("/report/source/{affair_id}")
    @ApiOperation("获取源文件列表")
    public RespBean getSourceReportList(@PathVariable("affair_id") Long affairId,
                                        @RequestHeader("token")String token) {
        return RespBean.build(reportService.getSourceList(JwtUtil.decodeToken(token), affairId), RespType.OK);
    }

    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="path", dataType = "Long",dataTypeClass = Long.class)
    @GetMapping("/report/source/{affair_id}/{filename}")
    @ApiOperation("获取源文件")
    public ResponseEntity<byte[]> getSourceReportFile(@PathVariable("affair_id") Long affairId,
                                                      @PathVariable("filename") String filename,
                                                      @RequestHeader("token")String token) {
        FileData fileData = reportService.getSource(JwtUtil.decodeToken(token), affairId, filename);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.add("Content-Disposition", "attachment; filename=" + new String(fileData.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        return ResponseEntity.ok().headers(httpHeaders).body(fileData.getData());
    }


    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="path", dataType = "Long",dataTypeClass = Long.class)
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

    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="path", dataType = "Long",dataTypeClass = Long.class)
    @GetMapping("/report/pdf/{affair_id}/view")
    @ApiOperation("查看pdf文件")
    public ResponseEntity<byte[]> viewPdfReport(@PathVariable("affair_id")Long affairId) {
        FileData fileData = reportService.getPDF(1L, affairId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(httpHeaders).body(fileData.getData());
    }

    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="path", dataType = "Long",dataTypeClass = Long.class)
    @GetMapping("/approval/{affair_id}")
    @ApiOperation("获取审批记录")
    public RespBean getApproval(@PathVariable("affair_id")Long affairId,
                                HttpServletRequest request) {
        String token = request.getHeader("token");
        return RespBean.build(affairService.getApproval(JwtUtil.decodeToken(token), affairId), RespType.OK);
    }

    @ApiImplicitParam(name = "affair_id", value = "事务ID", required = true, paramType="query", dataType = "Long",dataTypeClass = Long.class)
    @PostMapping("/approve")
    @ApiOperation("审批事务")
    public RespBean approve(@RequestParam("affair_id")Long affairId,
                            @RequestParam("is_pass")Boolean isPass,
                            @RequestParam("msg")String msg,
                            @RequestParam(value = "files", required = false)MultipartFile[] files,
                            HttpServletRequest request) {
        List<FileData> fileDataList = new ArrayList<>();
        try {
            if(files != null) {
                for (MultipartFile file : files) {
                    FileData fileData = new FileData();
                    fileData.setName(file.getOriginalFilename());
                    fileData.setData(file.getBytes());
                    fileDataList.add(fileData);
                }
            }
        }catch (Exception e) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }
        String ip = IpUtil.getIpAddr(request);
        String token = request.getHeader("token");
        affairService.approve(JwtUtil.decodeToken(token), affairId, isPass, msg, ip, fileDataList);
        return RespBean.build(RespType.OK);
    }


}
