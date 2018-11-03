package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemConfigMapper;
import com.talentcenter.entity.ItemConfig;
import com.talentcenter.service.ItemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemConfigService")
public class ItemConfigServiceImpl implements ItemConfigService {
    @Autowired
    private ItemConfigMapper itemConfigMapper;

    @Override
    public List<ItemConfig> selectAll() {
        return itemConfigMapper.selectAll();
    }

    @Override
    public List<ItemConfig> select(ItemConfig itemConfig) {
        return itemConfigMapper.select(itemConfig);
    }

    @Override
    public List<ItemConfig> selectByInfo(Object o) {
       return itemConfigMapper.selectByExample(o);
//        return itemConfigMapper.selectByInfo(o);
    }

    @Override
    public ItemConfig selectByPrimaryKey(Long id) {
        return itemConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemConfig selectOne(ItemConfig itemConfig) {
        return itemConfigMapper.selectOne(itemConfig);
    }

    @Override
    public int insert(ItemConfig itemConfig) {
        return itemConfigMapper.insert(itemConfig);
    }

    @Override
    public int insertSelective(ItemConfig itemConfig) {
        return itemConfigMapper.insertSelective(itemConfig);
    }

    @Override
    public int insertList(List<ItemConfig> itemConfigs) {
        return itemConfigMapper.insertList(itemConfigs);
    }

    @Override
    public int updateByPrimaryKey(ItemConfig itemConfig) {
        return itemConfigMapper.updateByPrimaryKey(itemConfig);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemConfig itemConfig) {
        return itemConfigMapper.updateByPrimaryKeySelective(itemConfig);
    }

    @Override
    public int selectCount(ItemConfig itemConfig) {
        return itemConfigMapper.selectCount(itemConfig);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemConfig> selectByInfo(Map<String, Object> map) {
        return itemConfigMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemConfigMapper.batchDel(map);
    }


}
