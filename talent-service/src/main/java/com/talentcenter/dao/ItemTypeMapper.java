package com.talentcenter.dao;

import com.talentcenter.entity.ItemType;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ItemTypeMapper extends BaseMapper<ItemType> {

    int batchDel(Map<String, Object> map);

}