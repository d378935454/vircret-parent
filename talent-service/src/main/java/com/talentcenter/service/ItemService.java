package com.talentcenter.service;


import com.talentcenter.entity.Certificate;
import com.talentcenter.entity.Item;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemService extends BaseService<Item> {

    int batchDel(Map<String, Object> map);

}
