package com.talentcenter.dao;

import com.talentcenter.entity.TypeCategory;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TypeCategoryMapper extends BaseMapper<TypeCategory> {

    int batchDel(Map<String, Object> map);

}