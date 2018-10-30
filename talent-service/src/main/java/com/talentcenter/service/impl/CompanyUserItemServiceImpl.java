package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyUserItemMapper;
import com.talentcenter.entity.CompanyUserItem;
import com.talentcenter.service.CompanyUserItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyUserItemService")
public class CompanyUserItemServiceImpl implements CompanyUserItemService {
    @Autowired
    private CompanyUserItemMapper companyUserItemMapper;

    @Override
    public List<CompanyUserItem> selectAll() {
        return companyUserItemMapper.selectAll();
    }

    @Override
    public List<CompanyUserItem> select(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.select(companyUserItem);
    }

    @Override
    public List<CompanyUserItem> selectByInfo(Object o) {
       return companyUserItemMapper.selectByExample(o);
//        return companyUserItemMapper.selectByInfo(o);
    }

    @Override
    public CompanyUserItem selectByPrimaryKey(Long id) {
        return companyUserItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyUserItem selectOne(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.selectOne(companyUserItem);
    }

    @Override
    public int insert(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.insert(companyUserItem);
    }

    @Override
    public int insertSelective(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.insertSelective(companyUserItem);
    }

    @Override
    public int insertList(List<CompanyUserItem> companyUserItems) {
        return companyUserItemMapper.insertList(companyUserItems);
    }

    @Override
    public int updateByPrimaryKey(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.updateByPrimaryKey(companyUserItem);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.updateByPrimaryKeySelective(companyUserItem);
    }

    @Override
    public int selectCount(CompanyUserItem companyUserItem) {
        return companyUserItemMapper.selectCount(companyUserItem);
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
    public int batchDel(Map<String, Object> map) {
        return companyUserItemMapper.batchDel(map);
    }
}
