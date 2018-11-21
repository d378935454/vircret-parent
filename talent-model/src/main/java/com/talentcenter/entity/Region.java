package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * talent_region
 * @author 
 */
@Data
@Table(name="talent_region")
public class Region {
    /**
     * 表id
     */
    private Long id;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 地区等级 分省市县区
     */
    private Integer level;

    /**
     * 父id
     */
    private Long parentId;
}