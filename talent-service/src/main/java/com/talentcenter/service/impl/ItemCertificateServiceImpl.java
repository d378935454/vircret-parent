package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemCertificateMapper;
import com.talentcenter.entity.ItemCertificate;
import com.talentcenter.service.ItemCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemCertificateService")
public class ItemCertificateServiceImpl implements ItemCertificateService {
    @Autowired
    private ItemCertificateMapper itemCertificateMapper;

    @Override
    public List<ItemCertificate> selectAll() {
        return itemCertificateMapper.selectAll();
    }

    @Override
    public List<ItemCertificate> select(ItemCertificate itemCertificate) {
        return itemCertificateMapper.select(itemCertificate);
    }

    @Override
    public List<ItemCertificate> selectByInfo(Object o) {
       return itemCertificateMapper.selectByExample(o);
//        return itemCertificateMapper.selectByInfo(o);
    }

    @Override
    public ItemCertificate selectByPrimaryKey(Long id) {
        return itemCertificateMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemCertificate selectOne(ItemCertificate itemCertificate) {
        return itemCertificateMapper.selectOne(itemCertificate);
    }

    @Override
    public int insert(ItemCertificate itemCertificate) {
        return itemCertificateMapper.insert(itemCertificate);
    }

    @Override
    public int insertSelective(ItemCertificate itemCertificate) {
        return itemCertificateMapper.insertSelective(itemCertificate);
    }

    @Override
    public int insertList(List<ItemCertificate> itemCertificates) {
        return itemCertificateMapper.insertList(itemCertificates);
    }

    @Override
    public int updateByPrimaryKey(ItemCertificate itemCertificate) {
        return itemCertificateMapper.updateByPrimaryKey(itemCertificate);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemCertificate itemCertificate) {
        return itemCertificateMapper.updateByPrimaryKeySelective(itemCertificate);
    }

    @Override
    public int selectCount(ItemCertificate itemCertificate) {
        return itemCertificateMapper.selectCount(itemCertificate);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemCertificate> selectByInfo(Map<String, Object> map) {
        return itemCertificateMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemCertificateMapper.batchDel(map);
    }

    @Override
    public int delByItemConfigId(Long itemConfigId) {
        return itemCertificateMapper.delByItemConfigId(itemConfigId);
    }
}
