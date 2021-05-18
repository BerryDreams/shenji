package com.shenji.audit.utils;

import com.shenji.audit.common.CustomException;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;

    public InputStream getCer() throws CustomException{
        try {
            return minioClient.getObject("audit-config", "audit.pfx");
        } catch (Exception e) {
            throw new CustomException("获取证书错误");
        }
    }

    public byte[] getFileData(String bucketName, String fullPath) throws CustomException {
        try {
            return ByteUtil.readBytes(getFile(bucketName, fullPath));
        }catch (IOException e) {
            throw new CustomException("字节转化错误");
        }
    }

    /**
     * 获取文件
     * @param fullPath 文件路径
     * @return 文件流
     * @throws CustomException 标准异常
     */
    public InputStream getFile(String bucketName, String fullPath) throws CustomException {
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
    public List<String> listFile(String bucketName, String prefix) {
        List<String> fileList = new ArrayList<>();
        try {
            Iterable<Result<Item>> resultList = minioClient.listObjects(bucketName, prefix);
            for (Result<Item> result : resultList) {
                Item item = result.get();
                String name = item.objectName();
                String realName = name.substring(name.lastIndexOf("/") + 1);
                fileList.add(realName);
            }
        } catch (Exception e) {
            throw new CustomException("上传文件错误");
        }
        return fileList;
    }

    public void uploadFile(String bucketName, String fullPath, byte[] file) throws CustomException {
        InputStream is = new ByteArrayInputStream(file);
        uploadFile(bucketName, fullPath, is);
    }


    /**
     * 上传文件
     * @param is 文件数据流
     * @param fullPath 文件在桶中的路径
     */
    public void uploadFile(String bucketName, String fullPath, InputStream is) throws CustomException {
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
    public void delFile(String bucketName, String fullPath) throws CustomException {
        try {
            minioClient.removeObject(bucketName, fullPath);
        } catch (Exception e) {
            throw new CustomException("删除文件错误");
        }
    }

    public void delFolder(String bucketName, String prefix) throws CustomException {
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

    /**
     * 获取文件类型,看是否为图片
     * @param filename 路径
     * @return 返回文件类型
     */
    public String getContextType(String filename){
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
    private String getPrefix(String kind, Long id) {
        return kind + "/" + id.toString() + "/";
    }


    /**
     * 生成文件的全路径
     * @param kind 类型
     * @param id 编号
     * @param filename 文件名
     * @return 生成的字符串
     */
    private String getInnerPath(String kind, Long id, String filename) {
        return getPrefix(kind, id) + filename;
    }

}
