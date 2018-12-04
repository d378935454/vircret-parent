package com.talentcenter.dao;

import com.talentcenter.entity.ItemTeamContent;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemTeamContentMapper extends BaseMapper<ItemTeamContent> {

    int batchDel(Map<String, Object> map);

    int delByTeamId(Long itemTeamId);

    List<ItemTeamContent> selectTeam(Long itemId);
}