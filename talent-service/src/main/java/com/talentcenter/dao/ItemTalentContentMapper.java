package com.talentcenter.dao;

import com.talentcenter.entity.ItemTalentContent;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ItemTalentContentMapper extends BaseMapper<ItemTalentContent> {

    int batchDel(Map<String, Object> map);

    int delByItemConfigId(Long itemConfigId);

}