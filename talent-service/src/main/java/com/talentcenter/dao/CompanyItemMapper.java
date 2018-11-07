package com.talentcenter.dao;

import com.talentcenter.entity.CompanyItem;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CompanyItemMapper extends BaseMapper<CompanyItem> {

    int batchDel(Map<String, Object> map);
    int delByCompanyId(Long companyId);

}