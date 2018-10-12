package com.talentcenter.service;


import com.talentcenter.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MenuService extends BaseService<Menu> {
    List<Menu> selectByParentId(Long parentId);
}
