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
    private Integer del;

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
     * 备注
     */
    private String memo;

    private Long roleId;

    @Transient
    private String nut;
}