package com.shenji.audit.controller;

import com.shenji.audit.common.FileData;
import com.shenji.audit.common.RespBean;
import com.shenji.audit.model.MaterialLog;
import com.shenji.audit.service.MaterialService;
import com.shenji.audit.service.MinioService;
import com.shenji.audit.type.MinioType;
import com.shenji.audit.type.RespType;
import com.shenji.audit.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
@Api("资料相关接口")
public class MaterialController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private MaterialService materialService;

    @PostMapping("/material")
    @ApiOperation("上传资料")
    public RespBean saveMaterial(@RequestParam("id")Long id,
                                 @RequestParam("name")String name,
                                 @RequestParam("remark")String remark,
                                 @RequestParam("files") MultipartFile[] files) {

        if(files == null || files.length <= 0) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }

        List<FileData> fileDataList = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                FileData fileData = new FileData();
                fileData.setName(file.getOriginalFilename());
                fileData.setData(file.getBytes());
                fileDataList.add(fileData);
            }
        } catch (IOException e) {
            return RespBean.build(RespType.USER_INPUT_ERROR);
        }

        materialService.uploadMaterial(1L, id, name, remark, fileDataList);
        return RespBean.build(RespType.OK);
    }

    @GetMapping("/material")
    @ApiOperation("获取资料列表")
    public RespBean getMaterialList(@RequestParam("affair_id") Long id) {
        List<MaterialLog> list = materialService.getMaterialList(1L, id);
        return RespBean.build(list, RespType.OK);
    }

    @GetMapping("/material/{material_id}")
    @ApiOperation("获取资料文件列表")
    public RespBean getMaterialFileList(@PathVariable("material_id") Long id) {
        List<String> list = materialService.getMaterialFileList(1L, id);
        return RespBean.build(list, RespType.OK);
    }

    @GetMapping("/material/{id}/{filename}")
    @ApiOperation("获取资料文件")
    public ResponseEntity<byte[]> getMaterialFile(@PathVariable("id")Long id, @PathVariable("filename") String filename) {
        FileData fileData = materialService.getMaterial(1L, id, filename);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MinioUtil.getContextType(filename));
        return new ResponseEntity<>(fileData.getData(), httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/material/{id}")
    @ApiOperation("删除资料文件")
    public RespBean delMaterial(@PathVariable("id")Long id) {
        materialService.delMaterial(1L, id);
        return RespBean.build(RespType.OK);
    }
}
