package com.talentcenter.service.impl;

import com.talentcenter.dao.CompanyItemMapper;
import com.talentcenter.entity.CompanyItem;
import com.talentcenter.service.CompanyItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CompanyItemService")
public class CompanyItemServiceImpl implements CompanyItemService {
    @Autowired
    private CompanyItemMapper companyItemMapper;

    @Override
    public List<CompanyItem> selectAll() {
        return companyItemMapper.selectAll();
    }

    @Override
    public List<CompanyItem> select(CompanyItem companyItem) {
        return companyItemMapper.select(companyItem);
    }

    @Override
    public List<CompanyItem> selectByInfo(Object o) {
       return companyItemMapper.selectByExample(o);
//        return companyItemMapper.selectByInfo(o);
    }

    @Override
    public CompanyItem selectByPrimaryKey(Long id) {
        return companyItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public CompanyItem selectOne(CompanyItem companyItem) {
        return companyItemMapper.selectOne(companyItem);
    }

    @Override
    public int insert(CompanyItem companyItem) {
        return companyItemMapper.insert(companyItem);
    }

    @Override
    public int insertSelective(CompanyItem companyItem) {
        return companyItemMapper.insertSelective(companyItem);
    }

    @Override
    public int insertList(List<CompanyItem> companyItems) {
        return companyItemMapper.insertList(companyItems);
    }

    @Override
    public int updateByPrimaryKey(CompanyItem companyItem) {
        return companyItemMapper.updateByPrimaryKey(companyItem);
    }

    @Override
    public int updateByPrimaryKeySelective(CompanyItem companyItem) {
        return companyItemMapper.updateByPrimaryKeySelective(companyItem);
    }

    @Override
    public int selectCount(CompanyItem companyItem) {
        return companyItemMapper.selectCount(companyItem);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<CompanyItem> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return companyItemMapper.batchDel(map);
    }
    @Override
    public int delByCompanyId(Long companyId){
        return companyItemMapper.delByCompanyId(companyId);
    }
}
