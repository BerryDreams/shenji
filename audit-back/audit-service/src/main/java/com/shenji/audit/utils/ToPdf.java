package com.shenji.audit.utils;

import com.shenji.audit.common.FileData;

import java.util.List;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/29 12:35
 */
public class ToPdf {

    public static void genReport(String fullPath, List<FileData> sourceFile) {

        FileData fileData = new FileData();
        fileData.setName("生成报告(功能开发中).pdf");
        fileData.setData(new byte[12]);
    }

    private static void execCmd() {

    }
}
