package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyUserContractMapper;
import com.talentcenter.entity.CompanyUserContract;
import com.talentcenter.service.CompanyUserContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyUserContractService")
public class CompanyUserContractServiceImpl implements CompanyUserContractService {
    
    @Autowired
    private CompanyUserContractMapper companyUserContractMapper;

    @Override
    public List<CompanyUserContract> selectAll() {
        return companyUserContractMapper.selectAll();
    }

    @Override
    public List<CompanyUserContract> select(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.select(companyUserContract);
    }

    @Override
    public List<CompanyUserContract> selectByInfo(Object o) {
      // return companyUserContractMapper.selectByExample(o);
      return companyUserContractMapper.selectByInfo(o);
    }

    @Override
    public CompanyUserContract selectByPrimaryKey(Long id) {
        return companyUserContractMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyUserContract selectOne(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.selectOne(companyUserContract);
    }

    @Override
    public int insert(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.insert(companyUserContract);
    }

    @Override
    public int insertSelective(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.insertSelective(companyUserContract);
    }

    @Override
    public int insertList(List<CompanyUserContract> companyUserContracts) {
        return companyUserContractMapper.insertList(companyUserContracts);
    }

    @Override
    public int updateByPrimaryKey(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.updateByPrimaryKey(companyUserContract);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.updateByPrimaryKeySelective(companyUserContract);
    }

    @Override
    public int selectCount(CompanyUserContract companyUserContract) {
        return companyUserContractMapper.selectCount(companyUserContract);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyUserContract> selectByInfo(Map<String, Object> map) { return companyUserContractMapper.selectByInfo(map); }



}
