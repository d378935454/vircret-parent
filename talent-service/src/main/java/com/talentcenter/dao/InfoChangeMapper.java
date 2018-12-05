package com.talentcenter.dao;

import com.talentcenter.entity.InfoChange;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoChangeMapper extends BaseMapper<InfoChange> {
    int deleteByUserId(Long userId);
}