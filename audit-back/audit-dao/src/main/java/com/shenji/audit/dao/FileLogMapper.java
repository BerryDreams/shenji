package com.shenji.audit.dao;

import com.shenji.audit.model.FileLog;

import java.util.Date;
import java.util.List;

public interface FileLogMapper {

    void insertOne(FileLog fileLog);

    void delOne(Long fileLogId);

    void updateOne(Long fileLogId, String filename, Long size, Date updateTime);

    FileLog selectOne(Long fileLogId);

    List<FileLog> listItems(Long fromId, String type);
}
