package com.talentcenter.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date itemConfigAcceptBegin;
    /**
     *受理结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date itemConfigAcceptEnd;
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
    private Float itemConfigAmountTotal;
    /**
     * 每份补助金额
     */
    private Float itemConfigAmountPer;
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
     *是否需要企业审核
     */
    private Boolean itemConfigCompanyCheck;
    /**
     *是否需要街道审核
     */
    private Boolean itemConfigStreetCheck;
    /**
     *是否需要平台审核
     */
    private Boolean itemConfigCenterCheck;
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
}