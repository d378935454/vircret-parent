package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * talent_street
 * @author 
 */
@Data
@Table(name="talent_street")
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long streetId;

    private String streetName;

    private String streetAddress;

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