package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserCertificate;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompanyUserCertificateMapper extends BaseMapper<CompanyUserCertificate> {
    int deleteByUserId(Long userId);

    List<CompanyUserCertificate> selectByUserId(Map<String,Object> map);
}