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
    public static final String PDF_REPORT = "pdf_report";
    public static final String SOURCE_REPORT = "source_report";
    public static final String TEMPLATE = "template";

    public static String sourcePrefix(Long affairId) {
        return affairId.toString() + "/" + SOURCE_REPORT + "/";
    }

    public static String pdfPrefix(Long affairId) {
        return affairId.toString() + "/" + PDF_REPORT + "/";
    }

    public static String materialPrefix(Long affairId, Long materialId) {
        return affairId.toString() + "/" + MATERIAL + "/" + materialId.toString() + "/";
    }

}
