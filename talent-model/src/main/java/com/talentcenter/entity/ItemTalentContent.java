package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * talent_item_talent_content
 * @author 
 */
@Data
@Table(name="talent_item_talent_content")
public class ItemTalentContent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemTalentId;

    /**
     * 政策配置ID
     */
    private Long itemConfigId;

    /**
     * 人才分类名称
     */
    private String talentTypeName;

    /**
     * 人才分类ID
     */
    private Long talentTypeId;

    /**
     * 补助内容(目前只有钱和半分比)
     */
    private String itemTalentContent;

    private Boolean payType;
}