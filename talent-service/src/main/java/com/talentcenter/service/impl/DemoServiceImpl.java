package com.talentcenter.service.impl;

import com.talentcenter.dao.DemoMapper;
import com.talentcenter.entity.Demo;
import com.talentcenter.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DemoService")
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;

    @Override
    public List<Demo> demos() {
//        demoMapper.sele
        return demoMapper.selectAll();
    }
}
