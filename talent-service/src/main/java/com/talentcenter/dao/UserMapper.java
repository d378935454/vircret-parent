package com.talentcenter.dao;

import com.talentcenter.entity.User;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}