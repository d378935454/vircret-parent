package com.talentcenter.service;


import com.talentcenter.entity.Company;
import com.talentcenter.entity.CompanyUserItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CenterService extends BaseService<CompanyUserItem> {

    List<Map<String,Object>> selectCenterItemCheckedItem(Map<String, Object> map);
}
