package com.shenji.audit.controller;

import com.shenji.audit.model.FileLog;
import com.shenji.audit.service.FileService;
import com.shenji.audit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/5/18 19:50
 */
@RestController
@RequestMapping("/api")
@Api(tags = "文件接口")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/download/{file_log_id}")
    @ApiOperation("下载文件")
    public ResponseEntity<byte[]> getSourceReportFile(@PathVariable("file_log_id") Long fileLogId,
                                                      @RequestHeader("token")String token) {
        byte[] fileData = fileService.getFileData(fileLogId);
        FileLog fileLog = fileService.getFileLog(fileLogId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.add("Content-Disposition", "attachment; filename=" + new String(fileLog.getFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        return ResponseEntity.ok().headers(httpHeaders).body(fileData);
    }

    @GetMapping("/view/{file_log_id}")
    @ApiOperation("查看文件")
    public ResponseEntity<byte[]> viewPdfReport(@PathVariable("file_log_id")Long fileLogId) {
        FileLog fileLog = fileService.getFileLog(fileLogId);
        String filename = fileLog.getFilename();
        String type = filename.substring(filename.lastIndexOf('.') + 1);
        byte[] fileData = fileService.getFileData(fileLogId);
        HttpHeaders httpHeaders = new HttpHeaders();
        if("pdf".equals(type)) {
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        }else if("png".equals(type) || "jpg".equals(type) || "jpeg".equals(type) || "gif".equals(type)) {
            httpHeaders.setContentType(MediaType.IMAGE_PNG);
        }

        return ResponseEntity.ok().headers(httpHeaders).body(fileData);
    }


}
