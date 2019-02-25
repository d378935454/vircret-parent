package com.talentcenter.service;


import com.talentcenter.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService extends BaseService<User> {
    int batchDel(Map<String, Object> map);

    Integer ifCheck(String userName);
}
