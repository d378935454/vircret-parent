package com.talentcenter.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CommonMapper {
    int checkUnique(Map<String, Object> map);
}
