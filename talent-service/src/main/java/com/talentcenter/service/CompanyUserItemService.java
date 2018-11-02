package com.talentcenter.service;


import com.talentcenter.entity.CompanyUserItem;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CompanyUserItemService extends BaseService<CompanyUserItem> {

    int batchDel(Map<String, Object> map);

    int delByUserId(Long userId);

}
