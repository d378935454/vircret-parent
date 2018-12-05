package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * talent_info_change
 * @author 
 */
@Data
@Table(name = "talent_info_change")
public class InfoChange implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long changeId;

    private String filedName;

    private Long userId;
}