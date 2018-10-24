package com.talentcenter.dao;

import com.talentcenter.entity.Item;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    int batchDel(Map<String, Object> map);

}