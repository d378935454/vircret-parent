package com.talentcenter.service;


import com.talentcenter.entity.ItemTeamContent;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemTeamContentService extends BaseService<ItemTeamContent> {
    int batchDel(Map<String, Object> map);

    int delByTeamId(Long itemTeamId);
}
