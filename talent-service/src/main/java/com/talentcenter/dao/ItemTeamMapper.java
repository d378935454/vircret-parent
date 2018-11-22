package com.talentcenter.dao;

import com.talentcenter.entity.ItemTeam;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ItemTeamMapper extends BaseMapper<ItemTeam> {

    int batchDel(Map<String, Object> map);

}