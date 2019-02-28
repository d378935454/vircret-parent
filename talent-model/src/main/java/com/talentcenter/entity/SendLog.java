package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * talent_send_log
 * @author 
 */
@Data
@Table(name="talent_send_log")
public class SendLog{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sendId;

    private Date month;

    private Long userId;

    private Long itemId;

    private String itemName;

    private Long itemConfigId;

    private Boolean sendState;

    private BigDecimal amount;

    private Long companyUserItmeId;
}