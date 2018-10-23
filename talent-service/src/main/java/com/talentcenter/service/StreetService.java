package com.talentcenter.service;

import com.talentcenter.entity.Street;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface StreetService extends BaseService<Street> {

    int batchDel(Map<String, Object> map);

    int delStreetUser(Map<String,Object> map);

}
