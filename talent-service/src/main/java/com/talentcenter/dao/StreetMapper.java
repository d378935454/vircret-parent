package com.talentcenter.dao;

import com.talentcenter.entity.Street;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface StreetMapper extends BaseMapper<Street> {
    int delStreetUser(Map<String,Object> map);
}