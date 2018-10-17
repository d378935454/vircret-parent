package com.talentcenter.service.impl;

import com.talentcenter.dao.MenuMapper;
import com.talentcenter.entity.Menu;
import com.talentcenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

    @Override
    public List<Menu> select(Menu menu) {
        return menuMapper.select(menu);
    }

    @Override
    public List<Menu> selectByInfo(Object o) {
       return menuMapper.selectByExample(o);
//        return menuMapper.selectByInfo(o);
    }

    @Override
    public Menu selectByPrimaryKey(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public Menu selectOne(Menu menu) {
        return menuMapper.selectOne(menu);
    }

    @Override
    public int insert(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int insertSelective(Menu menu) {
        return menuMapper.insertSelective(menu);
    }

    @Override
    public int insertList(List<Menu> menus) {
        return menuMapper.insertList(menus);
    }

    @Override
    public int updateByPrimaryKey(Menu menu) {
        return menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int selectCount(Menu menu) {
        return menuMapper.selectCount(menu);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<Menu> selectByInfo(Map<String, Object> map) {
        return menuMapper.selectByInfo(map);
    }


    @Override
    public List<Menu> selectByParentId(Long parentId) {
        return menuMapper.selectByParentId(parentId);
    }
}
