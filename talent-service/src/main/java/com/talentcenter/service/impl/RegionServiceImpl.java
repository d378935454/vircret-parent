package com.talentcenter.service.impl;

import com.talentcenter.dao.RegionMapper;
import com.talentcenter.entity.Region;
import com.talentcenter.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("RegionService")
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> selectAll() {
        return regionMapper.selectAll();
    }

    @Override
    public List<Region> select(Region region) {
        return regionMapper.select(region);
    }

    @Override
    public List<Region> selectByInfo(Object o) {
       return regionMapper.selectByExample(o);
//        return regionMapper.selectByInfo(o);
    }

    @Override
    public Region selectByPrimaryKey(Long id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Region selectOne(Region region) {
        return regionMapper.selectOne(region);
    }

    @Override
    public int insert(Region region) {
        return regionMapper.insert(region);
    }

    @Override
    public int insertSelective(Region region) {
        return regionMapper.insertSelective(region);
    }

    @Override
    public int insertList(List<Region> regions) {
        return regionMapper.insertList(regions);
    }

    @Override
    public int updateByPrimaryKey(Region region) {
        return regionMapper.updateByPrimaryKey(region);
    }

    @Override
    public int updateByPrimaryKeySelective(Region region) {
        return regionMapper.updateByPrimaryKeySelective(region);
    }

    @Override
    public int selectCount(Region region) {
        return regionMapper.selectCount(region);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<Region> selectByInfo(Map<String, Object> map) {
        return regionMapper.selectByInfo(map);
    }
}
