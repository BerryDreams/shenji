package com.shenji.audit.controller;

import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.FileLog;
import com.shenji.audit.model.MaterialLog;
import com.shenji.audit.service.MaterialService;
import com.shenji.audit.utils.JwtUtil;
import com.shenji.audit.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 资料相关接口
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 13:20
 */
@RestController
@RequestMapping("/api")
@Api(tags = "资料接口")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/material")
    @ApiOperation("上传资料")
    public RespBean saveMaterial(@RequestParam("affair_id")Long affairId,
                                 @RequestParam("name")String name,
                                 @RequestParam("remark")String remark,
                                 @RequestParam("files") MultipartFile[] files,
                                 @RequestHeader("token")String token) {

        if(files == null || files.length <= 0) {
            return RespBean.error("用户输入错误");
        }

        materialService.uploadMaterial(JwtUtil.decodeToken(token), affairId, name, remark, files);
        return RespBean.ok();
    }

    @GetMapping("/material/{affair_id}")
    @ApiOperation("获取资料列表")
    public RespBean getMaterialList(@PathVariable("affair_id") Long id,
                                    @RequestHeader("token")String token) {
        List<MaterialLog> list = materialService.getMaterialList(JwtUtil.decodeToken(token), id);
        return RespBean.ok(list);
    }

    @GetMapping("/material/source/{material_id}")
    @ApiOperation("获取资料文件列表")
    public RespBean getMaterialFileList(@PathVariable("material_id") Long id,
                                        @RequestHeader("token")String token) {
        List<FileLog> list = materialService.getMaterialFolder(JwtUtil.decodeToken(token), id);
        return RespBean.ok(list);
    }

//    @GetMapping("/material/source/{id}/{filename}")
//    @ApiOperation("获取资料文件")
//    public ResponseEntity<byte[]> getMaterialFile(@PathVariable("id")Long id,
//                                                  @PathVariable("filename") String filename,
//                                                  @RequestHeader("token")String token) throws IOException {
//        byte[] fileData = materialService.getMaterial(JwtUtil.decodeToken(token), id, filename);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        httpHeaders.add("Content-Disposition",
//                "attachment; filename=" +
//                        new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
//        httpHeaders.set("Content-Type", minioUtil.getContextType(filename));
//        return new ResponseEntity<>(fileData, httpHeaders, HttpStatus.OK);
//    }
}
