package com.talentcenter.service;


import com.talentcenter.entity.ItemType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemTypeService extends BaseService<ItemType> {

    int batchDel(Map<String, Object> map);

}
