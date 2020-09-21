package com.imooc.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Messages {
    @Id
    private String id;

    private String path;

    @Column(name = "cover_path")
    private String coverPath;

    @Column(name = "like_counts")
    private Long likeCounts;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    private String title;

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "source_icon")
    private String sourceIcon;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return cover_path
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     * @param coverPath
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     * @return like_counts
     */
    public Long getLikeCounts() {
        return likeCounts;
    }

    /**
     * @param likeCounts
     */
    public void setLikeCounts(Long likeCounts) {
        this.likeCounts = likeCounts;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return source_name
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * @return source_icon
     */
    public String getSourceIcon() {
        return sourceIcon;
    }

    /**
     * @param sourceIcon
     */
    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }
}