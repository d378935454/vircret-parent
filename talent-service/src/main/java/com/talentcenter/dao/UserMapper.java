package com.talentcenter.dao;

import com.talentcenter.entity.User;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    int batchDel(Map<String, Object> map);
}