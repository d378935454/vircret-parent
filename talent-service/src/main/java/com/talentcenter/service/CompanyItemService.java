package com.talentcenter.service;


import com.talentcenter.entity.CompanyItem;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CompanyItemService extends BaseService<CompanyItem> {

    int batchDel(Map<String, Object> map);

    int delByCompanyId(Long userId);

}
