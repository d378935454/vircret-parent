package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemTypeMapper;
import com.talentcenter.entity.ItemType;
import com.talentcenter.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemTypeService")
public class ItemTypeServiceImpl implements ItemTypeService {
    @Autowired
    private ItemTypeMapper itemTypeMapper;

    @Override
    public List<ItemType> selectAll() {
        return itemTypeMapper.selectAll();
    }

    @Override
    public List<ItemType> select(ItemType itemType) {
        return itemTypeMapper.select(itemType);
    }

    @Override
    public List<ItemType> selectByInfo(Object o) {
       return itemTypeMapper.selectByExample(o);
//        return itemTypeMapper.selectByInfo(o);
    }

    @Override
    public ItemType selectByPrimaryKey(Long id) {
        return itemTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemType selectOne(ItemType itemType) {
        return itemTypeMapper.selectOne(itemType);
    }

    @Override
    public int insert(ItemType itemType) {
        return itemTypeMapper.insert(itemType);
    }

    @Override
    public int insertSelective(ItemType itemType) {
        return itemTypeMapper.insertSelective(itemType);
    }

    @Override
    public int insertList(List<ItemType> itemTypes) {
        return itemTypeMapper.insertList(itemTypes);
    }

    @Override
    public int updateByPrimaryKey(ItemType itemType) {
        return itemTypeMapper.updateByPrimaryKey(itemType);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemType itemType) {
        return itemTypeMapper.updateByPrimaryKeySelective(itemType);
    }

    @Override
    public int selectCount(ItemType itemType) {
        return itemTypeMapper.selectCount(itemType);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemType> selectByInfo(Map<String, Object> map) {
        return itemTypeMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemTypeMapper.batchDel(map);
    }
}
