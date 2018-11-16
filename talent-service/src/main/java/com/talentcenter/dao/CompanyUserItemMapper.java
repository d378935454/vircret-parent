package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserItem;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompanyUserItemMapper extends BaseMapper<CompanyUserItem> {

    int batchDel(Map<String, Object> map);

    int delByUserId(Long userId);

    List<CompanyUserItem> selectByInfo(Object o);

}