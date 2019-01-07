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

    List<CompanyUserItem> selectByUserId(Long userId);

    List<CompanyUserItem> selectSameTeamItems(Map<String,Object> map);

    int updateByItemIdAndUserId(Map<String,Object> map);

    Map<String,Object> getCompanyCheckItem(Map<String,Object> map);

    int updateUserItem(CompanyUserItem companyUserItem);

    int delByParentId(Long parentId);
}