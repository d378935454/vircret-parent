package com.talentcenter.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * talent_company_user_item
 *
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
     * 企业退回或不通过原因
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

    /**
     * 申请状态 0:未申请 1:已申请
     */
    private Boolean haveSubmit;

    private Long parentId;

    private Integer type;

    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal amount;

    private String memo;

    private Long talentType;

    private String talentTypeContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date submitTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    private Long configId;
}