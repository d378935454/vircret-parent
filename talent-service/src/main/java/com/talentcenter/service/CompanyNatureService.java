package com.talentcenter.service;


import com.talentcenter.entity.CompanyNature;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CompanyNatureService extends BaseService<CompanyNature> {

    int batchDel(Map<String, Object> map);

}
