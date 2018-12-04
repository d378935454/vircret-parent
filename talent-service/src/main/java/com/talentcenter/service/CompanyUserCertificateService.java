package com.talentcenter.service;


import com.talentcenter.entity.CompanyUserCertificate;
import org.springframework.stereotype.Service;

@Service
public interface CompanyUserCertificateService extends BaseService<CompanyUserCertificate> {
    int deleteByUserId(Long userId);
}
