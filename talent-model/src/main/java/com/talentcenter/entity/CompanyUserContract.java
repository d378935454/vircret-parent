package com.talentcenter.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * talent_company_user_contract
 * @author 
 */
@Data
@Table(name = "talent_company_user_contract")
public class CompanyUserContract implements Serializable {

    private Long companyUserContractId;

    private Long userId;

    /**
     * 合同名称
     */
    private String companyUserContractName;
    /**
     * 合同开始时间
     */

    @DateTimeFormat(pattern = "y-MM-dd")
    private Date companyUserContractTimeBegin;
    /**
     * 合同结束时间
     */
    @DateTimeFormat(pattern = "y-MM-dd")
    private Date companyUserContractTimeEnd;


}