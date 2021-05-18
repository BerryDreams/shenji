package com.shenji.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文件信息
 *
 * @author misxr
 * @version 1.0
 * @date 2021/5/18 9:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileLog {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private Date uploadTime;

    private String type;

    @JsonIgnore
    private String bucketName;

    @JsonIgnore
    private String prefix;

    private String filename;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long size;

    @JsonIgnore
    @JsonSerialize(using=ToStringSerializer.class)
    private Long fromId;


}
