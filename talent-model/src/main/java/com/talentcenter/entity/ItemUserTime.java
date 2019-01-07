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
 * talent_info_change
 * @author 
 */
@Data
@Table(name = "talent_item_user_time")
public class ItemUserTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemUserTimeId;

    private Long itemId;

    private Long itemConfigId;

    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Long companyUserItemId;

    private Integer state;

    private Integer totalMonth;
}