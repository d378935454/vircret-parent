package com.talentcenter.service;

import com.talentcenter.entity.Demo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DemoService {

    public List<Demo> demos();
}
