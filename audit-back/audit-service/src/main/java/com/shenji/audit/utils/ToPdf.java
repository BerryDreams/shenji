package com.shenji.audit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/29 12:35
 */
@Slf4j
@Component
public class ToPdf {

    public Boolean genReport(String path) {

        String minioPath = "/root/minio-data/audit-report/";
        String[] cmd = {"/etc/init.d/soffice.sh", minioPath + path};
        try {
            String commands ="pgrep soffice";
            Process process = Runtime.getRuntime().exec(commands);
            int code = process.waitFor();
            log.info("路径为：" + minioPath + path);

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if(in.readLine() == null){
                process = Runtime.getRuntime().exec(cmd);
                code = process.waitFor();
                log.info("soffice script started");
            } else {
                log.info("soffice script is already running");
            }
            in.close();
            return true;
        } catch (Exception e) {
            log.error("pdf生成命令执行失败");
            return false;
        }

    }
}
