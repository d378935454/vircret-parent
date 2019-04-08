package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserItem;
import com.talentcenter.entity.Street;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface StreetMapper extends BaseMapper<Street> {
    int delStreetUser(Map<String,Object> map);

    List<Map<String,Object>>  selectStreetItems(Map<String,Object> map);

    List<Map<String,Object>> census(Map<String,Object> map);

    int checkAll(Map<String,Object> map);
}