package com.talentcenter.service;


import com.talentcenter.entity.ItemConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemConfigService extends BaseService<ItemConfig> {

    int batchDel(Map<String, Object> map);

}
