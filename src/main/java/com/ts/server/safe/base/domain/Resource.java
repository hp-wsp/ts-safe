package com.ts.server.safe.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 *  上传资源
 *
 * @author <a href="mailto:hhywangwei@mgail.com">WangWei</a>
 */
public class Resource {
    private String id;
    private String path;
    private String fileName;
    private int fileSize;
    private String contentType;
    private String username;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return fileSize == resource.fileSize &&
                Objects.equals(id, resource.id) &&
                Objects.equals(path, resource.path) &&
                Objects.equals(fileName, resource.fileName) &&
                Objects.equals(contentType, resource.contentType) &&
                Objects.equals(username, resource.username) &&
                Objects.equals(createTime, resource.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, fileName, fileSize, contentType, username, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("path", path)
                .append("fileName", fileName)
                .append("fileSize", fileSize)
                .append("contentType", contentType)
                .append("username", username)
                .append("createTime", createTime)
                .toString();
    }
}
