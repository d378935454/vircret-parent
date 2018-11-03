package com.talentcenter.dao;

import com.talentcenter.entity.CompanyNature;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CompanyNatureMapper extends BaseMapper<CompanyNature> {

    int batchDel(Map<String, Object> map);

}