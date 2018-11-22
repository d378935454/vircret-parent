package com.talentcenter.service;


import com.talentcenter.entity.ItemTeam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ItemTeamService extends BaseService<ItemTeam> {
    int batchDel(Map<String, Object> map);
}
