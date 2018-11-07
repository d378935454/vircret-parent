package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * talent_company
 * @author 
 */
@Data
@Table(name = "talent_company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String companyCode;

    /**
     * 联系人
     */
     private String companyContactor;
    /**
     * 联系人电话
     */
    private String companyContactorPhone;

    /**
     * 企业性质
     */
    private Long companyNatureId;
    /**
     * 企业性质名称
     */
    private String companyNatureName;

    /**
     *企业类型
     */
    private Long companyTypeId;
    /**
     * 企业类型名称
     */
    private String companyTypeName;
    /**
     * 所属街道ID
     */
    private Long streetId;
    /**
     *所属街道名
     */
    private String streetName;
    /**
     * 企业详细地址
     */
    private String companyAddress;
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

    @Transient
    private Boolean checked;
}