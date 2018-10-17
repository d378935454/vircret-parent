package com.talentcenter.service.impl;

import com.talentcenter.dao.CertificateMapper;
import com.talentcenter.entity.Certificate;
import com.talentcenter.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CertificateService")
public class CertificateServiceImpl implements CertificateService {
    @Autowired
    private CertificateMapper certificateMapper;

    @Override
    public List<Certificate> selectAll() {
        return certificateMapper.selectAll();
    }

    @Override
    public List<Certificate> select(Certificate certificate) {
        return certificateMapper.select(certificate);
    }

    @Override
    public List<Certificate> selectByInfo(Object o) {
       return certificateMapper.selectByExample(o);
//        return certificateMapper.selectByInfo(o);
    }

    @Override
    public Certificate selectByPrimaryKey(Long id) {
        return certificateMapper.selectByPrimaryKey(id);
    }

    @Override
    public Certificate selectOne(Certificate certificate) {
        return certificateMapper.selectOne(certificate);
    }

    @Override
    public int insert(Certificate certificate) {
        return certificateMapper.insert(certificate);
    }

    @Override
    public int insertSelective(Certificate certificate) {
        return certificateMapper.insertSelective(certificate);
    }

    @Override
    public int insertList(List<Certificate> certificates) {
        return certificateMapper.insertList(certificates);
    }

    @Override
    public int updateByPrimaryKey(Certificate certificate) {
        return certificateMapper.updateByPrimaryKey(certificate);
    }

    @Override
    public int updateByPrimaryKeySelective(Certificate certificate) {
        return certificateMapper.updateByPrimaryKeySelective(certificate);
    }

    @Override
    public int selectCount(Certificate certificate) {
        return certificateMapper.selectCount(certificate);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return certificateMapper.batchDel(map);
    }
}
