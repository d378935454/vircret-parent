package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyUserFamilyMapper;
import com.talentcenter.entity.CompanyUserFamily;
import com.talentcenter.service.CompanyUserFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyUserFamilyService")
public class CompanyUserFamilyServiceImpl implements CompanyUserFamilyService {
    
    @Autowired
    private CompanyUserFamilyMapper companyUserFamilyMapper;

    @Override
    public List<CompanyUserFamily> selectAll() {
        return companyUserFamilyMapper.selectAll();
    }

    @Override
    public List<CompanyUserFamily> select(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.select(companyUserFamily);
    }

    @Override
    public List<CompanyUserFamily> selectByInfo(Object o) {
      // return companyUserFamilyMapper.selectByExample(o);
      return companyUserFamilyMapper.selectByInfo(o);
    }

    @Override
    public CompanyUserFamily selectByPrimaryKey(Long id) {
        return companyUserFamilyMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyUserFamily selectOne(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.selectOne(companyUserFamily);
    }

    @Override
    public int insert(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.insert(companyUserFamily);
    }

    @Override
    public int insertSelective(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.insertSelective(companyUserFamily);
    }

    @Override
    public int insertList(List<CompanyUserFamily> companyUserFamilys) {
        return companyUserFamilyMapper.insertList(companyUserFamilys);
    }

    @Override
    public int updateByPrimaryKey(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.updateByPrimaryKey(companyUserFamily);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.updateByPrimaryKeySelective(companyUserFamily);
    }

    @Override
    public int selectCount(CompanyUserFamily companyUserFamily) {
        return companyUserFamilyMapper.selectCount(companyUserFamily);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyUserFamily> selectByInfo(Map<String, Object> map) { return companyUserFamilyMapper.selectByInfo(map); }



}
