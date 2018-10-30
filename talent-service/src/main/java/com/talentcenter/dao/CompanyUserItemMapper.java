package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserItem;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CompanyUserItemMapper extends BaseMapper<CompanyUserItem> {

    int batchDel(Map<String, Object> map);

}