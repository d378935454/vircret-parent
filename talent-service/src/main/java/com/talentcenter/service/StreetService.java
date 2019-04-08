package com.talentcenter.service;

import com.talentcenter.entity.CompanyUserItem;
import com.talentcenter.entity.Street;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StreetService extends BaseService<Street> {

    int batchDel(Map<String, Object> map);

    int delStreetUser(Map<String,Object> map);

    List<Map<String,Object>> selectStreetItems(Map<String,Object> map);

    List<Map<String,Object>> census(Map<String,Object> map);

    int checkAll(Map<String,Object> map);

}
