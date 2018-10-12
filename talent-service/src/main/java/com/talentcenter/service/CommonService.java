package com.talentcenter.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CommonService {
    boolean checkUnique(Map<String, Object> map);
}
