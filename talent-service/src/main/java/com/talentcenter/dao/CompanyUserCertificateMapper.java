package com.talentcenter.dao;

import com.talentcenter.entity.CompanyUserCertificate;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyUserCertificateMapper extends BaseMapper<CompanyUserCertificate> {
    int deleteByUserId(Long userId);
}