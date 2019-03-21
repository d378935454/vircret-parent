package com.talentcenter.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * talent_itemConfig
 * @author 
 */
@Data
@Table(name = "talent_item_config")
public class ItemConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemConfigId;
    /**
     * 政策id
     */
    private Long itemId;
    /**
     *受理开始时间
     */
    @DateTimeFormat(pattern = "MM-dd")
    private Date itemConfigAcceptBegin;
    /**
     *受理结束时间
     */
    @DateTimeFormat(pattern = "MM-dd")
    private Date itemConfigAcceptEnd;

    /**
     *审核开始时间
     */
    @DateTimeFormat(pattern = "MM-dd")
    private Date itemConfigCheckBegin;
    /**
     *审核结束时间
     */
    @DateTimeFormat(pattern = "MM-dd")
    private Date itemConfigCheckEnd;

    /**
     * 生效时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date itemConfigEffect;
    /**
     *窗口办理
     */
    private Boolean itemConfigFace;

    /**
     *补助方式
     */
    private Long itemConfigType;
    /**
     * 总补助金额
     */
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal itemConfigAmountTotal;
    /**
     * 每份补助金额
     */
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal  itemConfigAmountPer;
    /**
     * 补助发放方式
     */
    private Integer itemConfigSendType;
    /**
     * 补助发放频率
     */
    private Float itemConfigSendRate;
    /**
     * 服务描述
     */
    private String itemConfigService;

    /**
     * 政策详情
     */
    private String itemConfigContent;

    /**
     * 政策详情附件
     */
    private String itemConfigConetentAttachment;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     *是否需要企业审核
     */
    private Integer itemConfigCompanyCheck;

    /**
     *是否需要街道审核
     */
    private Integer itemConfigStreetCheck;

    /**
     *是否需要平台审核
     */
    private Integer itemConfigCenterCheck;

    /**
     * 发放对象 0:个人 1:机构/企业
     */
    private Integer itemConfigTarget;

    /**
     * 是否按人才分类 0:否 1:是
     */
    private Boolean itemConfigTType;

    /**
     *人才分类类型ID
     */
    private Long typeCategoryId;

    private String itemConfigContactTime;

    private Boolean ifHouse;

    private Boolean ifCantHouse;

    private Boolean ifChild;

    private Boolean ifSave;

    private Boolean ifTax;

    /**
     * 状态 0:删除 1:正常
     */
    private Boolean del;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 添加人id
     */
    private Long createId;

    /**
     * 添加人姓名
     */
    private String createName;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 更新人姓名
     */
    private String updateName;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态 0:停止 1:启用
     */
    private Boolean itemConfigState;

    @Transient
    private String itemName;

    private String itemYear;

    private String itemNeedYear;
}