package com.talentcenter.service;


import com.talentcenter.entity.InfoChange;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface InfoChangeService extends BaseService<InfoChange> {
    int deleteByUserId(Long userId);
}
