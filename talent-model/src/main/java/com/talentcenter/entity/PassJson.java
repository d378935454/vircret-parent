package com.talentcenter.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * talent_item
 *
 * @author
 */
@Data
public class PassJson {

    private Long userItemId;

    private String itemTime;

    private BigDecimal amount;

    private String talentType;

    private String talentTypeContent;

    private Date start;

    private Date end;

    private Integer type;

    private String memo;

    private List<ItemJson> items;
}