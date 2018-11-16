package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemTalentContentMapper;
import com.talentcenter.entity.ItemTalentContent;
import com.talentcenter.service.ItemTalentContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemTalentContentService")
public class ItemTalentContentServiceImpl implements ItemTalentContentService {
    @Autowired
    private ItemTalentContentMapper itemTalentContentMapper;

    @Override
    public List<ItemTalentContent> selectAll() {
        return itemTalentContentMapper.selectAll();
    }

    @Override
    public List<ItemTalentContent> select(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.select(itemTalentContent);
    }

    @Override
    public List<ItemTalentContent> selectByInfo(Object o) {
       return itemTalentContentMapper.selectByExample(o);
//        return itemTalentContentMapper.selectByInfo(o);
    }

    @Override
    public ItemTalentContent selectByPrimaryKey(Long id) {
        return itemTalentContentMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemTalentContent selectOne(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.selectOne(itemTalentContent);
    }

    @Override
    public int insert(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.insert(itemTalentContent);
    }

    @Override
    public int insertSelective(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.insertSelective(itemTalentContent);
    }

    @Override
    public int insertList(List<ItemTalentContent> itemTalentContents) {
        return itemTalentContentMapper.insertList(itemTalentContents);
    }

    @Override
    public int updateByPrimaryKey(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.updateByPrimaryKey(itemTalentContent);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.updateByPrimaryKeySelective(itemTalentContent);
    }

    @Override
    public int selectCount(ItemTalentContent itemTalentContent) {
        return itemTalentContentMapper.selectCount(itemTalentContent);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemTalentContent> selectByInfo(Map<String, Object> map) {
        return itemTalentContentMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemTalentContentMapper.batchDel(map);
    }

    @Override
    public int delByItemConfigId(Long itemConfigId) {
        return itemTalentContentMapper.delByItemConfigId(itemConfigId);
    }
}
