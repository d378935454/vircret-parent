package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyNatureMapper;
import com.talentcenter.entity.CompanyNature;
import com.talentcenter.service.CompanyNatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyNatureService")
public class CompanyNatureServiceImpl implements CompanyNatureService {
    @Autowired
    private CompanyNatureMapper companyNatureMapper;

    @Override
    public List<CompanyNature> selectAll() {
        return companyNatureMapper.selectAll();
    }

    @Override
    public List<CompanyNature> select(CompanyNature companyNature) {
        return companyNatureMapper.select(companyNature);
    }

    @Override
    public List<CompanyNature> selectByInfo(Object o) {
       return companyNatureMapper.selectByExample(o);
//        return companyNatureMapper.selectByInfo(o);
    }

    @Override
    public CompanyNature selectByPrimaryKey(Long id) {
        return companyNatureMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyNature selectOne(CompanyNature companyNature) {
        return companyNatureMapper.selectOne(companyNature);
    }

    @Override
    public int insert(CompanyNature companyNature) {
        return companyNatureMapper.insert(companyNature);
    }

    @Override
    public int insertSelective(CompanyNature companyNature) {
        return companyNatureMapper.insertSelective(companyNature);
    }

    @Override
    public int insertList(List<CompanyNature> companyNatures) {
        return companyNatureMapper.insertList(companyNatures);
    }

    @Override
    public int updateByPrimaryKey(CompanyNature companyNature) {
        return companyNatureMapper.updateByPrimaryKey(companyNature);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyNature companyNature) {
        return companyNatureMapper.updateByPrimaryKeySelective(companyNature);
    }

    @Override
    public int selectCount(CompanyNature companyNature) {
        return companyNatureMapper.selectCount(companyNature);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyNature> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return companyNatureMapper.batchDel(map);
    }
}
