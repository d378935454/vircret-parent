package com.talentcenter.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyUserFamilyId;

    private Long userId;

    /**
     * 家庭类型
     */
    private Integer companyUserFamilyType;
    /**
     * 家庭成员姓名
     */

    private String companyUserFamilyName;
    /**
     * 成员性别
     */

    private Integer companyUserFamilySex;

    /**
     * 成员身份证号码
     */

    private String companyUserFamilyCard;


}