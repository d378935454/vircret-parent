package com.talentcenter.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * talent_company_user_family
 * @author 
 */
@Data
@Table(name = "talent_company_user_family")
public class CompanyUserFamily implements Serializable {

    private Long companyUserFamilyId;

    private Long userId;

    /**
     * 家庭类型
     */
    private String companyUserFamilyType;
    /**
     * 家庭成员姓名
     */

    private String companyUserFamilyName;
    /**
     * 成员性别
     */

    private Boolean companyUserFamilySex;

    /**
     * 成员身份证号码
     */

    private String companyUserFamilyCard;


}