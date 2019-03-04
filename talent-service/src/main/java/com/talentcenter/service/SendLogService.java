package com.talentcenter.service;


import com.talentcenter.entity.SendLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SendLogService extends BaseService<SendLog> {

    int updateAmount(Map<String,Object> mpa);

}
