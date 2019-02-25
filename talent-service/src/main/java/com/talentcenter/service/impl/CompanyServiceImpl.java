package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyMapper;
import com.talentcenter.entity.Company;
import com.talentcenter.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public List<Company> select(Company company) {
        return companyMapper.select(company);
    }

    @Override
    public List<Company> selectByInfo(Object o) {
       return companyMapper.selectByExample(o);
//        return companyMapper.selectByInfo(o);
    }

    @Override
    public Company selectByPrimaryKey(Long id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    @Override
    public Company selectOne(Company company) {
        return companyMapper.selectOne(company);
    }

    @Override
    public int insert(Company company) {
        return companyMapper.insert(company);
    }

    @Override
    public int insertSelective(Company company) {
        return companyMapper.insertSelective(company);
    }

    @Override
    public int insertList(List<Company> companys) {
        return companyMapper.insertList(companys);
    }

    @Override
    public int updateByPrimaryKey(Company company) {
        return companyMapper.updateByPrimaryKey(company);
    }

    @Override
    public int updateByPrimaryKeySelective(Company company) {
        return companyMapper.updateByPrimaryKeySelective(company);
    }

    @Override
    public int selectCount(Company company) {
        return companyMapper.selectCount(company);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<Company> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return companyMapper.batchDel(map);
    }

    @Override
    public List<Map<String, Object>> selectCompanyCheckedItem(Map<String, Object> map) {
        return companyMapper.selectCompanyCheckedItem(map);
    }

    @Override
    public List<Company> selectCheckCompany(String streetId) {
        return companyMapper.selectCheckCompany(streetId);
    }
}
