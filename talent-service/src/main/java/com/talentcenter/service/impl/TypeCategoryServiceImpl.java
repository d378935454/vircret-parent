package com.talentcenter.service.impl;

import com.talentcenter.dao.TypeCategoryMapper;
import com.talentcenter.entity.TypeCategory;
import com.talentcenter.service.TypeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("TypeCategoryService")
public class TypeCategoryServiceImpl implements TypeCategoryService {
    @Autowired
    private TypeCategoryMapper typeCategoryMapper;

    @Override
    public List<TypeCategory> selectAll() {
        return typeCategoryMapper.selectAll();
    }

    @Override
    public List<TypeCategory> select(TypeCategory typeCategory) {
        return typeCategoryMapper.select(typeCategory);
    }

    @Override
    public List<TypeCategory> selectByInfo(Object o) {
       return typeCategoryMapper.selectByExample(o);
//        return typeCategoryMapper.selectByInfo(o);
    }

    @Override
    public TypeCategory selectByPrimaryKey(Long id) {
        return typeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public TypeCategory selectOne(TypeCategory typeCategory) {
        return typeCategoryMapper.selectOne(typeCategory);
    }

    @Override
    public int insert(TypeCategory typeCategory) {
        return typeCategoryMapper.insert(typeCategory);
    }

    @Override
    public int insertSelective(TypeCategory typeCategory) {
        return typeCategoryMapper.insertSelective(typeCategory);
    }

    @Override
    public int insertList(List<TypeCategory> typeCategorys) {
        return typeCategoryMapper.insertList(typeCategorys);
    }

    @Override
    public int updateByPrimaryKey(TypeCategory typeCategory) {
        return typeCategoryMapper.updateByPrimaryKey(typeCategory);
    }

    @Override
    public int updateByPrimaryKeySelective(TypeCategory typeCategory) {
        return typeCategoryMapper.updateByPrimaryKeySelective(typeCategory);
    }

    @Override
    public int selectCount(TypeCategory typeCategory) {
        return typeCategoryMapper.selectCount(typeCategory);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<TypeCategory> selectByInfo(Map<String, Object> map) {
        return typeCategoryMapper.selectByInfo(map);
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return typeCategoryMapper.batchDel(map);
    }
}
