package com.talentcenter.service.impl;

import com.talentcenter.dao.StreetMapper;
import com.talentcenter.entity.CompanyUserItem;
import com.talentcenter.entity.Street;
import com.talentcenter.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("StreetService")
public class StreetServiceImpl implements StreetService {
    @Autowired
    private StreetMapper streetMapper;

    @Override
    public List<Street> selectAll() {
        return streetMapper.selectAll();
    }

    @Override
    public List<Street> select(Street street) {
        return streetMapper.select(street);
    }

    @Override
    public List<Street> selectByInfo(Object o) {
       return streetMapper.selectByInfo(o);
    }

    @Override
    public Street selectByPrimaryKey(Long id) {
        return streetMapper.selectByPrimaryKey(id);
    }

    @Override
    public Street selectOne(Street street) {
        return streetMapper.selectOne(street);
    }

    @Override
    public int insert(Street street) {
        return streetMapper.insert(street);
    }

    @Override
    public int insertSelective(Street street) {
        return streetMapper.insertSelective(street);
    }

    @Override
    public int insertList(List<Street> streets) {
        return streetMapper.insertList(streets);
    }

    @Override
    public int updateByPrimaryKey(Street street) {
        return streetMapper.updateByPrimaryKey(street);
    }

    @Override
    public int updateByPrimaryKeySelective(Street street) {
        return streetMapper.updateByPrimaryKeySelective(street);
    }

    @Override
    public int selectCount(Street street) {
        return streetMapper.selectCount(street);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<Street> selectByInfo(Map<String, Object> map) {
        return streetMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return streetMapper.batchDel(map);
    }

    @Override
    public int delStreetUser(Map<String, Object> map) {
        return streetMapper.delStreetUser(map);
    }

    @Override
    public List<Map<String,Object>>  selectStreetItems(Map<String, Object> map) {
        return streetMapper.selectStreetItems(map);
    }
}
