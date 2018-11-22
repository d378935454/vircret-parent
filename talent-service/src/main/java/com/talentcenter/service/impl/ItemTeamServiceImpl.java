package com.talentcenter.service.impl;

import com.talentcenter.dao.ItemTeamMapper;
import com.talentcenter.entity.ItemTeam;
import com.talentcenter.service.ItemTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ItemTeamService")
public class ItemTeamServiceImpl implements ItemTeamService {
    @Autowired
    private ItemTeamMapper itemTeamMapper;

    @Override
    public List<ItemTeam> selectAll() {
        return itemTeamMapper.selectAll();
    }

    @Override
    public List<ItemTeam> select(ItemTeam itemTeam) {
        return itemTeamMapper.select(itemTeam);
    }

    @Override
    public List<ItemTeam> selectByInfo(Object o) {
      //return itemTeamMapper.selectByExample(o);
       return itemTeamMapper.selectByInfo(o);
    }

    @Override
    public ItemTeam selectByPrimaryKey(Long id) {
        return itemTeamMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemTeam selectOne(ItemTeam itemTeam) {
        return itemTeamMapper.selectOne(itemTeam);
    }

    @Override
    public int insert(ItemTeam itemTeam) {
        return itemTeamMapper.insert(itemTeam);
    }

    @Override
    public int insertSelective(ItemTeam itemTeam) {
        return itemTeamMapper.insertSelective(itemTeam);
    }

    @Override
    public int insertList(List<ItemTeam> itemTeams) {
        return itemTeamMapper.insertList(itemTeams);
    }

    @Override
    public int updateByPrimaryKey(ItemTeam itemTeam) {
        return itemTeamMapper.updateByPrimaryKey(itemTeam);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemTeam itemTeam) {
        return itemTeamMapper.updateByPrimaryKeySelective(itemTeam);
    }

    @Override
    public int selectCount(ItemTeam itemTeam) {
        return itemTeamMapper.selectCount(itemTeam);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<ItemTeam> selectByInfo(Map<String, Object> map) {
        return itemTeamMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return itemTeamMapper.batchDel(map);
    }
}
