package com.talentcenter.service;


import com.talentcenter.entity.CompanyType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CompanyTypeService extends BaseService<CompanyType> {

    int batchDel(Map<String, Object> map);

}
