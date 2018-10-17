package com.talentcenter.service.impl;

import com.talentcenter.dao.UserMapper;
import com.talentcenter.entity.User;
import com.talentcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> select(User user) {
        return userMapper.select(user);
    }

    @Override
    public List<User> selectByInfo(Object o) {
       return userMapper.selectByExample(o);
//        return userMapper.selectByInfo(o);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectOne(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int insertList(List<User> users) {
        return userMapper.insertList(users);
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int selectCount(User user) {
        return userMapper.selectCount(user);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<User> selectByInfo(Map<String, Object> map) {
        return userMapper.selectByInfo(map);
    }
}
