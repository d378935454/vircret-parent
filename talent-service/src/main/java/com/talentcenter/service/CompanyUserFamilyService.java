package com.talentcenter.service;

import com.talentcenter.entity.CompanyUserFamily;
import org.springframework.stereotype.Service;

@Service
public interface CompanyUserFamilyService extends BaseService<CompanyUserFamily> {

    int delByUserId(Long userId);
}
