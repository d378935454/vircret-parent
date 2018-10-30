package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
}