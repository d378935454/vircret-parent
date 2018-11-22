package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemTeamContentMapper;
import com.talentcenter.entity.ItemTeamContent;
import com.talentcenter.service.ItemTeamContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemTeamContentService")
public class ItemTeamContentServiceImpl implements ItemTeamContentService {
    @Autowired
    private ItemTeamContentMapper itemTeamContentMapper;

    @Override
    public List<ItemTeamContent> selectAll() {
        return itemTeamContentMapper.selectAll();
    }

    @Override
    public List<ItemTeamContent> select(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.select(itemTeamContent);
    }

    @Override
    public List<ItemTeamContent> selectByInfo(Object o) {
      //return itemTeamContentMapper.selectByExample(o);
       return itemTeamContentMapper.selectByInfo(o);
    }

    @Override
    public ItemTeamContent selectByPrimaryKey(Long id) {
        return itemTeamContentMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemTeamContent selectOne(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.selectOne(itemTeamContent);
    }

    @Override
    public int insert(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.insert(itemTeamContent);
    }

    @Override
    public int insertSelective(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.insertSelective(itemTeamContent);
    }

    @Override
    public int insertList(List<ItemTeamContent> itemTeamContents) {
        return itemTeamContentMapper.insertList(itemTeamContents);
    }

    @Override
    public int updateByPrimaryKey(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.updateByPrimaryKey(itemTeamContent);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.updateByPrimaryKeySelective(itemTeamContent);
    }

    @Override
    public int selectCount(ItemTeamContent itemTeamContent) {
        return itemTeamContentMapper.selectCount(itemTeamContent);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemTeamContent> selectByInfo(Map<String, Object> map) {
        return itemTeamContentMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemTeamContentMapper.batchDel(map);
    }

    @Override
    public int delByTeamId(Long itemTeamId) {
        return itemTeamContentMapper.delByTeamId(itemTeamId);
    }
}
