package com.talentcenter.dao;

import com.talentcenter.entity.ItemCertificate;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ItemCertificateMapper继承基类
 */
@Mapper
public interface ItemCertificateMapper extends BaseMapper<ItemCertificate> {
    int delByItemConfigId(Long itemConfigId);

    List<ItemCertificate> selectByConfigId(Long configId);
}