package com.talentcenter.dao;

import com.talentcenter.entity.ItemConfig;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ItemConfigMapper extends BaseMapper<ItemConfig> {

    int batchDel(Map<String, Object> map);

}