package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@Data
@Table(name = "talent_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别 1:男 2:女
     */
    private Integer sex;

    /**
     * 状态 0:删除 1:锁定 2:正常
     */
    private Boolean del;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 联系电话
     */
    private String userTel;

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
     * 用户分类 0:超级管理员 1:管理员 2:窗口服务人员
     */
    private Integer userType;

    private String userCode;

    private String userJob;

    private String userEmail;

    private Long streetId;

    private Long companyId;
    /**
     * 用户性质 0:平台用户  1:企业管理员  2:街道管理员  3:普通用户
     */
    private Integer userNature;
    /**
     * 备注
     */
    private String memo;

    private Long roleId;

    @Transient
    private String nut;


}