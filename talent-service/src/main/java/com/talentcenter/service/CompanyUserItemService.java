package com.talentcenter.service;


import com.talentcenter.entity.CompanyUserItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyUserItemService extends BaseService<CompanyUserItem> {

    int batchDel(Map<String, Object> map);

    int delByUserId(Long userId);

    List<CompanyUserItem> selectByUserId(Long userId);

    int updateByItemIdAndUserId(Map<String,Object> map);

    Map<String,Object> getCompanyCheckItem(Map<String,Object> map);
}
