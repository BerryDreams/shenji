package com.shenji.audit.utils;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.FileData;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
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

    /**
     * 获取文件
     * @param filepath 文件路径
     * @return 文件流
     * @throws CustomException 标准异常
     */
    public static InputStream getFile(String filepath) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }

        InputStream is = null;

        try {
            is = minioClient.getObject(bucketName, filepath);
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
                fileList.add(name.substring(name.lastIndexOf("/") + 1));
            }
        } catch (Exception e) {
            throw new CustomException("上传文件错误");
        }
        return fileList;
    }


    /**
     * 上传文件
     * @param is 文件数据流
     * @param filepath 文件在桶中的路径
     */
    public static void uploadFile(InputStream is, String filepath, String filename) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }
        try {
            minioClient.putObject(bucketName, filepath, is, is.available(), getContextType(filename));
        } catch (Exception e) {
            throw new CustomException("上传文件错误");
        }
    }

    /**
     * 删除文件
     * @param fileNameList 文件名列表
     */
    public static void delFile(List<String> fileNameList) throws CustomException {
        if(minioClient == null) {
            getMinioClient();
        }
        try {
            minioClient.removeObject(bucketName, fileNameList);
        } catch (Exception e) {
            throw new CustomException("删除文件错误");
        }
    }

    private static void getMinioClient() {
        try {
            minioClient = new MinioClient("http://localhost:8080", "miniolwtczg", "miniolwtczg");
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
        if(type.equals("jpeg")||type.equals("png")||type.equals("gif")||type.equals("jpg")){
            return "image/jpeg";
        }else {
            return "application/octet-stream";
        }
    }

}
