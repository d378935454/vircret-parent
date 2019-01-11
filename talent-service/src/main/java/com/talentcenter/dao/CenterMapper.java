package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserItem;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CenterMapper extends BaseMapper<CompanyUserItem> {

    int batchDel(Map<String, Object> map);

    List<Map<String,Object>> selectCenterItemCheckedItem(Map<String, Object> map);
}