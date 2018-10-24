package com.talentcenter.service;


import com.talentcenter.entity.Company;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CompanyService extends BaseService<Company> {

    int batchDel(Map<String, Object> map);

}
