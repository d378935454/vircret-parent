package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * talent_company_item
 * @author 
 */
@Data
@Table(name = "talent_company_item")
public class CompanyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyItemId;
    /**
     * 政策ID
     */
    private Long itemId;

    /**
     * 政策名称
     */
    private String itemName;


    private Long companyId;


}