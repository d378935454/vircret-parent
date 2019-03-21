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
 * talent_company_user_info
 * @author 
 */
@Data
@Table(name = "talent_company_user_info")
public class CompanyUserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyUserInfoId;

    private Long userId;

    /**
     * 姓名
     */
    private String companyUserName;

    /**
     * 性别 0:女 1:男
     */
    private Boolean companyUserSex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserBirthday;

    /**
     * 身份证号码
     */
    private String companyUserCard;

    /**
     * 户口所在省份
     */
    private String companyUserHomeProvince;

    /**
     * 户口所在市
     */
    private String companyUserHomeCity;

    /**
     * 户口所在区县
     */
    private String companyUserHomeDistrict;

    /**
     * 户口所在地地址
     */
    private String companyUserHomeAddress;

    /**
     * 户口所在省份ID
     */
    private Long companyUserHomeProvinceId;

    /**
     * 户口所在市ID
     */
    private Long companyUserHomeCityId;

    /**
     * 户口所在区县ID
     */
    private Long companyUserHomeDistrictId;

    /**
     * 在沪区县
     */
    private String companyUserHuDistrict;

    /**
     * 在沪地址
     */
    private String companyUserHuAddress;

    /**
     * 在沪区县ID
     */
    private Integer companyUserHuDistrictId;

    /**
     * 工作年限
     */
    private Integer companyUserWorkLength;

    /**
     * 毕业院校
     */
    private String companyUserSchool;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserWorkStart;

    private Boolean companyUserMarriage;

    private Boolean companyUserNotBuyHouse;

    private Boolean companyUserHaveAccept;

    private Integer companyUserDegree;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserIitBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserIitEnd;

    private Float companyUserIit;


    /**
     * 毕业时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserGraduation;

    /**
     * 学历 0:中专 1:本科 2:研究生 3:博士 4:博士后
     */
    private Integer companyUserEducation;

    /**
     * 职称 0:初级 1:中级 2:高级
     */
    private Integer companyUserPositional;

    /**
     * 职务
     */
    private String companyUserPost;

    /**
     * 手机号码
     */
    private String companyUserMobel;

    /**
     * 固定电话
     */
    private String companyUserTel;

    /**
     * 开户行地址
     */
    private String companyUserBank;

    /**
     * 银行卡号
     */
    private String companyUserBankNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserContractTimeBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserContractTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserHouseContractTimeBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserHouseContractTimeEnd;

    private Float companyUserHousePrice;

    private Integer companyUserHouseType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserSocietySaveTime1Begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserSocietySaveTime1End;

    private Float companyUserSocietySave1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserSocietySaveTime2Begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyUserSocietySaveTime2End;

    private Float companyUserSocietySave2;

    private Integer updateTimes;

    private Float companyUserHouseArea;

    private Integer companyUserHuType;
}