package com.talentcenter.dao;

import com.talentcenter.entity.ItemCertificate;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ItemCertificateMapper继承基类
 */
@Mapper
public interface ItemCertificateMapper extends BaseMapper<ItemCertificate> {
    int delByItemConfigId(Long itemConfigId);
}