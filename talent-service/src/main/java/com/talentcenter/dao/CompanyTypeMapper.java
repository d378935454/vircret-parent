package com.talentcenter.dao;

import com.talentcenter.entity.CompanyType;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CompanyTypeMapper extends BaseMapper<CompanyType> {

    int batchDel(Map<String, Object> map);

}