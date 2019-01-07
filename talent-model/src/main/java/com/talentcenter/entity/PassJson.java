package com.talentcenter.entity;

import lombok.Data;

import java.math.BigDecimal;
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

    private Long talentType;

    private Integer type;

    private String memo;

    private List<ItemJson> items;
}