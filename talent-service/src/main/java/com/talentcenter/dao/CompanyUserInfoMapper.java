package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserInfo;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyUserInfoMapper extends BaseMapper<CompanyUserInfo> {
    int updateByUserId(CompanyUserInfo companyUserInfo);
}