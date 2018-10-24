package com.talentcenter.service;


import com.talentcenter.entity.Notice;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface NoticeService extends BaseService<Notice> {

    int batchDel(Map<String, Object> map);

}
