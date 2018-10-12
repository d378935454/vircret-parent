package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
@Table(name = "talent_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 权限(菜单)名称
     */
    private String menuName;

    private String icoStr;

    private String keyName;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0:删除 1:锁定 2:正常
     */
    private Integer status;

    /**
     * 地址
     */
    private String url;

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

    @Transient
    private List<Menu> childmenus;

    private int menuType;

    @Transient
    private int ifChecked;
}