package com.talentcenter.service.impl;

import com.talentcenter.dao.CenterMapper;
import com.talentcenter.entity.CompanyUserItem;
import com.talentcenter.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CenterService")
public class CenterServiceImpl implements CenterService {
    @Autowired
    private CenterMapper centerMapper;

    @Override
    public List<CompanyUserItem> selectAll() {
        return centerMapper.selectAll();
    }

    @Override
    public List<CompanyUserItem> select(CompanyUserItem center) {
        return centerMapper.select(center);
    }

    @Override
    public List<CompanyUserItem> selectByInfo(Object o) {
       return centerMapper.selectByExample(o);
//        return centerMapper.selectByInfo(o);
    }

    @Override
    public CompanyUserItem selectByPrimaryKey(Long id) {
        return centerMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyUserItem selectOne(CompanyUserItem center) {
        return centerMapper.selectOne(center);
    }

    @Override
    public int insert(CompanyUserItem center) {
        return centerMapper.insert(center);
    }

    @Override
    public int insertSelective(CompanyUserItem center) {
        return centerMapper.insertSelective(center);
    }

    @Override
    public int insertList(List<CompanyUserItem> centers) {
        return centerMapper.insertList(centers);
    }

    @Override
    public int updateByPrimaryKey(CompanyUserItem center) {
        return centerMapper.updateByPrimaryKey(center);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyUserItem center) {
        return centerMapper.updateByPrimaryKeySelective(center);
    }

    @Override
    public int selectCount(CompanyUserItem center) {
        return centerMapper.selectCount(center);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyUserItem> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectCenterItemCheckedItem(Map<String, Object> map) {
        return centerMapper.selectCenterItemCheckedItem(map);
    }

    @Override
    public int checkAll(Map<String, Object> map) {
        return centerMapper.checkAll(map);
    }
}
