package com.talentcenter.service.impl;

import com.talentcenter.dao.CommonMapper;
import com.talentcenter.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("CommonService")
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public boolean checkUnique(Map<String, Object> map) {
        return commonMapper.checkUnique(map) <= 0;
    }
}
