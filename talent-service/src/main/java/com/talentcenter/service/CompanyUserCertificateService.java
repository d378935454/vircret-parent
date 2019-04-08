package com.talentcenter.service;


import com.talentcenter.entity.CompanyUserCertificate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyUserCertificateService extends BaseService<CompanyUserCertificate> {
    int deleteByUserId(Long userId);

    List<CompanyUserCertificate> selectByUserId(Map<String,Object> map);
}
