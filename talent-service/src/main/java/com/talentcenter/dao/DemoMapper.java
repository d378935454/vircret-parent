package com.talentcenter.dao;

import com.talentcenter.entity.Demo;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper extends BaseMapper<Demo>{
}
