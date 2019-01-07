package com.talentcenter.service;


import com.talentcenter.entity.ItemUserTime;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemUserTimeService extends BaseService<ItemUserTime> {

    int batchDel(Map<String, Object> map);

    int updateItemUserTime(ItemUserTime itemUserTime);

}
