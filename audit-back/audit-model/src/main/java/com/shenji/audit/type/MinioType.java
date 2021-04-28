package com.shenji.audit.type;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/27 10:35
 */
public class MinioType {

    public static final String MATERIAL = "material";
    public static final String SIGNATURE = "signature";
    public static final String REPORT = "report";
    public static final String PDF = "pdf";
    public static final String TEMPLATE = "template";

    public static String sourcePrefix(Long affairId) {
        return affairId.toString() + "/" + REPORT + "/";
    }

    public static String pdfPrefix(Long affairId) {
        return sourcePrefix(affairId) + PDF + "/";
    }

    public static String materialPrefix(Long affairId, Long materialId) {
        return affairId.toString() + "/" + MATERIAL + "/" + materialId.toString() + "/";
    }

}
