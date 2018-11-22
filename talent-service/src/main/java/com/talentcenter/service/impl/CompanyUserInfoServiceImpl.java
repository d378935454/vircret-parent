package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyUserInfoMapper;
import com.talentcenter.entity.CompanyUserInfo;
import com.talentcenter.service.CompanyUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyUserInfoService")
public class CompanyUserInfoServiceImpl implements CompanyUserInfoService {
    @Autowired
    private CompanyUserInfoMapper companyUserInfoMapper;

    @Override
    public List<CompanyUserInfo> selectAll() {
        return companyUserInfoMapper.selectAll();
    }

    @Override
    public List<CompanyUserInfo> select(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.select(companyUserInfo);
    }

    @Override
    public List<CompanyUserInfo> selectByInfo(Object o) {
      // return companyUserInfoMapper.selectByExample(o);
      return companyUserInfoMapper.selectByInfo(o);
    }

    @Override
    public CompanyUserInfo selectByPrimaryKey(Long id) {
        return companyUserInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyUserInfo selectOne(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.selectOne(companyUserInfo);
    }

    @Override
    public int insert(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.insert(companyUserInfo);
    }

    @Override
    public int insertSelective(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.insertSelective(companyUserInfo);
    }

    @Override
    public int insertList(List<CompanyUserInfo> companyUserInfos) {
        return companyUserInfoMapper.insertList(companyUserInfos);
    }

    @Override
    public int updateByPrimaryKey(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.updateByPrimaryKey(companyUserInfo);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.updateByPrimaryKeySelective(companyUserInfo);
    }

    @Override
    public int selectCount(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.selectCount(companyUserInfo);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyUserInfo> selectByInfo(Map<String, Object> map) { return companyUserInfoMapper.selectByInfo(map); }


    @Override
    public int updateByUserId(CompanyUserInfo companyUserInfo) {
        return companyUserInfoMapper.updateByUserId(companyUserInfo);
    }
}
