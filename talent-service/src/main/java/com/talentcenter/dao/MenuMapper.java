package com.talentcenter.dao;

import com.talentcenter.entity.Menu;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> selectByParentId(Long parentId);
}