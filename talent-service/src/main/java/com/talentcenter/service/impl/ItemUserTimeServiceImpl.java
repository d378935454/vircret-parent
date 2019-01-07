package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemUserTimeMapper;
import com.talentcenter.entity.ItemUserTime;
import com.talentcenter.service.ItemUserTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemUserTimeService")
public class ItemUserTimeServiceImpl implements ItemUserTimeService {
    @Autowired
    private ItemUserTimeMapper itemUserTimeMapper;

    @Override
    public List<ItemUserTime> selectAll() {
        return itemUserTimeMapper.selectAll();
    }

    @Override
    public List<ItemUserTime> select(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.select(itemUserTime);
    }

    @Override
    public List<ItemUserTime> selectByInfo(Object o) {
       return itemUserTimeMapper.selectByExample(o);
//        return itemUserTimeMapper.selectByInfo(o);
    }

    @Override
    public ItemUserTime selectByPrimaryKey(Long id) {
        return itemUserTimeMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemUserTime selectOne(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.selectOne(itemUserTime);
    }

    @Override
    public int insert(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.insert(itemUserTime);
    }

    @Override
    public int insertSelective(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.insertSelective(itemUserTime);
    }

    @Override
    public int insertList(List<ItemUserTime> itemUserTimes) {
        return itemUserTimeMapper.insertList(itemUserTimes);
    }

    @Override
    public int updateByPrimaryKey(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.updateByPrimaryKey(itemUserTime);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.updateByPrimaryKeySelective(itemUserTime);
    }

    @Override
    public int selectCount(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.selectCount(itemUserTime);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemUserTime> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemUserTimeMapper.batchDel(map);
    }

    @Override
    public int updateItemUserTime(ItemUserTime itemUserTime) {
        return itemUserTimeMapper.updateItemUserTime(itemUserTime);
    }
}
