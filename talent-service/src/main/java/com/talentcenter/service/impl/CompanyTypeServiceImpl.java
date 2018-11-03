package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyTypeMapper;
import com.talentcenter.entity.CompanyType;
import com.talentcenter.service.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyTypeService")
public class CompanyTypeServiceImpl implements CompanyTypeService {
    @Autowired
    private CompanyTypeMapper companyTypeMapper;

    @Override
    public List<CompanyType> selectAll() {
        return companyTypeMapper.selectAll();
    }

    @Override
    public List<CompanyType> select(CompanyType companyType) {
        return companyTypeMapper.select(companyType);
    }

    @Override
    public List<CompanyType> selectByInfo(Object o) {
       return companyTypeMapper.selectByExample(o);
//        return companyTypeMapper.selectByInfo(o);
    }

    @Override
    public CompanyType selectByPrimaryKey(Long id) {
        return companyTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyType selectOne(CompanyType companyType) {
        return companyTypeMapper.selectOne(companyType);
    }

    @Override
    public int insert(CompanyType companyType) {
        return companyTypeMapper.insert(companyType);
    }

    @Override
    public int insertSelective(CompanyType companyType) {
        return companyTypeMapper.insertSelective(companyType);
    }

    @Override
    public int insertList(List<CompanyType> companyTypes) {
        return companyTypeMapper.insertList(companyTypes);
    }

    @Override
    public int updateByPrimaryKey(CompanyType companyType) {
        return companyTypeMapper.updateByPrimaryKey(companyType);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyType companyType) {
        return companyTypeMapper.updateByPrimaryKeySelective(companyType);
    }

    @Override
    public int selectCount(CompanyType companyType) {
        return companyTypeMapper.selectCount(companyType);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyType> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return companyTypeMapper.batchDel(map);
    }
}
