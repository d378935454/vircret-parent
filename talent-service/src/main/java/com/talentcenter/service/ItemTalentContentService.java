package com.talentcenter.service;


import com.talentcenter.entity.ItemTalentContent;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemTalentContentService extends BaseService<ItemTalentContent> {

    int batchDel(Map<String, Object> map);

    int delByItemConfigId(Long itemConfigId);

}
