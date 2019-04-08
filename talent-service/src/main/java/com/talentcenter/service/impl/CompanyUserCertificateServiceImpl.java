package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyUserCertificateMapper;
import com.talentcenter.entity.CompanyUserCertificate;
import com.talentcenter.service.CompanyUserCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyUserCertificateService")
public class CompanyUserCertificateServiceImpl implements CompanyUserCertificateService {
    @Autowired
    private CompanyUserCertificateMapper companyUserCertificateMapper;

    @Override
    public List<CompanyUserCertificate> selectAll() {
        return companyUserCertificateMapper.selectAll();
    }

    @Override
    public List<CompanyUserCertificate> select(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.select(companyUserCertificate);
    }

    @Override
    public List<CompanyUserCertificate> selectByInfo(Object o) {
      // return companyUserCertificateMapper.selectByExample(o);
      return companyUserCertificateMapper.selectByInfo(o);
    }

    @Override
    public CompanyUserCertificate selectByPrimaryKey(Long id) {
        return companyUserCertificateMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyUserCertificate selectOne(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.selectOne(companyUserCertificate);
    }

    @Override
    public int insert(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.insert(companyUserCertificate);
    }

    @Override
    public int insertSelective(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.insertSelective(companyUserCertificate);
    }

    @Override
    public int insertList(List<CompanyUserCertificate> companyUserCertificates) {
        return companyUserCertificateMapper.insertList(companyUserCertificates);
    }

    @Override
    public int updateByPrimaryKey(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.updateByPrimaryKey(companyUserCertificate);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.updateByPrimaryKeySelective(companyUserCertificate);
    }

    @Override
    public int selectCount(CompanyUserCertificate companyUserCertificate) {
        return companyUserCertificateMapper.selectCount(companyUserCertificate);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyUserCertificate> selectByInfo(Map<String, Object> map) { return companyUserCertificateMapper.selectByInfo(map); }


    @Override
    public int deleteByUserId(Long userId) {
        return companyUserCertificateMapper.deleteByUserId(userId);
    }

    @Override
    public List<CompanyUserCertificate> selectByUserId(Map<String, Object> map) {
        return companyUserCertificateMapper.selectByUserId(map);
    }
}
