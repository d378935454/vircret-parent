package com.talentcenter.service;


import com.talentcenter.entity.Certificate;
import com.talentcenter.entity.NoticeTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface NoticeTemplateService extends BaseService<NoticeTemplate> {

    int batchDel(Map<String, Object> map);

}
