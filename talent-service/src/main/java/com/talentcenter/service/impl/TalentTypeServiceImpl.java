package com.talentcenter.service.impl;

import com.talentcenter.dao.TalentTypeMapper;
import com.talentcenter.entity.TalentType;
import com.talentcenter.service.TalentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("TalentTypeService")
public class TalentTypeServiceImpl implements TalentTypeService {
    @Autowired
    private TalentTypeMapper talentTypeMapper;

    @Override
    public List<TalentType> selectAll() {
        return talentTypeMapper.selectAll();
    }

    @Override
    public List<TalentType> select(TalentType talentType) {
        return talentTypeMapper.select(talentType);
    }

    @Override
    public List<TalentType> selectByInfo(Object o) {
       return talentTypeMapper.selectByExample(o);
//        return talentTypeMapper.selectByInfo(o);
    }

    @Override
    public TalentType selectByPrimaryKey(Long id) {
        return talentTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public TalentType selectOne(TalentType talentType) {
        return talentTypeMapper.selectOne(talentType);
    }

    @Override
    public int insert(TalentType talentType) {
        return talentTypeMapper.insert(talentType);
    }

    @Override
    public int insertSelective(TalentType talentType) {
        return talentTypeMapper.insertSelective(talentType);
    }

    @Override
    public int insertList(List<TalentType> talentTypes) {
        return talentTypeMapper.insertList(talentTypes);
    }

    @Override
    public int updateByPrimaryKey(TalentType talentType) {
        return talentTypeMapper.updateByPrimaryKey(talentType);
    }

    @Override
    public int updateByPrimaryKeySelective(TalentType talentType) {
        return talentTypeMapper.updateByPrimaryKeySelective(talentType);
    }

    @Override
    public int selectCount(TalentType talentType) {
        return talentTypeMapper.selectCount(talentType);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<TalentType> selectByInfo(Map<String, Object> map) {
        return talentTypeMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return talentTypeMapper.batchDel(map);
    }
}
