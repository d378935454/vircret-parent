package com.talentcenter.service;


import com.talentcenter.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyService extends BaseService<Company> {

    int batchDel(Map<String, Object> map);

    List<Map<String,Object>> selectCompanyCheckedItem(Map<String,Object> map);

    List<Company> selectCheckCompany(String streetId);
}
