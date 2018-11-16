package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemMapper;
import com.talentcenter.entity.Item;
import com.talentcenter.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> selectAll() {
        return itemMapper.selectAll();
    }

    @Override
    public List<Item> select(Item item) {
        return itemMapper.select(item);
    }

    @Override
    public List<Item> selectByInfo(Object o) {
      //return itemMapper.selectByExample(o);
       return itemMapper.selectByInfo(o);
    }

    @Override
    public Item selectByPrimaryKey(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public Item selectOne(Item item) {
        return itemMapper.selectOne(item);
    }

    @Override
    public int insert(Item item) {
        return itemMapper.insert(item);
    }

    @Override
    public int insertSelective(Item item) {
        return itemMapper.insertSelective(item);
    }

    @Override
    public int insertList(List<Item> items) {
        return itemMapper.insertList(items);
    }

    @Override
    public int updateByPrimaryKey(Item item) {
        return itemMapper.updateByPrimaryKey(item);
    }

    @Override
    public int updateByPrimaryKeySelective(Item item) {
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public int selectCount(Item item) {
        return itemMapper.selectCount(item);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<Item> selectByInfo(Map<String, Object> map) {
        return itemMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemMapper.batchDel(map);
    }

    @Override
    public List<Item> selectByUserId(Long userId) { return itemMapper.selectByUserId(userId); }
}
