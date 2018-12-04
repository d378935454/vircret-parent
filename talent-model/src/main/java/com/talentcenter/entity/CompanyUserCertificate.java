package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * talent_company_user_certificate
 * @author 
 */
@Data
@Table(name = "talent_company_user_certificate")
public class CompanyUserCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyUserCertificateId;

    /**
     * 证书ID
     */
    private Long certificateId;

    /**
     * 证书名称
     */
    private String certificateName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 图片链接地址
     */
    private String imgUrl;
}