package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * talent_item_team_content
 * @author 
 */
@Data
@Table(name="talent_item_team_content")
public class ItemTeamContent{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemTeamContentId;

    private Long itemTeamId;

    private Long itemId;

    private String itemName;
}