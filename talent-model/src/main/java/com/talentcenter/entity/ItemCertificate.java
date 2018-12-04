package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * talent_item_certificate
 * @author 
 */
@Data
@Table(name = "talent_item_certificate")
public class ItemCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemCertificateId;

    /**
     * 政策配置ID
     */
    private Long itemConfigId;

    /**
     * 证书ID
     */
    private Long certificateId;

    /**
     * 证书名称
     */
    private String certificateName;

    @Transient
    private List<CompanyUserCertificate> companyUserCertificates;
}