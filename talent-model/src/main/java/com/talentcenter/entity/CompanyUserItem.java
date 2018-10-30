package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * talent_company_user_item
 * @author 
 */
@Data
@Table(name = "talent_company_user_item")
public class CompanyUserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyUserItemId;

    private Long userId;
    /**
     * 政策ID
     */
    private Long itemId;

    /**
     * 政策名称
     */
    private String itemName;

    /**
     *企业退回或不通过原因
     */
    private String companyReason;
    /**
     * 公司审核状态 0:不通过 1:退回 2:通过 3:已提交
     */
    private Integer companyChecked;
    /**
     * 街道退回或不通过原因
     */
    private String streetReason;
    /**
     * 街道审核状态 0:不通过 1:退回 2:通过 3:已提交
     */
    private Integer streetChecked;
    /**
     * 人才中心退回或不通过原因
     */
    private String centerReason;
    /**
     * 人才中心审核状态 0:不通过 1:退回 2:通过 3:已提交
     */
    private Integer centerChecked;

}