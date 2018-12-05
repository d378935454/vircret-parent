package com.talentcenter.service.impl;

import com.talentcenter.dao.InfoChangeMapper;
import com.talentcenter.entity.InfoChange;
import com.talentcenter.service.InfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("InfoChangeService")
public class InfoChangeServiceImpl implements InfoChangeService {
    @Autowired
    private InfoChangeMapper infoChangeMapper;

    @Override
    public List<InfoChange> selectAll() {
        return infoChangeMapper.selectAll();
    }

    @Override
    public List<InfoChange> select(InfoChange infoChange) {
        return infoChangeMapper.select(infoChange);
    }

    @Override
    public List<InfoChange> selectByInfo(Object o) {
      //return infoChangeMapper.selectByExample(o);
       return infoChangeMapper.selectByInfo(o);
    }

    @Override
    public InfoChange selectByPrimaryKey(Long id) {
        return infoChangeMapper.selectByPrimaryKey(id);
    }

    @Override
    public InfoChange selectOne(InfoChange infoChange) {
        return infoChangeMapper.selectOne(infoChange);
    }

    @Override
    public int insert(InfoChange infoChange) {
        return infoChangeMapper.insert(infoChange);
    }

    @Override
    public int insertSelective(InfoChange infoChange) {
        return infoChangeMapper.insertSelective(infoChange);
    }

    @Override
    public int insertList(List<InfoChange> infoChanges) {
        return infoChangeMapper.insertList(infoChanges);
    }

    @Override
    public int updateByPrimaryKey(InfoChange infoChange) {
        return infoChangeMapper.updateByPrimaryKey(infoChange);
    }

    @Override
    public int updateByPrimaryKeySelective(InfoChange infoChange) {
        return infoChangeMapper.updateByPrimaryKeySelective(infoChange);
    }

    @Override
    public int selectCount(InfoChange infoChange) {
        return infoChangeMapper.selectCount(infoChange);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<InfoChange> selectByInfo(Map<String, Object> map) {
        return infoChangeMapper.selectByInfo(map);
    }

    @Override
    public int deleteByUserId(Long userId) {
        return infoChangeMapper.deleteByUserId(userId);
    }
}
