package com.talentcenter.service;


import com.talentcenter.entity.TalentType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TalentTypeService extends BaseService<TalentType> {

    int batchDel(Map<String, Object> map);

}
