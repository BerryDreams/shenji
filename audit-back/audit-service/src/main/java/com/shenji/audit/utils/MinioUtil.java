package com.shenji.audit.utils;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * minio操作文件工具类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/22 18:53
 */
@Slf4j
public class MinioUtil {

    private static MinioClient minioClient;

    private static final String bucketName = "audit";

    static {
        getMinioClient();
    }

    public static FileData getFileData(String fullPath) throws CustomException {
        FileData fileData = new FileData();
        String filename = fullPath.substring(fullPath.lastIndexOf("/") + 1);
        fileData.setName(filename);
        try {
            fileData.setData(getFile(fullPath).readAllBytes());
        }catch (IOException e) {
            throw new CustomException("字节转化错误");
        }
        return fileData;
    }

    /**
     * 获取文件
     * @param fullPath 文件路径
     * @return 文件流
     * @throws CustomException 标准异常
     */
    public static InputStream getFile(String fullPath) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }

        InputStream is = null;

        try {
            is = minioClient.getObject(bucketName, fullPath);
        } catch (Exception e) {
            throw new CustomException("上传文件错误");
        }

        return is;
    }

    /**
     * 获取某个文件夹
     * @param prefix 文件前缀
     * @return
     */
    public static List<String> listFile(String prefix) {
        List<String> fileList = new ArrayList<>();
        try {
            Iterable<Result<Item>> resultList = minioClient.listObjects(bucketName, prefix);
            for (Result<Item> result : resultList) {
                Item item = result.get();
                String name = item.objectName();
                fileList.add(name);
            }
        } catch (Exception e) {
            throw new CustomException("上传文件错误");
        }
        return fileList;
    }

    public static void uploadFile(String fullPath, byte[] file) throws CustomException {
        InputStream is = new ByteArrayInputStream(file);
        uploadFile(fullPath, is);
    }


    /**
     * 上传文件
     * @param is 文件数据流
     * @param fullPath 文件在桶中的路径
     */
    public static void uploadFile(String fullPath, InputStream is) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }
        try {
            minioClient.putObject(bucketName, fullPath, is, is.available(), getContextType(fullPath));
        } catch (Exception e) {
            throw new CustomException("上传文件错误");
        }
    }

    /**
     * 删除文件
     * @param fullPath 文件路径
     */
    public static void delFile(String fullPath) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }
        try {
            minioClient.removeObject(bucketName, fullPath);
        } catch (Exception e) {
            throw new CustomException("删除文件错误");
        }
    }

    public static void delFolder(String prefix) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }
        try {
            Iterable<Result<Item>> resultList = minioClient.listObjects(bucketName, prefix);
            for (Result<Item> result : resultList) {
                Item item = result.get();
                String name = item.objectName();
                minioClient.removeObject(bucketName, item.objectName());
            }
        } catch (Exception e) {
            throw new CustomException("删除文件错误");
        }
    }

    private static void getMinioClient() {
        try {
            minioClient = new MinioClient("http://10.2.9.173:8080", "miniolwtczg", "miniolwtczg");
            log.info("创建minio成功");
        } catch (Exception e) {
            log.error("创建minioClient失败");
        }
    }

    /**
     * 获取文件类型,看是否为图片
     * @param filename 路径
     * @return 返回文件类型
     */
    public static String getContextType(String filename){
        String type = filename.substring(filename.lastIndexOf(".") + 1);
        if("jpeg".equals(type)|| "png".equals(type)|| "gif".equals(type)|| "jpg".equals(type)){
            return "image/jpeg";
        }else {
            return "application/octet-stream";
        }
    }

    /**
     * 生成文件的前缀路径
     * @param kind 类型
     * @param id 编号
     * @return 生成的字符串
     */
    private static String getPrefix(String kind, Long id) {
        return kind + "/" + id.toString() + "/";
    }


    /**
     * 生成文件的全路径
     * @param kind 类型
     * @param id 编号
     * @param filename 文件名
     * @return 生成的字符串
     */
    private static String getInnerPath(String kind, Long id, String filename) {
        return getPrefix(kind, id) + filename;
    }

}
