package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * talent_notice
 * @author 
 */
@Data
@Table(name = "talent_notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    /**
     * 公告名称
     */
    private String noticeName;
    /**
     *公告内容
     */
    private String noticeContent;

    @Transient
    private String realNoticeContent;

    /**
     * 是否强制阅读 0:否 1:是
     */
    private Boolean noticeReadeType;
    /**
     *公告分类
     */
    private Integer noticeType;
    /**
     * 状态 0:删除 1:正常
     */
    private Boolean del;

    /**
     * 添加时间
     */
    private Date createTime;


    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 添加人id
     */
    private Long createId;

    /**
     * 添加人姓名
     */
    private String createName;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 更新人姓名
     */
    private String updateName;

    /**
     * 备注
     */
    private String memo;
}