package com.talentcenter.dao;

import com.talentcenter.entity.ItemUserTime;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface ItemUserTimeMapper extends BaseMapper<ItemUserTime> {
    int updateItemUserTime(ItemUserTime itemUserTime);

}