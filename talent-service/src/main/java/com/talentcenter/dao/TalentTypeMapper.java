package com.talentcenter.dao;

import com.talentcenter.entity.TalentType;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TalentTypeMapper extends BaseMapper<TalentType> {

    int batchDel(Map<String, Object> map);

}